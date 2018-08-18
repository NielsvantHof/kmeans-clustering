package nl.nielsvanthof.kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Cluster {
    private final List<Customer> members;
    private final Customer Centroid;

    Cluster(ArrayList<Customer> members, Customer centroid) {
        this.members = members;
        Centroid = centroid;
    }

    // Get squared error method
    double getSE() {
        double val = 0;

        for(Customer customer : members) {
            val += customer.calculateSquaredEuclideanDistance(Centroid);
        }

        return val;
    }

    // Cluster update method
    void updateCentroid() {
        int dimension = Centroid.getDimension();
        double[] values = new double[dimension];

        // Set centroids coordinates to the average of all clusters customers
        for (int i = 0; i < dimension; i++) {
            for (Customer member : members) {
                values[i] += member.getBehaviour()[i];
            }

            values[i] /= dimension;
        }

        Centroid.setBehaviour(values);
    }

    List<Customer> getMembers()
    {
        return members;
    }

    Customer getCentroid()
    {
        return Centroid;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("There are " + members.size() + " customers in this cluster.\nSSE = " + getSE() + "\n");
        Map<Integer, Integer> offerBought = new TreeMap<>();

        for (int i = 0; i < members.get(0).getBehaviour().length; i++) {
            int numberBought = 0;

            // For every offer, calculate how many times it has been bought by customers of this cluster
            for (Customer customer : members) {
                numberBought += customer.getBehaviour()[i];
            }

            // Add result to map. -numberBought is used as as key so it sorts the map in descendant order
            offerBought.put(-numberBought, i + 1);
        }

        // Output all the offers bought at least 3 times
        for (Map.Entry<Integer, Integer> entry : offerBought.entrySet()) {
            if (entry.getKey() > -3) {
                break;
            }

            // Add into results, the key has -entry else negative 'bought x times' will be displayed
            result.append("Offer ").append(entry.getValue()).append(" bought ").append(-entry.getKey()).append(" times\n");
        }

        return result.toString();
    }
}
