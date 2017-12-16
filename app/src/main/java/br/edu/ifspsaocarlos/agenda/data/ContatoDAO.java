package br.edu.ifspsaocarlos.agenda.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.ifspsaocarlos.agenda.model.Contato;
import java.util.ArrayList;
import java.util.List;


public class ContatoDAO {
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    public static final Integer FAVORITO_ON = 1;
    public static final Integer FAVORITO_OFF = 0;

    public ContatoDAO(Context context) {
        this.dbHelper=new SQLiteHelper(context);
    }

    public  List<Contato> buscaTodosContatos()
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID,SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FAVORITO, SQLiteHelper.KEY_FONE_COMERCIAL, SQLiteHelper.KEY_MES_ANIVERSARIO, SQLiteHelper.KEY_DIA_ANIVERSARIO};

        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, null , null,
                null, null, SQLiteHelper.KEY_NAME);

        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(cursor.getInt(4));
            contato.setFoneComercial(cursor.getString(5));
            contato.setMesAniversario(cursor.getInt(6));
            contato.setDiaAniversario(cursor.getInt(7));
            contatos.add(contato);
        }
        cursor.close();


        database.close();
        return contatos;
    }

    public  List<Contato> buscaContato(String criterio)
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID,SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FAVORITO, SQLiteHelper.KEY_FONE_COMERCIAL, SQLiteHelper.KEY_MES_ANIVERSARIO, SQLiteHelper.KEY_DIA_ANIVERSARIO};
        String where=SQLiteHelper.KEY_NAME + " like ? OR " + SQLiteHelper.KEY_EMAIL + " like ?";
        String[] argWhere=new String[]{criterio + "%", criterio + "%"};


        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , argWhere,
                null, null, SQLiteHelper.KEY_NAME);


        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(cursor.getInt(4));
            contato.setFoneComercial(cursor.getString(5));
            contato.setMesAniversario(cursor.getInt(6));
            contato.setDiaAniversario(cursor.getInt(7));
            contatos.add(contato);
        }
        cursor.close();

        database.close();
        return contatos;
    }

    public  List<Contato> buscaContatosFavoritados()
    {
        database=dbHelper.getReadableDatabase();
        List<Contato> contatos = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {SQLiteHelper.KEY_ID,SQLiteHelper.KEY_NAME, SQLiteHelper.KEY_FONE, SQLiteHelper.KEY_EMAIL, SQLiteHelper.KEY_FAVORITO, SQLiteHelper.KEY_FONE_COMERCIAL, SQLiteHelper.KEY_MES_ANIVERSARIO, SQLiteHelper.KEY_DIA_ANIVERSARIO};
        String where=SQLiteHelper.KEY_FAVORITO + " = ?";
        String[] argWhere=new String[]{FAVORITO_ON.toString()};


        cursor = database.query(SQLiteHelper.DATABASE_TABLE, cols, where , argWhere,
                null, null, SQLiteHelper.KEY_NAME);


        while (cursor.moveToNext())
        {
            Contato contato = new Contato();
            contato.setId(cursor.getInt(0));
            contato.setNome(cursor.getString(1));
            contato.setFone(cursor.getString(2));
            contato.setEmail(cursor.getString(3));
            contato.setFavorito(cursor.getInt(4));
            contato.setFoneComercial(cursor.getString(5));
            contato.setMesAniversario(cursor.getInt(6));
            contato.setDiaAniversario(cursor.getInt(7));
            contatos.add(contato);
        }
        cursor.close();

        database.close();
        return contatos;
    }

    public void salvaContato(Contato c) {
        database=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_NAME, c.getNome());
        values.put(SQLiteHelper.KEY_FONE, c.getFone());
        values.put(SQLiteHelper.KEY_EMAIL, c.getEmail());
        values.put(SQLiteHelper.KEY_FAVORITO, c.getFavorito());
        values.put(SQLiteHelper.KEY_FONE_COMERCIAL, c.getFoneComercial());
        values.put(SQLiteHelper.KEY_MES_ANIVERSARIO, c.getMesAniversario());
        values.put(SQLiteHelper.KEY_DIA_ANIVERSARIO, c.getDiaAniversario());

       if (c.getId()>0)
          database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);
        else
           database.insert(SQLiteHelper.DATABASE_TABLE, null, values);

        database.close();
    }



    public void apagaContato(Contato c)
    {
        database=dbHelper.getWritableDatabase();
        database.delete(SQLiteHelper.DATABASE_TABLE, SQLiteHelper.KEY_ID + "="
                + c.getId(), null);
        database.close();
    }
}
