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
import t1_05_zadaca_3.terminal.ObradaTeksta;
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

            //Ispis u terminal
            //TODO Makni hardkodiranje
            int sirina = 80;
            int visina = 30;
            
            //ispis okomitog okvira
            IspisO ispis = new IspisO(visina);
            ispis.nacrtajGranice(sirina, visina);

            //potrebni stringovi za svaki prozor
            String tekstZaPrviProzor = "Assure polite his really and others figure though. Day age advantages end sufficient eat expression travelling. Of on am father by agreed supply rather either. Own handsome delicate its property mistress her end appetite. Mean are sons too sold nor said. Son share three men power boy you. Now merits wonder effect garret own. Oh he decisively impression attachment friendship so if everything. Whose her enjoy chief new young. Felicity if ye required likewise so doubtful. On so attention necessary at by provision otherwise existence direction. Unpleasing up announcing unpleasant themselves oh do on. Way advantage age led listening belonging supposing.";
            String tekstZaDrugiProzor = "Assure polite his really and others figure though. Day age advantages end sufficient eat expression travelling. Of on am father by agreed supply rather either. Own handsome delicate its property mistress her end appetite. Mean are sons too sold nor said. Son share three men power boy you. Now merits wonder effect garret own. Oh he decisively impression attachment friendship so if everything. Whose her enjoy chief new young. Felicity if ye required likewise so doubtful. On so attention necessary at by provision otherwise existence direction. Unpleasing up announcing unpleasant themselves oh do on. Way advantage age led listening belonging supposing.";

            //raskomadam stringove u redove potrebne sirine
            ObradaTeksta ot = new ObradaTeksta();
            List<String> redoviPrviProzor = ot.tekstPoRedovima(tekstZaPrviProzor, sirina - 2);
            List<String> redoviDrugiProzor = ot.tekstPoRedovima(tekstZaDrugiProzor, sirina - 2);

            //ispišem redove u terminalu
            ispis.ubaciTextGore(redoviPrviProzor, sirina, visina);
            ispis.ubaciTextGore(redoviPrviProzor, sirina, visina);
            ispis.ubaciTextDolje(redoviDrugiProzor, sirina, visina);
            ispis.ubaciTextDolje(redoviDrugiProzor, sirina, visina);
            ispis.prebaciNaDno(sirina, visina + 1);

            //prikaz prozora
            //FRAMEOVI
            /*System.out.println("---------------------------------------------\n");
             WindowFactory ff = new WindowFactory();
             ff.createWindow(args);*/
            /*
             //spremanje mementom
             izvor.setState(es);
             save.add(izvor.saveStateToMemento());
             */
        }

    }
}
