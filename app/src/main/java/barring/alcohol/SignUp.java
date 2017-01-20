package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.*;

import java.util.Map;

/**
 * Created by Josh on 2/20/2016.
 */
public class SignUp extends Activity
{
    public static MainUser mainUser;

    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bsignupsignup)
        {
            EditText name = (EditText)findViewById(R.id.TFsignupname);
            EditText email = (EditText)findViewById(R.id.TFsignupemail);
            EditText username = (EditText)findViewById(R.id.TFsignupusername);
            EditText pass1 = (EditText)findViewById(R.id.TFsignuppassword1);
            EditText pass2 = (EditText)findViewById(R.id.TFsignuppassword2);

            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String usernamestr = username.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();


            if (namestr.equals(""))
            {
                showToastMessage("Must enter a name!");
            }
            else if (emailstr.equals(""))
            {
                showToastMessage("Must enter an email!");
            }
            else if (usernamestr.equals(""))
            {
                showToastMessage("Must enter an username!");
            }
            else if (emailstr.equals(""))
            {
                showToastMessage("Must enter an email!");
            }
            else if (pass1str.equals("") || pass2str.equals(""))
            {
                showToastMessage("Must enter both passwords!");
            }
            else if (!pass1str.equals(pass2str))
            {
                showToastMessage("Passwords don't match!");
            }
            else if (!isValidPassword(pass1str))
            {
                showToastMessage("Password needs to be Alphanumeric and greater than 8 characters long!");
            }
            else
            {
                mainUser = new MainUser();
                mainUser.setName(namestr);
                mainUser.setEmail(emailstr);
                mainUser.setGroups("");
                mainUser.setNumOfGroups(0);
                mainUser.setPassword(pass1str);
                mainUser.setUserName(usernamestr);
                mainUser.setPic("http://img.stockfresh.com/files/s/shockfactor/x/89/731968_93993468.jpg");
                mainUser.setFriendsInit();

                User u = new User();
                u.setName(namestr);
                u.setNumOfGroups(0);
                u.setEmail(emailstr);
                u.setGroups("");
                u.setFriends("");
                u.setUserName(usernamestr);
                u.setPic("http://img.stockfresh.com/files/s/shockfactor/x/89/731968_93993468.jpg");

                //add to the user section
                FireStore.addUser(u, pass1str);

                showToastMessage("Welcome!");

                Intent i = new Intent(SignUp.this, Display.class);
                startActivity(i);
                System.out.println("Task was just executed!!");
            }
        }
        else if (v.getId() == R.id.Bsignupback)
        {
            Intent i = new Intent(SignUp.this, MainActivity.class);
            startActivity(i);
        }
    }

    private void showToastMessage(String mess)
    {
        //popup message
        Toast toastmess = Toast.makeText(SignUp.this, mess, Toast.LENGTH_SHORT);
        toastmess.show();
    }

    private boolean isValidPassword(String pass)
    {
        return pass.matches("([A-Za-z0-9])*") && (pass.length() > 8 );
    }
}
