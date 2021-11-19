//this program was created by humzah okadia on february 20 2020
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;//imports
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.Arrays;

public class assignment5 extends JPanel{//class assign3 extends jpanel
    int count = 0, count1 = 0;
    boolean count2 = false,count3 = false;
    int x,y,w,h;//first two x,ypoints of line and last 2 x,y points of the line
    boolean add = true, moves = false, delete = false, secondmove = false, nod = false, movenode = false,  movenode1 = false;
    boolean up = false, down = false, left = false, right = false;//boolean variables for different actions
    ArrayList<Line2D> edges = new ArrayList<>();//arraylist of lines called edges
    ArrayList<Ellipse2D> nodes = new ArrayList<>();//arraylist of nodes called edges
    Ellipse2D first;
    int index;
    static String message1 = "click on one node and drag to another to make an edge";//instructions for each option
    static String message2 = "click on the node you would like to delete";
    static String message3 = "Click on an edge on the screen then select delete";
    static String message4 = "click anywhere to add a node";
    static String message5 = "click on the node you would like to move then select move then click where you want to move it";

    Line2D choice;
    Ellipse2D choice1;


    public assignment5(){//constructor
        JPopupMenu npop = new JPopupMenu();//popup menu for moving nodes
        JPopupMenu bpop = new JPopupMenu();//popup menu for moving nodes
        JMenuItem c7 = new JMenuItem("move");
        c7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand() == "move"){//move a node
                    ArrayList<Ellipse2D> rem = new ArrayList();
                    for (Ellipse2D b : nodes) {
                        rem.add(b);
                    }
                    for (Ellipse2D w : rem) {
                        if (w.contains(x, y)) {
                            nodes.remove(w);
                        }
                    }
                    movenode1 = true;
                    return;
                }

