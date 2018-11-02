import java.util.*;
import java.util.stream.*;
import java.util.stream.Collectors;
import java.util.function.*;
import java.util.Optional;
/*
 * Find the smallest dominating set for a graph
 *  
 * On EOS, compile with the following command
 * javac DominatingSet.java Pair.java ComparePais.java
 */

public class DominatingSet {

    private static int n;
    private static int adjacencyMatrix[][];

    /* Return true if the long x encodes
       a subset that is a dominating set
    */
    private static boolean isDominatingSet(long x) {
	int verticesInSet[]   = new int [n];
	int verticesCovered[] = new int [n];
	long mask = 1;
	/* Calculate which vertices belong to the set
	   encoded by the value x
	*/
	for(int i=0;i < n;i++) {
	    if ((x & mask) != 0) {
		verticesInSet[i] = 1;
		verticesCovered[i] = 1;
	    }
	    else {
		verticesInSet[i] = 0;
		verticesCovered[i] = 0;
	    }
	    mask = mask * 2;
	}	
	/* 
	   Check for all nodes if:
	   - They belong to the dominating set
	   - or they are connected to a node in the dominating set
	*/
	for(int i = 0;i < n;i++) {
	    // Check if node i is adjacent
	    // to a node in verticesInSet
	    if (verticesCovered[i] == 0) { 
		for(int j = 0;j < n;j++) {
		    if (i!=j && adjacencyMatrix[i][j] == 1 && verticesInSet[j] == 1) {
			verticesCovered[i] = 1;

			/* if Vertex j is in the Set and the adjacent Vertex i is also in
			 * the Set, then the Set is not independent. Two Vertices in the 
			 * Set cannot be adjacent to each other for the set to be independent.
			 * It still could be dominating, but not Independent.
			 * A Maximal Independent Set is also a dominating set
			 * in the graph. This is because if there was a Vertex that was
			 * not adjacent to any of the verticies in the Maximal Independent
			 * set, then it should be in the Set as seeing adding it wouldn't 
			 * touch any of the other verticies. Therefore, if every vertex has
			 * to be adjacent to at least one vertex of the Maximal Independent Set,
			 * then the MIS is also a Dominating set.*/
			if(verticesInSet[i] == 1){
				/* Checking to see if the adjacent vertex is in the set,
				 * if it is, the set is not Independent. */
				return false;
			}
			break;
		    }
		}
	    }
	}
	// Check if all vertices are covered
	for(int i = 0;i < n;i++) {
	    if (verticesCovered[i] == 0) {
		return false;
	    }
	}
	// 
	// System.out.println("Inside isDominatingSet - argument "+x);
	return true;
    }


    public static void main(String args[]) {
	/* Read the size of the graph - number of vertices
	   and then read the adjacency matrix for the graph
	*/
	Scanner scanner = new Scanner(System.in);
	n = scanner.nextInt();
	adjacencyMatrix = new int [n][n];
	for(int i = 0;i < n;i++) {
	    for(int j = 0;j < n;j++) {
		adjacencyMatrix[i][j] = scanner.nextInt();
	    }
	}
	// Print the adjacency matrix
	/*
	for(int i = 0;i < n;i++) {
	    for(int j = 0;j < n;j++) {
		System.out.print(adjacencyMatrix[i][j]);
	    }
	    System.out.println();
	}
	*/
	long twoToN = 1;
	for(int i = 0;i < n;i++) {
	    twoToN = twoToN * 2;
	}
	// System.out.println("2^n is: "+twoToN);

	Comparator < Pair > comparator = new ComparePairs();

	Optional result = LongStream.range(1,twoToN).parallel().
	    filter(e ->{ return(isDominatingSet(e));}).
	    mapToObj(e -> new Pair(e,n)).
	    collect(Collectors.maxBy(comparator));

	System.out.println("Result is: "+result.toString());

    }
}
