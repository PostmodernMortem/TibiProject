package userManagment;

/**
 * Created by Wojtek on 2014-07-15.
 */
public interface Manager {
    public void addUser(User toBeAdded);
    public void modifyUser(String id, User toModify);
    public void deleteUser(User toBeDeleted);
    public User findUser(String username);
    public void list();
}
