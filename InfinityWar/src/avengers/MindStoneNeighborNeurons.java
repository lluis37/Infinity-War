package avengers;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Given a Set of Edges representing Vision's Neural Network, identify all of the 
 * vertices that connect to the Mind Stone. 
 * List the names of these neurons in the output file.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * MindStoneNeighborNeuronsInputFile name is passed through the command line as args[0]
 * Read from the MindStoneNeighborNeuronsInputFile with the format:
 *    1. v (int): number of neurons (vertices in the graph)
 *    2. v lines, each with a String referring to a neuron's name (vertex name)
 *    3. e (int): number of synapses (edges in the graph)
 *    4. e lines, each line refers to an edge, each line has 2 (two) Strings: from to
 * 
 * Step 2:
 * MindStoneNeighborNeuronsOutputFile name is passed through the command line as args[1]
 * Identify the vertices that connect to the Mind Stone vertex. 
 * Output these vertices, one per line, to the output file.
 * 
 * Note 1: The Mind Stone vertex has out degree 0 (zero), meaning that there are 
 * no edges leaving the vertex.
 * 
 * Note 2: If a vertex v connects to the Mind Stone vertex m then the graph has
 * an edge v -> m
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for EVERY neuron (vertex) neighboring the Mind Stone neuron (vertex)
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/MindStoneNeighborNeurons mindstoneneighborneurons.in mindstoneneighborneurons.out
 *
 * @author Yashas Ravi
 * 
 */


public class MindStoneNeighborNeurons {
    
    public static void main (String [] args) {
        
    	if ( args.length < 2 ) {
            StdOut.println("Execute: java MindStoneNeighborNeurons <INput file> <OUTput file>");
            return;
        }
    	
    	// WRITE YOUR CODE HERE
        String mindStoneNeighborNeuronsInput = args[0];
        String mindStoneNeighborNeuronsOutput = args[1];
        StdIn.setFile(mindStoneNeighborNeuronsInput);
        StdOut.setFile(mindStoneNeighborNeuronsOutput);

       int numNeurons = StdIn.readInt();

        // HashMap to associate the input strings (value) with the array indices (key) of our adjacency list
        HashMap<Integer, String> stringMappings = new HashMap<Integer, String>();

        // HashMap to associate the array indices (value) of our adjacency list with the input strings (key)
        HashMap<String, Integer> indexMappings = new HashMap<String, Integer>();

        // adjacency list representation of directed graph
        LinkedList<Integer>[] networkAdjList = new LinkedList[numNeurons];

        // initialize indices of networkAdjList to a new LinkedList
        for (int i = 0; i < networkAdjList.length; i++) {
            networkAdjList[i] = new LinkedList<Integer>();
        }

        // add neuronName(s) to the HashMap stringMappings with key i
        // add the indices i to the HashMap indexMappings with key neuronName(s)
        for (int i = 0; i < numNeurons; i++) {
            String neuronName = StdIn.readString();
            stringMappings.put(i, neuronName);
            indexMappings.put(neuronName, i);
        }

        int e = StdIn.readInt();

        // populate networkAdjList with input edges
        for (int i = 0; i < e; i++) {
            String from = StdIn.readString();
            String to = StdIn.readString();

            int fromIndex = indexMappings.get(from);
            int toIndex = indexMappings.get(to);

            networkAdjList[fromIndex].add(toIndex);
        }

        int mindStoneIndex = 0;
        // finds the index associated with the mindStone in networkAdjList
        for (int i = 0; i < networkAdjList.length; i++) {
            if (networkAdjList[i].size() == 0) {
                mindStoneIndex = i;
                break;
            }
        }

        // finds and prints the vertices that connect to the mindStone one per line
        for (int i = 0; i < networkAdjList.length; i++) {
            if (i == mindStoneIndex) {
                continue;
            }

            if (networkAdjList[i].contains(mindStoneIndex)) {
                String vertexToStone = stringMappings.get(i);
                StdOut.println(vertexToStone);
            }
        }

    }
}
