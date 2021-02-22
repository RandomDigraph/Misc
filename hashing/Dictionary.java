/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author dw
 * @param <K> the class type of the keys
 * @param <V> the class type of the values
 */ 
public class Dictionary<K,V> implements IHash<K,V> {
    private final int MAX_SIZE;
    private KeyValuePair<K,V>[] hashTable;
    private int filledSlots;
    /*** @param args the command line arguments*/
    
    public Dictionary(int size){
        MAX_SIZE = size;
        hashTable = (KeyValuePair<K,V>[]) new KeyValuePair[MAX_SIZE];
        ArrayList<?>[] list = new ArrayList<?>[10];
        KeyValuePair<?,?>[] array = new KeyValuePair[MAX_SIZE];
        filledSlots = 0;
    }
        
    /**Add a key-value pair to the dictionary*/
    @Override
    public void add(K key, V value) {
        if(filledSlots == MAX_SIZE) throw new UnsupportedOperationException("Dictionary full");
        
        int index = hash(key);
        
        while(hashTable[index]!=null && !hashTable[index].key().equals(key)){
            index=incr(index);
        }
        if(hashTable[index]!=null) throw new IllegalArgumentException("Key already in dictionary");
        hashTable[index] = new KeyValuePair<K,V>(key, value);
        filledSlots++;
    }
    /**Returns the value associated with the given key*/
    @Override
    public V item(K key) {
        int index = indexOfKey(key);
        if(index == -1) throw new IllegalArgumentException("Key Not In Dictionary");
        return hashTable[index].value();
    }
    /**Change the value associated with a key*/
    public void setValue(K key, V newValue){
        int index = indexOfKey(key);
        if(index == -1) throw new IllegalArgumentException("Key Not In Dictionary");
        hashTable[index].value(newValue);
    }
    @Override
    public void delete(K key) {
        int indexToDelete = indexOfKey(key);
        if(indexToDelete==-1){
            throw new IllegalArgumentException("Key not in dictionary");
        }
        deleteIndex(indexToDelete);
        filledSlots--;
    }
    /**Returns true if the dictionary contains the given key*/
    @Override
    public boolean contains(K key) {
        return (indexOfKey(key)!=-1);
    }
    
    
    /**Returns a list of keys*//*
    public ArrayList<K> getKeys(){
        ArrayList<K> keys = new ArrayList<>();
        for(KeyValuePair<K,V> pair : hashTable){
            if(pair!=null){
                keys.add(pair.key());
            }
        }
        return keys;
    }*/
    /**Returns how many slots are filled up*/
    @Override
    public int length() {
        return filledSlots;
    }
    @Override
    public boolean isFull(){
        return(filledSlots == MAX_SIZE);
    }
    @Override
    public boolean isEmpty() {
        return (filledSlots == 0);
    }
    @Override
    /**Returns the load factor of the hash table*/
    public float loadFactor(){
        return (float)filledSlots/(float)MAX_SIZE;
    }
    @Override
    /**Returns the max size of the hash table*/
    public int maxSize(){
        return MAX_SIZE;
    }
    
    public String[] asArray(){
        String[] output = new String[MAX_SIZE];
        for(int i = 0; i<MAX_SIZE;i++){
            output[i] = (hashTable[i]==null)? null:hashTable[i].toString();
        }
        return output;
    }
    @Override
    public String toString(){
        /*String output = "[";
        for(KeyValuePair pair : hashTable){
            output += (pair == null)?"BLANK,":"("+pair.key()+","+pair.value()+"),";
        }
        output = output.substring(0, output.length()-1) + "]";
        return output;*/
        
        String[] items = new String[filledSlots];
        int i = 0;
        for(KeyValuePair pair : hashTable){
            if(pair!=null){
                items[i] = pair.toString(); //key:value
                i++;
            }
        }
        Arrays.sort(items);
        
        String output = "{";
        for(String s : items){
            output+=s+",";
        }
        
        output = output.substring(0,output.length()-1);
        output+="}";
        return output;
    }
    
    /**Returns the hash of a given key*/
    private int hash(K key){
        char[] keyChars = key.toString().toCharArray();
        int sum = 0;
        for(char c : keyChars) sum+=(int)c;
        return sum%MAX_SIZE;
    }
    /**Increments the inputted number, but sets to zero if the end of array is reached*/
    private int incr(int index){
        return (index+1)%MAX_SIZE;
    }
    /**Decrements the inputted number, but sets to the end of the array if the start of array is reached*/
    private int decr(int index){
        return (index+MAX_SIZE-1)%MAX_SIZE;
    }
    /**Returns how many indexes you have to increment by to get from the {@code startIndex} to the {@code endIndex}*/
    private int dist(int startIndex, int endIndex){
        return (endIndex-startIndex+MAX_SIZE)%MAX_SIZE;
    }
    /**Returns the index of a given key. If the key isn't found, -1 is returned*/
    private int indexOfKey(K key){
        int index = hash(key);
        while(hashTable[index] != null && !hashTable[index].key().equals(key)){
            index = incr(index);
        }
        if(hashTable[index]==null) index = -1;
        return index;
    }
    /**Returns the 'ideal' position of the item at the given index*/
    private int idealPos(int index){
        return hash((K) hashTable[index].key());
    }
    /**Private function for deleting items at a certain index*/
    private void deleteIndex(int indexToDelete){
        int indexToMove;
        if (isFull()){
            indexToMove = decr(indexToDelete);
        }
        else{
            indexToMove = indexToDelete;
            while(hashTable[incr(indexToMove)]!=null){
                indexToMove = incr(indexToMove);
            }
        }
        //now currentIndex is at the last index of the 'block' started at the index to be removed
        
        while(indexToMove!=indexToDelete && (dist(idealPos(indexToMove),indexToMove) < dist(idealPos(indexToMove),indexToDelete))){
            //the second expression tests for whether the removed index won't be found before the item at indexToMove, when traversing the hashtable
            indexToMove = decr(indexToMove);
        }
        if(indexToMove==indexToDelete){//i.e. no moving needs to be done
            hashTable[indexToDelete] = null;
        }
        else{//item at indexToMove needs to be moved to indexToDelete
            hashTable[indexToDelete] = hashTable[indexToMove];
            deleteIndex(indexToMove);
        }
    }
    
}
