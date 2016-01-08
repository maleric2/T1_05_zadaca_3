/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.option9;

import java.io.File;
import t1_05_zadaca_3.structure.Element;

/**
 *
 * @author Filip
 */
public class Layer2Directory implements LayerInterface {
    //Kreira Direktorije

    private Element e;
    private String putanja;
    private LayerVisitor visitor;

    public Layer2Directory(Element e, String putanja, LayerVisitor visitorLayer2) {
        this.visitor = visitorLayer2;
        this.e = e;
        this.putanja = putanja;
    }

    @Override
    public boolean create() {
        String putanjaNazivDir = putanja + e.getNaziv();

        if (e.isDirektorij()) {
            new File(putanjaNazivDir).mkdir();
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String ispis() {
        if (create()) {
            return visitor.visit(this) + "Kreiran je direktorij " + e.getNaziv() + ": u Layer2Directory";
        } else {
            return visitor.visit(this) + "Element nije direktorij";
        }
    }
}
