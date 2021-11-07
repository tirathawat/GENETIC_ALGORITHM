package CPE341;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.WindowConstants;

import org.jfree.data.xy.XYDataset;

public class CPE341Lab {
    public static void main(String[] args) {
        Boolean stop;
        ArrayList<GenerationData> generationData = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(13, 0.9, 0.01, 1);
        geneticAlgorithm.initialPopulation();
        do {
            geneticAlgorithm.evaluation();
            geneticAlgorithm.printPopulation();
            GenerationData data = new GenerationData(geneticAlgorithm.getGeneration(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getFitness(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getChromosome(),
                    geneticAlgorithm.getAverageFitness(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getDecimal());
            generationData.add(data);
            stop = geneticAlgorithm.isTerminate();
            // stop = !(i < 50);
            if (!stop) {
                geneticAlgorithm.createNewPopulation();
            }
            // i++;
        } while (!stop);
        long stopTime = System.currentTimeMillis();
        for (GenerationData g : generationData) {
            System.out.print("Gen : " + g.getGeneration() + ", ");
            System.out.print("Best fitness : " + g.getFitness() + ", ");
            System.out.print("Best chromosome : " + Arrays.toString(g.getChromosome().toArray()) + "( " + g.getDecimal()
                    + " )" + ", ");
            System.out.print("Average fitness : " + g.getAverage() + "\n");

        }
        System.out.println("Running Time : " + (stopTime - startTime) + " ms");
        showBestGraph(generationData);
        showAvgGraph(generationData);
    }

    private static void showBestGraph(ArrayList<GenerationData> generationData) {
        ScatterPlot plot = new ScatterPlot("Graph genetic Algorithm");
        XYDataset dataset = plot.createBestDataSet(generationData);
        plot.setup(dataset, "Generation and Best Fitness Graph", "Generation", "Best Fitness");
        plot.setSize(800, 400);
        plot.setLocationRelativeTo(null);
        plot.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        plot.setVisible(true);
    }

    private static void showAvgGraph(ArrayList<GenerationData> generationData) {
        ScatterPlot plot = new ScatterPlot("Graph genetic Algorithm");
        XYDataset dataset = plot.createAvgDataSet(generationData);
        plot.setup(dataset, "Generation and Avg Fitness Graph", "Generation", "Avg Fitness");
        plot.setSize(800, 400);
        plot.setLocationRelativeTo(null);
        plot.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        plot.setVisible(true);
    }
}
