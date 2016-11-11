package com.netmng.websvc.soap.svc.provider;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.6.9
 * 2013-09-11T15:22:33.100+09:00
 * Generated source version: 2.6.9
 * 
 */
@WebServiceClient(name = "ConnectionServiceProvider", 
                  wsdlLocation = "http://nsi2.kisti.re.kr/wsdl/ConnectionService/ogf_nsi_connection_provider_v2_0.wsdl",
                  targetNamespace = "http://schemas.ogf.org/nsi/2013/07/connection/provider") 
public class ConnectionServiceProvider extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://schemas.ogf.org/nsi/2013/07/connection/provider", "ConnectionServiceProvider");
    public final static QName ConnectionServiceProviderPort = new QName("http://schemas.ogf.org/nsi/2013/07/connection/provider", "ConnectionServiceProviderPort");
    static {
        URL url = null;
        try {
            url = new URL("http://nsi2.kisti.re.kr/wsdl/ConnectionService/ogf_nsi_connection_provider_v2_0.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ConnectionServiceProvider.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://nsi2.kisti.re.kr/wsdl/ConnectionService/ogf_nsi_connection_provider_v2_0.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ConnectionServiceProvider(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ConnectionServiceProvider(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ConnectionServiceProvider() {
        super(WSDL_LOCATION, SERVICE);
    }
    /*
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ConnectionServiceProvider(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ConnectionServiceProvider(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public ConnectionServiceProvider(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }*/

    /**
     *
     * @return
     *     returns ConnectionProviderPort
     */
    @WebEndpoint(name = "ConnectionServiceProviderPort")
    public ConnectionProviderPort getConnectionServiceProviderPort() {
        return super.getPort(ConnectionServiceProviderPort, ConnectionProviderPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ConnectionProviderPort
     */
    @WebEndpoint(name = "ConnectionServiceProviderPort")
    public ConnectionProviderPort getConnectionServiceProviderPort(WebServiceFeature... features) {
        return super.getPort(ConnectionServiceProviderPort, ConnectionProviderPort.class, features);
    }

}