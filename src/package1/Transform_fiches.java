package package1;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import static package1.Output_creation_et_repetitif.createParser;
import static package1.Output_creation_et_repetitif.creation_sortie;

public class Transform_fiches
{
    public static int fichesX ;


    public static void transform_fiches(String input, String output1,String output2) throws Exception {
        transform_ficheX( input, output1);
        transform_ficheX( input, output2);
    }

    public static void transform_ficheX(String input, String output) throws  Exception,IOException
    {
        if(output.equals("fiches1.xml"))
            fichesX = 1;
        else
            fichesX = 2;

        //Declarations hashmap
        HashMap<String, ArrayList<String>> mon_dico =  new HashMap<>();

        //Lire le fichier text fiches
        InputStream flux = new FileInputStream(input);
        InputStreamReader lecture = new InputStreamReader(flux);
        BufferedReader buffer = new BufferedReader(lecture);

        String ligne;
        int i = -1;

        ligne = buffer.readLine();


        //lecture et extraction
        while(  ligne != null )
        {

            if ( ligne.matches("PNR(.*)") )
            {
//                System.out.println("nous sommes dans le pnr");
                i++;
                String key = "header"+i;
                ArrayList<String> tab =  new ArrayList<String>();
                mon_dico.put(key,tab);

                while (ligne!= null &&  (!ligne.matches("AR(.*)")))
                {
                    mon_dico.get(key).add(ligne);
                    ligne = buffer.readLine();
                }
//                System.out.println("fin du pnr");

            }else if(ligne.matches("AR(.*)"))
            {
//                System.out.println("nous sommes dans le ar");

                String key = "Arabe"+i;
                ArrayList<String> tab =  new ArrayList<String>();
                mon_dico.put(key,tab);
                while (ligne!= null && !ligne.matches("FR"))
                {
                    mon_dico.get(key).add(ligne);
                    ligne = buffer.readLine();
                }
//                System.out.println("fin du ar");
            }else //francais
            {
//                System.out.println("nous sommes dans le fr");
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
//                System.out.println("fin du fr");
            }
        }


        // Creation du parseur
        DocumentBuilder parseur = createParser();

        DOMImplementation domimp = parseur.getDOMImplementation();
        Document document_but = domimp.createDocument(null, "FICHES", null);
        Element rac_but = document_but.getDocumentElement();

        //Remplissage du document_but
        for (i = 0; i <4 ; i++)
        {
            Element fich = document_but.createElement("FICHE");
            fich.setAttribute("id",""+(i+1));
            rac_but.appendChild(fich);
            remplissage(document_but, mon_dico, fich,"header"+i,"Arabe"+i,"Francais"+i);
        }

        // Transformer et generer la sortie
        creation_sortie(output,document_but);

   }

    public static void remplissage(Document document_but,HashMap<String, ArrayList<String>> mon_dico,Element fich,String head,String ar,String fr)
    {
//        System.out.println("head= "+head);
        Element be = document_but.createElement("BE");
        fich.appendChild(be);
        be.appendChild(document_but.createTextNode(mon_dico.get(head).get(0).replaceAll("BE","")));
        if (fichesX ==2)
        {
            for (int i = 1; i <4 ; i++)
            {
                String [] listStr = mon_dico.get(head).get(i).split("\t");
                Element balise = document_but.createElement(listStr[1]);
                balise.appendChild(document_but.createTextNode(listStr[1]+" : "+mon_dico.get(head).get(i).replaceAll(listStr[1],"")));
                fich.appendChild(balise);
            }
        }else
            {
                Element ty = document_but.createElement("TY");
                ty.appendChild(document_but.createTextNode("TY : "+mon_dico.get(head).get(1).replaceAll("TY","")));
                fich.appendChild(ty);
            }

        Element au = document_but.createElement("AU");
        au.appendChild(document_but.createTextNode("AU : "+mon_dico.get(head).get(4).replaceAll("\tAU","\t")));
        fich.appendChild(au);


        Element langue = document_but.createElement("Langue");
        langue.setAttribute("id","AR");
        fich.appendChild(langue);

        remplissage_langue( document_but, mon_dico, langue, head,ar);

        langue = document_but.createElement("Langue");
        langue.setAttribute("id","FR");
        fich.appendChild(langue);
        remplissage_langue( document_but, mon_dico, langue, head,fr);
    }
    public static void remplissage_langue(Document document_but,HashMap<String, ArrayList<String>> mon_dico,Element langue,String head,String lan)
    {
        if(fichesX == 1)
        {
            Element d0 = document_but.createElement("DO");
            d0.appendChild(document_but.createTextNode("DO : "+mon_dico.get(head).get(2).replaceAll("DO","")));
            langue.appendChild(d0);
            Element sd = document_but.createElement("SD");
            sd.appendChild(document_but.createTextNode("SD : "+mon_dico.get(head).get(3).replaceAll("SD","")));
            langue.appendChild(sd);
        }
        
        Element ve = document_but.createElement("VE");
        ve.appendChild(document_but.createTextNode("VE : "+mon_dico.get(lan).get(1).replaceAll("VE :","")));
        langue.appendChild(ve);

        Element df = document_but.createElement("DF");
        df.appendChild(document_but.createTextNode("DF : "+mon_dico.get(lan).get(2).replaceAll("DF :","")));
        langue.appendChild(df);

        //System.out.println(mon_dico.get(lan).get(3));

        Element ph = document_but.createElement("PH");
        ph.appendChild(document_but.createTextNode("PH : "+mon_dico.get(lan).get(3).replaceAll("PH :","")));
        langue.appendChild(ph);

        Element nt = document_but.createElement("NT");
        nt.appendChild(document_but.createTextNode("NT : "+mon_dico.get(lan).get(4).replaceAll("NT :","")));
        langue.appendChild(nt);


        //Traitement particulier pour les dernieres lignes(rf)
        Element rf = null;
        String [] liste = null;
        //la cas spécial ou il faut inverser ...
        liste = mon_dico.get(lan).get(5).split("\t");
        String s = liste[1];

        //Construction de la chaine inversée à afficher.
        String inverStr="";
        for(int j=s.length()-1;j>0;j--)
        {
            if(s.charAt(j)==':')
                inverStr=inverStr+s.charAt(j-3)+s.charAt(j-2)+" : ";
        }

        //gerer les cas special et probleme d'indentation
        rf = document_but.createElement("RF");
        if (lan.matches("Arabe[013]"))
            rf.appendChild(document_but.createTextNode("RF | "+inverStr+liste[0]+"\t \t"));
        else if (lan.matches("Arabe2"))
        {
            rf.appendChild(document_but.createTextNode("RF | " + inverStr + liste[0] + "\t   \t"));
        }
            else if(lan.matches("Francais3"))
                    rf.appendChild(document_but.createTextNode("RF | "+inverStr+liste[0]+"\t  \t"));
            else
                rf.appendChild(document_but.createTextNode("RF | "+inverStr+liste[0]+"\t\t"));

        langue.appendChild(rf);
        //le reste des rf
        for(int i=6;i<mon_dico.get(lan).size();i++ )
        {
            liste = mon_dico.get(lan).get(i).split("\t");
//            System.out.println("la boucle i= "+i);
            rf = document_but.createElement("RF");
            rf.appendChild(document_but.createTextNode("RF | "+liste[liste.length-1]+" "+liste[0]+"\t\t"));
            langue.appendChild(rf);
        }


    }

}
