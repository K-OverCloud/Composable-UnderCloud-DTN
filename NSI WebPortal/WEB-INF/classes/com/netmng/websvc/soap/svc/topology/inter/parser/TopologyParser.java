/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.svc.topology.inter.parser;

import com.sun.net.ssl.internal.ssl.X509ExtendedTrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import com.netmng.websvc.soap.parser.inter.base.LabelGroupType;
import com.netmng.websvc.soap.parser.inter.base.LocationType;
import com.netmng.websvc.soap.parser.inter.base.NetworkObject;
import com.netmng.websvc.soap.parser.inter.base.PortGroupRelationType;
import com.netmng.websvc.soap.parser.inter.base.PortGroupType;
import com.netmng.websvc.soap.parser.inter.base.PortType;
import com.netmng.websvc.soap.parser.inter.base.TopologyRelationType;
import com.netmng.websvc.soap.parser.inter.base.TopologyType;
import com.netmng.websvc.soap.parser.inter.topology.NSARelationType;
import com.netmng.websvc.soap.parser.inter.topology.NSAType;
import com.netmng.websvc.soap.parser.inter.topology.NsiServiceRelationType;
import com.netmng.websvc.soap.parser.inter.topology.NsiServiceType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author TSOC
 */
public class TopologyParser {

    ArrayList<TopologyReference> trlist = null;
    ArrayList<NSAType> nsaList = null;
    
    public TopologyParser(String topologyURL) {
        this.trlist = getTopologyReference(topologyURL);
        /*
        for(TopologyReference tr : trlist){
            System.out.println(tr.getTopologyName());
        }
        */
        this.nsaList = getTopology(trlist);
    }

    
    public TopologyParser(ArrayList<TopologyReference> trlist) {
        this.nsaList = getTopology(trlist);
    }
    
    public ArrayList<TopologyReference> getTrlist() {
        return trlist;
    }
    
    // https://raw.github.com/jeroenh/AutoGOLE-Topologies/master/master.xml
    private ArrayList<TopologyReference> getTopologyReference(String topologyURL) {
        ArrayList<TopologyReference> trlist = new ArrayList<TopologyReference>();
        
        //정확하지 않은(인증되지 않은 인증서)인증서를 허용
        trustAllSSL();
        
        try {
            JAXBContext context = JAXBContext.newInstance(com.netmng.websvc.soap.parser.inter.base.TopologyRelationType.class);
            Unmarshaller u = context.createUnmarshaller();
            URL url = new URL(topologyURL);
            JAXBElement<TopologyType> topologyElement = (JAXBElement<TopologyType>) u.unmarshal(url);
            TopologyType masterType = topologyElement.getValue();

            ArrayList<NetworkObject> networkList = (ArrayList<NetworkObject>) masterType.getGroup();
            for (NetworkObject network : networkList) {
                TopologyReference tr = new TopologyReference();
                tr.setTopologyName(network.getId());
                tr.setDeployAddress(network.getIsReference());
                trlist.add(tr);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trlist;
    }
    
    private void trustAllSSL() {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509ExtendedTrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string, String string1, String string2) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string, String string1, String string2) throws CertificateException {
                }
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
        };
        
