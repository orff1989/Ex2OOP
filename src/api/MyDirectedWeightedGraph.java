package api;

import java.util.*;

public class MyDirectedWeightedGraph implements DirectedWeightedGraph {

    private HashMap<Vector, EdgeData> Edges;
    private HashMap<Integer, NodeData> Nodes;

    private Iterator itOfNodes;
    private int itOfNodesCreated;
    private Iterator itOfEdges;
    private int itOfEdgesCreated;
    private int changes;
    private Collection<EdgeData> srcConllection;
    private boolean isConnected;

    public MyDirectedWeightedGraph(HashMap Nodes, HashMap Edges){
        this.Nodes = Nodes;
        this.Edges = Edges;
        changes=0;
        createIteratorOfNodes();
        createIteratorOfEdges();
        isConnected=false;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    private void fixLocation(){
        for (Object o : Nodes.values()) {
            Node nd = (Node) o;
            nd.posToLocation();
        }
    }

    public double getMaxX(){
        fixLocation();
        double maxX = Integer.MIN_VALUE;
        for (Object o : Nodes.values()){
            NodeData nd = (Node) o;
            if (nd.getLocation().x()>maxX) maxX=nd.getLocation().x();
        }
        return maxX;
    }

    public double getMinX(){
        fixLocation();
        double minX = Integer.MAX_VALUE;
        for (Object o : Nodes.values()){
            NodeData nd = (Node) o;
            if (nd.getLocation().x()<minX) minX=nd.getLocation().x();
        }
        return minX;
    }

    public double getMaxY(){
        fixLocation();
        double maxY = Integer.MIN_VALUE;
        for (Object o : Nodes.values()){
            NodeData nd = (Node) o;
            if (nd.getLocation().y()>maxY) maxY=nd.getLocation().y();
        }
        return maxY;
    }
    public double getMinY(){
        fixLocation();
        double minY = Integer.MAX_VALUE;
        for (Object o : Nodes.values()){
            NodeData nd = (Node) o;
            if (nd.getLocation().y()<minY) minY=nd.getLocation().y();
        }
        return minY;
    }

    //this method returns the reversed graph without considering the weight of edges
    public MyDirectedWeightedGraph reversedGraph(){
        HashMap<Vector,EdgeData> newEdges = new HashMap<>();
        for (NodeData  a : Nodes.values()) {
            for (NodeData  b : Nodes.values()) {
                if (a!=b && getEdge(a.getKey(),b.getKey())==null) {
                    EdgeData ed = new Edge(a.getKey(),0 ,b.getKey());
                    Vector v = new Vector(2);
                    v.add(ed.getSrc());
                    v.add(ed.getDest());
                    newEdges.put(v,ed);
                }
            }
        }
        MyDirectedWeightedGraph rg = new MyDirectedWeightedGraph(Nodes, newEdges);
        return rg;
    }

    public HashMap<Integer, NodeData> getNodes() {
        return Nodes;
    }

    public HashMap<Vector, EdgeData> getEdges() {
        return Edges;
    }

    public DirectedWeightedGraph copy(){
        return new MyDirectedWeightedGraph(Nodes, Edges);
    }

    //this method creates the iterator
    private void createIteratorOfNodes(){
        Iterator it = Nodes.values().iterator();
        itOfNodesCreated=changes;
        itOfNodes=it;
    }

    //this method creates the iterator
    private void createIteratorOfEdges(){
        Iterator it = Edges.values().iterator();
        itOfEdgesCreated=changes;
        itOfEdges=it;
    }

    @Override
    public NodeData getNode(int key) {
        return Nodes.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        Vector<Integer> v = new Vector<Integer>(2);
        v.add(src);
        v.add(dest);
         return Edges.get(v);
    }

    @Override
    public void addNode(NodeData n) {
    Nodes.put(n.getKey(), n);
    changes++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        if (Nodes.containsKey(src) && Nodes.containsKey(dest)) {
            Vector<Integer> v = new Vector<Integer>(2);
            v.add(src);
            v.add(dest);

            EdgeData e = new Edge(src,w,dest);

            Node srcNode =(Node) Nodes.get(src);
            Node destNode =(Node) Nodes.get(dest);

            changes++;
            Edges.put(v,e);
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        try {
            if (itOfNodesCreated == changes) {
                return itOfNodes;
            } else throw new RuntimeException();
        }
        catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        try {
            if (itOfEdgesCreated == changes) {
                return itOfEdges;
            } else throw new RuntimeException();
        }
        catch (RuntimeException e){
            return null;
        }
    }
    //this function is adding the neighbors of every node in the graph
    private void fixNodesNeighbors(){
        for (Object o1 : Nodes.values()){
            Node n = (Node) o1;
            n.neighbors = new LinkedList<EdgeData>();
        }

        for (Object o : Edges.values()){
            EdgeData ed = (Edge) o;
            Node srcNode = (Node) getNode(ed.getSrc());
            srcNode.addNeighbor(ed);
        }
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        try {
            if (itOfEdgesCreated == changes) {
                Node n = (Node) getNode(node_id);
                return n.getNeighbors().iterator();
            }
            else throw new RuntimeException();
        }
        catch (RuntimeException e){
            return null;
        }

    }

    @Override
    public NodeData removeNode(int key) {
        NodeData nData = Nodes.get(key);
        graphHelper gh = new graphHelper(this);

        for (Edge ed : gh.Edges){
            if (ed.getSrc()==key || ed.getDest()==key)
                removeEdge(ed.getSrc(),ed.getDest());
        }
        Nodes.remove(key);
        changes++;
        return nData;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        Vector vec = new Vector(2);
        vec.add(src);
        vec.add(dest);
        EdgeData ed = Edges.get(vec);

        Edges.remove(vec);
        changes++;
        return ed;
    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        return Edges.size();
    }

    @Override
    public int getMC() {
        return this.changes;
    }

    public void setMC(int changes) {
        this.changes = changes;
    }
}
