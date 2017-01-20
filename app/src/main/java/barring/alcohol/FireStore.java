package barring.alcohol;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GbearTheGenius on 3/28/16.
 */
public class FireStore
{
    static Firebase firebase;
    final static String TAG ="Firebase";

    static User currentUser;

    /*Get the Firebase instance that you will be using for the app*/
    public static void setFire(Firebase fire) {
        firebase = fire;

        //setup the Bar part of Firebase
        String[] bars = {"brothers","harrys","cactus","whereelse"};
        for(String ii: bars) {
            Firebase firebar = firebase.child("bars").child(ii);
            //intialize the value
            BarClass set = new BarClass(0,ii);
            firebar.setValue(set);
            Log.d(TAG, "Adding "+ii+" to Bars.");
        }
    }

    /**
     * Add a User to the DB
     */
    public static void addUser(User user, String pass) {
        Log.d("Following:", "About to add to Firebase " + user.getName() + " " + user.getEmail());
        String userID = user.getEmail().replace(".","-").substring(0, user.getEmail().indexOf("@"));

        final Firebase base = firebase.child("users").child(userID);
        base.setValue(user);

        firebase.createUser(user.getEmail(), pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Log.d(TAG ,"Successfully created user account with uid: " + result.get("uid"));
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error

            }
        });

    }

    static Group result; //same as user
    public static Group getGroupByName( String groupname ) {
        final Group targetGroup = new Group("Bars", "Vanilla Ice");

        result = targetGroup;

        final Firebase using = new Firebase(firebase.toString() + "//groups//" + groupname);
        String name = using.toString();
        //get the user data
        Query que = using.orderByKey();
        que.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // User us = dataSnapshot.getValue(User.class);
                String key = dataSnapshot.getKey();

                Log.d(key, "Daya"+dataSnapshot.toString());

                switch (key) {
                    case "members":
                        targetGroup.setMembers(dataSnapshot.getValue(String.class));
                        break;
                    case "name":
                        targetGroup.setName(dataSnapshot.getValue(String.class));
                        break;
                    case "numOfMembers":
                        targetGroup.setNumOfMembers(dataSnapshot.getValue(Integer.class));
                }
                Log.d(TAG, "added was called " + targetGroup.toString());
                if (key.equals("numOfMembers")) {

                    result = targetGroup;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "changed was called");


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Log.d("Group Info",result.getName() + ", " + result.getMembers() + ", " + result.getNumOfMembers());
        return result;
    }


    /*
    * This will retrieve the User based on the data in the DB
    * NOTE: This needs to be called before you enter an Intent that requires the needed information.
    *
    * */


    static User finished;// the reference to the User that is being changed
    public static User getUserByName( String email )
    {
        if(email.equals(""))
            return new User("VanillaIce123","VanillaIce123","VanillaIce123");

        String userID = email.replace(".","-").substring(0, email.indexOf("@"));

        final User us = new User("VanillaIce123","VanillaIce123","VanillaIce123");

        finished = us;

        final Firebase using = new Firebase(firebase.toString()+"//users//"+userID);

        //get the user data
        Query que = using.orderByKey();
        que.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // User us = dataSnapshot.getValue(User.class);
                String key = dataSnapshot.getKey();

                switch (key) {
                    case "email":
                        us.setEmail(dataSnapshot.getValue(String.class));
                        break;
                    case "name":
                        us.setName(dataSnapshot.getValue(String.class));
                        break;
                    case "pic":
                        us.setPic(dataSnapshot.getValue(String.class));
                        break;
                    case "userName":
                        us.setUserName(dataSnapshot.getValue(String.class));
                        break;
                    case "friends":
                        us.setFriends(dataSnapshot.getValue(String.class));
                        break;
                    case "numOfGroups":
                        us.setNumOfGroups(dataSnapshot.getValue(Integer.class));
                        break;
                    case "groups":
                        us.setGroups(dataSnapshot.getValue(String.class));
                        break;
                }
                Log.d(TAG, "added was called " + us.toString());
                if (key.equals("userName")) {
                    finished = us;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "changed was called");


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return us;//returns a reference to the user that is to be manipulated
    }
    public static void setCurrentUser(User use) {
        currentUser = use;
    }

    public static User getCurrentUser() {
        return currentUser;
    }


    static Integer numBar;
    //These functions will up date the bar with the places that people are at.
    public static int checkIn(String bar){

        Integer y = 0;
        final String barz = bar;

        Firebase fire = new Firebase(firebase.toString()+"//bars");
        //get the value at bar from DB

        Query query = fire.orderByKey();
        Log.d(TAG, "Data to be retrieved");

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "Data "+dataSnapshot.toString());
                //get the value
                BarClass in = dataSnapshot.getValue(BarClass.class);
                //store the value
                numBar = in.getPeople();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //increment it

        //push to the DB

        return y;

    }
    public static void incrementBarInt(int i){
        //increment
        if(numBar != null)
            numBar = numBar + i;
    }


    public static void setNumber(String bar) {

        Firebase fire = new Firebase(firebase.toString()+"//bars//"+bar);
        fire.setValue(numBar);

        //get the value from DB

    }

}
class BarClass {

    String name;
    int people;
    public BarClass(int u, String n){
        people = u;
        name = n;
    }
    public void setPeople(int p){
        people = p;
    }
    public int getPeople(){
        return people;
    }

    public String getName(){
        return name;
    }
    public String toString(){
        return "{ people:"+people+" }";
    }

}
