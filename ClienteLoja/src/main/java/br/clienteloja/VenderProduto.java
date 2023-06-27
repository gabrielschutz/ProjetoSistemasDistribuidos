package br.clienteloja;

import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPConnection;
import jakarta.xml.soap.SOAPConnectionFactory;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPEnvelope;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPPart;

public class VenderProduto {
    
    public String callSoapWebService(String soapEndpointUrl, String soapAction, int id, int quantidade) {
        try {
            // Criar conexão SOAP
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Enviar SOAP Message para o servidor
            SOAPMessage soapRequest = createSOAPRequest(id, quantidade);
            SOAPMessage soapResponse = soapConnection.call(soapRequest, soapEndpointUrl);

            // Extrair resposta do servidor
            String response = extractResponse(soapResponse);

            soapConnection.close();

            return response;
        } catch (Exception e) {
            return "ERRO:\n" + e.getMessage();
        }
    }

    private SOAPMessage createSOAPRequest(int id, int qtd) throws Exception {
        // Criar mensagem SOAP
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Criar envelope SOAP
        createSoapEnvelope(soapMessage, id, qtd);
        soapMessage.saveChanges();

        return soapMessage;
    }

    private void createSoapEnvelope(SOAPMessage soapMessage, int id, int quantidade) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Verificar no WSDL o namespace utilizado
        String myNamespace = "ws";
        String myNamespaceURI = "http://ws.br/";

        // Preencher SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // Preencher SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("venderProduto", myNamespace);

        SOAPElement idElem = soapBodyElem.addChildElement("id");
        idElem.addTextNode(String.valueOf(id));
        
        SOAPElement qtdElem = soapBodyElem.addChildElement("quantidade");
        qtdElem.addTextNode(String.valueOf(quantidade));

    }

    private String extractResponse(SOAPMessage soapMessage) throws SOAPException {
        SOAPBody soapBody = soapMessage.getSOAPBody();
        SOAPElement responseElement = (SOAPElement) soapBody.getElementsByTagName("return").item(0);
        return responseElement.getTextContent();
    }
    
}
