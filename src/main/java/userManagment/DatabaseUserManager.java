package userManagment;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by Wojtek on 2014-07-15.
 */
public class DatabaseUserManager implements Manager{

    private static DatabaseUserManager entity=null;

    static DatabaseUserManager getEntity(){
        if(entity==null){
            entity = new DatabaseUserManager();
        }
        return entity;
    }

    public User addUser(String username, Integer age) {
        if(findUser(username)!=null){
            System.out.println("Podany użytkownik istnieje");
            return null;
        }
        try {
            DBCollection table = openDbConnection();
            /**** Insert ****/
            BasicDBObject document = new BasicDBObject();
            document.put("name", username);
            document.put("age", age);
            table.insert(document);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return new User(username,age);
    }

    public void modifyUser(User toBeModified, String newUsername, Integer age) {
        try {
            DBCollection table = openDbConnection();
            /**** Update ****/
            BasicDBObject query = new BasicDBObject();
            query.put("name", toBeModified.getName());

            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("name", newUsername);
            newDocument.put("age", age);

            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", newDocument);

            table.update(query, updateObj);
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    public void deleteUser(User toBeDeleted) {
        if(toBeDeleted == null){
            System.out.println("Użytkownik nie istnieje, nie można go usunąć.");
            return;
        }
        DBCollection table = null;
        try {
            table = openDbConnection();
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("name", toBeDeleted.getName());

            table.remove(searchQuery);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public User findUser(String username) {
        User toReturn = null;
        try {
            DBCollection table = openDbConnection();
            /**** Find and display ****/
            DBCursor cursor = table.find();
            while (cursor.hasNext()) {
                BasicDBObject obj = (BasicDBObject) cursor.next();
                //System.out.println(obj.getString("age"));
                if(obj.getString("name").equals(username)) {
                    toReturn = new User(username, Integer.parseInt(obj.getString("age")));
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public void list() {
        DBCollection table = null;
        try {
            table = openDbConnection();
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("name");

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
