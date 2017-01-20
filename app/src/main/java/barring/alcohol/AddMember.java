package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by nickz on 4/26/16.
 */


public class AddMember extends Activity{
    public String newMember;
    String tempGroupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend);
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Baddfriendadd)
        {
            searchUsers();
        }
        else if (v.getId() == R.id.Baddfriendcancel)
        {
            Intent i = new Intent(AddMember.this, Members.class);
            startActivity(i);
        }

    }

    private void searchUsers()
    {
        EditText editTextEmail = (EditText)findViewById(R.id.TFaddfriendsearchbyname);
        final String emailstr = editTextEmail.getText().toString();
        final String groupName = getIntent().getStringExtra("GroupName");

        final User temp = FireStore.getUserByName(emailstr);
        Firebase ref = new Firebase("https://pubhubpurdue.firebaseio.com");

        ref.authWithPassword(SignUp.mainUser.getEmail(), SignUp.mainUser.getPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                if (!temp.getName().equals("VanillaIce123"))
                {
                    //SignUp.mainUser.addFriend(temp.getEmail());


                    Firebase ref = new Firebase("https://pubhubpurdue.firebaseio.com").child("groups").child("members");
                    ref.push().setValue(temp.getEmail());
                    newMember = temp.getEmail();

                    Bundle b = new Bundle();
                    b.putString("group", tempGroupName);
                    //Members.members = Members.members + newMember + "\n";
                    //Members.t.setText("Hey it works");
                    showToastMessage(temp.getName() + " has been added!");
                    Members.myGroup.addMember(emailstr);

                    SignUp.mainUser.addGroup(groupName, temp);

                    Intent i = new Intent(AddMember.this, Members.class);
                    i.putExtras(b);
                    i.putExtra("group", groupName);
                    startActivity(i);
                }
                else
                {
                    showToastMessage("User Not Found, Try Again!");
                }
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                showToastMessage(firebaseError.getMessage()); //this shouldn't ever happen...just a precaution
            }
        });

    }

    private void showToastMessage(String mess)
    {
        //popup message
        Toast toastmess = Toast.makeText(AddMember.this, mess, Toast.LENGTH_SHORT);
        toastmess.show();
    }
}
