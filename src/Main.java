import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int F = Integer.parseInt(br.readLine());
        // fragment interval [a,b] => intervals[i][0] = a;  intervals[i][1] = b;
        int[][] intervals = new int[F][2];

        int globalStart = Integer.MAX_VALUE;
        int globalEnd = Integer.MIN_VALUE;
        for (int i = 0; i < F; i++) {
            String[] range = br.readLine().split(" ");
            int a = Integer.parseInt(range[0]);
            int b = Integer.parseInt(range[1]);
            intervals[i] = new int[]{a, b};
            if (globalStart > a)
                globalStart = a;
            if (globalEnd < b)
                globalEnd = b;
        }

        // states[0] represents coverage up to (globalStart - 1)
        // states[k] represents coverage up to (globalStart - 1 + k)
        int n = globalEnd - globalStart + 1; // number of positions to cover
        int statesSize = n + 1;  // states[statesSize - 1] corresponds to globalEnd

        // states[i][0]: number of fragments used; states[i][1]: total extra overlap cost.
        int[][] states = new int[statesSize][2];
        for (int i = 1; i < statesSize; i++)
            states[i] = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};

        states[0] = new int[]{0, 0}; // base state: no fragments, no overlap, covers up to globalStart-1.

        // Let i be the current state index. The corresponding covered position is:
        // p = (globalStart - 1) + i.
        for (int i = 0; i < statesSize; i++) {
            if (states[i][0] == Integer.MAX_VALUE)
                continue;
            int currentPos = i + globalStart - 1;
            int nextPos = currentPos + 1;

            // Try every fragment that can cover the next needed position.
            for (int[] frag : intervals) {
                if (frag[0] <= nextPos && frag[1] >= nextPos) {
                    // The fragment extends coverage up to at least nextPos.
                    // We cap the new coverage to globalEnd.
                    int newCoverage = Math.min(frag[1], globalEnd);
                    int j = newCoverage - globalStart + 1;

                    // Additional overlap: the new fragment re-covers the region from
                    // max(frag[0], globalStart) to currentPos (if any).
                    int additionalOverlap = (i + globalStart - Math.max(frag[0], globalStart));

                    int candidateCount = states[i][0] + 1;
                    int candidateOverlap = states[i][1] + additionalOverlap;

                    // Update states[j] lexicographically: first by count, then by overlap.
                    if (candidateCount < states[j][0] ||
                            (candidateCount == states[j][0] && candidateOverlap < states[j][1])) {
                        states[j][0] = candidateCount;
                        states[j][1] = candidateOverlap;
                    }
                }
            }
        }

        int[] answer = states[statesSize - 1];
        System.out.println(answer[0] + " " + answer[1]);
    }
}
