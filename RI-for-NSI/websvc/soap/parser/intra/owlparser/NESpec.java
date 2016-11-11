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
 */
public class NESpec {
    private String NEName;
    private String DeviceName;
    private String DeviceVendor;
    private String ID;
    private String IP;
    private String Protocol;
    private String Port;
    private String PW;

    private ArrayList<DuplicatedMapInt> connectedTo;

    public NESpec() {
        connectedTo = new ArrayList<DuplicatedMapInt>();
    }
    
    public static enum ObjectProperties{
        connectedTo;
    }
    
    public static enum DataProperties{
        DeviceName, DeviceVendor, ConnectionInfo, PW;
    }
    
    public static enum AnnotationProperties{
        Weight;
    }

    public void addConnectedTo(int weight, String connectedTo) {
        this.connectedTo.add(new DuplicatedMapInt(connectedTo, weight));
    }

    public ArrayList<DuplicatedMapInt> getConnectedTo() {
        return connectedTo;
    }
    
    public void setDeviceName(String DeviceName) {
        this.DeviceName = DeviceName;
    }

    public void setDeviceVendor(String DeviceVendor) {
        this.DeviceVendor = DeviceVendor;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setNEName(String NEName) {
        this.NEName = NEName;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public String getDeviceVendor() {
        return DeviceVendor;
    }

    public String getID() {
        return ID;
    }

    public String getIP() {
        return IP;
    }

    public String getNEName() {
        return NEName;
    }

    public String getPW() {
        return PW;
    }

    public void setProtocol(String Protocol) {
        this.Protocol = Protocol;
    }

    public String getProtocol() {
        return Protocol;
    }

    public void setPort(String Port) {
        this.Port = Port;
    }

    public String getPort() {
        return Port;
    }

}
