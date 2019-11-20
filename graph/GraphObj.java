package graph;

import java.util.ArrayList;
import javafx.util.Pair;
import java.util.PriorityQueue;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Michael Chang
 */
abstract class GraphObj extends Graph {

    /** The number of vertices in a Graph object. */
    private int _numVertices;

    /** The number of edges in a Graph object. */
    private int _numEdges;

    /** Stores whether two vertices have an edge between them. */
    private ArrayList<ArrayList<Integer>> _graph;

    /** The last index of a vertex in _graph. */
    private int _maxVertex;

    /** Vertex indices that were removed from _graph. */
    private PriorityQueue<Integer> _removed;

    /** ArrayList of all edges in a graph. */
    private ArrayList<Pair<Integer, Integer>> _edgeList;

    /** List of all Pairs. */
    private ArrayList<Pair<Integer, Integer>> _pairList;

    /** List used for edgeID where ID is the element index of that pair. */
    private ArrayList<Pair<Integer, Integer>> _idList;



    /** A new, empty Graph. */
    GraphObj() {
        _numVertices = 0;
        _numEdges = 0;
        _maxVertex = 0;
        _graph = new ArrayList<>();
        _graph.add(null);
        _removed = new PriorityQueue<>();
        _edgeList = new ArrayList<>();
        _pairList = new ArrayList<>();
        _idList = new ArrayList<>();
        _idList.add(null);
    }

    @Override
    public int vertexSize() {
        return _numVertices;
    }

    @Override
    public int maxVertex() {
        return _maxVertex;
    }

    @Override
    public int edgeSize() {
        return _numEdges;
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (contains(v)) {
            return _graph.get(v).size();
        } else {
            return 0;
        }
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return 0 < u && u <= maxVertex() && _graph.get(u) != null;
    }

    @Override
    public boolean contains(int u, int v) {
        if (!contains(u)) {
            return false;
        } else if (!contains(v)) {
            return false;
        } else {
            return _graph.get(u).contains(v);
        }
    }

    @Override
    public int add() {
        _numVertices += 1;
        _maxVertex = Math.max(_numVertices, _maxVertex);
        if (_removed.isEmpty()) {
            _graph.add(new ArrayList<>());
            return _numVertices;
        } else {
            _graph.set(_removed.peek(), new ArrayList<>());
            return _removed.remove();
        }
    }

    @Override
    public int add(int u, int v) {
        assert contains(u) && contains(v);
        if (!_graph.get(u).contains(v)) {
            _graph.get(u).add(v);
            _numEdges += 1;
            _edgeList.add(pair(new Pair<>(u, v)));
            if (!_idList.contains(pair(new Pair<>(u, v)))) {
                _idList.add(pair(new Pair<>(u, v)));

            }
        }
        return edgeId(u, v);
    }

    @Override
    public void remove(int v) {
        if (contains(v)) {
            Iteration<int[]> edges = edges();
            for (int[] edge : edges) {
                if (edge[0] == v || edge[1] == v) {
                    _numEdges -= 1;
                    _graph.get(edge[0]).remove(((Integer) edge[1]));
                    _edgeList.remove(pair(new Pair<>(edge[0], edge[1])));
                }
            }
            _graph.set(v, null);
            _removed.offer(v);
            _numVertices -= 1;
            if (_maxVertex != findMaxVertex()) {
                _maxVertex = findMaxVertex();
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        if (contains(u, v)) {
            int index = _edgeList.indexOf(pair(new Pair<>(u, v)));
            _graph.get(u).remove(_graph.get(u).indexOf(v));
            _edgeList.remove(index);
            _numEdges -= 1;
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> vertices = new ArrayList<>();
        for (int i = 1; i <= maxVertex(); i += 1) {
            if (!_removed.contains(i)) {
                vertices.add(i);
            }
        }
        return Iteration.iteration(vertices);
    }

    @Override
    public Iteration<Integer> successors(int v) {
        if (contains(v)) {
            return Iteration.iteration(_graph.get(v));
        } else {
            return Iteration.iteration(java.util.Collections.emptyIterator());
        }
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> result = new ArrayList<>();
        for (Pair<Integer, Integer> pair: _edgeList) {
            int[] edge = {pair.getKey(), pair.getValue()};
            result.add(edge);
        }
        return Iteration.iteration(result);
    }

    @Override
    protected int edgeId(int u, int v) {
        if (contains(u, v) && _idList.contains(pair(new Pair<>(u, v)))) {
            return _idList.indexOf(pair(new Pair<>(u, v)));
        } else {
            return 0;
        }
    }

    /** Returns unique Pair so that == compare works.
     * @param p Pair. */
    private Pair<Integer, Integer> pair(Pair<Integer, Integer> p) {
        for (int i = 0; i < _pairList.size(); i += 1) {
            if (_pairList.get(i).equals(p)) {
                return _pairList.get(i);
            }
        }
        _pairList.add(p);
        return p;
    }

    /** Returns the max vertex that is not null. */
    private int findMaxVertex() {
        for (int i = maxVertex(); i > 0; i -= 1) {
            if (_graph.get(i) != null) {
                return i;
            }
        }
        return 0;
    }
}
