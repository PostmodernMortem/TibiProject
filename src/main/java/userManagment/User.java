/**
 * Created by Wojtek on 2014-07-14.
 */
package userManagment;

import java.io.*;

public class User {
    private Integer id;
    private String name;

    /*Konstruktory*/
    User(){

    };
    User(String name, Integer id){
        this.id = id;
        this.name = name;
    };
    /**Ten konstruktor powinien być użyty tylko w sytuacji tworzenia nowej instancji z danych zaszyfrowanych.*/
    User(String serialized){
        name = serialized.substring(0, serialized.lastIndexOf(";"));
        id = Integer.parseInt(serialized.substring(1+serialized.lastIndexOf(";")));

    };
    /*odbiór i modyfikacja pól*/
    void setId(Integer id) {
        this.id = id;
    };
    void setName(String name) {
        this.name = name;
    };
    public Integer getId() {
        return id;
    };
    public String getName() {
        return name;
    };
    /*Serializacja*/
    public String serialize(){
        return name + ";" + id;
    };
}
