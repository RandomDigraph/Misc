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
public class KeyValuePair {
    private int key;
    private String value;
    public KeyValuePair(){
        key = -1;
        value = "DELETED";
    }
    /**Makes a filled KeyValuePair with items*/
    public KeyValuePair(int key, String value){
        this.key = key;
        this.value = value;
    }
    public int key(){
        return key;
    }
    public String value(){
        return value;
    }
    public void value(String newValue){
        value = newValue;
    }
}
