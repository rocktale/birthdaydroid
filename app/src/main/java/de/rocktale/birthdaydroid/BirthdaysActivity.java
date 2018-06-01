package de.rocktale.birthdaydroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.rocktale.birthdaydroid.model.ContactWithBirthday;
import de.rocktale.birthdaydroid.ui.BirthdaysAdapter;

public class BirthdaysActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdays);

        mRecyclerView = (RecyclerView) findViewById(R.id.birthdayList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ContactWithBirthday[] birthdays = new ContactWithBirthday[3];
        birthdays[0] = new ContactWithBirthday();
        birthdays[0].fullName = "James Bond";
        birthdays[0].birthday = new GregorianCalendar(1976, Calendar.MARCH, 23).getTime();

        birthdays[1] = new ContactWithBirthday();
        birthdays[1].fullName = "Harry Potter";
        birthdays[1].birthday = new GregorianCalendar(1989, Calendar.NOVEMBER, 2).getTime();

        birthdays[2] = new ContactWithBirthday();
        birthdays[2].fullName = "John Snow";
        birthdays[2].birthday = new GregorianCalendar(1977, Calendar.JANUARY, 8).getTime();


        // specify an adapter (see also next example)
        mAdapter = new BirthdaysAdapter(birthdays);
        mRecyclerView.setAdapter(mAdapter);
    }
}
