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
import t1_05_zadaca_3.option9.Layer0Main;
import t1_05_zadaca_3.structure.CareTaker;
import t1_05_zadaca_3.structure.Element;
import t1_05_zadaca_3.structure.ElementStructure;
import t1_05_zadaca_3.structure.Originator;
import t1_05_zadaca_3.structure.PrintStructure;
import t1_05_zadaca_3.terminal.Drawer;
import t1_05_zadaca_3.terminal.IspisO;
import static t1_05_zadaca_3.terminal.IspisO.ANSI_ESC;
import t1_05_zadaca_3.terminal.IspisV;
import t1_05_zadaca_3.terminal.ObradaTeksta;
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

            //prosljedit instancu careTaker-a pa da vrati stanja
            ThreadController tc = new ThreadController(drawer, es, interval);
            tc.setSaveSetup(izvor, save);

            controller.updateViewStructure();
            controller.updateViewStats();
            do {
                //drawer.clear();
                tc.updateViewMenu();
                choice = menu.getChoice();
                String[] choice2 = choice.split(" ");
                switch (choice2[0]) {
                    case "1": {
                        //Prikaze podatke o broju direktorija/datoteka i velicini
                        controller.updateViewStatsInWindow1();
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
                            String date = structure.getVrijemeKreiranja();
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
                                        drawer.drawWindow1("Pronasao sam stanje " + setNewState + "\n");
                                        t1_05_zadaca_3.structure.Memento memento = VT100Application.save.get(i);
                                        ElementStructure structure = memento.getState();
                                        izvor.setState(structure); //TODO stanja izdvojit u nekoj strukturi pa da se ovo automatski savea sve
                                        controller.setModel(structure);
                                        tc.setModel(structure);
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
                        if (choice2.length < 2) {
                            drawer.drawWindow1("Za opciju 7 je potrebno unijeti redni broj za usporedbu\n");
                        } else {
                            int redniBroj;
                            try {
                                redniBroj = Integer.parseInt(choice2[1]);
                                drawer.drawWindow1("Tražim stanje...\n");
                                boolean find = false;
                                for (int i = 0; i < VT100Application.save.vratiVelicinu(); i++) {
                                    if (redniBroj == i) {
                                        drawer.drawWindow1("Pronasao sam stanje " + redniBroj + "\n");
                                        t1_05_zadaca_3.structure.Memento memento = VT100Application.save.get(i);
                                        ElementStructure structure = memento.getState();
                                        drawer.drawWindow1("Dohvaćeno stanje sa rednim brojem + " + redniBroj + "\n");
                                        find = true;

                                        //TODO Usporedi dohvaćeno stanje structure sa trenutnim stanjem es                    
                                        drawer.drawWindow1(structure.usporedbaStrukture(structure.getSviElementi(), es.getSviElementi()));

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
                    case "8": {
                        //ponovno učitavanje strukture na zadanom direktoriju
                        ElementStructure es2 = new ElementStructure();
                        PrintStructure ps2 = new PrintStructure();
                        es2.setRootPath(args[3]);
                        es2.setLevels(es2.getKorijenskiElement(), es2, brojacRazina);
                        controller.setModel(es2);
                        controller.updateViewStructure();
                        controller.updateViewStats();
                        izvor.setState(es2);
                        save.clear();
                        save.add(izvor.saveStateToMemento());
                        tc.setModel(es2);
                        es = es2;
                        break;
                    }
                    case "9": {
                        //Layers
                        //TODO: s visitorom dohvatiti sve elemente u listu u Layer0Main
                        Layer0Main l0 = new Layer0Main(args);
//                        String s = "";
//                        for (int i = 0; i < es.getSviElementi().size(); i++) {
//                            s += " " + i;
//                        }
//                        drawer.drawWindow2(s);// ima ih 18

                        if (choice2.length < 2) {
                            int i = 0;
                            String s = "Za opciju 9 potrebno je unijeti redni broj elementa za kreiranje uz 9\n"
                                    + "Odaberite jedan od elemenata:\n";
                            for (Element e : es.getSviElementi()) {
                                s += i + " " + e.toString() + "\n";
                                i++;
                            }
                            drawer.drawWindow1(s);
                        } else {
                            int redniBroj;
                            try {
                                redniBroj = Integer.parseInt(choice2[1]);
                                drawer.drawWindow1("Tražim element...\n");
                                boolean find = false;
                                for (int i = 0; i < es.getSviElementi().size(); i++) {
                                    if (redniBroj == i) {
                                        drawer.drawWindow1("Pronasao sam element " + redniBroj + "\n");
                                        Element e = es.getSviElementi().get(i);
                                        l0.setElement(e);
                                        l0.setLayer();
                                        drawer.drawWindow1("Dohvaćen element sa rednim brojem " + redniBroj + "\n");
                                        find = true;

                                        drawer.drawWindow1(l0.ispisiKreiranje(es));

                                    }
                                }
                                if (!find) {
                                    drawer.drawWindow1("Element nije pronadjen\n");
                                }
                            } catch (Exception ex) {
                                System.out.println("Pogrešan parametar " + choice2[1] + "! Message: " + ex.getMessage());
                            }

                            
//                        StructurePart struktura = new ElementStructure();
//                        struktura.accept(new StructurePartDoVisitor(), es.getKorijenskiElement(), es);
//                        
//                        es.accept(new StructurePartDoVisitor(), es.getKorijenskiElement(), es);
//                        int i = 0;
//                        String s = "Odaberite broj elementa koji želite pretvoriti u dat/dir:\n";
//                        for (Element e: es.getSviElementi()) {
//                            s += i + " " + e.toString() + "\n";
//                            i++;
//                        }
//                        drawer.drawWindow2(s);
                        }
                        break;
                    }
                }

            } while (!choice.equals("Q"));
            tc.terminateThread();
        }
    }
}
