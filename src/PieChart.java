import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class PieChart extends JPanel {
    public PieChart() {
        this.setLayout(new GridLayout());
    }
    public void reset() {
        this.removeAll();
    }
    public void addData(DefaultPieDataset dataset ,String name) {
        JFreeChart chart = createChart(dataset,name);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        this.add(chartPanel);
    }


    public DefaultPieDataset createDataset() {

        var dataset = new DefaultPieDataset();
        dataset.setValue("Apache", 52);
        dataset.setValue("Nginx", 31);
        dataset.setValue("IIS", 12);
        dataset.setValue("LiteSpeed", 2);
        dataset.setValue("Google server", 1);
        dataset.setValue("Others", 2);

        return dataset;
    }


    private JFreeChart createChart(DefaultPieDataset dataset,String name) {

        JFreeChart pieChart = ChartFactory.createPieChart(
                name,
                dataset,
                false, true, false);

        return pieChart;
    }

    public static void main(String[] args) {
        new Chart();
        System.out.println("run");
    }
}