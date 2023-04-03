package avengers;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
*/

public class PredictThanosSnap {
	 
    public static void main (String[] args) {
 
        if ( args.length < 2 ) {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }
        
    	// WRITE YOUR CODE HERE
        String predictThanosSnapInput = args[0];
        String predictThanosSnapOutput = args[1];
        StdIn.setFile(predictThanosSnapInput);
        StdOut.setFile(predictThanosSnapOutput);

        long seed = StdIn.readLong();
        int numPeople = StdIn.readInt();

        int[][] networkAdjMatrix = new int[numPeople][numPeople];
        // populate networkAdjMatrix using input file
        for (int r = 0; r < numPeople; r++) {
            for (int c = 0; c < numPeople; c++) {
                networkAdjMatrix[r][c] = StdIn.readInt();
            }
        }

        // keeps track of the vertices that have been deleted
        // (will be used later to check if graph is connected)
        boolean[] deleted = new boolean[numPeople];

        StdRandom.setSeed(seed);
        for (int v = 0; v < numPeople; v++) {
            if (StdRandom.uniform() <= 0.5) {
                deleted[v] = true;

                // deletes the vertex
                for (int r = 0; r < networkAdjMatrix.length; r++) {
                    // if the row being looked at is indicative of the vertex being deleted,
                    // remove all edges that come from the vertex being removed;
                    // else remove the edge going to the vertex being deleted from the row that is being looked at
                    if (r == v) {
                        for (int c = 0; c < networkAdjMatrix[r].length; c++) {
                            networkAdjMatrix[r][c] = 0;
                        }
                    } else {
                        networkAdjMatrix[r][v] = 0;
                    }
                }
            }
        }

        // find the first vertex that has not been deleted to use as a source for BFS
        int notDeletedVertex = 0;
        for (int i = 0; i < deleted.length; i++) {
            if (!deleted[i]) {
                notDeletedVertex = i;
                break;
            }
        }

        // perform BFS to find all vertices connected to the first vertex that has not been deleted
        boolean[] visited = new boolean[numPeople];
        Queue<Integer> bfsQueue = new LinkedList<Integer>();

        bfsQueue.add(notDeletedVertex);
        visited[notDeletedVertex] = true;
        while (bfsQueue.peek() != null) {
            int vFromQ = bfsQueue.remove();
            for (int c = 0; c < networkAdjMatrix[vFromQ].length; c++) {
                if (networkAdjMatrix[vFromQ][c] != 0 && !visited[c]) {
                    bfsQueue.add(c);
                    visited[c] = true;
                }
            }
        }
        // end of BFS

        // find out if there exists a vertex that has not been deleted and was not connected
        // to notDeletedVertex
        // (if there exists such a vertex, then the graph is not connected; otherwise the graph is connected)
        boolean isConnected = true;
        for (int v = 0; v < numPeople; v++) {
            if (!deleted[v] && !visited[v]) {
                isConnected = false;
                break;
            }
        }

        StdOut.print(isConnected);
    }
}
