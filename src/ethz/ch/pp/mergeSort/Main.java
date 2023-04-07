package ethz.ch.pp.mergeSort;

import ethz.ch.pp.util.RandomGenerator;

import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
	    RandomGenerator dg = new RandomGenerator();
	    int[] input = dg.randomArray(1024*1024*5);
		//int[] input=new int[]{7,8,3,1,5,13,11,25,2,342,45,77,12044,478567,55,4,9};
	    
		System.out.println("warm-up cache for more precise measurements");
		taskBaseline(input);
		
		//taskBaseline(input);
		for (int numThreads = 2; numThreads < 100; numThreads++) {
			taskParallel(input, numThreads);
		}
	}
	
	public static void taskBaseline(int[] input){
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			MergeSortSingle.sort(input);
		}
		long t1 = System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ") MergeSort Single takes " + ((t1 - t0)/5) + " msec");
		
	}
	
	public static void taskParallel(int[] input, int numThreads){
		long t0 = System.currentTimeMillis();
		int[]ab=new int[8];
		for (int i = 0; i < 5; i++) {
			ab=MergeSortMulti.sort(input, numThreads);


		}
long t1=System.currentTimeMillis();
		System.out.println("For (inputsize=" + input.length + ") MergeSort Multi with " + numThreads + " threads takes " + ((t1 - t0)/5) + " msec");
	}

}

