package pantin.diiage.org.chillindijon.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChillInDijonDbHelper extends SQLiteOpenHelper {

    public static final String CHILLINDIJON_DB = "chillindijon.db";
    public static final int VERSION = 1;

    /**
     * Nom de la table contenant les favoris
     */
    public static final String TABLE_FAVORIS = "Favoris";

    public ChillInDijonDbHelper(Context context) {
        super(context, CHILLINDIJON_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + this.TABLE_FAVORIS +
                "(idFavoris INT PRIMARY KEY," +
                "idPOI INT," +
                "stars INT," +
                "dateAjout DATETIME)");

        db.execSQL("CREATE TABLE POI (" +
                "idPOI INT PRIMARY KEY," +
                "idLocation INT," +
                "type TEXT)");

        db.execSQL("CREATE TABLE Location (" +
                "idLocation INT PRIMARY KEY," +
                "idPosition INT," +
                "type TEXT)");

        db.execSQL("CREATE TABLE Positon (" +
                "idPosition INT PRIMARY KEY," +
                "lon DOUBLE," +
                "lat DOUBLE)");

        // TO DO : unique lat + long
    }

    /**
     * Appelé quand il est nécessaire de maj la structure de la base
     * le but  est de :
     * modifier la structure (ajout , suppr table, modif champ, ...)
     *
     * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // rien à fair car en version 1
    }
}
