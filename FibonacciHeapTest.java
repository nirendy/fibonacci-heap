import java.util.Arrays;
import java.util.Optional;

public class FibonacciHeapTest {
    enum OperationType {
        Ins("insert"),
        Del("delete"),
        Dec("decrease key"),
        DelMin("delete min ");
        
        private final String name;
        
        private OperationType(String name) {
            this.name = name;
        }
    }
    
    public static void main(String[] args) {
        test4();
    }
    
    private static void test4() {
        FibonacciHeapPrintable heap = new FibonacciHeapPrintable();
        doOperation(heap, OperationType.Ins, 5, null);
        doOperation(heap, OperationType.Ins, 4, null);
        doOperation(heap, OperationType.Ins, 3, null);
        doOperation(heap, OperationType.Ins, 2, null);
        
        System.out.println("done");
        
    }
    
    private static void test1() {
        FibonacciHeapPrintable heap = new FibonacciHeapPrintable();
        for (int i = 1; i <= 10; i++) {
            doOperation(heap, OperationType.Ins, i, null);
        }
        
        for (int i = 1; i <= 2; i++) {
            doOperation(heap, OperationType.DelMin, null, null);
        }
        
        System.out.println("done");
        
    }
    
    private static void test3() {
        FibonacciHeapPrintable   heap = new FibonacciHeapPrintable();
        FibonacciHeap.HeapNode[] arr  = new FibonacciHeap.HeapNode[16];
        for (int i = 1; i < arr.length; i++) {
            arr[i] = doOperation(heap, OperationType.Ins, i, null).orElseGet(null);
        }
        
        for (int i = 1; i <= 5; i++) {
            doOperation(heap, OperationType.DelMin, null, null);
        }
        
        
        doOperation(heap, OperationType.Dec, 5, arr[8]);
        doOperation(heap, OperationType.Dec, 5, arr[12]);
        doOperation(heap, OperationType.Dec, 5, arr[8]);
        doOperation(heap, OperationType.Dec, 5, arr[7]);
        
        
        System.out.println("done");
        
    }
    
    private static Optional<FibonacciHeap.HeapNode> doOperation(FibonacciHeap heap, OperationType opT, Integer num, FibonacciHeap.HeapNode node) {
        System.out.print(opT.name);
        if (node != null) {
            System.out.print("node key: " + node.key);
        }
        if (num != null) {
            System.out.print(", " + num);
        }
        System.out.print(" ************\n");
        
        FibonacciHeap.HeapNode heapNode = null;
        
        switch (opT) {
            case Ins: {
                heapNode = heap.insert(num);
                break;
            }
            case Del: {
                heap.delete(node);
                break;
            }
            case Dec: {
                heap.decreaseKey(node, num);
                break;
            }
            case DelMin: {
                heap.deleteMin();
                break;
            }
            
        }
        
        
        System.out.println(
                Arrays.toString(heap.countersRep()) + //
                " Size: " + heap.size() + //
                " min : " + heap.findMin().getKey() + //
                " TreesCount: " + heap.treesCount()
        );
        
        System.out.println(heap.toString());
        
        if (heapNode != null) {
            return Optional.of(heapNode);
        } else {
            return Optional.empty();
        }
    }
    
    
    private static void test2() {
        FibonacciHeapPrintable heap = new FibonacciHeapPrintable();
        for (int i = 1; i <= 10; i++) {
            doOperation(heap, OperationType.Ins, 1, null);
        }
        
        for (int i = 1; i <= 2; i++) {
            doOperation(heap, OperationType.DelMin, null, null);
        }
        
        System.out.println("done");
        
        
    }
}