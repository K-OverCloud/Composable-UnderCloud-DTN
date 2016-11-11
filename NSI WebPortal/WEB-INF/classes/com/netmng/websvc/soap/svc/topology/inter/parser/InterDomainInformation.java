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
public class InterDomainInformation {
    private String nsa; //NSA명
    private String adminContact; //관리자 연락처
    private String endPointAddress; //웹 서비스 엔드포인주 주소(ex : nsi2.kisti.re.kr)
    private String networkId; //STP - 네트워크 ID
    private String nsaType;
    private ArrayList<String> peersWith = new ArrayList<String>();
    
    private ArrayList<HashMap<String , ArrayList<String>>> bidirectionalPort = null;
    private HashMap<String , String> inboundPort = null; //port, vlanLabel
    private HashMap<String , String> outboundPort = null; //port, vlanLabel
    
    
    public InterDomainInformation() {
        this.bidirectionalPort = new ArrayList<HashMap<String , ArrayList<String>>>();
        this.inboundPort = new HashMap<String , String>();
        this.outboundPort = new HashMap<String , String>();
    }

    public ArrayList<HashMap<String, ArrayList<String>>> getBidirectionalPort() {
        return bidirectionalPort;
    }
    
    public void setAdminContact(String adminContact) {
        this.adminContact = adminContact;
    }

    public void setEndPointAddress(String endPointAddress) {
        this.endPointAddress = endPointAddress;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public void setNsa(String nsa) {
        this.nsa = nsa;
    }

    public String getAdminContact() {
        return adminContact;
    }

    public String getEndPointAddress() {
        return endPointAddress;
    }

    public String getNetworkId() {
        return networkId;
    }

    public String getNsa() {
        return nsa;
    }

    public void setNsaType(String type) {
        this.nsaType = type;
    }

    public void addPeersWith(String id) {
        peersWith.add(id);
    }

    public ArrayList<String> getPeersWith() {
        return peersWith;
    }

    public String getNsaType() {
        return nsaType;
    }

    public HashMap<String, String> getInboundPort() {
        return inboundPort;
    }

    public HashMap<String, String> getOutboundPort() {
        return outboundPort;
    }

    public void setInboundPort(HashMap<String, String> inboundPort) {
        this.inboundPort = inboundPort;
    }

    public void setOutboundPort(HashMap<String, String> outboundPort) {
        this.outboundPort = outboundPort;
    }


    
    
    
}
