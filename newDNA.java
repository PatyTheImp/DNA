import java.util.Arrays;

public class newDNA {
    int fragmentsNumber;
    Fragment[] fragments;
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    int[] solution = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    int[][] table;


    public newDNA ( Fragment[] fragments, int minimum, int maximum ) {
        this.fragments = fragments;
        this.fragmentsNumber = fragments.length;
        Arrays.sort(fragments, new FragmentComparator()); //organiza os fragmentos
        this.min = minimum;
        this.max = maximum;
        this.table = new int[fragmentsNumber][2];
        for (int i = 0; i < fragmentsNumber; i++) {
            table[i][0] = Integer.MAX_VALUE;
            table[i][1] = Integer.MAX_VALUE;
        }
    }

    public void solveFromBaseFragments() {
        for (int i = 0; i < fragmentsNumber; i++) {
            if (fragments[i].getMin() == min) {
                table[i][0] = 1;
                table[i][1] = 0;
            }
        }
        dynamicFunction();
    }

    private void dynamicFunction() {
        for (int i = 0; i < fragmentsNumber; i++) {
            if (table[i][0] != Integer.MAX_VALUE) { // Condição para pular fragmentos
                for (int k = i + 1; k < fragmentsNumber; k++) {
                    if (fragments[k].getMin() > fragments[i].getMax() + 1)
                        break; // Sai do loop se há gap
                    int overlaps = Math.max(0, fragments[i].getMax() - fragments[k].getMin() + 1);
                    int newFrags = table[i][0] + 1;
                    int newOverlaps = table[i][1] + overlaps;
                    if (newFrags < table[k][0] || (newFrags == table[k][0] && newOverlaps < table[k][1])) {
                        table[k][0] = newFrags;
                        table[k][1] = newOverlaps;
                    }
                }
            }
        }
        for (int i = 0; i < fragmentsNumber; i++) {
            if (fragments[i].getMax() >= max && (table[i][0] < solution[0] || (table[i][0] == solution[0] && table[i][1] < solution[1]))) {
                solution = table[i];
            }
        }
    }

    public int[] getSolution() {
        return solution;
    }

}
