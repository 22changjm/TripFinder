package graph;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Collection;
import java.util.TreeSet;

/** Queue with underlying representation as a TreeSet.
 * @author Michael Chang */
class TreeSetQ extends LinkedList<Integer> {

    /** Underlying TreeSet for Queue. */
    private TreeSet<Integer> tree;

    /** Constructor for Queue with underlying representation of a TreeSet.
     * @param comp Comparator used to sort queue.*/
    TreeSetQ(Comparator<Integer> comp) {
        tree = new TreeSet<>(comp);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        for (Integer i : c) {
            tree.add(i);
        }
        return true;
    }

    @Override
    public boolean add(Integer c) {
        return tree.add(c);
    }

    @Override
    public Integer peek() {
        return tree.first();
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public Integer remove() {
        return tree.pollFirst();
    }

    @Override
    public Integer remove(int i) {
        tree.remove(i);
        return i;
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public boolean contains(Object i) {
        return tree.contains(i);
    }
}
