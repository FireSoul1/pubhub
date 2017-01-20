package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * Created by Jacob on 4/26/16.
 */
public class Brothers extends Activity implements View.OnClickListener {


    Button barBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brothers);

        //setup the Button
        //barBut = (Button)findViewById(R.id.checkin);
        //barBut.setOnClickListener(this);


        //update the ScollView with the data
        LinearLayout ll = (LinearLayout)findViewById(R.id.LLbarscrollview);

        //get the specials
        String specials = BarUpdater.getSpecials("brothers");

        //add the specials here!
        TextView tv = new TextView(this);
        tv.setText("Thrist Thursday deals!");
        tv.setAllCaps(true);
        tv.setTextSize(56);
        tv.setGravity(Gravity.CENTER);
        ll.addView(tv);

        tv = new TextView(this);
        tv.setText("Drink Specials\n$2 Bud Light\n$3 32oz quad wells\n$4 32oz flavored LITs\n\nShots!\n$1 o bombs\n$4 Vegas bombs\n$2.50 Fireball");
        tv.setTextSize(25);

        tv.setTextColor(Color.WHITE);
        ll.addView(tv);

        //a cooler progress bar
        WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setTopTitle("Hoppin'!");
        mWaveLoadingView.setTopTitleSize(14);

        mWaveLoadingView.setBottomTitle("Lame...");
        mWaveLoadingView.setBottomTitleSize(14);

        mWaveLoadingView.setBorderWidth(10);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.rgb(255, 128, 0));
        mWaveLoadingView.setProgressValue(80);
        mWaveLoadingView.setBorderColor(Color.BLACK);




    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bbrotherback)
        {
            Intent i = new Intent(Brothers.this, Bar.class);
            startActivity(i);
        }
        /*
        else if(v.getId() == R.id.checkin) {
            //change the button text
            if(barBut.getText().toString().charAt(0) == 'C') {
                barBut.setText("I left!");
                Log.d("BROTHERs","Checking in");
                BarUpdater.checkRequest("harrys", 1);

            }
            else {
                barBut.setText("Check In");
                BarUpdater.checkRequest("brothers", -1);
            }
        }
        */
        else
            Log.d("BROTHERs","Nothing");

        //update the circle progress Bar


    }
}