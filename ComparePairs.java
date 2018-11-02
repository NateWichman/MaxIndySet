
import java.util.*;
    public class ComparePairs implements Comparator <Pair> {
	public int compare(Pair p1,Pair p2) {
	    return p1.getNumberOfBits() - p2.getNumberOfBits();
	}

	public boolean equals(Object obj) {
	    return this == obj;
	}

    }
