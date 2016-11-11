/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.OWLTypes;

import java.util.ArrayList;

/**
 *
 * @author TSOC
 */
public class NE_PathType {
    private String path_name;
    private String source;
    private String destination;
    private int weight;
    private int linkCount;
    private ArrayList<NE_LinkType> linkList;

    public int getLinkCount() {
        return linkCount;
    }

    public void setLinkCount(int linkCount) {
        this.linkCount = linkCount;
    }

    public NE_PathType() {
        linkList = new ArrayList<NE_LinkType>();
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPath_name(String path_name) {
        this.path_name = path_name;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDestination() {
        return destination;
    }

    public ArrayList<NE_LinkType> getLinkList() {
        return linkList;
    }

    public String getPath_name() {
        return path_name;
    }

    public String getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }
    
    
}
