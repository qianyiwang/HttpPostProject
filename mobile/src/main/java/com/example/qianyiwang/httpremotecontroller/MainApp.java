package com.example.qianyiwang.httpremotecontroller;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainApp extends AppCompatActivity implements View.OnClickListener{

    Button submitBt;
    EditText commendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        submitBt = (Button)findViewById(R.id.submitBt);
        submitBt.setOnClickListener(this);
        commendText = (EditText)findViewById(R.id.textEnter);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.submitBt:
                String msg = commendText.getText().toString();
//                Toast.makeText(this, msg, 0).show();
                SendHpptPost sendHpptPost = new SendHpptPost(msg);
                sendHpptPost.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;
        }
    }

    private class SendHpptPost extends AsyncTask<Object, Object, Boolean> {

        String urlParameters;
        byte[] postData;
        int postDataLength;
//        String request = "http://wqianyi.com/commandReceiver.php";
        String request;
        URL url;
        HttpURLConnection connection;
        String line;

        SendHpptPost(String msg){
            urlParameters = "param1="+msg;
//            urlParameters = msg;
            request = "http://wqianyi.com/commandReceiver2.php";
            postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            postDataLength = postData.length;
            try {
                url = new URL(request);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean doInBackground(Object... voids) {

            try {
                connection = (HttpURLConnection)url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("Content-Length", Integer.toString( postDataLength ));
//                connection.getOutputStream().write(postData);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream())){
                wr.write(postData);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("write data", e.toString());
            }
            try {
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    Log.i("Request Status", "This is success response status from server: " + responseCode);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "sent successfully", 0).show();
                        }
                    });
                    return true;
                } else {
                    Log.i("Request Status", "This is failure response status from server: " + responseCode);
                    return false;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }
}
