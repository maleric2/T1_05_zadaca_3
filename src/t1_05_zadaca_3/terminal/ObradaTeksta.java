package t1_05_zadaca_3.terminal;


import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tomislav
 */
public class ObradaTeksta {

    public ObradaTeksta() {
    }
    
    public List<String> tekstPoRedovima(String text, int sirina)
    {
        List<String> stupci = new ArrayList<>();
        boolean gotovo = false;
        for (int i = 0; i < text.length();) {
            String stupac = "";
            for (int j = 0 + i; j < sirina + i; j++) {
                if (j >= text.length()) {
                    gotovo = true;
                    break;

                }
                stupac += Character.toString(text.charAt(j));
            }
            stupci.add(stupac);
            if (gotovo) {
                break;
            }
            i += sirina;
        }
        return stupci;
    }
}
