package twofoldtest;

import java.io.*;
import java.util.*;

public class Main {

    public static List<double[]> loadData(String fileName) throws IOException {
        List<double[]> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            double[] row = new double[values.length];

            for (int i = 0; i < values.length; i++) {
                row[i] = Double.parseDouble(values[i]);
            }

            data.add(row);
        }

        reader.close();
        return data;
    }

    public static double calculateEuclideanDistance(double[] point1, double[] point2) {
        double sum = 0.0;

        for (int i = 0; i < point1.length - 1; i++) {
            sum += Math.pow(point1[i] - point2[i], 2);
        }

        return Math.sqrt(sum);
    }

    public static int findNearestNeighbor(List<double[]> trainingData, double[] testDataPoint) {
        double smallestDistance = Double.MAX_VALUE;
        int closestLabel = -1;

        for (double[] trainingPoint : trainingData) {
            double distance = calculateEuclideanDistance(trainingPoint, testDataPoint);

            if (distance < smallestDistance) {
                smallestDistance = distance;
                closestLabel = (int) trainingPoint[trainingPoint.length - 1];
            }
        }

        return closestLabel;
    }

    public static double calculatingAccuracyOfModel(List<double[]> trainingData, List<double[]> testData) {
        int correctPredictions = 0;

        for (double[] testPoint : testData) {
            int predictedLabel = findNearestNeighbor(trainingData, testPoint);
            int actualLabel = (int) testPoint[testPoint.length - 1];

            if (predictedLabel == actualLabel) {
                correctPredictions++;
            }
        }

        return (double) correctPredictions / testData.size() * 100;
    }

    public static void main(String[] args) throws IOException {
        List<double[]> dataGroup1 = loadData("data/dataSet1.csv");
        List<double[]> dataGroup2 = loadData("data/dataSet2.csv");

        double accuracy1 = calculatingAccuracyOfModel(dataGroup1, dataGroup2);
        double accuracy2 = calculatingAccuracyOfModel(dataGroup2, dataGroup1);

        System.out.println("Accuracy of model when trained on group1 and tested on group2: " + accuracy1 + "%");
        System.out.println("Accuracy of model when trained on group2 and tested on group1: " + accuracy2 + "%");

        double averageAccuracy = (accuracy1 + accuracy2) / 2;
        System.out.println("Average Accuracy: " + averageAccuracy + "%");

       
    }
}
