package CPE341;

import java.util.ArrayList;

class GenerationData {
    private ArrayList<Integer> chromosome;
    private int fitness;
    private double averageFitness;
    private int generation;

    GenerationData(int generation, int fitness, ArrayList<Integer> chromosome, double averageFitness) {
        this.generation = generation;
        this.fitness = fitness;
        this.averageFitness = averageFitness;
        this.chromosome = new ArrayList<>(chromosome);
    }

    ArrayList<Integer> getChromosome() {
        return chromosome;
    }

    int getFitness() {
        return fitness;
    }

    double getAverage() {
        return averageFitness;
    }

    int getGeneration() {
        return generation;
    }

}
