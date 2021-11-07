import java.util.ArrayList;
import java.lang.Math;

class Individual implements Comparable<Individual> {

    private ArrayList<Integer> chromosome;
    private int decimal;
    private int fitness;
    private int inverseFitness;
    private double fitnessRatio;

    Individual() {
        generateChromosome();
    }

    Individual(ArrayList<Integer> chromosome) {
        this.chromosome = chromosome;
        this.decimal = toDecimal(chromosome);
    }

    ArrayList<Integer> getChromosome() {
        return chromosome;
    }

    int getDecimal() {
        return decimal;
    }

    int getFitness() {
        return fitness;
    }

    int getInverseFitness() {
        return inverseFitness;
    }

    double getFitnessRatio() {
        return fitnessRatio;
    }

    void calculateFitnessRatio(int inverseTotalFitness, int total) {
        this.fitnessRatio = (double) inverseFitness / inverseTotalFitness;
    }

    void calculateInverseFitness(int totalFitness) {
        this.inverseFitness = totalFitness - fitness;
    }

    void calculateFitness() {
        int x = decimal;
        fitness = (int) (Math.pow(x, 3) - (60 * Math.pow(x, 2)) + (900 * x) + 150);
    }

    int toDecimal(ArrayList<Integer> binary) {
        String binaryString = "";
        for (Integer gene : binary) {
            binaryString += gene.toString();
        }
        int decimal = Integer.parseInt(binaryString, 2);
        return decimal + 1;
    }

    ArrayList<Integer> toBinary(int decimal) {
        decimal = decimal - 1;
        ArrayList<Integer> result = new ArrayList<>();
        String binary = Integer.toBinaryString(decimal);
        for (int i = binary.length(); i < 6; i++) {
            result.add(0);
        }
        for (int i = 0; i < binary.length(); i++) {
            result.add(Integer.parseInt(binary.substring(i, i + 1)));
        }
        return result;
    }

    void generateChromosome() {
        int min = 1;
        int max = 64;
        this.decimal = min + (int) (Math.random() * ((max - min) + 1));
        this.chromosome = new ArrayList<Integer>(toBinary(this.decimal));
    }

    public void printChromosome() {
        System.out.print("binary: ");
        for (Integer gene : chromosome) {
            System.out.print(gene);
        }
        System.out.print(", decimal: " + this.decimal + ", fitness: " + this.fitness);
        System.out.println();
    }

    @Override
    public int compareTo(Individual o) {
        return Integer.compare(this.getFitness(), o.getFitness());
    }
}
