package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class Display extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String FIREBASE_URL = "https://pubhubpurdue.firebaseio.com";
    /**
     * Created by Rishabh on 4/7/2016.
     */
    private Firebase myFire;
    private Firebase prevChild;
    private String val;
    String[] messageList;
    private int mListIndex = 0;
    ListView l;
    int listSize = 50;

    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;
    private RelativeLayout layyout;

    int start = 0;
    View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



        //set up the NavBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        final View activityRootView = findViewById(R.id.templay);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 250) { // if more than 100 pixels, its probably a keyboard...
                    Log.d("Change","this change is clicked"+heightDiff);
                    resize(-1);
                    start++;
                }
                else if(start != 0){
                    Log.d("Change","this change is done "+heightDiff);
                    resize(160);
                }
            }
        });



        mUsername = SignUp.mainUser.getName();
        setTitle("Chatting as " + mUsername);

        // Setup our Firebase mFirebaseRef
        mFirebaseRef = new Firebase(FIREBASE_URL).child("communityChat");

        layyout = (RelativeLayout) findViewById(R.id.landcast);
        final RelativeLayout scrollView = layyout;


        // Setup our input methods. Enter key on the keyboard or pushing the send button
        EditText inputText = (EditText) findViewById(R.id.ETdisplaypost);

        inputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });


//        inputText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //get to the bottom of the listView
//               // resize();
////                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) scrollView.getLayoutParams();
////                params.height = 150;
////                scrollView.setLayoutParams(params);
//            }
//        });

        findViewById(R.id.Bdisplaypost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });


        //set Webview
        WebView webView = (WebView)findViewById(R.id.webView);
        Log.d("Web", SignUp.mainUser.getPic());
        webView.loadUrl(SignUp.mainUser.getPic());

        View setup = getLayoutInflater().inflate(R.layout.headermanu, null);
        WebView view = (WebView) setup.findViewById(R.id.profile);
        view.loadUrl(MainUser.getImag());

    }

    public void resize(int num) {


        if(num != -1)
            layyout.animate().setDuration(0).y(num);
        //get to the bottom of the listView
        ListView listview = (ListView) layyout.findViewById(R.id.list);
        if(num < 0) {
            (layyout.findViewById(R.id.webView)).setVisibility(View.VISIBLE);
            listview.setSelection(listview.getAdapter().getCount()-1);

        }
        else
            (layyout.findViewById(R.id.webView)).setVisibility(View.VISIBLE);


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = (ListView)findViewById(R.id.list);
        // Tell our list adapter that we only want 50 messages at a time
        mChatListAdapter = new ChatListAdapter(mFirebaseRef.limit(50), this, R.layout.chat_message, mUsername);
        listView.setAdapter(mChatListAdapter);
        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mChatListAdapter.getCount() - 1);
            }
        });
        //generate and store a line
        PickUpLineGenrator.randomLine();

        final View view = currentView;

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    //Toast.makeText(Display.this, "Connected to the Community", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Display.this, "Disconnected from the Community", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // No-op
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mFirebaseRef.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void sendMessage() {
        EditText inputText = (EditText) findViewById(R.id.ETdisplaypost);
        String input = inputText.getText().toString();
        //see if they want a pickup line
        if(input.equalsIgnoreCase("#pickupline"))
        {
           input = PickUpLineGenrator.Line;

           //send a notification

        }
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername);
            // Create a new, auto-generated child of that chat location, and save our chat data there
            mFirebaseRef.push().setValue(chat);
            inputText.setText("");
        }
        //generate a new pickupline
        PickUpLineGenrator.randomLine();

//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layyout.getLayoutParams();
//        params.height = 150;
//        layyout.setLayoutParams(params);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_friends) {
            Intent in = new Intent(Display.this, AddFriend.class);
            startActivity(in);

        } else if (id == R.id.nav_Groups) {

            Intent in = new Intent(Display.this, GroupList.class);
            startActivity(in);

        } else if (id == R.id.nav_polls) {
            Intent in = new Intent(Display.this, PollList.class);
            startActivity(in);

        } else if (id == R.id.nav_profile) {
            Intent in = new Intent(Display.this, Profile.class);
            startActivity(in);


        } else if (id == R.id.nav_settings) {
            Intent in = new Intent(Display.this, Settings.class);
            startActivity(in);

        } else if (id == R.id.nav_bars) {
            Intent in = new Intent(Display.this, Bar.class);
            startActivity(in);

        }
        return false;
    }
}
