package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Josh on 4/6/2016.
 */
public class GroupList extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);

        final LinearLayout friendsList = (LinearLayout)findViewById(R.id.LLfriendslist);


        //Log.d("numofGroups", "NUM OF GROUPS: " + SignUp.mainUser.getNumOfGroups());
        final Firebase ref = new Firebase("https://pubhubpurdue.firebaseio.com");
        final User current = FireStore.getUserByName(SignUp.mainUser.getEmail());
        final Activity same = this;

        ref.authWithPassword(SignUp.mainUser.getEmail(), SignUp.mainUser.getPassword(), new Firebase.AuthResultHandler() {
            public void onAuthenticated(AuthData authData) {
                //final int groupCount = current.getNumOfGroups();
                Log.d("groupName", "Group name is " + current.getName());
                //Log.d("groupCount", "GroupCount is " + groupCount);
                Log.d("getGroups", "GetGroups returns: " + current.getGroups());
                final String[] allGroups = current.getGroups().split(";");
                Log.d("length", "Group Length is " + allGroups[0]);
                //int groupCount = StringUtils.countMatches(current.getGroups(), ";");

                if(!current.getGroups().equals("")) {
                    for (int i = 0; i < allGroups.length; i++) {
                        final Group currentGroup = FireStore.getGroupByName(allGroups[i]);

                        Log.d("currentGroup", "CurrentGroup is: " + currentGroup.getName());
                        final TextView tempTV = new TextView(same);
                        tempTV.setText(allGroups[i]);
                        tempTV.setTextSize(30);
                        tempTV.setTextColor(Color.WHITE);
                        friendsList.addView(tempTV);
                        tempTV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(GroupList.this, GroupChat.class);
                                i.putExtra("GroupName", currentGroup.getName());
                                startActivity(i);
                            }
                        });
                    }
                }
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                //this shouldn't ever happen...just a precaution
            }
        });



    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bgroupback)
        {
            Intent i = new Intent(GroupList.this, Menu.class);
            startActivity(i);
        }

        else if (v.getId() == R.id.Bgroupsadd)
        {
            Intent i = new Intent(GroupList.this, CreateGroup.class);
            startActivity(i);
        }
    }
}
