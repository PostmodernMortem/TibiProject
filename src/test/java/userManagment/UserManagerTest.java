package userManagment;

import org.junit.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class UserManagerTest {

    @DataProvider(name="dane")
    public Object[][] createData1() {
        return new Object[][] {
                 { "Cedric", new Integer(36) },
                 { "Anne", new Integer(37)},
        };
    }

    @DataProvider(name="dane2")
    public Object[][] createData2() {
        return new Object[][] {
               { "Cedric", new Integer(36), "Wojciech", new Integer(24) },
               { "Anne", new Integer(37), "Aleksandra", new Integer(30)},
        };
    }

    @DataProvider(name="dane3")
    public Object[][] createData4(){
        return new Object[][]{
                {"Wojciech", new Integer(24)},
                {"Aleksandra", new Integer(30)},
        };
    }

    @DataProvider(name="base")
    public Object[][] createData3(){
        return new Object[][]{
                {"txt"},
                {"db"},
//                {"hibernate"},
        };
    }

    @Test(dataProvider = "dane")
    public void testAddUser(String name, Integer age) throws Exception {
        User user = new User(name, age);
        assertNull(user.getId());
        assertEquals(user.getAge(), (Integer) age);
        assertEquals(user.getName(), name);
        System.out.println(user.getName());
        Manager userManager = UserManagerProvider.getManager();
        if (userManager.findUser(name)==null) {
            userManager.addUser(user);
        }
            assertEquals(user.getName(), userManager.findUser(user.getName()).getName());
            assertEquals(user.getAge(),userManager.findUser(user.getName()).getAge());

    }

    @Test(dataProvider = "dane", expectedExceptions = Exception.class)
    public void testAddUserWithException(String name, Integer age) throws Exception {
        User user = new User(name, age);
        assertNull(user.getId());
        assertEquals(user.getAge(), (Integer) age);
        assertEquals(user.getName(), name);
        Manager userManager = UserManagerProvider.getManager();
        if (userManager.findUser(name)==null) {
            userManager.addUser(user);
            assertEquals(user.getName(), userManager.findUser(user.getName()).getName());
            assertEquals(user.getAge(),userManager.findUser(user.getName()).getAge());
        }
        userManager.addUser(user);
    }


    @Test(dataProvider = "dane")
    public void testFindUser(String name, Integer age){
        Manager userManager = UserManagerProvider.getManager();
        if (userManager.findUser(name)==null){
            try {
                userManager.addUser(new User(name,age));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        User user = userManager.findUser(name);
        assertEquals(user.getName(), name);
        assertEquals(user.getAge(), age);
    }

    @Test(dataProvider = "dane2")
    public void testModifyUser(String name, Integer age, String newName, Integer newAge) throws Exception {
        User user = new User(name, age);
        Manager userManager = UserManagerProvider.getManager();
        if (userManager.findUser(user.getName())==null){
            userManager.addUser(user);
        }
            assertEquals(user.getName(), userManager.findUser(user.getName()).getName());
            User toModify = new User(newName, newAge);
            userManager.modifyUser(name, toModify);
            assertNull(userManager.findUser(user.getName()));
            assertNotNull(userManager.findUser(toModify.getName()));
            assertEquals(userManager.findUser(toModify.getName()).getName(), newName);
            assertEquals(userManager.findUser(toModify.getName()).getAge(), (Integer) newAge);
        }


//    @Test(dataProvider = "dane3")
    public void testDeleteUser(String name, Integer age) throws Exception {
        User user = new User(name, age);
        Manager userManager = UserManagerProvider.getManager();
        if(userManager.findUser(name)==null) {
            userManager.addUser(user);
        }
        assertEquals(user, userManager.findUser(user.getName()));
        userManager.deleteUser(user);
        assertNull(userManager.findUser(user.getName()));
    }




//    @Test(dataProvider = "base")
    public void testList(String db) throws Exception {
        Manager userManager = UserManagerProvider.getManager(db);
        List<User> list = userManager.list();
        if(list.isEmpty()){
            userManager.addUser(new User("Wojciech", (Integer)24));
        }
        list = userManager.list();
        Integer countBefore = list.size();
        userManager.deleteUser(list.get(0));
        list = userManager.list();
        Integer countAfter = list.size();
        assertEquals(countBefore, (Integer)(countAfter+1));
    }

    @BeforeClass
    public void SetUp(){

    }
}