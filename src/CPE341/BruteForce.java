package CPE341;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import org.jfree.data.xy.XYDataset;

class BruteForce {

    private int problemSize;

    private int[][] distance;
    private int[][] travelDuration;
    private int[] nodeDuration;
    private FileWriter fileWriter;
    private BufferedWriter writer;

    private ScatterPlot scatterPlotBruteForce;
    private HashMap<Double, Double> data = new HashMap<Double, Double>();

    private ArrayList<Individual> individuals = new ArrayList<>();

    BruteForce(int problemSize) {
        this.problemSize = problemSize;
    }

    private ArrayList<Integer> generateInitChromosome() {
        ArrayList<Integer> chromosome = new ArrayList<>();
        int i = 1;
        while (i <= problemSize) {
            chromosome.add(i + 1);
            // chromosome.add(i+1);
            i++;
        }
        return chromosome;
    }

    private void printChromosome(ArrayList<Integer> target) {
        for (Integer item : target) {
            System.out.print(item);
        }
        System.out.println("");
    }

    void permute(ArrayList<Integer> chromosome, int l, int r) {
        if (l == r) {
            // printChromosome(chromosome);
            Individual ind = new Individual(chromosome);
            ind.calculateFitness(distance, travelDuration, nodeDuration);
            individuals.add(ind);
            // data.put(ind.getTimeFitness(), ind.getFitness());
            try {
                writer.write(ind.getTimeFitness() + " " + ind.getFitness() + "\n");
            } catch (IOException err) {
                System.out.println(err);
            }
        } else {
            for (int i = l; i <= r; i++) {
                Collections.swap(chromosome, l, i);
                permute(chromosome, l + 1, r);
                Collections.swap(chromosome, l, i);
            }
        }
    }

    public void calculate(int[][] _distance, int[][] _travelDuration, int[] _nodeDuration) {
        System.out.println("starting..");

        distance = _distance;
        travelDuration = _travelDuration;
        nodeDuration = _nodeDuration;

        // ScatterPlot scatterPlotBruteForce = new ScatterPlot("Brute Force Result");
        // HashMap<Double, Double> data = new HashMap<Double, Double>();

        ArrayList<Integer> chromosome = generateInitChromosome();
        try {
            fileWriter = new FileWriter("solutions" + problemSize + ".txt");
        } catch (IOException err) {
            System.out.println("Problem with file");
            return;
        }
        writer = new BufferedWriter(fileWriter);
        permute(chromosome, 0, problemSize - 1);

        // ArrayList<Individual> individuals = new ArrayList<>();

        // int l = 0;
        // int r = problemSize-1;
        // for (int i = l; i <= r; i++)
        // {
        // str = swap(str,l,i);
        // permute(str, l+1, r);
        // str = swap(str,l,i);
        // }

        // while (true) {
        // if (isChromosomeValid(chromosome)) {
        // printChromosome(chromosome);
        // Individual ind = new Individual(chromosome);
        // ind.calculateFitness(distance, travelDuration, nodeDuration);
        // // individuals.add(ind);
        // // data.put(ind.getTimeFitness(), ind.getFitness());
        // try {
        // writer.write(ind.getChromosome().toString() + " " + ind.getTimeFitness() + "
        // " + ind.getFitness() + "\n");
        // } catch (IOException err) {
        // System.out.println(err);
        // }
        // }
        // int pivot = problemSize-1;
        // while (pivot>=0) {
        // if (chromosome.get(pivot)==problemSize) {
        // chromosome.set(pivot, 1);
        // pivot--;
        // }
        // else {
        // chromosome.set(pivot, chromosome.get(pivot) + 1);
        // break;
        // }
        // }
        // // printChromosome(chromosome);
        // if (pivot<0) break;
        // }

        // printParetoFront(individuals);

        try {
            writer.close();
        } catch (IOException err) {
            System.out.println(err);
        }
        // scatterPlotBruteForce.plot(scatterPlotBruteForce.createDataSet("Brute Force
        // Result", data), "Brute Force Result", "Distance(km)", "Time(m)");
        // printParetoFront(individuals);
    }

    void printParetoFront(ArrayList<Individual> individuals) {
        System.out.println("Starting pareto..");
        ScatterPlot scatterPlotPareto = new ScatterPlot("Pareto Result");
        HashMap<Double, Double> dataPareto = new HashMap<Double, Double>();
        for (int i = 0; i < individuals.size() - 1; i++) {
            Individual p = individuals.get(i);
            for (int j = i + 1; j < individuals.size(); j++) {
                Individual q = individuals.get(j);
                if (p.compareDominate(q)) {
                    p.addDominates();
                } else if (q.compareDominate(p)) {
                    q.addDominates();
                }
            }
            if (p.getDominates() == 0) {
                p.setRank(1);
                dataPareto.put(p.getTimeFitness(), p.getFitness());
            }
        }
        scatterPlotPareto.plot(scatterPlotPareto.createDataSet("Brute Force Result", dataPareto), "Pareto Result",
                "Distance(km)", "Time(m)");
    }

}
