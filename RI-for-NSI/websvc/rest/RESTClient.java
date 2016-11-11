package com.netmng.websvc.rest;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.HashMap;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 *
 * @author TSOC
 */
public class RESTClient {
    
    Client client = null;
    WebResource webResource = null;

    public RESTClient() {
        client = Client.create();
        //webResource = client.resource("http://localhost:8080/NSI_v2.9.4/extend-api/management");
        
        webResource = client.resource("http://nsi2.kisti.re.kr/extend-api/management");        
        //webResource = client.resource("http://112.216.233.235:7080/netmng/rest/api");
        
        /*URI uri = UriBuilder.fromUri("http://112.216.233.235:7080/netmng/rest/api").build();
        webResource = client.resource(uri);*/
    }
    
    public int setEvent(){
    	ClientResponse response = null;
    	try{
	    	String subPath = "/event";
	        String parameter = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
	                + "<event xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
	                + "	   xsi:schemaLocation=\"http://kisti.re.kr/dynamicKl/api dynamicKl.xsd\"\n"
	                + "           xmlns=\"http://kisti.re.kr/dynamicKL/api\">"
	                + "		<cause>1-InterfaceDown</cause>"
	                + "		<description>SNMP Trap Raw Data</description>"
	                + "		<router>192.168.0.1</router>"
	                + "		<interface>192.168.10.1</interface>"
	                + "		<interface>192.168.10.2</interface>"
	        		+ "</event>\n";
	        
	        System.out.println("=====>>"+parameter);
	        response = webResource.path(subPath).type(MediaType.APPLICATION_XML).post(ClientResponse.class, parameter);
	        //response = webResource.path(subPath).type(MediaType.APPLICATION_XML).post(ClientResponse.class);
	        System.out.println(response.getStatus());
    	}catch(Exception e){
    		System.out.println("=====================================>>");
    		e.printStackTrace();
    		System.out.println("=====================================>>");
    	}
    	return response.getStatus();
    }
    
    public int setEvent2(){
    	ClientResponse response = null;
    	try{	  
    		String subPath = "/event2";
	        response = webResource.path(subPath).type(MediaType.APPLICATION_XML).post(ClientResponse.class);
	        System.out.println(response.getStatus());
    	}catch(Exception e){
    		System.out.println("=====================================>>");
    		e.printStackTrace();
    		System.out.println("=====================================>>");
    	}
    	return response.getStatus();
    }
    
    public int intraLink(String url){
        String subPath = "/intra-link";
        String parameter = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<intraLink xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "	   xsi:schemaLocation=\"http://kisti.re.kr/dynamicKl/api dynamicKl.xsd\"\n"
                + "           xmlns=\"http://kisti.re.kr/dynamicKL/api\">" + url + "</intraLink>\n";
     
        ClientResponse response = webResource.path(subPath).type(MediaType.APPLICATION_XML).post(ClientResponse.class, parameter);
        System.out.println(response.getStatus());
        return response.getStatus();
    }
    
    public int interLink(String url){
        String subPath = "/inter-link";
        String parameter = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<interLink xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "	   xsi:schemaLocation=\"http://kisti.re.kr/dynamicKl/api dynamicKl.xsd\"\n"
                + "           xmlns=\"http://kisti.re.kr/dynamicKL/api\">" + url + "</interLink>\n";
     
        ClientResponse response = webResource.path(subPath).type(MediaType.APPLICATION_XML).post(ClientResponse.class, parameter);
        System.out.println(response.getStatus());
        return response.getStatus();
    }
    
    public HashMap<Integer,String> getResidualBw(
            String srcNetworkId,
            String srcLocalId,
            String destNetworkId,
            String destLocalId,
            String startTime,
            String endTIme
            ){
        String subPath = "/residual-bw";
        
        HashMap<Integer,String> residualBwMap = new HashMap<Integer,String>();
        
        MultivaluedMap<String, String> params = new MultivaluedHashMap<String, String>();
        params.add("srcNetworkId", srcNetworkId);
        params.add("srcLocalId", srcLocalId);
        params.add("destNetworkId", destNetworkId);
        params.add("destLocalId", destLocalId);
        params.add("startTime", startTime);
        params.add("endTIme", endTIme);
        
        ClientResponse response = webResource.path(subPath).queryParams(params).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);
        System.out.println("Request URL : " + webResource.path(subPath).getURI());
        
        int status = response.getStatus();
        String result = "";
        if(status == 200){
            result = response.getEntity(String.class);
            System.out.println("Success! result raw data : ");
            System.out.println(result);
        }else{
            System.out.println("HTTP REST request FAIL! " + status);
            residualBwMap.put(-1, "fail");
            return residualBwMap;
        }
        
        String [] splitResult1 = result.split("\">");
        String [] splitResult2 = splitResult1[1].split("</");
        int residualBw = Integer.parseInt(splitResult2[0]);
        
        splitResult1 = result.split("unit=\"");
        splitResult2 = splitResult1[1].split("\"");
        String unit = splitResult2[0];
        
        residualBwMap.put(residualBw, unit);
        
        return residualBwMap;
    }
}
