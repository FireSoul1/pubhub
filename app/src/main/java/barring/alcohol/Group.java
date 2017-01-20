package barring.alcohol;

import com.firebase.client.Firebase;

import java.util.ArrayList;

/**
 * Created by nickz on 4/26/16.
 */
public class Group {

    String members;
    int numOfMembers;
    String name;


    Group(String groupname, String creator) {
        this.name = groupname;
        this.numOfMembers = 1;
        this.members = creator + ";";
    }


    public String getName() { return name; }
    public String getMembers() { return members; }
    public int getNumOfMembers() { return  numOfMembers; }

    public void setName(String name) {this.name = name; }
    public void setMembers(String members) { this.members = members; }
    public void setNumOfMembers(int num) { this.numOfMembers = num; }
    public void addMember(String email) {
        this.members = this.members + email + ";";
        numOfMembers++;
        Firebase me = new Firebase("https://pubhubpurdue.firebaseio.com" + "//groups//" + this.getName() + "//members");
        me.setValue(members);
    }


}
