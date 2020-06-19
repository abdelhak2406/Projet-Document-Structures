package package1;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static package1.Output_creation_et_repetitif.creation_sortie;
import static package1.Output_creation_et_repetitif.setFeatures;


public class Transform_M674 {
    public static  void transform_m674(String input, String output) throws Exception{

        DocumentBuilderFactory parseur0 = DocumentBuilderFactory.newInstance();
        parseur0 = setFeatures(parseur0);

        /*        parseur0.setValidating(false);
        parseur0.setFeature ("http://xml.org/sax/features/namespaces", false);
        parseur0.setFeature ("http://xml.org/sax/features/validation", false);
        parseur0.setFeature ("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
        parseur0.setFeature ("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        parseur0.setSchema(null);*/
        DocumentBuilder parseur = parseur0.newDocumentBuilder();
        Document document_src = parseur.parse(input);

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

                if (out!= null  )
                {  // System.out.println("aaa "+p1.item(j).getChildNodes());
                    if (i==6 && j==188 ){//le dernier element qui
                      continue;
                    }
//                    System.out.print("le type: "+p1.item(j).getNodeValue().getClass().getName());
                    Element texte = document_but.createElement("texte");

                    texte.appendChild(document_but.createTextNode(out.replaceAll("[\r\n]+", "")));
                    M674.appendChild(texte);

                }


            }

        }

        creation_sortie(output,document_but);


       }

}
