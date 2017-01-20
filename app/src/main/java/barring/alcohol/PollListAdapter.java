package barring.alcohol;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * Created by Josh on 4/26/2016.
 */
public class PollListAdapter extends FirebaseListAdapter<PollObject>
{
    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;
    private static final String FIREBASE_URL = "https://pubhubpurdue.firebaseio.com";

    public PollListAdapter(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, PollObject.class, layout, activity);
        this.mUsername = mUsername;
    }

    @Override
    protected void populateView(View v, final PollObject poll)
    {
        // Map a Chat object to an entry in our listview
        final Firebase ref = new Firebase(FIREBASE_URL).child("pollChat");
        String author = poll.getAuthor();
        final String thisUser = SignUp.mainUser.getEmail();
        TextView authorText = (TextView) v.findViewById(R.id.poll_author);
        authorText.setText(author + ": ");
        // If the message was sent by this user, color it differently
        if (author != null && author.equals(mUsername)) {
            authorText.setTextColor(Color.RED);
        } else {
            authorText.setTextColor(Color.BLUE);
        }

        TextView pollQ = (TextView) v.findViewById(R.id.poll_question);
        TextView pollA1 = (TextView) v.findViewById(R.id.poll_answer1);
        TextView pollA2 = (TextView) v.findViewById(R.id.poll_answer2);
        final TextView pollA1S = (TextView) v.findViewById(R.id.poll_answer1_score);
        final TextView pollA2S = (TextView) v.findViewById(R.id.poll_answer2_score);
        TextView pollT = (TextView) v.findViewById(R.id.poll_totalvotes);
        final TextView pollTS = (TextView) v.findViewById(R.id.poll_totalvotes_score);

        pollQ.setText(poll.getQuestion());
        pollA1.setText(poll.getAnswer1());
        pollA2.setText(poll.getAnswer2());
        pollT.setText("Total Votes:");

        pollA1S.setText(String.valueOf(poll.getAnswer1Score()));
        pollA2S.setText(String.valueOf(poll.getAnswer2Score()));
        pollTS.setText(String.valueOf(poll.getTotalVotes()));

        //if there is only 2 answers, there isn't any need to show the TextView's for answer3/4.
        //This is taken care of here.
        final TextView pollA3 = (TextView) v.findViewById(R.id.poll_answer3);
        final TextView pollA4 = (TextView) v.findViewById(R.id.poll_answer4);
        final TextView pollA3S = (TextView) v.findViewById(R.id.poll_answer3_score);
        final TextView pollA4S = (TextView) v.findViewById(R.id.poll_answer4_score);
        if (poll.getNumAnswers() >= 3)
        {
            //at least 3 answers, can show
            pollA3.setText(poll.getAnswer3());
            pollA3S.setText(String.valueOf(poll.getAnswer3Score()));
        }
        else
        {
            //set invisible
            LinearLayout temp = (LinearLayout)v.findViewById(R.id.LLpoll_answer3);
            temp.setVisibility(View.GONE);
        }

        if (poll.getNumAnswers() >= 4)
        {
            //at least 4 answers, can show
            pollA4.setText(poll.getAnswer4());
            pollA4S.setText(String.valueOf(poll.getAnswer4Score()));
        }
        else
        {
            //set invisible
            LinearLayout temp = (LinearLayout)v.findViewById(R.id.LLpoll_answer4);
            temp.setVisibility(View.GONE);
        }

        pollA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasVoted(poll))
                {
                    poll.addVoter(thisUser);
                    poll.incrementAnswer1();
                    pollA1S.setText(String.valueOf(poll.getAnswer1Score()));
                    pollTS.setText(String.valueOf(poll.getTotalVotes()));

                    String tempKey = poll.getKey();
                    ref.child(tempKey).setValue(poll);
                }
            }
        });

        pollA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasVoted(poll))
                {
                    poll.addVoter(thisUser);
                    poll.incrementAnswer2();
                    pollA2S.setText(String.valueOf(poll.getAnswer2Score()));
                    pollTS.setText(String.valueOf(poll.getTotalVotes()));

                    String tempKey = poll.getKey();
                    ref.child(tempKey).setValue(poll);
                }
            }
        });

        pollA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasVoted(poll))
                {
                    poll.addVoter(thisUser);
                    poll.incrementAnswer3();
                    pollA3S.setText(String.valueOf(poll.getAnswer3Score()));
                    pollTS.setText(String.valueOf(poll.getTotalVotes()));

                    String tempKey = poll.getKey();
                    ref.child(tempKey).setValue(poll);
                }
            }
        });

        pollA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasVoted(poll))
                {
                    poll.addVoter(thisUser);
                    poll.incrementAnswer4();
                    pollA4S.setText(String.valueOf(poll.getAnswer4Score()));
                    pollTS.setText(String.valueOf(poll.getTotalVotes()));

                    String tempKey = poll.getKey();
                    ref.child(tempKey).setValue(poll);
                }
            }
        });

    }

    private boolean hasVoted(PollObject poll)
    {
        String thisUser = SignUp.mainUser.getEmail();
        String voters = poll.getVoters();
        if (voters.equals(""))
        {
            return false;
        }
        String[] splitVoters = voters.split(";");
        for (int i = 0; i < splitVoters.length; i ++)
        {
            if (splitVoters[i].equals(thisUser))
            {
                return true;
            }
        }
        return false;
    }
}
