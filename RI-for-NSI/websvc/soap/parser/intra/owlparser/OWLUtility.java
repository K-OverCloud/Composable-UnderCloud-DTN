/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.owlparser;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;



/**
 *
 * @author TSOC
 */
public class OWLUtility {

    private OWLOntologyManager manager = null;
    private File f = null;
    private OWLOntology dynamicKL_Intra = null;

    private static ArrayList<NESpec> NESpecList = null;
    private static ArrayList<LinkSpec> LinkSpecList = null;
    private static ArrayList<SPSpec> SPSpecList = null;

    public OWLUtility() {
        try {
            manager = OWLManager.createOWLOntologyManager();
            f = new File(this.getClass().getResource("").getPath()+"dynamicKL_Intra_v1.2.owl");
            //f = new File("C:\\Users\\TSOC\\Documents\\NetBeansProjects\\NSI_v2.9.1\\src\\java\\OWLParser\\dynamicKL_Intra_v1.2.owl");
            dynamicKL_Intra = manager.loadOntologyFromOntologyDocument(f);
            
            NESpecList = new ArrayList<NESpec>();
            LinkSpecList = new ArrayList<LinkSpec>();
            SPSpecList = new ArrayList<SPSpec>();
            
            parsingOWL();
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OWLUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public OWLUtility(String s) {
        try {
            //System.out.println("=====>> "+this.getClass().getResource("").getPath()+"dynamicKL_Intra_v1.2.owl");
            manager = OWLManager.createOWLOntologyManager();
            f = new File(s);
            //f = new File("C:\\Users\\TSOC\\Documents\\NetBeansProjects\\NSI_v2.9.1\\src\\java\\OWLParser\\dynamicKL_Intra_v1.2.owl");
            dynamicKL_Intra = manager.loadOntologyFromOntologyDocument(f);
            
            NESpecList = new ArrayList<NESpec>();
            LinkSpecList = new ArrayList<LinkSpec>();
            SPSpecList = new ArrayList<SPSpec>();
            
            parsingOWL();
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OWLUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<NESpec> getNetworkEquipment() {
        return NESpecList;
    }

    public ArrayList<SPSpec> getServicePoint() {
        return SPSpecList;
    }

    public ArrayList<LinkSpec> getLinkSpec() {
        return LinkSpecList;
    }

    private enum OWLClassEnum {
        FixedPath, NetworkEquipment, Link, SDP, STP, ExternalSTP;
    }

    public static ArrayList<NESpec> getNESpecList() {
        return NESpecList;
    }

    public static ArrayList<SPSpec> getSPSpecList() {
        return SPSpecList;
    }

    public static ArrayList<LinkSpec> getLinkSpecList() {
        return LinkSpecList;
    }
    

    public void printResult(){
        System.out.println("=============== NetworkEquipment ===============");
        for(NESpec ne : NESpecList){
            System.out.println(ne.getNEName());
            System.out.println(ne.getDeviceName());
            System.out.println(ne.getDeviceVendor());
            System.out.println(ne.getProtocol() + " / " + ne.getPort() + " / " + ne.getIP() + " / " + ne.getID() + " / " + ne.getPW());
            for(DuplicatedMapInt dmi : ne.getConnectedTo()){
                System.out.println(NESpec.ObjectProperties.connectedTo.name() + " : " + dmi.key + " / " + NESpec.AnnotationProperties.Weight.name() + " : " + dmi.value);
            }
            System.out.println("----- Next -----");
        }
        System.out.println("=============== SDP or STP ===============");
        for(SPSpec sp : SPSpecList){
            System.out.println(sp.getNEName());
            System.out.println(sp.getNSAName());
            System.out.println(sp.getSTPLocalID());
            System.out.println(sp.getType().name());
            System.out.println(sp.getDescription());
            for(String ct : sp.getConnectedTo()){
                System.out.println(SPSpec.ObjectProperties.connectedTo.name() + " : " + ct);
            }
            for(DuplicatedMap stpe : sp.getConnectedTo_External()){
                System.out.println(SPSpec.ObjectProperties.connectedTo.name() + " : [" + stpe.key + "] -> [" + stpe.value + "]");
            }
            System.out.println("----- Next -----");
        }
        System.out.println("=============== Path or FixedPath ===============");
        for(LinkSpec ps : LinkSpecList){
            System.out.println(ps.getLinkName());
            System.out.println(ps.getType().name());
            System.out.println(ps.getAtoBBW());
            System.out.println(ps.getBtoABW());
            System.out.println(ps.getBidirectional());
            System.out.println(ps.getLatency());            
            for(DuplicatedMapInt dmi : ps.getHasNetworkEquipment()){
                System.out.println(LinkSpec.ObjectProperties.hasNetworkEquipment.name() + " : " + dmi.key + " / " + LinkSpec.AnnotationProperties.Order.name() + " : " + dmi.value);
            }
            System.out.println("----- Next -----");
        }
        
    }
    
    private void parsingOWL() {
        ArrayList<OWLSourceData> owlsdlist = new ArrayList<OWLSourceData>();
        for (OWLNamedIndividual nIndividuals : dynamicKL_Intra.getIndividualsInSignature()) {
            //if (nIndividuals.toStringID().indexOf(SourceSDP) != -1) {                
            String individualClass = "";
            for (OWLClassExpression owlce : nIndividuals.getTypes(dynamicKL_Intra)) {
                individualClass = owlce.asOWLClass().getIRI().getFragment();
            }
            String individualName = nIndividuals.getIRI().getFragment();
            System.out.println("\n==> Individual : " + individualName + " / Class : " + individualClass);

            //OWLSourceDate 초기화
            OWLSourceData owlsd = new OWLSourceData();
            owlsd.individualName = individualName;
            owlsd.individualClass = individualClass;
            //OWLSourceDate 초기화 끝
            
            System.out.println("---------- ObjectProperty ----------");
            for (OWLObjectPropertyAssertionAxiom propertyAssertionAxiom : dynamicKL_Intra.getObjectPropertyAssertionAxioms(nIndividuals)) {
                OWLSourceDataSet objectProperties = new OWLSourceDataSet();
                for (OWLAnnotation annotation : propertyAssertionAxiom.getAnnotations()) {
                    String annotationProperty = annotation.getProperty().getIRI().getFragment();
                    String annotationValue = annotation.getValue().toString().replaceAll("\"", " ").trim();
                    System.out.printf("AnnotationProperty : %s / AnnotationValue : %s\n", annotationProperty, annotationValue);

                    objectProperties.annotation.add(new DuplicatedMap(annotationProperty, annotationValue));
                }
                for (OWLObjectProperty objectProperty : propertyAssertionAxiom.getObjectPropertiesInSignature()) {
                    String ObjectProperty = objectProperty.getIRI().getFragment();
                    System.out.println("ObjectProperty : " + ObjectProperty);
                    for (OWLNamedIndividual subIndividual : propertyAssertionAxiom.getIndividualsInSignature()) {
                        String ObjectValue = subIndividual.getIRI().getFragment();
                        System.out.println("ObjectValue : " + ObjectValue);
                        
                        if(!ObjectValue.equals(individualName)){
                            objectProperties.property = new DuplicatedMap(ObjectProperty, ObjectValue);
                        }
                    }
                }
                owlsd.objectProperty.add(objectProperties);
            }

            System.out.println("---------- DataProperty ----------");
            for (OWLDataPropertyAssertionAxiom dataAssertionAxiom : dynamicKL_Intra.getDataPropertyAssertionAxioms(nIndividuals)) {
                OWLSourceDataSet dataProperties = new OWLSourceDataSet();
                for (OWLAnnotation annotation : dataAssertionAxiom.getAnnotations()) {
                    String annotationProperty = annotation.getProperty().getIRI().getFragment();
                    String annotationValue = annotation.getValue().toString().replaceAll("\"", " ").trim();
                    System.out.printf("AnnotationProperty : %s / AnnotationValue : %s\n", annotationProperty, annotationValue);
                    
                    dataProperties.annotation.add(new DuplicatedMap(annotationProperty, annotationValue));
                }
                OWLDataPropertyExpression dataProerty = dataAssertionAxiom.getProperty();
                String DataProperty = dataProerty.asOWLDataProperty().getIRI().getFragment();
                System.out.println("DataProperty : " + DataProperty);
                for (OWLLiteral value : nIndividuals.getDataPropertyValues(dataProerty, dynamicKL_Intra)) {
                    String DataValue = value.getLiteral();
                    System.out.println("DataValue : " + DataValue);
                    
                    dataProperties.property = new DuplicatedMap(DataProperty, DataValue);
                }
                owlsd.dataProperty.add(dataProperties);
            }   

            owlsdlist.add(owlsd);
        }
        
        for (OWLSourceData owlsd : owlsdlist) {
            //SDP 및 STP일 경우 파싱
            if (owlsd.individualClass.equals(OWLClassEnum.SDP.name()) || owlsd.individualClass.equals(OWLClassEnum.STP.name())) {
                SPSpec sps = new SPSpec();
                sps.setType(SPSpec.SPType.valueOf(owlsd.individualClass));
                for (OWLSourceDataSet propertySet : owlsd.objectProperty) {
                    if(propertySet.property.key.equals(SPSpec.ObjectProperties.connectedTo.name())){
                        if(propertySet.property.value.equals("STPE")){
                            for(DuplicatedMap annotationSet : propertySet.annotation){                                
                                sps.getConnectedTo_External().add(new DuplicatedMap(propertySet.property.value, annotationSet.value));
                            }
                        }else{
                            sps.getConnectedTo().add(propertySet.property.value);
                        }
                    }
                }
                for (OWLSourceDataSet dataSet : owlsd.dataProperty) {
                    if (dataSet.property.key.equals(SPSpec.DataProperties.Description.name())) {
                        sps.setDescription(dataSet.property.value);
                    } else if (dataSet.property.key.equals(SPSpec.DataProperties.NEName.name())) {
                        sps.setNEName(dataSet.property.value);
                    } else if (dataSet.property.key.equals(SPSpec.DataProperties.NSAName.name())) {
                        sps.setNSAName(dataSet.property.value);
                    } else if (dataSet.property.key.equals(SPSpec.DataProperties.STP_LocalID.name())) {
                        sps.setSTPLocalID(dataSet.property.value);
                    } else if (dataSet.property.key.equals(SPSpec.DataProperties.Option.name())) {
                        //만약 옵션이 있다면 여기서 파싱. 예비!
                    }
                }
                SPSpecList.add(sps);
            } //Path 및 FixedPath일 경우 파싱
            else if (owlsd.individualClass.equals(OWLClassEnum.Link.name()) || owlsd.individualClass.equals(OWLClassEnum.FixedPath.name())) {
                LinkSpec ps = new LinkSpec();
                ps.setType(LinkSpec.PathType.valueOf(owlsd.individualClass));
                ps.setLinkName(owlsd.individualName);
                for (OWLSourceDataSet objectSet : owlsd.objectProperty) { //오브젝트 셋 추출
                    if(objectSet.property.key.equals(LinkSpec.ObjectProperties.hasNetworkEquipment.name())){
                        for(DuplicatedMap annoatationSet : objectSet.annotation){ //해당 오브젝트의 어노테이션 추출
                            if(annoatationSet.key.equals(LinkSpec.AnnotationProperties.Order.name())){ //특정 어노테이션만 추출하여 사용
                                ps.addHasNetworkEquipment(Integer.parseInt(annoatationSet.value), objectSet.property.value);
                            }
                        }
                    }
                }
                for (OWLSourceDataSet dataSet : owlsd.dataProperty) {
                    if (dataSet.property.key.equals(LinkSpec.DataProperties.AtoBBandwidth.name())) {
                        ps.setAtoBBW(Integer.parseInt(dataSet.property.value));
                    } else if (dataSet.property.key.equals(LinkSpec.DataProperties.BtoABandwidth.name())) {
                        ps.setBtoABW(Integer.parseInt(dataSet.property.value));
                    } else if (dataSet.property.key.equals(LinkSpec.DataProperties.Bidirectional.name())) {
                        ps.setBidirectional(dataSet.property.value);
                    } else if (dataSet.property.key.equals(LinkSpec.DataProperties.Latency.name())) {
                        ps.setLatency(Integer.parseInt(dataSet.property.value));
                    }/*else if(dm.key.equals(LinkSpec.DataProperties.Fixed_Source.name())){
                     ps.setNE_Source(dm.value);
                     }else if(dm.key.equals(LinkSpec.DataProperties.Fixed_Destination.name())){
                     ps.setNE_Destination(dm.value);
                     }*/
                }
                LinkSpecList.add(ps);
            } //NE일 경우 파싱
            else if (owlsd.individualClass.equals(OWLClassEnum.NetworkEquipment.name())) {
                NESpec nes = new NESpec();
                nes.setNEName(owlsd.individualName);
                for(OWLSourceDataSet objectSet : owlsd.objectProperty){
                    if(objectSet.property.key.equals(NESpec.ObjectProperties.connectedTo.name())){
                        for(DuplicatedMap annotationSet : objectSet.annotation){
                            if(annotationSet.key.equals(NESpec.AnnotationProperties.Weight.name())){
                                nes.addConnectedTo(Integer.parseInt(annotationSet.value), objectSet.property.value);
                            }
                        }
                    }
                }
                for (OWLSourceDataSet dataSet : owlsd.dataProperty) {
                    if (dataSet.property.key.equals(NESpec.DataProperties.DeviceName.name())) {
                        nes.setDeviceName(dataSet.property.value);
                    } else if (dataSet.property.key.equals(NESpec.DataProperties.DeviceVendor.name())) {
                        nes.setDeviceVendor(dataSet.property.value);
                    } else if (dataSet.property.key.equals(NESpec.DataProperties.ConnectionInfo.name())) {
                        String[] str_arr = dataSet.property.value.split("://");
                        String protocol = str_arr[0]; //protocl
                        str_arr = str_arr[1].split("@");
                        String id = str_arr[0]; //user
                        str_arr = str_arr[1].split(":");
                        String ip = str_arr[0]; //ip address
                        String port = str_arr[1]; //port

                        nes.setProtocol(protocol);
                        nes.setID(id);
                        nes.setIP(ip);
                        nes.setPort(port);
                    } else if (dataSet.property.key.equals(NESpec.DataProperties.PW.name())) {
                        nes.setPW(dataSet.property.value);
                    }
                }
                NESpecList.add(nes);
            }
        }
    }
    
    public static NESpec getNESpec(String NEName){
        for(NESpec nes : NESpecList){
            if(nes.getNEName().equals(NEName)){
                return nes;
            }
        }
        return null;
    }
    
    public static String getLinkName(String srcNE, String destNE){
        String path_name = "";
        for(LinkSpec ps : LinkSpecList){
            String src = ps.getHasNetworkEquipment().get(0).getKey();
            String dest = ps.getHasNetworkEquipment().get(1).getKey();
            //System.out.println(" src ======> " + src + " / " + " dest ======> " + dest + "/ bidirectional ======> " + ps.getBidirectional() + " / " + Boolean.parseBoolean(ps.getBidirectional()));
            //System.out.println("csrc ======> " + srcNE + " / " + "cdest ======> " + dest);
            if(src.equals(srcNE) && dest.equals(destNE)){
                path_name = ps.getLinkName();
                return path_name;
            }
            
            if(Boolean.parseBoolean(ps.getBidirectional())){
                if (src.equals(destNE) && dest.equals(srcNE)) {
                    path_name = ps.getLinkName();
                    return path_name;
                }
            }
        }
        return path_name;
    }
    
    public static SPSpec getSPSpec(String NEName){
        for(SPSpec sps : SPSpecList){
            if(sps.getNEName().equals(NEName)){
                return sps;
            }
        }
        
        return null;
    }
}
class OWLSourceData{
    ArrayList<OWLSourceDataSet> objectProperty;
    ArrayList<OWLSourceDataSet> dataProperty;
    String individualName;
    String individualClass;   
    
    public OWLSourceData() {
        objectProperty = new ArrayList<OWLSourceDataSet>();
        dataProperty = new ArrayList<OWLSourceDataSet>();
    }   
}

class OWLSourceDataSet{
    DuplicatedMap property;
    ArrayList<DuplicatedMap> annotation;

    public OWLSourceDataSet() {
        property = new DuplicatedMap();
        annotation = new ArrayList<DuplicatedMap>();
    }
}
