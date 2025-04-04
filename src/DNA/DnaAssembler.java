package DNA;

/**
 * Class that assembles the DNA fragments into a sequence that covers all positions and
 * has optimal number of fragments and optimal overlap.
 * The main logic of the algorithm is here.
 */
public class DnaAssembler {
    int fragmentCount; // number of fragments
    Fragment[] fragments;
    int minPos;
    int maxPos;
    int[] solution;

    /**
     * Constructor
     * @param sortedFragments - DNA fragments already sorted
     * @param minimum - start position of the total sequence
     * @param maximum - end position of the total sequence
     */
    public DnaAssembler(Fragment[] sortedFragments, int minimum, int maximum ) {
        this.fragments = sortedFragments;
        this.fragmentCount = sortedFragments.length;
        this.minPos = minimum;
        this.maxPos = maximum;
        this.solution = new int[2];

        this.assembleFragments();
    }

    /**
     * Dynamic programming algorithm
     */
    private void assembleFragments() {
        // We store the optimal states of a DNA sequence that start at minPos and end at fragment i last position
        int[][] optimalStates = new int[fragmentCount][2];

        for (int i = 0; i < fragmentCount; i++) {
            // Base cases
            if (fragments[i].getMinPos() == minPos) {
                // We found a fragment that starts in the minPos and ends in the maxPos
                if (fragments[i].getMaxPos() == maxPos) {
                    solution[0] = 1;
                    solution[1] = 0;
                    return;
                }
                optimalStates[i][0] = 1;
                optimalStates[i][1] = 0;
            }

            if (optimalStates[i][0] == 0)  // skip empty position
                continue;

            // We have to check if fragment i can be assembled with the fragments from fragment i+1 to the last one
            for (int k = i + 1; k < fragmentCount; k++) {
                if (fragments[k].getMinPos() > fragments[i].getMaxPos() + 1)
                    break; // there's a gap between fragment i and fragment k (since they are sorted, we can break)
                int nFrags = optimalStates[i][0] + 1;
                if (optimalStates[k][0] != 0 && nFrags > optimalStates[k][0])
                    continue; // number of fragments is worse
                int overlap = optimalStates[i][1] + fragments[i].getMaxPos() - fragments[k].getMinPos() + 1;
                // if optimalStates[k] is empty or has worst state
                if (optimalStates[k][0] == 0 || (nFrags == optimalStates[k][0] && overlap < optimalStates[k][1])) {
                    optimalStates[k][0] = nFrags;
                    optimalStates[k][1] = overlap;
                    // if we find a possible solution, check if is better than the current one
                    if (fragments[k].getMaxPos() == maxPos
                            && (solution[0] == 0 // no solution yet
                            || (nFrags < solution[0]
                            || (nFrags == solution[0]
                            && overlap < solution[1])))) {
                        solution[0] = nFrags;
                        solution[1] = overlap;
                    }
                }
            }

        }
    }

    /**
     * The optimal fragments number and overlap for the sequence that cover all positions
     * @return solution[0] = optimal fragments number and solution[1] = optimal overlap
     */
    public int[] getSolution() {
        return solution;
    }

}
