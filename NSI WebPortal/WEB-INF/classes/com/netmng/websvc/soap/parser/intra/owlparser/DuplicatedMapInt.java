/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.owlparser;

/**
 *
 * @author TSOC
 */
public class DuplicatedMapInt{
    public String key;
    public int value;

    public DuplicatedMapInt(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
    
}