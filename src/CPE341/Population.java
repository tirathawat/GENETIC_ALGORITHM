import java.util.ArrayList;
import java.util.Collections;

class Population {

    private int populationSize;
    private ArrayList<Individual> individuals;

    Population(int populationSize) {
        this.populationSize = populationSize;
        this.individuals = new ArrayList<>();
    }

    ArrayList<Individual> getIndividuals() {
        Collections.sort(individuals);
        return individuals;
    }

    void setIndividual(int index, Individual individual) {
        this.individuals.set(index, individual);
    }

    void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = new ArrayList<>(individuals);
    }

    void generateIndividual() {
        for (int i = 0; i < populationSize; i++)
            individuals.add(new Individual());
    }

    void calculateFitnessRatioEachIndividual() {
        int totalFitness = calculateTotalFitness();
        calculateInverseFitnessEachIndividual(totalFitness);
        int inverseTotalFitness = calculateInverseTotalFitness();
        for (Individual individual : individuals)
            individual.calculateFitnessRatio(inverseTotalFitness, totalFitness);
    }

    void calculateInverseFitnessEachIndividual(int totalFitness) {
        for (Individual individual : individuals) {
            individual.calculateInverseFitness(totalFitness);
        }
    }

    void calculateFitnessEachIndividual() {
        for (Individual individual : individuals) {
            individual.calculateFitness();
        }
    }

    int calculateInverseTotalFitness() {
        int inverseTotalFitness = 0;
        for (Individual individual : individuals)
            inverseTotalFitness += individual.getInverseFitness();
        return inverseTotalFitness;
    }

    int calculateTotalFitness() {
        int totalFitness = 0;
        for (Individual individual : individuals)
            totalFitness += individual.getFitness();
        return totalFitness;
    }
}
