/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.option9;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import t1_05_zadaca_3.structure.Element;

/**
 *
 * @author Filip
 */
public class Layer1File implements LayerInterface {
    //Kreira Datoteke

    private Element e;
    private String putanja;

    public Layer1File(Element e, String putanja) {
        this.e = e;
        this.putanja = putanja;
    }

    @Override
    public boolean create() {
        String putanjaNazivDat = putanja + e.getNaziv();
        File datoteka = new File(putanjaNazivDat);
        if (e.isDatoteka()) {
            try {
                datoteka.createNewFile();
                return true;
            } catch (IOException ex) {
                Logger.getLogger(Layer1File.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        else {
            return false;
        }
        return false;
    }

    @Override
    public String ispis() {
        if (create()) {
            return "Kreirana je datoteka " + e.getNaziv() + ": u layeru Layer1File";
        } else {
            return "Element nije datoteka";
        }

    }

}
