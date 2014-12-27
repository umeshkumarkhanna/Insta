package ca.umeshkhanna.insta;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.AlertDialog;

import java.util.ArrayList;
import ca.umeshkhanna.insta.R;

public class ChooseContacts extends Activity {
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contacts);
        layout = (RelativeLayout)findViewById(R.id.chooseContactsLayout);

        Intent intent = getIntent();
        final String message = intent.getStringExtra("MESSAGE_FROM_USER");

            final ArrayList<MyContacts> contacts = new ArrayList<MyContacts>();
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);

            while (phones.moveToNext())
            {
                String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\D+", "");

                contacts.add(new MyContacts(name, phoneNumber));

            }
            phones.close();

            System.out.println(contacts.get(3).name);
            ScrollView sv = new ScrollView(this);
            final LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.VERTICAL);
            sv.addView(ll);
            Button selectb = new Button(this);
            selectb.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub

                }

            });
            selectb.setText("Select All");
            ll.addView(selectb);
            for(MyContacts c : contacts){
                CheckBox cb = new CheckBox(this);
                cb.setText(c.name + " " + c.phoneNumber);
                c.cb = cb;
                ll.addView(cb);
            }
            Button b = new Button(this);
            final SmsManager sms = SmsManager.getDefault();
            b.setText("text them!");
            ll.addView(b);
            b.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    for(MyContacts c: contacts){
                        if(c.cb.isChecked()){
                            String first = c.name.split(" ")[0];
                            sms.sendTextMessage(c.phoneNumber, null, "Hey " + first + "!" + message, null, null);
                        }

                    }

                }

            });

            this.setContentView(sv);


        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

    }
