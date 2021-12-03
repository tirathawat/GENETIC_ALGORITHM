package CPE341;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class VehicleRoutingProblem {

    public static void main(String[] args) {
        Preparator preparator = new Preparator();
        ArrayList<String> distance = preparator.readFile("distance.txt");
        ArrayList<String> travelDuration = preparator.readFile("travel_duration.txt");
        ArrayList<String> nodeDuration = preparator.readFile("node_duration.txt");
        ArrayList<String> nodeName = preparator.readFile("node_name.txt");
        preparator.prepareData(distance, travelDuration, nodeDuration, nodeName, "	");

        ArrayList<GenerationData> generationData = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        BruteForce bruteForce = new BruteForce(20);
        bruteForce.calculate(preparator.getDistance(), preparator.getTravelDuration(),
            preparator.getNodeDuration());
        long stopTime = System.currentTimeMillis();
        
        System.out.println("Running Time : " + (stopTime - startTime) + " ms");

    }
}
