import java.util.Arrays;

public class Dna {
    int fragmentsNumber;
    Fragment[] fragments;
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    int[] solution = {Integer.MAX_VALUE, Integer.MAX_VALUE};


    public Dna ( Fragment[] fragments, int minimum, int maximum ) {
        this.fragments = fragments;
        this.fragmentsNumber = fragments.length;
        Arrays.sort(fragments, new FragmentComparator()); //organiza os fragmentos
        this.min = minimum;
        this.max = maximum;
    }

    public void solveFromBaseFragments() {
        for (int i = 0; i < fragmentsNumber && fragments[i].getMin()==min; i++) {
            int[] sol = new int[]{0,0};
            int[] newSolution = dynamicFunction(i, fragments[i].getMax(), sol);
            if( newSolution[0] < solution[0] || newSolution[0] == solution[0] && newSolution[1] < solution[1]) {
                this.solution = newSolution;
            }
        }
    }

    public int[] dynamicFunction(int i, int maxOfPreviousFrag, int[] sol){
            if (maxOfPreviousFrag == max) {
                int[] newSolution = new int[]{sol[0]+1, sol[1]};
                if (newSolution[0] < this.solution[0] || newSolution[0] == solution[0] && newSolution[1] < solution[1])//falta || tendo em conta overlapps
                    this.solution = newSolution;
            } else {
                for (int k = i+1; k < fragmentsNumber; k++) {
                    if (fragments[k].getMin() <= maxOfPreviousFrag+1) {
                        int maxOfNextFragment = fragments[k].getMax();
                        int overlaps = fragments[i].getMax() - fragments[k].getMin()+1;
                        int[] newSol = new int[]{sol[0] + 1, Math.max(0, overlaps + sol[1])};
                        int[] newSolution = dynamicFunction(k, maxOfNextFragment, newSol);
                        if (newSolution[0] < this.solution[0] || newSolution[0] == solution[0] && newSolution[1] < solution[1])//falta || tendo em conta overlapps
                            this.solution = newSolution;
                    }
                }
            }
       return solution;
    }


    public int[] getSolution(){
        return solution;
    }

}


