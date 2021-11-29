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
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(10, 500, 0.75, 0.01, .75);
        geneticAlgorithm.initialPopulation();

        Boolean stop;
        do {
            geneticAlgorithm.evaluation(preparator.getDistance(), preparator.getTravelDuration(),
                    preparator.getNodeDuration());

            GenerationData newGen = new GenerationData(geneticAlgorithm.getGeneration(),
            geneticAlgorithm.getPopulation().getIndividuals().get(0), geneticAlgorithm.getAverageFitness());

            generationData.add(newGen);

            // stop = geneticAlgorithm.isTerminate();
            stop = false;
            if (!stop) {
                geneticAlgorithm.createNewPopulation(preparator.getDistance(), preparator.getTravelDuration(),
                        preparator.getNodeDuration());
            stop = true;
        }
        } while (!stop);
        long stopTime = System.currentTimeMillis();
        for (GenerationData g : generationData) {
            System.out.print("Gen : " + g.getGeneration() + ", ");
            System.out.print("Best distance: " + g.getIndividual().getFitness() / 1000 + "km, ");
            System.out.print("Path : " + Arrays.toString(g.getIndividual().getPath().toArray()) + ", ");

            System.out.print("Days : " + Arrays.toString(g.getIndividual().getDays().toArray()) + " ("
                    + g.getIndividual().getDays().size() + " days), ");
            System.out.print("Average distance : " + g.getAverageDistance() / 1000 + " km\n");
        }
        System.out.println("Running Time : " + (stopTime - startTime) + " ms");
    }
}
