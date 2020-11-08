import java.util.ArrayList;

class Individual implements Comparable<Individual> {

    private ArrayList<Integer> chromosome;
    private int fitness;
    private double fitnessRatio;

    Individual (ArrayList<Integer> chromosome) {
        this.chromosome = chromosome;
    }

    ArrayList<Integer> getChromosome() {
        return chromosome;
    }

    public void setChromosome(ArrayList<Integer> chromosome) {
        this.chromosome = chromosome;
    }

    int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public double getFitnessRatio() {
        return fitnessRatio;
    }

    public void setFitnessRatio(double fitnessRatio) {
        this.fitnessRatio = fitnessRatio;
    }

    int getSize() {
        return chromosome.size();
    }

    void calculateFitnessRatio(int totalFitness) {
        this.fitnessRatio = (double) fitness / totalFitness;
    }

    void calculateFitness(int[][] distance) {
        fitness = 0;
        for (int i = 0; i < chromosome.size() - 1; i++)
            fitness += distance[chromosome.get(i) - 1][chromosome.get(i + 1) - 1];
    }

    @Override
    public int compareTo(Individual o) {
        return Integer.compare(this.getFitness(), o.getFitness());
    }
}
