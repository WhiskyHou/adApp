package cn.xxhouyi.app1024;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    public static String url;

    private Button submit;
    private EditText input;
    private TextView content;
    private MyImageView image;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case -1:
                    break;
                case 1:
                    parseHtml(msg.obj.toString());
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button)findViewById(R.id.submit);
        input = (EditText)findViewById(R.id.input);
        content = (TextView)findViewById(R.id.htmlText);
        image = (MyImageView)findViewById(R.id.imageView);

        input.setText("");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.url = input.getText().toString();
                NetWork.get(MainActivity.url, handler);
            }
        });
    }

    private void parseHtml(String content){
        Document doc = Jsoup.parse(content);
        Element title = doc.selectFirst("h4");
        Elements srcs = doc.select("input[data-src]");
        Log.d("number--", String.valueOf(srcs.size()));

        if(title != null) {
            Log.d("title--", title.text());
        }
        Vector<String> src = new Vector<>();
        for(int i = 0; i < srcs.size(); i++){
            Log.d("src--", srcs.eq(i).attr("data-src").toString());
            src.add(srcs.eq(i).attr("data-src").toString());
        }

        image.setImageURL(src.firstElement());
    }

}
