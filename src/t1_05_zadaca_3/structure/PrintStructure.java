/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.structure;

/**
 *
 * @author Filip
 */
public class PrintStructure {
    
    public PrintStructure() {
        
    }
    
    /**
     * Metoda za ispis strukture elemenata sa Iteratorom
     * @param e
     * @param es 
     */
    public void printStructure(Element e, ElementStructure es) {

        for (Iterator iter = es.getIterator(); iter.hasNext(e);) {
            Element e1 = (Element) iter.next(e);
            System.out.println(e1.toString());
            if (e1.isDirektorij() && e1.isImaDjecu() ) {
                printStructure(e1, es);
            }
            
        }
    }
    
    //OPCIJA 1 u izborniku
    
    public void MenuOption1() {
        
    }
    
    public void MenuOption2() {
        
    }
}
