package com.example.autenticazione;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APICaller {

    /*
    La classe gestisce le chiamate alle API tramite RequestHandler.
    Ogni metodo pubblico punta ad un link diverso, ma l'idea è in ogni caso quella di effettuare una richiesta
    http e in base alla risposta mostrare un pop-up consono.
    Le operazioni sono gestite a BE.
     */

    public void nuovoParcheggioPersonaleStatus() {
        String code = "1";
        String url = generateURL(code); // genera url relativo con codice servizio e token
        handleResponse(url);
    }

    public void nuovoParcheggioStatus() {
        String code = "2";
        String url = generateURL(code);
        handleResponse(url);
    }

    public void nuovaRichiestaStatus() {
        String code = "3";
        String url = generateURL(code);
        handleResponse(url);
    }

    public void richiestaAccettataStatus() {
        String code = "4";
        String url = generateURL(code);
        handleResponse(url);
    }

    public void nuovaPrenotazioneStatus() {
        String code = "5";
        String url = generateURL(code);
        handleResponse(url);
    }


    private String generateURL(String code) {
        // Il metodo per ottenere il token andrà verificato e modificato se necessario
        String url = code + "/" + FirebaseAuth.getInstance().getAccessToken(true);
        // RequestHandler genererà l'url completo col metodo getAbsoluteUrl()
        return url;
    }


    private void handleResponse(String url) {
        /*
        Al momento si limita a loggare lo status ottenuto. Per eseguire altre azioni bisognerà modificare i metodi override in
        maniera opportuna. L'esplorazione di JSONArray e JSONObject dipende anche da ciò che il backend restituisce e andrà
        perciò rivista.
        L'url è ricevuto in maniera corretta mentre i parametri della richiesta sono null.
         */
        RequestHandler.get(url,
                null,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                        // Se si tratta di JSONArray dovrà prendere la posizione corretta
                        JSONObject json = null;
                        String status = "";
                        String data = "";
                        try {
                            json = (JSONObject) response.get(0);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            status = json.getString("status");
                            data = json.getString("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("ResponseType", "JSONArray");
                        Log.d("ResponseStatus", status);

                        // template per implementazione di azioni alternative a seconda dello status ricevuto
                        if (status == "OK") {
                            // mostrare pop-up che confermi il successo
                        } else {
                            // altrimenti mostrare pop-up di errore
                        }

                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        // se è JSONObject prendo direttamente gli elementi
                        String status = "";
                        String data = "";
                        try {
                            status = response.getString("users");
                            data = response.getString("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("ResponseType", "JSONObject");
                        Log.d("ResponseType", status);

                        // template per implementazione di azioni alternative a seconda dello status ricevuto
                        if (status == "OK") {
                            // mostrare pop-up che confermi il successo
                        } else {
                            // altrimenti mostrare pop-up di errore
                        }

                    }

                }
        );
    }
}
