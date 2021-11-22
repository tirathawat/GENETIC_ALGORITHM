package CPE341;

import java.util.ArrayList;
import java.util.Arrays;

public class VehicleRoutingProblem {

    public static void main(String[] args) {
        Preparator preparator = new Preparator();
        ArrayList<String> distance = preparator.readFile("distance.txt");
        ArrayList<String> travelDuration = preparator.readFile("travel_duration.txt");
        ArrayList<String> nodeDuration = preparator.readFile("node_duration.txt");
        ArrayList<String> nodeName = preparator.readFile("node_name.txt");
        preparator.prepareData(distance, travelDuration, nodeDuration, nodeName, "	");

        ArrayList<GenerationData> generationData = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(150, 0.8, 0.03, 0.3);
        geneticAlgorithm.initialPopulation();
        do {
            geneticAlgorithm.evaluation(preparator.getDistance());
            generationData.add(new GenerationData(geneticAlgorithm.getGeneration(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getFitness(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0).getChromosome(),
                    geneticAlgorithm.getAverageFitness()));
            geneticAlgorithm.createNewPopulation(preparator.getDistance());
        } while (geneticAlgorithm.isTerminate());
        long stopTime = System.currentTimeMillis();
        for (GenerationData g : generationData) {
            System.out.print("Gen : " + g.getGeneration() + ", ");
            System.out.print("Best distance: " + g.getBestDistance() + ", ");
            System.out.print("Path : " + Arrays.toString(g.getPath().toArray()) + ", ");
            System.out.print("Average distance : " + g.getAverageDistance() + "\n");
        }
        System.out.println("Running Time : " + (stopTime - startTime) + " ms");
    }
}
