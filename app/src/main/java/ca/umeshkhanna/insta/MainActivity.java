package ca.umeshkhanna.insta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.Typeface;
import android.widget.TextView;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    private EditText messageInput;
    private Button beginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            win.setFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            win.setFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        messageInput = (EditText)findViewById(R.id.message);
        beginButton = (Button)findViewById(R.id.beginButton);

        Typeface openSansLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        Typeface openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
        Typeface openSansRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        TextView prompt = (TextView)findViewById(R.id.prompt);
        Button inputRecipient = (Button)findViewById(R.id.insertRecipient);

        prompt.setTypeface(openSansLight);
        inputRecipient.setTypeface(openSansLight);
        beginButton.setTypeface(openSansBold);
        messageInput.setTypeface(openSansRegular);

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

