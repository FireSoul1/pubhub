package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Josh on 4/6/2016.
 */
public class Settings extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bsettingsback)
        {
            Intent i = new Intent(Settings.this, Menu.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.Bsettingssave)
        {
            //tbc
        }
    }
}
