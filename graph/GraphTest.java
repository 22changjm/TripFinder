package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Michael Chang
 */
public class GraphTest {

    private UndirectedGraph makeUndirected() {
        UndirectedGraph g = new UndirectedGraph();
        assertEquals(1, g.add());
        assertEquals(2, g.add());
        assertEquals(3, g.add());
        assertEquals(4, g.add());
        assertEquals(1, g.add(1, 3));
        assertEquals(2, g.add(3, 2));
        assertEquals(3, g.add(1, 2));
        assertEquals(4, g.add(4, 4));
        return g;
    }

    private DirectedGraph makeDirected() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(1, g.add());
        assertEquals(2, g.add());
        assertEquals(3, g.add());
        assertEquals(4, g.add());
        assertEquals(1, g.add(1, 3));
        assertEquals(2, g.add(3, 2));
        assertEquals(3, g.add(1, 2));
        assertEquals(4, g.add(4, 4));
        assertEquals(5, g.add(3, 1));
        return g;
    }

    @Test
    public void testEmptyDirectedGraph() {
        DirectedGraph g = new DirectedGraph();
        assertTrue(g.isDirected());
        assertEquals(0, g.vertexSize());
        assertEquals(0, g.maxVertex());
        assertEquals(0, g.edgeSize());
        assertEquals(0, g.outDegree(0));
        assertEquals(0, g.outDegree(3));
        assertEquals(0, g.inDegree(0));
        assertEquals(0, g.inDegree(3));
        assertFalse(g.contains(1, 3));
        assertFalse(g.contains(0, 0));
        assertFalse(g.contains(0));
        assertFalse(g.contains(1));
        g.remove(3);
        g.remove(3, 4);
        assertFalse(g.vertices().hasNext());
        assertFalse(g.successors(3).hasNext());
        assertFalse(g.predecessors(3).hasNext());
        assertFalse(g.edges().hasNext());
        assertEquals(0, g.edgeId(3, 2));
        assertEquals(0, g.vertexSize());
        assertEquals(0, g.maxVertex());
        assertEquals(0, g.edgeSize());
    }

    @Test
    public void testEmptyUndirectedGraph() {
        UndirectedGraph g = new UndirectedGraph();
        assertFalse(g.isDirected());
        assertEquals(0, g.vertexSize());
        assertEquals(0, g.maxVertex());
        assertEquals(0, g.edgeSize());
        assertEquals(0, g.outDegree(0));
        assertEquals(0, g.outDegree(3));
        assertEquals(0, g.inDegree(0));
        assertEquals(0, g.inDegree(3));
        assertFalse(g.contains(1, 3));
        assertFalse(g.contains(0, 0));
        assertFalse(g.contains(0));
        assertFalse(g.contains(1));
        g.remove(3);
        g.remove(3, 4);
        assertFalse(g.vertices().hasNext());
        assertFalse(g.successors(3).hasNext());
        assertFalse(g.predecessors(3).hasNext());
        assertFalse(g.edges().hasNext());
        assertEquals(0, g.edgeId(3, 2));
        assertEquals(0, g.vertexSize());
        assertEquals(0, g.maxVertex());
        assertEquals(0, g.edgeSize());
    }

    @Test
    public void testSizes() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        assertEquals(4, u.vertexSize());
        assertEquals(4, g.vertexSize());
        assertEquals(4, u.maxVertex());
        assertEquals(4, g.maxVertex());
        assertEquals(4, u.edgeSize());
        assertEquals(5, g.edgeSize());
    }

    @Test
    public void testDegrees() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        assertEquals(2, u.outDegree(1));
        assertEquals(2, u.inDegree(1));
        assertEquals(2, u.outDegree(2));
        assertEquals(2, u.inDegree(2));
        assertEquals(2, u.outDegree(1));
        assertEquals(2, g.outDegree(1));
        assertEquals(1, g.inDegree(1));
        assertEquals(1, g.outDegree(4));
        assertEquals(1, g.inDegree(4));

    }

    @Test
    public void testContains() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        assertTrue(g.contains(1));
        assertTrue(u.contains(1));
        assertFalse(g.contains(5));
        assertFalse(u.contains(5));
        assertFalse(u.contains(0, 0));
        assertFalse(g.contains(0, 0));
        assertTrue(u.contains(1, 3));
        assertTrue(u.contains(3, 1));
        assertFalse(u.contains(5, 4));
        assertTrue(g.contains(1, 3));
        assertTrue(g.contains(3, 1));
        assertTrue(g.contains(1, 2));
        assertFalse(g.contains(2, 1));
        assertTrue(g.contains(4, 4));
    }

    @Test
    public void testVerticesIterations() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        Iteration<Integer> uv = u.vertices();
        Iteration<Integer> gv = g.vertices();
        int countu = 0;
        int countg = 0;
        for (int v : uv) {
            countu += 1;
        }
        for (int v : gv) {
            countg += 1;
        }
        assertEquals(4, countu);
        assertEquals(4, countg);

    }

    @Test
    public void testSuccessorsIterations() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        Iteration<Integer> uv1 = u.successors(1);
        Iteration<Integer> uv3 = u.successors(3);
        Iteration<Integer> gv1 = g.successors(1);
        Iteration<Integer> gv2 = g.successors(2);
        int countu1 = 0;
        int countu3 = 0;
        int countg1 = 0;
        int countg2 = 0;
        for (int v : uv1) {
            countu1 += 1;
        }
        for (int v : uv3) {
            countu3 += 1;
        }
        for (int v : gv1) {
            countg1 += 1;
        }
        for (int v : gv2) {
            countg2 += 1;
        }
        assertEquals(2, countu1);
        assertEquals(2, countu3);
        assertEquals(2, countg1);
        assertEquals(0, countg2);

    }

    @Test
    public void testPredecessorsIterations() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        Iteration<Integer> uv2 = u.predecessors(2);
        Iteration<Integer> uv3 = u.predecessors(3);
        Iteration<Integer> gv1 = g.predecessors(1);
        Iteration<Integer> gv2 = g.predecessors(2);
        int countu2 = 0;
        int countu3 = 0;
        int countg1 = 0;
        int countg2 = 0;
        for (int v : uv2) {
            countu2 += 1;
        }
        for (int v : uv3) {
            countu3 += 1;
        }
        for (int v : gv1) {
            countg1 += 1;
        }
        for (int v : gv2) {
            countg2 += 1;
        }
        assertEquals(2, countu2);
        assertEquals(2, countu3);
        assertEquals(1, countg1);
        assertEquals(2, countg2);

    }

    @Test
    public void testEdgesIteration() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        Iteration<int[]> uv = u.edges();
        Iteration<int[]> gv = g.edges();
        int countu = 0;
        int countg = 0;
        for (int[] v : uv) {
            countu += 1;
        }
        for (int[] v : gv) {
            countg += 1;
        }
        assertEquals(4, countu);
        assertEquals(5, countg);
    }

    @Test
    public void testedgeID() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        assertEquals(1, u.edgeId(1, 3));
        assertEquals(1, u.edgeId(3, 1));
        assertEquals(0, u.edgeId(5, 4));
        assertEquals(1, g.edgeId(1, 3));
        assertEquals(5, g.edgeId(3, 1));
        assertEquals(0, g.edgeId(8, 2));
    }

    @Test
    public void testAddRemove() {
        UndirectedGraph u = makeUndirected();
        DirectedGraph g = makeDirected();
        assertEquals(5, u.add(4, 1));
        u.remove(1);
        assertEquals(3, u.vertexSize());
        assertEquals(4, u.maxVertex());
        assertEquals(2, u.edgeSize());
        u.add();
        assertFalse(u.contains(1, 3));
        assertFalse(u.contains(3, 1));
        assertEquals(4, u.vertexSize());
        assertEquals(4, u.maxVertex());
        u.remove(3, 2);
        assertFalse(u.contains(3, 2));
        assertEquals(2, u.add(3, 2));
        assertTrue(u.contains(3, 2));
        assertEquals(4, u.vertexSize());
        assertEquals(4, u.maxVertex());
        assertEquals(2, u.edgeSize());

        g.remove(1);
        assertEquals(3, g.vertexSize());
        assertEquals(4, g.maxVertex());
        assertEquals(2, g.edgeSize());
        g.add();
        assertFalse(g.contains(1, 3));
        assertFalse(g.contains(3, 1));
        assertEquals(4, g.vertexSize());
        assertEquals(4, g.maxVertex());
        g.remove(3, 2);
        assertFalse(g.contains(3, 2));
        assertEquals(2, g.add(3, 2));
        assertTrue(g.contains(3, 2));
        assertEquals(4, g.vertexSize());
        assertEquals(4, g.maxVertex());
        assertEquals(2, g.edgeSize());
    }

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

}
