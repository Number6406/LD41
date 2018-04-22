/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ld41.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *
 * @author Number6406
 */
public class Attributes {
    
    // ATTRIBUTES DATA
    
    public enum AttributeList {
        STRENGTH,
        STAMINA,
        DEFENSE,
        DEXTERITY,
        INTELLIGENCE,
        LUCK,
        PERCEPTION
    }
    
    Map<AttributeList, Integer> dataset;
    
    public Attributes() {
        
        dataset = new HashMap<AttributeList, Integer>();
        for (AttributeList attribute : AttributeList.values()) {
            dataset.put(attribute, 0);
        }
        
    }
    
    public int get(AttributeList index) {
        return dataset.get(index);
    }
    
    void set(AttributeList index, int value) {
        dataset.put(index,value);
    }
    
    public void increment(AttributeList index) {
        dataset.replace(index, dataset.get(index), dataset.get(index)+1);
    }
    
    public int getValue() {
        int val = 0;
        for (AttributeList index : AttributeList.values()) {
            val += dataset.get(index);
        }
        return val;
    }
    
}
