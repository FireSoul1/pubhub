package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * Created by Josh on 3/28/2016.
 */
public class Menu extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Log.d("Firestore", "Usr"+FireStore.finished);
    }

    public void onClick(View v)
    {
//        if (v.getId() == R.id.Bmenuback)
//        {
//            Intent i = new Intent(Menu.this, Display.class);
//            startActivity(i);
//        }
//        else if (v.getId() == R.id.TVmenuprofile)
//        {
//            Intent i = new Intent(Menu.this, Profile.class);
//            startActivity(i);
//        }
//        else if (v.getId() == R.id.TVmenupolls)
//        {
//            Intent i = new Intent(Menu.this, PollList.class);
//            startActivity(i);
//        }
//        else if (v.getId() == R.id.TVmenugroup)
//        {
//            Intent i = new Intent(Menu.this, GroupList.class);
//            startActivity(i);
//        }
//        else if (v.getId() == R.id.TVmenusettings)
//        {
//            Intent i = new Intent(Menu.this, Settings.class);
//            startActivity(i);
//        }
//        else if (v.getId() == R.id.TVmenuabout)
//        {
//            Intent i = new Intent(Menu.this, About.class);
//            startActivity(i);
//        }
////        else if (v.getId() == R.id.TVmenulogout)
////        {
////            //TODO idk if this is going to be an option, but ill leave it as one for now
////        }
//        else if (v.getId() == R.id.TVmenubars)
//        {
//            Intent i = new Intent(Menu.this, Bar.class);
//            startActivity(i);
//        }
    }

}
