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
import java.util.ArrayList;

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
    }

    public XYDataset createDataSet(String seriesName, ArrayList<Double> x, ArrayList<Double> y) {
        XYSeriesCollection dataSet = new XYSeriesCollection();
        XYSeries series = new XYSeries(seriesName, false, false);
        for (int i = 0; i < x.size(); i++) {
            series.add(x.get(i), y.get(i));
        }
        dataSet.addSeries(series);
        return dataSet;
    }

}
