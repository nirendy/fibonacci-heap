/**
 * FibonacciHeap
 * <p>
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeapPrintable extends FibonacciHeap {
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (HeapNode heapNode : this.treesList) {
            output.append("").append(heapNode.key).append(" --> ");
        }
        output.append(" #");
        
        return output.toString();
    }
    
    private static String printNode(HeapNode node) {
        StringBuilder output = new StringBuilder();
        
        return output.toString();
    }
}
