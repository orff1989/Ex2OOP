package api;

import java.util.LinkedList;

public class Node implements NodeData {

    private String pos;
    private transient GeoLocation location;
    private int id;
    private transient int tag;
    private transient double weight;
    private transient String info;
    transient LinkedList<EdgeData> neighbors; //this list is saving all the edges that goes from this node

    public Node(String pos, int id){
        this.pos =new String(pos) ;
        this.id = id;
        String[] loc= pos.split(",");
        double xx =  Double.parseDouble(loc[0]);
        double yy =  Double.parseDouble(loc[1]);
        double zz =  Double.parseDouble(loc[2]);
        this.location= new Location(xx,yy,zz);
        this.neighbors = new LinkedList<>();
    }

    public void posToLocation(){
            String[] loc = this.pos.split(",");
            double xx = Double.parseDouble(loc[0]);
            double yy = Double.parseDouble(loc[1]);
            double zz = Double.parseDouble(loc[2]);
            this.location = new Location(xx, yy, zz);
    }


    public LinkedList<EdgeData> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(EdgeData ed){
        neighbors.add(ed);
    }

    public void removeNeighbor(EdgeData ed){
        neighbors.remove(ed);
    }
    @Override
    public String toString() {
        return "pos=" + pos + ", id=" + id;
    }

    public String getPos() {
        return pos;
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(GeoLocation p) {
    this.location = new Location(p.x(),p.y(), p.z());
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
    this.weight=w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
    this.info= new String(s);
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
    this.tag=t;
    }
}
