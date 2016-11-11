/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.OWLTypes;

/**
 *
 * @author TSOC
 */
public enum DataPlaneStateEnumType {
    Active, Inactive, Unknown;
    
    public static DataPlaneStateEnumType fromValue(String value){
        if(Active.name().equals(value)){
            return Active;
        }else if(Inactive.name().equals(value)){
            return Inactive;
        }else if(Unknown.name().equals(value)){
            return Unknown;
        }else{
            return null;
        }
    }
}
