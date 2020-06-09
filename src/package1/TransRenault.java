package package1;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNamedNodeMap;
import com.sun.xml.internal.ws.util.xml.NodeListIterator;
import org.w3c.dom.*;

import javax.crypto.spec.PSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.sql.SQLOutput;

public class TransRenault {
    public static void main(String[] args) throws Exception {
        Document document_src = null;

        String htmlFile = "/home/goku/Code/Xml/ProjetDocumentStructure/projet/projet_bis/poeme/fiches/renault/renault.html";
        DocumentBuilderFactory parseur0 = DocumentBuilderFactory.newInstance();
        DocumentBuilder parseur = parseur0.newDocumentBuilder();
//        System.out.println("avant parse");
//        document_src =  parseur.parse(htmlFile);

        // creation du document but
        DOMImplementation domimp = parseur.getDOMImplementation();
        Document document_but = domimp.createDocument(null, "Concessionnaires", null);
        document_but.setXmlStandalone(true);
        Element rac_but = document_but.getDocumentElement();
        //--------------------------------------------


        document_src = (Document) serializeDataIn();

        //extraire la racine
        Element racineSrc = document_src.getDocumentElement();

        System.out.println("apres parse");
        System.out.println(document_src);

        NodeList div = racineSrc.getElementsByTagName("div");

        System.out.println(div.getLength());
        int len = div.getLength();
        for (int i = 0; i < len; i++) {
            System.out.println("aaaa");
            Element div0 = (Element) div.item(i);

            if (div0.getAttribute("class").equals("post-single")) {//on a truvé le bon div
                System.out.println("nekchem");
                NodeList pListe = div0.getElementsByTagName("p");//a la recherche des p
                StringBuilder ch = new StringBuilder("");
                /****************************************solution de X **********************************************************/
                boolean first= true;

                for(int l=0; l<pListe.getLength(); l++){
                    Element p = (Element)pListe.item(l);
                    NodeList pnoeuds = p.getChildNodes();
                  if(pnoeuds.item(1).getNodeName().compareTo("strong")==0){
                        Element nom = document_but.createElement("Nom");
                        Element adresse = document_but.createElement("Adresse");
                        Element tel = document_but.createElement("Num_téléphone");

                        nom.appendChild(document_but.createTextNode(pnoeuds.item(1).getFirstChild().getNodeValue().replaceAll("[\n\r]", " ").trim()));

                        String adresseValue = pnoeuds.item(6).getNodeValue();
                        if(first) adresseValue= adresseValue.replace(":", "");

                        adresse.appendChild(document_but.createTextNode(adresseValue.replaceAll("[\n\r]", " ").trim()));

                        String telValue = pnoeuds.item(first ? 10 : 8).getNodeValue();
                        if(first) telValue= telValue.replace(":", "");
                        tel.appendChild(document_but.createTextNode(telValue.replaceAll("[\n\r]", " ").trim()));

                        rac_but.appendChild(nom);
                        rac_but.appendChild(adresse);
                        rac_but.appendChild(tel);

                        if(first) first=false;
                    }
                }





                /**************************************************************************************************/






                //for (int j = 0; j < p.getLength(); j++) {//on doit parcurir ces élements
                  //  NodeList px = p.item(j).getChildNodes();
                    //System.out.println("/////////-----------------------------------////////////");
                    //Element paragraphe = (Element) p.item(i);
//                    Node br = paragraphe.getElementsByTagName("br").item(0);
//
//                    System.out.println("---------" + i + "----------");
//
//                    Node titre = br.getPreviousSibling().getPreviousSibling().getFirstChild();
//                    System.out.println("+Titre = " + titre.getNodeName() + " valeur = " + titre.getNodeValue().replaceAll("[\r\n]+", " ").trim());
//                    System.out.println("j= "+j+"  longeur enfant= "+p.item(i).getChildNodes().getLength());
//                    int cpt = -1;
//                    Element Num_telephone = null;
/*                    for (int k = 0; k < px.getLength(); k++) {
                        String out = px.item(k).getNodeValue();

                        if (out != null) {
                            String out0 = out.replaceAll("[\r\n]+", " ").trim();
                            //System.out.println("out\n" + out);
                            if (!out0.isEmpty()) {
                                cpt++;
                                switch (cpt) {
                                    case 0:
                                        Element Nom = document_but.createElement("Nom");
                                        Nom.appendChild(document_but.createTextNode(out0));
                                        rac_but.appendChild(Nom);
                                        break;
                                    case 1:
                                        Element Adresse = document_but.createElement("Adresse");
                                        Adresse.appendChild(document_but.createTextNode(out0));
                                        rac_but.appendChild(Adresse);
                                        break;
                                        case 2:
                                        Num_telephone = document_but.createElement("Num_téléphone");
                                        Num_telephone.appendChild(document_but.createTextNode(out0));
                                        rac_but.appendChild(Num_telephone);
                                        break;
                                } System.out.println("les truc  " + out0);


                            }
                        }
                    }*/





//                    System.out.println(p.item(1).getNodeValue());
            /*        System.out.println("***************************************************************************\nj= ");
                    parcourRecursif(p.item(1), ch);
                    System.out.println("la chaine" + ch);*/
        //              }

//                Element paragraphe = (Element)p.item(i);
//                Node br = paragraphe.getElementsByTagName("br").item(0);
//
//                Node titre = br.getPreviousSibling().getPreviousSibling().getFirstChild();
//                System.out.println("taille: "+br.getNodeValue());
//                Element pp =  (Element) p.item(1);


//                System.out.println(ch);


//                System.out.println("le pp: \n"+pp.getFirstChild().getNodeValue());


//                    Element br0= (Element)p.item(j);
//                    Node p0 =  p.item(j);
//                    System.out.println("first CHild: " +p0.getFirstChild().getNodeName());

//                    NodeList br = br0.getElementsByTagName("br");
//                    System.out.println("taille du strong"+br.getLength());

//                    for (int k = 0; k <br.getLength() ; k++) {
//                        System.out.println("le i "+i+"  j: "+j+" k: "+k);
//                        Element elementStrong =(Element) br.item(k);

//                        System.out.println("element p: "+elementStrong.getTextContent());

            }
//                    System.out.println("all"+strong0.getNodeValue());
//                    System.out.println("childs "+p.item(j).getChildNodes()) ;
//                    System.out.println(p.item(j).getNodeValue());

        }
        DOMSource ds = new DOMSource(document_but);
        StreamResult res = new StreamResult(new File("/home/goku/Code/Xml/ProjetDocumentStructure/mes sorties/renault0.xml"));

        TransformerFactory transform = TransformerFactory.newInstance();
        //Création du transformateur "tr".
        Transformer tr = transform.newTransformer();
        //************************************************
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");

        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        tr.transform(ds, res);

//            NodeList res = (NodeList) div.item(i).getAttributes("post-single");
//            if (div.item(i).getNodeValue() != null){
//                System.out.println("le nom de la classe: "+div0.getAttribute("class"));
//            }

//
//            for (int j = 0; j < res.getLength(); j++) {
//                Node out =res.item(j);
//
//
//            System.out.println("nodeValue:\n"+out.getNodeValue());
////            System.out.println("item "+i+"child nodes"+" = "+p.item(i).getFirstChild().getNodeValue());
//            }
//        System.out.println("laaylaay");
    }



    public static void parcourRecursif(Node E, StringBuilder ss) {
        NodeList LN = E.getChildNodes();
        for (int i = 0; i < LN.getLength(); i++) {
            if (LN.item(i).getNodeValue() != null && !LN.item(i).getNodeValue().equals("null")){
                System.out.println(LN.item(i).getNodeName());
                ss.append(LN.item(i).getNodeValue());}
            if (E.hasChildNodes())
                parcourRecursif(LN.item(i), ss);
        }
    }

    public static void serializeDataOut (Object ish)throws Exception{
        //Méthode pour enregistrer les objets
        String fileName= "/home/goku/Code/Xml/ProjetDocumentStructure/mes sorties/Test.txt";
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(ish);
        oos.close();
    }

    public static Document serializeDataIn() throws IOException, ClassNotFoundException {
//        Methode pour litre les objet enregistrer

        String fileName= "/home/goku/Code/Xml/ProjetDocumentStructure/mes sorties/Test.txt";
        FileInputStream fin = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fin);
        Document iHandler= (Document) ois.readObject();
        ois.close();
        return iHandler;
    }


}
