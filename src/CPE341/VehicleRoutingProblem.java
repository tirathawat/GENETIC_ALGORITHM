package CPE341;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class VehicleRoutingProblem {

    public static void main(String[] args) {
        Preparator preparator = new Preparator();
        ArrayList<String> distance = preparator.readFile("distance.txt");
        ArrayList<String> travelDuration = preparator.readFile("travel_duration.txt");
        ArrayList<String> nodeDuration = preparator.readFile("node_duration.txt");
        ArrayList<String> nodeName = preparator.readFile("node_name.txt");
        ArrayList<String> geolocation = preparator.readFile("node_geolocation.txt");
        preparator.prepareData(distance, travelDuration, nodeDuration, nodeName, "	");

        ArrayList<GenerationData> generationData = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(10, 400, 0.7, 0.4, 3000);
        geneticAlgorithm.initialPopulation();

        Boolean stop;
        do {
            geneticAlgorithm.evaluation(preparator.getDistance(),
                    preparator.getTravelDuration(),
                    preparator.getNodeDuration());
            GenerationData g = new GenerationData(geneticAlgorithm.getGeneration(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0),
                    geneticAlgorithm.getAverageFitness());
            generationData.add(g);

            System.out.print("Gen : " + g.getGeneration() + ", ");
            System.out.print("Best distance: " + g.getIndividual().getFitness() / 1000 +
                    "km, ");
            System.out.print("Path : " +
                    Arrays.toString(g.getIndividual().getPath().toArray()) + ", ");

            System.out.print("Days : " +
                    Arrays.toString(g.getIndividual().getDays().toArray()) + " ("
                    + g.getIndividual().getDays().size() + " days), ");
            System.out.print("Average distance : " + g.getAverageDistance() / 1000 +
                    "km\n");

            stop = geneticAlgorithm.isTerminate();
            if (!stop) {
                geneticAlgorithm.createNewPopulation(preparator.getDistance(),
                        preparator.getTravelDuration(),
                        preparator.getNodeDuration());
            }
        } while (!stop);
        long stopTime = System.currentTimeMillis();
        // for (GenerationData g : generationData) {
        // System.out.print("Gen : " + g.getGeneration() + ", ");
        // System.out.print("Best distance: " + g.getIndividual().getFitness() / 1000 +
        // "km, ");
        // System.out.print("Path : " +
        // Arrays.toString(g.getIndividual().getPath().toArray()) + ", ");

        // System.out.print("Days : " +
        // Arrays.toString(g.getIndividual().getDays().toArray()) + " ("
        // + g.getIndividual().getDays().size() + " days), ");
        // System.out.print("Average distance : " + g.getAverageDistance() / 1000 +
        // "km\n");
        // }
        System.out.println("Running Time : " + (stopTime - startTime) + " ms");

        // RoutePlotter routePlotter = new RoutePlotter();
        // try {
        // routePlotter.plot(generationData.get(generationData.size() -
        // 1).getIndividual().getPath(), geolocation,
        // generationData.get(generationData.size() - 1).getIndividual().getDays());
        // } catch (IOException e) {
        // System.out.println(e.getMessage());
        // }
    }
}
