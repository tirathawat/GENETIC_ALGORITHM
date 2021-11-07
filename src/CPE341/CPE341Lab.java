import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.WindowConstants;

public class CPE341Lab {
    public static void main(String[] args) {
        Boolean stop;
        ArrayList<GenerationData> generationData = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(8, 0.9, 0.05, 1);
        geneticAlgorithm.initialPopulation();
        int i = 0;
        do {
            geneticAlgorithm.evaluation();
            geneticAlgorithm.printPopulation();
            GenerationData data = new GenerationData(geneticAlgorithm.getGeneration(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getFitness(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getChromosome(),
                    geneticAlgorithm.getAverageFitness(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getDecimal());
            generationData.add(data);
//            stop = geneticAlgorithm.isTerminate();
            stop = !(i < 50);
            if (!stop) {
                geneticAlgorithm.createNewPopulation();
            }
            i++;
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
    }
}
