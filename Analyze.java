import java.util.Optional;
import java.util.Random;

public class Analyze {
    enum OperationType {
        Ins("insert"),
        Del("delete"),
        Dec("decrease key"),
        DelMin("delete min ");
        
        private final String name;
        
        OperationType(String name) {
            this.name = name;
        }
    }
    
    static final int TIMES = 10;
    
    public static void main(String[] args) {
        String[] resultsArr = new String[TIMES];
        
        test1(1000);
        for (int i = 1; i <= TIMES; i++) {
            resultsArr[i - 1] = i + "," + test1(i * 1000);
        }
        
        System.out.println("\n--------TEST 1---------\n");
        for (String s : resultsArr) {
            System.out.println(s);
        }
        
        test2(1000);
        for (int i = 1; i <= TIMES; i++) {
            resultsArr[i - 1] = i + "," + test2(i * 1000);
        }
        
        System.out.println("\n--------TEST 2---------\n");
        
        for (String s : resultsArr) {
            System.out.println(s);
        }
    }
    
    private static int[] generateRandomArr(int i) {
        Random r = new Random();
        System.out.println("strart " + i);
        return r.ints(i).toArray();
    }
    
    public static String test2(int i) {
        FibonacciHeapPrintable kak          = new FibonacciHeapPrintable();
        int           oldTotalLink = FibonacciHeapPrintable.totalLinks();
        int           oldTotalCuts = FibonacciHeapPrintable.totalCuts();
        
        long oldTime = System.nanoTime();
        
        for (int j = i; j >= 1; j--) {
            doOperation(kak, OperationType.Ins, j, null);
        }
        
        for (int j = i / 2; j >= 1; j--) {
            doOperation(kak, OperationType.DelMin, j, null);
        }
        
        long TimeTaken = System.nanoTime() - oldTime;
    
        // kak.updateString();
        // System.out.println(kak.lastString);
        
        int newTotalLink = FibonacciHeapPrintable.totalLinks() - oldTotalLink;
        int newTotalCuts = FibonacciHeapPrintable.totalCuts() - oldTotalCuts;
        
        return i + "," + TimeTaken / Math.pow(10, 6) + "," + (newTotalLink) + "," + newTotalCuts + "," +
               (kak.potential());
        
    }
    
    public static String test1(int i) {
        FibonacciHeap kak          = new FibonacciHeap();
        int           oldTotalLink = FibonacciHeap.totalLinks();
        int           oldTotalCuts = FibonacciHeap.totalCuts();
        
        long oldTime = System.nanoTime();
        
        for (int j = i; j >= 1; j--) {
            doOperation(kak, OperationType.Ins, j, null);
        }
        
        long TimeTaken = System.nanoTime() - oldTime;
        
        int newTotalLink = FibonacciHeap.totalLinks() - oldTotalLink;
        int newTotalCuts = FibonacciHeap.totalCuts() - oldTotalCuts;
        
        return i + "," + TimeTaken / Math.pow(10, 6) + "," + (newTotalLink) + "," + newTotalCuts + "," +
               (kak.potential());
        
    }
    
    // public static String test2(int i) {
    //     double avgInsert = 0;
    //     int    maxInsert = 0;
    //     double avgDelete = 0;
    //     int    maxDelete = 0;
    //
    //
    //     FibonacciHeap kak         = new FibonacciHeap();
    //     int[]         genratedArr = generateRandomArr(i);
    //     int[]         sortedArr   = genratedArr.clone();
    //     Arrays.sort(sortedArr);
    //
    //     int val;
    //
    //     for (int j = genratedArr.length; j >= 1; j--) {
    //         doOperation(kak, OperationType.Ins, i, null);
    //         val = kak.insert(genratedArr[j], "");
    //         avgInsert += val;
    //         maxInsert = Math.max(maxInsert, val);
    //     }
    //
    //     for (int j = 0; j < genratedArr.length; j++) {
    //         val = kak.delete(sortedArr[j]);
    //
    //         avgDelete += val;
    //         maxDelete = Math.max(maxDelete, val);
    //     }
    //
    //     return i + "," + (avgInsert / i) + "," + (maxInsert) + "," + (avgDelete / i) + "," + (maxDelete);
    // }
    
    private static Optional<FibonacciHeap.HeapNode> doOperation(FibonacciHeap heap, OperationType opT, Integer num, FibonacciHeap.HeapNode node) {
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
        
        if (heapNode != null) {
            return Optional.of(heapNode);
        } else {
            return Optional.empty();
        }
    }
}
