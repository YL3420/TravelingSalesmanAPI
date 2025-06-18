package github.com.YL3420.mst_learn_.data_structure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeduplicatedLinkedListTest {

    @Test
    void testEmptyList(){
        DeduplicatedLinkedList<Integer> dll = new DeduplicatedLinkedList<>();

        assertTrue(dll.isEmpty());
    }

    @Test
    void testAddDistinct(){
        DeduplicatedLinkedList<Integer> dll = new DeduplicatedLinkedList<>();

        assertTrue(dll.add(3));
        assertEquals(1, dll.size());
        assertEquals(3, dll.getFirst());
        assertEquals(3, dll.getLast());

        assertTrue(dll.add(5));
        assertEquals(2, dll.size());
        assertEquals(3, dll.getFirst());
        assertEquals(5, dll.getLast());

        assertTrue(dll.add(20009));
        assertEquals(3, dll.size());
        assertEquals(3, dll.getFirst());
        assertEquals(20009, dll.getLast());

        assertEquals(3, dll.get(0));
        assertEquals(5, dll.get(1));
        assertEquals(20009, dll.get(2));
    }

    @Test
    void testAddDuplicates(){
        DeduplicatedLinkedList<Integer> dll = new DeduplicatedLinkedList<>();

        dll.add(3);
        dll.add(200);
        dll.addFirst(5);
        dll.addLast(500);

        assertFalse(dll.add(3));
        assertFalse(dll.add(200));
        assertFalse(dll.add(500));

        assertEquals(4, dll.size());
        assertEquals(5, dll.getFirst());
        assertEquals(500, dll.getLast());
    }

    @Test
    void testRemove(){
        DeduplicatedLinkedList<Integer> dll = new DeduplicatedLinkedList<>();

        dll.add(2);
        dll.add(3);
        dll.add(4);
        dll.add(5);

        assertTrue(dll.remove(2));
        assertFalse(dll.contains(2));

        assertEquals(0, dll.indexOf(3));
        assertEquals(1, dll.indexOf(4));
        assertEquals(2, dll.indexOf(5));

        dll.remove(3);
        assertEquals(4, dll.remove());
        dll.remove(5);

        assertTrue(dll.isEmpty());
    }
}
