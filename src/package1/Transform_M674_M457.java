package package1;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static package1.Output_creation_et_repetitif.creation_sortie;
import static package1.Output_creation_et_repetitif.setFeatures;


public class Transform_M674_M457 {

    public static  void transform_mX(String input, String output) throws Exception
    {
        int MX ;
        if(output.equals("sortie1.xml"))
            MX = 1;
        else
            MX = 2;
        // construire le parseur
        DocumentBuilderFactory parseur0 = DocumentBuilderFactory.newInstance();
        parseur0 = setFeatures(parseur0);
        DocumentBuilder parseur = parseur0.newDocumentBuilder();

        Document document_src = parseur.parse(input);

        //creation du domImplementation
        DOMImplementation domimp = parseur.getDOMImplementation();
        // creation du document
        Document document_but = domimp.createDocument(null,"TEI_S",null);
        //récupération du noeud d'element racine
        Element racine_but= document_but.getDocumentElement();
        //creation du premier element
        Element M674_or_457 = null;
        if (MX==1)
             M674_or_457 = document_but.createElement("M674.xml");
        else
             M674_or_457 = document_but.createElement("M457.xml");
        racine_but.appendChild(M674_or_457);

        //chercher la premiére phrase
        NodeList p =  document_src.getElementsByTagName("p");
        int len = p.getLength();
        for (int i=0;i<len;i++)
        {
            NodeList p1 =p.item(i).getChildNodes();
            for (int j=0 ;j<p1.getLength();j++)
            {
                String out = p1.item(j).getNodeValue();
                if (out!= null )
                {
                    if(MX ==1 ) //m674
                    {
                        if (i == 6 && j == 188) continue;//le dernier element qui nous pose un porbleme
                    }
                    else  //m457
                    {
                        if (i == 14 && j == 62) continue;
                    }
                    Element texte = document_but.createElement("texte");
                    texte.appendChild(document_but.createTextNode(out.replaceAll("[\r\n]+", "")));
                    M674_or_457.appendChild(texte);

                }
            }
        }

        creation_sortie(output,document_but);


       }

}
