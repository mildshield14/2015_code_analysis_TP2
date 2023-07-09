import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

import java.util.ArrayList;

public class Chart {

    public static void main(String[] args) {
        ArrayList<Double> yData= new ArrayList<>();
        yData.add(54.8);
        yData.add(10.8);
        yData.add(100.8);
        yData.add(7.8);
        yData.add(80.8);


        ArrayList<Double> xData= new ArrayList<>();
        xData.add(45.8);
        xData.add(32.5);
        xData.add(90.8);
        xData.add(7.8);
        xData.add(30.8);

        draw(xData,yData);
    }
    public static void draw(ArrayList<Double> yData, ArrayList<Double> xData) {
        // Create a dataset and populate it with the data
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] seriesData = new double[2][xData.size()];
        for (int i = 0; i < xData.size(); i++) {
            seriesData[0][i] = xData.get(i);
            seriesData[1][i] = yData.get(i);
        }
        dataset.addSeries("Series 1", seriesData);

        // Create the chart
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Graphique montrant l'Analyse Empirique",
                "n / entrepot(s)",
                "temps / ms",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        // Customize the data point shape
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
        renderer.setSeriesShapesVisible(0, true);
        plot.setRenderer(renderer);

        // Display the chart in a frame
        ChartFrame frame = new ChartFrame("Chart", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
