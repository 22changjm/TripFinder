package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author Michael Chang
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {
        return degree(v);
    }

    @Override
    public int outDegree(int v) {
        int count = 0;
        for (int[] array: edges()) {
            if (array[0] == v || array[1] == v) {
                count += 1;
            }
        }
        return count;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        ArrayList<Integer> list = new ArrayList<>();
        Iteration<int[]> edges = edges();
        for (int[] array: edges) {
            if (array[1] == v) {
                list.add(array[0]);
            } else if (array[0] == v) {
                list.add(array[1]);
            }
        }
        return Iteration.iteration(list);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        return predecessors(v);
    }

    @Override
    public boolean contains(int u, int v) {
        return super.contains(u, v) || super.contains(v, u);
    }

    @Override
    protected int edgeId(int u, int v) {
        if (super.edgeId(u, v) != 0) {
            return super.edgeId(u, v);
        } else {
            return super.edgeId(v, u);
        }
    }

    @Override
    public int add(int u, int v) {
        if (!contains(u, v)) {
            super.add(u, v);
        }
        return edgeId(u, v);
    }

    @Override
    public void remove(int u, int v) {
        if (super.contains(u, v)) {
            super.remove(u, v);
        } else if (super.contains(v, u)) {
            super.remove(v, u);
        }
    }
}
