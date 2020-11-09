import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Arrays;

public class VehicleRoutingProblem {

    public static void main (String[] args) {
        Excel excel = new Excel("Genetic Algorithm");
        String[] columnData = {"Path", "Distance"};
        int[][] distance = {
                {0,10,28,23,65,16,16,42,16,21},
                {10,0,76,72,150,52,52,104,42,62},
                {28,76,0,106,74,48,83,28,88,58},
                {23,72,106,0,180,82,82,134,18,92},
                {65,150,74,180,0,122,157,56,162,132},
                {16,52,48,82,122,0,59,76,64,34},
                {16,52,83,82,157,59,0,111,64,69},
                {42,104,28,134,56,76,111,0,116,106},
                {16,42,88,18,162,64,64,116,0,74},
                {21,62,58,92,132,34,69,106,74,0},
        };
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100, 0.8, 0.01, 0.4);
        geneticAlgorithm.initialPopulation(distance);
        excel.writeRow("Initial Population", 0);
        excel.writeHeaderRow(columnData);
        for (Individual individual : geneticAlgorithm.getPopulation().getIndividuals()) {
            ArrayList<String> data = new ArrayList<>();
            data.add("" + individual.getChromosome());
            data.add("" + individual.getFitness());
            excel.writeRow(data);
        }
        do {
            geneticAlgorithm.evaluation(distance);
            geneticAlgorithm.createNewPopulation(distance);
        } while (geneticAlgorithm.isTerminate());
        excel.export("test");
    }

}
