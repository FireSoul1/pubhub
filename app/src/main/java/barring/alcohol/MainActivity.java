package barring.alcohol;

import android.Manifest.*;
import android.app.Activity;
import android.app.Dialog;

import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.accounts.*;

import com.firebase.client.*;

import com.google.android.gms.auth.*;
import com.google.android.gms.auth.api.*;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.*;
import com.google.android.gms.common.api.*;

import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;


///CLIENT IDENTITY: 223135792779-ogsggjjhbj27ehe21bmi4oqkmrjmlue8.apps.googleusercontent.com

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, ResultCallback<People.LoadPeopleResult>, SignInFragment.OnFragmentInteractionListener {

    //
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;

  // /  private ProgressDialog mProgressDialog;

    //FireBase Varibles
    public static Firebase mFireBase;
    private Firebase.AuthStateListener mAuthStateListener;


    //MainActivity Varibles
    DatabaseHelper helper = new DatabaseHelper(this);
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 1;


    //AsyncTask varibles
    private boolean mGoogleIntentInProgress;
    private boolean mGoogleLoginClicked;
    private ConnectionResult mGoogleConnectionResult;
    String img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //set up miscellaneous things
        mStatusTextView = (TextView)findViewById(R.id.textView2);

        SignInButton butt = (SignInButton)findViewById(R.id.sign_in_button);
        butt.setOnClickListener(this);
      //  if(mStatusTextView != null)
        ///    mStatusTextView.setText("Need to Sign In");

        //setting up our DB
        Firebase.setAndroidContext(this);
        mFireBase = new Firebase("https://" + "pubhubpurdue.firebaseio.com");

        //FireStore.setFire(mFireBase);

      //  Log.d("Following:", "Just finished FireBase");
        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                //mAuthProgressDialog.hide();
                //setAuthenticatedUser(authData);
            }
        };
        /* Check if the user is authenticated with Firebase already. If this is the case we can set the authenticated
         * user and hide hide any login buttons */
        mFireBase.addAuthStateListener(mAuthStateListener);

        //setting up the Client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();


        FireStore.setFire(mFireBase);
        Log.d("Following:", mFireBase.toString());


    }


    /* A helper method to resolve the current ConnectionResult error. */
    private void resolveSignInError() {
        if (mGoogleConnectionResult.hasResolution()) {
            try {
                mGoogleIntentInProgress = true;
                mGoogleConnectionResult.startResolutionForResult(this, MainActivity.RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mGoogleIntentInProgress = false;
                Log.d(TAG, "ajkhfsd ");

                mGoogleApiClient.connect();
            }
        }
    }

    int what;
    public void onClick(View v)
    {
        if (v.getId() == R.id.Blogin)//signning in manually
        {
            EditText a = (EditText) findViewById(R.id.TFsignupusername);
            final String email = a.getText().toString();

            EditText b = (EditText) findViewById(R.id.TFpassword);
            final String password = b.getText().toString();

            String exp = "[A-Za-z0-9\\.]+@[A-Za-z]+\\.[A-Za-z]+";
            Pattern pattern = Pattern.compile(exp);
            Matcher matcher = pattern.matcher(email);
            boolean goodEmail = matcher.matches();
            //goodEmail = true;
            if (!goodEmail) {
                Toast toastmess = Toast.makeText(MainActivity.this, "Invalid email/password. Please try again.", Toast.LENGTH_SHORT);
                toastmess.show();
            }
            else {
                final User temp = FireStore.getUserByName(email);

                what++;
                SignUp.mainUser = new MainUser();
                SignUp.mainUser.setImage(img);

                mFireBase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.d(TAG, "User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());

                        //send over the Username and the name

                        SignUp.mainUser.setUserName(email.substring(0, email.indexOf('@')));
                        SignUp.mainUser.setName(email.substring(0, email.indexOf('@')));
                        SignUp.mainUser.setEmail(email);

                        if (temp != null) {
                            SignUp.mainUser.setUserName(temp.getUserName());
                            SignUp.mainUser.setName(temp.getName());
                            Log.d("Auth", temp.getPic());
                            SignUp.mainUser.setPic(temp.getPic());
                            SignUp.mainUser.setPassword(password);
                            SignUp.mainUser.setFriends(temp.getFriends());
                        }
                        Log.d(TAG, "O authFinished");

                        Intent i = new Intent(MainActivity.this, Display.class);
                        startActivity(i);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast temp = Toast.makeText(MainActivity.this, firebaseError.getMessage(), Toast.LENGTH_SHORT);
                        temp.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                        temp.show();

                        Log.d(TAG, "Finished");
                    }
                });
            }
        }
        else if (v.getId() == R.id.Bsignup)//create a new account
        {
            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.sign_in_button)//logining with OAuth
        {

            mGoogleLoginClicked = true;
            if (!mGoogleApiClient.isConnecting())
            {
                if (mGoogleConnectionResult != null)
                {
                    resolveSignInError();
                }
                else if (mGoogleApiClient.isConnected())
                {
                    Log.d(TAG, "Connected to Google API 1");
                    //login
                    loginAction();
                }
                else {
                /* connect API now */
                    Log.d(TAG, "Trying to connect to Google API 2");
                    mGoogleApiClient.connect();

                    if (ContextCompat.checkSelfPermission(this, permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                        //if not granted, let's ask for it
                        Log.d(TAG, "Request permission");
                        ActivityCompat.requestPermissions(this,
                                new String[]{permission.READ_CONTACTS, permission.GET_ACCOUNTS},
                                9);

                    }
                }
            }
        }


    }
    private boolean isValidPassword(String pass) {

        return pass.matches("([A-Za-z0-9])*") && (pass.length() > 8 );

    }

    private String email;

