package DNA;

public class Fragment implements Comparable<Fragment> {
    int minPos;
    int maxPos;

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

    @Override
    public int compareTo(Fragment o) {
        if (this.getMinPos() == o.getMinPos())
            return this.getMaxPos() - o.getMaxPos();
        return this.getMinPos() - o.getMinPos();
    }
}
