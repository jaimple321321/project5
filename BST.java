// 作者 ChenyuWang251130
package project5;
import java.util.*;


//
 // Generic Binary Search Tree (BST) storing elements in sorted order.
 //
 // Those variables and methods are defaults methods of BST<E> class.
 // - add(E), remove(E)
 // - contains(Object)
 // - iterator()
 // - get(int)
 // - first(), last(), floor(E), ceiling(E), lower(E), higher(E)
 // - toStringTreeFormat()
 //
 // Now is the local variables and methods I customized for this project.
 //
 // Height Variables:
 // - empty tree height = 0
 // - leaf height = 1
 // Three different iterator algorithms.
 // And toString parts.
 // Many exceptions throwing(I am not really understand whether
 // some situation need to return false or throw some exception.)
 //
 // @param <E> element type
public class BST<E extends Comparable<? super E>> implements Iterable<E> {

    // Tree node .
    protected static class Node<E> {
        E data;
        Node<E> left, right;
        // Store element value in a new node.
        Node(E data) { this.data = data; }
    }

    protected Node<E> root;
    protected int size;

    // Constructs an empty BST.
    public BST() {
        root = null;
        size = 0;
    }

    // Constructs a BST by inserting all items from a collection (in iteration order).
    public BST(Collection<? extends E> data) {
        this();
        if (data == null) throw new IllegalArgumentException("data is null");
        for (E e : data) add(e);
    }

    // Constructs a BST by inserting all items from an array.
    public BST(E[] data) {
        this();
        if (data == null) throw new IllegalArgumentException("data is null");
        for (E e : data) add(e);
    }

    // Return number of elements in the tree.
    public int size() { return size; }
    // Check whether the tree has no elements.
    public boolean isEmpty() { return size == 0; }

    // Clears the tree.
    public void clear() {
        root = null;
        size = 0;
    }

    //
     // Height of the tree.
    public int height() { return height(root); }

    // Alias for older code that used "depth" as height.
    public int depth() { return height(); }

    // Compute height for the given subtree.
    protected int height(Node<E> n) {
        if (n == null) return 0;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    // Returns root element data (null if empty).
    public E getRoot() {
        return root == null ? null : root.data;
    }

    // Adds item; returns true if inserted, false if it was already present.
    public boolean add(E item) {
        if (item == null) throw new IllegalArgumentException("item is null");

        if (root == null) {
            root = new Node<>(item);
            size = 1;
            return true;
        }

        Node<E> curr = root;
        while (true) {
            int cmp = item.compareTo(curr.data);
            if (cmp == 0) return false; // no duplicates
            if (cmp < 0) {
                if (curr.left == null) {
                    curr.left = new Node<>(item);
                    size++;
                    return true;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new Node<>(item);
                    size++;
                    return true;
                }
                curr = curr.right;
            }
        }
    }

    // Check whether the tree contains the provided object.
    public boolean contains(Object o) {
        //if (o == null) return false;
        if (o == null) throw new NullPointerException();

        final E item;
        try {
            E cast = (E) o;
            item = cast;
        } catch (ClassCastException ex) {
            return false;
        }

        Node<E> curr = root;
        while (curr != null) {
            final int cmp;
            try {
                cmp = item.compareTo(curr.data);
            } catch (ClassCastException ex) {
                return false;
            }
            if (cmp == 0) return true;
            curr = (cmp < 0) ? curr.left : curr.right;
        }
        return false;
    }

    // Removes item; returns true if removed.
    public boolean remove(E item) {
        if (item == null) throw new IllegalArgumentException("item is null");
        boolean[] removed = new boolean[] { false };
        root = remove(root, item, removed);
        if (removed[0]) size--;
        return removed[0];
    }

    // Internal recursive remove helper.
    private Node<E> remove(Node<E> node, E item, boolean[] removed) {
        if (node == null) return null;

        int cmp = item.compareTo(node.data);
        if (cmp < 0) {
            node.left = remove(node.left, item, removed);
        } else if (cmp > 0) {
            node.right = remove(node.right, item, removed);
        } else {
            removed[0] = true;

            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // two children: replace with inorder successor (min of right subtree)
            Node<E> succParent = node;
            Node<E> succ = node.right;
            while (succ.left != null) {
                succParent = succ;
                succ = succ.left;
            }
            node.data = succ.data;

            // delete successor node (without touching 'removed' / size again)
            if (succParent == node) {
                succParent.right = succ.right;
            } else {
                succParent.left = succ.right;
            }
        }
        return node;
    }

    // Returns the in-order element at 0-based index.
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }

        Deque<Node<E>> st = new ArrayDeque<>();
        Node<E> curr = root;
        int seen = 0;

