package com.example.hackathon.ngo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.data.NGOData;
import com.example.hackathon.data.RequestData;
import com.example.hackathon.donor.HomeFragment;
import com.example.hackathon.handlers.DatabaseHandler;
import com.example.hackathon.login.SplashActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonorRequestList extends Fragment {

    ListView listView;
    List<RequestData> requestList;
    CustomAdapter customAdapter;

    String url = Init.ip+"RequestListNGO.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.donor_request_list_fragment, container, false);

        listView = view.findViewById(R.id.requestListView);

        customAdapter =new CustomAdapter();

        requestList = new ArrayList<>();

        listView.setAdapter(customAdapter);

        databaseOperation();

        return view;
    }


    private void databaseOperation() {

        DatabaseHandler databaseHandler = new DatabaseHandler(getContext(),url) {
            @Override
            public void getResponse(String response) throws Exception {
                Log.d("TAG", "getResponse: "+response);

                JSONArray jsonArray = new JSONArray(response);

                for (int i=0; i<jsonArray.length(); i++)
                {
                    JSONObject object = jsonArray.getJSONObject(i);

                    requestList.add(new RequestData(object.getString("rno"),object.getString("donorName"),object.getString("donorMobile"),object.getString("donorAddress"),object.getString("donorCity"),object.getString("donorFoodDetails")));
                }
                customAdapter.notifyDataSetChanged();
            }
        };

        Map<String,String> map = new HashMap<>();
        map.put("ngoEmail", SplashActivity.sharedPrefrencesHandler.getEmail());

        databaseHandler.putValues(map);

        databaseHandler.execute();

    }

    public  class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return requestList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.donor_request_list_item,parent,false);

            TextView itemName = convertView.findViewById(R.id.donorNameTextView);
            TextView itemMobile = convertView.findViewById(R.id.donorMobileTextView);
            TextView itemAddress = convertView.findViewById(R.id.donorAddressTextView);
            TextView itemCity = convertView.findViewById(R.id.donorCityTextView);
            TextView itemFoodDetails = convertView.findViewById(R.id.donorFoodDetailsTextView);

            itemName.setText(new StringBuilder("Name :").append(requestList.get(position).getDonorName()));
            itemMobile.setText(new StringBuilder("Mobile :").append(requestList.get(position).getDonorMobile()));
            itemAddress.setText(new StringBuilder("Address :").append(requestList.get(position).getDonorAddress()));
            itemCity.setText(new StringBuilder("City :").append(requestList.get(position).getDonorCity()));
            itemFoodDetails.setText(new StringBuilder("Food Details: ").append(requestList.get(position).getDonorFoodDetails()));

            Button reject = convertView.findViewById(R.id.donorreject);
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String,String> values = new HashMap<>();
                    values.put("rno",requestList.get(position).getRno());

                    String rejecturl = Init.ip+"RejectRequest.php";

                    DatabaseHandler databaseHandler = new DatabaseHandler(getContext(),rejecturl) {
                        @Override
                        public void getResponse(String response) throws Exception {
                            Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                            requestList.remove(position);
                            customAdapter.notifyDataSetChanged();
                        }
                    };

                    databaseHandler.putValues(values);
                    databaseHandler.execute();
                }
            });


            Button accept = convertView.findViewById(R.id.donoraccept);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),AcceptRequestActivity.class);
                    intent.putExtra("rno",requestList.get(position).getRno());
                    intent.putExtra("city",requestList.get(position).getDonorCity());
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }
}