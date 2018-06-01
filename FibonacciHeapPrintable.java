import java.util.Iterator;

/**
 * FibonacciHeap
 * <p>
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeapPrintable extends FibonacciHeap {
    static final int LEVEL_SPACE = 1;
    
    public String toString() {
        StringBuilder      output            = new StringBuilder();
        Iterator<HeapNode> treesListIterator = this.getTreesListIterator();
        
        while (treesListIterator.hasNext()) {
            output
                    .append(printNode(treesListIterator.next(), 1))
                    .append("\n\n|\n\n")
            ;
        }
        output.append("#");
        
        return output.toString();
    }
    
    public static String getSpace(int level) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < (level * LEVEL_SPACE); i++) {
            output.append("\t");
        }
        return output.toString();
    }
    
    private static String printNode(HeapNode node, int level) {
        StringBuilder output = new StringBuilder();
        String        space  = getSpace(level);
        output.append(node.key);
        Iterator<HeapNode> childIterator = node.getChildIterator();
        if (node.isLeaf()) {
            output.append(space).append(" -> #");
        } else {
            while (childIterator.hasNext()) {
                HeapNode childNode = childIterator.next();
                output
                        .append(printNode(childNode, level + 1))
                        .append("\n\n|\n\n")
                ;
            }
        }
        return output.toString();
    }
}
