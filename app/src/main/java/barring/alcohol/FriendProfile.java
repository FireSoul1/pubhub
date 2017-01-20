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
 * Created by Josh on 4/24/2016.
 */
public class FriendProfile extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendprofile);

        String tempFriendEmail = getIntent().getStringExtra("friendemail");
        final User tempFriend = FireStore.getUserByName(tempFriendEmail);

        Firebase ref = new Firebase("https://pubhubpurdue.firebaseio.com");
        ref.authWithPassword(SignUp.mainUser.getEmail(), SignUp.mainUser.getPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                WebView friendPic = (WebView) findViewById(R.id.WVfriendprofilepicture);
                friendPic.loadUrl(tempFriend.getPic());

                TextView friendName = (TextView) findViewById(R.id.TFfriendprofilename);
                friendName.setText(tempFriend.getName());

                TextView friendUName = (TextView) findViewById(R.id.TFfriendprofileusername);
                friendUName.setText(tempFriend.getUserName());

                TextView friendEmail = (TextView) findViewById(R.id.TFfriendprofileemail);
                friendEmail.setText(tempFriend.getEmail());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                //shouldn't ever get to here...
            }
        });
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bfriendprofileback)
        {
            Intent i = new Intent(FriendProfile.this, Friends.class);
            startActivity(i);
        }
    }

}
