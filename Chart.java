import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.util.ArrayList;

    public class Chart {

        public static void main(String[] args) {
            ArrayList<Double> yData = new ArrayList<>();
            yData.add(54.8);
            yData.add(10.8);
            yData.add(100.8);
            yData.add(7.8);
            yData.add(80.8);

            ArrayList<Double> xData = new ArrayList<>();
            xData.add(45.8);
            xData.add(32.5);
            xData.add(90.8);
            xData.add(7.8);
            xData.add(30.8);

            draw(xData, yData);
        }

        public static void draw(ArrayList<Double> xData, ArrayList<Double> yData) {
            // Create a dataset and populate it with the data
            DefaultXYDataset dataset = new DefaultXYDataset();
            double[][] seriesData = new double[2][xData.size()];
            for (int i = 0; i < xData.size(); i++) {
                seriesData[0][i] = xData.get(i);
                seriesData[1][i] = yData.get(i);
            }
            double[] x = new double[100];
            for (int i = 0; i < x.length; i++) {
                x[i] = i + 1;
            }
            dataset.addSeries("Data", seriesData);
//            double[] y1 = calculateFunctionValues(x, val -> val);
//            double[] y2 = calculateFunctionValues(x, val -> val * val);
//            double[] y3 = calculateFunctionValues(x, val -> val * val * val);
//            double[] y4 = calculateConstantFunctionValues(x, 1);
//            double[] y5 = calculateFunctionValues(x, val -> val * Math.log(val));
//            double[] y6 = calculateFunctionValues(x, Math::log);
//            double[] y7 = calculateFunctionValues(x, Math::sqrt);
//            double[] y8 = calculateFunctionValues(x, val -> Math.pow(2, val));
//
//            dataset.addSeries("y=x", new double[][] {x, y1});
//            dataset.addSeries("y=x^2", new double[][] {x, y2});
//            dataset.addSeries("y=x^3", new double[][] {x, y3});
//            dataset.addSeries("y=1", new double[][] {x, y4});
//            dataset.addSeries("y=nlgn", new double[][] {x, y5});
//            dataset.addSeries("y=lgn", new double[][] {x, y6});
//            dataset.addSeries("y=x^1/2", new double[][] {x, y7});
//            dataset.addSeries("y=2^x", new double[][] {x, y8});

            // Fit a curve using polynomial regression
            double[] coefficients = polynomialRegression(xData, yData, 3); // Adjust the degree as needed
            double[] curveX = new double[100]; // Number of points on the curve
            double[] curveY = new double[100];
            double xMin = xData.get(0);
            double xMax = xData.get(xData.size() - 1);
            double step = (xMax - xMin) / (curveX.length - 1);
            for (int i = 0; i < curveX.length; i++) {
                curveX[i] = xMin + i * step;
                curveY[i] = evaluatePolynomial(coefficients, curveX[i]);
            }
            dataset.addSeries("Best-fit Curve", new double[][]{curveX, curveY});

            // Create the chart
            JFreeChart chart = ChartFactory.createScatterPlot(
                    "Graphique montrant l'Analyse Empirique du code Integrale",
                    "n / fichiers testés",
                    "temps / ms",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false
            );

            // Customize the data point shape
            XYPlot plot = chart.getXYPlot();
           plot.getRangeAxis().setRange(0, 300);
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesShapesVisible(0, true);
            renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0));
            renderer.setSeriesLinesVisible(1, true); // Make the best-fit curve visible
            plot.setRenderer(renderer);

            // Display the chart in a frame
            ChartFrame frame = new ChartFrame("Chart", chart);
            frame.pack();
            frame.setVisible(true);
        }

        private static double[] polynomialRegression(ArrayList<Double> xData, ArrayList<Double> yData, int degree) {
            int n = xData.size();
            int m = degree + 1;
            double[][] matrix = new double[m][m + 1];

            for (int row = 0; row < m; row++) {
                for (int col = 0; col <= row; col++) {
                    double sum = 0.0;
                    for (int i = 0; i < n; i++) {
                        double x = xData.get(i);
                        double y = yData.get(i);
                        sum += Math.pow(x, row + col);
                    }
                    matrix[row][col] = sum;
                    matrix[col][row] = sum;
                }
            }

            for (int row = 0; row < m; row++) {
                double sum = 0.0;
                for (int i = 0; i < n; i++) {
                    double x = xData.get(i);
                    double y = yData.get(i);
                    sum += Math.pow(x, row) * y;
                }
                matrix[row][m] = sum;
            }

            double[] coefficients = new double[m];
            for (int pivot = 0; pivot < m - 1; pivot++) {
                for (int row = pivot + 1; row < m; row++) {
                    double factor = matrix[row][pivot] / matrix[pivot][pivot];
                    for (int col = pivot; col <= m; col++) {
                        matrix[row][col] -= factor * matrix[pivot][col];
                    }
                }
            }

            for (int i = m - 1; i >= 0; i--) {
                double sum = matrix[i][m];
                for (int j = i + 1; j < m; j++) {
                    sum -= matrix[i][j] * coefficients[j];
                }
                coefficients[i] = sum / matrix[i][i];
            }

            return coefficients;
        }

        private static double evaluatePolynomial(double[] coefficients, double x) {
            double result = 0.0;
            for (int i = 0; i < coefficients.length; i++) {
                result += coefficients[i] * Math.pow(x, i);
            }
            return result;
        } private static double[] calculateFunctionValues(double[] xData, Function<Double, Double> function) {
            double[] values = new double[xData.length];
            for (int i = 0; i < xData.length; i++) {
                double x = xData[i];
                values[i] = function.apply(x);
            }
            return values;
        }private static double[] calculateConstantFunctionValues(double[] xData, double constant) {
            double[] values = new double[xData.length];
            for (int i = 0; i < xData.length; i++) {
                values[i] = constant;
            }
            return values;
        }

        // Functional interface for defining functions
        private interface Function<T, R> {
            R apply(T value);
        }

    }