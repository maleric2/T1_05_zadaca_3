/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.thread;

import t1_05_zadaca_3.application.MainMenu;
import t1_05_zadaca_3.structure.ElementStates;
import t1_05_zadaca_3.structure.ElementStructure;
import t1_05_zadaca_3.structure.ElementState;
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
    private ElementState izvor;
    private ElementStates save;

    CheckingThread thread;
    int interval;

    public ThreadController(Drawer view, ElementStructure model, int interval) {
        this.view = view;
        this.model = model;
        this.interval = interval;
    }

    public void setSaveSetup(ElementState izvor, ElementStates save) {
        this.save = save;
        this.izvor = izvor;
    }

    public int saveState(ElementStructure model) {
        if (izvor != null && save != null) {
            izvor.setState(model);
            save.add(izvor.saveStateToMemento());
            return save.vratiVelicinu() - 1;
        } else {
            return -1;
        }
    }

    public void setModel(ElementStructure model) {
        this.model = model;
    }

    public ElementStructure getModel() {
        return model;
    }

    public void startThread() {
        updateViewMenu();
        if (thread != null && thread.isPause()) {
            thread.setPause(true);
            thread.interrupt();
            thread = null;
        } else if (thread != null && !thread.isPause()) {
            view.drawWindow1("Thread: Vec je aktivna dretva");
            updateViewMenu();
            return;
        }
        updateThreadView(false);
        thread = new CheckingThread(interval, view, this);
        thread.start();
    }

    public void pauseThread() {
        if (thread != null) {
            updateThreadView(true);
            thread.setPause(true);
        }else{
            view.drawWindow1("Dretva ne postoji\n");
        }
    }

    private void updateThreadView(boolean pause) {
        if (pause) {
            view.drawWindow1("Thread: Iskljucujem izvrsavanje\n");
        } else {
            view.drawWindow1("Thread: Uključujem izvrsavanje\n");
        }
        updateViewMenu(); //Moze i bez
    }

    public void terminateThread() {
        if (!thread.isPause()) {
            thread.interrupt();
        } else {
            view.drawWindow1("Cekam na dretvu..");
        }
    }

    public void updateView() {
        updateViewStructure();
        updateViewStats();
    }

    public void updateViewStructure() {
        view.drawWindow1(ps.MenuOption2(model.getStrukturaElemenata(), model));
    }

    public void updateViewStats() {
        view.drawWindow2(ps.MenuOption1(model.getDirektoriji(), model.getDatoteke(), model.getVelicinaKorDir()));
    }

    public void updateViewMenu() {
        if (thread != null && !thread.isPause()) {
            view.drawBottomColor2(MainMenu.getInstance().getMainMenu());//zeleno
        } else if (thread == null) {
            view.drawBottom(MainMenu.getInstance().getMainMenu());
        } else {
            view.drawBottomColor1(MainMenu.getInstance().getMainMenu());//zuto
        }
    }
}
