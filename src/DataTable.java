import java.util.ArrayList;

class DataTable {
    private ArrayList<Integer> path;
    private double fitnessRatio;
    private double CDF;
    private double distance;

    public DataTable(ArrayList<Integer> path, double fitnessRatio, double CDF, double distance) {
        this.path = path;
        this.fitnessRatio = fitnessRatio;
        this.CDF = CDF;
        this.distance = distance;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }

    public double getFitnessRatio() {
        return fitnessRatio;
    }

    public void setFitnessRatio(double fitnessRatio) {
        this.fitnessRatio = fitnessRatio;
    }

    public double getCDF() {
        return CDF;
    }

    public void setCDF(double CDF) {
        this.CDF = CDF;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
