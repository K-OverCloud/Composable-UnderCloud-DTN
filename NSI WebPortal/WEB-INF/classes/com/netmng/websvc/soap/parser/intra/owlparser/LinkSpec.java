/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.owlparser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author TSOC
 * @see PathSpec의 경우 OWL의 FixedPath가 아닌 이상 Link로서 사용됨.
 */
public class LinkSpec {

    private String LinkName;
    private String Bidirectional;
    private int AtoBBW;
    private int BtoABW;
    private int Latency;
    private ArrayList<DuplicatedMapInt> hasNetworkEquipment;
    private PathType type;
    
    public LinkSpec() {
        hasNetworkEquipment = new ArrayList<DuplicatedMapInt>();
    }
    
    public static enum PathType {
        Link, FixedPath;
    }

    public PathType getType() {
        return type;
    }

    public void setLinkName(String LinkName) {
        this.LinkName = LinkName;
    }

    public String getLinkName() {
        return LinkName;
    }

    public void setType(PathType type) {
        this.type = type;
    }
    
    public static enum ObjectProperties {
        hasNetworkEquipment;
    }

    public static enum DataProperties {
        AtoBBandwidth, BtoABandwidth, Latency, Bidirectional,
        Fixed_Source, Fixed_Destination
    }

    public static enum AnnotationProperties {
        Order;
    }

    public void setBidirectional(String Bidirectional) {
        this.Bidirectional = Bidirectional;
    }

    public String getBidirectional() {
        return Bidirectional;
    }
    
    public void addHasNetworkEquipment(int order, String NEName) {
        this.hasNetworkEquipment.add(new DuplicatedMapInt(NEName, order));
    }

    public ArrayList<DuplicatedMapInt> getHasNetworkEquipment() {
        return hasNetworkEquipment;
    }

    public void setHasNetworkEquipment(ArrayList<DuplicatedMapInt> hasNetworkEquipment) {
        this.hasNetworkEquipment = hasNetworkEquipment;
    }

    public void setAtoBBW(int AtoBBW) {
        this.AtoBBW = AtoBBW;
    }

    public void setBtoABW(int BtoABW) {
        this.BtoABW = BtoABW;
    }

    public void setLatency(int Latency) {
        this.Latency = Latency;
    }

    public int getAtoBBW() {
        return AtoBBW;
    }

    public int getBtoABW() {
        return BtoABW;
    }

    public int getLatency() {
        return Latency;
    }
}
