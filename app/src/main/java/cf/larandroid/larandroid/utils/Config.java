package cf.larandroid.larandroid.utils;

import android.content.Intent;
import android.widget.EditText;

import cf.larandroid.larandroid.LoginActivity;
import cf.larandroid.larandroid.R;
import cf.larandroid.larandroid.RegisterActivity;

/*
 * Created by mrsilentreader on 28/11/16.
 */

public class Config {
    // Action Url
    public static final String URL_LOGIN = "http://YOUR_URL/api/login";
    public static final String URL_REGISTER = "http://YOUR_URL/api/register";

    public static final String URL_INDEX = "http://YOUR_URL/api/phone/index";
    public static final String URL_CREATE = "http://YOUR_URL/api/phone/create";
    public static final String URL_SHOW = "http://YOUR_URL/api/phone/show/";
    public static final String URL_UPDATE = "http://YOUR_URL/api/phone/update";
    public static final String URL_DELETE = "http://YOUR_URL/api/phone/delete/";

    // Value Name
    public static final String AUTH_NAME = "name";
    public static final String AUTH_USERNAME = "username";
    public static final String AUTH_PASSWORD = "password";

    public static final String CRUD_ID = "id";
    public static final String CRUD_BRAND = "brand";
    public static final String CRUD_TYPE = "type";
    public static final String CRUD_PRICE = "price";

    // TAG Name
    public static final String TAG_ID = "id";
    public static final String TAG_BRAND = "brand";
    public static final String TAG_TYPE = "type";
    public static final String TAG_PRICE = "price";


}
