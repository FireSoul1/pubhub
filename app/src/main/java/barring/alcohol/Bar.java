package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ProgressBar;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * Created by Josh on 4/14/2016.
 */
public class Bar extends Activity
{
    ImageButton where_else_bttn;
    ImageButton brothers_bttn;
    ImageButton harrys_bttn;
    ImageButton cactus_bttn;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar);

        addListenerOnButton();


        //a cooler progress bar
        WaveLoadingView mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);
        mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
        mWaveLoadingView.setTopTitle("Hoppin'!");
        mWaveLoadingView.setTopTitleSize(14);

        mWaveLoadingView.setBottomTitle("Lameeeee");
        mWaveLoadingView.setBottomTitleSize(14);

        mWaveLoadingView.setBorderWidth(10);
        mWaveLoadingView.setAmplitudeRatio(60);
        mWaveLoadingView.setWaveColor(Color.rgb(255, 128, 0));
        mWaveLoadingView.setBorderColor(Color.BLACK);

        //test Progress Bar
        new Progress(mWaveLoadingView).execute(100);

    }


    public void onClick(View v)
    {
        if (v.getId() == R.id.Bbarback)
        {
            Intent i = new Intent(Bar.this, Menu.class);
            startActivity(i);
        }
//        else if (v.getId() ==  R.id.Bbaradd)
//        {
//            //tbc
//        }
    }

    public void addListenerOnButton() {

        where_else_bttn = (ImageButton) findViewById(R.id.where_else);

        where_else_bttn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bar.this, WhereElse.class);
                startActivity(i);
            }

        });

        brothers_bttn = (ImageButton) findViewById(R.id.brothers);

        brothers_bttn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bar.this, Brothers.class);
                startActivity(i);
            }

        });

        harrys_bttn = (ImageButton) findViewById(R.id.harrys);

        harrys_bttn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bar.this, Harrys.class);
                startActivity(i);
            }

        });

        cactus_bttn = (ImageButton) findViewById(R.id.cactus);

        cactus_bttn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Bar.this, Cactus.class);
                startActivity(i);
            }

        });


    }

}
class Progress extends AsyncTask<Integer, Integer, String>
{
    WaveLoadingView progressBar;

    public Progress(WaveLoadingView p) { progressBar = p; }

    @Override
    protected String doInBackground(Integer... params) {

        for(int u = 0; u <= params[0]; u++) {
            try {
                Thread.sleep(1000);
                publishProgress(u);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Integer...values) {

        progressBar.setProgressValue(values[0]);
    }


}

