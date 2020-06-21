package package1;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;


import static package1.Output_creation_et_repetitif.createParser;
import static package1.Output_creation_et_repetitif.creation_sortie;

public class Transform_Renault {
    public static  void transform_renault(String input, String output) throws Exception {

        DocumentBuilder parseur = createParser();
//        System.out.println("avant parse");
        Document document_src =  parseur.parse(input);

        // creation du document but
        DOMImplementation domimp = parseur.getDOMImplementation();
        Document document_but = domimp.createDocument(null, "Concessionnaires", null);

        Element rac_but = document_but.getDocumentElement();
        //--------------------------------------------


//        document_src = (Document) serializeDataIn();

        //extraire la racine
        Element racineSrc = document_src.getDocumentElement();

//        System.out.println("apres parse");

        NodeList div = racineSrc.getElementsByTagName("div");

//        System.out.println(div.getLength());
        int len = div.getLength();
        for (int i = 0; i < len; i++) {
            Element div0 = (Element) div.item(i);

            if (div0.getAttribute("class").equals("post-single")) {//on a trouvé le bon div
                NodeList pListe = div0.getElementsByTagName("p");//a la recherche des p
                StringBuilder ch = new StringBuilder("");

                boolean first= true;

                for(int l=0; l<pListe.getLength(); l++)
                {
                    Element p = (Element)pListe.item(l);
                    NodeList pnodes = p.getChildNodes();
                    if (pnodes.item(1).getNodeName().compareTo("strong")==0)
                    {
                        Element nom = document_but.createElement("Nom");
                        Element adresse = document_but.createElement("Adresse");
                        Element tel = document_but.createElement("Num_téléphone");

                        nom.appendChild(document_but.createTextNode(pnodes.item(1).getFirstChild().getNodeValue().replaceAll("[\n\r]", " ").trim()));

                        String adresseValue = pnodes.item(6).getNodeValue();
                        if(first) adresseValue= adresseValue.replace(":", "");

                        adresse.appendChild(document_but.createTextNode(adresseValue.replaceAll("[\n\r]", " ").trim()));

                        String telValue = pnodes.item(first ? 10 : 8).getNodeValue();
                        if(first) telValue= telValue.replace(":", "");
                        tel.appendChild(document_but.createTextNode(telValue.replaceAll("[\n\r]", " ").trim()));

                        rac_but.appendChild(nom);
                        rac_but.appendChild(adresse);
                        rac_but.appendChild(tel);

                        if(first) first=false;
                    }
                }
            }
        }

        creation_sortie(output,document_but);


    }

}
