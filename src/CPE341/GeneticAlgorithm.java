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

    GeneticAlgorithm(int populationSize, double crossoverRate, double mutationRate, double terminateRate) {
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

    void evaluation(int[][] distance) {
        population.calculateFitnessEachIndividual(distance);
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
            if (runLength > modeFrequency)
                modeFrequency = runLength;
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
            if (partialSum >= roulette)
                return individual;
        }
        return null;
    }

    private Individual crossover(Individual parent1, Individual parent2) {
        ArrayList<Integer> offspring = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            offspring.add(null);
        offspring.add(0, 1);
        offspring.add(1);
        Random random = new Random();
        int startPoint = random.nextInt(3) + 1;
        int endPoint = random.nextInt(9 - startPoint) + (startPoint + 1);
        for (int i = startPoint + 1; i <= endPoint; i++)
            offspring.set(i, parent2.getChromosome().get(i));
        for (int i = 1; i <= 9; i++) {
            if (i <= startPoint || i >= endPoint + 1) {
                int index = i;
                while (offspring.contains(parent1.getChromosome().get(index)))
                    index = offspring.indexOf(parent1.getChromosome().get(index));
                offspring.set(i, parent1.getChromosome().get(index));
            }
        }
        return new Individual(offspring);
    }

    private void mutation(int[][] distance) {
        for (int i = 1; i < populationSize && mutationRate != 0; i++)
            if (Math.random() < mutationRate) {
                population.getIndividuals().get(i).generateChromosome();
                population.getIndividuals().get(i).calculateFitness(distance);
            }
    }

    void createNewPopulation(int[][] distance) {
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        for (int i = 0; i < crossoverRate * populationSize; i++) {
            Individual individual = crossover(rouletteWheelSelection(), rouletteWheelSelection());
            individual.calculateFitness(distance);
            newIndividuals.add(individual);
        }
        Collections.sort(population.getIndividuals());
        for (int i = 0; newIndividuals.size() < populationSize; i++)
            newIndividuals.add(population.getIndividuals().get(i));
        population.setIndividuals(newIndividuals);
        Collections.sort(population.getIndividuals());
        mutation(distance);
        population.calculateFitnessRatioEachIndividual();
        generation++;
    }
}
