/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.terminal;

import java.util.List;

/**
 * TODO: Pretvorit sve ovo u View ili u 2 View-a
 * TODO: Model = struktura
 * @author Marko
 */
public class Drawer {
    DrawerUI drawer;
    String type;
    ObradaTeksta ot = new ObradaTeksta();
    
    public Drawer(String type, int sirina, int visina) {
        this.type = type;
        if(type.equals("O")){
            drawer = new DrawerUIO(sirina, visina);
        }
        else if( type.equals("V")){
            drawer = new DrawerUIV(sirina, visina);
        }
        drawer.drawFrame();
    }

    public void drawBottom(String text){
        drawer.drawBottom(text);
    }
    /**
     * Text je sadržajStrukture
     * @param text 
     */
    public void drawWindow1(String text){
        List<String> drawText = null;
        if(type.equals("O")){
            drawText = ot.tekstPoRedovima(text, drawer.sirina - 2, 2); 
        }
        else if(type.equals("V")){
             drawText = ot.tekstPoRedovima(text, drawer.sirina / 2, 0);
        }
        drawer.drawWindow1(drawText);
    }
    /**
     * Text je broj direktorija datoteke
     * @param text 
     */
    public void drawWindow2(String text){
        List<String> drawText = null;
        if(type.equals("O")){
            drawText = ot.tekstPoRedovima(text, drawer.sirina - 2, 2); 
        }
        else if(type.equals("V")){
             drawText = ot.tekstPoRedovima(text, drawer.sirina / 2, 0);
        }
        drawer.drawWindow2(drawText);
    }
    public void clear(){
        //TODO: Clear da očisti višak
        //drawer.drawFrame();
    }
}
