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

    public List<String> tekstPoRedovima(String text, int sirina) {
        List<String> redovi = new ArrayList<>();
        String lines[] = text.split("\\r?\\n");

        for (String red : lines) {
            red = red.replace("\n", "").replace("\r", "");
            redovi.add(red);
        }

        redovi = provjeraDuzineRedova(sirina, redovi);
        
        return redovi;
    }

    private List<String> provjeraDuzineRedova(int sirina, List<String> redovi) {
        List<String> pomocniRedovi = new ArrayList<>();
        for (String red : redovi) {
            if (red.length() > sirina - 2) {
                String prviDio = "";
                String drugiDio = "";
                //prolaz kroz cijeli string
                for (int i = 0; i < red.length(); i++) {

                    if (i < 7) {
                        //kopiranje koda za boju
                        prviDio += Character.toString(red.charAt(i));
                        drugiDio += Character.toString(red.charAt(i));
                    } else if (i < sirina - 2 + 7) {
                        //kopiranje teksta za popuniti jedan cijeli red
                        prviDio += Character.toString(red.charAt(i));
                        //kopiranje ostatka teksta
                    } else {
                        //kopiranje ostatka teksta
                        drugiDio += Character.toString(red.charAt(i));
                    }
                }

                pomocniRedovi.add(prviDio);
                pomocniRedovi.add(drugiDio);
                /*redovi.set(brojacRedova, prviDio);
                 redovi.add(brojacRedova, drugiDio);
                 brojacRedova++;*/
            } else {
                pomocniRedovi.add(red);
            }
        }
        return pomocniRedovi;
    }
}
