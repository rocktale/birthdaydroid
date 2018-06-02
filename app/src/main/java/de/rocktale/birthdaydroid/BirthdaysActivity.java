package de.rocktale.birthdaydroid;

import android.Manifest;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.rocktale.birthdaydroid.model.Birthday;
import de.rocktale.birthdaydroid.model.Contact;
import de.rocktale.birthdaydroid.model.SortContactsByNextBirthday;
import de.rocktale.birthdaydroid.ui.BirthdaysAdapter;

public class BirthdaysActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = "BirthdaysActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdays);

        mRecyclerView = findViewById(R.id.birthdayList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.ItemDecoration deviderItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(deviderItemDecoration);


        requestContacts();
    }


    // Request code for READ_CONTACTS
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private void requestContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            showBirthdays();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showBirthdays();
            } else {
                Log.e("Permissions", "Access denied");
            }
        }
    }


    // method to get name, contact id, and birthday
    private Cursor getContactsBirthdays() {
        Uri uri = ContactsContract.Data.CONTENT_URI;

        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.START_DATE,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };

        String where =
                ContactsContract.Data.MIMETYPE + "= ? AND " +
                        ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY;
        String[] selectionArgs = new String[] {
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE
        };
        return getContentResolver().query(uri, projection, where, selectionArgs, null);
    }

    private void showBirthdays() {
        Cursor cursor = getContactsBirthdays();
        int bDayColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE);
        int nameColumn = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        int idColumn = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);

        List<Contact> contacts = new ArrayList<>(cursor.getCount());

        while (cursor.moveToNext()) {

            Contact c = new Contact(cursor.getString(nameColumn));

            try
            {
                c.birthday = new Birthday(cursor.getString(bDayColumn));
            }
            catch (DateTimeParseException e)
            {
                Log.e(TAG, "Failed to parse birthday for contact '" + c.fullName + "': " + cursor.getString(bDayColumn));
                continue;
            }

            long contactId = cursor.getLong(idColumn);
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
            c.profilePicture = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);
            contacts.add(c);
        }

        // sort the array in the order of next birthdays
        Collections.sort(contacts, new SortContactsByNextBirthday(LocalDate.now()));

        // set the adapter on the recycler view
        mAdapter = new BirthdaysAdapter(contacts);
        mRecyclerView.setAdapter(mAdapter);
    }
}
