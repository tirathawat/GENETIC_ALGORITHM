package CPE341;

import java.util.ArrayList;
import java.util.Collections;

class Individual implements Comparable<Individual> {

    private ArrayList<Integer> chromosome;
    private ArrayList<Integer> path;
    private ArrayList<Integer> days;
    private double fitness;
    private double inverseFitness;
    private double fitnessRatio;
    
    private double timeFitness;
    private double timeInverseFitness;
    private double timeFitnessRatio;

    private int rank = 0;
    private int dominates = 0;
    private ArrayList<Individual> dominatedIndividual;

    Individual(int problemSize) {
        this.path = new ArrayList<Integer>();
        this.days = new ArrayList<Integer>();
        generateChromosome(problemSize);
    }

    Individual(ArrayList<Integer> chromosome) {
        this.path = new ArrayList<Integer>();
        this.days = new ArrayList<Integer>();
        this.chromosome = chromosome;
    }

    ArrayList<Integer> getChromosome() {
        return chromosome;
    }

    ArrayList<Integer> getPath() {
        return path;
    }

    ArrayList<Integer> getDays() {
        return days;
    }

    double getFitness() {
        return fitness;
    }

    int getRank() {
        return rank;
    }

    void setRank(int newRank) {
        rank = newRank;
    }

    int getDominates() {
        return dominates;
    }

    int addDominates() {
        dominates++;
        return dominates;
    }
    int removeDominates() {
        dominates--;
        return dominates;
    }

    ArrayList<Individual> getDominatedIndividual() {
        return dominatedIndividual;
    }

    void addDominatedIndividual(Individual target) {
        dominatedIndividual.add(target);
    }

    void reset () {
        rank = -1;
        dominates = 0;
        dominatedIndividual = new ArrayList<>();
    }

    double getInverseFitness() {
        return inverseFitness;
    }

    double getFitnessRatio() {
        return fitnessRatio;
    }

    double getTimeFitness() {
        return timeFitness;
    }

    double getTimeInverseFitness() {
        return timeInverseFitness;
    }

    double getTimeFitnessRatio() {
        return timeFitnessRatio;
    }

    void calculateFitnessRatio(double inverseTotalFitness) {
        this.fitnessRatio = inverseFitness / inverseTotalFitness;
    }

    void calculateInverseFitness(double totalFitness) {
        this.inverseFitness = totalFitness - fitness;
    }

    void calculateFitness(int[][] distance, int[][] travelDuration, int[] nodeDuration) {
        int countNode = 1; // count node per day
        int timeLimit = 10;
        int time = 0;
        int totalTime = 0;
        fitness = 0;
        days.clear();
        path.clear(); // reset path
        path.add(1); // add depot node
        path.add(chromosome.get(0));
        for (int i = 0; i < chromosome.size() - 1; i++) {
            time += nodeDuration[chromosome.get(i) - 1];
            totalTime += nodeDuration[chromosome.get(i) - 1];
            int futureTime = time + nodeDuration[chromosome.get(i + 1) - 1];
            if (futureTime > timeLimit) {
                days.add(countNode);
                countNode = 0; // reset count
                time = 0; // reset time
                path.add(1); // go to depot
                fitness += distance[chromosome.get(i) - 1][0]; // calculate distance from current node to depot
                path.add(chromosome.get(i + 1)); // go to next node
                countNode++;
                fitness += distance[0][chromosome.get(i + 1) - 1];// calculate distane from depot to next node
            } else {
                countNode++;
                path.add(chromosome.get(i + 1));
                fitness += distance[chromosome.get(i) - 1][chromosome.get(i + 1) - 1];
            }
        }
        days.add(countNode);
        path.add(1); // back to depot
        timeFitness = totalTime;
    }

    void generateChromosome(int problemSize) {
        ArrayList<Integer> chromosome = new ArrayList<>();
        for (int i = 1; i < problemSize + 1; i++) {
            chromosome.add(i + 1);
        }
        Collections.shuffle(chromosome);
        this.chromosome = new ArrayList<>(chromosome);
    }

    public void printChromosome() {
        System.out.println("++++ print chromosome ++++");
        System.out.println("chromosome: " + chromosome.toString());
        System.out.println("travel path: " + path.toString());
        System.out.println("days: " + days.toString() + " (" + days.size() + " days)");
        System.out.println("fitness(distance): " + this.fitness);
        System.out.println();
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(this.getFitness(), o.getFitness());
    }

    public boolean compareDominate (Individual o) {
        return Double.compare(this.getFitness(), o.getFitness()) > 0 && Double.compare(this.getTimeFitness(), o.getTimeFitness()) > 0;
    }
}
