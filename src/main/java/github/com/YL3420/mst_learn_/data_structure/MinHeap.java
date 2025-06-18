package github.com.YL3420.mst_learn_.data_structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MinHeap<KeyType> {

    /*
        each node in minheap has its key value as well as the priority
        that determines its placement in the heap
     */
    public record Entry<KeyType> (KeyType key, double priority){

    }


    /*
        a heap representation of the min heap binary tree
        leftmost is the root node, and we continue to fill the leaf layer of the tree graph
        with the following entries. min heap is a binary tree, so each parent
        has at most two children
     */
    private ArrayList<Entry<KeyType>> heapList;

    /*
        maps each node in the min heap to its index in the list rep of minheap
        by its key value
     */
    private HashMap<KeyType, Integer> keyToIndex;

    public MinHeap(){
        heapList = new ArrayList<>();
        keyToIndex = new HashMap<>();
    }

    public boolean isEmpty(){
        return heapList.isEmpty();
    }

    public int size(){
        return heapList.size();
    }

    /*
        get the value of the highest priority value
     */
    public KeyType peek(){
        assert !heapList.isEmpty();
        return heapList.getFirst().key();
    }

    /*
        utility to maintain order invariant of min heap
     */
    private void bubbleUp(int keyIndex, double priority){
        int currIndex = (keyIndex-1)/2;
        double currPriority = heapList.get(currIndex).priority();
        if(currPriority <= priority) return;

        swap(currIndex, keyIndex);
        bubbleUp(currIndex, priority);
    }

    /*
        utility to maintain order invariant of min heap
     */
    private void bubbleDown(int keyIndex, double priority){
        Entry<KeyType> left = !(2 * keyIndex + 1 >= size()) ? heapList.get(2 * keyIndex + 1)
                : null;
        Entry<KeyType> right = !(2 * keyIndex + 2 >= size()) ? heapList.get(2 * keyIndex + 2)
                : null;
        if(left == null && right == null){
            return;
        }

        Entry<KeyType> childMax = left;
        if(right != null && right.priority() < left.priority()) childMax = right;

        if(childMax.priority() < priority){
            int childIndex = keyToIndex.get(childMax.key());
            swap(childIndex, keyToIndex.get(heapList.get(keyIndex).key()));
            bubbleDown(childIndex, priority);
        } else {
            return;
        }

    }

    /*
        utility to swap two elements in heapList and update the keyToIndex hashmap
     */
    private void swap(int i, int j){
        KeyType kI = heapList.get(i).key();
        KeyType kJ = heapList.get(j).key();

        Collections.swap(heapList, i, j);
        keyToIndex.put(kI, j);
        keyToIndex.put(kJ, i);
    }


    /*
        add new element
     */
    private void add(KeyType key, double priority){
        heapList.add(new Entry<>(key, priority));
        keyToIndex.put(key, heapList.size()-1);

        bubbleUp(heapList.size()-1, priority);
    }


    /*
        update priority for an existing element
     */
    private void update(KeyType key, double priority){
        int keyIndex = keyToIndex.get(key);
        heapList.set(keyIndex, new Entry<>(key, priority));
        bubbleUp(keyIndex, priority);
        keyIndex = keyToIndex.get(key);
        bubbleDown(keyIndex, priority);
    }

    /*
        client method for add or update
     */
    public void addOrUpdate(KeyType key, double priority){
        if(keyToIndex.containsKey(key)) update(key, priority);
        else add(key, priority);
    }


    /*
        remove and return the most prioritized key in the heap
        then reorder the heap
     */
    public KeyType extractMin(){
        if(isEmpty()) return null;

        swap(0, size()-1);
        keyToIndex.remove(heapList.getLast().key());
        Entry<KeyType> removed = heapList.removeLast();

        if(size()!=0)
            bubbleDown(0, heapList.getFirst().priority());

        return removed.key();
    }
}