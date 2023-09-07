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

public class CreateMessageActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Mensaje";

    private ArrayList<String> Messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

        // Recupera la lista de mensajes almacenada en SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Set<String> savedMessages = sharedPref.getStringSet("messages", new HashSet<String>());
        Messages.addAll(savedMessages);

        Intent intent = getIntent();
        String texto_mensaje1 = intent.getStringExtra(EXTRA_MESSAGE);
        TextView messageView = (TextView) findViewById(R.id.mensaje1);
        messageView.setText(texto_mensaje1);
        Messages.add(texto_mensaje1);
        updateMessagesView();
    }
    public void onSendMessage(View view) {
        EditText messageView = (EditText) findViewById(R.id.mensaje);
        String texto_mensaje = messageView.getText().toString();

        // Almacena la lista actualizada en SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet("messages", new HashSet<>(Messages));
        editor.apply();

        Intent intent = new Intent(this, ReceiveMessageActivity.class);
        intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, texto_mensaje);
        startActivity(intent);
        Messages.clear();
    }
    private void updateMessagesView(){
        TextView historyView = (TextView) findViewById(R.id.history1);
        StringBuilder historyText = new StringBuilder();
        for(String message : Messages){
            historyText.append(message).append('\n');
        }
        historyView.setText(historyText.toString());
    }
}



