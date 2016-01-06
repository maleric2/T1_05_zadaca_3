/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.terminal;

import java.util.List;

/**
 * Bivši IspisO
 * @author Marko
 */
public class DrawerUIO extends DrawerUI {

    public DrawerUIO(int sirina, int visina) {
        super(sirina, visina); //Konstruktor od DrawerUI
        this.pocetakDrugogOkvira = visina / 2 + 1;
        this.donjiKursorY = pocetakDrugogOkvira;
    }

    @Override
    public void drawFrame() {
        for (int i = 1; i <= sirina; i++) {
            draw(1, i, 37, "#");
        }
        //sredina
        for (int i = 1; i <= sirina; i++) {
            draw(visina / 2, i, 37, "#");
        }
        //dolje
        for (int i = 1; i <= sirina; i++) {
            draw(visina, i, 37, "#");
        }
        //ljevo
        for (int i = 1; i <= visina; i++) {
            draw(i, 1, 37, "#");
        }
        //desno
        for (int i = 1; i <= visina; i++) {
            draw(i, sirina, 37, "#");
        }
    }

    
    /**
     * Prvi (Gornji) prozor te ispisuje prethodno formatirani text
     * 1 član liste = 1 red
     * @param tekst 
     */
    @Override
    public void drawWindow1(List<String> tekst) {
        int sljedeciRed = 0;
        for (int i = gornjiKursorY; i < visina; i++) {
            if (sljedeciRed == tekst.size()) {
                gornjiKursorX = tekst.get(sljedeciRed - 1).length() + 2;
                gornjiKursorY = i;
                break;
            }
            draw(i, 2, 37, tekst.get(sljedeciRed));
            sljedeciRed++;
            if (i == visina / 2 - 1) {
                clearWindow1();
                i = 1;
                gornjiKursorX = 2;
                gornjiKursorY = 2;
            }
        }
    }

    
    /**
     * Drugi (Donji) prozor te ispisuje prethodno formatirani text
     * 1 član liste = 1 red
     * @param tekst 
     */
    @Override
    public void drawWindow2(List<String> tekst) {
        int sljedeciRed = 0;
        for (int i = donjiKursorY; i < visina; i++) {
            if (sljedeciRed == tekst.size()) {
                donjiKursorX = tekst.get(sljedeciRed - 1).length() + 2;
                donjiKursorY = i;
                break;
            }
            draw(i, 2, 37, tekst.get(sljedeciRed));
            sljedeciRed++;

            if (i == visina - 1) {
                clearWindow2();
                donjiKursorY = pocetakDrugogOkvira;
                i = pocetakDrugogOkvira - 1;
            }
        }
    }

    @Override
    public void clearWindow1() {
        for (int i = 2; i < visina / 2; i++) {
            for (int j = 2; j < sirina; j++) {
                draw(i, j, 37, " ");
            }
        }
    }

    @Override
    public void clearWindow2() {
        for (int i = visina / 2 + 1; i < visina; i++) {
            for (int j = 2; j < sirina; j++) {
                draw(i, j, 37, " ");
            }
        }
    }

}
