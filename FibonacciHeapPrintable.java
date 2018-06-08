import java.util.Arrays;
import java.util.Iterator;

/**
 * FibonacciHeap
 * <p>
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeapPrintable extends FibonacciHeap {
    static final int LEVEL_SPACE = 1;
    public String lastString;
    
    public void updateString() {
        StringBuilder stringB = new StringBuilder();
        stringB.append(Arrays.toString(this.countersRep()))
                .append(" Size: ")
                .append(this.size())
                .append(" min : ")
                .append(this.findMin().getKey())
                .append(" TreesCount: ")
                .append(this.treesCount())
                .append("\n");
        
        this.lastString = stringB.toString() + toString();
    }
    
    public String toString() {
        StringBuilder output = new StringBuilder();
        if (empty()) {
            return "<Empty Heap>";
        }
        Iterator<HeapNode> treesListIterator = this.getTreesListIterator();
        
        while (treesListIterator.hasNext()) {
            output
                    .append(printNode(treesListIterator.next(), 1))
                    .append("\n|\n")
            ;
        }
        output.append("#");
        
        return output.toString();
    }
    
    public static String getSpace(int level) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < (level * LEVEL_SPACE); i++) {
            output.append("|");
            output.append("\t");
        }
        return output.toString();
    }
    
    public static String printNode(HeapNode node, int level) {
        StringBuilder output = new StringBuilder();
        String        space  = getSpace(level);
        
        if (!node.isRoot()) {
            output.append("(");
            output.append(node.parent.key);
            output.append(")");
        }
        output.append(node.key);
        
        if (node.isMarked()) {
            output.append("*");
        }
        output.append(" -> ");
        
        if (node.isLeaf()) {
            output.append("#");
        } else {
            Iterator<HeapNode> childIterator = node.getChildIterator();
            while (childIterator.hasNext()) {
                HeapNode childNode = childIterator.next();
                output
                        .append("\n")
                        .append(space)
                        .append(printNode(childNode, level + 1))
                ;
            }
            output
                    .append("\n")
                    .append(space).append("|\n")
                    .append(space).append("#");
        }
        
        return output.toString();
    }
}
