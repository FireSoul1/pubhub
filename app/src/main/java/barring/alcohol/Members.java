package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by nickz on 3/28/2016.
 */


public class Members extends Activity
{
    String groupName = "";
    public static Group myGroup = null;
    //public static String members;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members);

        final Group tempGroup;
        final String tempGroupName = getIntent().getStringExtra("group");

        if (myGroup == null) {
            groupName = tempGroupName;
            tempGroup = FireStore.getGroupByName(tempGroupName);
            myGroup = tempGroup;
        }
        else {
            tempGroup = myGroup;
            groupName = tempGroupName;
        }

        Log.d("Check Group", "Group name: " + tempGroup.getName());
        Log.d("Check Group", "Group members: " + tempGroup.getMembers());
        Log.d("Check Group", "Number of group members: " + tempGroup.getNumOfMembers());

        final LinearLayout memberList = (LinearLayout)findViewById(R.id.LLmemberslist);
        final Activity same = this;

        Firebase ref = new Firebase("https://pubhubpurdue.firebaseio.com");
        ref.authWithPassword(SignUp.mainUser.getEmail(), SignUp.mainUser.getPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                String memberConcat = tempGroup.getMembers();
                Log.d("members", "Members are: " + memberConcat);
                if (memberConcat.equals(""))
                    return;

                final String[] memberSplit = memberConcat.split(";");

                Log.d("length", "Member count is " + memberSplit.length);

                for (int i = 0; i < memberSplit.length; i++) {
                    Log.d("Check what is passed", "User passed is " + memberSplit[i]);
                    final User memberUser = FireStore.getUserByName(memberSplit[i]);
                    final TextView tempTV = new TextView(same);
                    tempTV.setText(memberSplit[i]);
                    tempTV.setTextSize(30);
                    memberList.addView(tempTV);
                    tempTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Members.this, MemberProfile.class);
                            i.putExtra("memberemail", memberUser.getEmail());
                            i.putExtra("groupname", tempGroupName);
                            startActivity(i);
                        }
                    });
                }
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {

            }
        });



    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bfriendsback)
        {
            Intent i = new Intent(Members.this, GroupChat.class);
            i.putExtra("GroupName", groupName);
            startActivity(i);
        }

        if (v.getId() == R.id.Bmembersadd)
        {
            Intent i = new Intent(Members.this, AddMember.class);
            i.putExtra("GroupName", groupName);
            startActivity(i);
        }
    }
}
