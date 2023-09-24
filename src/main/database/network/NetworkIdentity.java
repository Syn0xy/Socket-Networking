package database.network;

public class NetworkIdentity {
    private static int count = 0;
    private final int ID;

    public NetworkIdentity(int id){
        this.ID = id;
    }

    public NetworkIdentity(){
        this(getNextId());
    }

    public int getId(){ return ID; }

    public static int getNextId(){
        return count++;
    }
}