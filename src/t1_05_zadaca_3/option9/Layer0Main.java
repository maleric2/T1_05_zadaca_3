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
public class Layer0Main {
    //TODO:
    //Element se proslijeđuje iz ElementStructure

    private Element element;
    private LayerInterface layerInterface;
    private String putanjaDoKorijena;
    private String folderOpcija9;
    private String opcija9Path;

    public Layer0Main(String[] args) {
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

    public String ispisiKreiranje() {
        String izlaz = "";
        
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

}
