import java.util.ArrayList;
import java.util.Collections;

class Population {

    private int populationSize;
    private ArrayList<Individual> individuals;

    Population (int populationSize) {
        this.populationSize = populationSize;
        this.individuals = new ArrayList<>();
    }

    ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = new ArrayList<>(individuals);
    }

    void generateIndividual() {
        for (int i = 0; i < populationSize; i++)
            individuals.add(new Individual(generateChromosome()));
    }

    private ArrayList<Integer> generateChromosome() {
        ArrayList<Integer> chromosome = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            chromosome.add(i + 1);
        }
        Collections.shuffle(chromosome);
        chromosome.add(0, 1);
        chromosome.add(1);
        return chromosome;
    }

    void calculateFitnessRatioEachIndividual() {
        for (Individual individual : individuals)
            individual.calculateFitnessRatio(calculateTotalFitness());
    }

    void calculateFitnessEachIndividual(int[][] distance) {
        for (Individual individual : individuals)
            individual.calculateFitness(distance);
    }

    int calculateTotalFitness() {
        int totalFitness = 0;
        for (Individual individual : individuals)
            totalFitness += individual.getFitness();
        return totalFitness;
    }
}
