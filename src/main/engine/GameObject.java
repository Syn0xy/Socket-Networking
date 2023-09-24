package engine;

import geometric.Transform;
import database.network.NetworkIdentity;

public class GameObject {
    private final static String NAME = "GameObject";
    private static int count = 0;
    
    private String name;
    private Transform transform;
    private NetworkIdentity identity;

    public GameObject(String name, Transform transform, NetworkIdentity identity){
        this.name = name;
        this.transform = transform;
        this.identity = identity;
    }

    public GameObject(String name, Transform transform){
        this(name, transform, new NetworkIdentity());
    }

    public GameObject(Transform transform){
        this(getNextName(), transform);
    }
    public GameObject(String name){
        this(name, new Transform());
    }
    
    public GameObject(){
        this(getNextName());
    }

    public String getName(){ return name; }
    public Transform getTransform(){ return transform; }
    public NetworkIdentity getIdentity(){ return identity; }

    public static String getNextName(){
        return NAME + count++;
    }
}