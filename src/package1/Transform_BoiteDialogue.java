package package1;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

public class Transform_BoiteDialogue
{
    public static void transform_boiteDialogue(String input, String output) throws Exception
    {
        // creation du document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parseur = factory.newDocumentBuilder();
        DOMImplementation domimp = parseur.getDOMImplementation();
        Document doc_but = domimp.createDocument(null, "Racine", null);
        doc_but.setXmlStandalone(true);

        //creation du noeud d'element racine
        Element racine_but = doc_but.getDocumentElement();
        racine_but.setAttribute("xmlns:fx", "http://javafx.com/fxml");

        //Lecture du fichier fxml

        Document doc_src = parseur.parse(input);
        Element racine_src = doc_src.getDocumentElement();


        //Traitements
        Element elt = null;
        for(int i=0;i<racine_src.getAttributes().getLength();i++)
        {
            Attr attr = (Attr) racine_src.getAttributes().item(i);
            elt = doc_but.createElement("texte");
            elt.setAttribute(attr.getName(), "x");
            elt.appendChild(doc_but.createTextNode(attr.getValue()));
            //elt.setTextContent(attr.getValue());
            racine_but.appendChild(elt);
        }
        recursive(racine_src,elt,racine_but, doc_but);

        // enregistrer le fichier en sortie et Création du transformateur "tr".
        DOMSource ds = new DOMSource(doc_but);
        StreamResult res = new StreamResult(new File(output));
        TransformerFactory transform = TransformerFactory.newInstance();
        Transformer tr = transform.newTransformer();
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tr.setOutputProperty("{http://xml.apache.org/xalan}indent-amount","4");
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.transform(ds, res);

    }

    static void recursive (Node n,Element elt,Element rac,Document doc)
    {
        for (int j=0;j<n.getChildNodes().getLength();j++)
            if((!n.getChildNodes().item(j).getNodeName().matches("#text|#comment")))
            {
                Node e = n.getChildNodes().item(j);
                for(int k=0;k<e.getAttributes().getLength();k++){
                    Attr attr = (Attr) e.getAttributes().item(k);
                    elt = doc.createElement("texte");
                    elt.setAttribute(attr.getName(), "x");
                    elt.appendChild(doc.createTextNode(attr.getValue()));
                    rac.appendChild(elt);
                }
                recursive(e, elt, rac, doc);
            }
    }


}
