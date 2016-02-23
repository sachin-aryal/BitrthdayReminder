package org.application.bitrthdayreminder.Implementation.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.application.bitrthdayreminder.Constants;

/**
 * Created by sachin on 2/16/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String FRIENDS_TABLE =
            "CREATE TABLE " + FriendsEntry.FRIEND_TABLE + " (" +
                    FriendsEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + Constants.COMMA_SEP +
                    FriendsEntry.COLUMN_NAME_NAME +Constants.SPACE+ Constants.INTEGER_TYPE + Constants.COMMA_SEP +
                    FriendsEntry.COLUMN_NAME_DOB +Constants.SPACE+ Constants.TEXT_TYPE + Constants.COMMA_SEP +
                    FriendsEntry.COLUMN_NAME_CONTACT +Constants.SPACE+ Constants.TEXT_TYPE + Constants.COMMA_SEP +
                    FriendsEntry.COLUMN_NAME_FACEBOOK +Constants.SPACE+ Constants.TEXT_TYPE + Constants.COMMA_SEP +
                    FriendsEntry.COLUMN_NAME_LOCATION +Constants.SPACE+ Constants.TEXT_TYPE + " )";

    public static final String MESSAGE_TABLE=
            "CREATE TABLE "+ MessageEntry.MESSAGE_TABLE+" ("+
                    MessageEntry.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT "+Constants.COMMA_SEP+
                    MessageEntry.COLUMN_NAME_Messsage+Constants.SPACE+ Constants.TEXT_TYPE+ ")";

    public static final String USERINFO_TABLE=
            "CREATE TABLE "+ UserEntry.USER_TABLE+" ("+
                    UserEntry.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT "+Constants.COMMA_SEP+
                    UserEntry.COLUMN_NAME_USERNAME+Constants.SPACE+ Constants.TEXT_TYPE+ Constants.COMMA_SEP+
                    UserEntry.COLUMN_NAME_PASSWORD+Constants.SPACE+ Constants.TEXT_TYPE+")";

    

    public DbHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, 1);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FRIENDS_TABLE);
        db.execSQL(MESSAGE_TABLE);
        db.execSQL(USERINFO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
