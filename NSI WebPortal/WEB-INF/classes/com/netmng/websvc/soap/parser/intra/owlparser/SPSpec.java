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
public class SPSpec {
    private String NSAName;
    private String STPLocalID;
    private String Description;
    private String NEName;
    private SPType Type;
    
    private ArrayList<String> connectedTo; //NSAName 형식적 사용
    private ArrayList<DuplicatedMap> connectedTo_External;

    public SPSpec() {
        connectedTo = new ArrayList<String>();
        connectedTo_External = new ArrayList<DuplicatedMap>();
    }
    
    public void addConnectedTo(String connectedTo) {
        this.connectedTo.add(connectedTo);
    }

    public ArrayList<String> getConnectedTo() {
        return connectedTo;
    }
    
    public static enum SPType{
        SDP, STP, ExternalSTP;
    }
    
    public static enum ObjectProperties{
        connectedTo;
    }
    
    public static enum ObjectProperties_Annotation{
        stp, nsa;
    }
    
    public static enum DataProperties{
        Description, NEName, NSAName, STP_LocalID, Option;
    }

    public SPType getType() {
        return Type;
    }

    public void setConnectedTo(ArrayList<String> connectedTo) {
        this.connectedTo = connectedTo;
    }    

    public ArrayList<DuplicatedMap> getConnectedTo_External(){
        return connectedTo_External;
    }
    
    public boolean searchConnectedTo_External(String stp_localid){
        for(DuplicatedMap STPESet : connectedTo_External){
            if(STPESet.value.equals(stp_localid)){
                return true;
            }
        }
        
        return false;
    }
    
    public void setType(SPType Type) {
        this.Type = Type;
    } 
    
    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setNEName(String NEName) {
        this.NEName = NEName;
    }

    public void setNSAName(String NSAName) {
        this.NSAName = NSAName;
    }

    public void setSTPLocalID(String STPLocalID) {
        this.STPLocalID = STPLocalID;
    }

    public String getDescription() {
        return Description;
    }

    public String getNEName() {
        return NEName;
    }

    public String getNSAName() {
        return NSAName;
    }

    public String getSTPLocalID() {
        return STPLocalID;
    }
    
}