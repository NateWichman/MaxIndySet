public class Pair {
	private long id;
	private int numberOfBits;
	private int n;
	public Pair(long id,int n) {
	    this.id = id;
	    numberOfBits = 0;
	    long mask = 1;
	    for(int i=0;i < n;i++) {
		if ((id & mask) != 0) {
		    numberOfBits++;
		}
		mask = mask * 2;
	    }
	}

	public int getNumberOfBits() {
	    return numberOfBits;
	}

	public String toString() {
	    return id+" contains "+numberOfBits+" bits.";
	}
    }