# DNA Fragment Assembly


In bioinformatics, genome sequencing involves reconstructing a complete DNA sequence from numerous short fragments (or reads). Due to the limitations of sequencing technology, these fragments may overlap. The goal is to reassemble the genome efficiently by minimizing the number of fragments used and their redundancy.

We are given a set of fragments. Each fragment is represented by an interval of positive integers, which corresponds to a range of consecutive genome positions. The union of all intervals defines the whole set of positions that need to be covered.

The goal is to select a subset of fragments that cover all positions, that is, whose union contains all positions. Among all possible subsets that satisfy this condition, we want one with the fewest possible number of fragments. Furthermore, when there are several solutions that require the minimum number of fragments, we want one that minimizes the overlap, which is the sum of the position repetitions. If a position belongs to *k* intervals, it is repeated *k − 1* times. So, positions that occur only once are not repeated.

## Example

Consider an example with six fragments, whose intervals are defined in the table below. For instance, the last fragment has 4 positions: 20, 21, 22, and 23. In this case, the DNA sequence starts at position 13 and ends at position 58.

| Fragment | Interval  |
|----------|-----------|
| 1        | [13, 30]  |
| 2        | [13, 22]  |
| 3        | [22, 58]  |
| 4        | [31, 45]  |
| 5        | [46, 58]  |
| 6        | [20, 23]  |

The following subsets of fragments cover all positions:

- **Set A = {1, 4, 5}**  
  Has 3 fragments that do not overlap. The overlap of A is zero.

- **Set B = {1, 3}**  
  Has only 2 fragments (making set A a worse alternative). Since positions 22, 23, 24, 25, 26, 27, 28, 29, and 30 occur twice, each one is repeated once. Thus, there are 9×1 repetitions and the overlap of B is 9.

- **Set C = {2, 3}**  
  Also has 2 fragments and its overlap is 1 (because 12 is the only position that belongs to both intervals).

- **Set D = {2, 3, 6}**  
  Has 3 fragments. To compute its overlap, notice that positions 20, 21, and 23 occur twice, whereas position 22 occurs three times. The overlap of D is 5 (5 = 3×1 + 1×2).

The optimal solution is **Set C** because it covers all positions with the fewest possible number of fragments and, with only 2 fragments, the minimum possible overlap is 1.

## Task

Write a program that, given a set of fragments, computes the minimum size of the subsets of fragments that cover all positions, and the minimum overlap of those subsets of minimum size. It is guaranteed that, for the given inputs, the union of all given intervals corresponds to a range of consecutive integers.

### Input

- The first line contains an integer, *F*, which represents the number of fragments.
- Each of the following *F* lines contains two integers separated by a single space, *a<sub>i</sub>* and *b<sub>i</sub>*, indicating that there is a fragment whose interval is [*a<sub>i</sub>*, *b<sub>i</sub>*] (for every *i = 1, 2, …, F*).

#### Constraints

- 1 ≤ *F* ≤ 1,000  
- 1 ≤ *a<sub>i</sub>* ≤ *b<sub>i</sub>* ≤ 10,000

### Output

The output consists of a single line with two integers separated by a space:

- The first number is the minimum size of the subsets of fragments that cover all positions.
- The second number is the minimum overlap of those subsets of minimum size.

