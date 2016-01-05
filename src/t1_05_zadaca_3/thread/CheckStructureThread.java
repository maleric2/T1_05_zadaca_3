/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.thread;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import t1_05_zadaca_3.structure.Element;

/**
 *
 * @author Filip
 */
public class CheckStructureThread implements Runnable {

    private Thread dretvaZaProvjeru;
    private boolean izvrsavanje;
    private int intervalUSec;

    private File korijenskiDir;
    private List<Element> sviElementi;
    private List<File> sviDirektorijiIDatoteke;
   
    
    public CheckStructureThread(File korijenskiDir, List<Element> direktoriji, List<Element> datoteke) {
        this.korijenskiDir = korijenskiDir;
        
        sviDirektorijiIDatoteke = new ArrayList<>();
        sviElementi = new ArrayList();
        
        for (Element e : direktoriji)
            sviElementi.add(e);

        for (Element e : datoteke)
            sviElementi.add(e);
    }

    //BUSINESS LOGIC
    @Override
    public void run() {
        try {
            while (izvrsavanje) {
                sviDirektorijiIDatoteke.clear();
                readAllFiles(korijenskiDir);
                System.out.println(vrijemeProvjere());
                provjeraStrukture(sviDirektorijiIDatoteke, sviElementi);
                dretvaZaProvjeru.sleep(intervalUSec * 1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(CheckStructureThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //metoda za pokretanje dretve
    public void startThread() {
        if (dretvaZaProvjeru == null) {
            dretvaZaProvjeru = new Thread(this);
            dretvaZaProvjeru.start();
        }
    }

    public void readAllFiles(File f) {
        for (File file : f.listFiles()) {
            sviDirektorijiIDatoteke.add(file);
            if (file.isDirectory()) {
                readAllFiles(file);
            }
        }
    }

    public void test() {
        for (File f : sviDirektorijiIDatoteke) {
            System.out.println(f.getName());
        }
    }

    public String provjeraStrukture(List<File> sviDirDat, List<Element> sviElementi) {
        String poruka = "";

        for (Element e : sviElementi) {
            for (File f : sviDirDat) {
                if (e.getNaziv().equalsIgnoreCase(f.getName()) && e.getHashNaziva() == generateHash(f.getAbsolutePath())) {
                    System.out.println("Element -" + e.getNaziv() + "- postoji u datotečnom sustavu");
                    
                    
                } 
                //za sve ostale rezultate kad nije jednako -> NE VALJA!
//                    else if (!e.getNaziv().equalsIgnoreCase(f.getName())) {
//                    System.out.println("Element " + e.getNaziv() + " NE postoji u datotečnom sustavu");
//                }
            }
        }

        return poruka;
    }

    public static String vrijemeProvjere() {
        Date date = new Date();
        Format format = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
        
        return format.format(date);
    }

    //SETTERI i GETTERI
    public boolean isIzvrsavanje() {
        return izvrsavanje;
    }

    public void setIzvrsavanje(boolean izvrsavanje) {
        this.izvrsavanje = izvrsavanje;
    }

    public int getIntervalUSec() {
        return intervalUSec;
    }

    public void setIntervalUSec(int intervalUSec) {
        this.intervalUSec = intervalUSec;
    }

    //Metode iz ElementStructure
    /**
     * Metoda za generiranje hash-a imena roditelja pošto ne smijemo u strukturi
     * pamtiti apsolutne putanje mapa
     *
     * @param putanja
     * @return
     */
    public static int generateHash(String putanja) {
        int hash = 19;
        for (int i = 0; i < putanja.length(); i++) {
            hash = (hash << 15) - hash + putanja.charAt(i);
        }
        return hash;
    }

    /**
     * Metoda za formatiranje datuma
     *
     * @param f
     * @return
     */
    public static String convertDate(File f) {
        long datum = f.lastModified();
        Date date = new Date(datum);
        Format format = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");

        return format.format(date);
    }

    /**
     * Metoda kojoj proslijeđujemo naziv elementa iz datotečne strukture
     *
     * @param f
     * @return veličina elementa
     */
    public static String elementSize(File f) {
        long velicina = 0;

        if (f.isDirectory()) {
            velicina = directorySize(f);
            return formatiranaVelicina(velicina);
        } else if (f.isFile()) {
            velicina = fileSize(f);
            return formatiranaVelicina(velicina);
        }
        return formatiranaVelicina(velicina);
    }

    /**
     * Metoda za računanje veličine direktorija koristi se u metodi
     * elementSize()
     *
     * @param directory
     * @return
     */
    public static long directorySize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {

            if (file.isFile()) {
                length += file.length();
            } else {
                length += directorySize(file);
            }
        }
        return length;
    }

    /**
     * Metoda za računanje veličine datoteke koristi se u metodi elementSize()
     *
     * @param directory
     * @return
     */
    public static long fileSize(File directory) {
        long length = 0;
        length = directory.length();
        return length;
    }

    /**
     * Foramtirana veličina u bajtovima
     *
     * @param broj
     * @return
     */
    public static String formatiranaVelicina(long broj) {
        String velicina = String.format("%,3d", broj);
        return velicina;
    }
}
