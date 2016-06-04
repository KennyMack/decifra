package com.example.jonathan.decifraapp.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by otequio on 02/06/16.
 */
public class DatabaseController {
    private SQLiteDatabase db;
    private CreateDatabase createDb;

    public DatabaseController(Context context){
        createDb = new CreateDatabase(context);
    }

    public String insert(String idApi, String name, String artist, String tab, String type){
        ContentValues values;
        long resultado;

        db = createDb.getWritableDatabase();
        values = new ContentValues();
        values.put(createDb.getIDAPI(), idApi);
        values.put(createDb.getNAME(), name);
        values.put(createDb.getARTIST(), artist);
        values.put(createDb.getTAB(), tab);
        values.put(createDb.getTYPE(), type);

        resultado = db.insert(createDb.getTABLE(), null, values);
        db.close();

        if (resultado == -1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso";

    }

    public Cursor carregaDados(){
        Cursor cursor;
        String[] campos =  {
                createDb.getID(),
                createDb.getIDAPI(),
                createDb.getNAME(),
                createDb.getARTIST(),
                createDb.getTAB(),
                createDb.getTYPE()
        };
        db = createDb.getReadableDatabase();
        cursor = db.query(createDb.getTABLE(), campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
}
