import userManagment.*;

/**
 * Created by Wojtek on 2014-07-14.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    enum MyEnum {
        ADD("add"),
        UPDATE("update");

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

        MyEnum.get("add");

        /*Tutaj można true zamienić a coś innego*/
        //while(true) {
            System.out.println(args.length);
            Integer numberOfArguments = new Integer(args.length);
            if (numberOfArguments == 0) {
                consoleInterface();
            }else {
                UserManager myManager = UserManager.getEntity();

                if (args[0].equals("add") &&numberOfArguments == 3) {
                    myManager.addUser(args[1], Integer.parseInt(args[2]));
                }else if (args[0].equals("delete") &&numberOfArguments == 2){
                    myManager.deleteUser(myManager.findUser(args[1]));
                }else if (args[0].equals("find") &&numberOfArguments == 2) {
                    myManager.findUser(args[1]);
                }else if (args[0].equals("list") &&numberOfArguments == 1) {
                    myManager.list();
                }else {
                    System.out.println("Nieprawidłowa liczba argumentów.");
                }


            }


       // }
    }

    public static void consoleInterface(){
        /*Komunikacja z użytkownikiem*/
        System.out.println("Co chcesz zrobić?");
        System.out.println("Wybierz: add, find, update, delete, list");

            /*Wczytujemy dane*/
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            String s = br.readLine();
            UserManager mojManager = UserManager.getEntity();
            if (s.equals("add")) {
                System.out.println("Podaj nazwe uzytkowika: ");
                String name = br.readLine();
                System.out.print("Podaj ID (Integer): ");
                try {
                    Integer id = Integer.parseInt(br.readLine());
                    mojManager.addUser(name, id);
                } catch (NumberFormatException nfe) {
                    System.err.println("Invalid Format!");
                }
            } else if (s.equals("update")) {
                System.out.println("Podaj nazwe uzytkowika: ");
                String name = br.readLine();
                System.out.println("Podaj nową nazwe uzytkowika: ");
                String newName = br.readLine();
                System.out.print("Podaj nowe ID (Integer): ");
                try {
                    Integer id = Integer.parseInt(br.readLine());
                    mojManager.modifyUser(mojManager.findUser(name), newName, id);
                } catch (NumberFormatException nfe) {
                    System.err.println("Invalid Format!");
                }
            } else if (s.equals("delete")) {
                System.out.println("Podaj nazwe uzytkowika: ");
                String name = br.readLine();
                mojManager.deleteUser(mojManager.findUser(name));
            } else if (s.equals("find")) {
                System.out.println("Podaj nazwe uzytkowika: ");
                String name = br.readLine();
                User found = mojManager.findUser(name);
                if(found==null) {
                    System.out.println("Nie znaleziono użytkownika");

                }else {

                    System.out.println("Znaleziono użytkownika: " + found.getName() + ", jego identyfikator to " + found.getId());
                }
            }else if(s.equals("list")) {
                mojManager.list();
            }else
            {
                System.out.println("Nieprawidłowa komenda");
            }
        } catch (IOException e) {
            System.err.println("Wystąpił wyjątek");
        } catch (NullPointerException e){
            System.err.println("Łapie null pointera");
        }


    }
}
