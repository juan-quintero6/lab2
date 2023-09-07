package unipiloto.edu.co.mymessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ReceiveMessageActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "Mensaje";
    private ArrayList<String> Messages2 = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        // Recupera la lista de mensajes almacenada en SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Set<String> savedMessages = sharedPref.getStringSet("messages", new HashSet<String>());
        Messages2.addAll(savedMessages);

        Intent intent = getIntent();
        String texto_mensaje = intent.getStringExtra(EXTRA_MESSAGE);
        TextView messageView = (TextView) findViewById(R.id.mensaje);
        messageView.setText(texto_mensaje);
        Messages2.add(texto_mensaje);
        updateMessagesView();
    }
    public void onSendMessage1(View view){
        EditText messageView = (EditText) findViewById(R.id.mensaje1);
        String texto_mensaje1 = messageView.getText().toString();

        // Almacena la lista actualizada en SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("messages", new HashSet<>(Messages2));
        editor.apply();

        Intent intent = new Intent(this, CreateMessageActivity.class);
        intent.putExtra(CreateMessageActivity.EXTRA_MESSAGE,texto_mensaje1);
        startActivity(intent);
        Messages2.clear();

    }
    private void updateMessagesView(){
        TextView historyView = (TextView) findViewById(R.id.history1);
        StringBuilder historyText = new StringBuilder();
        for(String message : Messages2){
            historyText.append(message).append('\n');
        }
        historyView.setText(historyText.toString());
    }
}