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

public class Transform_M674 {
    public static void main(String[] args) throws Exception{
        String xmlFile = "/home/goku/Code/Xml/ProjetDocumentStructure/projet/projet_bis/M674.xml";
        DocumentBuilderFactory parseur0 = DocumentBuilderFactory.newInstance();
        System.out.println("adgg");
        parseur0.setValidating(false);
        parseur0.setFeature ("http://xml.org/sax/features/namespaces", false);
        parseur0.setFeature ("http://xml.org/sax/features/validation", false);
        parseur0.setFeature ("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        parseur0.setFeature ("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        parseur0.setSchema(null);
        DocumentBuilder parseur = parseur0.newDocumentBuilder();
        Document document_src = parseur.parse(xmlFile);

        //creation du domImplementation
        DOMImplementation domimp = parseur.getDOMImplementation();
        // creation du document
        Document document_but = domimp.createDocument(null,"TEI_S",null);
        document_but.setXmlStandalone(true);
        Element rac_but= document_but.getDocumentElement();//récupération du noeud d'element racine
        //creation du premier element
        Element M674 = document_but.createElement("M674.xml");
       // Element racineTEI_S = document_but.createElement("shit");

        rac_but.appendChild(M674);

        //racineTEI_S.appendChild(M674);//l'enfant 1


        //chercher la premiére phrase
        NodeList p =  document_src.getElementsByTagName("p");
        int len = p.getLength();

        for (int i=0;i<len;i++)
        {
          NodeList p1 =p.item(i).getChildNodes();

//            System.out.println("taille de l'item "+i+"= "+p1.getLength());
//            System.out.println("**************************************************************");
            for (int j=0 ;j<p1.getLength();j++)
            {
//                System.out.println("le node Value "+j+" "+p1.item(j).getNodeValue());
                String out = p1.item(j).getNodeValue();
               if (i==6 && j==188 && out!="\n")
               {
                   System.out.println("************************************************************************************************");
                   System.out.println("i "+i+" j "+j);
                   System.out.println("le out\n"+out);
                   System.out.println("************************************************************************************************");
               }


                if (out!= null  )
                {   System.out.println("aaa "+p1.item(j).getChildNodes());
                    if (i==6 && j==188 ){//le dernier element qui
                      continue;
                    }
//                    System.out.print("le type: "+p1.item(j).getNodeValue().getClass().getName());
                    Element texte = document_but.createElement("texte");

                    texte.appendChild(document_but.createTextNode(out.replaceAll("[\r\n]+", "")));
                    M674.appendChild(texte);

                }


            }
//            Element aa = (Element) p.item(i);
//            String leTexte = aa.getTextContent();
//
//            Element texte = document_but.createElement("text");
//            //System.out.println(leTexte);
//            texte.appendChild(document_but.createTextNode(leTexte));
//            M674.appendChild(texte);

            // System.out.println("le text "+aa.getTextContent()+"\nl'autre");
        }
        //************************************
        //La sortie
        DOMSource ds = new DOMSource(document_but);
        StreamResult res = new StreamResult(new File("/home/goku/Code/Xml/ProjetDocumentStructure/mes sorties/sortie0.xml"));

        TransformerFactory transform = TransformerFactory.newInstance();
        //Création du transformateur "tr".
        Transformer tr = transform.newTransformer();
        //************************************************
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "dom.dtd");
        tr.transform(ds, res);

        //Document document_but = transformer(document_src);

//        //***************Impression du resultat************//
//        DOMSource ds = new DOMSource(document_but);
//        StreamResult res = new StreamResult(new File("/home/goku/Code/Xml/ProjetDocumentStructure/mes sorties/sortie1.xml"));
//        TransformerFactory transform = TransformerFactory.newInstance();
//        Transformer tr = transform.newTransformer();
//        document_but.setXmlStandalone(true);
//        tr.transform(ds, res);
       }
//    public static Document transformer (Document document_src) throws Exception{
//        DOMImplementation domimp = CreateDomParser.imp();
//        DocumentBuilder parseur;
//        DOMImplementation domimp = parseur.getDOMImplementation();
//
//        Document document_but =	domimp.createDocument(null,"TEI_S", null);//création de l'objet document
//
//
//        Element rac_but= document_but.getDocumentElement();//récupération du noeud d'elemen racine
//        //creation du premier element
//        Element m674 = document_but.createElement("M674.xml");
//        Element texte = document_but.createElement("text");
//        //la on doit récupérer Dans un cadre de recherche ou d'esneignement...
//
//
//        NodeList p =  document_src.getElementsByTagName("p");
//        int len = p.getLength();
//        for (int i=0;i<len;i++)
//        {
//            System.out.println(p.item(i).getAttributes());
//        }
//
//        String contenu = lb.item(0).getFirstChild().getNodeValue();
//        Element texte = document_but.createElement("texte");
//        rac_but.appendChild(texte);
//        texte.appendChild(document_but.createTextNode(contenu));
//     return document_but;
//    }
}
