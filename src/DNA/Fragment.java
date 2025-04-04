package DNA;

/**
 * Class that represents a DNA fragment
 */
public class Fragment implements Comparable<Fragment> {
    int minPos; // 1st position of the fragment
    int maxPos; // last position of the fragment

    public Fragment(int min, int max) {
        this.minPos = min;
        this.maxPos = max;
    }

    public int getMaxPos() {
        return maxPos;
    }

    public int getMinPos() {
        return minPos;
    }

    // We sort the fragments from the lowest start position of a fragment to the highest,
    // in case there's two or more fragments with the same start position, we sort from the lowest end position to the highest
    @Override
    public int compareTo(Fragment o) {
        if (this.getMinPos() == o.getMinPos())
            return this.getMaxPos() - o.getMaxPos();
        return this.getMinPos() - o.getMinPos();
    }
}
