package lol.com.epl;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;


public class DisplayActivity extends ActionBarActivity {
    // TextView tx;
    TextView name;
    TextView sho;
    TextView market;
    Display dis;
    ImageView imageview;
    public static final String TAG = MainActivity.class.getSimpleName();
    Button fixture;
    Button players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        fixture = (Button) findViewById(R.id.fixture);
        players = (Button) findViewById(R.id.players);
        Intent in = getIntent();
        final String str = in.getStringExtra("link");
        //  tx =(TextView)findViewById(R.id.tx);
        //  tx.setText(str);
        name = (TextView) findViewById(R.id.name);
        sho = (TextView) findViewById(R.id.sho);
        market = (TextView) findViewById(R.id.market);
        imageview = (ImageView) findViewById(R.id.ivImage);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(str)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    //Response response = call.execute();
                    String json = response.body().string();

                    //Response response = call.execute();
                    if (response.isSuccessful()) {
                        Log.v(TAG, json);
                        dis = getDetails(json);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDisplay();
                            }
                        });


                    } else {
                        Log.v(TAG, json);
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception");
                } catch (JSONException e) {
                    Log.e(TAG, "Exception");
                }
            }


        });

        fixture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DisplayActivity.this, FixTeamActivity.class);
                i.putExtra("ft", str);
                startActivity(i);


            }
        });

        players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent io = new Intent(DisplayActivity.this, PlayerActivity.class);
                io.putExtra("lin", str);
                startActivity(io);


            }
        });


    }

    private void updateDisplay() {
        name.setText(dis.getName());
        sho.setText(dis.getSho());
       market.setText(dis.getMarket());
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.abc_ab_share_pack_mtrl_alpha)
                .showImageOnFail(R.drawable.abc_ab_share_pack_mtrl_alpha)
                .showImageOnLoading(R.drawable.abc_ab_share_pack_mtrl_alpha).build();
        //imageview.setImageResource(R.drawable.toggle_expand);
        // new DownloadImageTask(imageview).execute(dis.getImage());
        // imageview.setImageResource();
        // ImageLoader imageLoader = ImageLoader.getInstance();
        //imageLoader.displayImage(dis.getImage(), imageview);
      //  new HttpImageRequestTask().execute();
      String lo=dis.getImage().replace("_1080x1776","");
        //market.setText(lo);

        imageLoader.displayImage(lo, imageview, options);
    }

    private Display getDetails(String json) throws JSONException {
        JSONObject jObj = new JSONObject(json);
        Display dis = new Display();
        dis.setName(getString("name", jObj));
        dis.setSho(getString("shortName", jObj));
        dis.setMarket(getString("squadMarketValue", jObj));
        dis.setImage(getString("crestUrl", jObj));


        return dis;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class HttpImageRequestTask extends AsyncTask<Void, Void, Drawable> {
        @Override
        protected Drawable doInBackground(Void... params) {
            try {


                final URL url = new URL("http://upload.wikimedia.org/wikipedia/commons/e/e8/Svg_example3.svg");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                SVG svg = SVGParser.getSVGFromInputStream(inputStream);
                Drawable drawable = svg.createPictureDrawable();
                return drawable;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            // Update the view
            if(drawable != null){

                // Try using your library and adding this layer type before switching your SVG parsing
                imageview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                imageview.setImageDrawable(drawable);
            }
        }
    }
}
