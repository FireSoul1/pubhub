package barring.alcohol;

/**
 * Created by GbearTheGenius on 4/18/16.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.net.*;


public class PickUpLineGenrator {

    static String Line;

    public static void main(String...args) throws IOException
     {
         URL site = new URL("http://www.gotlines.com/lines.php/2");
         BufferedReader read = new BufferedReader(new InputStreamReader(site.openStream()));

         String str = "";
         while((str = read.readLine()) != null) {
             if(str.contains("<a href='/line/"))
                 System.out.println(str.substring(str.indexOf(">")+1,str.indexOf("</a>")));
         }
         read.close();
     }
    public static void randomLine() {

        final String line = "Let's have sex.";
        AsyncTask <Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                URL site = null;

                int index = (int)(Math.random()*12);
                try {
                    //store the lines
                    ArrayList<String> arrayList = new ArrayList<>();

                    site = new URL("http://www.gotlines.com/lines.php/"+index);

                    BufferedReader read = new BufferedReader(new InputStreamReader(site.openStream()));

                    String str = "";
                    while((str = read.readLine()) != null) {
                        if(str.contains("<a href='/line/")) {
                            arrayList.add(str);
                        }
                    }
                    read.close();
                    String temp = "a";
                    //copy the reference over
                    //list = arrayList;
                    //Log.d("Pickup", "List size: "+arrayList.size()+" ");
                    if(arrayList.size() > 0 ) {
                        index = (int) (Math.random() * arrayList.size());
                        //chose what to return
                        str = arrayList.get(index);
                        temp = str.substring(str.indexOf(">") + 1,str.indexOf("</a>"));
                        line.replace("Let's have sex.", temp);
                        return temp;
                    }


                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return line;
            }

            @Override
            protected void onPostExecute(final String token) {
                PickUpLineGenrator.Line = token;
            }

        };
        task.execute();
        //Log.d("Pickupp", line);

    }


}
