package REST;

import org.springframework.web.bind.annotation.*;
import userManagment.Manager;
import userManagment.User;
import userManagment.UserManagerProvider;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MessageController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(value="/api/user", method = RequestMethod.GET)
    public List<User> listOfUsers(){
        Manager userManager = UserManagerProvider.getManager();
        return userManager.list();
    }

    @RequestMapping(value="/api/user/{username}", method = RequestMethod.GET)
    public User singleUser(@PathVariable("username") String username){
        Manager userManager = UserManagerProvider.getManager();
        System.out.println(username);
        return userManager.findUser(username);
    }

    @RequestMapping(value="/api/user", method = RequestMethod.POST, consumes = "application/json")
    public User postUser(@RequestBody User user){
        Manager userManager = UserManagerProvider.getManager();
        try {
            userManager.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userManager.findUser(user.getName());
    }

    @RequestMapping(value="/api/user/{username}", method = RequestMethod.PUT, consumes = "application/json")
    public User putUser(@PathVariable("username") String username, @RequestBody User user){
        Manager userManager = UserManagerProvider.getManager();
        try {
            userManager.modifyUser(username, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @RequestMapping(value="/api/user/{username}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("username") String username){
        Manager userManager = UserManagerProvider.getManager();
        try {
            userManager.deleteUser(userManager.findUser(username));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ("Usunieto uzytkownika: " + username);
    }

}