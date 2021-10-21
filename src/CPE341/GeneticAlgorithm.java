package CPE341;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class GeneticAlgorithm {

    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private double terminateRate;
    private Population population;
    private int generation;

    GeneticAlgorithm (int populationSize, double crossoverRate, double mutationRate, double terminateRate) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.terminateRate = terminateRate;
        this.generation = 1;
    }

    void initialPopulation() {
        population = new Population(this.populationSize);
        population.generateIndividual();
    }

    Population getPopulation() {
        return population;
    }

    int getGeneration() {
        return generation;
    }

    double getAverageFitness() {
        return (double) population.calculateTotalFitness() / populationSize;
    }

    void evaluation() {
        population.calculateFitnessEachIndividual();
        population.calculateFitnessRatioEachIndividual();
        Collections.sort(population.getIndividuals());
    }

    boolean isTerminate() {
        int modeFrequency = presortMode(population.getIndividuals());
        double percent = ((double) modeFrequency / populationSize);
        return !(percent >= terminateRate);
    }

    private int presortMode(ArrayList<Individual> individuals) {
        Collections.sort(individuals);
        int i = 0;
        int modeFrequency = 0;
        int n = individuals.size();
        while (i <= n - 1) {
            int runLength = 1;
            ArrayList<Integer> runValue = individuals.get(i).getChromosome();
            while (i + runLength <= n - 1 && individuals.get(i + runLength).getChromosome().equals(runValue))
                runLength++;
            if (runLength > modeFrequency) modeFrequency = runLength;
            i += runLength;
        }
        return modeFrequency;
    }

    private Individual rouletteWheelSelection() {
        double partialSum = 0;
        double roulette;
        roulette = Math.random();
        for (Individual individual : population.getIndividuals()) {
            partialSum += individual.getFitnessRatio();
            if (partialSum >= roulette) return individual;
        }
        return null;
    }

    private Individual crossover(Individual parent1, Individual parent2) {
        ArrayList<Integer> child = new ArrayList<>();
        for (int i = 0; i <= 5; i++) child.add(null);
        Random random = new Random();
        int dividedPoint = random.nextInt(5) + 1;
        for (int i = 0; i <= 5; i++) {
            if (i < dividedPoint)
                child.set(i, parent1.getChromosome().get(i));
            else 
                child.set(i, parent2.getChromosome().get(i));
        }

        System.out.print("parent1: " + parent1.getChromosome().toString());
        System.out.println();
        System.out.print("parent2: " + parent2.getChromosome().toString());
        System.out.println();
        System.out.print("dividedPoint: " + dividedPoint);
        System.out.println();
        System.out.print("child: " + child.toString());
        System.out.println();

        return new Individual(child);
    }

    private void mutation() {
        for (int i = 1; i < populationSize && mutationRate != 0; i++)
            if (Math.random() < mutationRate) {
                population.getIndividuals().get(i).generateChromosome();
                population.getIndividuals().get(i).calculateFitness();
            }
    }

    void createNewPopulation() {
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        for (int i = 0; i < crossoverRate * populationSize; i++) {
            Individual individual = crossover(rouletteWheelSelection(), rouletteWheelSelection());
            individual.calculateFitness();
            newIndividuals.add(individual);
        }
        Collections.sort(population.getIndividuals());
        for (int i = 0; newIndividuals.size() < populationSize; i++)
            newIndividuals.add(population.getIndividuals().get(i));
        population.setIndividuals(newIndividuals);
        Collections.sort(population.getIndividuals());
        mutation();
        population.calculateFitnessRatioEachIndividual();
        generation++;
    }
}
