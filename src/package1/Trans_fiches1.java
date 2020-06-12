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
import java.util.ArrayList;
import java.util.HashMap;

public class Trans_fiches1
{
    public static void main(String[] args) throws  Exception,IOException
    {
        //Declarations hashmap
        HashMap<String, ArrayList<String>> mon_dico =  new HashMap<>();


        //Lire le fichier text fiches
        InputStream flux = new FileInputStream("/home/goku/Code/Xml/Projet-Document-Structur-/projet/projet_bis/poeme/fiches/fiches.txt");
        InputStreamReader lecture = new InputStreamReader(flux);
        BufferedReader buffer = new BufferedReader(lecture);
        //System.out.println(buff.readLine());
        String ligne;
        int i = -1;
        System.out.println(mon_dico.get("header1"));
        ligne = buffer.readLine();
        //lecture et extraction
        while(  ligne != null )
        {

            if ( ligne.matches("PNR(.*)") )
            {
                System.out.println("nous somomes dans le pnr");

                i++;
                String key = "header"+i;
                ArrayList<String> tab =  new ArrayList<String>();
                mon_dico.put(key,tab);
                while (ligne!= null &&  (!ligne.matches("AR(.*)")))
                {
                    mon_dico.get(key).add(ligne);
                    ligne = buffer.readLine();
                }
                System.out.println("fin du pnr");

            }else if(ligne.matches("AR(.*)"))
                {
                    System.out.println("nous sommes dans le ar");

                    String key = "Arabe"+i;
                    ArrayList<String> tab =  new ArrayList<String>();
                    mon_dico.put(key,tab);
                    while (ligne!= null && !ligne.matches("FR"))
                    {
                        mon_dico.get(key).add(ligne);
                        ligne = buffer.readLine();
                    }
                    System.out.println("fin du ar");
                }else //francais
                {
                    System.out.println("nous sommes dans le fr");
                    String key = "Francais"+i;
                    ArrayList<String> tab =  new ArrayList<String>();
                    mon_dico.put(key,tab);
                    while (ligne!= null && !ligne.matches("PNR(.*)"))
                    {

                        if (ligne.length() >0 && !ligne.matches("[\r\n\t\\s]*") )
                        {
                            mon_dico.get(key).add(ligne);

                        }
                        ligne = buffer.readLine();
                    }
                    System.out.println("fin du fr");
                }
        }
        //ecriture

        DocumentBuilder parseur = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        DOMImplementation domimp = parseur.getDOMImplementation();
        Document document_but = domimp.createDocument(null, "FICHES", null);
        document_but.setXmlStandalone(true);
        Element rac_but = document_but.getDocumentElement();

        for (i = 0; i <4 ; i++) {

            Element fich = document_but.createElement("FICHE");
            fich.setAttribute("id",""+(i+1));
            rac_but.appendChild(fich);
            remplissage(document_but, mon_dico, fich,"header"+i,"Arabe"+i,"Francais"+i);
        }
        for (int j = 0; j < 4; j++) {
             System.out.println(mon_dico.get("Francais"+j).size());
            }

        // Ecriture

        DOMSource ds = new DOMSource(document_but);
        StreamResult res = new StreamResult(new File("/home/goku/Code/Xml/Projet-Document-Structur-/mes sorties/fichier001.xml"));

        TransformerFactory transform = TransformerFactory.newInstance();
        //Création du transformateur "tr".
        Transformer tr = transform.newTransformer();
        //************************************************
        tr.setOutputProperty(OutputKeys.INDENT, "yes");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"yes");

        tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        tr.transform(ds, res);

}

