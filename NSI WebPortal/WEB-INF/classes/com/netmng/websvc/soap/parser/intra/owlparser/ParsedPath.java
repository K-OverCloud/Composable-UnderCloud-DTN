/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.owlparser;

import java.util.ArrayList;

/**
 *
 * @author TSOC
 */
public class ParsedPath implements Cloneable {

    ArrayList<String> nelist;
    int accumulatedWeight;

    public ParsedPath() {
        nelist = new ArrayList<String>();
    }

    public void setAccumulatedWeight(int accumulatedWeight) {
        this.accumulatedWeight = accumulatedWeight;
    }

    public void addAccumulatedWeight(int accumulatedWeight) {
        this.accumulatedWeight += accumulatedWeight;
    }

    public int getAccumulatedWeight() {
        return accumulatedWeight;
    }

    public void addList(String NEName) {
        nelist.add(NEName);
    }

    public ArrayList<String> getNelist() {
        return nelist;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ParsedPath ncp = new ParsedPath();
        ncp.accumulatedWeight = this.accumulatedWeight;
        for (String value : this.nelist) {
            ncp.addList(value);
        }
        return ncp;
    }
}