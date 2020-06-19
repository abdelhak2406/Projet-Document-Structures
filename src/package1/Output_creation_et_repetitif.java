package package1;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Output_creation_et_repetitif {
    public static void creation_sortie(String out, Document doc_but) throws Exception
    {
        DOMSource ds = new DOMSource(doc_but);
        StreamResult res = new StreamResult(new File(out));

        TransformerFactory transform = TransformerFactory.newInstance();
        //Création du transformateur "tr".
        Transformer tr = transform.newTransformer();
        tr = transformations(tr,out);
        tr.transform(ds, res);

    }

    public static Transformer transformations (Transformer tra,String out)
    {   switch (out){
        case "sortie1.xml":
        case "sortie2.xml":
            //marche pour m674 les autres c'est un peu différent
            tra.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tra.setOutputProperty(OutputKeys.INDENT, "yes");
            tra.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            tra.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
            System.out.println("\n\nnous sommes dans sortie1\n\n");
            System.out.println("valeur de out: "+out);
            break;
        case "javafx.xml":
            tra.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tra.setOutputProperty(OutputKeys.INDENT, "yes");
            tra.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
            tra.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            tra.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","4");
            tra.setOutputProperty(OutputKeys.METHOD, "xml");
            System.out.println("\n\nnous sommes dans javafx\n\n");
            System.out.println("valeur de out: "+out);
        case "neruda.xml":
            tra.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tra.setOutputProperty(OutputKeys.INDENT, "yes");
            tra.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            tra.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "neruda.dtd");
            tra.setOutputProperty(OutputKeys.METHOD, "xml");
            break;
        case "renault.xml":
            tra.setOutputProperty(OutputKeys.INDENT, "yes");
            tra.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tra.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
            tra.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            System.out.println("valeur de out: "+out);
            break;
        default:
            System.out.println("\n nAAAAAAAAAAAAAAAAAAAAAAAAAAAA\nAAAAAAAAAAAAAAAAAAAAA\nAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

        return tra;
    }

    public  static DocumentBuilder createParser() throws ParserConfigurationException
    {
        DocumentBuilder par = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return par;


    }
    public static DocumentBuilderFactory setFeatures(DocumentBuilderFactory parseur0) throws ParserConfigurationException {
        parseur0.setValidating(false);
        parseur0.setFeature ("http://xml.org/sax/features/namespaces", false);
        parseur0.setFeature ("http://xml.org/sax/features/validation", false);
        parseur0.setFeature ("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        parseur0.setFeature ("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        parseur0.setSchema(null);
        return parseur0;
    }
}
