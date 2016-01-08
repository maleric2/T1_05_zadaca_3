/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.thread;

import java.util.Hashtable;
import java.util.Map;
import t1_05_zadaca_3.structure.Element;
import t1_05_zadaca_3.structure.ElementStructure;

/**
 *
 * @author Marko
 */
public class ChangesInStructure {
    private ElementStructure novaStruktura;
    private String vrijemeProvjere;
    private Map<Element,ElementChange> promjenjeniElementi;
    
    public ChangesInStructure(String vrijeme){
        this.vrijemeProvjere=vrijeme;
        promjenjeniElementi = new Hashtable<>();
    }
    public void newElementChange(Element e,ElementChange c){
        promjenjeniElementi.put(e, c);
    }
    public void setNovaStrutura(ElementStructure es){
        if(this.promjenjeniElementi.size()>0)
            this.novaStruktura = es;
        
    }
    public int size(){
        return this.promjenjeniElementi.size();
    }
    /*public Enumeration<Element> getElements(){
        return promjenjeniElementi.keys();
    }
    public ElementChange getChange(Element e){
        return promjenjeniElementi.get(e);
    }
    public Enumeration<ElementChange> getChanges(){
        return promjenjeniElementi.elements();
    }*/
    public String getChangesInElements(){
        StringBuilder poruka = new StringBuilder();
        poruka.append("Vrijeme provjere: ").append(vrijemeProvjere).append("\n");
        if(size()>0){
            for( Map.Entry<Element,ElementChange> entry : promjenjeniElementi.entrySet()){
                Element e = entry.getKey();
                ElementChange c = entry.getValue();
                //TODO: Putanju treba naći!!
                poruka.append("  ").append(e.getNaziv()).append(": ").append(c.promjena).append("\n");
            }
        }
        return poruka.toString();
    }

}
