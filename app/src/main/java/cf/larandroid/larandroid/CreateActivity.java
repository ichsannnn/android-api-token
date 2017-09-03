package cf.larandroid.larandroid;

import android.app.ProgressDialog;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cf.larandroid.larandroid.utils.Config;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText txtBrand;
    private EditText txtType;
    private EditText txtPrice;
    private Button btnSave;
    private Button btnBack;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        txtBrand = (EditText) findViewById(R.id.txtBrand);
        txtType = (EditText) findViewById(R.id.txtType);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        btnSave = (Button) findViewById(R.id.btnSavePhone);
        btnBack = (Button) findViewById(R.id.btnBackAdd);

        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void savePhone() {
        final String brand = txtBrand.getText().toString().trim();
        final String type = txtType.getText().toString().trim();
        final String price = txtPrice.getText().toString().trim();
        final HashMap<String, String> param = new HashMap<String, String>();

        param.put(Config.CRUD_BRAND, brand);
        param.put(Config.CRUD_TYPE, type);
        param.put(Config.CRUD_PRICE, price);

        pDialog = new ProgressDialog(CreateActivity.this);
        pDialog.setMessage("Inserting data . .");
        pDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Config.URL_CREATE, new JSONObject(param),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Web service is in trouble!", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            savePhone();
        }

        if (v == btnBack) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
