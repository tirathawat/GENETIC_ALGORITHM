package CPE212;

import java.util.ArrayList;

class GenerationData {
    private ArrayList<Integer> path;
    private int bestDistance;
    private double averageDistance;
    private int generation;

    GenerationData (int generation, int bestDistance, ArrayList<Integer> path, double averageDistance) {
        this.generation = generation;
        this.bestDistance = bestDistance;
        this.averageDistance = averageDistance;
        this.path = new ArrayList<>(path);
    }

    ArrayList<Integer> getPath() {
        return path;
    }

    int getBestDistance() {
        return bestDistance;
    }

    double getAverageDistance() {
        return averageDistance;
    }

    int getGeneration() {
        return generation;
    }
}
