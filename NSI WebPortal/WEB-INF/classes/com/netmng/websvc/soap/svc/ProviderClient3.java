/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netmng.websvc.soap.svc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import com.netmng.util.PropUtil;
import com.netmng.util.StrUtil;
import com.netmng.websvc.soap.param._interface.ServiceException;
import com.netmng.websvc.soap.svc.header.CommonHeaderType;
import com.netmng.websvc.soap.svc.provider.ConnectionProviderPort;
import com.netmng.websvc.soap.svc.provider.ConnectionServiceProvider;
import com.netmng.websvc.soap.svc.services.point2point.EthernetVlanType;
import com.netmng.websvc.soap.svc.services.types.DirectionalityType;
import com.netmng.websvc.soap.svc.services.types.StpType;
import com.netmng.websvc.soap.param.types.GenericAcknowledgmentType;
import com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType;
import com.netmng.websvc.soap.param.types.QueryNotificationType;
import com.netmng.websvc.soap.param.types.QuerySummaryConfirmedType;
import com.netmng.websvc.soap.param.types.QueryType;
import com.netmng.websvc.soap.param.types.ReservationRequestCriteriaType;
import com.netmng.websvc.soap.param.types.ScheduleType;
/*import com.sun.xml.ws.developer.WSBindingProvider;
import com.sun.xml.ws.api.message.Headers;*/

import org.apache.cxf.headers.Header;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 *
 * @author TSOC
 */
public class ProviderClient3 {

    private static final QName SERVICE_NAME = new QName("http://schemas.ogf.org/nsi/2013/07/connection/provider", "ConnectionServiceProvider");

