package graph;

/* See restrictions in Graph.java. */

import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

/** The shortest paths through an edge-weighted graph.
 *  By overriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Michael Chang
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        comp = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (getWeight(o1) + estimatedDistance(o1)
                        < getWeight(o2) + estimatedDistance(o2)) {
                    return -1;
                } else if (getWeight(o1) + estimatedDistance(o1)
                        > getWeight(o2) + estimatedDistance(o2)) {
                    return 1;
                } else {
                    return Integer.compare(o1, o2);
                }
            }
        };
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        for (int i = 1; i <= _G.maxVertex(); i += 1) {
            setWeight(i, Double.POSITIVE_INFINITY);
        }
        setWeight(_source, 0);
        AStarSearch search = new AStarSearch(_G);
        search.traverse(_source);
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        ArrayList<Integer> result = new ArrayList<>();
        if (_dest != 0 && v != _dest) {
            return null;
        } else {
            int m = v;
            while (m != _source) {
                result.add(m);
                m = getPredecessor(m);
            }
            result.add(_source);
            Collections.reverse(result);
            return result;
        }
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** Comparator for A* Search. */
    private Comparator<Integer> comp;

    /** An implementation of A* search using the Traversal class. */
    class AStarSearch extends Traversal {

        /** Constructor for Traversal using A*.
         * @param G graph that is searched. */
        AStarSearch(Graph G) {
            super(G, new TreeSetQ(comp));
        }

        @Override
        protected boolean visit(int v) {
            if (v == _dest) {
                return false;
            }
            for (int succ : _G.successors(v)) {
                double weight1 = getWeight(v) + getWeight(v, succ);
                double weight2 = getWeight(succ);
                if (weight1 < weight2) {
                    setWeight(succ, weight1);
                    if (_fringe.contains(((Object) succ))) {
                        _fringe.remove(succ);
                        _fringe.add(succ);
                    }
                    setPredecessor(succ, v);
                }
            }
            return true;
        }
    }
}
