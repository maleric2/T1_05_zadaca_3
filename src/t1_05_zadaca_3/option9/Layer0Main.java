/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.option9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import t1_05_zadaca_3.structure.Element;
import t1_05_zadaca_3.structure.ElementStructure;

/**
 *
 * @author Filip
 */
public class Layer0Main {
    //TODO:
    //Element se proslijeđuje iz ElementStructure

    private Element element;
    private LayerInterface layerInterface;
    private String putanjaDoKorijena;
    private String folderOpcija9;
    private String opcija9Path;

    private List<Element> sviElementi;

    public Layer0Main(String[] args) {
        sviElementi = new ArrayList<>();
        putanjaDoKorijena = args[3];
        opcija9Path = putanjaDoKorijena + "\\opcija9"; // C:\UzDiz\test\opcija9
        folderOpcija9 = opcija9Path + "\\"; // C:\UzDiz\test\opcija9\
    }

    public void setElement(Element e) {
        this.element = e;
    }

    public void setLayer() {
        this.layerInterface = new Layer1File(element, folderOpcija9);
    }

    public String ispisiKreiranje(ElementStructure es) {
        sviElementi.clear();
        String izlaz = "";

        sviElementi = es.getSviElementi();

        if (element.isDirektorij()) {
            this.layerInterface = new Layer2Directory(element, folderOpcija9);
        }

        File opcija9 = new File(opcija9Path);
        if (!opcija9.exists()) {
            izlaz += "Kreira se direktorij: " + opcija9.getAbsolutePath() + "\n";
            opcija9.mkdir();
        }

        this.layerInterface.create();
        izlaz += this.layerInterface.ispis();
        return izlaz;
    }

    public int vratiVelicinu() {
        return sviElementi.size();
    }
}
