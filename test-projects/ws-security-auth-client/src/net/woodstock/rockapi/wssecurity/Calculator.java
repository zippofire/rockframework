
package net.woodstock.rockapi.wssecurity;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1-b03-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "Calculator", targetNamespace = "http://rockapi.woodstock.net/wssecurity")
public interface Calculator {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.Integer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sum", targetNamespace = "http://rockapi.woodstock.net/wssecurity", className = "net.woodstock.rockapi.wssecurity.Sum")
    @ResponseWrapper(localName = "sumResponse", targetNamespace = "http://rockapi.woodstock.net/wssecurity", className = "net.woodstock.rockapi.wssecurity.SumResponse")
    public Integer sum(
        @WebParam(name = "arg0", targetNamespace = "")
        Integer arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        Integer arg1);

}