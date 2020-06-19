package package1;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

import static package1.Output_creation_et_repetitif.createParser;
import static package1.Output_creation_et_repetitif.creation_sortie;

public class Transform_poeme
{
    public static  void transform_poeme(String input, String output) throws Exception
    {
        System.out.println("\n/* --------------Debut-------------- */\n");

        //creation du domImplementation
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parseur = createParser();

        DOMImplementation domimp = parseur.getDOMImplementation();
        // creation du document
        Document doc_but = domimp.createDocument(null, "poema", null);
        doc_but.setXmlStandalone(true);
        //récupération du noeud d'element racine
        Element racine_but = doc_but.getDocumentElement();

        //Lire le fichier text poeme
        InputStream flux = new FileInputStream(input);
        InputStreamReader lecture = new InputStreamReader(flux);
        BufferedReader buffer = new BufferedReader(lecture);
        //System.out.println(buff.readLine());

        String ligne;
        int i = 1;

        while ((ligne = buffer.readLine()) != null)
        {
            if (i == 1) // récupérer le titre dab
            {
                Element titre = (Element) doc_but.createElement("titulo");
                racine_but.appendChild(titre);
                titre.appendChild((doc_but.createTextNode(ligne)));
                i++;
            } else // les couplets
                {
                if (ligne.length() > 0)
                {
                    Element estrofa_ele = doc_but.createElement("estrofa");
                    racine_but.appendChild(estrofa_ele);
                    while (ligne != null && ligne.length() > 0) //verso
                    {
                        Element verso_ele = doc_but.createElement("verso");
                        racine_but.appendChild(verso_ele);
                        verso_ele.appendChild((doc_but.createTextNode(ligne)));
                        estrofa_ele.appendChild(verso_ele);
                        ligne = buffer.readLine();
                    }
                }
            }

        }
        buffer.close();

        creation_sortie(output,doc_but);

    }
}
