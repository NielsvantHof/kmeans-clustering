package nl.nielsvanthof.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class KMeans {
    private final Random random = new Random(100);
    private final int iterationNumber;
    private final int clusterNumber;
    private final List<Cluster> clusters = new ArrayList<>();
    private List<Customer> allCustomers;

    KMeans(int iterationNumber, int clusterNumber, List<Customer> allCustomers) {
        this.iterationNumber = iterationNumber;
        this.clusterNumber = clusterNumber;
        this.allCustomers = allCustomers;
    }

    void simulate() {
        for(int i = 0; i < clusterNumber; i++) {
            int index = random.nextInt(allCustomers.size());

            // Create empty cluster with center at chosen customer
            Cluster cluster = new Cluster(new ArrayList<Customer>(), allCustomers.get(index).copy());
            clusters.add(cluster);
        }

        // Set initial customers to clusters
        int currentCluster = 0;

        for(Customer customer : allCustomers) {
            Cluster cluster = clusters.get(currentCluster);
            currentCluster = (currentCluster + 1) % clusterNumber;
            cluster.getMembers().add(customer);
            customer.setPresentCluster(cluster);
        }

        // Update clusters for iterations needed, trying to find optimal solution
        for (int iteration = 0; iteration < iterationNumber; iteration++) {

            // Assign each point to the closest centroid (using the Euclidean distance)
            for(Customer customer : allCustomers) {

                // Get distance between the customer and center of that customers cluster
                double presentDistance = customer.calculateEuclideanDistance(customer.getPresentCluster().getCentroid());

                // For every cluster, check if distance between its center and given customer is less than distance between customer and center of that customers cluster
                for(Cluster cluster : clusters) {
                    double centroidDistance = customer.calculateEuclideanDistance(cluster.getCentroid());

                    // If new distance is better, update distance
                    if (centroidDistance < presentDistance) {
                        presentDistance = centroidDistance;

                        // Move user to new cluster and removing it from previous
                        Cluster previousCluster = customer.getPresentCluster();

                        if (previousCluster != null) {
                            previousCluster.getMembers().remove(customer);
                        }

                        customer.setPresentCluster(cluster);
                        cluster.getMembers().add(customer);
                    }
                }
            }

            // Update all the clusters
            for(Cluster cluster : clusters) {
                cluster.updateCentroid();
            }

            // Print sum of squared errors for solution on that iteration
            System.out.println((iteration + 1) + " iteration. Present SSE value: " + calculateSSE());
        }
    }

    void resultOutput() {
        System.out.println("\n\n----Printing best solution----");

        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("\n\nCluster number: " + (i + 1));
            System.out.println(clusters.get(i));
        }

        System.out.println("SSE value of best solution: " + calculateSSE());
    }

    // Optimal solution found output
    private double calculateSSE() {
        double val = 0;

        for (Cluster cluster : clusters) {
            val += cluster.getSE();
        }

        return val;
    }
}