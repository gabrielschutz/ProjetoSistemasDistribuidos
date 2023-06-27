
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

public class AdicionarProduto {

    public static String callSoapWebService(String soapEndpointUrl, String soapAction, String nome, int quantidade, double preco) {
        try {
            // Criar conexão SOAP
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Enviar SOAP Message para o servidor
            SOAPMessage soapRequest = createSOAPRequest(nome, quantidade, preco);
            SOAPMessage soapResponse = soapConnection.call(soapRequest, soapEndpointUrl);

            // Extrair resposta do servidor
            String response = extractResponse(soapResponse);
            //System.out.println(response);
            soapConnection.close();
            return response;
        } catch (Exception e) {
            System.out.println("ERRO:");
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    private static SOAPMessage createSOAPRequest(String nome, int quantidade, double preco) throws Exception {
        // Criar mensagem SOAP
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Criar envelope SOAP
        createSoapEnvelope(soapMessage, nome, quantidade, preco);
        soapMessage.saveChanges();

        return soapMessage;
    }

    private static void createSoapEnvelope(SOAPMessage soapMessage, String nome, int quantidade, double preco) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Verificar no WSDL o namespace utilizado
        String myNamespace = "ws";
        String myNamespaceURI = "http://ws.br/";

        // Preencher SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // Preencher SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("adicionarProduto", myNamespace);

        SOAPElement nomeElem = soapBodyElem.addChildElement("nome");
        nomeElem.addTextNode(nome);

        SOAPElement quantidadeElem = soapBodyElem.addChildElement("quantidade");
        quantidadeElem.addTextNode(String.valueOf(quantidade));

        SOAPElement precoElem = soapBodyElem.addChildElement("preco");
        precoElem.addTextNode(String.valueOf(preco));
    }

    private static String extractResponse(SOAPMessage soapMessage) throws SOAPException {
        SOAPBody soapBody = soapMessage.getSOAPBody();
        SOAPElement responseElement = (SOAPElement) soapBody.getElementsByTagName("return").item(0);
        return responseElement.getTextContent();
    }
}
