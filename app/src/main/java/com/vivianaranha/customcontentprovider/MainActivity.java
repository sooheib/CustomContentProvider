package com.vivianaranha.customcontentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);
    }

    public void addUser(View view) {

        ContentValues values = new ContentValues();
        values.put(UsersProvider.NAME, userName.getText().toString());
        values.put(UsersProvider.ADDRESS, address.getText().toString());

        Uri uri = getContentResolver().insert(UsersProvider.CONTENT_URI, values);
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void viewDetails(View view) {
        Cursor c = getContentResolver().query(UsersProvider.CONTENT_URI, null, null, null, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UsersProvider.NAME)) +
                                ", " +  c.getString(c.getColumnIndex( UsersProvider.ADDRESS)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());

        }
    }

    public void getAddress(View view) {
        String[] proj = {UsersProvider.ADDRESS};
        String[] args = {userName.getText().toString()};
        Cursor c = getContentResolver().query(UsersProvider.CONTENT_URI, proj, UsersProvider.NAME+" = ?", args, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UsersProvider.ADDRESS)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());

        }
    }

    public void getUserId(View view) {
        String[] proj = {UsersProvider.UID};
        String[] args = {userName.getText().toString(), address.getText().toString()};
        Cursor c = getContentResolver().query(UsersProvider.CONTENT_URI, proj, UsersProvider.NAME+" = ? AND "+ UsersProvider.ADDRESS+" = ?", args, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UsersProvider.UID)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());

        }

    }

    public void updateUser(View view) {
        String currentName = userName.getText().toString();
        String newName = address.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersProvider.NAME, newName);


        String[] whereArgs = {currentName};
        int count = getContentResolver().update(UsersProvider.CONTENT_URI, contentValues, UsersProvider.NAME + " =? ", whereArgs);

        Toast.makeText(this, ""+count+" users Updated", Toast.LENGTH_LONG).show();

    }

    public void updateUserAddress(View view) {
        String theUser = userName.getText().toString();
        String newAddress = address.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersProvider.ADDRESS, newAddress);

        String[] whereArgs = {theUser};
        int count = getContentResolver().update(UsersProvider.CONTENT_URI, contentValues, UsersProvider.NAME + " =? ", whereArgs);

        Toast.makeText(this, ""+count+" users Updated", Toast.LENGTH_LONG).show();
    }

    public void deleteUser(View view) {

        String theUser = userName.getText().toString();

        String[] whereArgs = {theUser};
        int count = getContentResolver().delete(UsersProvider.CONTENT_URI, UsersProvider.NAME + "= ?", whereArgs);

        Toast.makeText(this, ""+count+" users Deleted", Toast.LENGTH_LONG).show();

    }
}
