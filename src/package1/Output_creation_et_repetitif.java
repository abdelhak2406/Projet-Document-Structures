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
import java.io.*;


public class Output_creation_et_repetitif {
    public static void creation_sortie(String out, Document doc_but) throws Exception
    {
        doc_but.setXmlStandalone(true);

        DOMSource ds = new DOMSource(doc_but);
        StringWriter s = new StringWriter();
        StreamResult res = new StreamResult(s);
        TransformerFactory transform = TransformerFactory.newInstance();
        //Création du transformateur "tr".
        Transformer tr = transform.newTransformer();
        StreamResult sortie = new StreamResult(s);
        tr = setProprieties(tr,out);
        tr.transform(ds, res);
        //transformer les  espaces en \t comme dans les sorties du prof et la ligne en plus dans javafx
        transformSpaceToindentation(s,out);


    }

    private static void transformSpaceToindentation(StringWriter s, String out) throws IOException {


        String str = (s+"").replaceAll(" {12}<", "\t\t\t<");
        str = str.replaceAll(" {8}<", "\t\t<");
        str = str.replaceAll(" {4}<", "\t<");
        if (out.equals("javafx.xml")){
            str = str.replaceFirst("[\n\r]+$", "");
        }
        FileOutputStream fos = new FileOutputStream(new File(out));
        fos.write(str.getBytes("UTF-8"));

    }

    private static Transformer setProprieties(Transformer tra, String out)
    {
        tra.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tra.setOutputProperty(OutputKeys.INDENT, "yes");
        tra.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        switch (out)
        {
            case "sortie1.xml":
            case "sortie2.xml":
                //marche pour m674 les autres c'est un peu différent

                tra.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
                break;
            case "javafx.xml":
                tra.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
                tra.setOutputProperty(OutputKeys.METHOD, "xml");
                break;
            case "neruda.xml":
                tra.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "neruda.dtd");
                tra.setOutputProperty(OutputKeys.METHOD, "xml");
                break;
            default://fiches1,2 et renault
                tra.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
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