public static void remplissage(Document document_but,HashMap<String, ArrayList<String>> mon_dico,Element fich,String head,String ar,String fr)
{
    Element be = document_but.createElement("BE");
    Element ty = document_but.createElement("TY");
    Element au = document_but.createElement("AU");

    be.appendChild(document_but.createTextNode(mon_dico.get(head).get(0).replaceAll("BE","")));
    ty.appendChild(document_but.createTextNode("TY : "+mon_dico.get(head).get(1).replaceAll("TY","")));

    au.appendChild(document_but.createTextNode("AU : "+mon_dico.get(head).get(4).replaceAll("\tAU","\t")));
    fich.appendChild(be);
    fich.appendChild(ty);
    fich.appendChild(au);
    Element langue = document_but.createElement("Langue");
    langue.setAttribute("id","AR");
    fich.appendChild(langue);
    System.out.println("avant l'appel pour arrabe");
    remplissage_langue( document_but, mon_dico, langue, head,ar);
    System.out.println("avant l'appel pour francais");
    langue = document_but.createElement("Langue");
    langue.setAttribute("id","FR");
    fich.appendChild(langue);
    remplissage_langue( document_but, mon_dico, langue, head,fr);
}
public static void remplissage_langue(Document document_but,HashMap<String, ArrayList<String>> mon_dico,Element langue,String head,String lan)
{


    Element d0 = document_but.createElement("DO");
    d0.appendChild(document_but.createTextNode("DO : "+mon_dico.get(head).get(2).replaceAll("DO","")));
    langue.appendChild(d0);
    Element sd = document_but.createElement("SD");
    sd.appendChild(document_but.createTextNode("SD : "+mon_dico.get(head).get(3).replaceAll("SD","")));
    langue.appendChild(sd);


    Element ve = document_but.createElement("VE");
    ve.appendChild(document_but.createTextNode("VE : "+mon_dico.get(lan).get(1).replaceAll("VE :","")));
    langue.appendChild(ve);

    Element df = document_but.createElement("DF");
    df.appendChild(document_but.createTextNode("DF : "+mon_dico.get(lan).get(2).replaceAll("DF :","")));
    langue.appendChild(df);

    System.out.println(mon_dico.get(lan).get(3));

    Element ph = document_but.createElement("PH");
    ph.appendChild(document_but.createTextNode("PH : "+mon_dico.get(lan).get(3).replaceAll("PH :","")));
    langue.appendChild(ph);
    Element nt = document_but.createElement("NT");
    nt.appendChild(document_but.createTextNode("NT : "+mon_dico.get(lan).get(4).replaceAll("NT :","")));
    langue.appendChild(nt);

    System.out.println("nous somme avant le for");

    Element rf = null;
    String [] liste = null;
    //la cas spécial ou il faut inverser ...
    liste = mon_dico.get(lan).get(5).split("\t");
    String s = liste[1];
    //Construction de la chaine inversée à afficher.

    String _s="";
    for(int $=s.length()-1;$>0;$--){
        if(s.charAt($)==':') _s=_s+s.charAt($-3)+s.charAt($-2)+" : ";}
    System.out.println("le _s: "+_s);


//    System.out.println("taille\n"+liste.length);
    rf = document_but.createElement("RF");
    if (lan.matches("Arabe[013]"))
        rf.appendChild(document_but.createTextNode("RF | "+_s+liste[0]+"\t \t"));
        else if (lan.matches("Arabe2"))
            {
                System.out.println("aqlagh i arabe2");
                rf.appendChild(document_but.createTextNode("RF | " + _s + liste[0] + "\t   \t"));
            }
            else if(lan.matches("Francais3"))
                rf.appendChild(document_but.createTextNode("RF | "+_s+liste[0]+"\t  \t"));
                else
                rf.appendChild(document_but.createTextNode("RF | "+_s+liste[0]+"\t\t"));


    langue.appendChild(rf);




    for(int i=6;i<mon_dico.get(lan).size();i++ )
    {
        liste = mon_dico.get(lan).get(i).split("\t");
        System.out.println("la boucle i= "+i);
        parcour_liste(liste);
        rf = document_but.createElement("RF");
        rf.appendChild(document_but.createTextNode("RF | "+liste[liste.length-1]+" "+liste[0]+"\t\t"));
        langue.appendChild(rf);
    }

    
}
public  static void parcour_liste(String[] lis){
    for (int i = 0; i < lis.length; i++) {
        System.out.println("element "+i+"\n"+lis[i]);
    }
}

}

