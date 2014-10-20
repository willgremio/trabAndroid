package br.unisc.gabrielcalderaro.vivaunisc;

public class RegrexAutoComplete {

    String padrao1 = "";

    String padrao11 = "[A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao12 = "[A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\,\\ " +
            "[A-Z][a-zà-ú]*";

    String padrao13 = "[A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ " +
            "[A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao14 = "[A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ " +
            "[a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao21 = "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\,\\ " +
            "[A-Z][a-zà-ú]*";

    String padrao22 = "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ " +
            "[A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao23 = "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ " +
            "[a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*,\\ [A-Z][a-zà-ú]*";

    String padrao24 = "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ " +
            "[A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao31 = "[A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ \\-\\ " +
            "[A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao32 = "[A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ \\-\\ " +
            "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao33 = "[A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ \\-\\ " +
            "[A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*,\\ [A-Z][a-zà-ú]*";

    String padrao34 = "[A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ \\-\\ " +
            "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\,\\ " +
            "[A-Z][a-zà-ú]*";

    String padrao41 = "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ " +
            "[A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao42 = "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ " +
            "[A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padrao43 = "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ " +
            "[A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ [A-Z][a-zà-ú]*,\\ " +
            "[A-Z][a-zà-ú]*";

    String padrao44 = "[A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ " +
            "[A-Z][a-zà-ú]*\\ \\-\\ [A-Z][a-zà-ú]*\\ [A-Z][a-zà-ú]*\\ [a-z][a-zà-ú]*\\ " +
            "[A-Z][a-zà-ú]*\\,\\ [A-Z][a-zà-ú]*";

    String padraoEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*" +
            "@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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


        public Boolean validaEmail(String email){

            if (email.matches(padraoEmail)) {
                return true;
            } else return false;
    }


}