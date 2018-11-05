import java.util.*;
import java.util.stream.*;
import java.util.stream.Collectors;
import java.util.function.*;
import java.util.Optional;
import java.util.TreeSet;
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
	long mask = 1;
	TreeSet<Integer> verticiesInSet =
		new TreeSet<Integer>();
	
	for(int i = 0; i < n; i++){
		if((x & mask) != 0){
			verticiesInSet.add(i);
		}

		mask = mask * 2;
	}


   	 for(int i : verticiesInSet){
		for(int j: verticiesInSet){
			if(j != i && adjacencyMatrix[i][j] == 1){
				return false;
			}
		}
  	  }

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
