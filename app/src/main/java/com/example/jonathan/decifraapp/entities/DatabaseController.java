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

    public Boolean insert(String idApi, String name, String artist, String tab, String type){
        Boolean result = true;
        ContentValues values;
        long insertResult;

        db = createDb.getWritableDatabase();
        values = new ContentValues();
        values.put(createDb.getIDAPI(), idApi);
        values.put(createDb.getNAME(), name);
        values.put(createDb.getARTIST(), artist);
        values.put(createDb.getTAB(), tab);
        values.put(createDb.getTYPE(), type);

        try {
            insertResult = db.insert(createDb.getTABLE(), null, values);

            if (insertResult == -1)
                result = false;
        }
        catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        finally {
            db.close();
        }

        return result;

    }

    public Cursor loadCipher(){
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

    public Boolean alreadyExists(String idApi)
    {
        Boolean result;
        String query = "Select * from " + createDb.getTABLE() +
                       " where " + createDb.getIDAPI() + " = '" + idApi + "'";

        Cursor cursor = createDb.getReadableDatabase().rawQuery(query, null);
        if (cursor.getCount() <= 0)
            result = false;
        else
            result = true;

        cursor.close();

        return result;
    }

    public Boolean deleteCipher(String id)
    {
        boolean result = true;
        db = createDb.getWritableDatabase();
        try {
            db.delete(createDb.getTABLE(), createDb.getID() + " = " + id, null);
        }
        catch (Exception e) {
            e.printStackTrace();
            result = false;
        }
        finally {
            db.close();
        }

        return result;
    }


}
