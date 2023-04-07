package ethz.ch.pp.mergeSort;

import ethz.ch.pp.util.ArrayUtils;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MergeSortMulti extends RecursiveTask<int[]> {

    private static final long serialVersionUID = 1531647254971804196L;

    private int[] input;
    private int length;


    public MergeSortMulti(int[] input, int length) {

        this.input = input;
        this.length = length;
    }

    //TODO: implement using ForkJoinPool
    // You should change this class to extend from RecursiveTask or RecursiveAction
    public static int[] sort(int[] input, int numThreads) {
        ForkJoinPool forkJoinPool=new ForkJoinPool(numThreads);
        MergeSortMulti mergeSortMulti=new MergeSortMulti(input, input.length);
        forkJoinPool.invoke(mergeSortMulti);

        return  forkJoinPool.invoke(mergeSortMulti);
    }

    @Override
    protected int[] compute() {
        int[] result = new int[length];

        if (length < 5) {
             result=MergeSortSingle.sort(input);
        } else {
            int middle = (int) (length / 2);
            int[] firsthalf = new int[middle];
            int[] secondhalf = new int[length - middle];
            for (int i = 0; i < middle; i++) firsthalf[i] = input[i];
            int counter=0;
            for (int i = middle; i < input.length; i++) {

                secondhalf[counter] = input[i];
            counter++;}
            MergeSortMulti mergeSortMulti1 = new MergeSortMulti(firsthalf, middle);
            MergeSortMulti mergeSortMulti2 = new MergeSortMulti(secondhalf, length - middle);
            mergeSortMulti1.fork();
            mergeSortMulti2.fork();
            ArrayUtils.merge(mergeSortMulti1.join(), mergeSortMulti2.join(), result);

        }
        return result;
    }
}
