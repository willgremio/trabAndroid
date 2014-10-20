package br.unisc.gabrielcalderaro.vivaunisc;


import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Gabriel on 19/10/2014.
 */
public class ConverterDataHora {

    public ConverterDataHora() {
    }

    public String retornaData(String dataHora){
            String  dia = dataHora.substring(8, 10);
            String mes = dataHora.substring(5, 7);
            String ano = dataHora.substring(0, 4);
            String hora = dataHora.substring(10,16);

            return dia +"/"+ mes +"/"+ ano+" "+hora;
    }

    public void validaCidade(){

    }
}
