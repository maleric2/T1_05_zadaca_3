/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.application;

import java.util.List;
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

            String tekstZaPrviProzor = ps.MenuOption1(es.getDirektoriji(), es.getDatoteke(), es.getVelicinaKorDir());
            String tekstZaDrugiProzor = ps.MenuOption2(es.getStrukturaElemenata(), es);

            //Ispis u terminal
            //TODO Makni hardkodiranje            
            int sirina = 100;
            int visina = 50;

            //ispis vertikalnog okvira
            /*IspisV ispis = new IspisV(visina);
            ispis.nacrtajGranice(sirina, visina);

            //raskomadam stringove u redove potrebne sirine
            ObradaTeksta ot = new ObradaTeksta();
            //za vertikalne je sirina/2
            List<String> redoviPrviProzor = ot.tekstPoRedovima(tekstZaPrviProzor, sirina/2, 0);
            List<String> redoviDrugiProzor = ot.tekstPoRedovima(tekstZaDrugiProzor, sirina/2, 0);

            //ispišem redove u terminalu
            ispis.ubaciTextLijevo(redoviDrugiProzor, sirina, visina);
            ispis.ubaciTextDesno(redoviDrugiProzor, sirina, visina);*/
            
            //ispis okomitog okvira
            
             IspisO ispis = new IspisO(visina);
             ispis.nacrtajGranice(sirina, visina);

             //raskomadam stringove u redove potrebne sirine
             ObradaTeksta ot = new ObradaTeksta();
            //za okomite je sirina - 2
             List<String> redoviPrviProzor = ot.tekstPoRedovima(tekstZaPrviProzor, sirina - 2, 2);
             List<String> redoviDrugiProzor = ot.tekstPoRedovima(tekstZaDrugiProzor, sirina - 2, 2);

             //ispišem redove u terminalu
             ispis.ubaciTextGore(redoviPrviProzor, sirina, visina);
             ispis.ubaciTextDolje(redoviDrugiProzor, sirina, visina);
             
             
            
            ispis.prebaciNaDno(sirina, visina + 1);
            //spremanje mementom
            //izvor.setState(es);
            //save.add(izvor.saveStateToMemento());
        }

    }
}
