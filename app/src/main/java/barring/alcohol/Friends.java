package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by Josh on 3/28/2016.
 */
public class Friends extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);

        //set up the list
        LinearLayout friendsList = (LinearLayout)findViewById(R.id.LLfriendslist);
        String friendConcat = SignUp.mainUser.getFriends();
        final String[] friendsSplit = friendConcat.split(";", 0);
        if (friendsSplit[0] == "")
        {
            return;
        }
        for (int i = 0; i < friendsSplit.length; i ++)
        {
            final User friendUser = FireStore.getUserByName(friendsSplit[i]);
            final TextView tempTV = new TextView(this);
            tempTV.setText(friendsSplit[i]);
            tempTV.setTextSize(20);
            tempTV.setTextColor(Color.WHITE);
            friendsList.addView(tempTV);
            tempTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Friends.this, FriendProfile.class);
                    i.putExtra("friendemail", friendUser.getEmail());
                    startActivity(i);
                }
            });
        }
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bfriendsback)
        {
            Intent i = new Intent(Friends.this, Display.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.Bfriendsadd)
        {
            Intent i = new Intent(Friends.this, AddFriend.class);
            startActivity(i);
        }
    }
}
