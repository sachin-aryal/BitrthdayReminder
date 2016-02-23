package org.application.bitrthdayreminder.Implementation.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.application.bitrthdayreminder.Design.IService;
import org.application.bitrthdayreminder.Implementation.Adapter.FriendsAdapter;
import org.application.bitrthdayreminder.Implementation.Model.Friend;
import org.application.bitrthdayreminder.Implementation.ObjectFactory.ObjectFactory;
import org.application.bitrthdayreminder.R;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setLogo(R.drawable.home_icon);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setTitle("");
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        header = (TextView) findViewById(R.id.heading);
        if(intent.getStringExtra("list")!=null){
            if(intent.getStringExtra("list").equals("all")){
                header.setText("Friend List");
                new FriendsListController(this).execute("all");
            }else if(intent.getStringExtra("list").equals("search")){
                SearchBar();
            }
            else{
                header.setText("Today's Birthday");
                new FriendsListController(this).execute("today");
            }
        }else {
            header.setText("Today's Birthday");
            new FriendsListController(this).execute("today");
        }
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
                friendList.putExtra("list", "all");
                startActivity(friendList);
                return true;
            case R.id.home:
                Intent home = new Intent(this,HomeActivity.class);
                startActivity(home);
                return true;
            case R.id.searchFriend:
                SearchBar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void SearchBar(){
        header.setVisibility(View.GONE);
        FriendsListController friendsListController = new FriendsListController(this);
        friendsListController.setWhich("autocomplete");
        friendsListController.execute("all");
        return;
    }

    public void SearchFriend(View view){
        AutoCompleteTextView hostelLocationTextView = (AutoCompleteTextView)findViewById(R.id.friendSearch);
        if(hostelLocationTextView.getText().toString().equals("")){
            hostelLocationTextView.setError("This is required field.");
            return;
        }
        header.setVisibility(View.VISIBLE);
        header = (TextView) findViewById(R.id.heading);
        header.setText("Search Result");
        ListView friend_list = (ListView)findViewById(R.id.friend_list);
        friend_list.setVisibility(View.GONE);
        TextView noResult = (TextView) findViewById(R.id.noResult);
        noResult.setVisibility(View.GONE);
        FriendsListController friendsListController = new FriendsListController(this);
        friendsListController.setWhich("searchFriend");
        friendsListController.execute(hostelLocationTextView.getText().toString());

    }


    public class FriendsListController extends AsyncTask<String, String, List<Friend>> {

        ObjectFactory objectFactory=null;
        Activity activity=null;
        private String which="";

        public FriendsListController(HomeActivity homeActivity){
            this.activity=homeActivity;
        }

        public void setWhich(String which){
            this.which=which;
        }

        @Override
        protected List<Friend> doInBackground(String... params) {
            objectFactory = new ObjectFactory();
            IService iService = objectFactory.getServiceObject("friends");
            Friend friend;
            if(params[0].equals("all")){
                friend = new Friend(0,"","all","","","");
            }else if(which.equals("searchFriend")){
                friend = new Friend(0,params[0],"searchFriend","","","");
            }else{
                friend = new Friend(0,"","","","","");
            }
            List<Object> objectList = iService.retrieve(friend,activity);

            List<Friend> friendList = new ArrayList<>();

            for(Object object:objectList){
                friendList.add((Friend)object);
            }

            return friendList;
        }
        @Override
        protected void onPostExecute(final List<Friend> friends) {
            super.onPostExecute(friends);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(which.equals("autocomplete")){
                        AutoCompleteTextView friendList = (AutoCompleteTextView) findViewById(R.id.friendSearch);
                        friendList.setVisibility(View.VISIBLE);
                        Button searchButton = (Button)findViewById(R.id.searchButton);
                        searchButton.setVisibility(View.VISIBLE);

                        String [] friendNameList = new String[friends.size()];
                        int i=0;
                        for(Friend friend:friends){
                            friendNameList[i]= friend.getName();
                            i++;
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,friendNameList);
                        friendList.setAdapter(adapter);
                        ListView friend_list = (ListView)findViewById(R.id.friend_list);
                        friend_list.setVisibility(View.GONE);
                        TextView noResult = (TextView) findViewById(R.id.noResult);
                        noResult.setVisibility(View.GONE);
                    }else {
                        ListView friend_list = (ListView)findViewById(R.id.friend_list);
                        friend_list.setVisibility(View.VISIBLE);
                        TextView noResult = (TextView) findViewById(R.id.noResult);
                        noResult.setVisibility(View.VISIBLE);
                        if (friends.size() != 0) {
                            FriendsAdapter friendsAdapter = new FriendsAdapter(activity, friends);
                            ListView friendListView = (ListView) findViewById(R.id.friend_list);
                            friendListView.setAdapter(friendsAdapter);
                            noResult.setText("");
                        } else {
                            ListView friendListView = (ListView) findViewById(R.id.friend_list);
                            friendListView.setAdapter(null);
                            noResult.setText("No Result found");
                        }
                    }
                }
            });
        }

    }

}
