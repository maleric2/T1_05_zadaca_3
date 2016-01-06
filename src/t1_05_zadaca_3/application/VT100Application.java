/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import t1_05_zadaca_3.structure.CareTaker;
import t1_05_zadaca_3.structure.ElementStructure;
import t1_05_zadaca_3.structure.Originator;
import t1_05_zadaca_3.structure.PrintStructure;
import t1_05_zadaca_3.terminal.Drawer;
import t1_05_zadaca_3.terminal.IspisO;
import static t1_05_zadaca_3.terminal.IspisO.ANSI_ESC;
import t1_05_zadaca_3.terminal.IspisV;
import t1_05_zadaca_3.terminal.ObradaTeksta;
import t1_05_zadaca_3.thread.CheckStructureThread;

/**
 * Glavna klasa za VT100 Terminal Singleton
 *
 * @author Marko
 */
public class VT100Application {

    private int brojacRazina;

    private static VT100Application instance;

    public static Originator izvor = new Originator();
    public static CareTaker save = new CareTaker();

    private VT100Application() {
        System.out.println("New VT100 Terminal Application..");
    }

    public static VT100Application getInstance() {
        if (instance == null) {
            synchronized (VT100Application.class) {
                if (instance == null) {
                    instance = new VT100Application();
                }
            }
        }
        return instance;
    }

    /**
     * Start metoda koja pokreće aplikaciju U metodi se nalaze pozivi svih
     * ostalih funkcionalnosti (poziva ispis izbornika i sl.)
     *
     * @param args
     */
    public void start(String[] args) {
        //validator
        System.out.print(ANSI_ESC + "2J");

        ArgumentsValidator validator = new ArgumentsValidator();
        if (!validator.validate(args)) {
            System.out.println(validator.getErrorMsg());
        } else {
            //validator is successful
            //TODO: Do some app logic

            //Struktura ELEMENATA
            ElementStructure es = new ElementStructure();
            PrintStructure ps = new PrintStructure();
            es.setRootPath(args[3]);
            es.setLevels(es.getKorijenskiElement(), es, brojacRazina);

            //spremanje početne strukture u memento
            izvor.setState(es);
            save.add(izvor.saveStateToMemento());

            String brojDirDat = ps.MenuOption1(es.getDirektoriji(), es.getDatoteke(), es.getVelicinaKorDir());
            String sadrzajStrukture = ps.MenuOption2(es.getStrukturaElemenata(), es);

            int sirina = Integer.parseInt(args[1]);
            int visina = Integer.parseInt(args[0]);

            //TODO: dodatno reorganizirat zbog MVC-a:
            //Model = ElementStructure
            //View -Drawer ili DrawerUI - razdvojiti na 3 View-a - BottomView, Window1View, Window2View
            //Controller - ovo - prebacit dio logike u novu klasu ElementController(model, view);
            
            MainMenu menu = MainMenu.getInstance();
            String choice = "";
            Drawer drawer = new Drawer(args[2], sirina, visina);
            drawer.drawWindow1(sadrzajStrukture);
            drawer.drawWindow2(brojDirDat);
            do {
                //drawer.clear();
                drawer.drawBottom(menu.getMainMenu());
                choice = menu.getChoice();

                switch (choice) {
                    case "1": {
                        //Prikaze podatke o broju direktorija/datoteka i velicini
                        drawer.drawWindow2(brojDirDat);
                        break;
                    }
                    case "2": {
                        //Prikaze podatke o sadrzaju direktorija
                        drawer.drawWindow1(sadrzajStrukture);
                        break;
                    }
                    case "3": {
                        //TODO: Izvrsavanje dretve
                        break;
                    }
                    case "4": {
                        //TODO: Prekid izvrsavanja dretve
                        break;
                    }
                    case "5": {
                        //TODO: Ispis informacija o stanjima
                        break;
                    }
                    case "6": {
                        //TODO: 
                        break;
                    }
                    case "7": {
                        //OVO SPREMA POCENO STANJE I ISPISUJE GA OVO JE SAMO TEST MEMENTA
                        //uhvatim pocetno stanje iz mementa
                        izvor.getStateFromMemento(save.get(0));
                        //spremim to stanje u vratiStanje
                        ElementStructure vratiStanje = izvor.getState();

                        //postavljanje strukture na staru
                        es = vratiStanje;

                        //ispis strukture koja je vraćena
                        String staroStanje = ps.MenuOption2(vratiStanje.getStrukturaElemenata(), vratiStanje);
                        drawer.drawWindow2(staroStanje);
                        break;
                    }
                    case "8": {
                        //PONOVNO UCITAVANJE STRUKTURE NA ZADANOM DIREKTORIJU (učivata ponovno direktorij)

                        ElementStructure es2 = new ElementStructure();
                        PrintStructure ps2 = new PrintStructure();
                        es2.setRootPath(args[3]);
                        es2.setLevels(es2.getKorijenskiElement(), es2, brojacRazina);

                        brojDirDat = ps.MenuOption1(es2.getDirektoriji(), es2.getDatoteke(), es2.getVelicinaKorDir());
                        sadrzajStrukture = ps.MenuOption2(es2.getStrukturaElemenata(), es2);

                        drawer.drawWindow1(sadrzajStrukture);
                        drawer.drawWindow2(brojDirDat);

                        es = es2;
                        ps = ps2;
                        
                        break;
                    }
                }

            } while (!choice.equals("Q"));

              
            /*//DRETVA
             CheckStructureThread cs = new CheckStructureThread(es.getKorijenskiDirektorij(), es.getDirektoriji(), es.getDatoteke());
             cs.setIntervalUSec(10); //args[4]
             cs.setIzvrsavanje(true); //varijabla za start i stop dretve 
             cs.startThread();*/
        }
    }
}
