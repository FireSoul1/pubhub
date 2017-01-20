package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Josh on 4/6/2016.
 */
public class About extends Activity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Baboutback)
        {
            Intent i = new Intent(About.this, Menu.class);
            startActivity(i);
        }

    }


}
