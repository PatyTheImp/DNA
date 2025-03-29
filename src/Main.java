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
        int[][] fragments = new int[nFrags][2];

        // 1st position of the complete sequence
        int startPos = Integer.MAX_VALUE;
        // last position of the complete sequence
        int endPos = Integer.MIN_VALUE;
        for (int i = 0; i < nFrags; i++) {
            String[] range = br.readLine().split(" ");
            fragments[i][0] = Integer.parseInt(range[0]);
            fragments[i][1] = Integer.parseInt(range[1]);
            startPos = Math.min(fragments[i][0], startPos);
            endPos = Math.max(fragments[i][1], endPos);
        }

        DnaAssembler assembler = new DnaAssembler(fragments, startPos, endPos);
        int[] solution = assembler.getOptimalAssembly();
        System.out.println(solution[0] + " " + solution[1]);
    }
}
