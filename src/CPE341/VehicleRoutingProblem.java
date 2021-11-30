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
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(10, 5000, 0.75, 0.01, .75);
        geneticAlgorithm.initialPopulation();

        Boolean stop;
        do {
            geneticAlgorithm.evaluation(preparator.getDistance(), preparator.getTravelDuration(),
                    preparator.getNodeDuration());

            GenerationData newGen = new GenerationData(geneticAlgorithm.getGeneration(),
                    geneticAlgorithm.getPopulation().getIndividuals().get(0), geneticAlgorithm.getAverageFitness(), geneticAlgorithm.getAverageTimeFitness());

            generationData.add(newGen);

            stop = geneticAlgorithm.isTerminate();
            if (!stop) {
                geneticAlgorithm.createNewPopulation(preparator.getDistance(), preparator.getTravelDuration(),
                        preparator.getNodeDuration());
            }
        } while (!stop);
        long stopTime = System.currentTimeMillis();
        
        ScatterPlot scatterPlotAvg = new ScatterPlot("NSGA-II Result");
        ScatterPlot scatterPlotBest = new ScatterPlot("NSGA-II Result");
        HashMap<Double, Double> dataAvg = new HashMap<Double, Double>();
        HashMap<Double, Double> dataBest = new HashMap<Double, Double>();

        for (GenerationData g : generationData) {
            dataAvg.put(g.getAverageDistance()/1000,  g.getAverageTime()/60);
            dataBest.put(g.getIndividual().getFitness()/1000,  g.getIndividual().getTimeFitness()/60);
            System.out.print("Gen : " + g.getGeneration() + ", ");
            System.out.print("Best distance: " + g.getIndividual().getFitness() / 1000 + "km, ");
            System.out.print("Best time: " + g.getIndividual().getTimeFitness() / 60 + "m, ");
            // System.out.print("Path : " +
            // Arrays.toString(g.getIndividual().getPath().toArray()) + ", ");

            System.out.print("Days : " +
            Arrays.toString(g.getIndividual().getDays().toArray()) + " ("
            + g.getIndividual().getDays().size() + " days), ");
            System.out.print("Average distance : " + g.getAverageDistance() / 1000 + "km");
            System.out.print("Average time : " + g.getAverageTime() / 60 + "m");
            System.out.println("");
        }
        System.out.println("Running Time : " + (stopTime - startTime) + " ms");
        scatterPlotAvg.plot(scatterPlotAvg.createDataSet("NSGA-II AVG", dataAvg), "NSGA-II Result", "Distance(AVG) (km)", "Time(AVG) (m)");
        scatterPlotBest.plot(scatterPlotBest.createDataSet("NSGA-II Best", dataBest), "NSGA-II Result", "Distance(Best) (km)", "Time(Best) (m)");

    }
}
