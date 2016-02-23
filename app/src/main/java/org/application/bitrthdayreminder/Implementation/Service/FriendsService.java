package org.application.bitrthdayreminder.Implementation.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.application.bitrthdayreminder.Constants;
import org.application.bitrthdayreminder.Design.IService;
import org.application.bitrthdayreminder.Implementation.Database.DbHelper;
import org.application.bitrthdayreminder.Implementation.Database.FriendsEntry;
import org.application.bitrthdayreminder.Implementation.Model.Friend;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sachin on 2/16/2016.
 */
public class FriendsService implements IService {
    SQLiteDatabase db = null;

    @Override
    public String store(Object object,Context context) {

        Friend storeFriend = (Friend)object;
        db = new DbHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FriendsEntry.COLUMN_NAME_NAME, storeFriend.getName());
        values.put(FriendsEntry.COLUMN_NAME_CONTACT, storeFriend.getContact());
        values.put(FriendsEntry.COLUMN_NAME_LOCATION, storeFriend.getLocation());
        values.put(FriendsEntry.COLUMN_NAME_FACEBOOK, storeFriend.getFacebookId());
        values.put(FriendsEntry.COLUMN_NAME_DOB, storeFriend.getDob());
        try{
            db.insert(FriendsEntry.FRIEND_TABLE, null, values);
            db.close();
            return "true";
        }catch (Exception er){
            return "false";
        }
    }

    @Override
    public List<Object> retrieve(Object object, Context context) {
        Friend friend = (Friend) object;
        db = new DbHelper(context).getReadableDatabase();
        List<Object> friendList = new ArrayList<>();
        Cursor cursor = null;
        Friend friends = null;
        String sql = "";
        if (friend.getDob().equals("searchFriend")) {
            sql = "SELECT * FROM " + FriendsEntry.FRIEND_TABLE + " WHERE " +
                    FriendsEntry.COLUMN_NAME_NAME+" LIKE ?";
            cursor = db.rawQuery(sql, new String[]{"%" + friend.getName() + "%"});
            cursor.moveToFirst();
            if (cursor.getCount() != 0) {
                do {
                        friends = new Friend(cursor.getInt(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_ID)),
                                cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_NAME)),
                                cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_DOB)),
                                cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_CONTACT)),
                                cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_FACEBOOK)),
                                cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_LOCATION)));
                        friendList.add(friends);
                } while (cursor.moveToNext());
            }
        }else {
                SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
                String today = dateFormat.format(new Date());
                String[] splitToday = today.split("-");
                sql = "SELECT * FROM " + FriendsEntry.FRIEND_TABLE;
                cursor = db.rawQuery(sql, null);
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    do {
                        if (friend.getDob().equals("all")) {
                            friends = new Friend(cursor.getInt(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_ID)),
                                    cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_NAME)),
                                    cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_DOB)),
                                    cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_CONTACT)),
                                    cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_FACEBOOK)),
                                    cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_LOCATION)));
                            friendList.add(friends);
                        } else {
                            String birthDate = cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_DOB)).trim();
                            String splitBirthDate[] = birthDate.split("-");
                            if (Integer.parseInt(splitToday[1]) == Integer.parseInt(splitBirthDate[1].trim()) &&
                                    Integer.parseInt(splitToday[2]) == Integer.parseInt(splitBirthDate[2].trim())) {
                                friends = new Friend(cursor.getInt(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_ID)),
                                        cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_NAME)),
                                        cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_DOB)),
                                        cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_CONTACT)),
                                        cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_FACEBOOK)),
                                        cursor.getString(cursor.getColumnIndex(FriendsEntry.COLUMN_NAME_LOCATION)));
                                friendList.add(friends);
                            }
                        }
                    } while (cursor.moveToNext());
                }
            }

        return friendList;
    }

    @Override
    public String delete(Object object) {
        return null;
    }

    @Override
    public String edit(Object object) {
        return null;
    }
}
