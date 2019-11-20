package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Michael Chang
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        int count = 0;
        for (int[] array : edges()) {
            if (array[1] == v) {
                count += 1;
            }
        }
        return count;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int[] array : edges()) {
            if (array[1] == v) {
                result.add(array[0]);
            }
        }
        return Iteration.iteration(result);
    }
}
