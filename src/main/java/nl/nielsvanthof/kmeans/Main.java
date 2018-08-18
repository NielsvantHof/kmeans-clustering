package nl.nielsvanthof.kmeans;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Customer> customers = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        setCustomers();

        Scanner scn = new Scanner(System.in);
        System.out.println("Enter number of clusters: ");
        int clusterNumber = scn.nextInt();

        System.out.println("Enter number of iterations: ");
        int iterationNumber = scn.nextInt();

        KMeans k = new KMeans(iterationNumber, clusterNumber, customers);

        k.simulate();
        k.resultOutput();

        scn.close();
    }

    private static void setCustomers() throws FileNotFoundException {
        int customerNumber = 100;
        int wineOffers = 32;
        int lineNumber = 0;
        double [][] allData = new double[customerNumber][wineOffers];

        Scanner scanner = new Scanner(new File("resources/WineData.csv"));

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            for (int i = 0; i < data.length; i++) {
                allData[i][lineNumber] = Double.parseDouble(data[i]);
            }

            lineNumber++;
        }

        for (int i = 0; i < customerNumber; i++) {
            Customer customer = new Customer(allData[i]);
            customers.add(customer);
        }

        scanner.close();
    }
}
