package com.example.hackathon.donor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.data.Gallery;
import com.example.hackathon.data.RequestData;
import com.example.hackathon.handlers.DatabaseHandler;
import com.example.hackathon.login.SplashActivity;
import com.example.hackathon.ngo.DonorRequestList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {

    ListView listView;
    List<Gallery> imageList;
    CustomAdapter customAdapter;
    String url = Init.ip+"GetImages.php";

    String email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        email = getIntent().getStringExtra("email");

        listView = findViewById(R.id.gallryListView);

        customAdapter =new CustomAdapter();

        imageList = new ArrayList<>();

        listView.setAdapter(customAdapter);

        databaseOperation();



    }

    private void databaseOperation() {

        DatabaseHandler databaseHandler = new DatabaseHandler(this,url) {
            @Override
            public void getResponse(String response) throws Exception {
                Log.d("TAG", "getResponse: "+response);

                JSONArray jsonArray = new JSONArray(response);

                for (int i=0; i<jsonArray.length(); i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);

                    imageList.add(new Gallery(object.getString("image"),object.getString("date")));
                }
                customAdapter.notifyDataSetChanged();
            }
        };

        Map<String,String> map = new HashMap<>();
        map.put("ngoEmail", email);

        databaseHandler.putValues(map);

        databaseHandler.execute();

    }

    public class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return imageList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.gallery_list_item,parent,false);

            ImageView imageView = convertView.findViewById(R.id.galleryImageView);
            TextView dateText = convertView.findViewById(R.id.dateTextView);

            dateText.setText(imageList.get(position).getDate());
            imageView.setImageBitmap(getImage(imageList.get(position).getImage()));

            return convertView;
        }

        private Bitmap getImage(String ob) {
            byte[] imagedecoded = Base64.decode(ob, Base64.DEFAULT);
            Bitmap decodedimage = BitmapFactory.decodeByteArray(imagedecoded, 0, imagedecoded.length);

            return decodedimage;
        }
    }
}