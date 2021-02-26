package outputManagePackage;

import java.awt.Dialog.ModalExclusionType;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import dataManagePackage.Database;
import dataManagePackage.Taxpayer;

public class PieChart {
	
	private static PieChart instance;
	
	private static DefaultPieDataset receiptPieChartDataset;
	private static JFreeChart receiptPieJFreeChart;
	private static PiePlot piePlot;
	private static ChartFrame receiptPieChartFrame;

	public DefaultPieDataset getReceiptPieChartDataset() {
		return receiptPieChartDataset;
	}
	
	public JFreeChart getReceiptPieJFreeChart() {
		return receiptPieJFreeChart;
	}

	public PiePlot getPiePlot() {
		return piePlot;
	}

	public ChartFrame getReceiptPieChartFrame() {
		return receiptPieChartFrame;
	}

	public void drawPieChart(int taxpayerIndex) {
		Database database = Database.getInstance();
		
		createTaxpayerReceiptsPieJFreeChart(taxpayerIndex);
		
		receiptPieJFreeChart = ChartFactory.createPieChart("Receipt Pie Chart", receiptPieChartDataset);
		piePlot = (PiePlot)receiptPieJFreeChart.getPlot();
		PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}: {1}$ ({2})", new DecimalFormat("0.00"), new DecimalFormat("0.00%"));
		piePlot.setLabelGenerator(generator);
		
		receiptPieChartFrame = new ChartFrame(database.getTaxpayerNameAfmValuesPairList(taxpayerIndex), receiptPieJFreeChart);
		receiptPieChartFrame.pack();
		receiptPieChartFrame.setResizable(false);
		receiptPieChartFrame.setLocationRelativeTo(null);
		receiptPieChartFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		receiptPieChartFrame.setVisible(true);
	}
	
	public void createTaxpayerReceiptsPieJFreeChart(int taxpayerIndex){
		Database database = Database.getInstance();
		
		receiptPieChartDataset = new DefaultPieDataset();
		Taxpayer taxpayer = database.getTaxpayerFromArrayList(taxpayerIndex);
		
		receiptPieChartDataset.setValue("Basic", taxpayer.getReceiptsTotalAmount("Basic"));
		receiptPieChartDataset.setValue("Entertainment", taxpayer.getReceiptsTotalAmount("Entertainment"));
		receiptPieChartDataset.setValue("Travel", taxpayer.getReceiptsTotalAmount("Travel"));
		receiptPieChartDataset.setValue("Health", taxpayer.getReceiptsTotalAmount("Health"));
		receiptPieChartDataset.setValue("Other", taxpayer.getReceiptsTotalAmount("Other"));
		
		
	}
	
	public static PieChart getInstance() {
		if(instance == null) {
			instance = new PieChart();
		}
		return instance;
	}

}
