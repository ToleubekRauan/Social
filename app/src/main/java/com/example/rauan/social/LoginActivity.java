package com.example.rauan.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity{
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email", "user_birthday"));
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final GraphRequest request = new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                Log.d("data",response.toString());
                                String myCustomizedResponse = response.getJSONObject().toString();

                                try {
                                    JSONObject obj = new JSONObject(myCustomizedResponse);
                                    String email = obj.getString("email");
                                    String id = obj.getString("id");
                                    String gender = obj.getString("gender");
                                    String name = obj.getString("name");
                                    String birthday = obj.getString("birthday");
                                    String picUrl = "https://graph.facebook.com/" + id + "/picture?type=large";
                                    userProfile = new UserProfile(id,name,birthday,email,gender,picUrl);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,birthday,email,gender,picture");
                request.setParameters(parameters);
                request.executeAsync();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
