package barring.alcohol;

/**
 * Created by GbearTheGenius on 4/27/16.
 */

import android.util.Log;

/**
 * This class <sp>BarUpdater<sp/> will get the data on the specials of the Bars that we need, mostly using
 * an AsyncTasks, or thru the pre defined ones.
 */
public class BarUpdater {

    public static void checkRequest(String bar, int num)
    {
        Log.d("Fire", bar+" "+num);
        //Firestore call
        FireStore.checkIn(bar);

        //force it to finish
        FireStore.getUserByName("testuser@gmail.com");

        Log.d("Fire", "Ther are this many: " + FireStore.numBar);

        //Increment
        FireStore.incrementBarInt(num);
        Log.d("Fire", "There are NOW this many: " + FireStore.numBar);

        //update the DB
        FireStore.setNumber(bar);
    }

    public static String getSpecials(String bar) {
        String ret = "";

        //TODO hard the specials, or parse the websites


        return ret;
    }


}
