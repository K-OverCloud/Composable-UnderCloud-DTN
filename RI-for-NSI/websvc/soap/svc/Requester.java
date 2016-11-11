package com.netmng.websvc.soap.svc;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import com.netmng.websvc.soap.assertion.AttributeStatementType;
/*import com.netmng.websvc.soap.param._interface.ForcedEndRequestType;
import com.netmng.websvc.soap.param._interface.GenericAcknowledgmentType;
import com.netmng.websvc.soap.param._interface.ProvisionConfirmedRequestType;
import com.netmng.websvc.soap.param._interface.ProvisionFailedRequestType;
import com.netmng.websvc.soap.param._interface.QueryConfirmedRequestType;
import com.netmng.websvc.soap.param._interface.QueryFailedRequestType;
import com.netmng.websvc.soap.param._interface.QueryRequestType;
import com.netmng.websvc.soap.param._interface.ReleaseConfirmedRequestType;
import com.netmng.websvc.soap.param._interface.ReleaseFailedRequestType;
import com.netmng.websvc.soap.param._interface.ReserveConfirmedRequestType;
import com.netmng.websvc.soap.param._interface.ReserveFailedRequestType;
import com.netmng.websvc.soap.param._interface.ServiceException;
import com.netmng.websvc.soap.param._interface.TerminateConfirmedRequestType;
import com.netmng.websvc.soap.param._interface.TerminateFailedRequestType;*/
/*import com.netmng.websvc.soap.param.types.BandwidthType;
import com.netmng.websvc.soap.param.types.OrderedServiceTerminationPointType;
import com.netmng.websvc.soap.param.types.PathType;
import com.netmng.websvc.soap.param.types.QueryConfirmedType;
import com.netmng.websvc.soap.param.types.QueryDetailsResultType;
import com.netmng.websvc.soap.param.types.QueryFilterType;
import com.netmng.websvc.soap.param.types.ReservationInfoType;
import com.netmng.websvc.soap.param.types.ServiceExceptionType;
import com.netmng.websvc.soap.param.types.ServiceParametersType;
import com.netmng.websvc.soap.param.types.ServiceTerminationPointType;
import com.netmng.websvc.soap.param.types.StpListType;
import com.netmng.websvc.soap.param.types.TechnologySpecificAttributesType;*/
import com.netmng.websvc.soap.param.types.QueryFailedType;
import com.netmng.websvc.soap.param.types.GenericConfirmedType;
import com.netmng.websvc.soap.param.types.GenericFailedType;
import com.netmng.websvc.soap.param.types.GenericRequestType;
import com.netmng.websvc.soap.param.types.QuerySummaryResultType;
import com.netmng.websvc.soap.param.types.QueryType;
import com.netmng.websvc.soap.param.types.ReserveConfirmedType;
import com.netmng.websvc.soap.param.types.ScheduleType; 
import com.netmng.websvc.soap.svc.param.requester.ForcedEnd;
import com.netmng.websvc.soap.svc.param.requester.ProvisionConfirmed;
import com.netmng.websvc.soap.svc.param.requester.ProvisionFailed;
import com.netmng.websvc.soap.svc.param.requester.Query;
import com.netmng.websvc.soap.svc.param.requester.QueryConfirmed;
import com.netmng.websvc.soap.svc.param.requester.QueryFailed;
import com.netmng.websvc.soap.svc.param.requester.ReleaseConfirmed;
import com.netmng.websvc.soap.svc.param.requester.ReleaseFailed;
import com.netmng.websvc.soap.svc.param.requester.ReserveConfirmed;
import com.netmng.websvc.soap.svc.param.requester.ReserveFailed;
import com.netmng.websvc.soap.svc.param.requester.TerminateConfirmed;
import com.netmng.websvc.soap.svc.param.requester.TerminateFailed;
import com.netmng.websvc.soap.svc.requester.ConnectionRequesterPort;
import com.netmng.websvc.soap.svc.requester.ConnectionServiceRequester;

public class Requester {
	
	//private QName service_name = new QName("http://schemas.ogf.org/nsi/2011/10/connection/requester", "ConnectionServiceRequester");
	private QName service_name = new QName("http://schemas.ogf.org/nsi/2013/07/connection/requester", "ConnectionServiceRequester");
	//private URL wsdlURL = ConnectionServiceRequester.WSDL_LOCATION;
	//private URL wsdlURL = ConnectionServiceRequester.CONNECTIONSERVICEREQUESTER_WSDL_LOCATION;
	private URL wsdlURL = ConnectionServiceRequester.WSDL_LOCATION;
	
