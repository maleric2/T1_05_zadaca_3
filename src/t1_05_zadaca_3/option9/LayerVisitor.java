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
public interface LayerVisitor {
    public String visit(Layer1File layer1);
    public String visit(Layer2Directory layer2);
    public String visit(Layer0Main layer0);
}
