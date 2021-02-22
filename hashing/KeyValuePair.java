/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashing;

/**
 *
 * @author Quack <Quack.a@school.horse>
 */
public class KeyValuePair<K,V>{
    private K key;
    private V value;
    /**Makes a filled KeyValuePair with items*/
    public KeyValuePair(K key, V value){
        this.key = key;
        this.value = value;
    }
    public K key(){
        return key;
    }
    public V value(){
        return value;
    }
    public void value(V newValue){
        value = newValue;
    }
    public String toString(){
        return key.toString()+":"+value.toString();
    }
}
