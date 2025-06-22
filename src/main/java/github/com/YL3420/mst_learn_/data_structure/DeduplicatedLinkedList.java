package github.com.YL3420.mst_learn_.data_structure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class DeduplicatedLinkedList<EntryType> implements Iterable<EntryType> {

    private LinkedList<EntryType> list;
    private HashSet<EntryType> set;

    public DeduplicatedLinkedList(){
        list = new LinkedList<>();
        set = new HashSet<>();
    }

    public DeduplicatedLinkedList(DeduplicatedLinkedList<EntryType> copyList){
        list = copyList.getList();
        set = copyList.getSet();
    }

    public boolean add(EntryType entry){
        if(set.add(entry)){
            list.add(entry);
            return true;
        }
        return false;
    }

    public boolean addFirst(EntryType entry){
        if(set.add(entry)){
            list.addFirst(entry);
            return true;
        }
        return false;
    }

    public boolean addLast(EntryType entry){
        if(set.add(entry)){
            list.addLast(entry);
            return true;
        }
        return false;
    }

    public boolean contains(EntryType entry){
        return set.contains(entry);
    }

    public boolean remove(EntryType entry){
        list.remove();
        return set.remove(entry);
    }

    public EntryType remove(){
        EntryType removed = list.remove();
        set.remove(removed);
        return removed;
    }

    public int indexOf(EntryType entry){
        return list.indexOf(entry);
    }

    public int size(){
        return list.size();
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public EntryType get(int index){
        return list.get(index);
    }

    public EntryType getFirst(){
        return list.getFirst();
    }

    public EntryType getLast(){
        return list.getLast();
    }

    public LinkedList<EntryType> getList(){
        return new LinkedList<>(this.list);
    }

    public HashSet<EntryType> getSet(){
        return new HashSet<>(this.set);
    }

    public Iterator<EntryType> iterator(){
        return list.iterator();
    }
}
