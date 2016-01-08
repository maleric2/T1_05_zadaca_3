/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3;

import t1_05_zadaca_3.application.VT100Application;

/**
 *
 * @author Marko
 */
public class T1_05_zadaca_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        VT100Application application = VT100Application.getInstance();
        application.start(args);
    }
    
}
