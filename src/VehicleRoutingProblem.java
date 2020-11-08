import java.util.Collections;

public class VehicleRoutingProblem {

    public static void main (String[] args) {
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
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100, 90, 0, 1);
        geneticAlgorithm.initialPopulation(distance);
        System.out.println("Initial Population");
        for (Individual individual : geneticAlgorithm.getPopulation().getIndividuals())
            System.out.println(individual.getChromosome() + " " + individual.getFitness());
        System.out.println();
        do {
            System.out.println("Generation = " + geneticAlgorithm.getGeneration());
            geneticAlgorithm.evaluation(distance);
            geneticAlgorithm.createNewPopulation(geneticAlgorithm.crossoverAll(geneticAlgorithm.reproduction(), distance));
            Collections.sort(geneticAlgorithm.getPopulation().getIndividuals());
            for (Individual individual : geneticAlgorithm.getPopulation().getIndividuals())
                System.out.println("Path : " + individual.getChromosome() + ", Distance : "
                        + individual.getFitness() + ", Fitness Ratio : "
                        + individual.getFitnessRatio());
            System.out.println("The minimum distance is "
                    + geneticAlgorithm.getPopulation().getIndividuals().get(0).getFitness());
            System.out.println("The average distance is " + geneticAlgorithm.getAverageFitness());
            System.out.println();
        } while (geneticAlgorithm.isTerminate());
    }

}
