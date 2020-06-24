package com.example.autenticazione;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.android.gms.common.api.Response;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRequestsToMe extends Fragment {


    public FragmentRequestsToMe() {
        // Required empty public constructor
    }


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    public Reservation addReservation(String timeMeeting, String username_entrant, String licensePlate_entrant, long rating_entrant, String model_entrant, String color_entrant)   {
        myRef = database.getReference();
        Reservation user = new Reservation(timeMeeting,username_entrant,licensePlate_entrant,rating_entrant,model_entrant,color_entrant);
        myRef.child("ReservationList").push().setValue(user);
        return user;
    }
     Reservation[] reservations = new Reservation[200];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       // Log.i("CREATION", "ciaooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_requests_to_me2, container, false);
 /*       addReservation("11:10","Giuseppe", "AT060EK", 3, "Audi", "Rosso");
        addReservation("11:10","Giuseppe", "AT060EK", 3, "Audi", "Rosso");
        addReservation("11:10","Giuseppe", "AT060EK", 3, "Audi", "Rosso");
        addReservation("11:10","Giuseppe", "AT060EK", 3, "Audi", "Rosso");
        addReservation("11:10","Giuseppe", "AT060EK", 3, "Audi", "Rosso");
        addReservation("11:10","Giuseppe", "AT060EK", 3, "Audi", "Rosso");
        addReservation("11:10","Giuseppe", "AT060EK", 3, "Audi", "Rosso");
*/
        final ListView listRequestsToMe;
        listRequestsToMe= (ListView)rootView.findViewById(R.id.listRequestsToMe);



        //collegamento a web server

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference().child("ReservationList");

        //    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        myRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot

                        int i = 0;
                        //iterate through each user, ignoring their UID
                        for (Map.Entry<String, Object> entry :   ((Map<String, Object>) dataSnapshot.getValue()).entrySet()){
                            //if(type == "Utenti"){
                            //Get user map
                            //Get phone field and append to list
                            if(i!=0){
                                Map keyValueFinalObj = (Map) entry.getValue();

                                String timeMeeting = (String)keyValueFinalObj.get("timeMeeting");
                                String username_entrant = (String)keyValueFinalObj.get("username_entrant");
                                String licensePlate_entrant = (String)keyValueFinalObj.get("licensePlate_entrant");
                                long rating_entrant = (long) keyValueFinalObj.get("rating_entrant");
                                String model_entrant = (String)keyValueFinalObj.get("model_entrant");
                                String color_entrant = (String)keyValueFinalObj.get("color_entrant");

                                reservations[i-1]= new Reservation(timeMeeting,username_entrant,licensePlate_entrant,rating_entrant,model_entrant,color_entrant);
          /* }else {
                Map keyValueFinalObj = (Map) entry.getValue();
                type = (String) entry.getKey();
            }*/

                            }
                            i++;

                            System.out.println(reservations.toString());
                        }
                        Log.i("TAG", "onCreateView: costom adapter "+reservations);
                        CustomAdapter customAdapter = new CustomAdapter(getActivity(), R.layout.j, reservations );
                        listRequestsToMe.setAdapter(customAdapter);
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

    private  Reservation[] collectPhoneNumbers(Map<String,Object> users) {

        Reservation[] phoneNumbers=null;
        String type ="";

        return phoneNumbers;
    }
    /*public void getAllReservation()   {
        myRef = database.getReference();
        myRef.child("ReservationList");
        //  return user;
        Query query = myRef.orderByKey().limitToFirst(10);
Log.i("ciao", String.valueOf(query))  ;      ChildEventListener reservationListener=new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Reservation post = dataSnapshot.getValue(Reservation.class);
                Log.i("ciao", "child add"+post.toString());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Reservation post = dataSnapshot.getValue(Reservation.class);
                Log.i("ciao", "child change"+post.toString());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Reservation post = dataSnapshot.getValue(Reservation.class);
                Log.i("ciao", "child removed"+post.getColor_entrant());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Reservation post = dataSnapshot.getValue(Reservation.class);
                Log.i("ciao", "child moved"+post);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        query.addChildEventListener(reservationListener);//in ascolto su i primi 10
        myRef.child("ReservationList").addChildEventListener((reservationListener));//in ascolto su tutta la lista
    }*/
}
