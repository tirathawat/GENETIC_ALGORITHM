package CPE341;

import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;

class Individual implements Comparable<Individual> {

    private ArrayList<Integer> chromosome;
    private int fitness;
    private double fitnessRatio;

    Individual() {
        generateChromosome();
    }

    Individual(ArrayList<Integer> chromosome) {
        this.chromosome = chromosome;
    }

    ArrayList<Integer> getChromosome() {
        return chromosome;
    }

    int getFitness() {
        return fitness;
    }

    double getFitnessRatio() {
        return fitnessRatio;
    }

    void calculateFitnessRatio(int totalFitness) {
        this.fitnessRatio = (double) fitness / totalFitness;
    }

    void calculateFitness() {
        int x = 0;
        fitness = 0;
        for (int i = chromosome.size() - 1; i >= 0; i--) {
            x += chromosome.get(i) * Math.pow(2, i);
        }
        fitness = (int) (Math.pow(x, 3) - (60 * Math.pow(x, 2)) + (900 * x) + 150);
    }

    void generateChromosome() {
        int count;
        ArrayList<Integer> chromosome = new ArrayList<>();
        do {
            count = 0;
            chromosome.clear();
            for (int i = 1; i <= 6; i++) {
                Random random = new Random();
                Integer binary = random.nextInt(2);
                count += binary;
                chromosome.add(binary);
            }
        } while (count == 0);
        this.chromosome = new ArrayList<>(chromosome);
    }

    @Override
    public int compareTo(Individual o) {
        return Integer.compare(this.getFitness(), o.getFitness());
    }
}
