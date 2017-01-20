package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Josh on 4/6/2016.
 */
public class AddFriend extends Activity
{

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
            Intent i = new Intent(AddFriend.this, Friends.class);
            startActivity(i);
        }

    }

    private void searchUsers()
    {
        EditText editTextEmail = (EditText)findViewById(R.id.TFaddfriendsearchbyname);
        String emailstr = editTextEmail.getText().toString();

        String[] list = SignUp.mainUser.getFriends().split(";");
        for (int i = 0; i < list.length; i ++)
        {
            if (list[i].equals(emailstr))
            {
                showToastMessage("This user is already on your friends list!");
                return;
            }
        }
        if (!isValidEmail(emailstr) || emailstr.equals(""))
        {
            showToastMessage("Not a Valid Email!");
            return;
        }
        if (emailstr.equals(SignUp.mainUser.getEmail()))
        {
            showToastMessage("I'm sorry you have no friends, but you can't add yourself as a friend.");
            return;
        }

        final User temp = FireStore.getUserByName(emailstr);
        Firebase ref = new Firebase("https://pubhubpurdue.firebaseio.com");

        ref.authWithPassword(SignUp.mainUser.getEmail(), SignUp.mainUser.getPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                if (!temp.getName().equals("VanillaIce123"))
                {
                    SignUp.mainUser.addFriend(temp.getEmail());

                    showToastMessage(temp.getName() + " has been added!");
                    Intent i = new Intent(AddFriend.this, Friends.class);
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

    public boolean isValidEmail(String email)
    {
        String exp = "^[A-Za-z0-9.]+@[A-Za-z]+.[A-Za-z]+$";
        Pattern pattern = Pattern.compile(exp);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showToastMessage(String mess)
    {
        //popup message
        Toast toastmess = Toast.makeText(AddFriend.this, mess, Toast.LENGTH_SHORT);
        toastmess.show();
    }
}
