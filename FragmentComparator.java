import java.util.Comparator;

public class FragmentComparator implements Comparator<Fragment> {
    @Override
    public int compare(Fragment a, Fragment b) {
        if (a.getMin() != b.getMin()) {
            return Integer.compare(a.getMin(), b.getMin());
        } else {
            return Integer.compare(a.getMax(), b.getMax());
        }
    }

}
















