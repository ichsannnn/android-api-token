package cf.larandroid.larandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cf.larandroid.larandroid.utils.Config;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtBrand;
    private EditText txtType;
    private EditText txtPrice;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnBack;

    private String phoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        phoneId = intent.getStringExtra(Config.TAG_ID);

        txtBrand = (EditText) findViewById(R.id.txtBrandEdit);
        txtType = (EditText) findViewById(R.id.txtTypeEdit);
        txtPrice = (EditText) findViewById(R.id.txtPriceEdit);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePhone);
        btnDelete = (Button) findViewById(R.id.btnDeletePhone);
        btnBack = (Button) findViewById(R.id.btnBackUpdate);

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        findPhone();
    }

    private void findPhone() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Config.URL_SHOW + phoneId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jObj = new JSONObject(response.getString("data"));
                            txtBrand.setText(jObj.getString("brand"));
                            txtType.setText(jObj.getString("type"));
                            txtPrice.setText(jObj.getString("price"));
                        } catch (JSONException e){
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

    private void updatePhone() {
        final String brand = txtBrand.getText().toString().trim();
        final String type = txtType.getText().toString().trim();
        final String price = txtPrice.getText().toString().trim();
        final HashMap<String, String> param = new HashMap<String, String>();

        param.put(Config.CRUD_ID, phoneId);
        param.put(Config.CRUD_BRAND, brand);
        param.put(Config.CRUD_TYPE, type);
        param.put(Config.CRUD_PRICE, price);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_UPDATE, new JSONObject(param),
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

    private void deletePhone() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Config.URL_DELETE + phoneId, null,
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
        if (v == btnUpdate) {
            updatePhone();
        }

        if (v == btnDelete) {
            deletePhone();
        }

        if (v == btnBack) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
