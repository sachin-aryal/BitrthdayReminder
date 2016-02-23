package org.application.bitrthdayreminder.Implementation.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import org.application.bitrthdayreminder.Design.IService;
import org.application.bitrthdayreminder.Implementation.Model.Friend;
import org.application.bitrthdayreminder.Implementation.ObjectFactory.ObjectFactory;
import org.application.bitrthdayreminder.R;

import java.util.Calendar;

public class AddFriendActivity extends AppCompatActivity implements OnClickListener{
    private ObjectFactory objectFactory=null;
    private Button ib;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private EditText et;
    private static final int SELECT_PICTURE = 1;
    static final int DATE_DIALOG_ID = 999;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ib = (Button) findViewById(R.id.dobButton);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        et = (EditText) findViewById(R.id.friend_dob);
        ib.setOnClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayShowHomeEnabled(true);
        }

    }
    @Override
    public void onClick(View v) {
        showDialog(0);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            et.setText(selectedYear+ "-" + (selectedMonth + 1) + "-"
                    + selectedDay);
        }
    };

    public void UploadImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                File file = new File(selectedImagePath);
                Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
                ImageView imageview = (ImageView) findViewById(R.id.picture);
                imageview.setImageBitmap(bmp);

            }
        }
    }

    public String getPath(Uri uri) {
        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addFriend:
                Intent newFriend = new Intent(this,AddFriendActivity.class);
                startActivity(newFriend);
                return true;
            case R.id.friendList:
                Intent friendList = new Intent(this,HomeActivity.class);
                friendList.putExtra("list","all");
                startActivity(friendList);
                return true;
            case R.id.home:
                Intent home = new Intent(this,HomeActivity.class);
                startActivity(home);
                return true;
            case R.id.searchFriend:
                Intent search = new Intent(this,HomeActivity.class);
                search.putExtra("list","search");
                startActivity(search);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void AddFriend(View view){
        TextView nameView = (TextView) findViewById(R.id.friend_name);
        TextView contactView = (TextView) findViewById(R.id.friend_contact);
        TextView locationView = (TextView) findViewById(R.id.friend_location);
        TextView facebookIdView = (TextView) findViewById(R.id.friend_facebookId);
        TextView dobView = (TextView) findViewById(R.id.friend_dob);
        new AddFriendTask(this).execute(
                nameView.getText().toString(),
                contactView.getText().toString(),
                locationView.getText().toString(),
                facebookIdView.getText().toString(),
                dobView.getText().toString());
    }


    public class AddFriendTask extends AsyncTask<String,String,String>{

        private Activity activity;

        public AddFriendTask(AddFriendActivity friendActivity){
           this.activity = friendActivity;
        }

        @Override
        protected String doInBackground(String... params) {
            Friend friend = new Friend(0,params[0],params[4],params[1],params[3],params[2]);
            objectFactory = new ObjectFactory();
            IService iService = objectFactory.getServiceObject("friends");
            return iService.store(friend,activity);
        }
        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(result.equals("true")){
                        Toast.makeText(activity, "Friend Successfully added to the list", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(activity,"Error while adding friend.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
