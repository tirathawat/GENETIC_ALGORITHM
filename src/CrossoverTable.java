import java.util.ArrayList;

class CrossoverTable {
    private ArrayList<Integer> parent1;
    private ArrayList<Integer> parent2;
    private ArrayList<Integer> offspring;
    private int pt1;
    private int pt2;
    private boolean isMutation;

    public CrossoverTable(ArrayList<Integer> parent1, ArrayList<Integer> parent2, ArrayList<Integer> offspring, int pt1, int pt2, boolean isMutation) {
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.offspring = offspring;
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.isMutation = isMutation;
    }

    public ArrayList<Integer> getParent1() {
        return parent1;
    }

    public void setParent1(ArrayList<Integer> parent1) {
        this.parent1 = parent1;
    }

    public ArrayList<Integer> getParent2() {
        return parent2;
    }

    public void setParent2(ArrayList<Integer> parent2) {
        this.parent2 = parent2;
    }

    public ArrayList<Integer> getOffspring() {
        return offspring;
    }

    public void setOffspring(ArrayList<Integer> offspring) {
        this.offspring = offspring;
    }

    public int getPt1() {
        return pt1;
    }

    public void setPt1(int pt1) {
        this.pt1 = pt1;
    }

    public int getPt2() {
        return pt2;
    }

    public void setPt2(int pt2) {
        this.pt2 = pt2;
    }

    public boolean isMutation() {
        return isMutation;
    }

    public void setMutation(boolean mutation) {
        isMutation = mutation;
    }
}
