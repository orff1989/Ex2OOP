package api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class graphHelper {
    LinkedList<Node> Nodes;
     LinkedList<Edge> Edges;

    public graphHelper(LinkedList nodes, LinkedList edges){
        for (Object n: nodes) {
            Node nd =(Node) n;
            Nodes.add(nd);
        }
        for (Object e: edges) {
            Edge ed = (Edge) e;
            Edges.add(ed);
        }
    }

    public graphHelper(MyDirectedWeightedGraph gg){
        LinkedList<Node> nodes = new LinkedList<>();
        LinkedList<Edge> edges = new LinkedList<>();
        for (NodeData n: gg.getNodes().values()) {
            nodes.add((Node) n);
        }
        for (EdgeData e: gg.getEdges().values()) {
            edges.add((Edge) e);
        }
        this.Edges=edges;
        this.Nodes=nodes;
    }

    public MyDirectedWeightedGraph LinkedListToGraph() {
        HashMap<Vector, EdgeData> edg = new HashMap<>();
        HashMap<Integer, NodeData> nds = new HashMap<>();
        for (NodeData nd: this.Nodes) {
            nds.put(nd.getKey(), nd);
        }
        for (EdgeData ed: this.Edges) {
            Vector v = new Vector(2);
            v.add(ed.getSrc());
            v.add(ed.getDest());
            edg.put(v,ed);
        }
        return new MyDirectedWeightedGraph(nds,edg);
    }

    public LinkedList<Node> getNodes() {
        return Nodes;
    }

    public LinkedList<Edge> getEdges() {
        return Edges;
    }

}
