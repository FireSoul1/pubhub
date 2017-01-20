package barring.alcohol;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * Created by Jacob on 4/26/16.
 */
public class WhereElse extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.where_else);

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

        LinearLayout ll = (LinearLayout)findViewById(R.id.LLbarscrollview);

        //add the specials here!
        TextView tv = new TextView(this);
        tv.setText("\nDrink Specials\n$2 Henry's Hard Soda\n$2 Heineken Bottles\n$2 Well Drinks\n$4 Pitchers\n\nShots!\n$2 Sour Apple Shots\n$2 Pineapple Bombs\n$3 Boiler Bombs");
        tv.setTextSize(25);
        tv.setTextColor(Color.WHITE);
        ll.addView(tv);
    }

    public void onClick(View v)
    {
        if (v.getId() == R.id.Bwhereback)
        {
            Intent i = new Intent(WhereElse.this, Bar.class);
            startActivity(i);
        }

    }
}
