import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;

        int fragmentsNumber = Integer.parseInt(in.readLine());
        Fragment[] fragments = new Fragment[fragmentsNumber];

        for (int i = 0; i < fragmentsNumber; i++) {
            String[] fragment = in.readLine().split(" ");

            int first = Integer.parseInt(fragment[0]);
            int second = Integer.parseInt(fragment[1]);
            fragments[i] = new Fragment(first, second);

            minPos = Math.min(minPos, fragments[i].getMin());
            maxPos = Math.max(maxPos, fragments[i].getMax());
        }

        newDNA dna = new newDNA(fragments, minPos, maxPos);
        dna.solveFromBaseFragments();
        int[] sol = dna.getSolution();
        System.out.println(sol[0] + " " + sol[1]);
        in.close();

    }
}