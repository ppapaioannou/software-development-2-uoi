package outputManagePackage;

import java.awt.Dialog.ModalExclusionType;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;

public class BarChart {
	
	private static BarChart instance;

	private DefaultCategoryDataset taxAnalysisBarChartDataset;

	public void drawBarChart(int taxpayerIndex) {
		Database database = Database.getInstance();
		
		createTaxpayerTaxAnalysisBarJFreeChart(taxpayerIndex);
		
		JFreeChart taxAnalysisJFreeChart = ChartFactory.createBarChart("Tax Analysis Bar Chart", "",  "Tax Analysis in $", taxAnalysisBarChartDataset, PlotOrientation.VERTICAL, true, true, false);

		ChartFrame receiptPieChartFrame = new ChartFrame(database.getTaxpayerNameAfmValuesPairList(taxpayerIndex), taxAnalysisJFreeChart);
		receiptPieChartFrame.pack();
		receiptPieChartFrame.setResizable(false);
		receiptPieChartFrame.setLocationRelativeTo(null);
		receiptPieChartFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		receiptPieChartFrame.setVisible(true);
		
	}
	
	public void createTaxpayerTaxAnalysisBarJFreeChart(int taxpayerIndex){
		Database database = Database.getInstance();
		
		taxAnalysisBarChartDataset = new DefaultCategoryDataset();
		Taxpayer taxpayer = database.getTaxpayerFromArrayList(taxpayerIndex);
		
		String taxVariationType = taxpayer.getTaxInxrease()!=0? "Tax Increase" : "Tax Decrease";
		double taxVariationAmount = taxpayer.getTaxInxrease()!=0? taxpayer.getTaxInxrease() : taxpayer.getTaxDecrease();// was taxpayer.getTaxDecrease()*(-1)
		
		taxAnalysisBarChartDataset.setValue(taxpayer.getBasicTax(), "Tax", "Basic Tax");
		taxAnalysisBarChartDataset.setValue(taxVariationAmount, "Tax", taxVariationType);
		taxAnalysisBarChartDataset.setValue(taxpayer.getTotalTax(), "Tax", "Total Tax");

	}
	
	public DefaultCategoryDataset getTaxAnalysisBarChartDataset() {
		return taxAnalysisBarChartDataset;
	}
	
	public static BarChart getInstance() {
		if(instance == null) {
			instance = new BarChart();
		}
		return instance;
	}
	
}
