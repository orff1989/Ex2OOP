package api;

public class Edge implements EdgeData {
    private int src;
    double w;
    private int dest;
    transient int tag;
    transient String info;

    public Edge(int src, double w, int dest) {
        this.src = src;
        this.dest = dest;
        this.w = w;

    }

    @Override
    public String toString() {
        return "src=" + src + ", w=" + w + ", dest=" + dest ;
    }

    @Override
    public int getSrc() {return this.src;}

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.w;
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
