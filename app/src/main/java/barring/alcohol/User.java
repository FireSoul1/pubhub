package barring.alcohol;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by GbearTheGenius on 3/28/16.
 */
public class User {

    //their creditenials
    private String userName, email, name;
    //maybe give them a pic
    private String pic;

    //store the grous they are part of
    private String groups;
    private String friends;
    private int numOfGroups;

    public User() {}

    User(String u, String t, String n) {
        name = u;
        email = t;
        userName = n;
        groups = "";
        friends = "";
        numOfGroups = 0;
        pic = "";
    }
    public void setImage( String vf) {
       pic = vf;
    }


    //the getter methods
    public String getPic() { return pic; }
    public String getUserName() { return userName; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getGroups() { return groups; }
    /*public Group getGroup(int num) {
        return groups.get(num);
    }*/
    public String getFriends() { return friends; }
    public int getNumOfGroups() { return numOfGroups; }

    //the setter methods

    public void setPic(String str) { pic = str; }
    public void setUserName(String str) { userName = str; }
    public void setName(String str) {
        name= str;
    }
    public void setNumOfGroups(int num) {this.numOfGroups = num; }
    public void setGroups(String str) { this.groups = str; }
    public void setEmail(String str) { email= str; }
    public void setFriends(String str)
    {
        friends = str;
    }



    public String toString(){
        return "{ usr:"+userName+" name:"+name+" email:"+email+" }";
    }

}