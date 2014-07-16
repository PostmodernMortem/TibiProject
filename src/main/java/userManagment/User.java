/**
 * Created by Wojtek on 2014-07-14.
 */
package userManagment;

public class User {
    private Integer age;
    private String name;

    /*Konstruktory*/
    User(){

    };
    User(String name, Integer age){
        this.age = age;
        this.name = name;
    };
    /**Ten konstruktor powinien być użyty tylko w sytuacji tworzenia nowej instancji z danych zaszyfrowanych.*/
    User(String serialized){
        name = serialized.substring(0, serialized.lastIndexOf(";"));
        age = Integer.parseInt(serialized.substring(1+serialized.lastIndexOf(";")));

    };
    /*odbiór i modyfikacja pól*/
    void setAge(Integer age) {
        this.age = age;
    };
    void setName(String name) {
        this.name = name;
    };
    public Integer getAge() {
        return age;
    };
    public String getName() {
        return name;
    };
    /*Serializacja*/
    public String serialize(){
        return name + ";" + age;
    };
}
