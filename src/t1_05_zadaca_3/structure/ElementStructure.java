/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.structure;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Filip
 */
public class ElementStructure {
    
    private static String korijenskaPutanja;
    private static File korijenskiDirektorij;
    private static Element korijenskiElement;
    
    private static List<Element> korijen;
    private static List<Element> direktoriji;
    private static List<Element> datoteke;
    
    private static List<Element> struktura;
    
    public ElementStructure() {
        korijen = new ArrayList<>();
        direktoriji = new ArrayList<>();
        datoteke = new ArrayList<>();
        struktura = new ArrayList<>();
    }
    
    public void test() {
        
    }
    
    /**
     * Postavljanje putanje za korijenski direktorij
     * @param path 
     */
    public void setRootPath(String path) {
        korijenskaPutanja = path;
       
        //test
        createStructure();
        //test 
    }
    
    /**
     * Metoda za kreiranje strukture stabla direktorija i datoteka
     */
    public static void createStructure() {
        struktura.clear();
        
        korijenskiDirektorij = new File(korijenskaPutanja);
        
        korijenskiElement = new Element(korijenskaPutanja, convertDate(korijenskiDirektorij), elementSize(korijenskiDirektorij), korijenskiDirektorij.toString(), true, true, true, true);
        
        System.out.println("---------------KORIJEN---------------------");
        System.out.println(korijenskiElement.toString());
        System.out.println("-------------------------------------------");
        
        
        checkFileSystem(korijenskiDirektorij);
        System.out.println("---------------DIREKTORIJI---------------------");
        for (Element e : direktoriji) {
            System.out.println(e.toString());
        }
        System.out.println("-----------------------------------------------");
        
        System.out.println("");
        System.out.println("---------------DATOTEKE-----------------------");
        for (Element e : datoteke) {
            System.out.println(e.toString());
        }
        System.out.println("----------------------------------------------"); 
        
        
        //struktura
        String s[] = korijenskiElement.getNaziv().split("\\\\");
        String nazivKorijena = s[s.length-1];
        
        for (Element dat : datoteke)
            if (dat.getRoditelj().equalsIgnoreCase(nazivKorijena))
                    korijenskiElement.addElement(dat);
        for (Element dir : direktoriji)
            if (dir.getRoditelj().equalsIgnoreCase(nazivKorijena))
                    korijenskiElement.addElement(dir);
        
        for (Element dir : direktoriji)
            for (Element dat : datoteke)
                if (dat.getRoditelj().equalsIgnoreCase(dir.getNaziv()))
                    dir.addElement(dat);
            
        for (Element dir1 : direktoriji)
            for (Element dir2 : direktoriji)
                if (dir1.getRoditelj().equalsIgnoreCase(dir2.getNaziv())) 
                    dir2.addElement(dir1);

        struktura.add(korijenskiElement);
        //---------------------------------------------------------
        
        
        
        //testni ispis -> zamijeniti Iteratorom, rekurzijom
        System.out.println("\n--------------STRUKTURA-----------------------");
        
        for (Element e : struktura) {
            System.out.println(e.toString());
            for (Element e1 : e.getElementi()) {
                System.out.println(e1.toString());
                for (Element e2 : e1.getElementi()) {
                    System.out.println(e2.toString());
                    for (Element e3 : e2.getElementi()) {
                        System.out.println(e3.toString());
                    }
                }
            }
        }
        
        
    }
    
    /**
     * Rekurzivno pretraživanje strukture za datoteke i direktorije
     * @param f 
     */
    public static void checkFileSystem(File f) {
        String naziv = "";
        boolean djeca = false;
        
        for (File file : f.listFiles()) {
            if (file.isFile()) {
                naziv = file.getName();
                datoteke.add(new Element(naziv, convertDate(file), elementSize(file), getParentName(file), false, true, false, false));
            } else if (file.isDirectory()) {
                naziv = file.getName();
                djeca = checkIfChildren(f);
                direktoriji.add(new Element(naziv, convertDate(file), elementSize(file), getParentName(file), false, false, true, djeca));
                checkFileSystem(file);
            }
        }
    }
    
    /**
     * Dobivanje imena roditelja bez pune putanje
     * @param f
     * @return 
     */
    public static String getParentName(File f) {
        File dir = new File(f.getParent());
        String s = dir.getName();
        return s;
    }
    
    /**
     * Meotda koja provjerava da li direktorij ima djecu
     * @param f
     * @return 
     */
    public static boolean checkIfChildren(File f) {
        if (f.list().length > 0)
            return true;
        else
            return false;
    }
    
    /**
     * Metoda za formatiranje datuma
     * @param f
     * @return 
     */
    public static String convertDate(File f) {
//        File f = new File(nazivDatDir);
        long datum = f.lastModified();
        Date date = new Date(datum);
        Format format = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");

        return format.format(date);
    }
    
    /**
     * Metoda kojoj proslijeđujemo naziv elementa iz datotečne strukture 
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
     * Metoda za računanje veličine direktorija
     * @param directory
     * @return 
     */
    public static long directorySize(File directory) {
        long length = 0;
        for (File file : directory.listFiles()) {
            
            if (file.isFile())
                length += file.length();
            else
                length += directorySize(file);
        }
        return length;
    }
    
    /**
     * Metoda za računanje veličine datoteke
     * @param directory
     * @return 
     */
    public static long fileSize(File directory) {
        long length = 0;
        length = directory.length();
        return length;
    }
    
    /**
     * Foramtirana velicina u bajtovima
     * @param broj
     * @return 
     */
    public static String formatiranaVelicina(long broj) {
        String velicina = String.format("%,3d", broj);
        return velicina;
    }
    
    /**
     * Metoda vraća broj kreiranih direktorija u strukturi
     * @param direktoriji
     * @return 
     */
    public static int brKreiranihDirektorija(List<Element> direktoriji) {
        int brDir = direktoriji.size();
        return brDir;
    }
    
    /**
     * Metoda vraća broj kreiranih datoteka u strukturi
     * @param datoteke
     * @return 
     */
    public static int brKreiranihDatoteka(List<Element> datoteke) {
        int brDat = datoteke.size();
        return brDat;
    }
}
