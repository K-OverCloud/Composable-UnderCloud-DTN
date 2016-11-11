/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.parser.intra.owlparser;

/**
 *
 * @author TSOC
 */
public class Example {
    public static void main(String [] args){
        
        OWLUtility owlu = new OWLUtility();
        
        /*
        본 클래스를 실행하기 위해서는 OWL 라이브러리(owlapi-distribution-3.4.3-bin.jar)가 필요합니다. 라이브러리는 프로젝트 폴더(OWLParser/) 안에 있습니다.
        아래에 있는 getServicePoint(), getNetworkEquipment(), getLinkSpec() 메서드로 얻을 수 있는 정보들을 출력해 주시면 됩니다.
        *****중요***** 단, 예외적으로 getNetworkEquipment 메서드로 받아오는 NESpec 정보에서 ID와 PW는 웹 상에 보여주면 안됩니다.
        */
        
        owlu.getServicePoint(); //ServicePoint(STP : Service Termination Point) 정보 출력
        owlu.getNetworkEquipment(); //네트워크 장비 정보
        owlu.getLinkSpec(); //링크 정보 출력
        
        owlu.printResult(); //테스트용 프린터 함수
        
    }
}
