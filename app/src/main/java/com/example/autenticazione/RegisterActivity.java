package com.example.autenticazione;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private static final int PERMISSION_SAVE_FILE = 3;
    EditText nome;
    EditText email;
    EditText password;
    EditText confermaPassword;
    ImageView aggiungiImmagine;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private InputStream inputStreamImg;
    private Bitmap bitmap;
    private Bitmap rotated_bitmap;
    private ConstraintLayout registerLayout;
    private Bitmap bitmap1;
    //byte[] byteArrayImage;


    //Firebase
    private FirebaseAuth mAuth;

    //per DB utente
    //FirebaseDatabase database;
   // DatabaseReference myRef;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        Log.i(TAG, "okonstart");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //sfondo

        registerLayout = (ConstraintLayout) this.findViewById(R.id.te_login);
        registerLayout.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        initUI();
        Log.i(TAG, "okoncreate");

        aggiungiImmagine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }
    String emailGlobal;
    String nomeGlobal;
    private void saveUserGettingToken(String emailUtente,String nome){
        emailGlobal =emailUtente;
        nomeGlobal=nome;
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                // controlliamo se abbiamo errori nell'esecuzione del task
                if (task.isSuccessful()){
                    // tra tutti id ati contenuti nel task chiediamo il token
                    String token = task.getResult().getToken();
                    Log.d("token", token);
                    Utente user = new Utente(emailGlobal,nomeGlobal,token);
                    myRef.child("Utenti").push().setValue(user);
// per salvare il token dell'utente sul dispositivo
                    //      myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid().setValue(token));

                } else {
                    //segnaliamo che dà errore e ci facciamo restituire l'eccezione del task
                    Log.d("error","errore", task.getException());
                    return;
                }

            }
        });

    }

    public void initUI(){
        //ciao a tutti ok
         nome = (EditText) findViewById(R.id.et_nome);
         email = (EditText) findViewById(R.id.et_email);
         password = (EditText) findViewById(R.id.et_password);
         confermaPassword = (EditText) findViewById(R.id.et_confermaPassword);
         aggiungiImmagine = (ImageView) findViewById(R.id.ivUploadImage);


//inizializzazione database firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        getToken();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
}
    public void registrati (View view) {
        Log.i(TAG, "Cliccato sul pulsante registrati");
        String nomeUtente = nome.getText().toString();
        String emailUtente = email.getText().toString();
        String passwordUtente = password.getText().toString();
        String confermaPasswordUtente = confermaPassword.getText().toString();

        //metodo 2 trasformazione immagine in stringa

       /* String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeFile("/path/to/image.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] b = baos.toByteArray(); */



/*
        //metodo 1 trasformazione immagine in stringa
       Bitmap bitmap1 = ((BitmapDrawable) aggiungiImmagine.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        //stringa finale da utilizzare
        String encodedImage = Base64.encodeToString(imageInByte, Base64.NO_WRAP); */

//modo per salvare i dati nel DB
        saveUserGettingToken(emailUtente,nomeUtente);
        /*  Utente utente = new Utente(emailUtente);
        myRef.child("Utenti").push().setValue(utente);  */

        //FirebaseDatabase database = FirebaseDatabase.getInstance();



       //modo per salvare i dati nel DB
        /*if (nomeUtente.compareTo("")!= 0 && emailUtente.compareTo("")!=0 && passwordUtente.compareTo("")!=0 && confermaPasswordUtente.compareTo("")!=0 && encodedImage.compareTo("")!=0)
        {

        } */

        //per ripulire i campi una volta che l'utente ha inserito i dati e dunque si impostano a vuoti
        nome.setText("");
        email.setText("");
        password.setText("");
        confermaPassword.setText("");


        Log.i(TAG, nomeUtente);
        Log.i(TAG, emailUtente);
        Log.i(TAG, passwordUtente);
        Log.i(TAG, confermaPasswordUtente);


        //controllo se i campi sono corretti e se si si crea l'utente (funzioni invocate per i controlli sottostanti)
        if (!nomeValido(nomeUtente))
            Toast.makeText(this, "Inserire un nome di almeno 3 caratteri", Toast.LENGTH_SHORT).show();
        else if (!emailValida(emailUtente))
            Toast.makeText(this, "Inserire una e-mail corretta", Toast.LENGTH_SHORT).show();
        else if (!passwordValida(passwordUtente, confermaPasswordUtente))
            Toast.makeText(this, "Occhio alle password", Toast.LENGTH_SHORT).show();
        else
            createFirebaseUser(emailUtente,passwordUtente,nomeUtente);


    }


    private void createFirebaseUser(String email, String password, final String nome) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.i(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        setNome(nome);


                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.i(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }

                }
            });

    }


    public String getNome() {

       String name = nome.getText().toString();
        return name;
    }

    private void setNome(String nome) {
        FirebaseUser user= mAuth.getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(nome)
                .build();

        user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Log.i(TAG, "completata con successo");
                else
                    Log.i(TAG, "errore");
            }
        });
    }

    private void updateUI(FirebaseUser user) {

    }


    public void login(View view) {
        Intent intent =new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //controllo sui campi inseriti per registrarsi

    private boolean nomeValido(String nome){
        if (nome.length()>3)
            return true;
        else
            return false;
    }

    private boolean emailValida(String email){
        if (email.contains("@") && email.contains("."))
            return true;
        else
            return false;
    }

    private boolean passwordValida(String password1, String password2){
        if (password1.compareTo(password2) == 0 && password1.length()>5)
            return true;
        else
            return false;
    }

    //funzione che permette di inserire l'immagine utente aprendo un dialog che permette di scegliere fra galleria e fotocamera

    private void selectImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_SAVE_FILE);
            }
        } else {
            start();
        }
    }

    //apertura dialog per caricamento foto
    private void start() {
        final CharSequence[] options = {"Scatta foto", "Scegli dalla galleria", "Annulla"};
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Scegli un'opzione");
        alertDialog.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Scatta foto")) {
                    dialog.dismiss();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, PICK_IMAGE_CAMERA);
                } else if (options[item].equals("Scegli dalla galleria")) {
                    dialog.dismiss();
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
                } else if (options[item].equals("Annulla")) {
                    dialog.dismiss();
                }
            }
            });
        alertDialog.show();
     }

    //caricamento della foto scattata o caricata sulla schermata di registrazione
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == PICK_IMAGE_CAMERA) {

            Uri selectedImage = data.getData();



            bitmap = (Bitmap) data.getExtras().get("data");
            rotated_bitmap= RotateBitmap(bitmap,90);
            aggiungiImmagine.setImageBitmap(rotated_bitmap);

        } else if (requestCode == PICK_IMAGE_GALLERY) {

            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            aggiungiImmagine.setImageBitmap(bitmap);
        }
    }

    //NB nel caso di emulatori di livello 10, questa funzione e la sua applicazione non sono necessarie

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    // questo metodo ci permette di ottenere il token dell'utente
    public void getToken(){
        // si cerca di ottenere l'istanza dell'oggetto Firebase che si sta utilizzando
        //in questo modo ci facciamo restituire l'id relativo all'istanza di Firebase che si sta utilizzando
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                // controlliamo se abbiamo errori nell'esecuzione del task
                if (task.isSuccessful()){
                    // tra tutti id ati contenuti nel task chiediamo il token
                    String token = task.getResult().getToken();
                    Log.d("token", token);

// per salvare il token dell'utente sul dispositivo
                    //      myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid().setValue(token));

                } else {
                    //segnaliamo che dà errore e ci facciamo restituire l'eccezione del task
                    Log.d("error","errore", task.getException());
                    return;
                }
            }
        });
    }



}

