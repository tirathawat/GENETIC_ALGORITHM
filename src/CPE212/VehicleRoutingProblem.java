package CPE212;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class VehicleRoutingProblem {
    public static void main (String[] args) {
        ArrayList<GenerationData> generationData = new ArrayList<>();
        int[][] distance = {
                {0,10,28,23,65,16,16,42,16,21},
                {10,0,76,72,150,52,52,104,42,62},
                {28,76,0,106,74,48,83,28,88,58},
                {23,72,106,0,180,82,82,134,18,92},
                {65,150,74,180,0,122,157,56,162,132},
                {16,52,48,82,122,0,59,76,64,34},
                {16,52,83,82,157,59,0,111,64,69},
                {42,104,28,134,56,76,111,0,116,106},
                {16,42,88,18,162,64,64,116,0,74},
                {21,62,58,92,132,34,69,106,74,0},
        };
        long startTime = System.currentTimeMillis();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(
                150,
                0.8,
                0.03,
                0.3);
        geneticAlgorithm.initialPopulation();
        do {
            geneticAlgorithm.evaluation(distance);
            generationData.add(new GenerationData(
                    geneticAlgorithm.getGeneration(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getFitness(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getChromosome(),
                    geneticAlgorithm.getAverageFitness()));
            geneticAlgorithm.createNewPopulation(distance);
        } while (geneticAlgorithm.isTerminate());
        long stopTime = System.currentTimeMillis();
        for (GenerationData g : generationData) {
            System.out.print("Gen : " + g.getGeneration() + ", ");
            System.out.print("Best distance: " + g.getBestDistance() + ", ");
            System.out.print("Path : " + Arrays.toString(g.getPath().toArray()) + ", ");
            System.out.print("Average distance : " + g.getAverageDistance() + "\n");
        }
        System.out.println("Running Time : " + (stopTime - startTime) + " ms");
        showGraph(generationData);
    }


    private static void showGraph(ArrayList<GenerationData> generationData) {
        SwingUtilities.invokeLater(() -> {
            ScatterPlot scatterPlot = new ScatterPlot(
                    "Graph genetic Algorithm",
                    "Generation and Best distance graph",
                    generationData);
            scatterPlot.setSize(800, 400);
            scatterPlot.setLocationRelativeTo(null);
            scatterPlot.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            scatterPlot.setVisible(true);
        });
    }
}
