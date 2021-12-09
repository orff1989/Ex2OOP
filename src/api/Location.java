package api;

public class Location implements GeoLocation {
private double x,y,z;

public Location(double x, double y, double z){
    this.x=x;
    this.y=y;
    this.z=z;
}

    @Override
    public String toString() {
        return x+","+y+","+z;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double xValues=(this.x-g.x())*(this.x-g.x());
        double yValues=(this.y-g.x())*(this.y-g.x());
        double zValues=(this.z-g.x())*(this.z-g.x());
            return Math.sqrt(xValues+yValues+zValues);
    }
}
