package com.example.m77611.exerprova.DB;

import android.provider.BaseColumns;

/**
 * Created by m77611 on 03/11/2014.
 */
public class EnergiaContract {

    public EnergiaContract(){}

    public final class Energia implements BaseColumns {
        public final static String TABLE_NAME = "ENERGIA";
        public final static String COD_CLI = "cod_cli";
        public final static String RUA = "rua";
        public final static String NUMERO = "numero";
        public final static String KWH = "kwh";

    }
}
