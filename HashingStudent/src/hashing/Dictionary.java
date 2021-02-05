/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashing;

import java.util.ArrayList;

/**
 *
 * @author dw
 */
public class Dictionary implements IHash {
    private final int MAX_SIZE = 11;
    private KeyValuePair[] hashTable;
    private int filledSlots;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dictionary h = new Dictionary();
        h.add(54, "Bill");
        System.out.println(h.toString());
    }    
    public Dictionary(){
        hashTable = new KeyValuePair[MAX_SIZE];
        filledSlots = 0;
    }
    public String[] asArray(){
        String[] output = new String[MAX_SIZE];
        for(int i = 0; i<MAX_SIZE;i++){
            output[i] = (hashTable[i]==null)? null:hashTable[i].value();
        }
        return output;
    }    
    /**Add a key-value pair to the dictionary*/
    @Override
    public void add(int key, String value) {
        if(filledSlots == MAX_SIZE){
            throw new UnsupportedOperationException("Dictionary full");
        }
        int index = hash(key);
        while(hashTable[index]!=null && hashTable[index].key()!=key){
            index=(index+1)%MAX_SIZE;
        }
        if(hashTable[index]!=null){
            throw new IllegalArgumentException("Key already in dictionary");
        }
        hashTable[index] = new KeyValuePair(key, value);
        filledSlots++;
    }
    /**Returns the value associated with the given key*/
    @Override
    public String item(int key) {
        int index = indexOfKey(key);
        if(index == -1){
            throw new IllegalArgumentException("Key Not In Dictionary");
        }
        return hashTable[index].value();
    }
    /**Change the value associated with a key*/
    public void setValue(int key, String newValue){
        int index = indexOfKey(key);
        if(index == -1){
            throw new IllegalArgumentException("Key Not In Dictionary");
        }
        hashTable[index].value(newValue);
    }
    @Override
    public void delete(int key) {
        int removedIndex = indexOfKey(key);
        if(removedIndex==-1){
            throw new IllegalArgumentException("Key not in dictionary");
        }
        hashTable[removedIndex] = null;
        filledSlots--;
        int currentIndex = (removedIndex+1)%MAX_SIZE;
        if(filledSlots == MAX_SIZE -1){
            currentIndex = removedIndex;
        }
        while(hashTable[currentIndex]!=null){
            currentIndex = (currentIndex+1)%MAX_SIZE;
        }
        currentIndex = (currentIndex+MAX_SIZE-1)%MAX_SIZE;
        while(hashTable[currentIndex]!=null&&
                ((currentIndex-removedIndex+MAX_SIZE)%MAX_SIZE > (currentIndex-hash(hashTable[currentIndex].key())+MAX_SIZE)%MAX_SIZE))
                //this boolean expression tests for if the 'ideal' position for the current item is between the removed slot and the current slot
                //if it is; then move on to the previous item
        {
            currentIndex = (currentIndex+MAX_SIZE-1)%MAX_SIZE;
        }
        if(hashTable[currentIndex]!=null){
            int tempKey = hashTable[currentIndex].key();
            String tempValue = hashTable[currentIndex].value();
            hashTable[removedIndex] = new KeyValuePair();
            delete(tempKey);
            filledSlots++;
            hashTable[removedIndex] = new KeyValuePair(tempKey,tempValue);
        }
        //fin
    }
    @Override
    public boolean contains(int key) {
        return (indexOfKey(key)!=-1);
    }
    /**Returns the index of a given key. If the key isn't found, -1 is returned*/
    private int indexOfKey(int key){
        int index = hash(key);
        while(hashTable[index] != null && hashTable[index].key()!=key){
            index= (index+1)%MAX_SIZE;
        }
        if(hashTable[index]==null){
            index = -1;
        }
        return index;
    }
    /**Returns how many slots are filled up*/
    @Override
    public int length() {
        return filledSlots;
    }
    /**Returns true if the hash table is empty*/
    @Override
    public boolean isEmpty() {
        return (filledSlots == 0);
    }
    /**Returns the hash of a given key*/
    private int hash(int key){
        return key%MAX_SIZE;
    }
    /**Returns the load factor of the hash table*/
    public float loadFactor(){
        return (float)filledSlots/(float)MAX_SIZE;
    }
    /**Returns the max size of the hash table*/
    public int maxSize(){
        return MAX_SIZE;
    }
    @Override
    public String toString(){
        String output = "[";
        for(KeyValuePair pair : hashTable){
            output += (pair == null)?"BLANK,":"("+pair.key()+","+pair.value()+"),";
        }
        output = output.substring(0, output.length()-1) + "]";
        return output;
    }
}
