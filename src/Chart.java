import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class Chart extends JFrame {
    JPanel p = new JPanel();
    JScrollPane jp = new JScrollPane(p);
    public Chart() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pie chart");
        p.setLayout(new GridLayout());
        add(jp);
        /*
        add(createDataset(),"Testing");
        reset();
        add(createDataset(),"Testing");
         */
        setVisible(true);
    }
    public void reset() {
        p.removeAll();
    }
    public void addData(CategoryDataset dataset ,String name,boolean vertical) {
        JFreeChart chart = createChart(dataset,name,vertical);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        p.add(chartPanel);
        pack();

    }

    private CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(46, "Gold medals", "USA");
        dataset.setValue(38, "Gold medals", "China");
        dataset.setValue(38, "medals", "China");
        dataset.setValue(29, "Gold medals", "UK");
        dataset.setValue(22, "Gold medals", "Russia");
        dataset.setValue(13, "Gold medals", "South Korea");
        dataset.setValue(11, "Gold medals", "Germany");

        return dataset;
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
class PieChart extends JFrame {
    JPanel p = new JPanel();
    JScrollPane jp = new JScrollPane(p);
    public PieChart() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Pie chart");
        p.setLayout(new GridLayout());
        add(jp);
        /*
        add(createDataset(),"Testing");
        reset();
        add(createDataset(),"Testing");
         */
        setVisible(true);
    }
    public void reset() {
        p.removeAll();
    }
    public void addData(CategoryDataset dataset ,String name,boolean vertical) {
        JFreeChart chart = createChart(dataset,name,vertical);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        p.add(chartPanel);
        pack();

    }

    private CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(46, "Gold medals", "USA");
        dataset.setValue(38, "Gold medals", "China");
        dataset.setValue(38, "medals", "China");
        dataset.setValue(29, "Gold medals", "UK");
        dataset.setValue(22, "Gold medals", "Russia");
        dataset.setValue(13, "Gold medals", "South Korea");
        dataset.setValue(11, "Gold medals", "Germany");

        return dataset;
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