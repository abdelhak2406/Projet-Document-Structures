package package1;

import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception{

        if (args.length < 1) {
            System.out.println("voici notre argment"+args[0]);
            System.out.println("Attention vous avez oublié de spécifier le nom du répertoire à traiter !");
            System.exit(0);
        }
        recursiveBrowse(args[0]);
    }
    private static void recursiveBrowse(String dir) throws Exception {
        Path path = Paths.get(dir);

        try{
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for (Path entry : stream){
                String filename = entry.toString();
                if(Files.isDirectory(entry)){
                    recursiveBrowse(filename);
                }else if(Files.isRegularFile(entry)) {
                    if(filename.contains("fiches.txt")) {
                        System.out.println("nous somme entré bitches!");
                        Transform_fiche_1.transform_fiche1(filename, "mes sorties/fiches1.xml");
                        Transform_fiche_2.trnasform_fiche2(filename,"mes sorties/fiches2.xml");
                    }
                    if(filename.contains("boitedialog.fxml")){
                        Transform_BoiteDialogue.transform_boiteDialogue(filename, "mes sorties/javafx.xml");
                    }
                    if(filename.contains("poeme.txt")) {
                        Transform_poeme.transform_poeme(filename, "mes sorties/neruda.xml");
                    }
                    if(filename.contains("M457.xml")) {
                        Transform_M457.transform_m457(filename, "mes sorties/sortie2.xml");
                    }
                    if(filename.contains("M674.xml")){
                        Transform_M674.transform_m674(filename, "mes sorties/sortie1.xml");
                    }
                    if(filename.contains("renault.html")) {
                        Transform_Renault.transform_renault(filename,"mes sorties/renault.xml");
                    }
                }
            }
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
