/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.svc.topology.inter.parser;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.bind.JAXBException;
import com.netmng.websvc.soap.parser.inter.topology.NSAType;
/**
 *
 * @author TSOC
 */
public class Example {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JAXBException, MalformedURLException {
        //TopologyParser tp = new TopologyParser("http://nsi2.kisti.re.kr/wsdl/ConfigFiles/master.xml");
        /*
        아래의 createInterDomainInformationList() 메서드에서 얻을 수 있는 InterDomainInformation의 정보를 사용하여 주시면 됩니다.
        또한 스키마 파일(nmlbase.xsd, nsi-ext.xsd, vCard.xsd)는 http://nsi2.kisti.re.kr/wsdl/ConfigFiles.jsp 에 올려져 있습니다.
        */
        //tp.createInterDomainInformationList();
        /*
        TopologyReference tr = new TopologyReference();
        tr.setDeployAddress("https://raw.github.com/jeroenh/AutoGOLE-Topologies/master/goles/krlight.net.xml");
        tr.setTopologyName("krlight");
        ArrayList<TopologyReference> trlist = new ArrayList<>();
        trlist.add(tr);
        TopologyParser tp = new TopologyParser(trlist);
        for(NSAType nsat : tp.getNsaList()){
            tp.printTopolgy(nsat);
        }
        */
        
        TopologyParser tp = new TopologyParser("http://nsi2.kisti.re.kr/wsdl/ConfigFiles/master.xml");
        tp.createInterDomainInformationList();
        /*
        ArrayList<InterDomainInformation> idiList = tp.createInterDomainInformationList();
        System.out.println("\n\nResult : Class IDI[" + idiList.size() + "]\n");
        for(InterDomainInformation idi : idiList){
            System.out.println("AdminContact : " + idi.getAdminContact());
            System.out.println("NetworkId : " + idi.getNetworkId());
            System.out.println("Nsa : " + idi.getNsa());
            System.out.println("EndPointAddress : " + idi.getEndPointAddress());
            ArrayList<HashMap<String, ArrayList<Integer>>> localIdList = idi.getLocalIdSet();
            for(HashMap<String, ArrayList<Integer>> localIdMap : localIdList){
                Iterator keyset = localIdMap.keySet().iterator();
                while(keyset.hasNext()){
                    String key = keyset.next().toString();
                    ArrayList<Integer> vlanLength = localIdMap.get(key);
                    System.out.println("LocalID : " + key);
                    for(int vlan : vlanLength){
                        System.out.println("   Vlan : " + vlan);
                    }
                }
            }
        } 
        */
    }
}
