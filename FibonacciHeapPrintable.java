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
        
        output.append(node.key);
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
