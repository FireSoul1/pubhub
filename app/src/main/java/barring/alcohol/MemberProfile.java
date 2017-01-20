package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Josh on 4/27/2016.
 */
public class MemberProfile extends Activity
{
    String tempGroupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memberprofile);

        tempGroupName = getIntent().getStringExtra("groupname");
        final String tempMemberEmail = getIntent().getStringExtra("memberemail");
        final User tempMember = FireStore.getUserByName(tempMemberEmail);

        Firebase ref = new Firebase("https://pubhubpurdue.firebaseio.com");
        ref.authWithPassword(SignUp.mainUser.getEmail(), SignUp.mainUser.getPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                WebView friendPic = (WebView) findViewById(R.id.WVmemberprofilepic);
                friendPic.loadUrl(tempMember.getPic());

                TextView friendName = (TextView) findViewById(R.id.TVmemberprofilename);
                friendName.setText(tempMember.getName());

                TextView friendUName = (TextView) findViewById(R.id.TVmemberprofileusername);
                friendUName.setText(tempMember.getUserName());

                TextView friendEmail = (TextView) findViewById(R.id.TVmemberprofileemail);
                friendEmail.setText(tempMember.getEmail());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                //shouldn't ever get to here...
            }
        });
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bmemberprofileback)
        {
            Intent i = new Intent(MemberProfile.this, Members.class);
            i.putExtra("group", tempGroupName);
            startActivity(i);
        }
    }
}
