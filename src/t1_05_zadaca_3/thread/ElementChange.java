/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.thread;

/**
 *
 * @author Marko
 */
public class ElementChange {
    public static int counter=0;
    public int id;
    public String promjena;
    
    public ElementChange(String promjena){
        this.id =counter++;
        this.promjena = promjena;
    }
    
}
