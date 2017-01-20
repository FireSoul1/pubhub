package barring.alcohol;

/**
 * Created by Rishabh on 4/7/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.security.acl.*;

public class GroupFeatures extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupfeatures);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.Bgroupback)
        {
            Intent i = new Intent(GroupFeatures.this, GroupChat.class);
            startActivity(i);
        }
        else if(v.getId() == R.id.b_makePoll)
        {

        }
    }
}
