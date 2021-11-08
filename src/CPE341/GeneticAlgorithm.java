package CPE341;

import java.util.ArrayList;
import java.util.Collections;

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

    void evaluation() {
        population.calculateFitnessEachIndividual();
        population.calculateFitnessRatioEachIndividual();
        Collections.sort(population.getIndividuals());
    }

    boolean isTerminate() {
        int modeFrequency = presortMode(population.getIndividuals());
        double percent = ((double) modeFrequency / populationSize);
        return percent >= terminateRate;
    }

    private int presortMode(ArrayList<Individual> individuals) {
        Collections.sort(individuals);
        int i = 0;
        int modeFrequency = 0;
        int n = individuals.size();
        while (i <= n - 1) {
            int runLength = 1;
            int runValue = individuals.get(i).getDecimal();
            while (i + runLength <= n - 1 && individuals.get(i + runLength).getDecimal() == runValue)
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
            if (partialSum >= roulette) {
                return individual;
            }
        }
        return null;
    }

    private Individual crossover(Individual parent1, Individual parent2) {
        ArrayList<Integer> child = new ArrayList<>();

        for (int i = 0; i <= 5; i++) {
            child.add(null);
        }
        int min = 1;
        int max = 5;
        int dividedPoint = min + (int) (Math.random() * ((max - min) + 1));
        System.out.println("divided point: " + dividedPoint);
        for (int i = 0; i < 6; i++) {
            if (i < dividedPoint) {
                child.set(i, parent1.getChromosome().get(i));
            } else {
                child.set(i, parent2.getChromosome().get(i));
            }
        }
        return new Individual(child);
    }

    private void mutation() {
        for (int i = 0; i < populationSize && mutationRate != 0; i++) {
            if (Math.random() < mutationRate) {
                System.out.println("+++++mutation+++++");
                System.out.println("    individuals mutation");
                System.out.print("  ");
                population.getIndividuals().get(i).printChromosome();
                Individual individual = new Individual();
                individual.calculateFitness();
                population.setIndividual(i, individual);
                System.out.println("    To: ");
                System.out.print("  ");
                population.getIndividuals().get(i).printChromosome();
                System.out.println("++++++++++");
            }
        }

    }

    void createNewPopulation() {
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        Collections.sort(population.getIndividuals());

        System.out.println("+++++elitism+++++");

        for (int i = 0; i < populationSize - (int) (crossoverRate * populationSize); i++) {
            Individual individual = population.getIndividuals().get(i);
            individual.printChromosome();
            newIndividuals.add(individual);
        }

        System.out.println("++++++++++");

        while (newIndividuals.size() < populationSize) {
            Individual child;
            System.out.println("+++++cross over+++++");
            Individual parent1 = rouletteWheelSelection();
            Individual parent2 = rouletteWheelSelection();
            System.out.print("parent1: ");
            parent1.printChromosome();
            System.out.print("parent2: ");
            parent2.printChromosome();
            do {
                child = crossover(parent1, parent2);
            } while (child.getDecimal() < 1 || child.getDecimal() > 64);
            child.calculateFitness();
            System.out.print("child: ");
            child.printChromosome();
            newIndividuals.add(child);
            System.out.println("++++++++++");
        }

        population.setIndividuals(newIndividuals);
        Collections.sort(population.getIndividuals());
        mutation();
        population.calculateFitnessRatioEachIndividual();
        generation++;
    }

    void printPopulation() {
        System.out.println("++++++++ Generation " + generation + " ++++++++");
        for (Individual individual : population.getIndividuals()) {
            individual.printChromosome();
        }
        System.out.println("++++++++++++++++");
    }
}
