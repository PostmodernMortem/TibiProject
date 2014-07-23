/**
 * Created by Wojtek on 2014-07-14.
 */
package userManagment;


import org.bson.types.ObjectId;

public class User{

//    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private ObjectId _id;


//    @Field
    private Integer age;
//    @Field
    private String name;

    /*Konstruktory*/
    User() {

    }

    ;
    public User(ObjectId id, String name, Integer age){
        this._id = id;
        this.name = name;
        this.age = age;
    }
    public User(String name, Integer age) {
        this.age = age;
        this.name = name;
    }

    ;

    /**
     * Ten konstruktor powinien być użyty tylko w sytuacji tworzenia nowej instancji z danych zaszyfrowanych.
     */
    User(String serialized) {
        name = serialized.substring(0, serialized.lastIndexOf(";"));
        age = Integer.parseInt(serialized.substring(1 + serialized.lastIndexOf(";")));

    }

    ;

    /*odbiór i modyfikacja pól*/
    public void setAge(Integer age) {
        this.age = age;
    }

    ;

    public void setName(String name) {
        this.name = name;
    }

    ;

    public void setId(ObjectId id1) {
        this._id = id1;
    }

    ;

    public ObjectId getId() {

        return _id;
    }

    ;

    public Integer getAge() {
        return age;
    }

    ;

    public String getName() {
        return name;
    }

    ;

    /*Serializacja*/
    public String serialize() {
        return name + ";" + age;
    };



    @Override
    public String toString() {
        return "Uzytkownik [id=" + _id + ", imie=" + name + ", wiek=" + age + "]";
    };

//    @Override
//    public boolean equals(Object obj){
//        if (!(obj instanceof User))
//            return false;
//        if (obj == this)
//            return true;
//
//        User rhs = (User) obj;
//        return new EqualsBuilder().
//                // if deriving: appendSuper(super.equals(obj)).
//                append(name, rhs.name).
//                append(age, rhs.age).
//                isEquals();
//    };

}
