package package1;

import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception{
        if (args.length < 1) {
            System.out.println("Attention vous avez oublié de spécifier le nom du répertoire à traiter !");
            System.exit(0);
        }

        DocumentBuilderFactory.newInstance().newDocumentBuilder();
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
                    if(filename.contains("renault.html")) {
                        renault.renaultx(filename, "renault.xml");
                    }
                    if(filename.contains("boitedialog.fxml")){
                        javafxT.dialog(filename, "javafx.xml");
                    }
                    if(filename.contains("poeme.txt")) {
                        poema.neruda(filename, "neruda.xml");
                    }
                    if(filename.contains("M457.xml")) {
                        sorties.sorties(filename, "M457");
                    }
                    if(filename.contains("M674.xml")){
                        sorties.sorties(filename, "M674");
                    }
                    if(filename.contains("fiches.txt")) {
                        fiches.fiche1(filename, "fiche1.xml");
                        fiches.fiche2(filename, "fiche2.xml");
                    }
                }
            }
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
