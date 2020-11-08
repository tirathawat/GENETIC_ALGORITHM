import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Random;

class GeneticAlgorithm {

    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int elitism;
    private Population population;
    private double avarageFitness;
    private int generation;

    GeneticAlgorithm (int populationSize, double crossoverRate, double mutationRate, int elitism) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitism = elitism;
        this.generation = 1;
    }

    void initialPopulation(int[][] distance) {
        population = new Population(this.populationSize);
        population.generateIndividual();
        population.calculateFitnessEachIndividual(distance);
        Collections.sort(population.getIndividuals());
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
        double percent = ((double) modeFrequency / populationSize) * 100;
        return !(percent >= 60);
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

    ArrayList<Individual> reproduction() {
        ArrayList<Individual> selected = new ArrayList<>();
        int reproductionSize = (int)((crossoverRate/100) * populationSize);
        for (int i = 0; i < reproductionSize; i++)
            selected.add(rouletteWheelSelection());
        return selected;
    }

    private Individual rouletteWheelSelection() {
        population.calculateFitnessRatioEachIndividual();
        while (true) {
            double partialSum = 0;
            double roulette;
            roulette = Math.random();
            for (Individual individual : population.getIndividuals()) {
                partialSum += individual.getFitnessRatio();
                if (partialSum >= roulette) return individual;
            }
        }
    }

    private ArrayList<Individual> crossover(Individual parent1, Individual parent2) {
        //System.out.print("Parent1 = " + parent1.getChromosome() + ", Parent2 = " + parent2.getChromosome());
        ArrayList<Integer> offspring1 = new ArrayList<>();
        ArrayList<Integer> offspring2 = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            offspring1.add(null);
            offspring2.add(null);
        }
        offspring1.add(0,1);
        offspring1.add(1);
        offspring2.add(0,1);
        offspring2.add(1);
        Random random = new Random();
        int startPoint = random.nextInt(3) + 1;
        int endPoint = random.nextInt(9 - startPoint) + (startPoint + 1);
        //System.out.print(", startPoint = " + startPoint + ", endPoint = " + endPoint);
        for (int i = startPoint + 1; i <= endPoint; i++) {
            offspring1.set(i, parent2.getChromosome().get(i));
            offspring2.set(i, parent1.getChromosome().get(i));
        }
        for (int i = 1; i <= 9; i++) {
            if (i <= startPoint || i >= endPoint + 1) {
                int index = i;
                while (offspring1.contains(parent1.getChromosome().get(index)))
                    index = offspring1.indexOf(parent1.getChromosome().get(index));
                offspring1.set(i, parent1.getChromosome().get(index));
            }
        }
        for (int i = 1; i <= 9; i++) {
            if (i <= startPoint || i >= endPoint + 1) {
                int index = i;
                while (offspring2.contains(parent2.getChromosome().get(index)))
                    index = offspring2.indexOf(parent2.getChromosome().get(index));
                offspring2.set(i, parent2.getChromosome().get(index));
            }
        }
        ArrayList<Individual> offspringArray = new ArrayList<>();
        offspringArray.add(new Individual(offspring1));
        offspringArray.add(new Individual(offspring2));
        //System.out.println(", Offspring1 = " + offspring1 + ", Offspring2 = " + offspring2);
        return offspringArray;
    }

    ArrayList<Individual> crossoverAll (ArrayList<Individual> selected, int[][] distance) {
        ArrayList<Individual> result = new ArrayList<>();
        for (int i = 0; i < selected.size() - 1; i++) {
            for (int j = i + 1; j < selected.size(); j++) {
                ArrayList<Individual> temp = crossover(selected.get(i), selected.get(j));
                result.addAll(temp);
            }
        }
        for (Individual individual : result) individual.calculateFitness(distance);
        Collections.sort(result);
        return result;
    }

    void createNewPopulation (ArrayList<Individual> result) {
        ArrayList<Individual> newIndividuals = population.getIndividuals();
        ArrayList<Individual> keep = new ArrayList<>();
        Collections.sort(newIndividuals);
        for (int i = 0; i < elitism; i++) keep.add(newIndividuals.get(i));
        newIndividuals = new ArrayList<>(keep);
        for (int i = 0; newIndividuals.size() < populationSize; i++)
            newIndividuals.add(result.get(i));
        generation++;
        population.setIndividuals(newIndividuals);
        population.calculateFitnessRatioEachIndividual();
    }
}
