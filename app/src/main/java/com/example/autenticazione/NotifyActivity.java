package com.example.autenticazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.example.autenticazione.Auto.AutoMetaData; //importante//
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NotifyActivity extends AppCompatActivity {
    private static final String[] FROMS = new String[]{AutoMetaData.LICENSEPLATE,
            AutoMetaData.MODEL, AutoMetaData.COLOR} ;
    private static final int[] TOS = {R.id.tvLicensePlate, R.id.tvModel,
            R.id.tvColor} ;
    private static final int CREATE_ACTIVITY_RESULT = 1;
    private static final int DELETE_MENU_OPTION = 2;
    private static final int UPDATE_MENU_OPTION = 1;
    private static final int UPDATE_ACTIVITY_RESULT = 2;
    private static final int NOTIFY_ACTIVITY = 4;
    private EditText etTime;
    private ImageView ivAuto;
    private ImageButton newTimeButton;
    private ImageButton ibNewAuto;
    private ImageButton ibCancelNotification;
    Context mcontext = this;
    private TextView tvTime;
    private SQLiteDatabase db;
    private Cursor cursor;
    private CursorAdapter adapter;
    private static final String TAG_LOG = "SQLite Auto" ;
    private static final int DB_VERSION = 3 ;
    private ListView list;
    private ConstraintLayout notifyLayout;
    //FirebaseDatabase database;
    //DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        initUI();

        //sfondo

        notifyLayout = (ConstraintLayout) this.findViewById(R.id.notifyLayout);
        notifyLayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));

        //time picker//
        newTimeButton = (ImageButton) findViewById(R.id.newTimeButton);
        tvTime = (TextView) findViewById(R.id.tvTime);
        Calendar calendar = Calendar.getInstance();
        final int hour1 = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute1 = calendar.get(Calendar.MINUTE);
        newTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(mcontext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        tvTime.setText(hour + ":" + minute);

                    }
                }, hour1, minute1, DateFormat.is24HourFormat(mcontext));
                timePickerDialog.show();
            }
        });

        //db sqlite con le auto, che vengono inserite dentro la listview
        list = (ListView) findViewById(android.R.id.list);
        db = dbHelper.getWritableDatabase();
        String sql = "SELECT _id, licensePlate, model, color FROM Auto";
        cursor = db.rawQuery(sql, null);
        adapter = new SimpleCursorAdapter(this, R.layout.row_layout_auto, cursor, FROMS, TOS, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        list.setAdapter(adapter);
        registerForContextMenu(list);

        //funzione che permette di selezionare l'auto desiderata dalla lista, ritornando subito dopo al menu principale.

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotifyActivity.this, MainActivity.class);
                /*L'ora inserita, e i dati dell'auto selezionata vengono inviati alla tabella Availability e viene creato
                un nuovo oggetto Availability;

                 */
                /*View view1 = (View)view.getParent();
                TextView licensePlate = (TextView) view1.findViewById(R.id.tvLicensePlate);
                TextView model = (TextView) view1.findViewById(R.id.tvModel);
                TextView color = (TextView) view1.findViewById(R.id.tvColor);
                Availability availability = new Availability(tvTime.getText().toString(),
                        licensePlate.getText().toString(), model.getText().toString(),
                        color.getText().toString());
                myRef.child("Availabilities").push().setValue(availability);

                 */
                Toast.makeText(NotifyActivity.this, "La tua segnalazione è stata salvata", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    //inizializzazione dei bottoni per tornare indietro o aggiungere auto

    public void initUI() {
        ibNewAuto = (ImageButton) findViewById(R.id.ibNewAuto);
        ibCancelNotification = (ImageButton) findViewById(R.id.ibCancelNotification);
        /*database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Availability");

         */
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListView();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    //creazione db: locato in View-Tool Windows-Device File Explorer-data-data-com.example.autenticaz - database

    private final SQLiteOpenHelper dbHelper = new SQLiteOpenHelper(this,
            "AUTO_DB", null, DB_VERSION) {



        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG_LOG, "Inizio Creazione DB");
            String sql = "";
            sql += "CREATE TABLE \"AUTO\" (";
            sql += "      \"_id\" INTEGER PRIMARY KEY AUTOINCREMENT,";
            sql += "      \"licensePlate\" TEXT NOT NULL,";
            sql += "      \"model\" TEXT NOT NULL,";
            sql += "      \"color\" TEXT NOT NULL";
            sql += ")";

            db.execSQL(sql);
            Log.i(TAG_LOG, "Creato db");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            /*
            String sql = "";
            sql += "DROP TABLE IF EXISTS \"AUTO\"";
            db.execSQL(sql);
            onCreate(db);

             */
        }


    };

    private void updateListView() {
       String sql = "SELECT _id, licensePlate, model, color FROM Auto";
       cursor = db.rawQuery(sql, null);
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged();
    }

    //menu di contesto che permette di modificare o eliminare elementi della lista
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        int group = Menu.FIRST;
        menu.add(group, DELETE_MENU_OPTION, Menu.FIRST, R.string.delete_option);
        menu.add(group, UPDATE_MENU_OPTION, Menu.FIRST + 1,
                R.string.update_option);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        long autoId = info.id;
        switch (item.getItemId()) {
            case DELETE_MENU_OPTION:
                db.delete("AUTO", "_id=" + autoId, null);
                updateListView();
                return true;
            case UPDATE_MENU_OPTION:
                Cursor tmpCursor = db.query(AutoMetaData.TABLE_NAME,
                        AutoMetaData.COLUMNS, "_id=" + autoId, null, null, null,
                        null);
                if (tmpCursor.moveToNext()) {
                    Intent updateIntent = new Intent(this, NewAutoActivity.class);
                    Bundle autoBundle = new Bundle();
                    Auto auto = new Auto();
                    auto.id = autoId;
                    auto.licensePlate = tmpCursor.getString(tmpCursor
                            .getColumnIndex(AutoMetaData.LICENSEPLATE));
                    auto.model = tmpCursor.getString(tmpCursor
                            .getColumnIndex(AutoMetaData.MODEL));
                    auto.color = tmpCursor.getString(tmpCursor
                            .getColumnIndex(AutoMetaData.COLOR));
                    autoBundle.putParcelable("auto", auto);
                    updateIntent.putExtra("auto", autoBundle);
                    startActivityForResult(updateIntent, UPDATE_ACTIVITY_RESULT);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //funzione per ereditare l'intent, a seconda che sia stato aggiunto un nuovo elemento o modificato un elemento preesistente aggiorna la lista//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                Bundle extra = data.getBundleExtra("auto");
                Auto auto = (Auto) extra.getParcelable("auto");
                String sql = "";
                switch (requestCode) {
                    case CREATE_ACTIVITY_RESULT:
                        sql += "INSERT INTO Auto (licensePlate, model, color) ";
                        sql += "VALUES ('" + auto.licensePlate+ "', '" + auto.model + "','" + auto.color + "')";
                        db.execSQL(sql);
                        break;
                    case UPDATE_ACTIVITY_RESULT:
                        sql += "UPDATE Auto ";
                        sql += "SET licensePlate     = '" + auto.licensePlate + "', ";
                        sql += "    model     = '" + auto.model + "', ";
                        sql += "    color  = '" + auto.color + "', ";
                        sql += "WHERE _id = '" + auto.id+ "'";
                        db.execSQL(sql);
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        updateListView();
    }

    //metodo per aggiungere direttamente nuova auto

    public void addNewAuto(View v) {
        Intent intent = new Intent(this, NewAutoActivity.class);
        startActivityForResult(intent, CREATE_ACTIVITY_RESULT);
    }

    //tornare indietro e annullare segnalazione

    public void cancelNotification(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}


