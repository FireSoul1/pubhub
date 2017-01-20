package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Josh on 4/26/2016.
 */
public class PollList extends Activity
{
    private static final String FIREBASE_URL = "https://pubhubpurdue.firebaseio.com";
    private String mUsername;
    private Firebase mFirebaseRef;
    private ValueEventListener mConnectedListener;
    private PollListAdapter mPollListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polllist);

        mUsername = SignUp.mainUser.getName();
        mFirebaseRef = new Firebase(FIREBASE_URL).child("pollChat");
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bpolllistback)
        {
            Intent i = new Intent(PollList.this, Menu.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.Bpolllistnew)
        {
            Intent i = new Intent(PollList.this, CreatePoll.class);
            startActivity(i);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Setup our view and list adapter. Ensure it scrolls to the bottom as data changes
        final ListView listView = (ListView)findViewById(R.id.LVpolllist);
        // Tell our list adapter that we only want 50 messages at a time
        mPollListAdapter = new PollListAdapter(mFirebaseRef.limit(50), this, R.layout.poll_message4, mUsername);
        listView.setAdapter(mPollListAdapter);
        mPollListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(mPollListAdapter.getCount() - 1);
            }
        });

        // Finally, a little indication of connection status
        mConnectedListener = mFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(PollList.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PollList.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
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
        mPollListAdapter.cleanup();
    }
}
