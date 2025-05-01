package com.oscarmena.formulariocedula;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{
    public static final String DB_NAME="oscarmena";
    public static final String TABLE_NAME="oscar";
    public static final int DB_VERSION=1;
    public static final String ID_COL="id";
    public static final String FIRST_COL="oscarmena";


    public DBHandler(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIRST_COL + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long ingresar(String documento)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRST_COL, documento);
        long id = db.insert(TABLE_NAME, null , values);
        return id;
    }

    public String consultar(){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {ID_COL, FIRST_COL};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            //int cid = cursor.getInt(cursor.getColumnIndex(ID_COL));
            int cid = cursor.getInt(0);
            String document = cursor.getString(1);
            buffer.append(cid + "   " + document +" \n");
        }
        return buffer.toString();
    }

}
