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
    private long velicina;
    //vrste u strukturi: korijen, dat i dir
    private boolean korijen;
    private boolean datoteka;
    private boolean direktorij;
    //ima li korijen ili direktorij djecu, defaultno false za datoteke
    private boolean imaDjecu; 

    private List<Element> elementi;
    
    
    public Element(String naziv, String vrijemePromjene, long velicina, boolean korijen, boolean datoteka, boolean direktorij, boolean imaDjecu) {
        this.naziv = naziv;
        this.velicina = velicina;
        this.vrijemePromjene = vrijemePromjene;
        this.korijen = korijen;
        this.direktorij = direktorij;
        this.datoteka = datoteka;
        this.imaDjecu = imaDjecu;
        
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

    //prikaz podataka o objektu
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
        return (naziv + " " + vrijemePromjene + " " + velicina + " " + tip);
    }
    
    
    //METODA ZA KLONIRANJE, kasnije bude trebala za memento i jos jedna treba tamo gdi se bude pozivao memento
     public Object kloniranje() {
        Element element;
        element = new Element(this.naziv, this.vrijemePromjene, this.velicina, this.korijen, this.datoteka, this.direktorij, this.imaDjecu);
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

    public long getVelicina() {
        return velicina;
    }

    public void setVelicina(long velicina) {
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

    public boolean isImaDjecu() {
        return imaDjecu;
    }

    public void setImaDjecu(boolean imaDjecu) {
        this.imaDjecu = imaDjecu;
    }
}
