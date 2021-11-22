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

    Individual() {
        this.path = new ArrayList<Integer>();
        this.days = new ArrayList<Integer>();
        generateChromosome();
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

    double getInverseFitness() {
        return inverseFitness;
    }

    double getFitnessRatio() {
        return fitnessRatio;
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
        fitness = 0;
        path.clear(); // reset path
        path.add(1); // add depot node
        path.add(chromosome.get(0));
        for (int i = 0; i < chromosome.size() - 1; i++) {
            time += nodeDuration[chromosome.get(i) - 1];
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
    }

    void generateChromosome() {
        ArrayList<Integer> chromosome = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
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
}
