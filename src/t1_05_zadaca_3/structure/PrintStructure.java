/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.structure;

import java.util.List;

/**
 *
 * @author Filip
 */
public class PrintStructure {

    public static final String ANSI_ESC = "\033[";

    //Zelena i žuta za dretve: 1;32m  i   1;33m
    
    private String ispisStrukture;

    public PrintStructure() {

    }

    /**
     * Metoda za ispis strukture elemenata sa Iteratorom
     *
     * @param e
     * @param es
     */
    public void printStructure(Element e, ElementStructure es) {

        for (Iterator iter = es.getIterator(); iter.hasNext(e);) {
            Element e1 = (Element) iter.next(e);
            String povlaka = razinaUSpace(e1.getRazina());
            if (e1.isDirektorij()) { //PLAVI
                ispisStrukture += ANSI_ESC + "32m" + povlaka + e1.toString() + "\n";
            } else if (e1.isDatoteka()) { //CRVENE
                ispisStrukture += ANSI_ESC + "31m" + povlaka + e1.toString() + "\n";
            }
            if (e1.isDirektorij() && e1.hasDjeca()) {

                printStructure(e1, es);
            }

        }
    }

    /**
     * Metoda koja prebacuje int razinu u broj blankova za ispis razina
     * strukture
     *
     * @param razina
     * @return
     */
    public String razinaUSpace(int razina) {
        String space = "";
        for (int i = 0; i < razina; i++) {
            space += "  ";
        }
        return space;
    }

    /**
     * Metoda vraća broj kreiranih direktorija u strukturi
     *
     * @param direktoriji
     * @return
     */
    public static int brKreiranihDirektorija(List<Element> direktoriji) {
        int brDir = direktoriji.size();
        return brDir;
    }

    /**
     * Metoda vraća broj kreiranih datoteka u strukturi
     *
     * @param datoteke
     * @return
     */
    public static int brKreiranihDatoteka(List<Element> datoteke) {
        int brDat = datoteke.size();
        return brDat;
    }

    //OPCIJA 1 u izborniku
    /**
     * Opcija br.1 u izborniku
     *
     * @param direktoriji
     * @param datoteke
     * @param velicinaStrukture
     * @return
     */
    public String MenuOption1(List<Element> direktoriji, List<Element> datoteke, String velicinaStrukture) {
        String vrati = "";
        vrati += "Menu Option 1:"
                + "\n" + ANSI_ESC + "32m" + "Broj kreiranih direktorija: " + brKreiranihDirektorija(direktoriji)
                + "\n" + ANSI_ESC + "31m" + "Broj kreiranih datoteka: " + brKreiranihDatoteka(datoteke)
                + "\n" + ANSI_ESC + "35m" + "Veličina cijele strukture: " + velicinaStrukture + " B";
        return vrati;
    }

    /**
     * Opcija br.2 u izborniku
     *
     * @param e
     * @param es
     * @return
     */
    public String MenuOption2(Element e, ElementStructure es) {
        String menu = "Menu Option 2:\n";

        ispisStrukture = "";
        printStructure(e, es);
        System.out.println("");
        return menu + ispisStrukture;
    }
}
