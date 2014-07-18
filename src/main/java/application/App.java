package application;

import userManagment.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class App {

    enum MyEnum {
        ADD("add"),
        UPDATE("update"),
        LIST("list"),
        DELETE("delete"),
        FIND("find");

        private String name;

        MyEnum(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static MyEnum get(String name) {
            for (MyEnum myEnum : MyEnum.values()) {
                if(myEnum.getName().equals(name)) {
                    return myEnum;
                }
            }
            return null;
        }
    };

    public static void main(String args[]){
        //testMethod();

        /*Tutaj można true zamienić a coś innego*/
        //while(true) {
            //System.out.println(args.length);

            Integer numberOfArguments = new Integer(args.length);
            if (numberOfArguments == 0) {
                consoleInterface();
            }else {
                Manager myManager = UserManagerProvider.getManager();
                if (args[0].equals("add") &&numberOfArguments == 3) {
                    myManager.addUser(new User(args[1], Integer.parseInt(args[2])));
                }else if (args[0].equals("delete") &&numberOfArguments == 2){
                    myManager.deleteUser(myManager.findUser(args[1]));
                }else if (args[0].equals("find") &&numberOfArguments == 2) {
                    myManager.findUser(args[1]);
                }else if (args[0].equals("list") &&numberOfArguments == 1) {
                    myManager.list();
                }else if (args[0].equals("update") &&numberOfArguments == 4){
                    myManager.modifyUser(args[1], new User(args[2], Integer.parseInt(args[3])));
                }else{
                        System.out.println("Nieprawidłowa liczba argumentów.");
                }
            }

        //}
    }





    public static void consoleInterface(){
        System.out.println("Co chcesz zrobić?");
        System.out.println("Wybierz: add, find, update, delete, list");
            /*Wczytujemy dane*/
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String s = br.readLine();

                Manager myManager = UserManagerProvider.getManager();
                if (s.equals("add")) {
                    System.out.println("Podaj nazwe uzytkowika: ");
                    String name = br.readLine();
                    System.out.print("Podaj wiek (Integer): ");
                    try {
                        Integer id = Integer.parseInt(br.readLine());
                        myManager.addUser(new User(name, id));
                    } catch (NumberFormatException nfe) {
                        System.err.println("Invalid Format!");
                    }
                } else if (s.equals("update")) {
                    System.out.println("Podaj nazwe uzytkowika: ");
                    String name = br.readLine();
                    System.out.println("Podaj nową nazwe uzytkowika: ");
                    String newName = br.readLine();
                    System.out.print("Podaj nowy wiek (Integer): ");
                    try {
                        Integer id = Integer.parseInt(br.readLine());
                        myManager.modifyUser(name, new User(newName, id));
                    } catch (NumberFormatException nfe) {
                        System.err.println("Invalid Format!");
                    }
                } else if (s.equals("delete")) {
                    System.out.println("Podaj nazwe uzytkowika: ");
                    String name = br.readLine();
                    myManager.deleteUser(myManager.findUser(name));
                } else if (s.equals("find")) {
                    System.out.println("Podaj nazwe uzytkowika: ");
                    String name = br.readLine();
                    User found = myManager.findUser(name);
                    if (found == null) {
                        System.out.println("Nie znaleziono użytkownika");

                    } else {

                        System.out.println("Znaleziono użytkownika: " + found.getName() + ", jego wiek to " + found.getAge());
                    }
                } else if (s.equals("list")) {
                    myManager.list();
                } else {
                    System.out.println("Nieprawidłowa komenda");
                }
        } catch (IOException e) {
            System.err.println("Wystąpił wyjątek");
        } catch (NullPointerException e){
            e.printStackTrace();
            System.err.println("Lapie null pointera");
        }




    }
}
