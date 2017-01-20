package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * Created by Josh on 3/3/2016.
 */
public class Profile extends Activity {

    private String username;
    private String email;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.profile);

        WebView friendPic = (WebView) findViewById(R.id.WVprofilepicture);
        friendPic.loadUrl(SignUp.mainUser.getPic());

        //Get the name of the current user
        TextView tv1 = (TextView) findViewById(R.id.TFprofilename);
        tv1.setText(SignUp.mainUser.getName());

        //Get the email of the current user
        TextView tv2 = (TextView) findViewById(R.id.TFprofileemail);
        tv2.setText(SignUp.mainUser.getEmail());

        //Get the username of the current user
        TextView tv3 = (TextView) findViewById(R.id.TFprofileusername);
        tv3.setText(SignUp.mainUser.getUserName());
    }



    public void onClick(View v) {
        if (v.getId() == R.id.Bprofileback)
        {
            Intent i = new Intent(Profile.this, Menu.class);
            startActivity(i);
        }
    }
}

