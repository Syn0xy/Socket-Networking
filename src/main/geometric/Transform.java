package geometric;

public class Transform {
    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale;

    public Transform(Vector3 position, Vector3 rotation, Vector3 scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform(Vector3 position, Vector3 rotation){
        this(position, rotation, new Vector3(1, 1, 1));
    }

    public Transform(Vector3 position){
        this(position, new Vector3());
    }

    public Transform(){
        this(new Vector3());
    }

    public Vector3 getPosition(){ return position; }
    public Vector3 getRotation(){ return rotation; }
    public Vector3 getScale(){ return scale; }
}