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
import t1_05_zadaca_3.terminal.IspisO;
import static t1_05_zadaca_3.terminal.IspisO.ANSI_ESC;
import t1_05_zadaca_3.terminal.IspisV;
import t1_05_zadaca_3.terminal.ObradaTeksta;

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

            //TODO Ne deklarirat i inicijalizirat oboje
            IspisO ispisO = new IspisO(visina);
            IspisV ispisV = new IspisV();

            ObradaTeksta ot = new ObradaTeksta();

            if (args[2].equals("O")) {
                List<String> redoviPrviProzor = ot.tekstPoRedovima(sadrzajStrukture, sirina - 2, 2);
                List<String> redoviDrugiProzor = ot.tekstPoRedovima(brojDirDat, sirina - 2, 2);
                ispisO.ubaciTextGore(redoviPrviProzor, sirina, visina);
                ispisO.ubaciTextDolje(redoviDrugiProzor, sirina, visina);
                ispisO.prebaciNaDno(sirina, visina + 1);
            } else if (args[2].equals("V")) {
                List<String> redoviPrviProzor = ot.tekstPoRedovima(sadrzajStrukture, sirina / 2, 0);
                List<String> redoviDrugiProzor = ot.tekstPoRedovima(brojDirDat, sirina / 2, 0);
                ispisV.ubaciTextLijevo(redoviPrviProzor, sirina, visina);
                ispisV.ubaciTextDesno(redoviDrugiProzor, sirina, visina);
                ispisV.prebaciNaDno(sirina, visina + 1);
            }

            //početni ispis granica i izbornika
            if (args[2].equals("O")) {
                ispisO.nacrtajGranice(sirina, visina);
                ispisO.prebaciNaDno(sirina, visina + 1);
            } else if (args[2].equals("V")) {
                ispisV.nacrtajGranice(sirina, visina);
                ispisV.prebaciNaDno(sirina, visina + 1);
            }

            int odabir = 0;
            while (true) {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                try {
                    odabir = Integer.parseInt(br.readLine());
                } catch (IOException ex) {
                    Logger.getLogger(VT100Application.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (odabir == 1) {
                    if (args[2].equals("O")) {
                        //raskomadam stringove u redove potrebne sirine
                        //za okomite je sirina - 2
                        List<String> redoviPrviProzor = ot.tekstPoRedovima(brojDirDat, sirina - 2, 2);
                        ispisO.ubaciTextGore(redoviPrviProzor, sirina, visina);
                        ispisO.prebaciNaDno(sirina, visina + 1);
                    } else if (args[2].equals("V")) {
                        //za vertikalne je sirina/2
                        List<String> redoviPrviProzor = ot.tekstPoRedovima(brojDirDat, sirina / 2, 0);
                        ispisV.ubaciTextLijevo(redoviPrviProzor, sirina, visina);
                        ispisV.prebaciNaDno(sirina, visina + 1);
                    }
                } else if (odabir == 2) {
                    if (args[2].equals("O")) {
                        //raskomadam stringove u redove potrebne sirine;
                        //za okomite je sirina - 2
                        List<String> redoviDrugiProzor = ot.tekstPoRedovima(sadrzajStrukture, sirina - 2, 2);
                        ispisO.ubaciTextGore(redoviDrugiProzor, sirina, visina);
                        //ispisO.ubaciTextGore(redoviDrugiProzor, sirina, visina);
                        ispisO.prebaciNaDno(sirina, visina + 1);
                    } else if (args[2].equals("V")) {
                        List<String> redoviPrviProzor = ot.tekstPoRedovima(sadrzajStrukture, sirina / 2, 0);
                        ispisV.ubaciTextLijevo(redoviPrviProzor, sirina, visina);
                        ispisV.prebaciNaDno(sirina, visina + 1);
                    }
                } else if (odabir == 7) {
                    //OVO SPREMA POCENO STANJE I ISPISUJE GA OVO JE SAMO TEST MEMENTA
                    //uhvatim pocetno stanje iz mementa
                    izvor.getStateFromMemento(save.get(0));
                    //spremim to stanje u vratiStanje
                    ElementStructure vratiStanje = izvor.getState();

                    //postavljanje strukture na staru
                    es = vratiStanje;

                    //ispis strukture koja je vraćena
                    String staroStanje = ps.MenuOption2(vratiStanje.getStrukturaElemenata(), vratiStanje);
                    if (args[2].equals("O")) {
                        List<String> stariRedovi = ot.tekstPoRedovima(staroStanje, sirina - 2, 2);
                        //TODO ubačeno od jedan od okvira samo za provjeru
                        ispisO.ubaciTextDolje(stariRedovi, sirina, visina);
                        ispisO.prebaciNaDno(sirina, visina + 1);
                    } else if (args[2].equals("V")) {
                        List<String> stariRedovi = ot.tekstPoRedovima(staroStanje, sirina / 2, 0);
                        ispisV.ubaciTextDesno(stariRedovi, sirina, visina);
                        ispisV.prebaciNaDno(sirina, visina + 1);
                    }
                } else if (odabir == 8) {
                    //PONOVNO UCITAVANJE STRUKTURE NA ZADANOM DIREKTORIJU

                    ElementStructure es2 = new ElementStructure();
                    PrintStructure ps2 = new PrintStructure();
                    es2.setRootPath(args[3]);
                    es2.setLevels(es2.getKorijenskiElement(), es2, brojacRazina);

                    brojDirDat = ps.MenuOption1(es2.getDirektoriji(), es2.getDatoteke(), es2.getVelicinaKorDir());
                    sadrzajStrukture = ps.MenuOption2(es2.getStrukturaElemenata(), es2);

                    if (args[2].equals("O")) {
                        List<String> redoviPrviProzor = ot.tekstPoRedovima(sadrzajStrukture, sirina - 2, 2);
                        List<String> redoviDrugiProzor = ot.tekstPoRedovima(brojDirDat, sirina - 2, 2);
                        ispisO.ubaciTextGore(redoviPrviProzor, sirina, visina);
                        ispisO.ubaciTextDolje(redoviDrugiProzor, sirina, visina);
                        ispisO.prebaciNaDno(sirina, visina + 1);
                    } else if (args[2].equals("V")) {
                        List<String> redoviPrviProzor = ot.tekstPoRedovima(sadrzajStrukture, sirina / 2, 0);
                        List<String> redoviDrugiProzor = ot.tekstPoRedovima(brojDirDat, sirina / 2, 0);
                        ispisV.ubaciTextLijevo(redoviPrviProzor, sirina, visina);
                        ispisV.ubaciTextDesno(redoviDrugiProzor, sirina, visina);
                        ispisV.prebaciNaDno(sirina, visina + 1);
                    }
                    
                    es = es2;
                    ps = ps2;

                } else if (odabir == 0) {
                    break;
                } else {
                    break;

                }
            }

        }

    }
}
