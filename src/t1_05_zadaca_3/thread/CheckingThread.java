/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.thread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import t1_05_zadaca_3.structure.CareTaker;
import t1_05_zadaca_3.structure.Element;
import t1_05_zadaca_3.structure.ElementStructure;
import t1_05_zadaca_3.structure.Originator;
import t1_05_zadaca_3.terminal.Drawer;

/**
 *
 * @author Marko
 */
public class CheckingThread extends Thread {

    private int interval = 60;
    private boolean pause = false;
    private Drawer view;
    private ThreadController controller;
    private ElementStructure provjeraStrukture;

    public CheckingThread(int interval, Drawer view, ThreadController controller) {
        this.view = view;
        this.controller = controller;
        this.interval = interval;
    }
    

    @Override
    public void run() {

        provjeraStrukture = new ElementStructure();
        while (!pause) {
            //TODO Logic
            view.drawWindow1("Thread: Izvrsavam dretvu (interval: " + interval + ")\n");
            //Load sve datoteke
            //Load sve direktorije
            //Check ima li izmjene

            provjeraStrukture.setRootPath(controller.getModel().getKorijenskaPutanja());
            ChangesInStructure cs = provjeraStrukture.usporedbaStruktureElemenata(provjeraStrukture.getSviElementi(), controller.getModel().getSviElementi());
            if (cs != null) {
                //TODO: spremiti postojeće stanje
                int brojStanja = controller.saveState(provjeraStrukture);
                if(brojStanja>0){
                    view.drawWindow2("Spremljeno novo stanje: " + brojStanja+"\n");
                    controller.setModel(provjeraStrukture);
                }
                else view.drawWindow2("Problem pri spremanju stanja\n");
                
                view.drawWindow2(cs.getChangesInElements());
            } else {
                view.drawWindow1(provjeraStrukture.getVrijemeKreiranja() + " - nema promjene u strukturi");
            }
            //view.drawWindow1(provjeraStrukture.usporedbaStrukture(provjeraStrukture.getSviElementi(), controller.getModel().getSviElementi()));
            controller.updateViewMenu();
            try {
                sleep((long) (interval * 1000));
            } catch (InterruptedException ex) {
                //ex.printStackTrace();
                break;
            }

        }
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
