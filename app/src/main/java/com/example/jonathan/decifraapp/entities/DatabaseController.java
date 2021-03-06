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
        values.put(CreateDatabase.getIDAPI(), idApi);
        values.put(CreateDatabase.getNAME(), name);
        values.put(CreateDatabase.getARTIST(), artist);
        values.put(CreateDatabase.getTAB(), tab);
        values.put(CreateDatabase.getTYPE(), type);

        try {
            insertResult = db.insert(CreateDatabase.getTABLE(), null, values);

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
                CreateDatabase.getID(),
                CreateDatabase.getIDAPI(),
                CreateDatabase.getNAME(),
                CreateDatabase.getARTIST(),
                CreateDatabase.getTAB(),
                CreateDatabase.getTYPE()
        };
        db = createDb.getReadableDatabase();
        cursor = db.query(CreateDatabase.getTABLE(), campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Boolean alreadyExists(String idApi, String typeTab)
    {
        Boolean result;
        String query = "Select * from " + CreateDatabase.getTABLE() +
                       " where " + CreateDatabase.getIDAPI() + " = '" + idApi + "'" +
                       "   and " + CreateDatabase.getTYPE() + " = '" + typeTab + "'";

        Cursor cursor = createDb.getReadableDatabase().rawQuery(query, null);
        result = cursor.getCount() > 0;

        cursor.close();

        return result;
    }

    public Boolean deleteCipher(String id)
    {
        boolean result = true;
        db = createDb.getWritableDatabase();
        try {
            db.delete(CreateDatabase.getTABLE(), CreateDatabase.getID() + " = " + id, null);
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
