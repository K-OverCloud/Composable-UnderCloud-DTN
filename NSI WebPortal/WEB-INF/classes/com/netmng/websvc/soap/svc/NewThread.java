package com.netmng.websvc.soap.svc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.netmng.util.PropUtil;
import com.netmng.util.StrUtil;
import com.netmng.websvc.soap.param.types.QueryNotificationConfirmedType;
import com.netmng.websvc.soap.param.types.QueryRecursiveResultType;
import com.netmng.websvc.soap.param.types.QuerySummaryResultType;
import com.netmng.websvc.soap.param.types.QueryType;

public class NewThread extends Thread{

	private Map map;
	private String sServiceType;
	
	public NewThread(Map map){
		this.map = map;
		this.sServiceType = (String)this.map.get("service_type");
	}
	
	@Override
	public void run(){
		if(this.sServiceType!=null){
			if(this.sServiceType.equals("querySummaryConfirmed")){
				StrUtil.sysPrint("querySummaryConfirmed thread start!!");
				
				String sCorrelationId	= (String)this.map.get("correlationId");
				String sProviderNSA		= (String)this.map.get("providerNSA");
				String sReplyTo			= (String)this.map.get("replyTo");
				QueryType param			= (QueryType)this.map.get("queryType");
				
				StrUtil.sysPrint("NewThread.querySummaryConfirmed : sCorrelationId="+sCorrelationId);
				StrUtil.sysPrint("NewThread.querySummaryConfirmed : sProviderNSA="+sProviderNSA);
				StrUtil.sysPrint("NewThread.querySummaryConfirmed : sReplyTo="+sReplyTo);
				
				ApplicationContext appContext = new ClassPathXmlApplicationContext("config/spring/spring-app.xml"); 
		        com.netmng.svc.nsa.NsaService nsaService = (com.netmng.svc.nsa.NsaService) appContext.getBean("nsaService"); 
		        Provider provider = new Provider(sCorrelationId, sProviderNSA, (new PropUtil()).getProp("nsa.replyTo"), sReplyTo, "requester");
				try{
			        List<QuerySummaryResultType> listQuerySummaryResult = nsaService.querySummaryConfirmed(param);
	            	provider.querySummaryConfirmed(listQuerySummaryResult);
				}catch(com.netmng.websvc.soap.param._interface.ServiceException e){
		        	StrUtil.sysPrint("querySummaryConfirmed thread exception!! : ServiceException");		
					e.printStackTrace();		        	
					try{
		        		provider.querySummaryFailed(e.getFaultInfo());
		        	}catch(Exception ex){
			        	StrUtil.sysPrint("querySummaryConfirmed thread exception!!");		
						ex.printStackTrace();
			        }
				}catch(Exception e){
					StrUtil.sysPrint("querySummaryConfirmed thread exception!! : Exception");		
					e.printStackTrace();
			        com.netmng.websvc.soap.svc.types.ServiceExceptionType servExcept = provider.getServExcept(e, "nsaId", "", "querySummaryConfirmed");
			        try{
			        	provider.querySummaryFailed(servExcept);
			        }catch(Exception ex){
			        	StrUtil.sysPrint("querySummaryFailed thread exception!!");		
						ex.printStackTrace();
			        }
				}
				StrUtil.sysPrint("querySummaryConfirmed thread end!!");			
			}
			else if(this.sServiceType.equals("queryRecursiveConfirmed"))
			{
				StrUtil.sysPrint("queryRecursiveConfirmed thread start!!");
				
				String sCorrelationId	= (String)this.map.get("correlationId");
				String sProviderNSA		= (String)this.map.get("providerNSA");
				String sReplyTo			= (String)this.map.get("replyTo");
				QueryType param			= (QueryType)this.map.get("queryType");
				
				StrUtil.sysPrint("NewThread.queryRecursiveConfirmed : sCorrelationId="+sCorrelationId);
				StrUtil.sysPrint("NewThread.queryRecursiveConfirmed : sProviderNSA="+sProviderNSA);
				StrUtil.sysPrint("NewThread.queryRecursiveConfirmed : sReplyTo="+sReplyTo);
				
				ApplicationContext appContext = new ClassPathXmlApplicationContext("config/spring/spring-app.xml"); 
		        com.netmng.svc.nsa.NsaService nsaService = (com.netmng.svc.nsa.NsaService) appContext.getBean("nsaService"); 
		        Provider provider = new Provider(sCorrelationId, sProviderNSA, (new PropUtil()).getProp("nsa.replyTo"), sReplyTo, "requester");
				try{
			        List<QueryRecursiveResultType> listQueryRecursiveResult = nsaService.queryRecursiveConfirmed(param);
	            	provider.queryRecursiveConfirmed(listQueryRecursiveResult);
				}catch(com.netmng.websvc.soap.param._interface.ServiceException e){
		        	StrUtil.sysPrint("queryRecursiveConfirmed thread exception!! : ServiceException");		
					e.printStackTrace();		        	
					try{
		        		provider.queryRecursiveFailed(e.getFaultInfo());
		        	}catch(Exception ex){
			        	StrUtil.sysPrint("queryRecursiveConfirmed thread exception!!");		
						ex.printStackTrace();
			        }
				}catch(Exception e){
					StrUtil.sysPrint("queryRecursiveConfirmed thread exception!! : Exception");			
					e.printStackTrace();
			        com.netmng.websvc.soap.svc.types.ServiceExceptionType servExcept = provider.getServExcept(e, "nsaId", "", "queryRecursiveConfirmed");
			        try{
			        	provider.queryRecursiveFailed(servExcept);
			        }catch(Exception ex){
			        	StrUtil.sysPrint("queryRecursiveFailed thread exception!!");		
						ex.printStackTrace();
			        }
				}
				StrUtil.sysPrint("queryRecursiveConfirmed thread end!!");	
			}
			else if(this.sServiceType.equals("queryNotificationConfirmed"))
			{
				StrUtil.sysPrint("queryNotificationConfirmed thread start!!");
				
				String sCorrelationId	= (String)this.map.get("correlationId");
				String sProviderNSA		= (String)this.map.get("providerNSA");
				String sReplyTo			= (String)this.map.get("replyTo");
				String sConnectionId	= (String)this.map.get("connectionId");
				int iStartNotificationId= (Integer)this.map.get("startNotificationId");
				int iEndNotificationId	= (Integer)this.map.get("endNotificationId");
				
				StrUtil.sysPrint("NewThread.queryNotificationConfirmed : sCorrelationId="+sCorrelationId);
				StrUtil.sysPrint("NewThread.queryNotificationConfirmed : sProviderNSA="+sProviderNSA);
				StrUtil.sysPrint("NewThread.queryNotificationConfirmed : sReplyTo="+sReplyTo);
				StrUtil.sysPrint("NewThread.queryNotificationConfirmed : sConnectionId="+sConnectionId);
				StrUtil.sysPrint("NewThread.queryNotificationConfirmed : iStartNotificationId="+iStartNotificationId);
				StrUtil.sysPrint("NewThread.queryNotificationConfirmed : iEndNotificationId="+iEndNotificationId);
				
				ApplicationContext appContext = new ClassPathXmlApplicationContext("config/spring/spring-app.xml"); 
		        com.netmng.svc.nsa.NsaService nsaService = (com.netmng.svc.nsa.NsaService) appContext.getBean("nsaService"); 
		        Provider provider = new Provider(sCorrelationId, sProviderNSA, (new PropUtil()).getProp("nsa.replyTo"), sReplyTo, "requester");
		        try{
		        	QueryNotificationConfirmedType queryNotificationConfirmed = nsaService.queryNotificationConfonfirmed(sConnectionId, iStartNotificationId, iEndNotificationId);
	            	provider.queryNotificationConfirmed(queryNotificationConfirmed);
		        }catch(com.netmng.websvc.soap.param._interface.ServiceException e){
		        	StrUtil.sysPrint("queryNotificationConfirmed thread exception!! : ServiceException");		
					e.printStackTrace();		        	
					try{
		        		provider.queryNotificationFailed(e.getFaultInfo());
		        	}catch(Exception ex){
			        	StrUtil.sysPrint("queryNotificationFailed thread exception!!");		
						ex.printStackTrace();
			        }
		        }catch(Exception e){
		        	StrUtil.sysPrint("queryNotificationConfirmed thread exception!! : ");		
					e.printStackTrace();
			        com.netmng.websvc.soap.svc.types.ServiceExceptionType servExcept = provider.getServExcept(e, "nsaId", "", "queryNotificationConfirmed");
			        try{
			        	provider.queryNotificationFailed(servExcept);
			        }catch(Exception ex){
			        	StrUtil.sysPrint("queryNotificationFailed thread exception!!");		
						ex.printStackTrace();
			        }
		        }
				StrUtil.sysPrint("queryNotificationConfirmed thread end!!");	
			}
		}
	}
}
