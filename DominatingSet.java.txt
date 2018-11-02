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
	    collect(Collectors.minBy(comparator));

	System.out.println("Result is: "+result.toString());

    }
}