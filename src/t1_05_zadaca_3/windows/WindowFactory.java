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
public class WindowFactory {

    private String orijentacija;
    private int brRedaka;
    private int brStupaca;

    public WindowFactory() {
        
    }

    public void createWindow(String[] args) {
        orijentacija = args[2];
        brRedaka = Integer.parseInt(args[0]);
        brStupaca = Integer.parseInt(args[1]);

        System.out.println("Orijentacija: " + orijentacija);
        System.out.println("Br. redaka: " + brRedaka);
        System.out.println("Br. stupaca: " + brStupaca);

        if (orijentacija.equalsIgnoreCase("O")) {
            Window window_o = new Window_O(args);
            window_o.createFrame1();
            window_o.createFrame2();
        } else if (orijentacija.equalsIgnoreCase("V")) {
            Window window_v = new Window_V(args);
            window_v.createFrame1();
            window_v.createFrame2();
        }

    }

    //GETTERI i SETTERI
    public String getOrijentacija() {
        return orijentacija;
    }

    public void setOrijentacija(String orijentacija) {
        this.orijentacija = orijentacija;
    }

    public int getBrRedaka() {
        return brRedaka;
    }

    public void setBrRedaka(int brRedaka) {
        this.brRedaka = brRedaka;
    }

    public int getBrStupaca() {
        return brStupaca;
    }

    public void setBrStupaca(int brStupaca) {
        this.brStupaca = brStupaca;
    }

}
