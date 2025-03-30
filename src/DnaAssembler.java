public class DnaAssembler {

    private final int[][] fragments;
    private final int offset;
    private final int[][] optimalAssembly;
    private final int nPos;

    public DnaAssembler(int[][] fragments, int startPos, int endPos) {
        this.fragments = fragments;
        this.offset = startPos - 1;
        this.nPos = endPos - offset;

        // Has nPos+1 positions because the 1st position is a dummy position corresponding to the base case
        // optimalAssembly[i][0] = min number of fragments to cover all the positions from startPos to i
        // optimalAssembly[i][1] = min overlap of those fragments
        this.optimalAssembly = new int[this.nPos+1][2];
        computeOptimalAssembly();
    }

    public int[] getOptimalAssembly() {
        return this.optimalAssembly[this.nPos];
    }

    /**
     * Fills the optimalAssembly matrix so that the last position is the optimal assembly that covers all positions
     */
    private void computeOptimalAssembly() {
        for (int i = 0; i < nPos; i++) {
            // We go through all positions that are covered by at least the end position of one fragment
            // Always go to the 1st position (base case). No need to go to the last.
            if (i == 0 || optimalAssembly[i][0] != 0) {
                // number of fragments needed to cover until this position
                int nFrags = optimalAssembly[i][0] + 1;
                // Let's search for fragments that can be assembled from this position i
                for (int[] frag : fragments) {
                    int a = frag[0] - offset;
                    int b = frag[1] - offset;
                    if ((a-1 <= i && b > i) && // 1st we check for a valid fragment
                            (optimalAssembly[b][0] == 0 || optimalAssembly[b][0] >= nFrags)) { // then we check if the position is not covered or if the nFrag is not optimal
                        int overlap = optimalAssembly[i][1] + i - a + 1;
                        if (optimalAssembly[b][0] == 0 || optimalAssembly[b][0] > nFrags ||
                                (optimalAssembly[b][0] == nFrags && optimalAssembly[b][1] > overlap)) {
                            optimalAssembly[b][0] = nFrags;
                            optimalAssembly[b][1] = overlap;
                        }
                    }
                }
            }
        }
    }
}
