import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class Chart extends JPanel {
    public Chart() {
        this.setLayout(new GridLayout());
    }
    public void reset() {
        this.removeAll();
    }
    public void addData(CategoryDataset dataset ,String name,boolean vertical) {
        JFreeChart chart = createChart(dataset,name,vertical);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        if (vertical) {
            chartPanel.setPreferredSize(new Dimension(dataset.getColumnCount()*50,600));
        } else {
            chartPanel.setPreferredSize(new Dimension(600,dataset.getColumnCount()*50));
        }

        this.add(chartPanel);
    }

    private JFreeChart createChart(CategoryDataset dataset,String name,boolean vertical) {
        if (vertical) {
            JFreeChart barChart = ChartFactory.createBarChart(
                    name,
                    "",
                    "",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false);

            return barChart;
        } else {
            JFreeChart barChart = ChartFactory.createBarChart(
                    name,
                    "",
                    "",
                    dataset,
                    PlotOrientation.HORIZONTAL,
                    false, true, false);

            return barChart;
        }

    }

    public static void main(String[] args) {
        new Chart();
        System.out.println("run");
    }
}