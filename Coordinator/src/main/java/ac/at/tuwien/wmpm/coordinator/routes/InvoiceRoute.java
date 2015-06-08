package ac.at.tuwien.wmpm.coordinator.routes;

import ac.at.tuwien.wmpm.domain.model.IncomingRequest;

import java.io.File;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class InvoiceRoute {
  
  private static final Logger logger = LoggerFactory.getLogger(InvoiceRoute.class);

/*public static void main(String argv[]) throws ParserConfigurationException, TransformerException {
  
    IncomingRequest ir = new IncomingRequest(UUID.randomUUID());
    
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
  
    Document doc = docBuilder.newDocument();
    Element rootElement = doc.createElement("Invoices");
    doc.appendChild(rootElement);
  
    Element invoice = doc.createElement("Invoice");
    rootElement.appendChild(invoice);
  
    Attr attr = doc.createAttribute("invoice_Id");
    attr.setValue(ir.getId().toString());
    invoice.setAttributeNode(attr);
    
    Element expert = doc.createElement("expert");
    expert.appendChild(doc.createTextNode("Test")); //xx.getExpert....
    invoice.appendChild(expert);
   
    Element mail = doc.createElement("email");
    mail.appendChild(doc.createTextNode("Max.Superexpert@gmail.com")); //xx.getExpertMail...
    invoice.appendChild(mail);
      
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File("../invoices/invoice.xml"));
  
    transformer.transform(source, result);
    
    logger.info("Invoice File created"); 
  
  }*/
}