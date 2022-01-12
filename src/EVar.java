import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EVar {

    protected String fileName;
    protected final Pattern pattern = Pattern.compile("(.+)=(.+)");
    protected File file;

    public EVar(String fileName){
        this.fileName=fileName;
        file = new File("./"+fileName);
    }

    public EVar(){
        this.fileName=".env";
        file = new File("./.env");
    }

    public void createFile() {
        if (file.exists()) return;
        try {
            if (file.createNewFile()) System.out.println("File was created with name "+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key){
        try {
            createFile();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                Matcher matcher = pattern.matcher(data);
                if (matcher.matches()) {
                    if (matcher.group(1).equals(key)) return matcher.group(2);
                } else {
                    System.err.println("The values in the file are not valid");
                    System.exit(0);
                }
            }
            System.out.println("There are no values with the key "+key+" in "+fileName);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}
