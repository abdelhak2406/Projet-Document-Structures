package package1;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
public class Main {
    public static void main(String[] args) throws Exception{

        if (args.length < 1) {
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
                        Transform_fiches.transform_fiches(filename, "fiches1.xml","fiches2.xml");
                    }
                    if(filename.contains("boitedialog.fxml")){
                        Transform_BoiteDialogue.transform_boiteDialogue(filename, "javafx.xml");
                    }
                    if(filename.contains("poeme.txt")) {
                        Transform_poeme.transform_poeme(filename, "neruda.xml");
                    }
                    if(filename.contains("M457.xml")) {
                        Transform_M674_M457.transform_mX(filename, "sortie2.xml");
                    }
                    if(filename.contains("M674.xml")){
                        Transform_M674_M457.transform_mX(filename, "sortie1.xml");
                    }
                    if(filename.contains("renault.html")) {
                        Transform_Renault.transform_renault(filename,"renault.xml");
                    }
                }
            }
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
