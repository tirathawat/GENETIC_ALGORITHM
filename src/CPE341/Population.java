package CPE341;

import java.util.ArrayList;

class Population {

    private int populationSize;
    private ArrayList<Individual> individuals;

    Population(int populationSize) {
        this.populationSize = populationSize;
        this.individuals = new ArrayList<>();
    }

    ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = new ArrayList<>(individuals);
    }

    void generateIndividual(int problemSize) {
        for (int i = 0; i < populationSize; i++)
            individuals.add(new Individual(problemSize));
    }

    void calculateFitnessRatioEachIndividual() {
        double totalFitness = calculateTotalFitness();
        calculateInverseFitnessEachIndividual(totalFitness);
        double inverseTotalFitness = calculateInverseTotalFitness();
        for (Individual individual : individuals)
            individual.calculateFitnessRatio(inverseTotalFitness);
    }

    double calculateInverseTotalFitness() {
        double inverseTotalFitness = 0;
        for (Individual individual : individuals)
            inverseTotalFitness += individual.getInverseFitness();
        return inverseTotalFitness;
    }

    void calculateInverseFitnessEachIndividual(double totalFitness) {
        for (Individual individual : individuals) {
            individual.calculateInverseFitness(totalFitness);
        }
    }

    void calculateFitnessEachIndividual(int[][] distance, int[][] travelDuration, int[] nodeDuration) {
        for (Individual individual : individuals)
            individual.calculateFitness(distance, travelDuration, nodeDuration);
    }

    double calculateTotalFitness() {
        double totalFitness = 0;
        for (Individual individual : individuals)
            totalFitness += individual.getFitness();
        return totalFitness;
    }
}
