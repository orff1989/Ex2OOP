package api;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Window extends JFrame implements ActionListener  {
    private JFrame mainFrame,subFrame;
    private JMenuBar menuBar;
    JMenu File,Edit, algo;
    JMenuItem load, save;
    JMenuItem addNode, connectNodes,removeNode,removeEdge;
    JMenuItem isConnect, shortestPathDist,center,tsp;
    JTextField loadTextField, saveTextField, addNodeTextField, connectNodesTextField, removeNodeTextField,removeEdgeTextField,shortestPathDistTextField;
    DirectedWeightedGraphAlgorithms dwga;
    Container c = new Container();
    int counterOfSaves;
    GUIGraph TheGuiGraph;


    public class GUIGraph extends JPanel {
        MyDirectedWeightedGraph graph;

        private void reset(){
            repaint();
        }


        public GUIGraph(DirectedWeightedGraph gg){
            this.graph=(MyDirectedWeightedGraph) gg;

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            double abX= Math.abs(graph.getMaxX()-graph.getMinX());
            double abY= Math.abs(graph.getMaxY()-graph.getMinY());

            double scaleX= 600/abX;
            double scaleY= 600/abY;

            for (Object o : graph.getEdges().values()){
                Edge ed =  (Edge) o;

                Node ndSrc =(Node) graph.getNode(ed.getSrc());
                Node ndDest =(Node) graph.getNode(ed.getDest());

                int xSrc = (int) (ndSrc.getLocation().x() *scaleX);
                int ySrc = (int) (ndSrc.getLocation().y()*scaleY);

                int xDest = (int) (ndDest.getLocation().x() *scaleX);
                int yDest = (int) (ndDest.getLocation().y()*scaleY);

                String srcStrX= "00"+xSrc;
                String srcStrY= "00"+ySrc;

                xSrc=Integer.parseInt(srcStrX.substring(srcStrX.length()-3));
                ySrc=Integer.parseInt(srcStrY.substring(srcStrY.length()-3));

                String destStrX= "00"+xDest;
                String destStrY= "00"+yDest;

                xDest=Integer.parseInt(destStrX.substring(destStrX.length()-3));
                yDest=Integer.parseInt(destStrY.substring(destStrY.length()-3));

                g.setColor(Color.black);
                g.drawLine(xSrc,ySrc,xDest,yDest);
            }

            for (Object o : graph.getNodes().values()){
                Node nd =  (Node) o;

                int x = (int) ( nd.getLocation().x()*scaleX);
                int y = (int) (nd.getLocation().y()*scaleY);

                String strX= "00"+x;
                String stry= "00"+y;

                x=Integer.parseInt(strX.substring(strX.length()-3));
                y=Integer.parseInt(stry.substring(stry.length()-3));

                int r=10;
                x = x-(r/2);
                y = y-(r/2);
                g.setColor(Color.red);
                g.fillOval(x,y,r,r);
            }
        }
    }

    public Window(String fileToRun) {

        dwga = new MyDirectedWeightedGraphAlgorithms();
        dwga.load(fileToRun);
        counterOfSaves = 0;

        mainFrame = new JFrame("Graph Drawer");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(getToolkit().getScreenSize());

        menuBar = new JMenuBar();

        File = new JMenu("File");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        load.addActionListener(this);
        save.addActionListener(this);
        File.add(load);
        File.add(save);
        menuBar.add(File);

        Edit = new JMenu("Edit");
        addNode = new JMenuItem("Add Node");
        connectNodes = new JMenuItem("Connect Nodes");
        removeNode = new JMenuItem("Remove Node");
        removeEdge = new JMenuItem("Remove Edge");
        addNode.addActionListener(this);

        connectNodes.addActionListener(this);
        removeNode.addActionListener(this);
        removeEdge.addActionListener(this);
        Edit.add(addNode);
        Edit.add(connectNodes);
        Edit.add(removeNode);
        Edit.add(removeEdge);
        menuBar.add(Edit);

        algo = new JMenu("Algorithms");
        isConnect = new JMenuItem("Is The Graph Connected?");
        shortestPathDist = new JMenuItem("The shortest path between:");
        center = new JMenuItem("Center");
        tsp = new JMenuItem("tsp");
        isConnect.addActionListener(this);
        shortestPathDist.addActionListener(this);
        center.addActionListener(this);
        tsp.addActionListener(this);
        algo.add(isConnect);
        algo.add(shortestPathDist);
        algo.add(center);
        algo.add(tsp);
        menuBar.add(algo);

        mainFrame.setJMenuBar(menuBar);

        TheGuiGraph = new GUIGraph(dwga.getGraph());
        mainFrame.getContentPane().add(TheGuiGraph);

        File.setVisible(true);
        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==load) {

            subFrame = getNewFrame();

            loadTextField = getNewTextField();
            loadTextField.addActionListener(this);
            subFrame.add(loadTextField);

            JLabel filePath = new JLabel("Enter file path: ");
            subFrame.add(filePath);

            filePath.setVisible(true);
            loadTextField.setVisible(true);
            subFrame.setVisible(true);
        }
        if (e.getSource()== loadTextField){

            dwga.load(new String(loadTextField.getText()));
            System.out.println("Graph " +loadTextField.getText() + " loaded");
            subFrame.setVisible(false);

            TheGuiGraph = new GUIGraph(dwga.getGraph());
            mainFrame.getContentPane().add(TheGuiGraph);

            mainFrame.setVisible(true);

        }

     if(e.getSource()==save) {
         subFrame =getNewFrame();

         saveTextField = getNewTextField();
         saveTextField.addActionListener(this);
         subFrame.add(saveTextField);

         JLabel fileName = new JLabel("Enter file name: ");
         subFrame.add(fileName);

         fileName.setVisible(true);
         saveTextField.setVisible(true);
         subFrame.setVisible(true);
     }
     if (e.getSource()==saveTextField){
         dwga.save(new String(saveTextField.getText()));
         System.out.println("saving this graph");
         subFrame.setVisible(false);
     }

        if(e.getSource()==addNode) {
            subFrame =getNewFrame();

            addNodeTextField = getNewTextField();
            addNodeTextField.addActionListener(this);
            subFrame.add(addNodeTextField);

            JLabel fileName = new JLabel("Enter Node Location: ");
            subFrame.add(fileName);

            fileName.setVisible(true);
            addNodeTextField.setVisible(true);
            subFrame.setVisible(true);
        }
        if (e.getSource()==addNodeTextField){
            NodeData nd = new Node(addNodeTextField.getText(),dwga.getGraph().nodeSize());
            dwga.getGraph().addNode(nd);
            System.out.println("Adding Node");
            subFrame.setVisible(false);

            TheGuiGraph = new GUIGraph(dwga.getGraph());
            mainFrame.getContentPane().add(TheGuiGraph);
            mainFrame.setVisible(true);
        }
        if(e.getSource()==connectNodes) {
            subFrame =getNewFrame();

            connectNodesTextField = getNewTextField();
            connectNodesTextField.addActionListener(this);
            subFrame.add(connectNodesTextField);

            JLabel fileName = new JLabel("Enter: srcId,destId,wight");
            subFrame.add(fileName);

            fileName.setVisible(true);
            connectNodesTextField.setVisible(true);
            subFrame.setVisible(true);
        }
        if (e.getSource()==connectNodesTextField){
            String[] arrOfStr = connectNodesTextField.getText().split(",");

            dwga.getGraph().connect(Integer.parseInt(arrOfStr[0]),Integer.parseInt(arrOfStr[1]),Double.parseDouble(arrOfStr[2]));
            System.out.println("Adding Edge");
            subFrame.setVisible(false);
            TheGuiGraph.reset();

        }
        if(e.getSource()==removeNode) {
            subFrame =getNewFrame();

            removeNodeTextField = getNewTextField();
            removeNodeTextField.addActionListener(this);
            subFrame.add(removeNodeTextField);

            JLabel fileName = new JLabel("Enter the id of Node: ");
            subFrame.add(fileName);

            fileName.setVisible(true);
            removeNodeTextField.setVisible(true);
            subFrame.setVisible(true);
        }
        if (e.getSource()==removeNodeTextField){
            int id =Integer.parseInt(removeNodeTextField.getText());
            dwga.getGraph().removeNode(id);
            System.out.println("Removing Node");
            subFrame.setVisible(false);
            TheGuiGraph.reset();
        }

        if(e.getSource()==removeEdge) {
            subFrame =getNewFrame();

            removeEdgeTextField = getNewTextField();
            removeEdgeTextField.addActionListener(this);
            subFrame.add(removeEdgeTextField);

            JLabel fileName = new JLabel("Enter: srcId, destId");
            subFrame.add(fileName);

            fileName.setVisible(true);
            removeEdgeTextField.setVisible(true);
            subFrame.setVisible(true);
        }
        if (e.getSource()==removeEdgeTextField){
            String[] arrOfStr = removeEdgeTextField.getText().split(",");

            dwga.getGraph().removeEdge(Integer.parseInt(arrOfStr[0]),Integer.parseInt(arrOfStr[1]));
            System.out.println("Removing Edge");
            subFrame.setVisible(false);
            TheGuiGraph.reset();
        }

        if(e.getSource()==isConnect) {

            subFrame =getNewFrame();
            long startTime = System.nanoTime();
            boolean connected = dwga.isConnected();
            System.out.println("is Connected: " +connected);


            if (connected) JOptionPane.showMessageDialog(mainFrame, "The Graph Is Connected");
            else JOptionPane.showMessageDialog(mainFrame, "The Graph Is Not Connected");

        }

        if(e.getSource()==shortestPathDist) {
            subFrame =getNewFrame();

            shortestPathDistTextField = getNewTextField();
            shortestPathDistTextField.addActionListener(this);
            subFrame.add(shortestPathDistTextField);

            JLabel fileName = new JLabel("Enter: srcId, destId");
            subFrame.add(fileName);

            fileName.setVisible(true);
            shortestPathDistTextField.setVisible(true);
            subFrame.setVisible(true);
        }
        if (e.getSource()==shortestPathDistTextField){
            String[] arrOfStr = shortestPathDistTextField.getText().split(",");

            double a = dwga.shortestPathDist(Integer.parseInt(arrOfStr[0]),Integer.parseInt(arrOfStr[1]));

            System.out.println("The shortest Path Distance is: "+ a);
            if (a==-1) JOptionPane.showMessageDialog(mainFrame, "There is no path.");
            else JOptionPane.showMessageDialog(mainFrame, "The shortest Path Distance is: "+ a);

            subFrame.setVisible(false);
        }

        if(e.getSource()==center) {
            subFrame =getNewFrame();

            NodeData nd = dwga.center();
            System.out.println("The center is: " +nd.toString());
            if (nd!=null) JOptionPane.showMessageDialog(mainFrame, "The center is: " +nd.toString());
            else JOptionPane.showMessageDialog(mainFrame, "There is no center");
        }
    }


    private JFrame getNewFrame(){
        JFrame newFrame = new JFrame("Enter Details");
        newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        newFrame.setSize(250,250);
        newFrame.setLocationRelativeTo(null);
        return newFrame;
    }
    private JTextField getNewTextField(){
        JTextField txf = new JTextField();
        txf.setSize(250,100);
        return txf;
    }

}
