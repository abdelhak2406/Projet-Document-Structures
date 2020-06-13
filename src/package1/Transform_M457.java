package package1;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Transform_M457
{
    public static void transform_m457(String input, String output) throws Exception
    {
        System.out.println("\n/* --------------Debut-------------- */\n");

        // recuperer le fichier a traiter
        String xmlFile = "C:/Users/Dyhia/Desktop/MyProject/M457.xml";

        // construire le parseur
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);

        factory.setFeature ("http://xml.org/sax/features/namespaces", false);
        factory.setFeature ("http://xml.org/sax/features/validation", false);
        factory.setFeature ("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        factory.setFeature ("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setSchema(null);

        DocumentBuilder parseur = factory.newDocumentBuilder();
        Document doc_src = parseur.parse(xmlFile);
        Element racine_src = doc_src.getDocumentElement();
        //***********************************************************************************************************

        //creation du domImplementation
        DOMImplementation domimp = parseur.getDOMImplementation();
        // creation du document
        Document doc_but = domimp.createDocument(null,"TEI_S",null);
        doc_but.setXmlStandalone(true);
        //récupération du noeud d'element racine
        Element racine_but= doc_but.getDocumentElement();
        //creation du premier element
        Element M457 = doc_but.createElement("M457.xml");
        racine_but.appendChild(M457);

        //analyse du doc source
        NodeList p =  racine_src.getElementsByTagName("p");
        int len = p.getLength();
        for (int i=0;i<len;i++)
        {
            NodeList p1 = p.item(i).getChildNodes();
            for (int j = 0; j < p1.getLength(); j++)
            {
                String out = p1.item(j).getNodeValue();
                if (out != null)
                {
                    if (i == 14 && j == 62) {continue;}

                    Element texte = doc_but.createElement("texte");
                    texte.appendChild(doc_but.createTextNode(out.replaceAll("[\r\n]+", "")));
                    M457.appendChild(texte);
                }

            }
        }
        DOMSource ds = new DOMSource(doc_but);
        StreamResult res = new StreamResult(new File("C:/Users/Dyhia/Desktop/MyProject/sorties/sortie2.xml"));
        TransformerFactory transform = TransformerFactory.newInstance();
        //Création du transformateur "tr".
        Transformer tr = transform.newTransformer();
        //************************************************
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
        tr.transform(ds, res);

    }

}