                return;
            }
        });

        npop.add(c7);
        JPopupMenu dpop = new JPopupMenu();//popup menu for deleting
        JMenuItem c2 = new JMenuItem("delete");
        c2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand() == "delete"){
                    if (delete) {
                        edges.remove(choice);
                        count2 = true;
                        repaint();
                    }
                    return;
                }
                return;
            }
        });
        dpop.add(c2);

        JPopupMenu mpop = new JPopupMenu();//popup menu for deleting
        JMenuItem c1 = new JMenuItem("delete");
        c1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand() == "delete"){
                        removeedge();
                }
                return;
            }
        });
        mpop.add(c1);

        addMouseListener(new MouseAdapter()//mouse action listener
        {
            public void mousePressed(MouseEvent m)//mouselistner
            {

                if (movenode) {//moving node part 1
                    for (Ellipse2D w : nodes) {
                        if (w.contains(m.getX(), m.getY())) {
                            first = w;
                            npop.show(m.getComponent(), m.getX(), m.getY());
                            x = m.getX();
                            y = m.getY();
                            movenode = false;

                        }
                    }
                }

                if (movenode1) {// moving node part 2
                            nodes.add(new Ellipse2D.Float(m.getX(),m.getY(),20,20));
                            for(Line2D q: edges){
                                if(first.contains(q.getX1(),q.getY1())){
                                    q.setLine(m.getX()+10,m.getY()+10,q.getX2(),q.getY2());
                                }
                                if(first.contains(q.getX2(),q.getY2())){
                                    q.setLine(q.getX1(),q.getY1(),m.getX()+10,m.getY()+10);
                                }
                            }
                            repaint();
                            movenode = true;
                            movenode1 = false;
                }

                if (add) { // add a node
                    for (Ellipse2D w : nodes) {
                        if (w.contains(m.getX(), m.getY())) {
                            x = m.getX();
                            y = m.getY();
                        }
                    }
                            repaint();//drawing lines
                }
                if (nod){
                    x = m.getX();
                    y = m.getY();
                    count3 = true;
                    repaint();//drawing lines
                }

                if (moves){ //remove a node
                    for (Ellipse2D w : nodes){
                        if(w.contains(m.getX(), m.getY())){
                            if(moves){//deleting
                                mpop.show(m.getComponent(), m.getX(), m.getY());
                                choice1 = w;
                            }

                        }
                    }

                }

                else{ // delete edge
                    for (Line2D w : edges){
                        double dist = w.ptLineDist(m.getX(),m.getY());
                        if( dist <= 35){
                            if(delete){//deleting
                                dpop.show(m.getComponent(), m.getX(), m.getY());
                                choice = w;
                            }

                        }
                    }

                }

            }
            public void mouseReleased(MouseEvent m)
            {
                if (add){ // add edge
                    for (Ellipse2D q : nodes) {
                        if (q.contains(m.getX(), m.getY())) {
                            w = m.getX();
                            h = m.getY();
                            count2 = true;
                        }
                    }
                    repaint();
                }
            }

        });
    }

    public void edges(Graphics g){//draws the edges
        Graphics2D g2d = (Graphics2D) g;
        //edge[count] = new Line2D.Float(x,y,w,h);
        //Rectangle r = new Rectangle(51,69,20,50);
        if (count2 == true){//draws every line in the list
            edges.add(new Line2D.Float(x,y,w,h));
            x=y=w=h=0;
            count++;
            count2 = false;
        }
            for(Shape q : edges){
                g2d.draw(q);
            }
            for(Shape x : nodes){
                g2d.fill(x);
            }

        //g2d.draw(r);

    }
    public void removeedge(){ // removes edges
            ArrayList<Line2D> toRemove = new ArrayList();
            for (Line2D b : edges) {
                toRemove.add(b);
            }
            for (Line2D a : toRemove) {
                if (choice1.contains(a.getX1(), a.getY1())) {
                    edges.remove(a);
                }
                if (choice1.contains(a.getX2(), a.getY2())) {
                    edges.remove(a);
                }
            }
            nodes.remove(choice1);
            repaint();
            return;
    }

    public void nodes(Graphics g){//draws the nodes
        Graphics2D g2d = (Graphics2D) g;
        if (count3 == true){//draws every node in the list
            nodes.add(new Ellipse2D.Float(x,y,20,20));
            x=y=0;
            count1++;
            count3 = false;
        }
            for(Shape q : nodes){
                g2d.fill(q);
            }
            for(Shape v : edges){
                g2d.draw(v);
            }

        //g2d.draw(r);

    }

    public void paint(Graphics g) {//paint component
        //super.paint(g);
        //System.out.println(delete);
        //g.drawLine(x,y,w,h);
        nodes(g);
        edges(g);
    }
    public static void main(String args[]){//main
        assignment5 a = new assignment5();//directed graph panel
        JPanel p = new JPanel();//combobox panel
        JLabel l = new JLabel(message1, SwingConstants.CENTER);//label
        String shapes[] = {"choose","Add Node","Move Node","Delete nodes","ADD Edge","Delete Edge"};//create a array of combobox menus
        JComboBox<String> c = new JComboBox<String>(shapes);//create a combo box
        c.setBounds(425, 450,100,30);
        JFrame f = new JFrame("a3");//frame
        f.setSize(800, 800);
        p.setLayout(new GridLayout(2,1));
        p.add(l);
        p.add(c);
        f.setLayout(new GridLayout(2,1));
        f.add(a);
        f.add(p);
        c.addActionListener(new ActionListener() {//combobox action listener

            public void actionPerformed(ActionEvent e)//action listener for combo box
            {
                if (c.getSelectedItem().equals("ADD Edge")){//set mode of th graph
                    a.repaint();
                    a.add = true;
                    a.moves = false;
                    a.nod = false;
                    a.delete = false;
                    l.setText(message1);
                }

                else if (c.getSelectedItem().equals("Move Node")){//set mode of th graph
                    a.repaint();
                    a.add = false;
                    a.movenode = true;
                    a.nod = false;
                    a.moves = false;
                    a.delete = false;
                    l.setText(message5);
                }

                else if (c.getSelectedItem().equals("Add Node")){//set mode of th graph
                    a.repaint();
                    a.add = false;
                    a.nod = true;
                    a.moves = false;
                    a.delete = false;
                    l.setText(message4);
                }
                else if (c.getSelectedItem().equals("Delete nodes")){
                    a.repaint();
                    a.add = false;
                    a.moves = true;
                    a.nod = false;
                    a.delete = false;
                    l.setText(message2);
                }
                else if (c.getSelectedItem().equals("Delete Edge")){
                    a.repaint();
                    a.add = false;
                    a.moves = false;
                    a.nod = false;
                    a.delete = true;
                    l.setText(message3);
                }
            }
        });
        f.setVisible(true);
    }

}