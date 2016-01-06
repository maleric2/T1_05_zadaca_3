/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.terminal;

import java.util.List;

/**
 * Apstraktna klasa koja je kostur za ispisivanje Okomitog i Vertilalnog prikaza
 * 
 * Ujedno (PRETPOSTAVKA) TODO: Provjerit iduće
 * Template Method jer definira metode koje se moraju koristiti
 * View jer se brine samo o ispisu
 * @author Marko
 */
public abstract class DrawerUI {
    public static final String ANSI_ESC = "\033[";
    int gornjiKursorX = 3;
    int gornjiKursorY = 2;

    int donjiKursorX = 3;
    int donjiKursorY = 2;
    int pocetakDrugogOkvira = 0;

    int sirina;
    int visina;

    public DrawerUI(int sirina, int visina){
        this.sirina = sirina;
        this.visina = visina;
    }
    
    /**
     * Ispisuje okvir
     * DrawerUIV - Lijevi i Desni
     * DrawerUIO - Gornji i Donji
     */
    public abstract void drawFrame();
    public abstract void drawWindow1(List<String> tekst);
    public abstract void drawWindow2(List<String> tekst);
    
    public abstract void clearWindow1();
    public abstract void clearWindow2();
    
    
    public void drawBottom(String text){
        draw(visina+1, 1, 37, text);
        
    }
    protected void draw(int x, int y, int boja, String tekst) {
        setPosition(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        System.out.print(tekst);
    }
    protected void setPosition(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }
}
