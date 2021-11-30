package CPE341;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import org.jfree.data.xy.XYDataset;

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

    double getAverageTimeFitness() {
        return population.calculateTotalTimeFitness() / populationSize;
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

    private Individual crossover(Individual parent1, Individual parent2) {
        ArrayList<Integer> offspring = new ArrayList<>();
        for (int i = 1; i < problemSize + 1; i++)
            offspring.add(null);
        Random random = new Random();
        int startPoint = random.nextInt(problemSize / 2) + 1;
        int endPoint = random.nextInt(problemSize - 1 - startPoint) + (startPoint + 1);
        for (int i = startPoint + 1; i <= endPoint; i++)
            offspring.set(i, parent2.getChromosome().get(i));
        for (int i = 0; i < problemSize; i++) {
            if (i <= startPoint || i >= endPoint + 1) {
                int index = i;
                while (offspring.contains(parent1.getChromosome().get(index)))
                    index = offspring.indexOf(parent1.getChromosome().get(index));
                offspring.set(i, parent1.getChromosome().get(index));
            }
        }
        return new Individual(offspring);
    }

    // private void mutation(int[][] distance, int[][] travelDuration, int[]
    // nodeDuration,
    // ArrayList<Individual> individuals) {
    // for (int i = 1; i < populationSize && mutationRate != 0; i++)
    // if (Math.random() < mutationRate) {
    // population.getIndividuals().get(i).generateChromosome(problemSize);
    // population.getIndividuals().get(i).calculateFitness(distance, travelDuration,
    // nodeDuration);
    // }
    // }

    private void specificMutation(int[][] distance, int[][] travelDuration, int[] nodeDuration, Individual individual) {
        individual.generateChromosome(problemSize);
        individual.calculateFitness(distance, travelDuration, nodeDuration);
    }

    Individual tournamentSelection(int index) {
        int pivot = 0;
        int i = 0;
        while (true) {
            for (Individual par : population.getIndividuals()) {
                if (par.getRank() == pivot) {
                    if (i == index)
                        return par;
                    else
                        i++;
                }
            }
            pivot++;
        }
    }

    void createNewPopulation(int[][] distance, int[][] travelDuration, int[] nodeDuration) {
        int index = (int) (Math.random() * (population.getIndividuals().size() - 1 - 0 + 1) + 0);
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        int tournamentIndex = 0;
        for (int i = 0; i < crossoverRate * populationSize; i++) {
            Individual individual = crossover(tournamentSelection(tournamentIndex),
                    population.getIndividuals().get(index)); // Tournament selection to select for two point crossover.
            tournamentIndex++; // Increase Tournamet selection index.
            individual.calculateFitness(distance, travelDuration, nodeDuration);
            newIndividuals.add(individual);
        }
        Collections.sort(population.getIndividuals());

        for (int i = 0; newIndividuals.size() < populationSize; i++) {
            // specificMutation(distance, travelDuration, nodeDuration,
            // population.getIndividuals().get(i));
            if (Math.random() < mutationRate) {
                specificMutation(distance, travelDuration, nodeDuration, population.getIndividuals().get(i));
            }
            newIndividuals.add(population.getIndividuals().get(i));
        }

        /// Merge Parent & Child
        for (int i = 0; i < populationSize; i++) {
            newIndividuals.add(population.getIndividuals().get(i));
        }

        int frontIndex = 0;
        while (true) {
            ArrayList<Individual> frontInd = new ArrayList<>();
            for (Individual ind : newIndividuals) {
                if (ind.getRank() == frontIndex)
                    frontInd.add(ind);
            }
            if (frontInd.size() < 2)
                break;
            crowindDistanceAssignment(frontInd);
            frontIndex++;
        }

        fastNonDominatedSort(newIndividuals);

        crowindDistanceAssignment(newIndividuals);
        selectionSortByCrowdingDistance(newIndividuals);

        population.setIndividuals(selectParentByRank(newIndividuals));

        population.calculateFitnessRatioEachIndividual();
        generation++;
    }

    ArrayList<Individual> selectParentByRank(ArrayList<Individual> parent) {
        int pivot = 0;
        ArrayList<Individual> child = new ArrayList<>();
        while (child.size() < populationSize) {
            for (Individual par : parent) {
                if (child.size() < populationSize && par.getRank() == pivot) {
                    child.add(par);
                }
            }
            pivot++;
        }
        return child;
    }

    void crowindDistanceAssignment(ArrayList<Individual> individuals) {
        int dimention = 0;
        for (Individual ind : individuals)
            ind.setCrowdingDistance(0);
        while (dimention < 2) {
            selectionSortByDimention(individuals, dimention);

            individuals.get(1).setCrowdingDistance(Double.MAX_VALUE);
            individuals.get(individuals.size() - 1).setCrowdingDistance(Double.MAX_VALUE);

            double max = Double.MIN_VALUE;
            double min = Double.MAX_VALUE;

            for (Individual ind : individuals) {
                double val = ind.getObjectiveVal(dimention);
                if (val > max)
                    max = val;
                if (val < min)
                    min = val;
            }
            for (int i = 2; i < individuals.size() - 1; i++) {
                individuals.get(i).setCrowdingDistance(
                        individuals.get(i).getCrowdingDistance() + ((individuals.get(i + 1).getObjectiveVal(dimention)
                                + individuals.get(i - 1).getObjectiveVal(dimention)) / (max - min)));
            }
            dimention++;
        }
    }

    void selectionSortByCrowdingDistance(ArrayList<Individual> individuals) {
        int pos;
        for (int i = 0; i < individuals.size(); i++) {
            pos = i;
            for (int j = i + 1; j < individuals.size(); j++) {
                if (individuals.get(j).getCrowdingDistance() < individuals.get(pos).getCrowdingDistance()) {
                    pos = j;
                }
            }
            Collections.swap(individuals, i, pos);
        }
    }

    void selectionSortByDimention(ArrayList<Individual> individuals, int dimention) {
        int pos;
        for (int i = 0; i < individuals.size(); i++) {
            pos = i;
            for (int j = i + 1; j < individuals.size(); j++) {
                if (individuals.get(j).getObjectiveVal(dimention) < individuals.get(pos).getObjectiveVal(dimention)) {
                    pos = j;
                }
            }
            Collections.swap(individuals, i, pos);
        }
    }

    void fastNonDominatedSort(ArrayList<Individual> individuals) {
        for (Individual individual : individuals) {
            individual.reset();
        }
        for (int i = 0; i < individuals.size() - 1; i++) {
            Individual p = individuals.get(i);
            for (int j = i + 1; j < individuals.size(); j++) {
                Individual q = individuals.get(j);
                if (p.compareDominate(q)) {
                    p.addDominates();
                    q.addDominatedIndividual(p);
                } else if (q.compareDominate(p)) {
                    q.addDominates();
                    p.addDominatedIndividual(q);
                }
            }
            if (p.getDominates() == 0) {
                p.setRank(1);
            }
        }

        if (individuals.get(individuals.size() - 1).getDominates() == 0) {
            individuals.get(individuals.size() - 1).setRank(1);
        }

        while (population.populationHasUnsetRank()) {
            for (int i = 0; i < individuals.size(); i++) {
                Individual ind = individuals.get(i);
                if (ind.getRank() != -1) {
                    for (int j = 0; j < ind.getDominatedIndividual().size(); j++) {
                        Individual dominated = ind.getDominatedIndividual().get(j);
                        if (dominated.getDominates() > 0) {
                            int left = dominated.removeDominates();
                            if (left == 0) {
                                dominated.setRank(ind.getRank() + 1);
                            }
                        }
                    }
                }
            }
        }
        // plotIndividuals(individuals, -1);
    }

    void plotIndividuals(ArrayList<Individual> individuals, int rank) {
        HashMap<Double, Double> data = new HashMap<Double, Double>();
        for (Individual i : individuals) {
            if (i.getRank() == rank || rank == -1)
                data.put(i.getFitness(), i.getTimeFitness());
        }
        ScatterPlot plot = new ScatterPlot("Gan");
        XYDataset _data = plot.createDataSet("Fast Non dominated set : " + rank, data);
        plot.plot(_data, "chartName", "Distance", "Time");
    }

}
