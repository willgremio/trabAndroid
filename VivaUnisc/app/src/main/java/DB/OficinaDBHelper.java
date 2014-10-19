package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OficinaDBHelper  extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE_OFICINA =
            "CREATE TABLE " + OficinaContract.Oficina.TABLE_NAME + " (" +
                    OficinaContract.Oficina._ID + " INTEGER PRIMARY KEY," +
                    OficinaContract.Oficina.ID_OFICINA + " TEXT," +
                    OficinaContract.Oficina.CURSO + " TEXT," +
                    OficinaContract.Oficina.TITULO + " TEXT," +
                    OficinaContract.Oficina.IMAGEM + " TEXT," +
                    OficinaContract.Oficina.DATA_HORA +  " TEXT," +
                    OficinaContract.Oficina.DURACAO + " TEXT," +
                    OficinaContract.Oficina.DESCRICAO + " TEXT," +
                    OficinaContract.Oficina.RESPONSAVEL + " TEXT," +
                    OficinaContract.Oficina.LOCAL + " TEXT," +
                    OficinaContract.Oficina.VAGAS + " TEXT," +
                    OficinaContract.Oficina.INSCRITOS + " TEXT" +
                    " )";


    private static final String SQL_CREATE_TABLE_ESTUDANTE =
            "CREATE TABLE " + OficinaContract.Estudante.TABLE_NAME + " (" +
                    OficinaContract.Estudante._ID + " INTEGER PRIMARY KEY," +
                    OficinaContract.Estudante.ID_OFICINA + " TEXT," +
                    OficinaContract.Estudante.NOME + " TEXT," +
                    OficinaContract.Estudante.EMAIL + " TEXT," +
                    OficinaContract.Estudante.TELEFONE +  " TEXT," +
                    OficinaContract.Estudante.CIDADE + " TEXT" +
                    " )";

    private static final String SQL_DELETE_TABLE_OFICINA =
            "DROP TABLE IF EXISTS " + OficinaContract.Oficina.TABLE_NAME;

    private static final String SQL_DELETE_TABLE_ESTUDANTE =
            "DROP TABLE IF EXISTS " + OficinaContract.Estudante.TABLE_NAME;


    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "VivaUnisc.db";

    public OficinaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_OFICINA);
        db.execSQL(SQL_CREATE_TABLE_ESTUDANTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_OFICINA);
        db.execSQL(SQL_DELETE_TABLE_ESTUDANTE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}