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
        test6();
    }
    
    private static void test8() {
        FibonacciHeapPrintable heap = new FibonacciHeapPrintable();
        
        doOperation(heap, OperationType.DelMin, null, null);
        
        System.out.println("done");
        
    }
    
    private static void test7() {
        FibonacciHeapPrintable heap1 = new FibonacciHeapPrintable();
        FibonacciHeapPrintable heap2 = new FibonacciHeapPrintable();
        
        // doOperation(heap1, OperationType.Ins, 50, null);
        doOperation(heap2, OperationType.Ins, 60, null);
        
        heap1.meld(heap2);
        
        heap1.updateString();
        System.out.println(heap1.lastString);
        
        System.out.println("done");
        
    }
    
    
    private static void test6() {
        // this test case want to make sure that the marks get removed when delete min
        
        FibonacciHeapPrintable                 heap = new FibonacciHeapPrintable();
        sun.misc.Queue<FibonacciHeap.HeapNode> a    = new sun.misc.Queue<>();
        sun.misc.Queue<FibonacciHeap.HeapNode> b    = new sun.misc.Queue<>();
        doOperation(heap, OperationType.Ins, 50, null);
        b.enqueue(doOperation(heap, OperationType.Ins, 60, null).get());
        a.enqueue(doOperation(heap, OperationType.Ins, 70, null).get());
        doOperation(heap, OperationType.Ins, 80, null).get();
        a.enqueue(doOperation(heap, OperationType.Ins, 90, null).get());
        doOperation(heap, OperationType.Ins, 100, null);
        doOperation(heap, OperationType.Ins, 110, null);
        doOperation(heap, OperationType.Ins, 120, null);
        a.enqueue(doOperation(heap, OperationType.Ins, 130, null).get());
        doOperation(heap, OperationType.DelMin, null, null);
        
        try {
            doOperation(heap, OperationType.Dec, 11, a.dequeue());
            doOperation(heap, OperationType.Dec, 40, a.dequeue());
            doOperation(heap, OperationType.DelMin, null, null);
            doOperation(heap, OperationType.DelMin, null, null);

            doOperation(heap, OperationType.Ins, 70, null);
            doOperation(heap, OperationType.Del, null, b.dequeue());
            
            
        } catch (InterruptedException err) {
            System.out.println(err);
        }
        
        
        
        System.out.println("done");
        
    }
    
    private static void test5() {
        FibonacciHeapPrintable heap = new FibonacciHeapPrintable();
        for (int i = 100; i >= 1; i--) {
            doOperation(heap, OperationType.Ins, i, null);
        }
        
        for (int i = 50; i >= 1; i--) {
            doOperation(heap, OperationType.DelMin, null, null);
        }
        
        
        System.out.println("done");
        
    }
    
    private static void test4() {
        FibonacciHeapPrintable heap = new FibonacciHeapPrintable();
        doOperation(heap, OperationType.Ins, 1, null);
        doOperation(heap, OperationType.Ins, 2, null);
        doOperation(heap, OperationType.DelMin, null, null);
        doOperation(heap, OperationType.DelMin, null, null);
        
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
    
    private static Optional<FibonacciHeap.HeapNode> doOperation(FibonacciHeapPrintable heap, OperationType opT, Integer num, FibonacciHeap.HeapNode node) {
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
        
        heap.updateString();
        System.out.println(heap.lastString);
        
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