import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

import database.account.Account;
import database.account.AccountSystem;

public class Main {
    private static String EMAIL_REQUEST = "Email : ";
    private static String LOGIN_REQUEST = "Login : ";
    private static String PASSWORD_REQUEST = "Password : ";
    
    public static void main(String[] args){
        AccountSystem.getInstance();
        
        System.out.println("1. Login");
        System.out.println("2. Register");
        String choice = readStringNotNull("Choix : ");
        if(choice.equals("1")) startLogin();
        if(choice.equals("2")) startRegister();
        
        AccountSystem.getInstance().closeConnection();
    }

    public static void startLogin(){
        String login = readStringNotNull(LOGIN_REQUEST);
        String password = readStringNotNull(PASSWORD_REQUEST);
        Account user = null;
        try {
            user = AccountSystem.login(login, password);
            System.out.println("User email : " + user.getEmail());
        } catch (SQLException e) {
            System.out.println("Le nom d'utilisateur ou le mot de passe est incorrect");
        }
    }

    public static void startRegister(){
        Account user = null;
        String email = readStringNotNull(EMAIL_REQUEST);
        String login = readStringNotNull(LOGIN_REQUEST);
        String password = readStringNotNull(PASSWORD_REQUEST);
        try {
            user = AccountSystem.register(email, login, password);
        } catch (SQLException e) { System.out.println(e.getMessage()); }

        if(user == null){
            System.out.println("L'utilisateur n'a pas reussi a se connecter !");
        }else{
            System.out.println("L'utilisateur a reussi a se connecter !");
        }
    }
    
    public static String readStringNotNull(){
        return readStringNotNull("");
    }

    public static String readStringNotNull(String request){
        String s = null;
        do{
            try{
                System.out.print(request);
                s = readString();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }while(s == null);
        return s;
    }
    
    public static String readString() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }
}
