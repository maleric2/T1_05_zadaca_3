/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.terminal;

import java.util.List;

/**
 *
 * @author Tomislav
 */
public class IspisV {

    public static final String ANSI_ESC = "\033[";
    int gornjiKursorX = 2;
    int gornjiKursorY = 2;

    int donjiKursorX = 2;
    int donjiKursorY = 2;
    int pocetakDrugogOkvira = 0;

    int sirina;
    int visina;

    public IspisV(int visina) {
        this.pocetakDrugogOkvira = sirina/2+1;
        //this.donjiKursorY = 2;
    }

    public void nacrtajGranice(int sirina, int visina) {
        //gore
        for (int i = 1; i <= sirina; i++) {
            prikazi(1, i, 37, "#");
        }
        //dolje
        for (int i = 1; i <= sirina; i++) {
            prikazi(visina, i, 37, "#");
        }
        //ljevo
        for (int i = 1; i <= visina; i++) {
            prikazi(i, 1, 37, "#");
        }
        //sredina V
        for (int i = 1; i <= visina; i++) {
            prikazi(i, sirina/2, 37, "#");
        }
        //desno
        for (int i = 1; i <= visina; i++) {
            prikazi(i, sirina, 37, "#");
        }
    }

    public void ubaciTextLijevo(List<String> tekst, int sirina, int visina) {
        int sljedeciRed = 0;
        for (int i = gornjiKursorY; i < visina; i++) {
            if (sljedeciRed == tekst.size()) {
                gornjiKursorX = tekst.get(sljedeciRed - 1).length() + 2;
                gornjiKursorY = i;
                break;
            }
            prikazi(i, 2, 37, tekst.get(sljedeciRed));
            sljedeciRed++;
            if(i==visina-1)
            {
                ocistiLijevo(sirina, visina+2);
                i = 1;
                gornjiKursorX = 2;
                gornjiKursorY = 2;
            }
        }
    }

    public void ocistiLijevo(int sirina, int visina) {
        for (int i = 2; i < visina-2; i++) {
            for (int j = 2; j < sirina/2; j++) {
                prikazi(i, j, 37, " ");
            }
        }
    }

    

    public void ubaciTextDesno(List<String> tekst, int sirina, int visina) {
        int sljedeciRed = 0;
        for (int i = donjiKursorY; i < visina; i++) {
            if (sljedeciRed == tekst.size()) {
                donjiKursorX = tekst.get(sljedeciRed-1).length()+2;
                donjiKursorY = i;
                break;
            }
            prikazi(i, sirina/2+1, 37, tekst.get(sljedeciRed));
            sljedeciRed++;
            
            if(i == visina-1)
            {
                ocistiDesno(sirina, visina);
                donjiKursorY = 2;
                i = pocetakDrugogOkvira;
            }
        }
    }

    public void ocistiDesno(int sirina, int visina) {
        for (int i = 2; i < visina; i++) {
            for (int j = sirina/2+1; j < sirina; j++) {
                prikazi(i, j, 37, " ");
            }
        }
    }
    
    public void prebaciNaDno(int sirina, int visina) {

        String izbornik
                = "Izbornik:\n-------------------------------------------------\n"
                + "-1 - ispis ukupnog broja direktorija i datoteka u strukturi\n"
                + "-2 - ispis sadržaja strukture direktorija i datoteka\n"
                + "-3 - izvršavanje dretve\n"
                + "-4 - prekid izvršavanja dretve\n"
                + "-5 - ispis informacija o svim spremljenim stanjima\n"
                + "-6 n - postavljanje stanja strukture\n"
                + "-7 m - uspoređivanje trenutnog stanja strukture i promjene s rednim brojem m\n"
                + "-8 - ponovno učitavanje strukture uz poništavanje svih spremljenih stanja strukture\n"
                + "-9 - dodana vlastita funkcionalnost\n"
                + "-Q - prekid rada programa\n"
                + "Odaberite: ";

        prikazi(visina, 1, 37, izbornik);
    }

    private void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    private void prikazi(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        System.out.print(tekst);
        /*try {
            Thread.sleep(2);
        } catch (InterruptedException ex) {
        }*/
    }
}