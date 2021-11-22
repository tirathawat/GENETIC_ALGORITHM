package CPE341;

class GenerationData {

    private Individual individual;
    private double averageDistance;
    private int generation;

    GenerationData(int generation, Individual individual, double averageDistance) {
        this.generation = generation;
        this.averageDistance = averageDistance;
        this.individual = individual;
    }

    Individual getIndividual() {
        return individual;
    }

    double getAverageDistance() {
        return averageDistance;
    }

    int getGeneration() {
        return generation;
    }
}
