package com.data.utship.utills.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.data.utship.activity.DashboardActivity;
import com.data.utship.model.LoginBean;
import com.data.utship.utills.dialog.DialogsProvider;
import com.data.utship.utills.SharedPrefManager;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class UserAccountManager {
    private static LoginBean user;

    //Token Type Parameters
    public static final int TOKEN_TYPE_NORMAL = 0;
    public static final int TOKEN_TYPE_BEARER = 1;
    public static final String FORCED_SIGN_OUT = "forcedSignOut";

    public static void signIn(Activity activity, Intent intent, String token, LoginBean userModel){
        user = userModel;
        Context context = activity.getApplicationContext();
        //Save User Data
        SharedPrefManager.get(context).setSignedIn(true);
        SharedPrefManager.get(context).setToken(token);
        SharedPrefManager.get(context).setUser(userModel);

        //Launch Main Activity
        activity.startActivity(intent);
        activity.onBackPressed();
    }

    public static LoginBean getUser(Context context){
        if(user == null) user = SharedPrefManager.get(context).getUser();
        return user;
    }

    public static void updateUser(Context context, LoginBean userModel){
        user = userModel;
        SharedPrefManager.get(context).setUser(user);
    }

    public static String getToken(Context context, int type){
        String output ="";
        switch (type){
            case TOKEN_TYPE_NORMAL:
                output = SharedPrefManager.get(context).getToken();
                break;

            case TOKEN_TYPE_BEARER:
                output = "Bearer " + SharedPrefManager.get(context).getToken();
                break;
        }

        return output;
    }

    public static void signOut(Activity activity, boolean forced){
        DialogsProvider.get(activity).setLoading(true);

        Context context = activity.getApplicationContext();

        //clear user account data
        SharedPrefManager.get(context).setSignedIn(false);
        SharedPrefManager.get(context).setToken("");
        SharedPrefManager.get(context).setUser(null);

        //signout from facebook
        if(AccessToken.getCurrentAccessToken()!=null) LoginManager.getInstance().logOut();

        //signout from google
        if(GoogleSignIn.getLastSignedInAccount(context) != null){

            GoogleSignIn.getClient(context, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
            ).signOut();

        }

        DialogsProvider.get(activity).setLoading(false);

        //Launch Account Sign Activity
        Intent intent = new Intent(context, DashboardActivity.class);
        intent.putExtra(FORCED_SIGN_OUT,forced);
        activity.startActivity(intent);
        activity.finish();
    }


}
