/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package br.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

/**
 *
 * @author gabri
 */
@WebService(serviceName = "SomaService")
public class SomaService {

    @WebMethod(operationName = "somar")
    public int somar(@WebParam(name = "campo1") String CAMPO1, @WebParam(name = "campo2") String CAMPO2) {
        return Integer.parseInt(CAMPO1) + Integer.parseInt(CAMPO2);
    }
}
