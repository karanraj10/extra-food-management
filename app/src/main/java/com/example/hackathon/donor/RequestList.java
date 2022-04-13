package com.example.hackathon.donor;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hackathon.Init;
import com.example.hackathon.R;
import com.example.hackathon.data.MyRequestData;
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

public class RequestList extends Fragment {

    ListView listView;
    List<MyRequestData> myRequestList;
    CustomAdapter customAdapter;

    String url = Init.ip+"RequestListDonor.php";

    String status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.request_list_fragment_donor, container, false);

        listView = view.findViewById(R.id.myRequestListView);

        customAdapter =new CustomAdapter();

        myRequestList = new ArrayList<>();

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

                    myRequestList.add(new MyRequestData(object.getString("requestNo"),object.getString("ngoName"),object.getString("foodDetails"),object.getString("status"),object.getString("needyArea"),object.getString("vContact")));
                }
                customAdapter.notifyDataSetChanged();
            }
        };

        Map<String,String> map = new HashMap<>();
        map.put("donorEmail", SplashActivity.sharedPrefrencesHandler.getEmail());

        databaseHandler.putValues(map);

        databaseHandler.execute();
    }

    public class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return myRequestList.size();
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

            convertView = getLayoutInflater().inflate(R.layout.myrequest_list_item,parent,false);

            TextView itemRequestNo = convertView.findViewById(R.id.myRequestNoTextView);
            TextView itemNgoName = convertView.findViewById(R.id.myNgoNameTextView);
            TextView itemFoodDetails = convertView.findViewById(R.id.myFoodDetailsTextView);
            TextView itemStatus = convertView.findViewById(R.id.myStatusTextView);
            TextView itemNeedyArea = convertView.findViewById(R.id.myNeedyAreaTextView);
            TextView itemNeedyContact = convertView.findViewById(R.id.myNeedyContactTextView);



            itemRequestNo.setText(new StringBuilder("Request No :").append(myRequestList.get(position).getRequestNo()));
            itemNgoName.setText(new StringBuilder("NGO :").append(myRequestList.get(position).getNgoName()));
            itemFoodDetails.setText(new StringBuilder("Food :").append(myRequestList.get(position).getFoodDetails()));

            if (myRequestList.get(position).getStatus().equals("0"))
            {
                status = "Pending";
            }
            else if (myRequestList.get(position).getStatus().equals("1"))
            {
                status = "Accepted";
            }
            else if (myRequestList.get(position).getStatus().equals("2"))
            {
                status = "Rejected";
            }
            itemStatus.setText(new StringBuilder("Status :").append(status));


            // IF Request Is Not Accepted Then Invisible Contact And Area
            if (myRequestList.get(position).getStatus().equals("1"))
            {
                itemNeedyArea.setVisibility(View.VISIBLE);
                itemNeedyContact.setVisibility(View.VISIBLE);
                itemNeedyArea.setText(new StringBuilder("Needy Area: ").append(myRequestList.get(position).getNeedyArea()));
                itemNeedyContact.setText(new StringBuilder("Contact: ").append(myRequestList.get(position).getNeedyContact()));
            }

            return convertView;
        }
    }
}