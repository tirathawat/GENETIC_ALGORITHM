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
    ScatterPlot(String title, String chartName, ArrayList<GenerationData> generationData) {
        super(title);
        XYDataset dataSet = createDataSet(generationData);
        JFreeChart chart = ChartFactory.createScatterPlot(chartName, "Generation", "Best distance", dataSet);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 228, 196));
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataSet(ArrayList<GenerationData> generationData) {
        XYSeriesCollection dataSet = new XYSeriesCollection();
        XYSeries series = new XYSeries("path", false, false);
        for (GenerationData g : generationData)
            series.add(g.getGeneration(), g.getFitness());
        dataSet.addSeries(series);
        return dataSet;
    }
}
