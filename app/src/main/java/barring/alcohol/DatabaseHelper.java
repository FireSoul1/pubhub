package barring.alcohol;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Josh on 2/20/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMNN_ID = "id";
    private static final String COLUMNN_NAME = "name";
    private static final String COLUMNN_EMAIL = "email";
    private static final String COLUMNN_USERNAME = "username";
    private static final String COLUMNN_PASSWORD = "password";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table "+TABLE_NAME+" (id integer primary key not null , " +
            "name text not null , email text not null , username text not null , password text not null);";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);

        this.onCreate(db);
    }

    public void insertContact(MainUser c)
    {
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        String query = "select * from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMNN_ID, count);
        values.put(COLUMNN_NAME, c.getName());
        values.put(COLUMNN_EMAIL, c.getEmail());
        values.put(COLUMNN_USERNAME, c.getUserName());
        values.put(COLUMNN_PASSWORD, c.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String searchPass(String username)
    {
        db = this.getReadableDatabase();
        String query = "select username, password from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "not found";
        if (cursor.moveToFirst())
        {
            do
            {
                a = cursor.getString(0);
                if (a.equals(username))
                {
                    b = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        db.close();
        return b;
    }
}
