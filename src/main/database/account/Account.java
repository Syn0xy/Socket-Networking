package database.account;

public class Account {
    private String email;
    private String username;

    protected Account(String email, String username){
        this.email = email;
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username;
    }
}