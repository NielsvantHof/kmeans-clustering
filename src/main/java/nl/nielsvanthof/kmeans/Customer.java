package nl.nielsvanthof.kmeans;

import java.util.Arrays;

class Customer {
    private double [] behaviour;
    private int dimension;
    private Cluster presentCluster;

    Customer(double[] behaviour)
    {
        this.behaviour = behaviour;
        setDimension();
    }

    double calculateEuclideanDistance(Customer other)
    {
        double val = 0;

        for(int i = 0; i < dimension; i++) {
            val += Math.pow(behaviour[i] - other.behaviour[i], 2);
        }

        return Math.sqrt(val);
    }

    double calculateSquaredEuclideanDistance(Customer other)
    {
        double val = 0;

        for(int i = 0; i < dimension; i++) {
            val += Math.pow(behaviour[i] - other.behaviour[i], 2);
        }

        return val;
    }

    Customer copy()
    {
        double[] copyArray = Arrays.copyOf(behaviour, dimension);

        return new Customer(copyArray);
    }

    Cluster getPresentCluster()
    {
        return presentCluster;
    }

    void setPresentCluster(Cluster presentCluster)
    {
        this.presentCluster = presentCluster;
    }

    int getDimension()
    {
        return dimension;
    }

    double [] getBehaviour()
    {
        return behaviour;
    }

    void setBehaviour(double[] behaviour)
    {
        this.behaviour = behaviour;
    }

    private void setDimension()
    {
        this.dimension = behaviour.length;
    }
}
