package br.unisc.gabrielcalderaro.vivaunisc;

public class RegrexAutoComplete {

    String padrao1 = "";

    String padrao11 = "[A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao12 = "[A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao13 = "[A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao14 = "[A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao21 = "[A-Z][a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao22 = "[A-Z][a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao23 = "[A-Z][a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*,\\ [A-Z][a-z]*";

    String padrao24 = "[A-Z][a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao31 = "[A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao32 = "[A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao33 = "[A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*,\\ [A-Z][a-z]*";

    String padrao34 = "[A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao41 = "[A-Z][a-z]*\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao42 = "[A-Z][a-z]*\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";

    String padrao43 = "[A-Z][a-z]*\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*,\\ [A-Z][a-z]*";

    String padrao44 = "[A-Z][a-z]*\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\ \\-\\ [A-Z][a-z]*\\ [A-Z][a-z]*\\ [a-z]*\\ [A-Z][a-z]*\\,\\ [A-Z][a-z]*";


    public RegrexAutoComplete() {
    }

    public Boolean validaCidade(String cidade){

        if(cidade.matches(padrao11)){
            return true;
        }  else if (cidade.matches(padrao1)){
            return true;
        }  else if (cidade.matches(padrao12)){
            return true;
        }  else if (cidade.matches(padrao13)){
            return true;
        }  else if (cidade.matches(padrao14)){
            return true;
        }  else if (cidade.matches(padrao21)){
            return true;
        }  else if (cidade.matches(padrao22)){
            return true;
        }  else if (cidade.matches(padrao23)){
            return true;
        }  else if (cidade.matches(padrao24)){
            return true;
        }  else if (cidade.matches(padrao31)){
            return true;
        }  else if (cidade.matches(padrao32)){
            return true;
        }  else if (cidade.matches(padrao33)){
            return true;
        }  else if (cidade.matches(padrao34)){
            return true;
        }  else if (cidade.matches(padrao41)){
            return true;
        }  else if (cidade.matches(padrao42)){
            return true;
        }  else if (cidade.matches(padrao43)){
            return true;
        }  else if (cidade.matches(padrao44)){
            return true;
        } else return false;
    }

}