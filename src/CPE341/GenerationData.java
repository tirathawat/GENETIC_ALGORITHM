package CPE341;

class GenerationData {

    private Individual individual;
    private double averageDistance;
    private double averageTime;
    private int generation;

    GenerationData(int generation, Individual individual, double averageDistance, double averageTime) {
        this.generation = generation;
        this.averageDistance = averageDistance;
        this.averageTime = averageTime;
        this.individual = individual;
    }

    Individual getIndividual() {
        return individual;
    }

    double getAverageDistance() {
        return averageDistance;
    }

    double getAverageTime() {
        return averageTime;
    }

    int getGeneration() {
        return generation;
    }
}
