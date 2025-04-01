package DNA;

public class DnaSequence implements Comparable<DnaSequence> {
    private final int start;
    private final int end;
    private final int nFrags;
    private final int overlap;

    public DnaSequence(int start, int end) {
        this.start = start;
        this.end = end;
        this.nFrags = 1;
        this.overlap = 0;
    }

    public DnaSequence(int start, int end, int nFrags, int overlap) {
        this.start = start;
        this.end = end;
        this.nFrags = nFrags;
        this.overlap = overlap;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getnFrags() {
        return nFrags;
    }

    public int getOverlap() {
        return overlap;
    }

    @Override
    public int compareTo(DnaSequence o) {
        if (this.getStart() == o.getStart())
            return this.getEnd() - o.getEnd();
        return this.getStart() - o.getStart();
    }
}
