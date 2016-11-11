/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.svc.topology.inter.parser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author TSOC
 */
public class TopologyPortType {
    private String Id;
    private String name;
    private HashMap<String, ArrayList<String>> bidirectionalPort;

    public TopologyPortType() {
        bidirectionalPort = new HashMap<String, ArrayList<String>>();
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }
    public HashMap<String, ArrayList<String>> getBidirectionalPort() {
        return bidirectionalPort;
    }
    
    
}
