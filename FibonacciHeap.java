import java.util.LinkedList;

/**
 * FibonacciHeap
 * <p>
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {
    static protected int                  totalLinks;
    static protected int                  totalCuts;
    protected        LinkedList<HeapNode> treesList;
    protected        HeapNode             min;
    protected        int                  markedNodesCount; // number of marked nodes at the moment
    protected        int                  size;
    
    public FibonacciHeap() {
        this.treesList = new LinkedList<HeapNode>();
    }
    
    /**
     * public boolean empty()
     * <p>
     * precondition: none
     * <p>
     * The method returns true if and only if the heap
     * is empty.
     */
    public boolean empty() {
        return this.size() == 0;
    }
    
    protected int treesCount() {
        return this.treesList.size();
    }
    
    /**
     * public HeapNode insert(int key)
     * <p>
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     */
    public HeapNode insert(int key) {
        // TODO: update size?
        
        HeapNode newNode = new HeapNode(key);
        this.treesList.add(newNode);
        this.size++;
        return newNode;
    }
    
    /**
     * public void deleteMin()
     * <p>
     * Delete the node containing the minimum key.
     */
    public void deleteMin() {
        // TODO
        return;
        
    }
    
    /**
     * public HeapNode findMin()
     * <p>
     * Return the node of the heap whose key is minimal.
     */
    public HeapNode findMin() {
        return this.min;
    }
    
    /**
     * public void meld (FibonacciHeap heap2)
     * <p>
     * Meld the heap with heap2
     */
    public void meld(FibonacciHeap heap2) {
        // TODO: update size?
        
        // add the list at the end of the list
        this.treesList.addAll(heap2.treesList);
        this.size += heap2.size();
        
        // update min
        if (heap2.min.key < this.min.key) {
            this.min = heap2.min;
        }
    }
    
    /**
     * public int size()
     * <p>
     * Return the number of elements in the heap
     */
    public int size() {
        return this.size;
    }
    
    /**
     * public int[] countersRep()
     * <p>
     * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap.
     */
    public int[] countersRep() {
        // TODO: where to put the +1
        
        int[] arr = new int[(int) Math.floor(Math.log(this.size()) + 1)];
        for (HeapNode heapNode : this.treesList) {
            arr[heapNode.rank]++;
        }
        return arr;
    }
    
    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     */
    public void delete(HeapNode x) {
        // TODO
        return;
    }
    
    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * The function decreases the key of the node x by delta. The structure of the heap should be updated
     * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        // TODO
        return;
    }
    
    /**
     * public int potential()
     * <p>
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap.
     */
    public int potential() {
        return this.treesCount() + 2 * this.markedNodesCount;
    }
    
    /**
     * public static int totalLinks()
     * <p>
     * This static function returns the total number of link operations made during the run-time of the program.
     * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of
     * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value
     * in its root.
     */
    public static int totalLinks() {
        return totalLinks;
    }
    
    /**
     * public static int totalCuts()
     * <p>
     * This static function returns the total number of cut operations made during the run-time of the program.
     * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts() {
        return totalCuts;
    }
    
    /**
     * public class HeapNode
     * <p>
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in
     * another file
     */
    public class HeapNode {
        protected int      rank;
        protected boolean  mark;
        protected HeapNode parent;
        protected HeapNode child;
        protected HeapNode next;
        protected HeapNode prev;
        public    int      key;
        
        public HeapNode(int key) {
            this.key = key;
        }
        
        public int getKey() {
            return this.key;
        }
        
    }
}
