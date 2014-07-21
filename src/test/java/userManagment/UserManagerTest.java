package userManagment;

import org.testng.annotations.Test;

import static org.testng.Assert.*;
//TODO Brak informacji zwrotnej z add user
public class UserManagerTest {

    private String name = "Katarina";
    private String newName = "Karyna";
    private Integer age = 32;
    private Integer newAge = 23;

    @Test
    public void testAddUser() throws Exception {
        User user = new User(name, age);
        assertNull(user.getId());
        assertEquals(user.getAge(), (Integer) age);
        assertEquals(user.getName(), name);
        Manager userManager = UserManager.getEntity();
        userManager.addUser(user);
        assertEquals(user.getName(), userManager.findUser(user.getName()).getName());
        System.out.println("TEST 1");

    }

    @Test
    public void testModifyUser(){
        User user = new User(name, age);
        Manager userManager = UserManager.getEntity();
        userManager.addUser(user);
        assertEquals(user.getName(), userManager.findUser(user.getName()).getName());
        User toModify = new User (newName, newAge);
        userManager.modifyUser(name, toModify);
        assertNull(userManager.findUser(user.getName()));
        assertNotNull(userManager.findUser(toModify.getName()));
        assertEquals(userManager.findUser(toModify.getName()).getName(), newName);
        assertEquals(userManager.findUser(toModify.getName()).getAge(), (Integer)newAge);
        System.out.println("TEST 2");
    }

    @Test
    public void testDeleteUser() {
        User user = new User(newName, newAge);
        Manager userManager = UserManager.getEntity();
        userManager.addUser(user);
        assertEquals(user, userManager.findUser(user.getName()));
        userManager.deleteUser(user);
        assertNull(userManager.findUser(user.getName()));
        System.out.println("TEST 3");
    }
}