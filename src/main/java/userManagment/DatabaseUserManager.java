package userManagment;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by Wojtek on 2014-07-15.
 */
public class DatabaseUserManager implements Manager{

    private static DatabaseUserManager entity=null;

    public static DatabaseUserManager getEntity(){
        if(entity==null){
            entity = new DatabaseUserManager();
        }
        return entity;
    }

    public User addUser(String username, Integer id) {
        try {
            DBCollection table = openDbConnection();
            /**** Insert ****/
            BasicDBObject document = new BasicDBObject();
            document.put("name", username);
            document.put("age", id);
            table.insert(document);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void modifyUser(User toBeModified, String newUsername, Integer newID) {

    }

    public void deleteUser(User toBeDeleted) {

    }

    public User findUser(String username) {
        return null;
    }

    public void list() {
        DBCollection table = null;
        try {
            table = openDbConnection();
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("name", "mkyong");

            DBCursor cursor = table.find(searchQuery);

            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private DatabaseUserManager(){};

    private DBCollection openDbConnection() throws UnknownHostException {

        /**** Connect to MongoDB ****/
        MongoClient mongo = new MongoClient("localhost", 27017);
        /**** Get database ****/
        DB db = mongo.getDB("testdb");

        /**** Get collection / table from 'testdb' ****/
        return db.getCollection("user");
    }
}