    /**
     * @param args the command line arguments
     * @throws JAXBException 
     * @throws IOException 
     * @throws ParserConfigurationException 
     * @throws SAXException 
     */
    public static void main(String[] args) throws Exception {
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	String input = "";
        do{
            try {
                /*System.out.println("input next message : \n [ a(abort), c(commit), m(modify), p(provision), r(release), t(termiante), qc(querySummaryCID), qg(querySummaryGID), qr(requesterNSA) e(exit) ]");*/
            	//System.out.println("input next message : \n [ r(reserve), rc(reserveCommit), ra(reserveAbort), p(provision], rl(release), t(terminate], qs(querySummary) ]");
            	System.out.println("*** input next message ***");
            	System.out.println("- r(reserve), rc(reserveCommit), ra(reserveAbort) ");
            	System.out.println("- p(provision], rl(release), t(terminate] ");
            	System.out.println("- qs(querySummary), qr(queryRecursive), qss(querySummarySync), qn(queryNotification), qnn(queryNotificationSync) ");
                in = new BufferedReader(new InputStreamReader(System.in));
                input = in.readLine();
                
                /*reserve*/
                if(input.equals("r")){
                	/*connectionId*/
                	Holder cid = new Holder("urn:uuid:" + UUID.randomUUID().toString());
                	/*globalReservationId*/
                    String gid = "urn:uuid:" + UUID.randomUUID().toString();
                	/*description*/
                    String description = "TEST REQUESTER!";
                	/*critieria*/            
                    ReservationRequestCriteriaType criteria = new ReservationRequestCriteriaType();
                    criteria.setVersion(0);
                    ScheduleType schedule = new ScheduleType();
                    try {
                        Date startTime = new Date();
                        GregorianCalendar startTimeGregorian = new GregorianCalendar();
                        startTimeGregorian.setTime(startTime);
                        XMLGregorianCalendar startTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(startTimeGregorian);
                        Duration sDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 0, 0, 0);
                        startTimeXMLGregorian.add(sDuration);
                        schedule.setStartTime(startTimeXMLGregorian);
                        
                        Date endTime = new Date();
                        GregorianCalendar endTimeGregorian = new GregorianCalendar();
                        endTimeGregorian.setTime(endTime);
                        XMLGregorianCalendar endTimeXMLGregorian = DatatypeFactory.newInstance().newXMLGregorianCalendar(endTimeGregorian);
                        Duration dDuration = DatatypeFactory.newInstance().newDuration(true, 0, 0, 0, 1, 0, 0);
                        endTimeXMLGregorian.add(dDuration);
                        schedule.setEndTime(endTimeXMLGregorian);
                    } catch (DatatypeConfigurationException ex) {
                    	StrUtil.sysPrint("ProviderClient3.main() : DatatypeConfigurationException");
                    	ex.printStackTrace();
                    }
                    criteria.setSchedule(schedule);
                    criteria.setServiceType("http://services.ogf.org/nsi/2013/07/descriptions/EVTS.A-GOLE");
                    criteria.setVersion(0);
                    
                    EthernetVlanType evt = new EthernetVlanType();
                    evt.setCapacity(200);
        	            StpType sourceSTP = new StpType();
        	            sourceSTP.setNetworkId("urn:ogf:network:dj.test:2013:topologyvv");
        	            sourceSTP.setLocalId("urn:ogf:network:dj.test:2013:p0");
        				sourceSTP.setLabels(null);
                    evt.setSourceSTP(sourceSTP);
                    evt.setEro(null);
        				StpType destSTP = new StpType();
        				destSTP.setNetworkId("urn:ogf:network:dj.test:2013:topology");
        				destSTP.setLocalId("urn:ogf:network:dj.test:2013:p1");
        				destSTP.setLabels(null);
        			evt.setDestSTP(destSTP);
        			evt.setMtu(null);
        			evt.setBurstsize(null);
        			evt.setDirectionality(DirectionalityType.BIDIRECTIONAL);
        			evt.setSourceVLAN(1780);
                    evt.setDestVLAN(1780);
                    com.netmng.websvc.soap.svc.services.point2point.ObjectFactory of = new com.netmng.websvc.soap.svc.services.point2point.ObjectFactory();
                    JAXBElement<EthernetVlanType> evts = of.createEvts(evt);
                    criteria.getAny().add(evts);

                	//Provider provider = new Provider();
		    		Provider provider = new Provider(	"urn:uuid:"+UUID.randomUUID().toString(), 
		    											"urn:ogf:network:krlight.net:2013:nsa", 
		    											/*"http://112.216.233.235:8787/SC_Requester/ConnectionServiceRequester",*/ 
		    											(new PropUtil()).getProp("nsa.replyTo"),
		    											"http://nsi2.kisti.re.kr/ConnectionServiceProvider");
                	provider.reserve(null, gid, description, criteria);	//최초 reserve 시에는 connectId 없음
                }
                /*reserveCommit*/
                else if(input.equals("rc")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	
                	if(sConnectionId!=null && !sConnectionId.equals("")){
	                	Provider provider = new Provider();
	                	provider.reserveCommit(sConnectionId);
                	}else{
                		System.out.println("connectionId가 존재하지 않습니다.");
                	}
                }
                /*reserveAbort*/
                else if(input.equals("ra")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	
                	if(sConnectionId!=null && !sConnectionId.equals("")){
	                	Provider provider = new Provider();
	                	provider.reserveAbort(sConnectionId);
                	}else{
                		System.out.println("connectionId가 존재하지 않습니다.");
                	}
                }
                /*provision*/
                else if(input.equals("p")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	
                	if(sConnectionId!=null && !sConnectionId.equals("")){
	                	Provider provider = new Provider();
	                	provider.provision(sConnectionId);
                	}else{
                		System.out.println("connectionId가 존재하지 않습니다.");
                	}
                }
                /*release*/
                else if(input.equals("rl")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	
                	if(sConnectionId!=null && !sConnectionId.equals("")){
	                	Provider provider = new Provider();
	                	provider.release(sConnectionId);
                	}else{
                		System.out.println("connectionId가 존재하지 않습니다.");
                	}
                }
                /*terminate*/
                else if(input.equals("t")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	
                	if(sConnectionId!=null && !sConnectionId.equals("")){
	                	Provider provider = new Provider();
	                	provider.terminate(sConnectionId);
                	}else{
                		System.out.println("connectionId가 존재하지 않습니다.");
                	}
                }
                /*querySummary*/
                else if(input.equals("qs")){
                	/*connectionId or globalReservationId 중 1개, 둘다 없으면 dynamicKL에서 자동으로 requesterNSA 검색*/
                	//System.out.println("input connectId : ");
                	//String sConnectionId = in.readLine();
                	/*System.out.println("input globalReservationId : ");
                	String sGlobalReservationId = in.readLine();*/
                	
                	QueryType querySummary = new QueryType();
                	//querySummary.getConnectionId().add(sConnectionId);
                	querySummary.getConnectionId().add("urn:uuid:03df9a96-8638-4325-be4b-ef37b0179c58");
                	/*querySummary.getGlobalReservationId().add(sGlobalReservationId);*/
                	
                	//Provider provider = new Provider();
                	//String sCorrelationId, String sProviderNSA, String sReplyTo, String sEndPointAddr
            		Provider provider = new Provider("", "", "", "http://112.216.233.235:8787/SC_Requester/ConnectionServiceProvider");
                	GenericAcknowledgmentType genericAcknowledgmentType = provider.querySummary(querySummary);
                }
                /*queryRecursive*/               
                else if(input.equals("qr")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	System.out.println("input globalReservationId : ");
                	String sGlobalReservationId = in.readLine();
                	
                	QueryType queryRecursive = new QueryType();
                	queryRecursive.getConnectionId().add(sConnectionId);
                	queryRecursive.getGlobalReservationId().add(sGlobalReservationId);
                	
                	Provider provider = new Provider();
                	GenericAcknowledgmentType genericAcknowledgmentType = provider.queryRecursive(queryRecursive);
                }
                /*querySummarySync*/               
                else if(input.equals("qss")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	System.out.println("input globalReservationId : ");
                	String sGlobalReservationId = in.readLine();
                	
                	QueryType querySummarySync = new QueryType();
                	querySummarySync.getConnectionId().add(sConnectionId);
                	querySummarySync.getGlobalReservationId().add(sGlobalReservationId);
                	
                	Provider provider = new Provider();
                	QuerySummaryConfirmedType querySummaryConfirmedType = provider.querySummarySync(querySummarySync);
                }
                /*queryNotification*/               
                else if(input.equals("qn")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	System.out.println("input startNotificationId : ");
                	int iStartNotificationId = Integer.parseInt(in.readLine());
                	System.out.println("input endNotificationId : ");
                	int iEndNotificationId = Integer.parseInt(in.readLine());
                	
                	if(sConnectionId!=null && !sConnectionId.equals("")){
	                	Provider provider = new Provider();
	                	provider.queryNotification(sConnectionId, iStartNotificationId, iEndNotificationId);
                	}else{
                		System.out.println("connectionId가 존재하지 않습니다.");
                	}
                }
                /*queryNotificationSync*/               
                else if(input.equals("qns")){
                	System.out.println("input connectId : ");
                	String sConnectionId = in.readLine();
                	System.out.println("input startNotificationId : ");
                	Integer iStartNotificationId = Integer.parseInt(in.readLine());
                	System.out.println("input endNotificationId : ");
                	Integer iEndNotificationId = Integer.parseInt(in.readLine());
                	
                	QueryNotificationType queryNotificationSync = new QueryNotificationType();
                	queryNotificationSync.setConnectionId(sConnectionId);
                	queryNotificationSync.setStartNotificationId(iStartNotificationId);
                	queryNotificationSync.setEndNotificationId(iEndNotificationId);
                	
                	Provider provider = new Provider();
                	QueryNotificationConfirmedType queryNotificationConfirmedType = provider.queryNotificationSync(queryNotificationSync);
                }
                /*exit*/               
                else if(input.equals("e")){
                    return ;
                }
            } catch (Exception ex) {
            	StrUtil.sysPrint("ProviderClient3.main() : Exception");
            	ex.printStackTrace();
            }
        }while(!input.equals("e"));
    }
}
