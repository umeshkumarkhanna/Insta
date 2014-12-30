package ca.umeshkhanna.insta;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.View;
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

        message = getIntent().getStringExtra("MESSAGE_FROM_USER");
        contactList = new ArrayList<Contact>();
        contactList = (ArrayList<Contact>)getIntent().getSerializableExtra("CONTACT_LIST");

        TextView messagePreview = (TextView)findViewById(R.id.message_preview);
        messagePreview.setText(message);

        TextView recipientsPreview = (TextView)findViewById(R.id.recipients_preview);
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
