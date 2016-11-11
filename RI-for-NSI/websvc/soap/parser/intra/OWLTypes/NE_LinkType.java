/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.OWLTypes;

/**
 *
 * @author TSOC
 */
public class NE_LinkType {
    private String NE_A;
    private String NE_B;
    private String bidirectional;
    private String OWL_linkName;
    private int latency;
    private int AtoBBW;
    private int BtoABW;
    private int order;
    private String linkName;

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkName() {
        return linkName;
    }
    
    public void setAtoBBW(int AtoBBW) {
        this.AtoBBW = AtoBBW;
    }

    public void setBidirectional(String bidirectional) {
        this.bidirectional = bidirectional;
    }

    public void setBtoABW(int BtoABW) {
        this.BtoABW = BtoABW;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public void setNE_A(String NE_A) {
        this.NE_A = NE_A;
    }

    public void setNE_B(String NE_B) {
        this.NE_B = NE_B;
    }

    public void setOWL_linkName(String OWL_linkName) {
        this.OWL_linkName = OWL_linkName;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getAtoBBW() {
        return AtoBBW;
    }

    public String getBidirectional() {
        return bidirectional;
    }

    public int getBtoABW() {
        return BtoABW;
    }

    public int getLatency() {
        return latency;
    }

    public String getNE_A() {
        return NE_A;
    }

    public String getNE_B() {
        return NE_B;
    }

    public String getOWL_linkName() {
        return OWL_linkName;
    }

    public int getOrder() {
        return order;
    }
}
