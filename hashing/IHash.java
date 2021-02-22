/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashing;

import java.lang.String;
/**
 *
 * @author dw
 */
public interface IHash<K,V> {
    void add(K Key, V Value);
    V item(K Key);
    boolean contains(K Key);
    void delete(K Key);
    
    int length();
    boolean isEmpty();  
    boolean isFull();  
    
    int maxSize();
    float loadFactor();
}
