package com.example.user.exercrudsql.DB;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 09/11/2014.
 */
public class ImovelHelper extends SQLiteOpenHelper{

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String NUMERIC_TYPE = " NUMERIC";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TABLE_CATEGORIA =
            "CREATE TABLE " + ImovelContract.Categoria.TABLE_NAME + " (" +
                    ImovelContract.Categoria._ID + " INTEGER PRIMARY KEY, " +
                    ImovelContract.Categoria.NOME + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_TABLE_IMOVEL =
            "CREATE TABLE " + ImovelContract.Imovel.TABLE_NAME + " (" +
                    ImovelContract.Imovel._ID + " INTEGER PRIMARY KEY, " +
                    ImovelContract.Imovel.CATEGORIA_ID + " INT " + COMMA_SEP +
                    ImovelContract.Imovel.TITULO + TEXT_TYPE + COMMA_SEP +
                    ImovelContract.Imovel.LOGRADOURO + TEXT_TYPE + COMMA_SEP +
                    ImovelContract.Imovel.NUMERO + NUMERIC_TYPE + COMMA_SEP +
                    ImovelContract.Imovel.BAIRRO + TEXT_TYPE + COMMA_SEP +
                    ImovelContract.Imovel.CIDADE + TEXT_TYPE + COMMA_SEP +
                    ImovelContract.Imovel.VALOR + REAL_TYPE + COMMA_SEP +
                    ImovelContract.Imovel.TIPO_NEGOCIO + TEXT_TYPE + COMMA_SEP +
                    " FOREIGN KEY (" + ImovelContract.Imovel.CATEGORIA_ID + ") " +
                    "REFERENCES " + ImovelContract.Categoria.TABLE_NAME + " (" +
                    ImovelContract.Categoria._ID + ") "+
                    " )";

    private static final String SQL_DELETE_TABLE_CATEGORIA =
            "DROP TABLE IF EXISTS " + ImovelContract.Categoria.TABLE_NAME;
    private static final String SQL_DELETE_TABLE_IMOVEL =
            "DROP TABLE IF EXISTS " + ImovelContract.Imovel.TABLE_NAME;

    // Se você modificar o schema do banco, você deve incrementar a versão do software.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "IMOVEIS.db";

    public ImovelHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_CATEGORIA);
        db.execSQL(SQL_CREATE_TABLE_IMOVEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE_CATEGORIA);
        db.execSQL(SQL_DELETE_TABLE_IMOVEL);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
