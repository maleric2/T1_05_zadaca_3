/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_05_zadaca_3.application;

/**
 *
 * @author Marko
 */
public class ArgumentsValidator {

    private String errorMsg;

    public boolean validate(String[] args) {

        //brojRedaka brojStupaca podjelaOkvira  putanja2_naziv brSekundi 
        
        if (args.length < 5) {
            //System.out.println("ERROR: Parameters do not match");
            errorMsg = "ERROR: Parameters do not match";
            return false;
        } else {
            for (int i = 0; i < 5; i++) {
                if (args[i] == null) {
                    //System.out.println("ERROR: Every parameter must have value");
                    errorMsg = "ERROR: Every parameter must have value";
                    return false;
                }
            }
            int brojRedaka, brojStupaca, brSekundi;
            brojRedaka = Integer.parseInt(args[0]);
            brojStupaca = Integer.parseInt(args[1]);
            String podjelaOkvira = args[2];
            String putanja2_naziv = args[3];
            brSekundi = Integer.parseInt(args[4]);

            if (brojRedaka < 24 || brojRedaka > 60) {
                //System.out.println("ERROR: 'brojRedaka' must be 24-60");
                errorMsg = "ERROR: 'brojRedaka' must be 24-60";
                return false;
            }
            if (brojStupaca < 80 || brojStupaca > 160) {
                //System.out.println("ERROR: 'brojStupaca' must be 80-160");
                errorMsg = "ERROR: 'brojStupaca' must be 80-160";
                return false;
            }
            if (brSekundi < 1 || brSekundi > 120) {
                //System.out.println("ERROR: 'brSekundi' must be 1-120");
                errorMsg = "ERROR: 'brSekundi' must be 1-120";
                return false;
            }
            if (!podjelaOkvira.equalsIgnoreCase("O") && !podjelaOkvira.equalsIgnoreCase("V")) {
                //System.out.println("ERROR: 'podjelaOkvira' must be V or O");
                errorMsg = "ERROR: 'podjelaOkvira' must be V or O";
                return false;
            }
            //TODO: provjera postojanja putanje za 'putanja2_naziv'

            return true;
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
