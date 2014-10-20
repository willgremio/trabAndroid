package br.unisc.gabrielcalderaro.vivaunisc;

public class ConverterDataHora {

    public ConverterDataHora() {
    }

    public String retornaData(String dataHora){
            String  dia = dataHora.substring(8, 10);
            String mes = dataHora.substring(5, 7);
            String ano = dataHora.substring(0, 4);
            String hora = dataHora.substring(10,16);

            return dia +"/"+ mes +"/"+ ano+" "+hora + " h";
    }

}