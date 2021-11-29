package CPE341;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

class ScatterPlot extends JFrame {

    ScatterPlot(String title) {
        super(title);
    }

    public void plot(XYDataset dataSet, String chartName, String xName, String yName) {
        JFreeChart chart = ChartFactory.createScatterPlot(chartName, xName, yName, dataSet);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public XYDataset createDataSet(String seriesName, HashMap<Double, Double> data) {
        XYSeriesCollection dataSet = new XYSeriesCollection();
        XYSeries series = new XYSeries(seriesName, false, false);
        for (Double k : data.keySet()) {
            series.add(k, data.get(k));
        }
        dataSet.addSeries(series);
        return dataSet;
    }

}
