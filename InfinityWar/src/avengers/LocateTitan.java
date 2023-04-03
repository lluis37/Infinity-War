package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }

    	// WRITE YOUR CODE HERE
        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];
        StdIn.setFile(locateTitanInputFile);
        StdOut.setFile(locateTitanOutputFile);

        int numGenerators = StdIn.readInt();
        double[] functionalities = new double[numGenerators];

        // populate an array with the functionalities of each generator
        for (int i = 0; i < numGenerators; i++) {
            int generatorNum = StdIn.readInt();
            double functionality = StdIn.readDouble();

            functionalities[generatorNum] = functionality;
        }

        int[][] costAdjMatrix = new int[numGenerators][numGenerators];
        // populate an adjacency matrix with the energyCost of traveling from one generator to another
        for (int r = 0; r < costAdjMatrix.length; r++) {
            for (int c = 0; c < costAdjMatrix[r].length; c++) {
                int energyCost = StdIn.readInt();
                costAdjMatrix[r][c] = energyCost;
            }
        }

        // update the edgeWeight between two vertices to be the totalCost of traveling from
        // one generator to another
        for (int r = 0; r < costAdjMatrix.length; r++) {
            for (int c = 0; c < costAdjMatrix[r].length; c++) {
                int edgeWeight = costAdjMatrix[r][c];
                double funcGenR = functionalities[r];
                double funcGenC = functionalities[c];

                int totalCost = (int) (edgeWeight / (funcGenR * funcGenC));
                costAdjMatrix[r][c] = totalCost;
            }
        }

        // Dijkstra's Algorithm
        int[] minCost = new int[numGenerators];
        boolean[] dijkstraSet = new boolean[numGenerators];

        for (int i = 0; i < minCost.length; i++) {
            if (i == 0) {
                minCost[i] = 0;
            } else {
                minCost[i] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < numGenerators - 1; i++) {
            int currentSource = 0;

            // find the first unprocessed node and consider that node to be 
            // the node with MINIMUM cost from node 0
            for (int j = 0; j < dijkstraSet.length; j++) {
                if (dijkstraSet[j] != true) {
                    currentSource = j;
                    break;
                }
            }

            // find the actual node with MIMIMUM cost from node 0
            for (int j = currentSource + 1; j < minCost.length; j++) {
                if (dijkstraSet[j] != true && minCost[j] < minCost[currentSource]) {
                    currentSource = j;
                }
            }

            dijkstraSet[currentSource] = true;

            // relax all the nodes adjacent to the node we are visiting (currentSource)
            for (int c = 0; c < costAdjMatrix[currentSource].length; c++) {
                // ensures we only relax neighbor nodes
                if (costAdjMatrix[currentSource][c] != 0) {
                    // now that a neighbor index has been located, proceed with Dijkstra's algorithm
                    // totalCost is the totalCost from 0 to the neighbor node
                    int totalCost = minCost[currentSource] + costAdjMatrix[currentSource][c];
                    if ( (dijkstraSet[c] != true) && (minCost[currentSource] != Integer.MAX_VALUE) && (totalCost < minCost[c]) ) {
                        minCost[c] = minCost[currentSource] + costAdjMatrix[currentSource][c];
                    }
                }
            }
        }
        // end of Dijkstra's Algorithm

        int minCostFromEarth = minCost[minCost.length - 1];
        StdOut.print(minCostFromEarth);
    }
}
