/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.structure;

/**
 * Memento
 * @author Tomislav
 */
public class ElementMemento {

    private ElementStructure state;

    public ElementMemento(ElementStructure state) {
        this.state = state;
    }

    public ElementStructure getState() {
        return state;
    }
}
