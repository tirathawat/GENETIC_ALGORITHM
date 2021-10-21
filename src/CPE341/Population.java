package CPE341;

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
        Collections.sort(individuals);
        return individuals;
    }

    void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = new ArrayList<>(individuals);
    }

    void generateIndividual() {
        for (int i = 0; i < populationSize; i++)
            individuals.add(new Individual());
    }

    void calculateFitnessRatioEachIndividual() {
        for (Individual individual : individuals)
            individual.calculateFitnessRatio(calculateTotalFitness());
    }

    void calculateFitnessEachIndividual() {
        for (Individual individual : individuals) {
            individual.calculateFitness();
        }
    }

    int calculateTotalFitness() {
        int totalFitness = 0;
        for (Individual individual : individuals)
            totalFitness += individual.getFitness();
        return totalFitness;
    }
}
