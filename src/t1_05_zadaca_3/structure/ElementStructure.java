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
    
    public void setRootPath(String path) {
        korijenskaPutanja = path;
        
        //test
        korijenskiElement = new Element(korijenskaPutanja, convertDate(korijenskaPutanja), elementSize(korijenskaPutanja), true, true, true, true);
        System.out.println(korijenskiElement.toString());
        //test
    }
    
    public static void createStructure() {
        struktura.clear();
        
        korijenskiElement = new Element(korijenskaPutanja, convertDate(korijenskaPutanja), elementSize(korijenskaPutanja), true, true, true, true);
        korijen.add(korijenskiElement);
        
        for (File f : korijenskiDirektorij.listFiles()) {
            if (f.isDirectory()) {
                //procitat naziv
                //provjerit jel ima djecu
//                direktoriji.add(new Element(korijenskaPutanja, korijenskaPutanja, velicina, false, true, true, true))
            } else if (f.isFile()) {
                
            }
        }
        
    }
    
    
    public static void addRecursive() {
        
    }
    
    //metoda za konvertiranje datuma
    public static String convertDate(String nazivDatDir) {
        File f = new File(nazivDatDir);
        long datum = f.lastModified();
        Date date = new Date(datum);
        Format format = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");

        return format.format(date);
    }
    
    //velicina direktorija ili datoteke
    public static long elementSize(String nazivElementa) {
        long velicina = 0;
        
        File direktorij = new File(nazivElementa);
        
        if (direktorij.isDirectory()) {
            velicina = directorySize(direktorij);
            return velicina;
        } else if (direktorij.isFile()) {
            velicina = fileSize(direktorij);
            return velicina;
        }
        return velicina;
    }

    
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
    
    public static long fileSize(File directory) {
        long length = 0;
        length = directory.length();
        return length;
    }
    
}
