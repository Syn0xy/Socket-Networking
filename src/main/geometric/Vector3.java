package geometric;

public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(){
        this(0, 0, 0);
    }

    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getZ(){ return z; }

    public String toString(){
        return x + "-" + y + "-" + z;
    }
}
