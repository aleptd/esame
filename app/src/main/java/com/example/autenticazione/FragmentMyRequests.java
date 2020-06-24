package com.example.autenticazione;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyRequests extends Fragment {

    public FragmentMyRequests() {
        // Required empty public constructor
    }

    DatabaseReference myRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
  public Request addReservation(String submissionTime,String username_incumbent, String auto_licensePlate_incumbent, String auto_model_incumbent, String auto_color_incumbent, float rating_incumbent,boolean status)   {
     myRef = database.getReference();
     Request user = new Request(submissionTime,username_incumbent,auto_licensePlate_incumbent,auto_model_incumbent,auto_color_incumbent,rating_incumbent,status);
     myRef.child("requestList").push().setValue(user);
     //addReservation(timeMeeting,"username_entrant","licensePlate_entrant",rating_entrant,model_entrant,color_entrant );

      return user;
  }
     Request[] requests = new Request[200];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_requests2, container, false);
        final ListView listMyRequests;
        listMyRequests= (ListView) rootView.findViewById(R.id.listMyRequests);


        myRef = database.getReference().child("requestList");

        //    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        myRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        int i = 0;
                        //iterate through each user, ignoring their UID
                        for (Map.Entry<String, Object> entry :((Map<String, Object>) dataSnapshot.getValue()).entrySet()){
                            //if(type == "Utenti"){
                            //Get user map
                            //Get phone field and append to list
                            if(i!=0){
                                Map keyValueFinalObj = (Map) entry.getValue();

                                String submissionTime = (String)keyValueFinalObj.get("submissionTime");
                                String username_incumbent = (String)keyValueFinalObj.get("username_incumbent");
                                String auto_licensePlate_incumbent = (String)keyValueFinalObj.get("auto_licensePlate_incumbent");
                                String auto_model_incumbent = (String) keyValueFinalObj.get("auto_model_incumbent");
                                String auto_color_incumbent = (String)keyValueFinalObj.get("auto_color_incumbent");
                                Long rating_incumbent = (Long)keyValueFinalObj.get("ratingIncumbent");
                                Boolean status = (Boolean)keyValueFinalObj.get("status");

                                requests[i-1]=new Request(submissionTime,username_incumbent,auto_licensePlate_incumbent,auto_model_incumbent,auto_color_incumbent,rating_incumbent,status);

                            }
                            i++;

                            System.out.println(requests.toString());
                        }
                        CustomAdapterRequest customAdapter = new CustomAdapterRequest(getActivity(), R.layout.row_layout_request, requests);
                        listMyRequests.setAdapter(customAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        return rootView;
    }

}
