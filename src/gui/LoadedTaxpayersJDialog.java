package gui;
import dataManagePackage.Database;
import dataManagePackage.Taxpayer;
import outputManagePackage.BarChart;
import outputManagePackage.OutputFiles;
import outputManagePackage.PieChart;

import java.awt.Color;
import java.awt.Font;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadedTaxpayersJDialog extends JDialog {

	private JList loadedTaxpayersJList;
	private JFrame appMainWindow;
	
	private Database database = Database.getInstance();
	private OutputFiles outputFiles = OutputFiles.getInstance();
	private PieChart pieChart = PieChart.getInstance();
	private BarChart barChart = BarChart.getInstance();
	
	
	public LoadedTaxpayersJDialog(JFrame appMainWindow) {
		
		this.appMainWindow = appMainWindow;
		
		setResizable(false);
		setBounds(100, 100, 556, 525);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		//setType(Type.POPUP); /*CHANGED*/
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Φορολογούμενοι");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 280, 400);
		getContentPane().add(scrollPane);
		
		loadedTaxpayersJList = new JList();
		loadedTaxpayersJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(loadedTaxpayersJList);
		loadedTaxpayersJList.setForeground(Color.BLUE);
		loadedTaxpayersJList.setFont(new Font("Tahoma", Font.BOLD, 14));
		loadedTaxpayersJList.setVisibleRowCount(100);
		
		JButton showSelectedTaxpayerInfoButton = new JButton();
		showSelectedTaxpayerInfoButton.setHorizontalAlignment(SwingConstants.LEFT);
		String buttonText = "<html>"
				+ "Εμφάνιση στοιχείων"
				+ "<br>"
				+ "επιλεγμένου φορολογούμενου"
				+ "</html>";
		showSelectedTaxpayerInfoButton.setText(buttonText);
		showSelectedTaxpayerInfoButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		showSelectedTaxpayerInfoButton.setBounds(300, 12, 240, 71);
		getContentPane().add(showSelectedTaxpayerInfoButton);
		
		JButton deleteSelectedTaxpayerFromDatabaseButton = new JButton();
		deleteSelectedTaxpayerFromDatabaseButton.setHorizontalAlignment(SwingConstants.LEFT);
		buttonText = "<html>"
				+ "Διαγραφή επιλεγμένου"
				+ "<br>"
				+ "φορολογούμενου"
				+ "</html>";
		deleteSelectedTaxpayerFromDatabaseButton.setText(buttonText);
		deleteSelectedTaxpayerFromDatabaseButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		deleteSelectedTaxpayerFromDatabaseButton.setBounds(300, 93, 240, 71);
		getContentPane().add(deleteSelectedTaxpayerFromDatabaseButton);
		
		JButton showSelectedTaxpayerReceiptsButton = new JButton();
		buttonText = "<html>"
				+ "Εμφάνιση αποδείξεων"
				+ "<br>"
				+ "επιλεγμένου φορολογούμενου"
				+ "</html>";
		showSelectedTaxpayerReceiptsButton.setText(buttonText);
		showSelectedTaxpayerReceiptsButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		showSelectedTaxpayerReceiptsButton.setBounds(300, 175, 240, 71);
		getContentPane().add(showSelectedTaxpayerReceiptsButton);
		
		JButton showSelectedTaxpayerPieChartButton = new JButton("Διάγραμμα πίτας αποδείξεων");
		showSelectedTaxpayerPieChartButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		showSelectedTaxpayerPieChartButton.setBounds(300, 257, 240, 71);
		getContentPane().add(showSelectedTaxpayerPieChartButton);
		
		JButton showSelectedTaxpayerBarChartButton = new JButton("Ραβδόγραμμα ανάλυσης φόρου");
		showSelectedTaxpayerBarChartButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		showSelectedTaxpayerBarChartButton.setBounds(300, 340, 240, 71);
		getContentPane().add(showSelectedTaxpayerBarChartButton);
		
		JButton saveSelectedTaxpayerInfoToTxtButton = new JButton("Αποθήκευση στοιχείων φορολογούμενου σε txt");
		saveSelectedTaxpayerInfoToTxtButton.setForeground(Color.RED);
		saveSelectedTaxpayerInfoToTxtButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		saveSelectedTaxpayerInfoToTxtButton.setBounds(10, 422, 530, 29);
		getContentPane().add(saveSelectedTaxpayerInfoToTxtButton);
		
		JButton saveSelectedTaxpayerInfoToXmlButton = new JButton("Αποθήκευση στοιχείων φορολογούμενου σε xml");
		saveSelectedTaxpayerInfoToXmlButton.setForeground(Color.RED);
		saveSelectedTaxpayerInfoToXmlButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		saveSelectedTaxpayerInfoToXmlButton.setBounds(10, 455, 530, 29);
		getContentPane().add(saveSelectedTaxpayerInfoToXmlButton);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) loadedTaxpayersJList.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		showSelectedTaxpayerInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loadedTaxpayersJList.getSelectedIndex()!=-1){
					Taxpayer taxpayer = database.getTaxpayerFromArrayList(loadedTaxpayersJList.getSelectedIndex());
					JOptionPane.showMessageDialog(null, taxpayer.toString(),  loadedTaxpayersJList.getSelectedValue().toString(), JOptionPane.PLAIN_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		deleteSelectedTaxpayerFromDatabaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loadedTaxpayersJList.getSelectedIndex()!=-1){
					int dialogResult = JOptionPane.showConfirmDialog (null, "Διαγραφή επιλεγμένου φορολογούμενου("+loadedTaxpayersJList.getSelectedValue().toString()+") απο την βάση δεδομένων?", "Επιβεβαίωση διαγραφής", JOptionPane.YES_NO_OPTION);
					if(dialogResult == JOptionPane.YES_OPTION){
						database.removeTaxpayerFromArrayList(loadedTaxpayersJList.getSelectedIndex());
						
						fillLoadedTaxpayersJList();
						
						JLabel totalLoadedTaxpayersJLabel = (JLabel)appMainWindow.getContentPane().getComponent(1);
						totalLoadedTaxpayersJLabel.setText(Integer.toString(database.getTaxpayersArrayListSize()));
						
						if (database.getTaxpayersArrayListSize()==0) dispose();
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		showSelectedTaxpayerReceiptsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (loadedTaxpayersJList.getSelectedIndex()!=-1){
					TaxpayerReceiptsManagementJDialog taxpayerReceiptsManagementJDialog = new TaxpayerReceiptsManagementJDialog(loadedTaxpayersJList.getSelectedValue().toString(), loadedTaxpayersJList.getSelectedIndex());
					taxpayerReceiptsManagementJDialog.fillTaxpayerReceiptsJList();
					taxpayerReceiptsManagementJDialog.setVisible(true);
				}
				else{
					JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		showSelectedTaxpayerPieChartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int taxpayerIndex = loadedTaxpayersJList.getSelectedIndex();
				if (taxpayerIndex!=-1){
					pieChart.drawPieChart(taxpayerIndex);//outputSystem.createTaxpayerReceiptsPieJFreeChart(taxpayerIndex);
				}
				else{
					JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		showSelectedTaxpayerBarChartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int taxpayerIndex = loadedTaxpayersJList.getSelectedIndex();
				if (taxpayerIndex!=-1){
					barChart.drawBarChart(taxpayerIndex);//outputSystem.createTaxpayerTaxAnalysisBarJFreeChart(taxpayerIndex);
				}
				else{
					JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		saveSelectedTaxpayerInfoToTxtButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int taxpayerIndex = loadedTaxpayersJList.getSelectedIndex();
				if (taxpayerIndex!=-1){
					JFileChooser saveFileFolderChooser = new JFileChooser();
					saveFileFolderChooser.setCurrentDirectory(new java.io.File("."));
					Taxpayer taxpayer = database.getTaxpayerFromArrayList(taxpayerIndex);
					saveFileFolderChooser.setDialogTitle("Επιλέξτε φάκελο αποθήκευσης "+taxpayer.getAFM()+"_LOG.txt");
					saveFileFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					if(saveFileFolderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					    String savePath = saveFileFolderChooser.getSelectedFile().toString();
					    
					    outputFiles.saveTaxpayerInfoToLogFile(savePath, taxpayerIndex,"txt",false);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		saveSelectedTaxpayerInfoToXmlButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int taxpayerIndex = loadedTaxpayersJList.getSelectedIndex();
				if (taxpayerIndex!=-1){
					JFileChooser saveFileFolderChooser = new JFileChooser();
					saveFileFolderChooser.setCurrentDirectory(new java.io.File("."));
					Taxpayer taxpayer = database.getTaxpayerFromArrayList(taxpayerIndex);
					saveFileFolderChooser.setDialogTitle("Επιλέξτε φάκελο αποθήκευσης "+taxpayer.getAFM()+"_LOG.xml");
					saveFileFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					if(saveFileFolderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					    String savePath = saveFileFolderChooser.getSelectedFile().toString();
					    
					    outputFiles.saveTaxpayerInfoToLogFile(savePath, taxpayerIndex,"xml",false);
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Δεν έχεις επιλέξει κάποιον φορολογούμενο απο την λίστα.", "Σφάλμα", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	public void fillLoadedTaxpayersJList(){
		Database database = Database.getInstance();
		
		String[] jlistValues = database.getTaxpayersNameAfmValuesPairList();
		
		loadedTaxpayersJList.setModel(new AbstractListModel() {
			String[] values = jlistValues;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
	}
}

