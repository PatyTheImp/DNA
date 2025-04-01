package DNA;

public class DnaAssembler {

    private final DnaSequence[] sortedFragments;
    private final DnaSequence[] assembledSequences;
    private int bestIndex;
    private final int startPos;
    private final int endPos;

    public DnaAssembler(DnaSequence[] sortedFragments, int startPos, int endPos) {
        this.sortedFragments = sortedFragments;
        this.startPos = startPos;
        this.endPos = endPos;
        this.bestIndex = -1;
        this.assembledSequences = new DnaSequence[this.sortedFragments.length];
        computeOptimalAssembly();
    }

    public DnaSequence getAssembledSequences() {
        return this.assembledSequences[this.bestIndex];
    }

    private void computeOptimalAssembly() {
        int maxI = -1;
        for (DnaSequence fragment : sortedFragments) {
            // Base case
            if (fragment.getStart() == startPos) {
                assembledSequences[++maxI] = fragment;
                if (fragment.getEnd() == endPos) {
                    bestIndex = maxI;
                    return;
                }
            }
            else {
                DnaSequence bestCurSeq = null;
                int subI = -1;
                for (int i = 0; i <= maxI; i++) {
                    if (assembledSequences[i].getEnd() == fragment.getEnd())
                        subI = i;
                    else if (seqsCanBeAssembled(assembledSequences[i], fragment)) {
                        int nFrags = assembledSequences[i].getnFrags() + 1;
                        if (bestCurSeq == null || nFrags < bestCurSeq.getnFrags()) {
                            bestCurSeq = new DnaSequence(startPos, fragment.getEnd(),
                                    nFrags, calcOverlap(assembledSequences[i], fragment));
                        } else if (nFrags == bestCurSeq.getnFrags()) {
                            int overlap = calcOverlap(assembledSequences[i], fragment);
                            if (overlap < bestCurSeq.getOverlap()) {
                                bestCurSeq = new DnaSequence(startPos, fragment.getEnd(),
                                        nFrags, overlap);
                            }
                        }
                    }
                }
                if (bestCurSeq != null) {
                    if (subI > -1 && seq1IsOptimal(bestCurSeq, assembledSequences[subI]))
                        assembledSequences[subI] = bestCurSeq;
                    else if (subI == -1) {
                        assembledSequences[++maxI] = bestCurSeq;
                        if (foundNewBest(bestCurSeq))
                            bestIndex = maxI;
                    }
                }
            }
        }
    }

    private boolean seqsCanBeAssembled(DnaSequence seq1, DnaSequence seq2) {
        return (seq2.getStart() - 1) <= seq1.getEnd() && seq1.getEnd() < seq2.getEnd();
    }

    private int calcOverlap(DnaSequence seq1, DnaSequence seq2) {
        return seq1.getOverlap() + seq1.getEnd() - seq2.getStart() + 1;
    }

    private boolean seq1IsOptimal(DnaSequence seq1, DnaSequence seq2) {
        if (seq1 == null)
            return false;
        if (seq2 == null)
            return true;
        return seq1.getnFrags() < seq2.getnFrags()
                || (seq1.getnFrags() == seq2.getnFrags() && seq1.getOverlap() < seq2.getOverlap());
    }

    private boolean foundNewBest(DnaSequence seq) {
        return seq.getEnd() == endPos
                && (bestIndex == -1 ||
                (bestIndex > -1 && seq1IsOptimal(seq, assembledSequences[bestIndex])));
    }
}
