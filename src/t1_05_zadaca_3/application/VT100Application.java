/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.application;

import t1_05_zadaca_3.structure.CareTaker;
import t1_05_zadaca_3.structure.ElementStructure;
import t1_05_zadaca_3.structure.Originator;
import t1_05_zadaca_3.structure.PrintStructure;
import t1_05_zadaca_3.windows.WindowFactory;

/**
 * Glavna klasa za VT100 Terminal Singleton
 *
 * @author Marko
 */
public class VT100Application {

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
            ps.printStructure(es.getStrukturaElemenata(), es);
            
            //prikaz prozora
            //FRAMEOVI
            System.out.println("---------------------------------------------\n");
            WindowFactory ff = new WindowFactory();
            ff.createWindow(args);

            /*
             //spremanje mementom
             izvor.setState(es);
             save.add(izvor.saveStateToMemento());
             */
        }

    }
}
