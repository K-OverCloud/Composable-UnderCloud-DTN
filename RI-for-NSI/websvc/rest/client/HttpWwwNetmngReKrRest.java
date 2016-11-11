
package com.netmng.websvc.rest.client;

import java.util.HashMap;
import javax.annotation.Generated;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;


/**
 * 
 */
@SuppressWarnings("unused")
/*@Generated(value = "http://www.netmng.re.kr/rest/application.wadl", comments = "wadl2java")*/
@Generated(value = "http://www.netmng.re.kr/rest/application.wadl", comment = "wadl2java")
public class HttpWwwNetmngReKrRest {


    public static class Api {

        private Client _client;
        private UriBuilder _uriBuilder;
        private HashMap<String, Object> _templateAndMatrixParameterValues;

        /**
         * Create new instance using existing Client instance
         * 
         */
        public Api(Client client) {
            _client = client;
            _uriBuilder = UriBuilder.fromPath("http://www.netmng.re.kr/rest/");
            _uriBuilder = _uriBuilder.path("/api");
            _templateAndMatrixParameterValues = new HashMap<String, Object>();
        }

        /**
         * Create new instance
         * 
         */
        public Api() {
            this(Client.create());
        }

        public static class FaultOff {

            private Client _client;
            private UriBuilder _uriBuilder;
            private HashMap<String, Object> _templateAndMatrixParameterValues;

            /**
             * Create new instance using existing Client instance
             * 
             */
            public FaultOff(Client client) {
                _client = client;
                _uriBuilder = UriBuilder.fromPath("http://www.netmng.re.kr/rest/");
                _uriBuilder = _uriBuilder.path("/api");
                _uriBuilder = _uriBuilder.path("/faultOff");
                _templateAndMatrixParameterValues = new HashMap<String, Object>();
            }

            /**
             * Create new instance
             * 
             */
            public FaultOff() {
                this(Client.create());
            }

            public void postApplicationXmlAsvoid(com.netmng.websvc.rest.client.param.FaultOff input) {
                UriBuilder localUriBuilder = _uriBuilder.clone();
                WebResource resource = _client.resource(localUriBuilder.buildFromMap(_templateAndMatrixParameterValues));
                WebResource.Builder resourceBuilder = resource.getRequestBuilder();
                resourceBuilder.type("application/xml").accept("text/xml;charset=UTF-8");
                resourceBuilder.method("POST", Void.class, input);
            }

            public<T >T postApplicationXml(Object input, GenericType<T> returnType) {
                UriBuilder localUriBuilder = _uriBuilder.clone();
                WebResource resource = _client.resource(localUriBuilder.buildFromMap(_templateAndMatrixParameterValues));
                WebResource.Builder resourceBuilder = resource.getRequestBuilder();
                resourceBuilder.type("application/xml").accept("text/xml;charset=UTF-8");
                return resourceBuilder.method("POST", returnType, input);
            }

            public<T >T postApplicationXml(Object input, Class<T> returnType) {
                UriBuilder localUriBuilder = _uriBuilder.clone();
                WebResource resource = _client.resource(localUriBuilder.buildFromMap(_templateAndMatrixParameterValues));
                WebResource.Builder resourceBuilder = resource.getRequestBuilder();
                resourceBuilder.type("application/xml").accept("text/xml;charset=UTF-8");
                return resourceBuilder.method("POST", returnType, input);
            }

        }

        public static class FaultOn {

            private Client _client;
            private UriBuilder _uriBuilder;
            private HashMap<String, Object> _templateAndMatrixParameterValues;

            /**
             * Create new instance using existing Client instance
             * 
             */
            public FaultOn(Client client) {
                _client = client;
                _uriBuilder = UriBuilder.fromPath("http://www.netmng.re.kr/rest/");
                _uriBuilder = _uriBuilder.path("/api");
                _uriBuilder = _uriBuilder.path("/faultOn");
                _templateAndMatrixParameterValues = new HashMap<String, Object>();
            }

            /**
             * Create new instance
             * 
             */
            public FaultOn() {
                this(Client.create());
            }

            public void postApplicationXmlAsvoid(com.netmng.websvc.rest.client.param.FaultOn input) {
                UriBuilder localUriBuilder = _uriBuilder.clone();
                WebResource resource = _client.resource(localUriBuilder.buildFromMap(_templateAndMatrixParameterValues));
                WebResource.Builder resourceBuilder = resource.getRequestBuilder();
                resourceBuilder.type("application/xml");//.accept("text/xml;charset=UTF-8");
                resourceBuilder.method("POST", Void.class, input);
            }

            public<T >T postApplicationXml(Object input, GenericType<T> returnType) {
                UriBuilder localUriBuilder = _uriBuilder.clone();
                WebResource resource = _client.resource(localUriBuilder.buildFromMap(_templateAndMatrixParameterValues));
                WebResource.Builder resourceBuilder = resource.getRequestBuilder();
                resourceBuilder.type("application/xml");//.accept("text/xml;charset=UTF-8");
                return resourceBuilder.method("POST", returnType, input);
            }

            public<T >T postApplicationXml(Object input, Class<T> returnType) {
                UriBuilder localUriBuilder = _uriBuilder.clone();
                WebResource resource = _client.resource(localUriBuilder.buildFromMap(_templateAndMatrixParameterValues));
                WebResource.Builder resourceBuilder = resource.getRequestBuilder();
                resourceBuilder.type("application/xml");//.accept("text/xml;charset=UTF-8");
                return resourceBuilder.method("POST", returnType, input);
            }

        }

    }

}
