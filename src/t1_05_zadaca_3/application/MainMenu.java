/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.application;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Marko
 */
public class MainMenu {

    private static MainMenu instance;
    Scanner scanner = new Scanner(System.in);

    private MainMenu() {
    }

    public static MainMenu getInstance() {
        if (instance == null) {
            synchronized (MainMenu.class) {
                if (instance == null) {
                    instance = new MainMenu();
                }
            }
        }
        return instance;
    }
    
    public String getMainMenu() {
        StringBuilder menu = new StringBuilder();
        menu.append("Izbornik:\n-------------------------------------------------\n");
        menu.append("-1 - ispis ukupnog broja direktorija i datoteka u strukturi\n");
        menu.append("-2 - ispis sadržaja strukture direktorija i datoteka\n");
        menu.append("-3 - izvršavanje dretve\n");
        menu.append("-4 - prekid izvršavanja dretve\n");
        menu.append("-5 - ispis informacija o svim spremljenim stanjima\n");
        menu.append("-6 n - postavljanje stanja strukture\n");
        menu.append("-7 m - uspoređivanje trenutnog stanja strukture i promjene s rednim brojem m\n");
        menu.append("-8 - ponovno učitavanje strukture uz poništavanje svih spremljenih stanja strukture\n");
        menu.append("-9 - dodana vlastita funkcionalnost\n");
        menu.append("-Q - prekid rada programa\n");
        menu.append("Odaberite: ");
        
        return menu.toString();
    }
    public String getChoice(){
        String choice = scanner.next();
        return choice;
    }
    public String readString(){
        return scanner.next();
    }

}
