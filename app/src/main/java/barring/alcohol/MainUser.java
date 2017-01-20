package barring.alcohol;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by Josh on 2/20/2016.
 */
public class MainUser extends User
{
    private String name;
    private String email;
    private String username;
    private String password;
    private String friends;
    private String groups;
    private int numOfGroups;

    //private int numOfGroups;
    //private ArrayList<Group> groups = new ArrayList<Group>();

    private static String image;
    Firebase ref;

    public MainUser()
    {
        ref = new Firebase("https://pubhubpurdue.firebaseio.com");
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return this.email;
    }

    public void setUserName(String username)
    {
        this.username = username;
    }

    public String getUserName()
    {
        return this.username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return this.password;
    }

    public static String getImag() { return image; }

    public static void setImag(String str) { image = str; }

    public void addFriend(final String newEmail)
    {
        //update locally
        friends = friends + newEmail + ";";
        //update my friends list
        String meID = email.replace(".","-").substring(0, email.indexOf("@"));
        Firebase me = ref.child("//users//" + meID + "//friends");
        me.setValue(friends);
        //update their friends list
        final User you = FireStore.getUserByName(newEmail);

        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                String theirFriends = you.getFriends();
                theirFriends = theirFriends + email + ";";

                String theirID = newEmail.replace(".","-").substring(0, newEmail.indexOf("@"));
                Firebase their = ref.child("//users//" + theirID + "//friends");
                their.setValue(theirFriends);

            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                //there is no reason it should get here...
            }
        });

    }

    public void addGroup(final String newGroup, User currentUser) {
        //update locally
        Log.d("Preexisting groups", "The preexisting groups are: " + currentUser.getGroups());
        groups = currentUser.getGroups();
        if(groups == null)
            groups = newGroup + ";";
        else
            groups = groups + newGroup + ";";

        Log.d("GroupUpdate", "Updated Groups: " + groups);
        //update my group list
        String meID = currentUser.getEmail().replace(".", "-").substring(0, currentUser.getEmail().indexOf("@"));
        Log.d("meID", "MeID: " + meID);
        Firebase me = ref.child("//users//" + meID + "//groups");
        Log.d("Group equals", "New Groups: " + groups);
        me.setValue(groups);
        numOfGroups++;



    }

    public String getFriends()
    {
        return friends;
    }

    public void setFriendsInit()
    {
        friends = "";
    }

    public void setFriends(String str)
    {
        friends = str;
    }

    public int getNumOfGroups() { return numOfGroups; }



    //public Group getGroup(int num) {return groups.get(num);}
}
