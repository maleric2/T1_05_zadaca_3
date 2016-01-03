/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.structure;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Filip
 */
public class Element {

    private String naziv;
    private String vrijemePromjene;
    private String velicina;
    //vrste u strukturi: korijen, dat i dir
    private boolean korijen;
    private boolean datoteka;
    private boolean direktorij;
    //ima li korijen ili direktorij djecu, defaultno false za datoteke
    private boolean djeca;
    private boolean parent;
    private String roditelj;
    private int hashRoditeljskePutanje;
    private int hashPutanjeElementa;
    private int razina;

    private List<Element> elementi;

    public Element(String naziv, String vrijemePromjene, String velicina, String roditelj, int hashRoditelja, int hashNaziva, boolean korijen, boolean datoteka, boolean direktorij, boolean djeca, boolean parent, int razina) {
        this.naziv = naziv;
        this.velicina = velicina;
        this.vrijemePromjene = vrijemePromjene;
        this.korijen = korijen;
        this.direktorij = direktorij;
        this.datoteka = datoteka;
        this.djeca = djeca;
        this.roditelj = roditelj;
        this.hashRoditeljskePutanje = hashRoditelja;
        this.hashPutanjeElementa = hashNaziva;
        this.parent = parent;
        this.razina = razina;

        elementi = new ArrayList<>();
    }

    //operacija za composite
    public void addElement(Element e) {
        elementi.add(e);
    }

    public void removeElement(Element e) {
        elementi.remove(e);
    }

    public void clearListOfElements() {
        this.elementi.clear();
    }

    public List<Element> getElementi() {
        return elementi;
    }

    public Element getElement(int i) {
        return elementi.get(i);
    }

    public Element getElementByHash(ElementStructure es, Element e, int hash) {

        return e;
    }

    /**
     * Override Object metode za prikaz
     *
     * @return
     */
    @Override
    public String toString() {
        String tip = "";
        if (korijen) {
            tip = "-> korijen";
        } else if (datoteka) {
            tip = "-> datoteka";
        } else if (direktorij) {
            tip = "-> direktorij";
        }
//        return (naziv + " Veličina: " + velicina + " Datum: " + vrijemePromjene + " Hash: " + hashPutanjeElementa + " " + tip + " ->\t Roditelj:" + roditelj + " -> Hash roditelja: " + hashRoditeljskePutanje);
        return (naziv + "\t" + vrijemePromjene + "\t" + velicina + " B");
    }

    /**
     * Metoda za kloniranje elemenata, biti će potrebna za Memento
     *
     * @return
     */
    public Object kloniranje() {
        Element element;
        element = new Element(this.naziv, this.vrijemePromjene, this.velicina, this.roditelj, this.hashRoditeljskePutanje, this.hashPutanjeElementa, this.korijen, this.datoteka, this.direktorij, this.djeca, this.parent, this.razina);
        return element;
    }

    //GETTERI I SETTERI
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getVrijemePromjene() {
        return vrijemePromjene;
    }

    public void setVrijemePromjene(String vrijemePromjene) {
        this.vrijemePromjene = vrijemePromjene;
    }

    public String getVelicina() {
        return velicina;
    }

    public void setVelicina(String velicina) {
        this.velicina = velicina;
    }

    public boolean isKorijen() {
        return korijen;
    }

    public void setKorijen(boolean korijen) {
        this.korijen = korijen;
    }

    public boolean isDatoteka() {
        return datoteka;
    }

    public void setDatoteka(boolean datoteka) {
        this.datoteka = datoteka;
    }

    public boolean isDirektorij() {
        return direktorij;
    }

    public void setDirektorij(boolean direktorij) {
        this.direktorij = direktorij;
    }

    public boolean hasDjeca() {
        return djeca;
    }

    public void setDjeca(boolean djeca) {
        this.djeca = djeca;
    }

    public String getRoditelj() {
        return roditelj;
    }

    public void setRoditelj(String roditelj) {
        this.roditelj = roditelj;
    }

    public int getHashRoditelja() {
        return hashRoditeljskePutanje;
    }

    public void setHashRoditelja(int hashRoditelja) {
        this.hashRoditeljskePutanje = hashRoditelja;
    }

    public int getHashNaziva() {
        return hashPutanjeElementa;
    }

    public void setHashNaziva(int hashNaziva) {
        this.hashPutanjeElementa = hashNaziva;
    }

    public boolean hasParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public int getRazina() {
        return razina;
    }

    public void setRazina(int dubina) {
        this.razina = dubina;
    }

}
