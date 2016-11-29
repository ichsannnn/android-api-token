package cf.larandroid.larandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cf.larandroid.larandroid.utils.Config;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText txtName;
    public EditText txtUsername;
    public EditText txtPassword;
    private Button btnToLogin;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtName = (EditText) findViewById(R.id.txtNameReg);
        txtUsername = (EditText) findViewById(R.id.txtUsernameReg);
        txtPassword = (EditText) findViewById(R.id.txtPasswordReg);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnToLogin = (Button) findViewById(R.id.btnGoToLogin);

        btnToLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    private void registerUser() {
        final String name = txtName.getText().toString().trim();
        final String username = txtUsername.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();
        final HashMap<String, String> param = new HashMap<String, String>();

        param.put(Config.AUTH_NAME, name);
        param.put(Config.AUTH_USERNAME, username);
        param.put(Config.AUTH_PASSWORD, password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_REGISTER, new JSONObject(param),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Web service is in trouble!", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == btnToLogin) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }

        if (v == btnRegister) {
            registerUser();
        }
    }
}
