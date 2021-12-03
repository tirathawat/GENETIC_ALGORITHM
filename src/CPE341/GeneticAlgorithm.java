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
    private int problemSize;

    GeneticAlgorithm(int problemSize, int populationSize, double crossoverRate, double mutationRate,
            double terminateRate) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.terminateRate = terminateRate;
        this.generation = 1;
        this.problemSize = problemSize;
    }

    void initialPopulation() {
        population = new Population(this.populationSize);
        population.generateIndividual(problemSize);
    }

    Population getPopulation() {
        return population;
    }

    int getGeneration() {
        return generation;
    }

    double getAverageFitness() {
        return population.calculateTotalFitness() / populationSize;
    }

    void evaluation(int[][] distance, int[][] travelDuration, int[] nodeDuration) {
        population.calculateFitnessEachIndividual(distance, travelDuration, nodeDuration);
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

    // public Individual crossover(Individual parent1, Individual parent2) {
    // Random firstRNum = new Random();
    // Random secondRNum = new Random();

    // int randomNo_Boundary = (parent1.getChromosome().size()) - 1;
    // ArrayList<Integer> offspring1 = parent1.getChromosome();
    // ArrayList<Integer> offspring2 = parent2.getChromosome();

    // int startedPoint = firstRNum.nextInt(randomNo_Boundary);
    // int endPoint = secondRNum.nextInt(randomNo_Boundary);

    // while (startedPoint == endPoint) {
    // endPoint = secondRNum.nextInt(randomNo_Boundary);
    // }

    // if (startedPoint > endPoint) {
    // int temp = startedPoint;
    // startedPoint = endPoint;
    // endPoint = temp;
    // }

    // for (int i = startedPoint + 1; i <= endPoint; i++)
    // offspring1.set(i, parent2.getChromosome().get(i));
    // for (int i = 0; i < problemSize; i++) {
    // if (i <= startedPoint || i >= endPoint + 1) {
    // int index = i;
    // while (offspring1.contains(parent1.getChromosome().get(index))) {
    // System.out.println("sdsd");
    // index = offspring1.indexOf(parent2.getChromosome().get(index));
    // }
    // offspring1.set(i, parent1.getChromosome().get(index));
    // }
    // }

    // return new Individual(offspring1);
    // }

    // private Individual crossover(Individual parent1, Individual parent2) {
    // ArrayList<Integer> offspring = new ArrayList<>();
    // for (int i = 1; i < problemSize + 1; i++)
    // offspring.add(null);
    // Random random = new Random();
    // int startPoint = random.nextInt(problemSize / 2) + 1;
    // int endPoint = random.nextInt(problemSize - 1 - startPoint) + (startPoint +
    // 1);

    // for (int i = startPoint + 1; i <= endPoint; i++)
    // offspring.set(i, parent2.getChromosome().get(i));

    // for (int i = 0; i < problemSize; i++) {
    // if (i <= startPoint || i >= endPoint + 1) {
    // int index = i;
    // while (offspring.contains(parent1.getChromosome().get(index))) {
    // index = offspring.indexOf(parent1.getChromosome().get(index));
    // System.out.println(index);
    // }

    // offspring.set(i, parent1.getChromosome().get(index));
    // }
    // }
    // return new Individual(offspring);
    // }

    public ArrayList<Integer> pmx(ArrayList<Integer> x, ArrayList<Integer> y, int index1, int index2) {

        boolean visited[] = new boolean[x.size() + 2]; // all false, are the node visited?

        ArrayList<Integer> z = new ArrayList<Integer>();// same dimensions as x

        for (int i = 0; i < x.size(); i++) {
            z.add(0);
        }

        for (int i = index1; i <= index2; i++) {
            z.set(i, x.get(i));
            visited[z.get(i)] = true;
        }
        int k = index1;
        // Traverse parent2
        for (int i = index1; i <= index2; i++) { // para cada elemento del segmente
            if (!visited[y.get(i)]) {
                k = i;
                int elementToBeCopied = y.get(i); // copiando el elemento desde la madre
                do {
                    int V = x.get(k);
                    // search in the mother ofr the index where the V is.
                    for (int j = 0; j < y.size(); j++) {
                        if (y.get(j) == V) {
                            k = j;
                        }
                    }
                } while (z.get(k) != 0);
                z.set(k, elementToBeCopied);
                visited[z.get(k)] = true;
            }
        }

        // copy the reminder elements from y

        for (int i = 0; i < z.size(); i++) {
            if (z.get(i) == 0)
                z.set(i, y.get(i));
        }
        return z;
    }

    private int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private Individual crossover(Individual parent1, Individual parent2) {
        Random firstRNum = new Random();
        Random secondRNum = new Random();

        int randomNo_Boundary = (parent1.getChromosome().size()) - 1;

        int startPoint = firstRNum.nextInt(randomNo_Boundary);
        int endPoint = secondRNum.nextInt(randomNo_Boundary);

        while (startPoint == endPoint) {
            endPoint = secondRNum.nextInt(randomNo_Boundary);
        }

        if (startPoint > endPoint) {
            int temp = startPoint;
            startPoint = endPoint;
            endPoint = temp;
        }

        if (randomNumber(0, 1) > 0.5) {
            return new Individual(pmx(parent1.getChromosome(), parent2.getChromosome(),
                    startPoint, endPoint));
        } else {
            return new Individual(pmx(parent2.getChromosome(), parent1.getChromosome(),
                    startPoint, endPoint));
        }
    }

    private void mutation(int[][] distance, int[][] travelDuration, int[] nodeDuration) {
        for (int i = 1; i < populationSize && mutationRate != 0; i++)
            if (Math.random() < mutationRate) {
                population.getIndividuals().get(i).generateChromosome(problemSize);
                population.getIndividuals().get(i).calculateFitness(distance, travelDuration, nodeDuration);
            }
    }

    void createNewPopulation(int[][] distance, int[][] travelDuration, int[] nodeDuration) {
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        for (int i = 0; i < crossoverRate * populationSize; i++) {
            Individual individual = crossover(rouletteWheelSelection(),
                    population.getIndividuals().get(randomNumber(0, population.getIndividuals().size() - 1)));
            individual.calculateFitness(distance, travelDuration, nodeDuration);
            newIndividuals.add(individual);
        }
        Collections.sort(population.getIndividuals());

        for (int i = 0; newIndividuals.size() < populationSize; i++)
            newIndividuals.add(population.getIndividuals().get(i));
        population.setIndividuals(newIndividuals);
        Collections.sort(population.getIndividuals());

        mutation(distance, travelDuration, nodeDuration);

        population.calculateFitnessRatioEachIndividual();
        generation++;
    }
}