        while (curr != null || !st.isEmpty()) {
            while (curr != null) {
                st.push(curr);
                curr = curr.left;
            }
            Node<E> node = st.pop();
            if (seen == index) return node.data;
            seen++;
            curr = node.right;
        }
        throw new IllegalStateException("Traversal failed");
    }

    // Smallest element.
    public E first() {
        if (root == null) throw new NoSuchElementException();
        Node<E> curr = root;
        while (curr.left != null) curr = curr.left;
        return curr.data;
    }

    // Largest element.
    public E last() {
        if (root == null) throw new NoSuchElementException();
        Node<E> curr = root;
        while (curr.right != null) curr = curr.right;
        return curr.data;
    }

    // Greatest element <= key (or null if none).
    public E floor(E key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        Node<E> curr = root;
        E ans = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.data);
            if (cmp == 0) return curr.data;
            if (cmp < 0) {
                curr = curr.left;
            } else {
                ans = curr.data;
                curr = curr.right;
            }
        }
        return ans;
    }

    // Smallest element >= key (or null if none).
    public E ceiling(E key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        Node<E> curr = root;
        E ans = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.data);
            if (cmp == 0) return curr.data;
            if (cmp < 0) {
                ans = curr.data;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return ans;
    }

    // Greatest element strictly < key (or null if none).
    public E lower(E key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        Node<E> curr = root;
        E ans = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.data);
            if (cmp <= 0) {
                curr = curr.left;
            } else {
                ans = curr.data;
                curr = curr.right;
            }
        }
        return ans;
    }

    // Smallest element strictly > key (or null if none).
    public E higher(E key) {
        if (key == null) throw new IllegalArgumentException("key is null");
        Node<E> curr = root;
        E ans = null;
        while (curr != null) {
            int cmp = key.compareTo(curr.data);
            if (cmp < 0) {
                ans = curr.data;
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }
        return ans;
    }

    //@Override
    // Provide an inorder iterator by default.
    public Iterator<E> iterator() {
        return new InOrderIterator<>(root);
    }

    // Three different iterator orders. Default: inorder.
    public Iterator<E> inorderIterator() {
        return iterator();
    }

    // Build a preorder iterator over the tree.
    public Iterator<E> preorderIterator() {
        return new PreIterator<>(root);
    }

    // Build a postorder iterator over the tree.
    public Iterator<E> postorderIterator() {
        return new PostIterator<>(root);
    }

    private static class InOrderIterator<E> implements Iterator<E> {
        private final Stack<Node<E>> st = new Stack<>();

        // Prepare stack with the leftmost path from root.
        public InOrderIterator(Node<E> root) {
            pushLeft(root);
        }

        // Push all left children for continued traversal.
        private void pushLeft(Node<E> n) {
            while (n != null) {
                st.push(n);
                n = n.left;
            }
        }

        // Determine if more nodes remain.
        @Override
        public boolean hasNext() {
            return !st.isEmpty();
        }

        // Visit the next node in inorder sequence.
        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node<E> n = st.pop();
            if (n.right != null) pushLeft(n.right);
            return n.data;
        }
    }

    private static class PreIterator<E> implements Iterator<E> {
        private final Stack<Node<E>> st = new Stack<>();

        // Start preorder traversal from the root if present.
        public PreIterator(Node<E> root) {
            if (root != null) st.push(root);
        }

        // Check if more nodes are queued.
        //@Override
        public boolean hasNext() {
            return !st.isEmpty();
        }

        // Return next node in preorder order.
        //@Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node<E> n = st.pop();
            if (n.right != null) st.push(n.right);
            if (n.left != null) st.push(n.left);
            return n.data;
        }
    }

    private static class PostIterator<E> implements Iterator<E> {
        private final Stack<Node<E>> out = new Stack<>();

        // Prepare a postorder traversal list using two stacks.
        public PostIterator(Node<E> root) {
            if (root == null) return;
            Stack<Node<E>> st = new Stack<>();
            st.push(root);
            while (!st.isEmpty()) {
                Node<E> n = st.pop();
                out.push(n);
                if (n.left != null) st.push(n.left);
                if (n.right != null) st.push(n.right);
            }
        }

        // Confirm whether traversal output remains.
        //@Override
        public boolean hasNext() {
            return !out.isEmpty();
        }

        // Pop the next node in postorder sequence.
        //@Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            return out.pop().data;
        }
    }



    //
     // Empty tree is printed as "[]" not null.
    //@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    //
     // Structural equality.
    //@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BST<?> other)) return false;
        if (this.size != other.size) return false;
        return equalsNodes(this.root, other.root);
    }

    // Compare two nodes recursively for structural equality.
    private boolean equalsNodes(Node<E> a, Node<?> b) {
        if (a == null || b == null) return a == b;
        try {
            if (a.data.compareTo((E) b.data) != 0) return false;
        } catch (ClassCastException ex) {
            return false;
        }
        return equalsNodes(a.left, b.left) && equalsNodes(a.right, b.right);
    }

    //@Override
    //The hashCode part is referenced from BST standard class.
    public int hashCode() {
        return hashNode(root);
    }

    // Compute a combined hash for the subtree.
    private int hashNode(Node<E> n) {
        if (n == null) return 0;
        int h = 1;
        h = 31 * h + (n.data == null ? 0 : n.data.hashCode());
        h = 31 * h + hashNode(n.left);
        h = 31 * h + hashNode(n.right);
        return h;
    }

    //
     // Returns a multi-line, human-readable tree representation.
     // Convention: right subtree printed first (on top), then node, then left.
     // Each depth level indents by 4 spaces.
    public String toStringTreeFormat() {
        StringBuilder sb = new StringBuilder();
        toStringTreeFormat(root, 0, sb);
        return sb.toString();
    }

    // Helper to format a subtree with indentation.
    private void toStringTreeFormat(Node<E> node, int depth, StringBuilder sb) {
        if (node == null) return;
        toStringTreeFormat(node.right, depth + 1, sb);
        for (int i = 0; i < depth; i++) sb.append("    ");
        sb.append(node.data).append("\n");
        toStringTreeFormat(node.left, depth + 1, sb);
    }
}

