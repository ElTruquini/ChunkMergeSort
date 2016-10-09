/*
 * University of Victoria
 * CSC 225 - Fall 2016
 * Assignment 2 - Template for ChunkMergesort
 * Modified by - Daniel Olaya Oct 9th, 2016
 * 
 * This template includes some testing code to help verify the implementation.
 * To interactively provide test inputs, run the program with:
 * 
 *     java ChunkMergesort
 * 
 * To conveniently test the algorithm with a large input, create a text file
 * containing space-separated integer values and run the program with:
 * 
 *     java ChunkMergesort file.txt
 * 
 * where file.txt is replaced by the name of the text file.
 * 
 * Miguel Jimenez
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Do not change the name of the ChunkMergesort class
public final class ChunkMergesort {
	
	/**
	 * Use this class to return two lists.
	 * 
	 * Example of use:
	 * 
	 * Chunks p = new Chunks(S1, S2); // where S1 and S2 are lists of integers
	 */
	public static final class Chunks {
		
		private  List<Integer> left;
		private  List<Integer> right;

		public Chunks(List<Integer> left, List<Integer> right) {
			this.left = left;
			this.right = right;
			
		}
		
		public List<Integer> left() {
			return this.left;
		}
		
		public List<Integer> right() {
			return this.right;
		}

	}

/**
	 * The list containing the integer comparisons in order of occurrence. Use
	 * this list to persist the comparisons; the method report will be invoked
	 * to write a text file containing this information.
	 * 
	 * Example of use (when comparing 1 and 9):
	 * 
	 * Integer[] d = new Integer[2];
	 * d[0] = 1;
	 * d[1] = 9;
	 * this.comparisons.add(d);
	 * 
	 * or just:
	 * 
	 * this.comparisons.add(new Integer[]{1, 9});
	 */
	private final List<Integer[]> comparisons;

	public ChunkMergesort() {
		this.comparisons = new ArrayList<Integer[]>();
	}

	public List<Integer> chunkMergesort(List<Integer> S) {
		int c =1;

		//Finds number of total chunks in array
		for (int i = 1 ; i<S.size() ; i++){
			this.comparisons.add(new Integer[]{S.get(i-1), S.get(i)});
	System.out.println("Printer 2 " + S.get(i-1) + " " +  S.get(i) );
			if (S.get(i-1) < S.get(i)){ //part of same chunk
			}else {	//new chunk
				c++;
			}
		}

		// base case
		if (c == 1){
			//System.out.println("BASE CASE! with " + S + "\n");
			return S;
		}

		//Divides list S into left and right chunk
		Chunks chunky = chunkDivide(S, c);

//System.out.println("+++Calling chunkMergeSort with LEFT " + chunky.left());
		chunky.left = chunkMergesort (chunky.left());
//System.out.println("+++Calling chunkMergeSort with RIGHT " + chunky.right());
		chunky.right = chunkMergesort (chunky.right()) ;
//System.out.println("+++Calling Merge with  " +chunky.left() + " " + chunky.right());

		//Merges right and left chunks 
		chunky = new Chunks ( (merge(chunky.left(), chunky.right())) ,null);

System.out.println("=== FINAL ChunkMergeSort return with " + chunky.left() + " " + chunky.right);
		return chunky.left();
	}

	public Chunks chunkDivide(List<Integer> S, int c) {
//System.out.println(" I am in chunkdivide with " + c + " chunks");
		List<Integer> left = new ArrayList<Integer>();
		List<Integer> right = new ArrayList<Integer>();
		int leftChunkCounter = 0;
		int rightChunkCounter = 0;

		// divides S into two different lists, left and right	
		boolean addingLeft = true;
		
		//Creating left and right
		leftChunkCounter ++;
		left.add(S.get(0));
		for (int i =1 ; i < S.size() ; i++){
			if (addingLeft == true){ //adding to the left
				this.comparisons.add(new Integer[]{S.get(i-1), S.get(i)});
				if (S.get(i-1)<S.get(i)){
					left.add(S.get(i));
				} else {
					right.add(S.get(i));
					addingLeft = false;
					rightChunkCounter ++;
				}
			} else { // adding to the right
				this.comparisons.add(new Integer[]{S.get(i-1), S.get(i)});
				if (S.get(i-1)<S.get(i)){
					right.add(S.get(i));
				} else {
					left.add(S.get(i));
					addingLeft = true;
					leftChunkCounter ++;
				}
			}
		}
		
		Chunks chunkyDivided = new Chunks(left, right);
//System.out.println("===Returning from CHUNKDIVIDE with==== " + chunkyDivided.left +" " + chunkyDivided.right + "\n");

		return chunkyDivided; 		
	}



	public List<Integer> merge(List<Integer> S1, List<Integer> S2) {	//S1 is left
		int left = 0, right = 0; 		// left is the curr open index for left list
										// right is the curr open index for right list
//System.out.println("****Inside Merge: " + S1 + " with " + S2);
//System.out.println("Merging " + chunky.left + " " + chunky.right);


		List<Integer> result = new ArrayList<Integer>();
		while (left < S1.size() && right < S2.size()){ //When both lists have items to add
			this.comparisons.add(new Integer[]{S1.get(left), S2.get(right)});
			if (S1.get(left) < S2.get(right)){ // left is smaller and gets added
				result.add(S1.get(left));
				left++;
			} else { //left is larger, right gets added
				result.add(S2.get(right));
				right++;
			}
		} 
		if (left < S1.size()){ //add reminder of left in result array
			while (left < S1.size()){
				result.add(S1.get(left));
				left++;
			}

		}else { //add reminder of right in result array
			while (right < S2.size()){
				result.add(S2.get(right));
				right++;
			}	
		}
//System.out.println("===Returning from MERGE WITH ======" + result + "\n");
		return result;

	}

	/**
	 * Writes a text file containing all the integer comparisons in order of
	 * ocurrence.
	 * 
	 * @throws IOException
	 *             If an I/O error occurs
	 */
	public void report() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("comparisons.txt", false));
		for (Integer[] data : this.comparisons)
			writer.append(data[0] + " " + data[1] + "\n");
		writer.close();
	}

	/**
	 * Contains code to test the chunkMergesort method. Nothing in this method
	 * will be marked. You are free to change the provided code to test your
	 * implementation, but only the contents of the methods above will be
	 * considered during marking.
	 */
	public static void main(String[] args) {
		Scanner s;
		if (args.length > 0) {
			try {
				s = new Scanner(new File(args[0]));
			} catch (java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n", args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n", args[0]);
		} else {
			s = new Scanner(System.in);
			System.out.printf("Enter a list of integers:\n");
		}
		List<Integer> inputList = new ArrayList<Integer>();

		int v;
		while (s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputList.add(v);

		s.close();
		System.out.printf("Read %d values.\n", inputList.size());

		long startTime = System.currentTimeMillis();

		ChunkMergesort mergesort = new ChunkMergesort();
		/* List<Integer> sorted = */ mergesort.chunkMergesort(inputList);

		long endTime = System.currentTimeMillis();
		double totalTimeSeconds = (endTime - startTime) / 1000.0;

		System.out.printf("Total Time (seconds): %.2f\n", totalTimeSeconds);
		
		try {
			mergesort.report();
			System.out.println("Report written to comparisons.txt");
		} catch (IOException e) {
			System.out.println("Unable to write file comparisons.txt");
			return;
		}
	}

}