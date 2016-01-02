/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.windows;

/**
 *
 * @author Filip
 */
public class Window_O implements Window {

    private String orijentacija;
    private int brRedaka;
    private int brStupaca;
    
    private int brRedakaFrame;
    
    private char[] redKojiDijeli;
    
    String ivicaIMarica = "Bio jednom jedan drvosječa koji je sa ženom i dvoje djece živio u maloj kolibi na kraju velike šume.";
    
    public Window_O(String[] args) {
        orijentacija = args[2];
        brRedaka = Integer.parseInt(args[0]);
        brStupaca = Integer.parseInt(args[1]);
        brRedakaFrame = brRedaka/2 - 1;
        
        redKojiDijeli = new char[brStupaca];
        for (int i = 0; i < redKojiDijeli.length; i++) {
            redKojiDijeli[i] = '_';
        }
    }
    
    
    @Override
    public void createFrame1() {
        System.out.println("Prikaz 1. prozora: ...");

        for (int k = 0; k<brRedakaFrame-1; k++) {
            char[] red = new char[brStupaca];

            for (int i = 0; i < red.length; i++) {
                if (i==0)
                    red[i] = '|';
                else if (i==brStupaca-1)
                    red[i] = '|';
                else {
                    red[i] = ' ';
                }   
            }

            System.out.println(red);
        }
        System.out.println(redKojiDijeli);   
    }
    
    @Override
    public void createFrame2() {

        for (int k = 0; k<brRedakaFrame-1; k++) {
            char[] red = new char[brStupaca];

            for (int i = 0; i < red.length; i++) {
                if (i==0)
                    red[i] = '|';
                else if (i==brStupaca-1)
                    red[i] = '|';
                else {
                    red[i] = ' ';
                }   
            }

            System.out.println(red);
        }
        System.out.println(redKojiDijeli);   
    }
    
}
