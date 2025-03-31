import DNA.DnaAssembler;
import DNA.DnaSequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Note about generative AI: The main logic of this solution was obtained from chatGPT.
 * Some alterations were made to better fit the requirements of this course unit.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int nFrags = Integer.parseInt(br.readLine());
        // fragment interval [a,b] => fragments[i][0] = a;  fragments[i][1] = b;
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

        DnaAssembler assembler = new DnaAssembler(fragments, startPos, endPos);
        DnaSequence solution = assembler.getOptimalAssembly();
        System.out.println(solution.getnFrags() + " " + solution.getOverlap());
    }
}
