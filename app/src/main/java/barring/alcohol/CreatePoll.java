package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Josh on 4/26/2016.
 */
public class CreatePoll extends Activity
{
    private String tempAuthor;
    private static final String FIREBASE_URL = "https://pubhubpurdue.firebaseio.com";
    private Firebase mFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createpoll);

        tempAuthor = SignUp.mainUser.getName();

        mFirebaseRef = new Firebase(FIREBASE_URL).child("pollChat");
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bpollcancel)
        {
            Intent i = new Intent(CreatePoll.this, PollList.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.Bpollsend)
        {
            EditText question = (EditText)findViewById(R.id.TFpollquestion);
            EditText answer1 = (EditText)findViewById(R.id.TFpollanswer1);
            EditText answer2 = (EditText)findViewById(R.id.TFpollanswer2);
            EditText answer3 = (EditText)findViewById(R.id.TFpollanswer3);
            EditText answer4 = (EditText)findViewById(R.id.TFpollanswer4);

            String questionStr = question.getText().toString();
            String answer1Str = answer1.getText().toString();
            String answer2Str = answer2.getText().toString();
            String answer3Str = answer3.getText().toString();
            String answer4Str = answer4.getText().toString();

            if (questionStr.equals("") || answer1Str.equals("") || answer2Str.equals(""))
            {
                showToastMessage("Question, Answer 1, and Answer 2 fields are required.");
            }

            if (!answer3Str.equals("") && !answer4Str.equals(""))
            {
                //createpoll with 4 answers
                Firebase tempRef = mFirebaseRef.push();
                String key  = tempRef.getKey();
                PollObject np = new PollObject(key, tempAuthor, questionStr, answer1Str, answer2Str, answer3Str, answer4Str);
                tempRef.setValue(np);
            }
            else if (!answer3Str.equals("") && answer4Str.equals(""))
            {
                //createpoll with 3 answers
                Firebase tempRef = mFirebaseRef.push();
                String key  = tempRef.getKey();
                PollObject np = new PollObject(key, tempAuthor, questionStr, answer1Str, answer2Str, answer3Str);
                tempRef.setValue(np);
            }
            else
            {
                //createpoll with 2 answers
                Firebase tempRef = mFirebaseRef.push();
                String key  = tempRef.getKey();
                PollObject np = new PollObject(key, tempAuthor, questionStr, answer1Str, answer2Str);
                tempRef.setValue(np);
            }

            Intent i = new Intent(CreatePoll.this, PollList.class);
            startActivity(i);
        }
    }

    private void showToastMessage(String mess)
    {
        //popup message
        Toast toastmess = Toast.makeText(CreatePoll.this, mess, Toast.LENGTH_SHORT);
        toastmess.show();
    }

}
