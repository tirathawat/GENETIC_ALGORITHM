package CPE341;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Preparator {

    private int[][] distance;
    private int[][] travelDuration;
    private int[] nodeDuration;
    private ArrayList<String> nodeName;

    public ArrayList<String> getNodeName() {
        return nodeName;
    }

    public int[] getNodeDuration() {
        return nodeDuration;
    }

    public int[][] getTravelDuration() {
        return travelDuration;
    }

    public int[][] getDistance() {
        return distance;
    }

    public ArrayList<String> readFile(String filename) {
        ArrayList<String> contenStrings = new ArrayList<String>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                contenStrings.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return contenStrings;
    }

    public void prepareData(ArrayList<String> rawDistance, ArrayList<String> rawTravelDuration,
            ArrayList<String> rawNodeDuration, ArrayList<String> nodeName, String regex) {
        this.distance = to2DMatrix(rawDistance, regex);
        this.travelDuration = to2DMatrix(rawTravelDuration, regex);
        this.nodeDuration = toIntArray(rawNodeDuration, regex);
        this.nodeName = nodeName;
    }

    private int[] toIntArray(ArrayList<String> raw, String regex) {
        int size = raw.get(0).length();
        if (size < 1) {
            return null;
        }
        int[] result = new int[size];
        String[] values = raw.get(0).split(regex);
        for (int i = 0; i < values.length; i++) {
            int value = Integer.parseInt(values[i]);
            result[i] = value;
        }
        return result;
    }

    private int[][] to2DMatrix(ArrayList<String> raw, String regex) {
        int size = raw.size();
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            String[] values = raw.get(i).split(regex);
            for (int j = 0; j < values.length; j++) {
                int value = Integer.parseInt(values[j]);
                result[i][j] = value;
            }
        }
        return result;
    }
}
