package ca.umeshkhanna.insta;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import java.util.ArrayList;
public class ConfirmationActivity extends Activity {

    ArrayList<Contact> contactList;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            win.setFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            win.setFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        Typeface openSansLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        Typeface openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
        Typeface openSansRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        TextView messagePreviewTitle = (TextView)findViewById(R.id.preview_message_title);
        messagePreviewTitle.setTypeface(openSansBold);
        TextView recipientsPreviewTitle = (TextView)findViewById(R.id.preview_recipients_title);
        recipientsPreviewTitle.setTypeface(openSansBold);

        message = getIntent().getStringExtra("MESSAGE_FROM_USER");
        contactList = new ArrayList<Contact>();
        contactList = (ArrayList<Contact>)getIntent().getSerializableExtra("CONTACT_LIST");

        TextView messagePreview = (TextView)findViewById(R.id.message_preview);
        messagePreview.setText(message);
        messagePreview.setTypeface(openSansLight);

        TextView recipientsPreview = (TextView)findViewById(R.id.recipients_preview);
        recipientsPreview.setTypeface(openSansLight);
        String recipientsList = "";
        for (Contact c : contactList) {
            if (c.isChecked()) {
                if (recipientsList.equals("")) recipientsList += c.getName();
                else recipientsList += ", " + c.getName();
            }
        }
        recipientsPreview.setText(recipientsList);
    }

    public void confirmSendMessage(View view) {
        SmsManager sms = SmsManager.getDefault();
        String recipientName, finalMessage;
        for (Contact c : contactList) {
            if (c.isChecked()) {
                recipientName = c.name.split(" ")[0];
                finalMessage = message.replace("[RECIPIENT]", recipientName);
                sms.sendTextMessage(c.phoneNumber, null, finalMessage, null, null);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmationActivity.this);
        builder.setTitle("Message sent");
        builder.setMessage("Your message has been successfully sent to the selected contacts.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent goHome = new Intent(ConfirmationActivity.this, MainActivity.class);
                dialog.dismiss();
                startActivity(goHome);

            }
        });
        builder.show();
    }
}
