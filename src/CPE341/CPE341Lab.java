package CPE341;

import java.util.ArrayList;
import java.util.Arrays;

public class CPE341Lab {
    public static void main(String[] args) {

        ArrayList<GenerationData> generationData = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(15, 0.75, 0.2, 0.75);
        geneticAlgorithm.initialPopulation();
        do {
            geneticAlgorithm.evaluation();
            generationData.add(new GenerationData(geneticAlgorithm.getGeneration(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getFitness(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getChromosome(),
                    geneticAlgorithm.getAverageFitness()));
            geneticAlgorithm.createNewPopulation();
        } while (geneticAlgorithm.isTerminate());
        long stopTime = System.currentTimeMillis();
        for (GenerationData g : generationData) {
            System.out.print("Gen : " + g.getGeneration() + ", ");
            System.out.print("Best fitness: " + g.getFitness() + ", ");
            System.out.print("Best chromosome : " + Arrays.toString(g.getChromosome().toArray()) + ", ");
            System.out.print("Average fitness : " + g.getAverage() + "\n");
        }
        System.out.println("Running Time : " + (stopTime - startTime) + " ms");
    }

}
