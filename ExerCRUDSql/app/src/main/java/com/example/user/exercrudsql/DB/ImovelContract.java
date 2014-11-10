package com.example.user.exercrudsql.DB;

import android.provider.BaseColumns;

/**
 * Created by User on 09/11/2014.
 */
public class ImovelContract {

    public ImovelContract() {}

    public final class Imovel implements BaseColumns {
        public final static String TABLE_NAME = "IMOVEL";
        public final static String TITULO = "titulo";
        public final static String LOGRADOURO = "logradouro";
        public final static String NUMERO = "numero";
        public final static String BAIRRO = "bairro";
        public final static String CIDADE = "cidade";
        public final static String VALOR = "valor";
        public final static String TIPO_NEGOCIO = "tipo_negocio";
        public final static String CATEGORIA_ID = "categoria_id";
    }

    public final class Categoria implements BaseColumns {
        public final static String TABLE_NAME = "CATEGORIA";
        public final static String NOME = "nome";
    }
}