	public String reserveConfirmed(ReserveConfirmed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        
        ReserveConfirmedRequestType reserveConfirmedRequestType = new ReserveConfirmedRequestType();
        	ReserveConfirmedType reserveConfirmedType = new ReserveConfirmedType();
        		
        		ReservationInfoType reservation = new ReservationInfoType();
        			
        			ServiceParametersType serviceParameters = new ServiceParametersType();
        				ScheduleType schedule = new ScheduleType();
        					schedule.setStartTime(param.getStartTime());
        					schedule.setEndTime(param.getEndTime());
        					schedule.setDuration(param.getDuration());
        				BandwidthType bandwidth = new BandwidthType();
        					bandwidth.setDesired(param.getDesired());
        					bandwidth.setMinimum(param.getMinimum());
        					bandwidth.setMaximum(param.getMaximum());
        				TechnologySpecificAttributesType serviceAttributes = new TechnologySpecificAttributesType();
        					serviceAttributes.setGuaranteed(param.getServiceGuaranteed());
        					serviceAttributes.setPreferred(param.getServicePreferred());
        				serviceParameters.setSchedule(schedule);
        				serviceParameters.setBandwidth(bandwidth);
        				serviceParameters.setServiceAttributes(serviceAttributes);
        			PathType path = new PathType();
        				ServiceTerminationPointType sourceSTP = new ServiceTerminationPointType();
        					sourceSTP.setStpId(param.getSourceStpId());
        					TechnologySpecificAttributesType sourceStpSpecAttrs = new TechnologySpecificAttributesType();
        						sourceStpSpecAttrs.setGuaranteed(param.getSourceGuaranteed());
        						sourceStpSpecAttrs.setPreferred(param.getSourcePreferred());
        				ServiceTerminationPointType destSTP = new ServiceTerminationPointType();
        					destSTP.setStpId(param.getDestStpId());
	    					TechnologySpecificAttributesType destStpSpecAttrs = new TechnologySpecificAttributesType();
	    						destStpSpecAttrs.setGuaranteed(param.getDestGuaranteed());
	    						destStpSpecAttrs.setPreferred(param.getDestPreferred());
        				StpListType stpList = new StpListType();						// nullable
        				
        				List<OrderedServiceTerminationPointType> stp = param.getStp();
        				
        				if(stp != null) {
        					for(int i=0; i<stp.size(); i++)
            					stpList.getStp().add(stp.get(i));
        				}
        					
        				path.setDirectionality(param.getDirectionality());
        				path.setSourceSTP(sourceSTP);
        				path.setDestSTP(destSTP);
        				path.setStpList(stpList);
        				
        			reservation.setGlobalReservationId(param.getGlobalReservationId());
        			reservation.setDescription(param.getDescription());
        			reservation.setConnectionId(param.getConnectionId());
        			reservation.setServiceParameters(serviceParameters);
        			reservation.setPath(path);
        		
        		reserveConfirmedType.setRequesterNSA(param.getRequesterNSA());
        		reserveConfirmedType.setProviderNSA(param.getProviderNSA());
        		reserveConfirmedType.setReservation(reservation);
            reserveConfirmedRequestType.setCorrelationId(param.getCorrelationId());
            reserveConfirmedRequestType.setReserveConfirmed(reserveConfirmedType);
        
        try {
	    	GenericAcknowledgmentType result = port.reserveConfirmed(reserveConfirmedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String reserveFailed(ReserveFailed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        
        ReserveFailedRequestType reserveFailedRequestType = new ReserveFailedRequestType();
        	reserveFailedRequestType.setCorrelationId(param.getCorrelationId());
        
        	GenericFailedType reserveFailed = new GenericFailedType();
        		reserveFailed.setRequesterNSA(param.getRequesterNSA());
        		reserveFailed.setProviderNSA(param.getProviderNSA());
        		reserveFailed.setGlobalReservationId(param.getGlobalReservationId());
        		reserveFailed.setConnectionId(param.getConnectionId());
        		reserveFailed.setConnectionState(param.getConnectionState());
        		
        		ServiceExceptionType serviceException = new ServiceExceptionType();
        			serviceException.setErrorId(param.getErrorId());
        			serviceException.setText(param.getErrorText());
        			AttributeStatementType variables = new AttributeStatementType();
        				
        				List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
        				
        				if(attributeOrEncryptedAttribute != null) {
        					for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
        						variables.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
        				}
        				
        			serviceException.setVariables(variables);
       			reserveFailed.setServiceException(serviceException);
        	reserveFailedRequestType.setReserveFailed(reserveFailed);
        
        try {
	    	GenericAcknowledgmentType result = port.reserveFailed(reserveFailedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String provisionConfirmed(ProvisionConfirmed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        
        ProvisionConfirmedRequestType provisionConfirmedRequestType = new ProvisionConfirmedRequestType();
        	provisionConfirmedRequestType.setCorrelationId(param.getCorrelationId());
        	GenericConfirmedType provisionConfirmed = new GenericConfirmedType();
        		provisionConfirmed.setRequesterNSA(param.getRequesterNSA());
        		provisionConfirmed.setProviderNSA(param.getProviderNSA());
        		provisionConfirmed.setGlobalReservationId(param.getGlobalReservationId());
        		provisionConfirmed.setConnectionId(param.getConnectionId());
       		provisionConfirmedRequestType.setProvisionConfirmed(provisionConfirmed);
        try {
	    	GenericAcknowledgmentType result = port.provisionConfirmed(provisionConfirmedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String provisionFailed(ProvisionFailed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        
        ProvisionFailedRequestType provisionFailedRequestType = new ProvisionFailedRequestType();
        	provisionFailedRequestType.setCorrelationId(param.getCorrelationId());
        	GenericFailedType provisionFailed = new GenericFailedType();
        	
        		provisionFailed.setRequesterNSA(param.getRequesterNSA());
        		provisionFailed.setProviderNSA(param.getProviderNSA());
        		provisionFailed.setGlobalReservationId(param.getGlobalReservationId());
        		provisionFailed.setConnectionId(param.getConnectionId());
        		provisionFailed.setConnectionState(param.getConnectionState());
	    		
	    		ServiceExceptionType serviceException = new ServiceExceptionType();
	    			serviceException.setErrorId(param.getErrorId());
	    			serviceException.setText(param.getErrorText());
	    			AttributeStatementType variables = new AttributeStatementType();
	    				
	    				List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
	    				
	    				if(attributeOrEncryptedAttribute != null) {
	    					for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
	    						variables.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
	    				}
	    				
	    			serviceException.setVariables(variables);
	    		provisionFailed.setServiceException(serviceException);
        	
        	provisionFailedRequestType.setProvisionFailed(provisionFailed);
        	
        try {
	    	GenericAcknowledgmentType result = port.provisionFailed(provisionFailedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String releaseConfirmed(ReleaseConfirmed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        	
        	ReleaseConfirmedRequestType releaseConfirmedRequestType = new ReleaseConfirmedRequestType();
        	
        	releaseConfirmedRequestType.setCorrelationId(param.getCorrelationId());
        	GenericConfirmedType releaseConfirmed = new GenericConfirmedType();
        		releaseConfirmed.setRequesterNSA(param.getRequesterNSA());
        		releaseConfirmed.setProviderNSA(param.getProviderNSA());
        		releaseConfirmed.setGlobalReservationId(param.getGlobalReservationId());
        		releaseConfirmed.setConnectionId(param.getConnectionId());
        	releaseConfirmedRequestType.setReleaseConfirmed(releaseConfirmed);
        	
        try {
	    	GenericAcknowledgmentType result = port.releaseConfirmed(releaseConfirmedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String releaseFailed(ReleaseFailed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        
        	ReleaseFailedRequestType releaseFailedRequestType = new ReleaseFailedRequestType();
        	
        	releaseFailedRequestType.setCorrelationId(param.getCorrelationId());
        	GenericFailedType releaseFailed = new GenericFailedType();
        	
        		releaseFailed.setRequesterNSA(param.getRequesterNSA());
        		releaseFailed.setProviderNSA(param.getProviderNSA());
        		releaseFailed.setGlobalReservationId(param.getGlobalReservationId());
        		releaseFailed.setConnectionId(param.getConnectionId());
        		releaseFailed.setConnectionState(param.getConnectionState());
	    		
	    		ServiceExceptionType serviceException = new ServiceExceptionType();
	    			serviceException.setErrorId(param.getErrorId());
	    			serviceException.setText(param.getErrorText());
	    			AttributeStatementType variables = new AttributeStatementType();
	    				
	    				List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
	    				
	    				if(attributeOrEncryptedAttribute != null) {
	    					for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
	    						variables.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
	    				}
	    				
	    			serviceException.setVariables(variables);
	    		releaseFailed.setServiceException(serviceException);
        	
	    	releaseFailedRequestType.setReleaseFailed(releaseFailed);
        	
        try {
	    	GenericAcknowledgmentType result = port.releaseFailed(releaseFailedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String terminateConfirmed(TerminateConfirmed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        
        	TerminateConfirmedRequestType terminateConfirmedRequestType = new TerminateConfirmedRequestType();
        	
        		terminateConfirmedRequestType.setCorrelationId(param.getCorrelationId());
	        	GenericConfirmedType terminateConfirmed = new GenericConfirmedType();
	        		terminateConfirmed.setRequesterNSA(param.getRequesterNSA());
	        		terminateConfirmed.setProviderNSA(param.getProviderNSA());
	        		terminateConfirmed.setGlobalReservationId(param.getGlobalReservationId());
	        		terminateConfirmed.setConnectionId(param.getConnectionId());
	        	terminateConfirmedRequestType.setTerminateConfirmed(terminateConfirmed);
        	
        try {
	    	GenericAcknowledgmentType result = port.terminateConfirmed(terminateConfirmedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String terminateFailed(TerminateFailed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        
        	TerminateFailedRequestType terminateFailedRequestType = new TerminateFailedRequestType();
        
        		terminateFailedRequestType.setCorrelationId(param.getCorrelationId());
	        	GenericFailedType terminateFailed = new GenericFailedType();
	        	
	        		terminateFailed.setRequesterNSA(param.getRequesterNSA());
	        		terminateFailed.setProviderNSA(param.getProviderNSA());
	        		terminateFailed.setGlobalReservationId(param.getGlobalReservationId());
	        		terminateFailed.setConnectionId(param.getConnectionId());
	        		terminateFailed.setConnectionState(param.getConnectionState());
		    		
		    		ServiceExceptionType serviceException = new ServiceExceptionType();
		    			serviceException.setErrorId(param.getErrorId());
		    			serviceException.setText(param.getErrorText());
		    			AttributeStatementType variables = new AttributeStatementType();
		    				
		    				List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
		    				
		    				if(attributeOrEncryptedAttribute != null) {
		    					for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
		    						variables.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
		    				}
		    				
		    			serviceException.setVariables(variables);
		    		terminateFailed.setServiceException(serviceException);
	    		terminateFailedRequestType.setTerminateFailed(terminateFailed);
        try {
	    	GenericAcknowledgmentType result = port.terminateFailed(terminateFailedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String query(Query param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        
        	QueryRequestType queryRequestType = new QueryRequestType();
        		queryRequestType.setCorrelationId(param.getCorrelationId());
        		queryRequestType.setReplyTo(param.getReplyTo());
        		QueryType query = new QueryType();
        			query.setRequesterNSA(param.getRequesterNSA());
        			query.setProviderNSA(param.getProviderNSA());
        			
        			AttributeStatementType sessionSecurityAttr = new AttributeStatementType();
    				
    				List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
    				
    				if(attributeOrEncryptedAttribute != null) {
    					for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
    						sessionSecurityAttr.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
    				}
        			
        			query.setSessionSecurityAttr(sessionSecurityAttr);
        			query.setOperation(param.getOperation());
        				QueryFilterType queryFilter = new QueryFilterType();
        					List<String> connectionId = param.getConnectionId();
        					List<String> globalReservationId = param.getGlobalReservationId();
        					if(connectionId != null) {
            					for(int i=0; i<connectionId.size(); i++) {
            						queryFilter.getConnectionId().add(connectionId.get(i));
            					}
            				}
        					if(globalReservationId != null) {
            					for(int i=0; i<globalReservationId.size(); i++)
            						queryFilter.getGlobalReservationId().add(globalReservationId.get(i));
            				}
        					
        			query.setQueryFilter(queryFilter);
        		queryRequestType.setQuery(query);
        try {
	    	GenericAcknowledgmentType result = port.query(queryRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String queryConfirmed(QueryConfirmed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
       		QueryConfirmedRequestType queryConfirmedRequestType = new QueryConfirmedRequestType();
       			queryConfirmedRequestType.setCorrelationId(param.getCorrelationId());
       			
       			QueryConfirmedType queryConfirmed = new QueryConfirmedType();
   					queryConfirmed.setRequesterNSA(param.getRequesterNSA());
       				queryConfirmed.setProviderNSA(param.getProviderNSA());
       				
       				List<QuerySummaryResultType> reservationSummary = param.getReservationSummary();
       					if(reservationSummary != null) {
       						for(int i=0; i<reservationSummary.size(); i++) {
       							queryConfirmed.getReservationSummary().add(reservationSummary.get(i));
       						}
       					}
       				List<QueryDetailsResultType> reservationDetails = param.getReservationDetails();
       					if(reservationDetails != null) {
       						for(int i=0; i<reservationDetails.size(); i++) {
       							queryConfirmed.getReservationDetails().add(reservationDetails.get(i));
       						}
       					}
       			queryConfirmedRequestType.setQueryConfirmed(queryConfirmed);
        try {
	    	GenericAcknowledgmentType result = port.queryConfirmed(queryConfirmedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String queryFailed(QueryFailed param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        	QueryFailedRequestType queryFailedRequestType = new QueryFailedRequestType();
        		queryFailedRequestType.setCorrelationId(param.getCorrelationId());
        		
        			QueryFailedType queryFailed = new QueryFailedType();
        				queryFailed.setRequesterNSA(param.getRequesterNSA());
        				queryFailed.setProviderNSA(param.getProviderNSA());
        			
	        				ServiceExceptionType serviceException = new ServiceExceptionType();
			    			serviceException.setErrorId(param.getErrorId());
			    			serviceException.setText(param.getErrorText());
			    			AttributeStatementType variables = new AttributeStatementType();
			    				
			    				List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
			    				
			    				if(attributeOrEncryptedAttribute != null) {
			    					for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
			    						variables.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
			    				}
			    				
			    			serviceException.setVariables(variables);
        				
        				queryFailed.setServiceException(serviceException);
        			
        		queryFailedRequestType.setQueryFailed(queryFailed);
        try {
	    	GenericAcknowledgmentType result = port.queryFailed(queryFailedRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
	public String forcedEnd(ForcedEnd param) {
		
		/*String resultCorrelationId = null;
		
		ConnectionServiceRequester ss = new ConnectionServiceRequester(this.wsdlURL, this.service_name);
        ConnectionRequesterPort port = ss.getConnectionServiceRequesterPort();  
        	ForcedEndRequestType forcedEndRequestType = new ForcedEndRequestType();
        		forcedEndRequestType.setCorrelationId(param.getCorrelationId());
        			GenericRequestType forcedEnd = new GenericRequestType();
        				forcedEnd.setRequesterNSA(param.getRequesterNSA());
        				forcedEnd.setProviderNSA(param.getProviderNSA());
        					AttributeStatementType sessionSecurityAttr = new AttributeStatementType();
	        					List<Object> attributeOrEncryptedAttribute = param.getAttributeOrEncryptedAttribute();
			    				
			    				if(attributeOrEncryptedAttribute != null) {
			    					for(int i=0; i<attributeOrEncryptedAttribute.size(); i++)
			    						sessionSecurityAttr.getAttributeOrEncryptedAttribute().add(attributeOrEncryptedAttribute.get(i));
			    				}
        					
        				forcedEnd.setSessionSecurityAttr(sessionSecurityAttr);
        				forcedEnd.setConnectionId(param.getConnectionId());
        		
        		forcedEndRequestType.setForcedEnd(forcedEnd);
        try {
	    	GenericAcknowledgmentType result = port.forcedEnd(forcedEndRequestType);
	    	resultCorrelationId = result.getCorrelationId();
		} catch (ServiceException e) { 
			System.out.println("Expected exception: serviceException has occurred.");
			System.out.println(e.toString());
		}
        
        return resultCorrelationId;*/
		return "";
	}
	
}
