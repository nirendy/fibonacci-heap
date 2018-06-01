import java.util.Arrays;

public class FibonacciHeapTest {
    public static void main(String[] args) {
        test1();
    }
    
    private static void test1() {
        FibonacciHeapPrintable heap = new FibonacciHeapPrintable();
        for (int i = 1; i <= 10; i++) {
            System.out.print("************\n");
            heap.insert(i);
            System.out.println(Arrays.toString(heap.countersRep()));
            System.out.println(heap.toString());
            
        }
    }
}