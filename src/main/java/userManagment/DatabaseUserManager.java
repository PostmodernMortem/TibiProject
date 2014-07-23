package userManagment;

import com.google.gson.Gson;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

    public void addUser(User toAdd) throws Exception {
        if(findUser(toAdd.getName())!=null){
            System.out.println("Podany użytkownik istnieje");
            throw new Exception("Uzytkownik istnieje");
        }
        try {
            DBCollection table = openDbConnection();
            /**** Insert ****/
            BasicDBObject document = new BasicDBObject();
            document.put("name", toAdd.getName());
            document.put("age", toAdd.getAge());
            table.insert(document);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return;
    }

    public void modifyUser(String id, User toBeModified) {
        try {
            DBCollection table = openDbConnection();
            /**** Update ****/
            BasicDBObject query = new BasicDBObject();
            query.put("name", id);

            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("name", toBeModified.getName());
            newDocument.put("age", toBeModified.getAge());

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
                    Gson gson = new Gson();
                    toReturn = gson.fromJson(obj.toString(),User.class);
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public List<User> list() {
        ArrayList<User> userList = new ArrayList<User>();
        DBCollection table = null;
        try {
            table = openDbConnection();
            BasicDBObject searchQuery = new BasicDBObject();
            //searchQuery.put("name");

            DBCursor cursor = table.find(searchQuery);
            Gson gson = new Gson();
            while (cursor.hasNext()) {
                System.out.println("ZACZYNAM");
                System.out.println(cursor.toString());
                userList.add(gson.fromJson(cursor.next().toString(),User.class));
//                System.out.println(cursor.next());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (userList == null) {
            userList = new ArrayList<User>();
        }
        return userList;
    }

    private DatabaseUserManager(){};

    private DBCollection openDbConnection() throws UnknownHostException {

        /**** Connect to MongoDB ****/
        MongoClient mongo = new MongoClient("localhost", 27017);
        /**** Get database ****/
        DB db = mongo.getDB("testdb");

        /**** Get collection / table from 'testdb' ****/
        return db.getCollection("User");
    }
}
