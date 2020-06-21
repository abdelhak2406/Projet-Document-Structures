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
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Output_creation_et_repetitif {
    public static void creation_sortie(String out, Document doc_but) throws Exception
    {
        doc_but.setXmlStandalone(true);
        DOMSource ds = new DOMSource(doc_but);
        StreamResult res = new StreamResult(new File(out));

        TransformerFactory transform = TransformerFactory.newInstance();
        //Création du transformateur "tr".
        Transformer tr = transform.newTransformer();
        tr = transformations(tr,out);
        tr.transform(ds, res);
        //transformer les  espaces en \t comme dans les sorties du prof et la ligne en plus dans javafx
        transform_indentation(out);

    }

    private static void transform_indentation(String out) throws IOException
    {
        Path file = Paths.get(out);
        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(file), charset);
        content = content.replaceAll(" {12}<", "\t\t\t<");
        content = content.replaceAll(" {8}<", "\t\t<");
        content = content.replaceAll(" {4}<", "\t<");
        if(out.equals("javafx.xml"))//pour enlever la derniére ligne en trop
            content = content.replaceAll("</Racine>\n", "</Racine>");
        Files.write(file, content.getBytes(charset));
    }

    private static Transformer transformations (Transformer tra,String out)
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
