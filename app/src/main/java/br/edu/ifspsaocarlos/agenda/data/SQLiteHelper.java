package br.edu.ifspsaocarlos.agenda.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "agenda.db";
    static final String DATABASE_TABLE = "contatos";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITO = "favorito";
    static final String KEY_FONE_COMERCIAL = "foneComercial";
    static final String KEY_MES_ANIVERSARIO = "mesAniversario";
    static final String KEY_DIA_ANIVERSARIO = "diaAniversario";
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_CREATE = "CREATE TABLE " + DATABASE_TABLE + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " TEXT NOT NULL, " +
            KEY_FONE + " TEXT, " +
            KEY_EMAIL + " TEXT);";
    private static final String DATABASE_V2 = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " +
            KEY_FAVORITO + " INTEGER;";
    private static final String DATABASE_V3 = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " +
            KEY_FONE_COMERCIAL + " TEXT;";

    private static final String DATABASE_V4 = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " +
            KEY_MES_ANIVERSARIO + " INTEGER;";

    private static final String DATABASE_V4_2 = "ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN " +
            KEY_DIA_ANIVERSARIO + " INTEGER;";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL(DATABASE_V2);
        database.execSQL(DATABASE_V3);
        database.execSQL(DATABASE_V4);
        database.execSQL(DATABASE_V4_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                database.execSQL(DATABASE_V2);
            case 2:
                database.execSQL(DATABASE_V3);
            case 3:
                database.execSQL(DATABASE_V4);
                database.execSQL(DATABASE_V4_2);
        }
    }
}
