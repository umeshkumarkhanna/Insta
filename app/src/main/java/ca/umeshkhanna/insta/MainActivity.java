package ca.umeshkhanna.insta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.AlertDialog;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private EditText messageInput;
    private Button beginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageInput = (EditText)findViewById(R.id.message);
        beginButton = (Button)findViewById(R.id.beginButton);
    }

    public void userClicked(View view){
        String message = messageInput.getText().toString();

        Intent openContactsPage = new Intent(this, ChooseContacts.class);
        openContactsPage.putExtra("MESSAGE_FROM_USER", message);
        startActivity(openContactsPage);
    }

    public void insertRecipientName(View view){
        messageInput.getText().insert(messageInput.getSelectionStart(), "[RECIPIENT]");
    }
}

