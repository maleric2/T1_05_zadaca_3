/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.structure;

/**
 * Originator
 * @author Tomislav
 */
public class ElementState {

    private ElementStructure state;

    public void setState(ElementStructure state) {
        this.state = state;
    }

    public ElementStructure getState() {
        return state;
    }

    public ElementMemento saveStateToMemento() {
        return new ElementMemento(state);
    }

    public void getStateFromMemento(ElementMemento Memento) {
        state = Memento.getState();
    }
}
