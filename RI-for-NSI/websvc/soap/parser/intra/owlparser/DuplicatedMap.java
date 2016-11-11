/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.owlparser;

/**
 *
 * @author TSOC
 */

public class DuplicatedMap{
    public String key;
    public String value;
    
    public DuplicatedMap(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public DuplicatedMap() {
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    
}
