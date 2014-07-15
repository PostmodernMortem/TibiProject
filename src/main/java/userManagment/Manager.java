package userManagment;

/**
 * Created by Wojtek on 2014-07-15.
 */
public interface Manager {
    public User addUser(String username, Integer id);
    public void modifyUser(User toBeModified, String newUsername, Integer newID);
    public void deleteUser(User toBeDeleted);
    public User findUser(String username);
    public void list();
}
