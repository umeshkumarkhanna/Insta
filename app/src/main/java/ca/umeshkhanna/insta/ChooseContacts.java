package ca.umeshkhanna.insta;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class ChooseContacts extends Activity {

    private ListView mainListView ;
    private ArrayAdapter<Contact> listAdapter ;
    ArrayList<Contact> contactList;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contacts);

        message = getIntent().getStringExtra("MESSAGE_FROM_USER");

        mainListView = (ListView) findViewById( R.id.mainListView );
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View item, int position, long id) {
                Contact contact = listAdapter.getItem( position );
                contact.toggleChecked();
                ContactViewHolder viewHolder = (ContactViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked( contact.isChecked() );
            }
        });

        contactList = new ArrayList<Contact>();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\D+", "");
            contactList.add(new Contact(name, phoneNumber));
        }
        phones.close();

        // Sort list alphabetically
        Collections.sort(contactList, new Comparator<Contact>() {
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getName().compareTo(contact2.getName());
            }
        });

        // Remove duplicates
        ArrayList<Contact> sortedContactList = new ArrayList<Contact>();
        Set<String> temp = new HashSet<String>();

        for( Contact c : contactList ) {
            if( temp.add(c.getName() + " " + c.getNumber()) ) {
                sortedContactList.add(c);
            }
        }

        listAdapter = new ContactArrayAdapter(this, sortedContactList);
        mainListView.setAdapter( listAdapter );
    }

    public void sendMessage(View view) {
        Intent openConfirmationActivity = new Intent(this, ConfirmationActivity.class);
        openConfirmationActivity.putExtra("MESSAGE_FROM_USER", message);
        openConfirmationActivity.putExtra("CONTACT_LIST", contactList);
        startActivity(openConfirmationActivity);
    }

    private static class ContactViewHolder {
        private CheckBox checkBox ;
        private TextView textView ;

        public ContactViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox ;
            this.textView = textView ;
        }
        public CheckBox getCheckBox() {
            return checkBox;
        }

        public TextView getTextView() {
            return textView;
        }
    }

    private static class ContactArrayAdapter extends ArrayAdapter<Contact> {

        private LayoutInflater inflater;

        public ContactArrayAdapter(Context context, List<Contact> contactList) {
            super( context, R.layout.simplerow, R.id.rowTextView, contactList );
            inflater = LayoutInflater.from(context) ;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CheckBox checkBox;
            TextView textView;

            Contact contact = (Contact) this.getItem( position );

            if ( convertView == null ) {
                convertView = inflater.inflate(R.layout.simplerow, null);

                textView = (TextView) convertView.findViewById( R.id.rowTextView );
                checkBox = (CheckBox) convertView.findViewById( R.id.CheckBox01 );

                convertView.setTag( new ContactViewHolder(textView, checkBox) );

                checkBox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox checkBox = (CheckBox) v ;
                        Contact contact = (Contact) checkBox.getTag();
                        contact.setChecked(checkBox.isChecked());
                    }
                });
            }
            else {
                ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox() ;
                textView = viewHolder.getTextView() ;
            }

            checkBox.setTag( contact );
            checkBox.setChecked(contact.isChecked());
            textView.setText( contact.getName() + " " + contact.getNumber() );

            return convertView;
        }
    }
}
