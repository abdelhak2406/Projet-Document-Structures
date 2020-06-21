package package1;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception{

        if (args.length < 1) {
            System.out.println("Attention vous avez oublié de spécifier le nom du répertoire à traiter !");
            System.exit(0);
        }


        recursiveBrowse(new File(args[0]));
    }

    static void recursiveBrowse(File repertoire) throws Exception {

        String liste[] = repertoire.list();

        if (liste != null) {
            for (int i = 0; i < liste.length; i++) {

                String s = repertoire+"/"+liste[i];

                File fils = new File(s);
                if(fils.isDirectory())
                {
                    recursiveBrowse(fils);
                }
                else
                    traitementFichier(s);

            }
        } else {
            System.err.println("Nom de repertoire invalide");
        }
    }

   static  void traitementFichier(String s) throws Exception {

        if(s.contains("fiches.txt"))
        {
           Transform_fiches.transform_fiches(s, "fiches1.xml","fiches2.xml");
        }
        if(s.contains("boitedialog.fxml"))
        {
            Transform_BoiteDialogue.transform_boiteDialogue(s, "javafx.xml");
        }
        if(s.contains("poeme.txt"))
        {
            Transform_poeme.transform_poeme(s, "neruda.xml");
        }
        if(s.contains("M457.xml"))
        {
            Transform_M674_M457.transform_mX(s, "sortie2.xml");
        }
        if(s.contains("M674.xml"))
        {
            Transform_M674_M457.transform_mX(s, "sortie1.xml");
        }
        if(s.contains("renault.html"))
        {
            Transform_Renault.transform_renault(s,"renault.xml");
        }
    }

}
