package userManagment;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserManagerTest {

    @Test
    public void testAddUser() throws Exception {
        User user = new User("Karyna", 32);
        assertNull(user.getId());
        assertEquals(user.getAge(), (Integer) 32);
        assertEquals(user.getName(), "Karyna");
        Manager userManager = UserManager.getEntity();
        userManager.addUser(user);
        assertEquals(user, userManager.findUser(user.getName()));
    }

    @Test
    public void testDeleteUser() {
        User user = new User("Karyna", 32);
        Manager userManager = UserManager.getEntity();
        userManager.addUser(user);
        assertEquals(user, userManager.findUser(user.getName()));
        userManager.deleteUser(user);
        assertNull(userManager.findUser(user.getName()));
    }

    @Test
    public void testModifyUser(){
        User user = new User("Karyna", 32);
        Manager userManager = UserManager.getEntity();
        userManager.addUser(user);
        assertEquals(user, userManager.findUser(user.getName()));
        User toModify = new User ("Katarina", 23);
        userManager.modifyUser("Karyna", toModify);
        assertNull(userManager.findUser(user.getName()));
        assertNotNull(userManager.findUser(toModify.getName()));
        assertEquals(userManager.findUser(toModify.getName()).getName(), "Katarina");
        assertEquals(userManager.findUser(toModify.getName()).getAge(), (Integer)23);
    }
}