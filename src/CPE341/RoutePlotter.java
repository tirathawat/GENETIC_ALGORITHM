package CPE341;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import CPE341.Plot.Data;

public class RoutePlotter {

    void plot(ArrayList<Integer> path, ArrayList<String> geolocation, ArrayList<Integer> days) throws IOException {
        int offset = 0;

        for (int i = 0; i < days.size(); i++) {

            int countNode = 0 - offset;
            Data data = Plot.data();

            for (Integer p : path) {

                int node = p - 1;

                if (node != 0) {
                    countNode++;
                }

                String[] geo = geolocation.get(node).split(" ");
                double lat = Double.parseDouble(geo[0]);
                double lng = Double.parseDouble(geo[1]);

                boolean hasLine = countNode <= days.get(i) && countNode > 0;

                data = data.xy(lat, lng, node == 0, hasLine);

            }

            plot("day_" + (i + 1), data);
            offset += days.get(i);
        }

    }

    private void plot(String filename, Data data) throws IOException {
        Plot plot = Plot.plot(
                Plot.plotOpts().title(filename).legend(Plot.LegendFormat.BOTTOM))
                .xAxis("x", Plot.axisOpts()).series(null,
                        data,
                        Plot.seriesOpts().marker(Plot.Marker.CIRCLE).markerColor(Color.GREEN).color(Color.BLACK));
        try {
            plot.save(filename, "png");
        } catch (IOException e) {
            throw e;
        }
    }
}
