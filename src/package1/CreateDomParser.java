package package1;
import org.w3c.dom.DOMImplementation;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
public class CreateDomParser{
    static DocumentBuilder parseur;
    public static  DocumentBuilder parseur() throws ParserConfigurationException {
        parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return parseur;
    }
    public static DOMImplementation imp() throws ParserConfigurationException {
        DOMImplementation imp =	parseur.getDOMImplementation();
        return imp;
    }
}