        try{
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }catch(Exception ex){
            Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<NSAType> getTopology(ArrayList<TopologyReference> topologyList) {        
        ArrayList<String> failList = new ArrayList<String>();
        ArrayList<NSAType> nsaList = new ArrayList<NSAType>();
        
        for(int i=0;i<topologyList.size();i++){
        //for (TopologyReference tr : topologyList) {
            TopologyReference tr = topologyList.get(i);
            try {
                JAXBContext context = JAXBContext.newInstance(com.netmng.websvc.soap.parser.inter.topology.NSAType.class);
                Unmarshaller u = context.createUnmarshaller();
                URL url = new URL(tr.getDeployAddress());
                JAXBElement<NSAType> element = (JAXBElement<NSAType>) u.unmarshal(url);
                NSAType nsaType = element.getValue();
                getBidirectionalPort(tr.getDeployAddress(), nsaType);
                nsaList.add(nsaType);
                printTopolgy(nsaType);
            } catch (JAXBException ex) {
                failList.add(tr.getTopologyName() + " --> [" + ex.getCause() + "]");
                //Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MalformedURLException ex) {
                failList.add(tr.getTopologyName() + " --> [" + ex.getCause() + "]");
                //Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        
        if(!failList.isEmpty()){
            System.err.println("\n********** FAIL LIST **********");
            for (String cause : failList) {
                System.err.println(cause);
            }
        }
        
        return nsaList;
    }

    public void getBidirectionalPort(String deployAddress, NSAType nsaType){
        trustAllSSL();
        
        InputStream is = null;
        Element root = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = null;
            
            URL urlType = new URL(deployAddress);
            URLConnection urlConnection = urlType.openConnection();
            is = urlConnection.getInputStream();

            document = builder.parse(is);
            root = document.getDocumentElement();
            
            NodeList topologyNodeList = root.getElementsByTagName("nml:Topology");
            for (int topolgyCnt = 0; topolgyCnt < topologyNodeList.getLength(); topolgyCnt++) {
                Node topologyNode = topologyNodeList.item(topolgyCnt);
                for (int topologyChildCnt = 0; topologyChildCnt < topologyNode.getChildNodes().getLength(); topologyChildCnt++) {
                    Node topolgyChildNode = topologyNode.getChildNodes().item(topologyChildCnt);

                    if (topolgyChildNode.getNodeName().equals("nml:BidirectionalPort")) {
                        PortGroupType pgt = new PortGroupType();
                        Node bidirectionalNode = topolgyChildNode;
                        NamedNodeMap bidirectionlNodeAttributeList = bidirectionalNode.getAttributes();
                        for (int j = 0; j < bidirectionlNodeAttributeList.getLength(); j++) {
                            Node bidirectionlNodeAttribute = bidirectionlNodeAttributeList.item(j);
                            //System.out.println(bidirectionlNodeAttribute.getNodeName());
                            //System.out.println(bidirectionlNodeAttribute.getTextContent());
                            pgt.setId(bidirectionlNodeAttribute.getTextContent());
                        }

                        NodeList bidirectionalNodeChildList = bidirectionalNode.getChildNodes();
                        for (int j = 0; j < bidirectionalNodeChildList.getLength(); j++) {
                            Node bidirectionalNodeChild = bidirectionalNodeChildList.item(j);
                            if (bidirectionalNodeChild.getNodeName().equals("nml:PortGroup")) {
                                NamedNodeMap bidirectionalNodeChildAttributeList = bidirectionalNodeChild.getAttributes();
                                for (int k = 0; k < bidirectionalNodeChildAttributeList.getLength(); k++) {
                                    Node bidirectionalNodeChildAttribute = bidirectionalNodeChildAttributeList.item(k);
                                    //System.out.println(bidirectionalNodeChildAttribute.getNodeName());
                                    //System.out.println(bidirectionalNodeChildAttribute.getTextContent());

                                    LabelGroupType lgt = new LabelGroupType();
                                    lgt.setLabeltype("nml:BidirectionalPort");
                                    lgt.setValue(bidirectionalNodeChildAttribute.getTextContent());
                                    pgt.getLabelGroup().add(lgt);
                                }
                            }
                        }
                        //TopologyType topolgy = new TopologyType();
                        //topolgy.getAny().add(pgt);
                        if(!nsaType.getTopology().isEmpty()){
                            nsaType.getTopology().get(0).getAny().add(pgt);
                        }
                        
                    }
                }
            }
            
            for(TopologyType tt : nsaType.getTopology()) {                
                for(int i=0;i<tt.getAny().size();i++){
                    PortGroupType pgt = (PortGroupType)tt.getAny().get(i);
                    System.out.println("-----------> " + pgt.getId());
                    for(LabelGroupType lgt : pgt.getLabelGroup()){
                        System.out.println(lgt.getValue());
                    }
                }
                /*
                for (CustomBidirectionalPortType cbptValue : tt.getBidirectionalPort()) {
                    System.out.println("BidirectionalPort ID = " + cbptValue.getId());
                    ArrayList<CustomPortGroupType> cpgtValueList = (ArrayList<CustomPortGroupType>) cbptValue.getPortGroup();
                    for (CustomPortGroupType cpgtValue : cpgtValueList) {
                        System.out.println("PortGroup ID = " + cpgtValue.getId());
                    }
                }
                */ 
            }
            /*
            NodeList bidirectionalNodeList = root.getElementsByTagName("nml:BidirectionalPort");            
            for(int i=0;i<bidirectionalNodeList.getLength();i++){
                CustomBidirectionalPortType cbpt = new CustomBidirectionalPortType();
                Node bidirectionalNode = bidirectionalNodeList.item(i);
                NamedNodeMap bidirectionlNodeAttributeList = bidirectionalNode.getAttributes();
                for(int j=0;j<bidirectionlNodeAttributeList.getLength();j++){
                    Node bidirectionlNodeAttribute = bidirectionlNodeAttributeList.item(j);
                    //System.out.println(bidirectionlNodeAttribute.getNodeName());
                    //System.out.println(bidirectionlNodeAttribute.getTextContent());
                    cbpt.setId(bidirectionlNodeAttribute.getTextContent());
                }
                
                NodeList bidirectionalNodeChildList = bidirectionalNode.getChildNodes();
                for(int j=0;j<bidirectionalNodeChildList.getLength();j++){
                    Node bidirectionalNodeChild = bidirectionalNodeChildList.item(j);
                    if(bidirectionalNodeChild.getNodeName().equals("nml:PortGroup")){
                        NamedNodeMap bidirectionalNodeChildAttributeList = bidirectionalNodeChild.getAttributes();
                        for(int k=0;k<bidirectionalNodeChildAttributeList.getLength();k++){
                            Node bidirectionalNodeChildAttribute = bidirectionalNodeChildAttributeList.item(k);
                            //System.out.println(bidirectionalNodeChildAttribute.getNodeName());
                            //System.out.println(bidirectionalNodeChildAttribute.getTextContent());
                            CustomPortGroupType cpgt = new CustomPortGroupType();
                            cpgt.setId(bidirectionalNodeChildAttribute.getTextContent());
                            cbpt.getPortGroup().add(cpgt);
                        }
                    }
                }
                
                nsaType.getTopology().get(i).getBidirectionalPort().add(cbpt);
                ArrayList<CustomBidirectionalPortType> cbptList = (ArrayList<CustomBidirectionalPortType>) nsaType.getTopology().get(i).getBidirectionalPort();
                
                for(CustomBidirectionalPortType cbptValue : cbptList){
                    System.out.println("BidirectionalPort ID = " + cbptValue.getId());
                    ArrayList<CustomPortGroupType> cpgtValueList = (ArrayList<CustomPortGroupType>) cbptValue.getPortGroup();
                    for(CustomPortGroupType cpgtValue : cpgtValueList){
                        System.out.println("PortGroup ID = " + cpgtValue.getId());
                    }
                }
            }
            */
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(TopologyParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    public ArrayList<TopologyReference> getTopologyList() {
        return trlist;
    }

    public ArrayList<NSAType> getNsaList() {
        return nsaList;
    }
    
    public NSAType getNsaTye(TopologyReference tr){
        for(NSAType nsa : nsaList){
            for(TopologyType tt : nsa.getTopology()){
                if(tt.getId().equals(tr.getTopologyName())){
                    return nsa;
                }
            }
        }
        return null;
    }
    
    public ArrayList<InterDomainInformation> createInterDomainInformationList() {

        ArrayList<InterDomainInformation> idiList = new ArrayList<InterDomainInformation>();

        for (NSAType nsa : nsaList) {
            InterDomainInformation idi = new InterDomainInformation();

            idi.setNsa(nsa.getId());
            for (NSARelationType nsart : nsa.getRelation()) {
                if (nsart.getType().equals("http://schemas.ogf.org/nsi/2013/09/topology#adminContact")) {
                    for (String text : nsart.getText()) {
                        idi.setAdminContact(text.trim());
                    }
                }else if(nsart.getType().equals("http://schemas.ogf.org/nsi/2013/09/topology#peersWith")){
                    for(NSAType nsat : nsart.getNSA()){
                        idi.addPeersWith(nsat.getId());
                    }
                }
            }

            for (NsiServiceType nsist : nsa.getService()) {
                idi.setEndPointAddress(nsist.getLink().trim());
                idi.setNsaType(nsist.getType());
                for(NsiServiceRelationType nsisrt : nsist.getRelation()){
                    for(NSAType nsat : nsisrt.getNSA()){
                    idi.setNsa(nsat.getId());
                    }
                }
            }
            
            for(TopologyType tt : nsa.getTopology()){
                
                if(tt.getId() == null){
                    continue;
                }
                
                idi.setNetworkId(tt.getId());
                
                for(int i=0;i<tt.getAny().size();i++){
                    HashMap<String , ArrayList<String>> bidirectionalPort = new HashMap<String , ArrayList<String>>();
                    PortGroupType pgt = (PortGroupType)tt.getAny().get(i);
                    ArrayList<String> portGroup = new ArrayList<String>();
                    for(LabelGroupType lgt : pgt.getLabelGroup()){
                        portGroup.add(lgt.getValue());
                    }
                    bidirectionalPort.put(pgt.getId(), portGroup);
                    idi.getBidirectionalPort().add(bidirectionalPort);
                }
                
                HashMap<String, String> inboundVlanPort = new HashMap<String, String>();
                HashMap<String, String> outboundVlanPort = new HashMap<String, String>();
                for(TopologyRelationType trt : tt.getRelation()){
                    if(trt.getType().equals("http://schemas.ogf.org/nml/2013/05/base#hasInboundPort")){
                        for(PortGroupType pgt : trt.getPortGroup()){
                            for(LabelGroupType lgt : pgt.getLabelGroup()){
                                inboundVlanPort.put(pgt.getId(), lgt.getValue());
                                System.out.println("---------in " + pgt.getId() + " / " + lgt.getValue());
                            }
                        }
                    }
                    if(trt.getType().equals("http://schemas.ogf.org/nml/2013/05/base#hasOutboundPort")){
                        for(PortGroupType pgt : trt.getPortGroup()){
                            for(LabelGroupType lgt : pgt.getLabelGroup()){
                                outboundVlanPort.put(pgt.getId(), lgt.getValue());
                                System.out.println("---------out " + pgt.getId() + " / " + lgt.getValue());
                            }
                        }
                    }
                }
                idi.setInboundPort(inboundVlanPort);
                idi.setOutboundPort(outboundVlanPort);
                
                /*
                for(CustomBidirectionalPortType cbpt : tt.getBidirectionalPort()){
                    HashMap<String , ArrayList<String>> bidirectionalPort = new HashMap<>();
                    ArrayList<String> portGroup = new ArrayList<>();
                    for(CustomPortGroupType cpgt : cbpt.getPort()){
                        portGroup.add(cpgt.getId());
                    }
                    bidirectionalPort.put(cbpt.getId(), portGroup);
                    idi.getBidirectionalPort().add(bidirectionalPort);
                }
                */
               
            }
            
            idiList.add(idi);
        } 
        
        System.out.println("\n\n========== print result(from createInterDomainInformationList) ==========");
        for(InterDomainInformation idi : idiList){
            System.out.println("NSA name : " + idi.getNsa() + " / Type : " + idi.getNsaType());
            System.out.println("Admin Contatct : " + idi.getAdminContact());
            System.out.println("End Point Address : " + idi.getEndPointAddress());
            System.out.println("Network Id : " + idi.getNetworkId());
            for(HashMap<String, ArrayList<String>> bidirectionalPort : idi.getBidirectionalPort()){
                Iterator<String> keyset = bidirectionalPort.keySet().iterator();
                while(keyset.hasNext()){
                    String key = keyset.next();
                    System.out.println("BidirectionalPort : " + key);
                    System.out.println("PortGroup : ");
                    ArrayList<String> bidirectionPortGroup = bidirectionalPort.get(key);
                    for(String portGroup : bidirectionPortGroup){
                        System.out.println(" -> " + portGroup);
                    }
                }
            }
            System.out.println("VLAN INFORMATION - InboundPort");
            HashMap<String, String> inboundPort = idi.getInboundPort();
            Iterator<String> inboundKeyset = inboundPort.keySet().iterator();
            while(inboundKeyset.hasNext()){
                String key = inboundKeyset.next();
                System.out.println("PortGroup : " + key);
                System.out.println(" --> VLAN Label : " + inboundPort.get(key));
                
            }
            System.out.println("VLAN INFORMATION - OutboundPort");
            HashMap<String, String> outboundPort = idi.getOutboundPort();
            Iterator<String> outboundKeyset = outboundPort.keySet().iterator();
            while(outboundKeyset.hasNext()){
                String key = outboundKeyset.next();
                System.out.println("PortGroup : " + key);
                System.out.println(" --> VLAN Label : " + outboundPort.get(key));
                
            }
            
            System.out.println("**********");
        }
        
        return idiList;
    }
    
    public void printTopolgy(NSAType nsa){
        System.out.println("\n{TopologyInfo - ["+nsa.getId()+" / "+nsa.getVersion()+"]}");
        
        LocationType lt = nsa.getLocation();
        if(lt != null){
            System.out.println("* Location[" + lt.getId() + "] *");
            System.out.printf("Lat:%s/Alt:%s/Long:%s/Name:%s/Unlocode:%s\n", lt.getLat(), lt.getAlt(), lt.getLong(), lt.getName(), lt.getUnlocode());
        }else{
            System.out.println("---------- Location[ EMPTY ] ----------");
        }
        
        for(NsiServiceType nsist : nsa.getService()){
            System.out.println("\n* Service[" + nsist.getId() + "] *");
            System.out.println("Link : " + nsist.getLink().trim());
            System.out.println("Type : " + nsist.getType());
            for(NsiServiceRelationType nsirt : nsist.getRelation()){
                System.out.println("  - ServiceRelation(" + nsirt.getType() + ")");
                System.out.println("  NSA : " + nsirt.getNSA());
            }
        }
        
        for(NSARelationType nsart : nsa.getRelation()){
            System.out.println("\n* Releation[" + nsart.getType()+ "] *");
            if(!nsart.getText().isEmpty()){
                for(String text : nsart.getText()){
                    System.out.println("text(vCard) : " + text.trim());
                }
            }
            if(!nsart.getNSA().isEmpty()){
                for(NSAType nsat : nsart.getNSA()){
                    System.out.println("NSA : " + nsat.getId());
                }
            }
        }
        
        for(TopologyType topology : nsa.getTopology()){
            System.out.println("\n* Topology[" + topology.getId() + "] / Name : " + topology.getName() + " *");

            for(TopologyRelationType trt : topology.getRelation()){
                System.out.println("  - TopologyRelation(" + trt.getType() + ")");
                
                for(PortGroupType pgt : trt.getPortGroup()){
                    System.out.println("  Id : " + pgt.getId());
                    for(LabelGroupType lgt : pgt.getLabelGroup()){
                        System.out.println("  LabelType : " + lgt.getLabeltype());
                        System.out.println("  LabelValue : " + lgt.getValue());
                    }
                    for(PortGroupRelationType pgrt : pgt.getRelation()){
                        System.out.println("    -> PortRelation(" + pgrt.getType() + ")");
                        for(PortGroupType pgt_relation : pgrt.getPortGroup()){
                            System.out.println("    Id : " + pgt_relation.getId());
                        }
                    }
                }
            }
        }
        
    }
}
