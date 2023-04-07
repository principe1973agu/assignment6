package ethz.ch.pp.seq;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class LongestCommonSequenceMulti extends RecursiveTask<Sequence> {

    private static final long serialVersionUID = 4179716026313995745L;
    private int[] input;
    private int low;
    private int high;



    private LongestCommonSequenceMulti(int[] input, int low, int high) {
        this.input = input;
        this.low = low;
        this.high = high;

    }

    public static Sequence longestCommonSequence(int[] input, int numThreads) {
        // TODO Implement

        ForkJoinPool forkJoinPool = new ForkJoinPool(numThreads);
        LongestCommonSequenceMulti longestCommonSequenceMulti=new LongestCommonSequenceMulti(input,0,input.length-1);

        return forkJoinPool.invoke(longestCommonSequenceMulti);
    }

    @Override
    protected Sequence compute() {
        // TODO Implement
    Sequence sequence=new Sequence(0,0);
    int length=high-low;
        if ((length) < 3) {
            if(input[high]==input[low])
      sequence= new Sequence(low,high);

        } else {

            int middle = low + (high - low) / 2 ;
            int newmiddle = middle;

            //To make sure that a common sequence in not split in two halves
            if (input[middle] == input[middle + 1]) {
                newmiddle++;
               // System.out.println("line 53 "+sequence);
                for (int i = newmiddle; i < high; i++) {

                    if (!(input[i] == input[i + 1])) break;
                    newmiddle++;
                    //case where the longest sequence end with the last element
                    if (newmiddle == high) {
                        newmiddle = middle;
                        //make the two halves from the right of the original middle
                        for (int a = middle; a > low; a--) {

                            if (!(input[a] == input[a - 1])){ break;}
                            newmiddle--;

                            if (newmiddle == low) {


                              return new Sequence(low,high);


                            }


                        }

                    }
                }
            }
            LongestCommonSequenceMulti longec1 = new LongestCommonSequenceMulti(input, low, newmiddle);
            LongestCommonSequenceMulti longec2 = new LongestCommonSequenceMulti(input, newmiddle+1, high);
            longec1.fork();
            longec2.fork();
            Sequence sequence1 = longec1.join();
            Sequence sequence2 = longec2.join();


            Sequence finalsequence = new Sequence(0,0);
            if ((sequence1.endIndex - sequence1.startIndex) == (sequence2.endIndex - sequence2.startIndex)) {
                if(sequence1.startIndex<sequence2.startIndex){
                finalsequence = sequence1;}else {finalsequence=sequence2;}
            } else if ((sequence1.endIndex - sequence1.startIndex) > (sequence2.endIndex - sequence2.startIndex)){
                finalsequence = sequence1;
            }else {finalsequence=sequence2;}


            sequence= finalsequence;
        }


   return sequence; }
}

