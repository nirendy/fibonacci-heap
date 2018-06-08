import java.util.*;

/**
 * FibonacciHeap
 * <p>
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap {
    static private int totalLinks       = 0;
    static private int totalCuts        = 0; //
    private        int markedNodesCount = 0; // number of marked nodes at the moment
    private        int size             = 0; // number of elements in the heap
    private        int treesCount       = 0; // number of trees in the heap
    private HeapNode first;
    private HeapNode min;
    
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

    /**
     * Returns the number of trees in the heap
     */
    public int treesCount() {
        return this.treesCount;
    }

    /*
    * Returns the last node in the
     */
    private HeapNode last() {
        return this.first.prev;
    }
    
    /**
     * public HeapNode insert(int key)
     * <p>
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     */
    public HeapNode insert(int key) {
        HeapNode newNode = new HeapNode(key);
        insertNode(newNode);
        checkAndChangeMin(newNode);
        this.size++;
        return newNode;
        
    }

    /**
     * Insert a HeapNode to the heap.
     * Disconnect the node from older family if needed
     */
    private HeapNode insertNode(HeapNode newNode) {
        // disconnect the node if it was part of a tree before
        newNode.disconnect();

        // if the heap was empty before the insertion
        if (this.empty()) {
            this.min = newNode;
            this.first = newNode;
            newNode.setNext(newNode);
        } else {
            this.last().setNext(newNode);
            newNode.setNext(this.first);
        }

        this.treesCount++;
        return newNode;
    }

    public void resetTree() {
        this.first = null;
        this.min = null;
        this.size = 0;
        this.treesCount = 0;
    }
    
    /**
     * public void deleteMin()
     * <p>
     * Delete the node containing the minimum key.
     */
    public void deleteMin() {
        HeapNode oldMin = this.findMin();
        
        
        if (oldMin.isOnlyChild()) { // only one tree in the heap
            if (oldMin.isLeaf()) { // and the single tree contains only one node
                this.resetTree(); // convert to empty heap now
                return;
            } else {
                // converts the tree list to be the child tree list
                first = oldMin.firstChild;
            }
        } else { // more than one tree in the heap
            if (oldMin == this.first) { // move the 'first' pointer to a sibling if needed
                this.first = oldMin.next;
            }
            
            if (oldMin.isLeaf()) {
                // oldMin has no children - just remove him from tree list
                oldMin.prev.setNext(oldMin.next);
            } else {
                // adding the oldMin children to list
                oldMin.firstChild.prev.setNext(oldMin.next);
                oldMin.prev.setNext(oldMin.firstChild);
            }
        }
        
        this.size--;
        this.consolidating(); // successive linking
        this.updateMin(); // find new min in the restructured heap, containing up to log(n+1) trees - O(logn)
        this.updateTreesCount(); // count the number of trees in heap - O(logn)
    }
    
    /*
    * Add a node (tree) to the tree list
     */
    private void add(HeapNode x) {
        this.last().setNext(x);
        x.setNext(this.first);
        
    }

    /*
    * Change the structure of the heap using successive linking, at the end of the process the
    * heap contains up to log(n+1) trees, 1 of each rank
     */
    private void consolidating() {
        HeapNode[]         bucketArr         = new HeapNode[this.getPossibleMaxRank()];
        Iterator<HeapNode> treesListIterator = getTreesListIterator();
        
        while (treesListIterator.hasNext()) {
            HeapNode nextTree = treesListIterator.next();
            nextTree.removeSiblingsRelations();
            
            while (bucketArr[nextTree.rank] != null) {
                // bucket is full
                
                nextTree = link(bucketArr[nextTree.rank], nextTree);
                bucketArr[nextTree.rank - 1] = null;
            }
            bucketArr[nextTree.rank] = nextTree;
        }
        
        this.first = null;
        for (HeapNode heapNode : bucketArr) { // connect the new trees
            if (heapNode != null) {
                if (this.first == null) {
                    this.first = heapNode;
                    this.first.setNext(this.first);
                } else {
                    add(heapNode);
                }
                
            }
        }
    }

    /*
    * @pre: heap contains up to log(n+1) trees
    * Iterate over the tree list, find a new minimum
    * Also removes old parent pointers and marks
     */
    private void updateMin() {
        this.min = this.first;
        this.getTreesListIterator().forEachRemaining((x) -> {
            x.parent = null;
            x.unMarkNode();
            this.min = x.key < this.min.key ? x : this.min;
        });
    }

    /*
    * @pre: heap contains up to log(n+1) trees
    * Update the number of trees (field) in the heap
     */
    private void updateTreesCount() {
        this.treesCount = 0;
        this.getTreesListIterator().forEachRemaining((x) -> this.treesCount++);
    }

    /*
    * Returns an iterator of the trees in the heap
     */
    protected Iterator<HeapNode> getTreesListIterator() {
        return this.first.getSiblingsIterator();
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
        // add tree list of heap2 at the end of this heap
        if (heap2.empty()){
            return;
        }
        if (this.empty()){ // heap2 is not empty
            this.first = heap2.first;
            this.min = heap2.findMin();
        }
        else {
            HeapNode oldLast = this.last();
            heap2.last().setNext(this.first);
            oldLast.setNext(heap2.first);
            if (heap2.min.key < this.min.key) { // update min
                this.min = heap2.min;
        }
        
        // update sizes
        this.treesCount += heap2.treesCount();
        this.size += heap2.size();
        this.markedNodesCount += heap2.markedNodesCount;
        

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

    /*
    * Returns the maximum possible rank of a tree in the heap
     */
    private int getPossibleMaxRank() {
        return (int) Math.floor((Math.log(this.size()) + 1) / Math.log(2));
    }
    
    /**
     * public int[] countersRep()
     * <p>
     * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap.
     */
    public int[] countersRep() {
        if (this.empty()) {
            return new int[1]; // returns an array containing a single zero
        }
        int[] arr = new int[this.getPossibleMaxRank()];
        
        Iterator<HeapNode> childIterator = this.first.getSiblingsIterator();
        while (childIterator.hasNext()) {
            arr[childIterator.next().rank]++;
        }
        return arr;
    }
    
    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     */
    public void delete(HeapNode x) {
        if (size == 1) {
            this.resetTree();
            return;
        }
        
        this.decreaseKey(x, x.getKey() - this.findMin().getKey() + 1);
        this.deleteMin();
        
    }
    
    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * The function decreases the key of the node x by delta. The structure of the heap should be updated
     * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        x.decreaseKeyBy(delta);
        checkAndChangeMin(x);
        if (x.isRoot() || x.getKey() >= x.parent.getKey()) {
            return;
        }
        
        HeapNode curr = x;
        do {
            totalCuts++;
            HeapNode currParent = curr.parent;
            this.insertNode(curr);
            curr = currParent;
        } while (!curr.isRoot() && curr.parent.isMarked());
        
        if (!curr.isRoot()) {
            curr.markNode();
        }
        
    }
    
    private void checkAndChangeMin(HeapNode possiblyMinNode) {
        if (possiblyMinNode.getKey() < this.min.getKey()) {
            this.min = possiblyMinNode;
        }
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
    
    static private HeapNode link(HeapNode x, HeapNode y) {
        totalLinks++;
        
        // deciding who's the parent
        HeapNode parentNode = x.key < y.key ? x : y;
        HeapNode childNode  = parentNode == x ? y : x;
        
        if (parentNode.isLeaf()) {
            parentNode.setFirstChild(childNode);
        } else {
            // puts the child as the first node of the parent
            HeapNode oldFirst = parentNode.firstChild;
            parentNode.setFirstChild(childNode);
            oldFirst.prev.setNext(childNode);
            childNode.setNext(oldFirst);
        }
        
        parentNode.increaseRank();
        return parentNode;
    }
    
    /**
     * public class HeapNode
     * <p>
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in
     * another file
     */
    public class HeapNode {
        private   int      rank       = 0;
        private   boolean  mark       = false;
        protected HeapNode parent     = null;
        private   HeapNode firstChild = null;
        private HeapNode next;
        private HeapNode prev;
        public  int      key;
        
        
        private void increaseRank() {
            this.rank++;
        }
        
        private void decreaseRank() {
            this.rank--;
        }
        
        public HeapNode(int key) {
            this.key = key;
        }
        
        private boolean isOnlyChild() {
            return this.next == this;
        }
        
        private boolean isFirstChild() {
            // TODO: what if root
            return this.parent.firstChild == this;
        }
        
        protected boolean isMarked() {
            return this.mark;
        }
        
        private void markNode() {
            if (!this.isMarked()) {
                this.mark = true;
                FibonacciHeap.this.markedNodesCount++;
            }
        }
        
        private void unMarkNode() {
            if (this.isMarked()) {
                this.mark = false;
                FibonacciHeap.this.markedNodesCount--;
            }
        }
        
        private void decreaseKeyBy(int delta) {
            this.key -= delta;
        }
        
        protected boolean isLeaf() {
            // todo: rank==0?
            return this.firstChild == null;
        }
        
        protected boolean isRoot() {
            return this.parent == null;
        }

        /*
        * Disconnect a node from its parents and siblings
         */
        private void disconnect() {
            if (!this.isRoot()) {
                
                // set parent first child
                if (this.isOnlyChild()) {
                    this.parent.setFirstChild(null);
                } else {
                    if (this.isFirstChild()) {
                        this.parent.setFirstChild(this.next);
                    }
                    this.removeSiblingsRelations();
                }
                
                this.parent.decreaseRank();
                this.parent = null;
            }
            
            this.unMarkNode();
        }
        
        public int getKey() {
            return this.key;
        }

        /*
        * Disconnect a node from its siblings
         */
        public void removeSiblingsRelations() {
            this.prev.setNext(this.next);
            this.setNext(this);
        }
        
        
        public void setParent(HeapNode parent) {
            this.parent = parent;
        }
        
        public void setFirstChild(HeapNode firstChild) {
            this.firstChild = firstChild;
            
            if (firstChild != null) {
                firstChild.parent = this;
            }
        }
        
        
        // set this.next and next.prev
        public void setNext(HeapNode next) {
            this.next = next;
            next.prev = this;
        }
        
        public Iterator<HeapNode> getSiblingsIterator() {
            return new Iterator<HeapNode>() {
                private HeapNode curr = null;
                private HeapNode next = HeapNode.this;
                private HeapNode lastChild = HeapNode.this.prev;
                
                @Override
                public boolean hasNext() {
                    return this.curr == null || this.curr != this.lastChild;
                }
                
                @Override
                public HeapNode next() {
                    this.curr = this.next;
                    this.next = this.curr.next;
                    return curr;
                }
                
            };
        }
        
        public Iterator<HeapNode> getChildIterator() {
            if (this.isLeaf()) {
                // empty iterator
                return new Iterator<HeapNode>() {
                    @Override
                    public boolean hasNext() {
                        return false;
                    }
                    
                    @Override
                    public HeapNode next() {
                        return null;
                    }
                };
            } else {
                return this.firstChild.getSiblingsIterator();
            }
        }
    }
}
