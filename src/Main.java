import DNA.DnaAssembler;
import DNA.DnaSequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Note about generative AI: We used chatGPT as "inspiration", but the main algorithm was developed by us.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int nFrags = Integer.parseInt(br.readLine());
        DnaSequence[] fragments = new DnaSequence[nFrags];

        // 1st position of the complete sequence
        int startPos = Integer.MAX_VALUE;
        // last position of the complete sequence
        int endPos = Integer.MIN_VALUE;
        for (int i = 0; i < nFrags; i++) {
            String[] range = br.readLine().split(" ");
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);
            fragments[i] = new DnaSequence(start, end);
            startPos = Math.min(start, startPos);
            endPos = Math.max(end, endPos);
        }

        Arrays.sort(fragments);
        DnaAssembler assembler = new DnaAssembler(fragments, startPos, endPos);
        DnaSequence solution = assembler.getAssembledSequences();
        System.out.println(solution.getnFrags() + " " + solution.getOverlap());
    }
}
