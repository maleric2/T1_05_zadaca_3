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
import t1_05_zadaca_3.mvc.ElementController;
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
import t1_05_zadaca_3.thread.ThreadController;

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
            //Validator is successful

            //Struktura ELEMENATA
            ElementStructure es = new ElementStructure();
            es.setRootPath(args[3]);
            es.setLevels(es.getKorijenskiElement(), es, brojacRazina);

            //spremanje početne strukture u memento
            izvor.setState(es);
            save.add(izvor.saveStateToMemento());

            int sirina = Integer.parseInt(args[1]);
            int visina = Integer.parseInt(args[0]);
            int interval = Integer.parseInt(args[4]);

            //TODO možda podjelit na 3 drawer-a
            //Drawer Window1 Window2 i Bottom i kojeg prosljedimo u njega zapisuje
            Drawer drawer = new Drawer(args[2], sirina, visina);
            ElementController controller = new ElementController(es, drawer);

            MainMenu menu = MainMenu.getInstance();
            String choice = "";

            CheckStructureThread cs=null;
            
            //prosljedit instancu careTaker-a pa da vrati stanja
            ThreadController tc = new ThreadController(drawer, es, interval);
            controller.updateView();
            do {
                //drawer.clear();
                controller.updateViewMenu();
                choice = menu.getChoice();
                String[] choice2 = choice.split(" ");
                switch (choice2[0]) {
                    case "1": {
                        //Prikaze podatke o broju direktorija/datoteka i velicini
                        controller.updateViewStats();
                        break;
                    }
                    case "2": {
                        //Prikaze podatke o sadrzaju direktorija
                        controller.updateViewStructure();
                        break;
                    }
                    case "3": {
                        //TODO: Izvrsavanje dretve
                        tc.startThread();
                        /*if(cs==null) cs = new CheckStructureThread(es.getKorijenskiDirektorij(), es.getDirektoriji(), es.getDatoteke());
                        cs.setIntervalUSec(10); //args[4]
                        cs.setIzvrsavanje(true); //varijabla za start i stop dretve 
                        cs.startThread();*/
                        break;
                    }
                    case "4": {
                        //TODO: Prekid izvrsavanja dretve
                        tc.pauseThread();
                        //cs.setIzvrsavanje(false); //varijabla za start i stop dretve 
                        break;
                    }
                    case "5": {
                        //Ispis informacija o svim stanjima (redni broj i vrijeme spremanja)
                        String printText = " Redni broj\t Datum\n============\t=======\n";
                        for (int i = 0; i < VT100Application.save.vratiVelicinu(); i++) {
                            t1_05_zadaca_3.structure.Memento memento = VT100Application.save.get(i);
                            ElementStructure structure = memento.getState();
                            String date = structure.GetDate();
                            printText += "\t" + i + "\t" + date + "\n";
                        }
                        drawer.drawWindow1(printText);
                        break;
                    }
                    case "6": {
                        //postavljanje stanja strukture na promjenu s rednim brojem n
                        if (choice2.length < 2) {
                            drawer.drawWindow1("Za opciju 6 je potrebno unijeti stanje\n");
                        } else {
                            int setNewState;
                            try {
                                setNewState = Integer.parseInt(choice2[1]);
                                drawer.drawWindow1("Tražim stanje...\n");
                                boolean find = false;
                                for (int i = 0; i < VT100Application.save.vratiVelicinu(); i++) {
                                    if (setNewState == i) {
                                        drawer.drawWindow1("Proasao sam stanje " + setNewState + "\n");
                                        t1_05_zadaca_3.structure.Memento memento = VT100Application.save.get(i);
                                        ElementStructure structure = memento.getState();
                                        izvor.setState(structure);
                                        drawer.drawWindow1("Novo stanje je postavljeno\n");
                                        find = true;
                                    }
                                }
                                if (!find) {
                                    drawer.drawWindow1("Stanje nije pronadjeno\n");
                                }
                            } catch (Exception ex) {
                                System.out.println("Pogrešan parametar " + choice2[1] + "! Message: " + ex.getMessage());
                            }
                        }
                        break;
                    }
                    case "7": {
                        //OVO SPREMA POCENO STANJE I ISPISUJE GA OVO JE SAMO TEST MEMENTA
                        //uhvatim pocetno stanje iz mementa
                        izvor.getStateFromMemento(save.get(0));
                        //spremim to stanje u vratiStanje
                        ElementStructure vratiStanje = izvor.getState();

                        //postavljanje strukture na staru
                        controller.setModel(vratiStanje);
                        es = vratiStanje;

                        //ispis strukture koja je vraćena
                        controller.updateViewStructure();
                        //String staroStanje = ps.MenuOption2(vratiStanje.getStrukturaElemenata(), vratiStanje);
                        //drawer.drawWindow2(staroStanje);
                        break;
                    }
                    case "8": {
                        //PONOVNO UCITAVANJE STRUKTURE NA ZADANOM DIREKTORIJU (učivata ponovno direktorij)

                        ElementStructure es2 = new ElementStructure();
                        PrintStructure ps2 = new PrintStructure();
                        es2.setRootPath(args[3]);
                        es2.setLevels(es2.getKorijenskiElement(), es2, brojacRazina);
                        controller.setModel(es2);
                        controller.updateView();
                        tc.setModel(es2);
                        es = es2;

                        break;
                    }
                }

            } while (!choice.equals("Q"));
            tc.terminateThread();
        }
    }
}
