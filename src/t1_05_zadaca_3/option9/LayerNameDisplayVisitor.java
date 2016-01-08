/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.option9;

/**
 *
 * @author Filip
 */
public class LayerNameDisplayVisitor implements LayerVisitor {
    
    public static final String ANSI_ESC = "\033[";
    
    @Override
    public String visit(Layer1File layer1) {
        return ANSI_ESC + "32m" + "Visitor Layer-a 1.\n";
    }

    @Override
    public String visit(Layer2Directory layer2) {
        return ANSI_ESC + "31m" + "Visitor Layer-a 2.\n";
    }

    @Override
    public String visit(Layer0Main layer0) {
        return ANSI_ESC + "35m" + "Visitor Layer-a 0 -GLAVNI LAYER-.\n";
    }
}
