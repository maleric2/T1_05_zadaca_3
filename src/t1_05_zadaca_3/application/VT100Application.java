/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.application;

/**
 * Glavna klasa za VT100 Terminal
 * Singleton
 * @author Marko
 */
public class VT100Application {

    private static VT100Application instance;

    private VT100Application() {
        System.out.println("New VT1000 Terminal Application..");
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
 * Start metoda koja pokreće aplikaciju
 * U metodi se nalaze pozivi svih ostalih funkcionalnosti (poziva ispis izbornika i sl.)
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
        }

    }
}
