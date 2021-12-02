package CPE341;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import org.jfree.data.xy.XYDataset;

class BruteForce {

    private int problemSize;

    BruteForce(int problemSize) {
        this.problemSize = problemSize;
    }

    private ArrayList<Integer> generateInitChromosome() {
        ArrayList<Integer> chromosome = new ArrayList<>();
        int i = 0;
        while (i < problemSize) {
            chromosome.add(1);
            // chromosome.add(i+1);
            i++;
        }
        return chromosome;
    }

    private Boolean isInArray (int target, ArrayList<Integer> set) {
        for (Integer item : set) {
            if (item == target) return true;
        }
        return false;
    }

    private void test () {
        ArrayList<Integer> used = new ArrayList<>();
    }

    private void printChromosome (ArrayList<Integer> target) {
        for (Integer item : target) {
            System.out.print(item);
        }
        System.out.println("");
    }
    

    private Boolean isChromosomeValid (ArrayList<Integer> target) {
        for (int i = 0; i < target.size(); i++) {
            for (int j = 0; j < target.size(); j++) {
                if (i!=j && target.get(i) == target.get(j)) return false;
            }
        }
        return true;
    }


    public void calculate (int[][] distance, int[][] travelDuration, int[] nodeDuration) {
        System.out.println("starting..");
        ScatterPlot scatterPlotBruteForce = new ScatterPlot("Brute Force Result");
        HashMap<Double, Double> data = new HashMap<Double, Double>();
        ArrayList<Integer> chromosome = generateInitChromosome();

        ArrayList<Individual> individuals = new ArrayList<>();

        while (true) {
            int pivot = problemSize-1;
            while (pivot>=0) {
                if (chromosome.get(pivot)==problemSize) {
                    chromosome.set(pivot, 1);
                    pivot--;
                }
                else {
                    chromosome.set(pivot, chromosome.get(pivot) + 1);
                    break;
                }
            }
            if (isChromosomeValid(chromosome)) {
                // printChromosome(chromosome);
                Individual ind = new Individual(chromosome);
                ind.calculateFitness(distance, travelDuration, nodeDuration);
                individuals.add(ind);
                data.put(ind.getTimeFitness(), ind.getFitness());
            }
            if (pivot<0) break; 
        }
        scatterPlotBruteForce.plot(scatterPlotBruteForce.createDataSet("Brute Force Result", data), "Brute Force Result", "Distance(km)", "Time(m)");
        printParetoFront(individuals);
    }

    // private Boolean individualsHasUnsetRank (ArrayList<Individual> list) {
    //     for (Individual item : list) {
    //         if (item.getRank()==-1) return true;
    //     }
    //     return false;
    // }
    
    void printParetoFront(ArrayList<Individual> individuals) {
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
        scatterPlotPareto.plot(scatterPlotPareto.createDataSet("Brute Force Result", dataPareto), "Pareto Result", "Distance(km)", "Time(m)");
    }

}
