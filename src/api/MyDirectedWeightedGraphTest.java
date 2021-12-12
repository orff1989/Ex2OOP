package api;


import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class MyDirectedWeightedGraphTest {

    @Test
    void getNode() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G1.json");


        assertEquals(g.getGraph().getNode(0).toString(),"pos=35.19589389346247,32.10152879327731,0.0, id=0");
        assertEquals(g.getGraph().getNode(6).toString(),"pos=35.20797194027441,32.104854472268904,0.0, id=6");
    }

    @Test
    void getEdge() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G2.json");

        assertEquals(g.getGraph().getEdge(0,1).toString(),"src=0, w=1.232037506070033, dest=1");
        assertNull(g.getGraph().getEdge(0,5));
    }

    @Test
    void addNode() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G2.json");

        assertNull(g.getGraph().getEdge(0,5));
        g.getGraph().connect(0,5,3.33);
        assertEquals(g.getGraph().getEdge(0,5).toString(),"src=0, w=3.33, dest=5");
    }

    @Test
    void connect() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G2.json");

        assertNull(g.getGraph().getEdge(0,5));
        g.getGraph().connect(0,5,5.55);
        assertEquals(g.getGraph().getEdge(0,5).toString(),"src=0, w=5.55, dest=5");
    }

    @Test
    void nodeIter() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G3.json");

        Iterator it = g.getGraph().nodeIter();
        int sizeOfNodes=g.getGraph().nodeSize();
        int counter=0;
        while (it.hasNext()){
            it.next();
            counter++;
        }
        assertTrue(counter==sizeOfNodes);
    }

    @Test
    void edgeIter() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G3.json");

        Iterator it = g.getGraph().edgeIter();
        int sizeOfNodes=g.getGraph().edgeSize();
        int counter=0;
        while (it.hasNext()){
            it.next();
            counter++;
        }
        assertTrue(counter==sizeOfNodes);
    }

    @Test
    void testEdgeIter() {
        MyDirectedWeightedGraphAlgorithms g = new MyDirectedWeightedGraphAlgorithms();
        boolean r =g.load("data/G2.json");

        Iterator it = g.getGraph().edgeIter(1);

        int counter=0;
        while (it.hasNext()){
            it.next();
            counter++;
        }

        assertTrue(counter==3);
    }

    @Test
    void removeNode() {
        DirectedWeightedGraphAlgorithms ga = new MyDirectedWeightedGraphAlgorithms();
        ga.load("data\\G1.json");
        DirectedWeightedGraph graph = ga.getGraph();

        assertEquals(graph.getNode(0).toString(),"pos=35.19589389346247,32.10152879327731,0.0, id=0");
        graph.removeNode(0);
        assertNull(graph.getNode(0));
    }

    @Test
    void removeEdge() {
        DirectedWeightedGraphAlgorithms ga = new MyDirectedWeightedGraphAlgorithms();
        ga.load("data\\G3.json");
        DirectedWeightedGraph graph = ga.getGraph();

        assertEquals(graph.getEdge(0,1).toString(),"src=0, w=1.0286816758196655, dest=1");
        graph.removeEdge(0,1);
        assertNull(graph.getEdge(0,1));
    }

    @Test
    void nodeSize() {
        DirectedWeightedGraphAlgorithms ga = new MyDirectedWeightedGraphAlgorithms();
        ga.load("data\\G1.json");

        assertEquals(17,ga.getGraph().nodeSize());
    }

    @Test
    void edgeSize() {
        DirectedWeightedGraphAlgorithms ga = new MyDirectedWeightedGraphAlgorithms();
        ga.load("data\\G2.json");

        assertEquals(80,ga.getGraph().edgeSize());
    }

    @Test
    void getMC() {
        DirectedWeightedGraphAlgorithms ga = new MyDirectedWeightedGraphAlgorithms();
        ga.load("data\\G2.json");

        assertEquals( ga.getGraph().getMC(),0);
        ga.getGraph().connect(0,10,4);
        ga.getGraph().connect(3,11,1);
        ga.getGraph().removeEdge(0,1);
        assertEquals(ga.getGraph().getMC(),3);
    }
}