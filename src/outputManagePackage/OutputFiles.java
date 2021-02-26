package outputManagePackage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;
import dataManagePackage.Receipt.Receipt;
import identifiersManagePackage.IdentifiersManager;

public class OutputFiles {
	
	private static OutputFiles instance;

	private String[] starts = new String[] {"Name","AFM","Status","Income","Receipts",
											"Receipt ID","Date","Kind",
											"Amount","Company","Country",
											"City","Street","Number"};
	
	private String[] logStarts = new String[] {	"Name","AFM","Income","Basic Tax",
												"Tax Increase","Tax Decrease","Total Tax",
												"Receipts","Entertainment","Basic",
												"Travel","Health","Other"};
	
	private Database database = Database.getInstance();
	private IdentifiersManager identifiersManager = IdentifiersManager.getInstance();
	private FileWriter fileWriter = FileWriter.getInstance();
	
	public void saveUpdatedTaxpayerInputFile(String filePath, int taxpayerIndex){
		String[][] identifiers = identifiersManager.prepareIdentifiers(filePath, starts);
		String[] receiptsIdentifiers = new String[] {identifiers[0][4],identifiers[1][4]};
		
		String[] starts = identifiers[0];
		String[] ends = identifiers[1];
		
		PrintWriter outputStream = initOutputStream(filePath);
		
		Taxpayer taxpayer = database.getTaxpayerFromArrayList(taxpayerIndex);

		fileWriter.writeTaxpayer(outputStream, taxpayer, starts, ends);
		
		if (taxpayer.getReceiptsArrayList().size() > 0){
			outputStream.println();
			outputStream.println(receiptsIdentifiers[0].replace(" ", "")); //to ensure that testing is done correctly
			outputStream.println();
			
			for (Receipt receipt : taxpayer.getReceiptsArrayList()){
				fileWriter.writeReceipt(outputStream, receipt, starts, ends);
			}
			outputStream.println(receiptsIdentifiers[1].replace(" ", "")); //to ensure that testing is done correctly
		}
		
		outputStream.close();

	}
	
	public void saveTaxpayerInfoToLogFile(	String folderSavePath, int taxpayerIndex, 
											String type, boolean testing){ //added the boolean parameter to exclude the JOptionPane from testing
		
		Taxpayer taxpayer = database.getTaxpayerFromArrayList(taxpayerIndex);
		String filepath = folderSavePath+File.separator+taxpayer.getAFM()+"_LOG."+type;
		
		String[][] identifiers = identifiersManager.prepareIdentifiers(filepath, logStarts);
		
		String[] starts = identifiers[0];
		String[] ends = identifiers[1];
		
		PrintWriter outputStream = initOutputStream(filepath);
		
		fileWriter.writeLogFile(outputStream, taxpayer, starts, ends);
		
		if(!testing) { JOptionPane.showMessageDialog(null, "Η αποθήκευση ολοκληρώθηκε", "Μήνυμα", JOptionPane.INFORMATION_MESSAGE); }
	}
	
	
	
	private PrintWriter initOutputStream(String filePath) {
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(new FileOutputStream(filePath));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening: "+filePath);
		}
		return outputStream;	
		
	}

	
	public static OutputFiles getInstance() {
		if(instance == null) {
			instance = new OutputFiles();
		}
		return instance;
	}

}

