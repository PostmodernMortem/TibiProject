package userManagment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;

/**
 * Created by Wojtek on 2014-07-16.
 */
public class UserManagerProvider {

    private static Manager entity;
    private UserManagerProvider(){};

    public static Manager getManager(){
        Properties projectProperties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            projectProperties.load(input);
            String dbOption = projectProperties.getProperty("typeOfManager");
            if(dbOption.equals("txt")){
                entity = UserManager.getEntity();
            }else if(dbOption.equals("db")){
                entity = DatabaseUserManager.getEntity();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return entity;
    }
}
