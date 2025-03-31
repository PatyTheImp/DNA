package DNA;

import java.util.Arrays;

public class DnaAssembler {

    private final DnaSequence[] fragments;
    private final int offset;
    private final DnaSequence[] optimalAssembly;
    private final int nPos;

    public DnaAssembler(DnaSequence[] fragments, int startPos, int endPos) {
        Arrays.sort(fragments);
        this.fragments = fragments;
        this.offset = startPos - 1;
        this.nPos = endPos - offset;

        // Has nPos+1 positions because the 1st position is a dummy position corresponding to the base case
        // optimalAssembly[i][0] = min number of fragments to cover all the positions from startPos to i
        // optimalAssembly[i][1] = min overlap of those fragments
        this.optimalAssembly = new DnaSequence[this.fragments.length + 1];
        // Base case "dummy" position
        optimalAssembly[0] = new DnaSequence(startPos, startPos - 1, 0, 0);
        computeOptimalAssembly();
    }

    public DnaSequence getOptimalAssembly() {
        return this.optimalAssembly[this.nPos];
    }

    /**
     * Fills the optimalAssembly matrix so that the last position is the optimal assembly that covers all positions
     */
    private void computeOptimalAssembly() {
        for (int i = 0; i < optimalAssembly.length - 1; i++) {
            // We go through all positions that are covered by at least the end position of one fragment
            // Always go to the 1st position (base case). No need to go to the last.
            if (i == 0 || optimalAssembly[i] != null) {
                // number of fragments needed to cover until this position
                int nFrags = optimalAssembly[i].getnFrags() + 1;
                // Let's search for fragments that can be assembled from this position i
                for (DnaSequence frag : fragments) {
                    int a = frag.getStart();
                    int b = frag.getEnd();
                    int curEnd = optimalAssembly[i].getEnd();
                    if ((a-1 <= curEnd && b > curEnd) && // 1st we check for a valid fragment
                            (optimalAssembly[b] == null || optimalAssembly[b].getnFrags() >= nFrags)) { // then we check if the position is not covered or if the nFrag is not optimal
                        /*int overlap = optimalAssembly[i][1] + curEnd - a + 1;
                        if (optimalAssembly[b][0] == 0 || optimalAssembly[b][0] > nFrags ||
                                (optimalAssembly[b][0] == nFrags && optimalAssembly[b][1] > overlap)) {
                            optimalAssembly[b][0] = nFrags;
                            optimalAssembly[b][1] = overlap;
                        }*/
                    }
                }
            }
        }
    }
}
