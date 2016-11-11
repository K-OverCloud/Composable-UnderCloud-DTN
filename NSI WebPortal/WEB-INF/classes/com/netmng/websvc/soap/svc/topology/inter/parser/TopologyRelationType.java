/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.svc.topology.inter.parser;

import java.util.ArrayList;

/**
 *
 * @author TSOC
 */
public class TopologyRelationType {
    private String type;
    private String portGroup;
    private ArrayList<Integer> vlanLength;
    private ArrayList<TopologyRelationType> relation;
}
