package userManagment;


import java.io.*;

/**
 * Created by Wojtek on 2014-07-14.
 * Klasa singletonowa
 */
public class UserManager implements Manager{

    private static UserManager entity;

    /**Konstruktor prywatny*/
    private UserManager(){};
    static UserManager getEntity(){
        if(entity == null)
            entity = new UserManager();

            return entity;
    };

    public void addUser(User toBeAdded){
        User found = findUser(toBeAdded.getName());
        if (found!=null) {
            System.out.println("Użytkownik o podanej nazwie istnieje.");
            return;
        }

        addToBase(toBeAdded);
        System.out.println("Dodano użytkownika: " + toBeAdded.getName() + " o identyfikatorze " + toBeAdded.getAge());
        return;
    };
    public void modifyUser(String id, User toBeModified){
        if (search(id)==null || search(toBeModified.getName())!=null) {
            System.out.println("Użytkownik o podanej nazwie nie istnieje lub nowa nazwa jest zajeta.");
            return;
        }

        deleteFromBase(id);
        System.out.println("Zmodyfikowano użytkownika: " + toBeModified.getName() + " na " + toBeModified.getName() + " i nadano identyfikator " + toBeModified.getAge());
        addToBase(toBeModified);
    };
    public void deleteUser(User toBeDeleted){
        if(search(toBeDeleted.getName()) == null) {
            System.out.println("Nie ma użytkownika o podanej nazwie");
            return;
        }
        deleteFromBase(toBeDeleted.getName());
        System.out.println( "Usunięto użytkownika: " + toBeDeleted.getName());
    };
    public User findUser(String username){
        Integer usersID = search(username);
        if(usersID==null) {
            return null;
        }
        return new User(username, usersID);
    };
    public void list(){
        BufferedReader in = null;
        try {
            File fileDir = new File("baza.txt");

            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));
            String str;

            while ((str = in.readLine()) != null) {
                User toCheck = new User(str);
                System.out.println(toCheck.getName() + ", ID: " + toCheck.getAge());
            }
            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage() + "!");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage() + "!!");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "!!!");
        }finally{
            if (in !=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    };

    private Integer search(String username){
        BufferedReader in=null;
        try {
            File fileDir = new File("baza.txt");

            in = new BufferedReader(
            new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

            String str;

            while ((str = in.readLine()) != null) {
                User toCheck = new User(str);
                if(toCheck.getName().equals(username)){

                    return toCheck.getAge();
                }
            }

        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage() + "!");
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage() + "!!");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage() + "!!!");
        }finally{
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    };
    private Boolean addToBase(User toBeAdded){
        BufferedWriter fbw = null;
        try{
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("baza.txt", true), "UTF-8");
            fbw = new BufferedWriter(writer);
            fbw.write(toBeAdded.serialize());
            fbw.newLine();

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }finally{
            if (fbw!= null) {
                try {
                    fbw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    };
    private Boolean deleteFromBase(String username){
        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            File inFile = new File("baza.txt");
            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return false;
            }
            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
            br = new BufferedReader(new FileReader("baza.txt"));
            pw = new PrintWriter(new FileWriter(tempFile));
            String line = null;
            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                User toBeChecked = new User(line);
                if (!toBeChecked.getName().equals(username)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return false;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }finally {

            if(pw!=null) {
                pw.close();
            }
            if(br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    };

}
