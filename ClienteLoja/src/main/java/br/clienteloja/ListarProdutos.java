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
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ListarProdutos {

    public String callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Criar conexão SOAP
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Enviar SOAP Message para o servidor
            SOAPMessage soapRequest = createSOAPRequest(soapAction);
            SOAPMessage soapResponse = soapConnection.call(soapRequest, soapEndpointUrl);

            // Extrair resposta do servidor
            String response = extractResponse(soapResponse);

            soapConnection.close();

            return response;
        } catch (Exception e) {
            return "ERRO:\n" + e.getMessage();
        }
    }

    private SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        // Criar mensagem SOAP
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        // Criar envelope SOAP
        createSoapEnvelope(soapMessage);

        // Adicionar ação SOAP
        soapMessage.getMimeHeaders().addHeader("SOAPAction", soapAction);
        soapMessage.saveChanges();

        return soapMessage;
    }

    private void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // Verificar no WSDL o namespace utilizado
        String myNamespace = "ws";
        String myNamespaceURI = "http://ws.br/";

        // Preencher SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // Preencher SOAP Body
        SOAPBody soapBody = envelope.getBody();
        soapBody.addChildElement("listarProdutos", myNamespace);
    }

    private String extractResponse(SOAPMessage soapMessage) throws SOAPException {
        SOAPBody soapBody = soapMessage.getSOAPBody();
        NodeList productList = soapBody.getElementsByTagName("return");

        StringBuilder responseBuilder = new StringBuilder();
        for (int i = 0; i < productList.getLength(); i++) {
            Node productNode = productList.item(i);
            if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                Element productElement = (Element) productNode;

                String id = productElement.getElementsByTagName("id").item(0).getTextContent();
                String nome = productElement.getElementsByTagName("nome").item(0).getTextContent();
                String quantidade = productElement.getElementsByTagName("quantidade").item(0).getTextContent();
                String preco = productElement.getElementsByTagName("preco").item(0).getTextContent();

                responseBuilder.append("----------------------------------\n");
                responseBuilder.append("ID: ").append(id).append("\n");
                responseBuilder.append("Nome: ").append(nome).append("\n");
                responseBuilder.append("Quantidade: ").append(quantidade).append("\n");
                responseBuilder.append("Preço: ").append(preco).append("\n");
                responseBuilder.append("----------------------------------\n");
            }
        }

        return responseBuilder.toString();
    }
}
