package com.example.hackathon.ngo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.handlers.DatabaseHandler;
import com.example.hackathon.login.SignupActivity;
import com.example.hackathon.login.SplashActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class AddImagesFragment extends Fragment {

    ImageView imageView;
    Button select, upload;

    String encoded;
    String url = Init.ip+"AddImageNgo.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.add_images_fragment, container, false);

        imageView = view.findViewById(R.id.addImageView);
        select = view.findViewById(R.id.selectImage);
        upload = view.findViewById(R.id.uploadImage);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");

                Intent chooser = Intent.createChooser(intent, "Choose a Picture");
                startActivityForResult(chooser,1);

                upload.setVisibility(View.VISIBLE);

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseOperation();
            }
        });

        return view;
    }

    private void databaseOperation() {

        Map<String ,String> values = new HashMap<>();
        values.put("image",encoded);
        values.put("email", SplashActivity.sharedPrefrencesHandler.getEmail());

        DatabaseHandler databaseHandler = new DatabaseHandler(getContext(),url) {
            @Override
            public void getResponse(String response) throws Exception {
                Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();

                upload.setVisibility(View.INVISIBLE);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.home_menu_icon));
            }
        };

        databaseHandler.putValues(values);

        databaseHandler.execute();

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1)
        {
            if(resultCode==-1) {
                Uri uri = data.getData();

                imageView.setImageURI(uri);

                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();

                Bitmap image = drawable.getBitmap();
                image = Bitmap.createScaledBitmap(image,256,256,false);

                imageView.setImageBitmap(image);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG,100,outputStream);
                byte[] arry = outputStream.toByteArray();

                encoded = Base64.encodeToString(arry, 0, arry.length, Base64.DEFAULT);

                Log.d("TAG", "onActivityResult: "+encoded);

            }
        }
    }
}