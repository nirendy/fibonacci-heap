import java.util.Arrays;

public class FibonacciHeapTest {
    public static void main(String[] args) {
        test1();
    }
    
    private static void test1() {
        FibonacciHeapPrintable heap = new FibonacciHeapPrintable();
        for (int i = 1; i <= 10; i++) {
            System.out.print("insert" + i);
            System.out.print(" ************\n");
            heap.insert(i);
            System.out.println(
                    Arrays.toString(heap.countersRep()) + //
                    " Size: " + heap.size() + //
                    " TreesCount: " + heap.treesCount()
            );
            
            System.out.println(heap.toString());
            
        }
        
        for (int i = 1; i <= 2; i++) {
            System.out.print("delete min ************\n");
            heap.deleteMin();
            System.out.println(
                    Arrays.toString(heap.countersRep()) + //
                    " Size: " + heap.size() + //
                    " TreesCount: " + heap.treesCount()
            );
            
            System.out.println(heap.toString());
            
        }
        
        
        System.out.println("done");
        
    }
}