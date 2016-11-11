/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.svc.requester;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:ExtendApiResource
 * [management]<br>
 * USAGE:
 * <pre>
 *        client client = new client();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author TSOC
 */
public class client {
    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://nsi2.kisti.re.kr/extend-api";

    public client() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("management");
    }

    public int intraLink(String requestEntity) throws ClientErrorException {
        String parameterTemp = "<?xml versoin=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<intraLink xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "	   xsi:schemaLocation=\"http://kisti.re.kr/dynamicKl/api dynamicKl.xsd\"\n"
                + "           xmlns=\"http://kisti.re.kr/dynamicKL/api\">" + requestEntity + "</intraLink>\n";
        requestEntity = parameterTemp;
        
        Response response = webTarget.path("intra-link").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        return response.getStatus();
    }

    public int interLink(String requestEntity) throws ClientErrorException {
        String parameterTemp = "<?xml versoin=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<interLink xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "	   xsi:schemaLocation=\"http://kisti.re.kr/dynamicKl/api dynamicKl.xsd\"\n"
                + "           xmlns=\"http://kisti.re.kr/dynamicKL/api\">" + requestEntity + "</interLink>\n";
        requestEntity = parameterTemp;
        
        Response response = webTarget.path("inter-link").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        return response.getStatus();
    }

    public String getString() throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public HashMap<Integer,String> getResidualBw(String startTime, String endTIme, String srcNetworkId, String destNetworkId, String srcLocalId, String destLocalId) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (startTime != null) {
            resource = resource.queryParam("startTime", startTime);
        }
        if (endTIme != null) {
            resource = resource.queryParam("endTIme", endTIme);
        }
        if (srcNetworkId != null) {
            resource = resource.queryParam("srcNetworkId", srcNetworkId);
        }
        if (destNetworkId != null) {
            resource = resource.queryParam("destNetworkId", destNetworkId);
        }
        if (srcLocalId != null) {
            resource = resource.queryParam("srcLocalId", srcLocalId);
        }
        if (destLocalId != null) {
            resource = resource.queryParam("destLocalId", destLocalId);
        }
        resource = resource.path("residual-bw");

        String result = resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(String.class);
        
        String [] splitResult1 = result.split("\">");
        String [] splitResult2 = splitResult1[1].split("</");
        int residualBw = Integer.parseInt(splitResult2[0]);
        
        splitResult1 = result.split("unit=\"");
        splitResult2 = splitResult1[1].split("\"");
        String unit = splitResult2[0];
        
        HashMap<Integer,String> residualBwMap = new HashMap<Integer,String>();
        residualBwMap.put(residualBw, unit);
        
        return residualBwMap;
    }

    public void close() {
        client.close();
    }
    
}