//    private void getGoogleOAuthTokenAndLogin() {
//        /* Get OAuth token in Background */
//        final AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
//
//            String errorMessage = null;
//           // Intent intent = new Intent(MainActivity.this, Display.class);
//
//            @Override
//            @Deprecated
//           protected String doInBackground(Void... params) {
//                String token = null;
//
//                try {
//
//
//
//
//                    //get the Account that is to be passed
//                    Pattern emailPat = Patterns.EMAIL_ADDRESS;
//                    Account[] accounts = AccountManager.get(MainActivity.this).getAccounts();
//
//                    Account passing = null;
//                //   Log.d("OAuth","You are getting a lot of tokens "+ accounts.length);
//
//                    for(Account ac: accounts) {
//                        if (emailPat.matcher(ac.name).matches()) {
//                            passing = ac;
//                            break;
//                        }
//                    }
//                    String scope = /*String.format(*/"oauth2:https://www.googleapis.com/auth/plus.me"; //, Scopes.PLUS_ME, Scopes.PLUS_LOGIN);
//
//                 //   Log.d(TAG, "AsyncTask is running! And finshing");
//                    //pass the account name
//                    if(passing != null) {
//
//                        token = GoogleAuthUtil.getToken(MainActivity.this, passing, scope);
//                        email = passing.name;
//
//
//
//                    }
//                    else
//                        Log.d("NULLPOINTER","Account is Null somthings went wrong Account Manager");
//
//                } catch (IOException transientEx) {
//                    /* Network or server error */
//                    Log.e(TAG, "Error authenticating with Google: " + transientEx);
//                    errorMessage = "Network error: " + transientEx.getMessage();
//                } catch (UserRecoverableAuthException e) {
//                    Log.w(TAG, "Recoverable Google OAuth error: " + e.toString());
//                    /* We probably need to ask for permissions, so start the intent if there is none pending */
//                    if (!mGoogleIntentInProgress) {
//                        mGoogleIntentInProgress = true;
//                        Intent recover = e.getIntent();
//                        startActivityForResult(recover, MainActivity.RC_SIGN_IN);
//                    }
//                } catch (GoogleAuthException authEx) {
//                    /* The call is not ever expected to succeed assuming you have already verified that
//                     * Google Play services is installed. */
//                    Log.e(TAG, "Error authenticating with Google: " + authEx.getMessage(), authEx);
//                    errorMessage = "Error authenticating with Google: " + authEx.getMessage();
//                }
//
//                return token;
//            }
//
//            @Override
//           protected void onPostExecute(final String token) {
//
//                mGoogleLoginClicked = false;
//                //doing a Firebase Call here
//                mFireBase.authWithOAuthToken("google", token, new Firebase.AuthResultHandler() {
//                    @Override
//                    public void onAuthenticated(AuthData authData) {
//                        Log.d("FireBase", "This was good! " + token);
//                        //updateUI(true);
//                       //if(mGoogleApiClient.isConnected())
//                        //Log.d(TAG, "Connected ");
//                       //else
//                        mGoogleApiClient.connect();
//
//
//                        //get the User Profile Image
//
//                        Log.d(TAG, img + " ");
//
//                        Intent intent = new Intent(MainActivity.this, Display.class);
//                        intent.putExtra("email",email);
//                        intent.putExtra("web",img);
//
//                        User user = new User(email.substring(0, email.indexOf("@")).replace("\\.", "-"), email, "default-2", img );
//                        FireStore.addUser(user);
//
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onAuthenticationError(FirebaseError firebaseError) {
//                        Log.d("FireBase", "You kinda failed. Keep trying!");
//
//                    }
//                });
//
//            }
//        };
//        task.execute();
//    }


    public void openDialog(final Account passing) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        Log.d("FRAG", "JUST LOADED INFLATER");


        final View overal = inflater.inflate(R.layout.activity_main, null);

        final View view = inflater.inflate(R.layout.fragment_sign_in, null);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        Log.d(TAG, "Clicked Signin");

                        EditText user = (EditText) view.findViewById(R.id.usernameMain);
                        EditText pass = (EditText) view.findViewById(R.id.passwordMain);

                        Log.d(TAG," "+pass.getText().toString() );

                        boolean check = isValidPassword(pass.getText().toString());

                        if (check) {
                            LayoutInflater inflater1 = getLayoutInflater();
                            View vim = inflater1.inflate(R.layout.toast_layout, null);

                            TextView text = (TextView) vim.findViewById(R.id.text);
                            text.setText("Sign-up Successful!");


                            Toast t = Toast.makeText( MainActivity.this, "Welcome!!", Toast.LENGTH_SHORT);
                            t.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 0);
                            t.setView(vim);
                            t.show();

                            Intent intent = new Intent(MainActivity.this, Display.class);
                            MainUser.setImag(img);

                            Log.d(TAG, "user name: " + user.getText().toString());

                            //add the User to the FireBase
                            User adding = new User(user.getText().toString() ,passing.name, user.getText().toString());


                            //send over the Username and the name
                            SignUp.mainUser = new MainUser();
                            SignUp.mainUser.setUserName(user.getText().toString());
                            SignUp.mainUser.setPassword(pass.getText().toString());
                            SignUp.mainUser.setFriendsInit();
                            SignUp.mainUser.setName(user.getText().toString());
                            SignUp.mainUser.setEmail(adding.getEmail());
                            SignUp.mainUser.setPic(img);

                            FireStore.addUser(adding, pass.getText().toString());
                            //open the community page
                            startActivity(intent);

                        }
                        else {

                            LayoutInflater inflater = getLayoutInflater();

                            View vim = inflater.inflate(R.layout.toast_layout, null);
                            TextView text = (TextView)vim.findViewById(R.id.text);
                            text.setText("Password needs one A-Z, one a-z, and a number");

                            Toast temp = Toast.makeText(MainActivity.this, "Password needs to be Alphanumeric and greater than 8 characters long!"
                                    , Toast.LENGTH_LONG);

                            temp.setView(vim);
                            temp.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM , 0, 0);
                            temp.show();

                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.show();
    }
    public void loginAction(){
        Log.d(TAG,"Connected to the Google Services");

        img = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient).getImage().getUrl();
        Log.d(TAG, img.toString());
        //get the Account that is to be passed

        Pattern emailPat = Patterns.EMAIL_ADDRESS;
        Account[] accounts = AccountManager.get(MainActivity.this).getAccounts();

        Account passing = null;

        for(Account ac: accounts) {
            if (emailPat.matcher(ac.name).matches()) {
                passing = ac;
                break;
            }
        }
        String ii = passing.name;
        Log.d(TAG, ii);

        if (ContextCompat.checkSelfPermission(this, permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            //if not granted, let's ask for it
            Log.d(TAG, "Request permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{permission.READ_CONTACTS, permission.GET_ACCOUNTS},
                    9);

        }
        if(what != 1)
            this.openDialog(passing);

    }

    @Override
    public void onConnected(Bundle bundle) {

        loginAction();
    }


    //after requesting the permissions, this is what executes
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //then login
        if(requestCode == 9) {
            Log.d(TAG, "GOING TO get OAuth token ");
            loginAction();
            ///getGoogleOAuthTokenAndLogin();
        }
        else {
            Log.d(TAG, "Permission request failed. Good bye");
            this.finish();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"Connecting to the Google Services");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!mGoogleIntentInProgress) {
            /* Store the ConnectionResult so that we can use it later when the user clicks on the Google+ login button */
            mGoogleConnectionResult = connectionResult;

            if (mGoogleLoginClicked) {
                /* The user has already clicked login so we attempt to resolve all errors until the user is signed in,
                 * or they cancel. */
                resolveSignInError();
            }
            else {
                Log.e(TAG, connectionResult.toString());
            }
        }
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {

       // getGoogleOAuthTokenAndLogin();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d("Listener","Interactiing");
    }
}
