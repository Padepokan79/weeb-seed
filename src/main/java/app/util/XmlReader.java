package app.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlReader {

	Document doc;
	
	public XmlReader(String filePath) {
		try {
			parse(filePath);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	public void parse(String filePath) throws ParserConfigurationException, IOException, SAXException {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    builder.setEntityResolver(new EntityResolver() {
	        @Override
	        public InputSource resolveEntity(String publicId, String systemId)
	                throws SAXException, IOException {
	            if (systemId.contains("http://jetty.eclipse.org/configure.dtd")) {
	                return new InputSource(new StringReader(""));
	            } else {
	                return null;
	            }
	        }
	    });
	    File file = new File(filePath);
	    this.doc = builder.parse(file);	    
	}
	
	public String getElementBySpecificAttribute(String tagName, String attributeName, String attributeValue) {
		if (attributeValue != null && !attributeValue.equals("")) {
			if (this.doc != null) {
		    	this.doc.getDocumentElement().normalize();

		        NodeList nodes = this.doc.getElementsByTagName(tagName);
		        
		    	for (int i = 0; i < nodes.getLength(); i++) {
			    	Element element = (Element) nodes.item(i);
			    	System.out.println("Element : " + element.getAttribute(attributeName));
			    	
			    	for (int j = 0; j < element.getChildNodes().getLength(); j++) {
				    	if (attributeValue.equals(element.getAttribute(attributeName))) {
				    		return getCharacterDataFromElement(element);
				    	}	
					}		    	
				}	    	
		    }	    
		}

	    return "";
	}
	
	private String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}
}
