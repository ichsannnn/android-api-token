package cf.larandroid.larandroid;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cf.larandroid.larandroid.utils.Config;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fabCreate;
    private FloatingActionButton fabLogout;
    private ListView phoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabCreate = (FloatingActionButton) findViewById(R.id.fabCreate);
        fabLogout = (FloatingActionButton) findViewById(R.id.fabLogout);
        phoneList = (ListView) findViewById(R.id.phoneList);

        fabCreate.setOnClickListener(this);
        fabLogout.setOnClickListener(this);
        phoneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), EditActivity.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
                String phoneId = map.get(Config.TAG_ID).toString();
                i.putExtra(Config.TAG_ID, phoneId);
                startActivity(i);
                finish();
            }
        });
        getPhone();
    }

    private void getPhone() {
        final ArrayList<HashMap<String, String>> phoneData = new ArrayList<HashMap<String, String>>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Config.URL_INDEX, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject phone = (JSONObject) response.get(i);

                                String id = phone.getString("id");
                                String brand = phone.getString("brand");
                                String type = phone.getString("type");
                                String price = phone.getString("price");

                                HashMap<String, String> param = new HashMap<String, String>();
                                param.put(Config.TAG_ID, id);
                                param.put(Config.TAG_BRAND, brand);
                                param.put(Config.TAG_TYPE, type);
                                param.put(Config.TAG_PRICE, price);
                                phoneData.add(param);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ListAdapter adapter = new SimpleAdapter(MainActivity.this, phoneData, R.layout.content_main, new String[] {Config.TAG_PRICE, Config.TAG_BRAND, Config.TAG_TYPE}, new int[] {R.id.viewPrice, R.id.viewBrand, R.id.viewType});
                        phoneList.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Web service is in trouble!", Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == fabCreate) {
            Intent i = new Intent(getApplicationContext(), CreateActivity.class);
            startActivity(i);
            finish();
        }

        if (v == fabLogout) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        }
    }
}
