package api;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class MyDirectedWeightedGraphAlgorithmsTest {

    @Test
    void init() {
        NodeData n = new Node("5,5,5",0);
        HashMap<Integer, NodeData> hm = new HashMap();
        hm.put(n.getKey(),n);
        DirectedWeightedGraph g = new MyDirectedWeightedGraph(hm, new HashMap<Vector,EdgeData>());
        DirectedWeightedGraphAlgorithms gh = new MyDirectedWeightedGraphAlgorithms();
        gh.init(g);
        assertEquals(g.getNode(0).toString(),"pos=5,5,5, id=0");
    }


    @Test
    void copy() {
        MyDirectedWeightedGraphAlgorithms g1 = new MyDirectedWeightedGraphAlgorithms();
        boolean r1 =g1.load("data/G1.json");
        DirectedWeightedGraph graph= g1.copy();

        assertEquals(g1.getGraph().getNode(0).toString(),graph.getNode(0).toString());
        assertEquals(g1.getGraph().getNode(5).toString(),graph.getNode(5).toString());
        assertEquals(g1.getGraph().getNode(10).toString(),graph.getNode(10).toString());
    }

    @Test
    void isConnected() {
        MyDirectedWeightedGraphAlgorithms g1 = new MyDirectedWeightedGraphAlgorithms();
        boolean r1 =g1.load("data/G1.json");

        //long startTime = System.nanoTime();
        assertTrue(g1.isConnected());
        //long endTime = System.nanoTime();
        //long duration = (endTime - startTime);
        //System.out.println("time to end run connected: "+duration/1000000 );

        MyDirectedWeightedGraphAlgorithms g2 = new MyDirectedWeightedGraphAlgorithms();
        boolean r2 =g2.load("data/G2.json");
        assertTrue(g2.isConnected());

    }

    @Test
    void shortestPathDist() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G1.json");

        assertEquals(4.096793421922225, g.shortestPathDist(0,3));
        assertEquals(3.031440459773105, g.shortestPathDist(2,7));
        assertEquals(8.665140841052668, g.shortestPathDist(15,4));
    }

    @Test
    void shortestPath() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G3.json");

        assertEquals(g.shortestPath(5,13).toString(), "[pos=35.20754740435835,32.10254648739496,0.0, id=5, pos=35.209415362389024,32.10265552605042,0.0, id=13]");
        assertEquals(g.shortestPath(14,2).toString(), "[pos=35.203259591606134,32.1031462,0.0, id=14, pos=35.209415362389024,32.10265552605042,0.0, id=13, pos=35.211092279257464,32.10265552605042,0.0, id=3, pos=35.21313005165456,32.1046000487395,0.0, id=2]");
        assertEquals(g.shortestPath(3,7).toString(), "[pos=35.211092279257464,32.10265552605042,0.0, id=3, pos=35.209415362389024,32.10265552605042,0.0, id=13, pos=35.20964885714286,32.104091201680674,0.0, id=11, pos=35.20945781598063,32.105781300840334,0.0, id=7]");
    }

    @Test
    void center() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G1.json");

        assertEquals(8,g.center().getKey());

        g.load("data/G2.json");

        assertEquals(0,g.center().getKey());

    }

    @Test
    void tsp() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G1.json");

        MyDirectedWeightedGraphAlgorithms g2 = new MyDirectedWeightedGraphAlgorithms();
        boolean r2 =g2.load("data/G2.json");

        LinkedList<NodeData> al = new LinkedList();
        al.add(g.getGraph().getNode(0));
        al.add(g.getGraph().getNode(1));
        al.add(g.getGraph().getNode(2));
        al.add(g.getGraph().getNode(3));

        List list =g.tsp(al);
        assertEquals(list.toString(),"[pos=35.19589389346247,32.10152879327731,0.0, id=0, pos=35.20319591121872,32.10318254621849,0.0, id=1, pos=35.20752617756255,32.1025646605042,0.0, id=2, pos=35.21007339305892,32.10107446554622,0.0, id=3]");

         al = new LinkedList();
        al.add(g2.getGraph().getNode(0));
        al.add(g2.getGraph().getNode(1));
        al.add(g2.getGraph().getNode(2));
        al.add(g2.getGraph().getNode(3));

        list =g2.tsp(al);
        assertEquals(list.toString(),"[pos=35.19589389346247,32.10152879327731,0.0, id=0, pos=35.19691277966102,32.10240110252101,0.0, id=21, pos=35.197952892655366,32.10271004537815,0.0, id=22, pos=35.198929325262306,32.10376408571429,0.0, id=23, pos=35.200160479418884,32.1041457210084,0.0, id=24, pos=35.201731262308314,32.104636394957986,0.0, id=25, pos=35.20260156093624,32.10476360672269,0.0, id=26, pos=35.20154022114608,32.10594485882353,0.0, id=8, pos=35.205764353510894,32.106326494117646,0.0, id=7, pos=35.20504264245359,32.104854472268904,0.0, id=27, pos=35.205764353510894,32.106326494117646,0.0, id=7, pos=35.20797194027441,32.104854472268904,0.0, id=6, pos=35.212111165456015,32.106235628571426,0.0, id=5, pos=35.21143190799032,32.104854472268904,0.0, id=28, pos=35.21310882485876,32.104636394957986,0.0, id=4, pos=35.21007339305892,32.10107446554622,0.0, id=3, pos=35.20752617756255,32.1025646605042,0.0, id=2, pos=35.20319591121872,32.10318254621849,0.0, id=1]");




    }

    @Test
    void save() {
        String str1= "35.19589389346247,32.10152879327731,0.0";
        String str2= "36.19589389346247,31.10152879327731,0.0";
        NodeData n1 = new Node(str1,0);
        NodeData n2 = new Node(str2,1);
        HashMap<Integer,NodeData> nodes = new HashMap();
        nodes.put(n1.getKey(),n1);
        nodes.put(n2.getKey(),n2);
        MyDirectedWeightedGraph graph1 = new MyDirectedWeightedGraph(nodes, new HashMap());
        graph1.connect(n1.getKey(),n2.getKey(),1);
        MyDirectedWeightedGraphAlgorithms gAlgo = new MyDirectedWeightedGraphAlgorithms();
        gAlgo.init(graph1);
        gAlgo.save("GG1.json");
        String str=null;
        try {
             str = new String(Files.readAllBytes(Paths.get("GG1.json")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(str,"{\"Nodes\":[{\"pos\":\"35.19589389346247,32.10152879327731,0.0\",\"id\":0},{\"pos\":\"36.19589389346247,31.10152879327731,0.0\",\"id\":1}],\"Edges\":[{\"src\":0,\"w\":1.0,\"dest\":1}]}");
    }

    @Test
    void load() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G1.json");
        MyDirectedWeightedGraph gg = (MyDirectedWeightedGraph) g.getGraph();

        assertEquals(gg.getNodes().get(0).toString(),"pos=35.19589389346247,32.10152879327731,0.0, id=0");
        assertEquals(gg.getNodes().get(5).toString(),"pos=35.212111165456015,32.106235628571426,0.0, id=5");
    }
}