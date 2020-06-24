package com.example.autenticazione;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMyAvailabilities extends Fragment {


    public FragmentMyAvailabilities() {
        // Required empty public constructor
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public Availability addAvailability(String submissionTime, String username_incumbent, String auto_licensePlate_incumbent, String auto_model_incumbent)   {
        myRef = database.getReference();
        Availability user = new Availability(submissionTime,username_incumbent,auto_licensePlate_incumbent,auto_model_incumbent);
        myRef.child("availabilityList").push().setValue(user);
        //addReservation(timeMeeting,"username_entrant","licensePlate_entrant",rating_entrant,model_entrant,color_entrant );

        return user;
    }
    Availability[] availabilities = new Availability[200];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_availabilities, container, false);
        final ListView listMyAvailabilites;
        listMyAvailabilites= (ListView)rootView.findViewById(R.id.listMyAvailabilities);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference().child("availabilityList");

        //    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        myRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot

                        int i = 0;
                        //iterate through each user, ignoring their UID
                        for (Map.Entry<String, Object> entry :   ((Map<String, Object>) dataSnapshot.getValue()).entrySet()){

                            if(i!=0){
                                Map keyValueFinalObj = (Map) entry.getValue();

                                String submissionTime = (String)keyValueFinalObj.get("time");
                                String username_incumbent = (String)keyValueFinalObj.get("auto_licensePlate_incumbent");
                                String auto_licensePlate_incumbent = (String)keyValueFinalObj.get("auto_model_incumbent");
                                String auto_model_incumbent = (String) keyValueFinalObj.get("auto_color_incumbent");


                                availabilities[i-1]= new Availability(submissionTime,username_incumbent,auto_licensePlate_incumbent,auto_model_incumbent);


                            }
                            i++;

                            System.out.println(availabilities.toString());
                        }
                        Log.i("TAG", "onCreateView: costom adapter "+availabilities);
                        CustomAdapterAvailability customAdapter = new CustomAdapterAvailability(getActivity(), R.layout.row_layout_availabilities, availabilities);
                        listMyAvailabilites.setAdapter(customAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
   /*   Reservation reservation = new Reservation ("11:10","Giuseppe", "AT060EK", 3, "Audi", "Rosso");
        Reservation reservation_2 = new Reservation("18:30", "Maria", "LS328GE", 2, "Cinquecento", "Rosa");
        reservations[0]= reservation;
        reservations[1]= reservation_2;*/

        return rootView;
    }





    private  Availability[] collectPhoneNumbers(Map<String,Object> users) {

        Availability[] phoneNumbers = null;
        String type ="";
        int i = 0;
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){
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
                Float rating_incumbent = (Float)keyValueFinalObj.get("rating_incumbent");
                Boolean status = (Boolean)keyValueFinalObj.get("status");

                phoneNumbers[i-1]=(new Availability(submissionTime,username_incumbent,auto_licensePlate_incumbent,auto_model_incumbent));
          /* }else {
                Map keyValueFinalObj = (Map) entry.getValue();
                type = (String) entry.getKey();
            }*/

            }
            i++;

            System.out.println(phoneNumbers.toString());
        }
        return phoneNumbers;
    }
}
