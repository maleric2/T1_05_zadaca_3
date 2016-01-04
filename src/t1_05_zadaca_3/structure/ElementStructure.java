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
public class ElementStructure implements Container {

    private static String korijenskaPutanja;
    private static File korijenskiDirektorij;
    private static Element korijenskiElement;
    private static Element strukturaElemenata;

    private static List<Element> direktoriji;
    private static List<Element> datoteke;

    private static String velicinaKorDir;

    public ElementStructure() {
        direktoriji = new ArrayList<>();
        datoteke = new ArrayList<>();
        //element u koji spremamo sve ostale uključujući i korijen tako da ne treba raditi s listama
        strukturaElemenata = new Element(null, null, null, null, 0, 0, false, false, false, true, false, -1);
    }

    /**
     * Postavljanje putanje za korijenski direktorij
     *
     * @param path
     */
    public void setRootPath(String path) {
        korijenskaPutanja = path;

        createStructure();
        velicinaKorDir = elementSize(korijenskiDirektorij);
    }

    /**
     * Metoda za kreiranje strukture stabla direktorija i datoteka
     */
    public static void createStructure() {
        strukturaElemenata.clearListOfElements();

        korijenskiDirektorij = new File(korijenskaPutanja);

        korijenskiElement = new Element(korijenskaPutanja, convertDate(korijenskiDirektorij), elementSize(korijenskiDirektorij), korijenskiDirektorij.toString(), generateHash(korijenskaPutanja), generateHash(korijenskaPutanja), true, false, true, true, true, 0);

        checkFileSystem(korijenskiDirektorij);

        // kreiranje strukture----------------------------------------------------------
        //dodavanje datoteka korijenu ako ih ima
        for (Element dat : datoteke) {
            if (dat.getHashRoditelja() == korijenskiElement.getHashNaziva()) {
                korijenskiElement.addElement(dat);
            }
        }

        //dodavanje direktorija u korijen ako ih ima
        for (Element dir : direktoriji) {
            if (dir.getHashRoditelja() == korijenskiElement.getHashNaziva()) {
                korijenskiElement.addElement(dir);
            }
        }

        //dodavanje datoteke direktoriju ako ih ima
        for (Element dir : direktoriji) {
            for (Element dat : datoteke) {
                if (dat.getRoditelj().equalsIgnoreCase(dir.getNaziv()) && dat.getHashRoditelja() == dir.getHashNaziva()) {
                    dir.addElement(dat);
                }
            }
        }

        //dodavanje direktorija u direktorije ako ih ima
        for (Element dir1 : direktoriji) {
            for (Element dir2 : direktoriji) {
                if (dir1.getHashRoditelja() == dir2.getHashNaziva()) {
                    dir2.addElement(dir1);
                }
            }
        }

        strukturaElemenata.addElement(korijenskiElement);
        //---------------------------------------------------------
    }

    /**
     * Rekurzivno pretraživanje strukture za datoteke i direktorije
     *
     * @param f
     */
    public static void checkFileSystem(File f) {
        String naziv = "";
        boolean djeca = false;

        for (File file : f.listFiles()) {
            if (file.isFile()) {
                naziv = file.getName();
                datoteke.add(new Element(naziv, convertDate(file), elementSize(file), getParentName(file), generateHash(file.getParentFile().getAbsolutePath()), generateHash(file.getAbsolutePath()), false, true, false, false, false, 0));
            } else if (file.isDirectory()) {
                naziv = file.getName();
                djeca = checkIfChildren(f);
                direktoriji.add(new Element(naziv, convertDate(file), elementSize(file), getParentName(file), generateHash(file.getParentFile().getAbsolutePath()), generateHash(file.getAbsolutePath()), false, false, true, djeca, true, 0));
                checkFileSystem(file);
            }
        }
    }

    /**
     * Metoda za generiranje hash-a imena roditelja pošto ne smijemo u strukturi
     * pamtiti apsolutne putanje mapa
     *
     * @param roditelj
     * @return
     */
    public static int generateHash(String roditelj) {
        int hash = 19;
        for (int i = 0; i < roditelj.length(); i++) {
            hash = (hash << 15) - hash + roditelj.charAt(i);
        }
        return hash;
    }

    /**
     * Dobivanje imena roditelja bez pune putanje
     *
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
     *
     * @param f
     * @return
     */
    public static boolean checkIfChildren(File f) {
        if (f.list().length > 0) {
            return true;
        } else {
            return false;
        }
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

    /**
     * Metoda koja dodijeljuje razinu elementu u Compositeu
     *
     * @param e
     * @param es
     * @param brojacRazina
     */
    public void setLevels(Element e, ElementStructure es, int brojacRazina) {
        e.setRazina(brojacRazina);
        brojacRazina++;
        for (Element e1 : e.getElementi()) {
            e1.setRazina(brojacRazina);
            if (e1.hasDjeca()) {
                setLevels(e1, es, brojacRazina);
            }
        }
    }

    @Override
    public Iterator getIterator() {
        return new StructureIterator();
    }

    /**
     * Klasa unutarnjeg iteratora
     */
    private class StructureIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext(Element e) {
            if (index < e.getElementi().size()) {
                return true;
            }
            return false;
        }

        @Override
        public Object next(Element e) {
            if (this.hasNext(e)) {
                return e.getElement(index++);
            }
            return null;
        }

    }

    public String getKorijenskaPutanja() {
        return korijenskaPutanja;
    }

    public void setKorijenskaPutanja(String aKorijenskaPutanja) {
        korijenskaPutanja = aKorijenskaPutanja;
    }

    public File getKorijenskiDirektorij() {
        return korijenskiDirektorij;
    }

    public void setKorijenskiDirektorij(File aKorijenskiDirektorij) {
        korijenskiDirektorij = aKorijenskiDirektorij;
    }

    public Element getKorijenskiElement() {
        return korijenskiElement;
    }

    public void setKorijenskiElement(Element aKorijenskiElement) {
        korijenskiElement = aKorijenskiElement;
    }

    public List<Element> getDirektoriji() {
        return direktoriji;
    }

    public void setDirektoriji(List<Element> aDirektoriji) {
        direktoriji = aDirektoriji;
    }

    public List<Element> getDatoteke() {
        return datoteke;
    }

    public void setDatoteke(List<Element> aDatoteke) {
        datoteke = aDatoteke;
    }

    public Element getStrukturaElemenata() {
        return strukturaElemenata;
    }

    public void setStrukturaElemenata(Element aStrukturaElemenata) {
        strukturaElemenata = aStrukturaElemenata;
    }

    public String getVelicinaKorDir() {
        return velicinaKorDir;
    }

    public void setVelicinaKorDir(String aVelicinaKorDir) {
        velicinaKorDir = aVelicinaKorDir;
    }
}
