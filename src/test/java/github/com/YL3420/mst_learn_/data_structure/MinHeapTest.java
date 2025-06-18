package github.com.YL3420.mst_learn_.data_structure;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MinHeapTest {

    /*
        We will make a minheap ranking the importance of friends that we have
     */

    @DisplayName("")
    @Test
    void testEmptyHeap(){
        MinHeap<String> newHeap = new MinHeap<>();

        assertTrue(newHeap.isEmpty());
    }

    @DisplayName("min heap with only one element")
    @Test
    void testAddToHeap() {
        MinHeap<String> newHeap = new MinHeap<>();
        newHeap.addOrUpdate("Josh", 2);

        assertEquals("Josh", newHeap.extractMin());
    }

    @DisplayName("Test that adding distinct items to the min heap maintains"
            + "heap invariants")
    @Test
    void testAddDistinctPriority(){
        MinHeap<String> newHeap = new MinHeap<>();
        newHeap.addOrUpdate("Josh", 2);

        assertEquals(1, newHeap.size());
        assertEquals("Josh", newHeap.peek());

        newHeap.addOrUpdate("Bella", 1);

        assertEquals(2, newHeap.size());
        assertEquals("Bella", newHeap.peek());

        newHeap.addOrUpdate("Cameron", 20);
        assertEquals("Bella", newHeap.peek());

        newHeap.addOrUpdate("Jonathan", 30);
        assertEquals("Bella", newHeap.peek());

        newHeap.addOrUpdate("Aidan", 0.2);
        assertEquals("Aidan", newHeap.peek());
    }

    @DisplayName("Test that update works and heap invariants"
            + "are maintained when duplicates are added")
    @Test
    void testAddUpdatePriority(){
        MinHeap<String> newHeap = new MinHeap<>();
        newHeap.addOrUpdate("Josh", 2);
        newHeap.addOrUpdate("Bella", 1);

        newHeap.addOrUpdate("Bella", 3);
        assertEquals("Josh", newHeap.peek());

        newHeap.addOrUpdate("Robert", 10);
        newHeap.addOrUpdate("Maria", 100);
        assertEquals("Josh", newHeap.peek());

        newHeap.addOrUpdate("Maria", 0.1);
        assertEquals("Maria", newHeap.peek());
    }

    @DisplayName("extractMin on min heap with singular item"
            + "will return the only item in the heap")
    @Test
    void testExtractSingular(){
        MinHeap<String> newHeap = new MinHeap<>();
        newHeap.addOrUpdate("Josh", 2);

        assertEquals("Josh", newHeap.extractMin());
        assertTrue(newHeap.isEmpty());
    }

    @DisplayName("")
    @Test
    void testReorderAfterExtract(){
        MinHeap<String> newHeap = new MinHeap<>();
        newHeap.addOrUpdate("Josh", 1);
        newHeap.addOrUpdate("Amelia", 3);
        newHeap.addOrUpdate("Dave", 2);

        newHeap.extractMin();
        String extracted = newHeap.extractMin();

        assertEquals("Dave", extracted);
        assertEquals(1, newHeap.size());

        /*
            -----------------------
         */

        MinHeap<String> heap2 = new MinHeap<>();
        heap2.addOrUpdate("Josh", 1);
        heap2.addOrUpdate("Amelia", 2);
        heap2.addOrUpdate("Dave", 3);
        heap2.addOrUpdate("Jonathan", 4);
        heap2.addOrUpdate("George", 10);

        heap2.extractMin();
        assertEquals("Amelia", heap2.peek());
    }

    @DisplayName("GIVEN a MinPQueue (whose elements' priorities may have been updated), "
            + "WHEN elements are successively removed, "
            + "THEN the minimum priority will not decrease after each removal")
    @Test
    void testRemovePriorityOrder() {
        MinHeap<Integer> q = new MinHeap<>();

        // use this instead
        int nUpdates = 100;

//        int nUpdates = 60;

        // Add random elements with random priorities to queue and randomly update some elements'
        //  priorities.
        int seed = 1;
        Random rng = new Random(seed);
        int bound = nUpdates / 2;
        for (int i = 0; i < nUpdates; i += 1) {
            int key = rng.nextInt(bound);
            int priority = key;
            q.addOrUpdate(key, priority);
        }

//        for(Entry<Integer> thing : q.heapList)
//            System.out.println(thing);

        // Remove until 1 left, but no more than nUpdates times (to prevent infinite loop in test)
        for (int i = 0; q.size() > 1 && i < nUpdates; i += 1) {
            double removedPriority = q.peek();
            int min = q.extractMin();
            System.out.println(min);
            assertTrue(q.peek() >= removedPriority);
        }
        q.extractMin();
        assertTrue(q.isEmpty());
    }
}
