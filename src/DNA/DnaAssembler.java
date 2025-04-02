package DNA;

public class DnaAssembler {
    int fragmentCount;
    Fragment[] fragments;
    int minPos;
    int maxPos;
    int[] solution;
    int[][] optimalStates;


    public DnaAssembler(Fragment[] sortedFragments, int minimum, int maximum ) {
        this.fragments = sortedFragments;
        this.fragmentCount = sortedFragments.length;
        this.minPos = minimum;
        this.maxPos = maximum;
        this.solution = new int[2];
        this.optimalStates = new int[fragmentCount][2];
        this.assembleFragments();
    }

    private void assembleFragments() {
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

            // General cases
            if (optimalStates[i][0] != 0) { // skip empty position
                for (int k = i + 1; k < fragmentCount; k++) {
                    if (fragments[k].getMinPos() > fragments[i].getMaxPos() + 1)
                        break; // there's a gap between fragment i and fragment k
                    int nFrags = optimalStates[i][0] + 1;
                    if (optimalStates[k][0] != 0 && nFrags > optimalStates[k][0])
                        break; // number of fragments is worse
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
    }

    public int[] getSolution() {
        return solution;
    }

}
