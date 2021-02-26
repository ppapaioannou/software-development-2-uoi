package inputManagePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;
import identifiersManagePackage.IdentifiersManager;

public class InputSystem {
	
	private static InputSystem instance;
	
	private String[] fileTags = new String[] {	"Name","AFM","Status","Income",
												"Receipt ID","Date","Kind",
												"Amount","Company","Country",
												"City","Street","Number"};
	
	private Database database = Database.getInstance();
	private IdentifiersManager infoIdentifiersManager = IdentifiersManager.getInstance();
	private InfoFilesParser infoFilesParser = InfoFilesParser.getInstance();
	
	
	public void addTaxpayersIntoDatabase(String afmInfoFilesFolderPath, List<String> taxpayersAfmInfoFiles){
		for (String afmInfoFile : taxpayersAfmInfoFiles)
		{
			String[][] identifiers = infoIdentifiersManager.prepareIdentifiers(afmInfoFile, fileTags);
			Scanner inputStream = initInputStream(afmInfoFilesFolderPath, afmInfoFile);
			
			Taxpayer newTaxpayer = infoFilesParser.createTaxpayer(inputStream, identifiers);
			database.addTaxpayerToList(newTaxpayer);
			
			inputStream.close();
		}
	}

	private Scanner initInputStream(String afmInfoFilesFolderPath, String afmInfoFile) {
		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new FileInputStream(afmInfoFilesFolderPath+
															File.separator+afmInfoFile));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Problem opening " + afmInfoFile + " file.");
			System.exit(0);
		}
		return inputStream;	
		
	}
	
	
	public static InputSystem getInstance() {
		if (instance == null) {
			instance = new InputSystem();
		}
		return instance;
	}

}
