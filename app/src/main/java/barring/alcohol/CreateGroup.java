package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Josh on 4/14/2016.
 */
public class CreateGroup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creategroup);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.Baddgroupback) {
            Intent i = new Intent(CreateGroup.this, GroupList.class);
            startActivity(i);
        } else if (v.getId() == R.id.Bcreategroupcreate) {


            EditText groupname = (EditText) findViewById(R.id.ETnewgroupname);
            final String groupnamestr = groupname.getText().toString();

            final Group newGroup = new Group(groupnamestr, SignUp.mainUser.getEmail());

            final Firebase ref = new Firebase("https://pubhubpurdue.firebaseio.com");

            //Adds the group to the user
            final User current = FireStore.getUserByName(SignUp.mainUser.getEmail());
            ref.authWithPassword(SignUp.mainUser.getEmail(), SignUp.mainUser.getPassword(), new Firebase.AuthResultHandler() {
                public void onAuthenticated(AuthData authData) {
                    //Add the group to the users list of groups
                    //current.addGroup(groupnamestr);
                    //String newGroups = current.getGroups() + groupnamestr + ";";
                    //current.setGroups(newGroups);
                    //current.setNumOfGroups(current.getNumOfGroups() + 1);

                    SignUp.mainUser.addGroup(groupnamestr, current);
                    SignUp.mainUser.setNumOfGroups(SignUp.mainUser.getNumOfGroups() + 1);
                    Log.d("Name", current.getName());
                    Log.d("AddGroup", current.getGroups());
                    Log.d("Attemp to add", groupnamestr);
                    //SignUp.mainUser.addGroup(groupnamestr);

                    //Add the group to firebase
                    final Firebase base = ref.child("groups").child(groupnamestr);
                    base.setValue(newGroup);


                    //Go back to group list
                    Intent i = new Intent(CreateGroup.this, GroupList.class);
                    startActivity(i);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    //showToastMessage(firebaseError.getMessage()); //this shouldn't ever happen...just a precaution
                }
            });
        }
    }
}