package api;

import com.google.gson.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MyDirectedWeightedGraphAlgorithms implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;

    @Override
    public void init(DirectedWeightedGraph gr) {
        this.graph = gr;
        fixNodesNeighbors();
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        MyDirectedWeightedGraph gra = (MyDirectedWeightedGraph) graph;
        return gra.copy();
    }

    @Override
    public boolean isConnected() {
        MyDirectedWeightedGraph g = (MyDirectedWeightedGraph) graph;

        boolean[] visi = new boolean[graph.nodeSize()];
        dfsIterative(0, visi);

        for (int i = 0; i < graph.nodeSize(); i++) {
            if (visi[i] == false){
                g.setConnected(false);
                return false;
            }
        }
        g.setConnected(true);
        return true;
    }
//this method is going of every node and mark if the node was visited
    private void dfs(int NodeId, boolean[] visi) {
        MyDirectedWeightedGraph gg = (MyDirectedWeightedGraph) graph;
        visi[NodeId] = true;
        Node n = (Node) gg.getNode(NodeId);

        for (Object o : n.neighbors) {
            EdgeData ed = (Edge) o;
            if (visi[ed.getDest()] == false) dfs(ed.getDest(), visi);
        }
    }

    //this method is going of every node and mark if the node was visited
    private void dfsIterative(int NodeId, boolean[] visi){
        MyDirectedWeightedGraph gg = (MyDirectedWeightedGraph) graph;
        visi[NodeId] = true;

        Stack<Integer> st = new Stack();
        st.push(NodeId);

        while (!st.isEmpty()){
            int v = st.pop();
            Node n = (Node) graph.getNode(v);

            for (Object o : n.neighbors) {
                EdgeData ed = (Edge) o;

                if (visi[ed.getDest()] == false){
                    st.push(ed.getDest());
                    visi[ed.getDest()]=true;
                }
            }
        }

    }

    //this method is adding every node its edges
    private void fixNodesNeighbors() {
        MyDirectedWeightedGraph gg = (MyDirectedWeightedGraph) graph;

        for (Object o1 : gg.getNodes().values()) {
            Node n = (Node) o1;
            n.neighbors = new LinkedList<EdgeData>();
        }

        for (Object o : gg.getEdges().values()) {
            EdgeData ed = (Edge) o;
            Node srcNode = (Node) gg.getNode(ed.getSrc());
            srcNode.addNeighbor(ed);
        }
    }

    //this method returns the distance of every node from the decided source
    private double[] dijkDist(int src, LinkedList<NodeData>[] thePath) {
        MyDirectedWeightedGraph gg = (MyDirectedWeightedGraph) graph;
        double[] distance = new double[graph.nodeSize()];
        boolean[] visi = new boolean[graph.nodeSize()];
        Set<Integer> mySet = new HashSet<>();

        for (int k = 0; k < graph.nodeSize(); k++) {
            visi[k] = false;
            distance[k] = 999999;
        }
        distance[src] = 0;
        int thisNode = src;

        while (1 == 1) {
            visi[thisNode] = true;
            Node n = (Node) gg.getNode(thisNode);

            for (EdgeData ed : n.getNeighbors()) {
                if (visi[ed.getDest()]) continue;
                ;

                mySet.add(ed.getDest());
                double tempLenghth = distance[thisNode] + ed.getWeight();
                if (tempLenghth < distance[ed.getDest()]) {
                    distance[ed.getDest()] = tempLenghth;
                    thePath[ed.getDest()] = new LinkedList<>();
                    thePath[ed.getDest()] = (LinkedList<NodeData>) thePath[thisNode].clone();
                    thePath[ed.getDest()].addLast(gg.getNode(thisNode));
                }
            }
            mySet.remove(thisNode);
            if (mySet.size() == 0) break;

            double minimumDistance = 999999;
            int j = 0;
            for (int i : mySet) {
                if (minimumDistance > distance[i]) {
                    minimumDistance = distance[i];
                    j = i;
                }
            }
            thisNode = j;
        }
        return distance;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;

        LinkedList<NodeData>[] thePath = new LinkedList[graph.nodeSize()];
        for (int i = 0; i < thePath.length; i++) {
            thePath[i] = new LinkedList<>();
        }
        double[] distance = dijkDist(src, thePath);
        if (distance[dest] == -1 || distance[dest] == 999999) return -1;
        return distance[dest];
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (src == dest) {
            NodeData n = graph.getNode(src);
            LinkedList<NodeData> thePath = new LinkedList();
            thePath.add(n);
            return thePath;
        }

        LinkedList<NodeData>[] thePath = new LinkedList[graph.nodeSize()];
        for (int i = 0; i < thePath.length; i++) {
            thePath[i] = new LinkedList<>();
        }
        double[] distance = dijkDist(src, thePath);
        if (distance[dest] == -1 || distance[dest] == 999999) return null;
        thePath[dest].add(graph.getNode(dest));
        List<NodeData> ans = thePath[dest];
        return ans;
    }

    //this method returns the max distance for every node
    private double[] eccentricity() {
        double[] ecc = new double[graph.nodeSize()];
        Arrays.fill(ecc, Integer.MIN_VALUE);

        MyDirectedWeightedGraph gg = (MyDirectedWeightedGraph) graph;

        for (Object o1 : gg.getNodes().values()) {
            NodeData nd1 = (Node) o1;
            for (Object o2 : gg.getNodes().values()) {
                NodeData nd2 = (Node) o2;
                double dist = shortestPathDist(nd1.getKey(), nd2.getKey());
                if (dist > ecc[nd1.getKey()])
                    ecc[nd1.getKey()] = dist;
            }
        }
        return ecc;
    }

    //this method returns the center of the graph
    @Override
    public NodeData center() {
        MyDirectedWeightedGraph gg = (MyDirectedWeightedGraph) graph;
        if (gg.getIsConnected() || isConnected()) {
            double[] dist = eccentricity(); //calculating the max distance of every node
            double min = dist[0];
            int index = 0;
            for (int i = 0; i < graph.nodeSize(); i++) {
                if (dist[i] < min) {
                    min = dist[i];
                    index = i;
                }
            }
            return graph.getNode(index); //returns the node with the minimum max distance of the graph
        }
        return null;
    }

    //this mehtod is checking if all the cities was visited
    private boolean allVisited(List<NodeData> cities,HashMap<Integer,Boolean> visited) {
        for (NodeData n: cities) {
            int i =n.getKey();
            if (visited.get(i)==false) return false;
        }
        return true;
    }

    //this method returns the next lightest node and checking if the node was visited
    private int theLightNextNodeNotVisited(int nodeId, HashMap<Integer,Boolean> visited) {
        int index = -1;
        Node n = (Node) graph.getNode(nodeId);
        if (n!=null) {
            if (n.getNeighbors().isEmpty()) return -1;

            double minW = Integer.MAX_VALUE;

            for (Object o : n.getNeighbors()) {
                EdgeData ed = (Edge) o;

                if (ed.getWeight() < minW) {
                    if (visited.containsKey(ed.getDest()) == false || visited.get(ed.getDest()) == false) {
                        minW = ed.getWeight();
                        index = ed.getDest();
                    }
                }
            }
        }
        return index;

    }
    //this method returns the next lightest node
    private int theLightNextNode(int nodeId) {
        int index = -1;
        Node n = (Node) graph.getNode(nodeId);
        if (n!=null) {
            if (n.getNeighbors().isEmpty()) return -1;

            double minW = Integer.MAX_VALUE;


            for (Object o : n.getNeighbors()) {
                EdgeData ed = (Edge) o;

                if (ed.getWeight() < minW) {
                    minW = ed.getWeight();
                    index = ed.getDest();
                }
            }
        }
        return index;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        HashMap<Integer, Boolean> visited = new HashMap();
        LinkedList<NodeData> thepath = new LinkedList<NodeData>();

        fillFalseInHashMap(cities,visited);

        int srcIndex = 0;

        int nextNode=-1;
        int curr=srcIndex;

        while (allVisited(cities, visited) == false) {
            if (srcIndex> graph.nodeSize()) return null;

        nextNode = theLightNextNodeNotVisited(curr,visited);

        if (nextNode==-1) nextNode = theLightNextNode(curr);
        if (nextNode==-1){
            thepath= new LinkedList<NodeData>();
            fillFalseInHashMap(cities,visited);
            srcIndex++;
            curr =srcIndex;
        }

        thepath.add(graph.getNode(curr));
        visited.put(curr,true);
        curr=nextNode;
        }

       return thepath;
    }

    //putting false at every node id in visited
    private void fillFalseInHashMap(List<NodeData> cities, HashMap<Integer, Boolean> visited) {
        for (NodeData nd : cities) {
            visited.put(nd.getKey(), false);
        }
    }

    @Override
    public boolean save(String file) {
        try {
            graphHelper gr = new graphHelper((MyDirectedWeightedGraph) graph);
            FileWriter fwriter = new FileWriter(file);
            Gson gson = new GsonBuilder().create();
            gson.toJson(gr, fwriter);
            fwriter.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            Gson gson = new Gson();
            String str = new String(Files.readAllBytes(Paths.get(file)));
            graphHelper gh = gson.fromJson(str, graphHelper.class);
            DirectedWeightedGraph gg = gh.LinkedListToGraph();
            MyDirectedWeightedGraph mdwg = (MyDirectedWeightedGraph) gg;
            mdwg.setMC(0);
            init(gg);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
