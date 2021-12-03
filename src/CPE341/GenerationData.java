package CPE341;

import java.util.ArrayList;

class GenerationData {

    private Individual individual;
    private ArrayList<Individual> individuals;
    private double averageDistance;
    private double averageTime;
    private int generation;

    GenerationData(int generation, Individual individual, double averageDistance, double averageTime, ArrayList<Individual> individuals) {
        this.generation = generation;
        this.averageDistance = averageDistance;
        this.averageTime = averageTime;
        this.individual = individual;
        this.individuals = individuals;
    }

    Individual getIndividual() {
        return individual;
    }

    ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    double getAverageDistance() {
        return averageDistance;
    }

    double getAverageTime() {
        return averageTime;
    }

    int getGeneration() {
        return generation;
    }
}
