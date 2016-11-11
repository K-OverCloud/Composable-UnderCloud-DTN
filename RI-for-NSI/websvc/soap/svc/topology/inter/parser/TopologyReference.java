/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.svc.topology.inter.parser;

/**
 *
 * @author TSOC
 */
public class TopologyReference {
    private String topologyName;
    private String deployAddress;

    public TopologyReference() {
    }
    
    public void setTopologyName(String networkName) {
        this.topologyName = networkName;
    }

    public void setDeployAddress(String deployAddress) {
        this.deployAddress = deployAddress;
    }

    public String getTopologyName() {
        return topologyName;
    }

    public String getDeployAddress() {
        return deployAddress;
    }

    
}
