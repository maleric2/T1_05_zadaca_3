/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.thread;

import t1_05_zadaca_3.application.MainMenu;
import t1_05_zadaca_3.structure.ElementStructure;
import t1_05_zadaca_3.structure.PrintStructure;
import t1_05_zadaca_3.terminal.Drawer;

/**
 *
 * @author Marko
 */
public class ThreadController {
    private ElementStructure model;
    private Drawer view;
    PrintStructure ps = new PrintStructure();
    
    CheckingThread thread;
    int interval;
    public ThreadController(Drawer view, ElementStructure model, int interval){
        this.view = view;
        this.model=model;
        this.interval = interval;
    }
    
    public void setModel(ElementStructure model){
        this.model = model;
    }
    public ElementStructure getModel(){
        return model;
    }
    public void startThread(){
        updateThreadView(false);
        thread = new CheckingThread(interval, view, this);
        thread.start();
    }
    public void pauseThread(){
        updateThreadView(true);
        thread.setPause(true);
    }
    private void updateThreadView(boolean pause){
        if(pause) view.drawWindow1("Thread: Iskljucujem izvrsavanje\n");
        else view.drawWindow1("Thread: Uključujem izvrsavanje\n");
        updateViewMenu(); //Moze i bez
    }
    public void terminateThread(){
        if(!thread.isPause())
            thread.interrupt();
        else  view.drawWindow1("Cekam na dretvu..");
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
