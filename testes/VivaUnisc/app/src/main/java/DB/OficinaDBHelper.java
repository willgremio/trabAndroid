package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shadow on 15/10/2014.
 */
public class OficinaDBHelper  extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE_OFICINA =
            "CREATE TABLE " + OficinaContract.Oficina.TABLE_NAME + " (" +
                    OficinaContract.Oficina.ID_OFICINA + " INTEGER PRIMARY KEY," +
                    OficinaContract.Oficina.CURSO + " INTEGER," +
                    OficinaContract.Oficina.TITULO + " VARCHAR," +
                    OficinaContract.Oficina.IMAGEM + " VARCHAR," +
                    OficinaContract.Oficina.DATA_HORA +  " DATETIME," +
                    OficinaContract.Oficina.DURACAO + " TIME," +
                    OficinaContract.Oficina.DESCRICAO + " TEXT," +
                    OficinaContract.Oficina.RESPONSAVEL + " VARCHAR," +
                    OficinaContract.Oficina.LOCAL + " VARCHAR," +
                    OficinaContract.Oficina.VAGAS + " INTEGER," +
                    OficinaContract.Oficina.INSCRITOS + " INTEGER" +
                    " )";

    private static final String SQL_DELETE_TABLE_OFICINA =
            "DROP TABLE IF EXISTS " + OficinaContract.Oficina.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VivaUnisc.db";

    public OficinaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_OFICINA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE_OFICINA);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
