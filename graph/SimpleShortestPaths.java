package graph;
import java.util.ArrayList;
/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Michael Chang
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        _weightList = new ArrayList<>();
        _predecessorList = new ArrayList<>();
        for (int i = 0; i <= G.maxVertex(); i += 1) {
            _weightList.add(null);
            _predecessorList.add(null);
        }
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        if (_G.contains(v) && _weightList.get(v) != null) {
            return _weightList.get(v);
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    protected void setWeight(int v, double w) {
        assert _G.contains(v);
        _weightList.set(v, w);
    }

    @Override
    public int getPredecessor(int v) {
        if (_G.contains(v) && _predecessorList.get(v) != null) {
            return _predecessorList.get(v);
        } else {
            return 0;
        }
    }

    @Override
    protected void setPredecessor(int v, int u) {
        _predecessorList.set(v, u);
    }

    /** List where index is vertex and value
     *  is their corresponding weight. */
    private ArrayList<Double> _weightList;
    /** List where index is vertex and value
     *  is their corresponding predecessor. */
    private ArrayList<Integer> _predecessorList;

}
