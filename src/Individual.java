import java.util.ArrayList;
import java.util.Collections;

class Individual implements Comparable<Individual> {

    private ArrayList<Integer> chromosome;
    private int fitness;
    private double fitnessRatio;

    Individual () {
        generateChromosome();
    }

    Individual (ArrayList<Integer> chromosome) {
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

    void calculateFitness(int[][] distance) {
        fitness = 0;
        for (int i = 0; i < chromosome.size() - 1; i++)
            fitness += distance[chromosome.get(i) - 1][chromosome.get(i + 1) - 1];
    }

    void generateChromosome() {
        ArrayList<Integer> chromosome = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            chromosome.add(i + 1);
        }
        Collections.shuffle(chromosome);
        chromosome.add(0, 1);
        chromosome.add(1);
        this.chromosome = new ArrayList<>(chromosome);
    }

    @Override
    public int compareTo(Individual o) {
        return Integer.compare(this.getFitness(), o.getFitness());
    }
}
