/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProiectGeometrie;

import ObiecteGeometrice.Point;
import ObiecteGeometrice.Triangle;
import ObiecteGeometrice.VectorGeo;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.io.*;
import ObiecteGeometrice.Poligon;

/**
 *
 * @author Dragos-Alexandru
 */
public class ProiectGeometrie extends JFrame{
    public static DrawingBoard drawingBoard;
    public JButton exit;
    public static int width = 600;
    public static int height = 600;
    public static int zoom = 30;
    public ProiectGeometrie(){
        super("LabGeo");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        drawingBoard = new DrawingBoard(width,height,zoom);
        exit = new JButton("Exit");
        exit.addActionListener((ActionEvent e) -> {
            if (exit.getParent().getParent().getParent().getParent() instanceof ProiectGeometrie) {
                ProiectGeometrie parent1 = (ProiectGeometrie)exit.getParent().getParent().getParent().getParent();
                parent1.dispose();
            }
        });
        this.add(drawingBoard,BorderLayout.CENTER);
        this.add(exit,BorderLayout.SOUTH);
        //this.setUndecorated(true);
        this.setVisible(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProiectGeometrie.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void start(){
        boolean exit = false;
        while(!exit){
            if(this.isActive()){
                drawingBoard.repaint(this.getGraphics());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProiectGeometrie.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                exit = true;
            }
            
        }
    }
    static void triangulate() throws CloneNotSupportedException{
        Scanner fs = null;
        try
        {
            fs = new Scanner(new File("poligon.in"));
        }
        catch(FileNotFoundException fnf)
        {
            System.out.print("Fisier inexistent");
        }
        int n = fs.nextInt();
        double x,y;
        Point p = new Point("p",0,0,0,0);
        Point []poligon = new Point[n+2];
        for(int i = 1 ; i <= n ; i ++){
            x = fs.nextDouble();
            y = fs.nextDouble();
            p.x = x;
            p.y = y;
            poligon[i] = p.clone();
        }
        poligon[n+1] = poligon[1].clone();
        Poligon P = new Poligon(poligon,n);
        Poligon P2 = new Poligon(poligon,n);
        //drawingBoard.poligons.add(P2);
        Triangle []triangles;
        triangles = P.weakEarCuttingTriangulation();
        for(int i = 1 ; i <= n-2 ; i ++){
            System.out.print("Triunghiul "+i+" are punctele :");
            System.out.println(triangles[i]);
            drawingBoard.triangles.add(triangles[i]);
            System.out.print("\n");
        }
    }
    
    public static void main(String[] args) throws CloneNotSupportedException {
        ProiectGeometrie frame = new ProiectGeometrie();
        triangulate();
        frame.start();
    }
}
