package com.example.administrator.volleyserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private TextView viewById;


    class MyStringRequest extends StringRequest {
        public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            this(Method.GET, url, listener, errorListener);
        }

        public MyStringRequest(int method, String url, Response.Listener<String> listener,
                               Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            String str = null;

            try {
                str = new String(response.data, "utf-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewById = (TextView) findViewById(R.id.textview);

        RequestQueue mQueue = Volley.newRequestQueue(this);
        MyStringRequest stringRequest = new MyStringRequest("http://192.168.23.1:8889",//"http://169.254.214.59:8889",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        viewById.setText(response);

                        Log.e("TestVolley", "onResponse - " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TestVolley", "onErrorResponse" + error.getMessage(), error);
                    }
                });

        mQueue.add(stringRequest);

//        try {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("name", "client");
//            map.put("metter","hello world！");
//            RequestMessage requestMessage = new RequestMessage();
//            requestMessage.setCode(1);
//            requestMessage.setData(map);
//            new ObjectConnection("192.168.23.1",9000)
//                    .sendRequest(requestMessage,
//                            ResponseContent.class,
//                            new Connection.SuccessListener<ResponseContent>() {
//                @Override
//                public void onResponse(ResponseContent responseContent) {
//                    //TODO 用来处理接收到的数据，数据都封装到resposeContent中了
//                    viewById.setText("1111");
//                }
//            }, new Connection.FailListener() {
//                @Override
//                public void onFile(Exception e) {
//                    Toast.makeText(MainActivity.this,"出现异常，识别失败",Toast.LENGTH_SHORT).show();//TODO Toasts是为了简写Toast而写的工具类，你可以直接用Toast的语法修改回来就可以了。
//                }
//            });
//        } catch (Exception e) {
//            Toast.makeText(MainActivity.this,"查询失败，其他异常",Toast.LENGTH_SHORT).show();
//        }
    }
}
