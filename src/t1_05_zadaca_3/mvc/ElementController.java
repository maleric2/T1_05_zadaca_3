/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.mvc;

import t1_05_zadaca_3.application.MainMenu;
import t1_05_zadaca_3.structure.ElementStructure;
import t1_05_zadaca_3.structure.PrintStructure;
import t1_05_zadaca_3.terminal.Drawer;

/**
 *
 * @author Marko
 */
public class ElementController {
    private ElementStructure model;
    private Drawer view;
    PrintStructure ps = new PrintStructure();
    
    public ElementController(ElementStructure model, Drawer view){
        this.model = model;
        this.view = view;
    }
    
    public void setModel(ElementStructure model){
        this.model = model;
    }
    
    public void updateView(){
        updateViewStructure();
        updateViewStats();
    }
    public void updateViewStructure(){
        view.drawWindow1(ps.MenuOption2(model.getStrukturaElemenata(), model));
    }
    public void updateViewStats(){
        view.drawWindow2(ps.MenuOption1(model.getDirektoriji(), model.getDatoteke(), model.getVelicinaKorDir()));
    }
    public void updateViewMenu(){
        view.drawBottom(MainMenu.getInstance().getMainMenu());
    }
}
