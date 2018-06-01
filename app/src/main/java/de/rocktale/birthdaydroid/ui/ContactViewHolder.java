package de.rocktale.birthdaydroid.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import de.rocktale.birthdaydroid.R;
import de.rocktale.birthdaydroid.model.ContactWithBirthday;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private TextView contactName;
    private TextView contactBirthday;
    private TextView contactAge;

    private DateFormat mDateFormat;

    public ContactViewHolder(final View itemView) {
        super(itemView);
        contactName = itemView.findViewById(R.id.contactName);
        contactBirthday = itemView.findViewById(R.id.contactBirthday);
        contactAge = itemView.findViewById(R.id.contactAge);

        mDateFormat = new SimpleDateFormat("dd.MM.yyyy");
    }

    public void bind(ContactWithBirthday c)
    {
        contactName.setText(c.fullName);
        contactBirthday.setText(mDateFormat.format(c.birthday));

        // display age after next birthday
        contactAge.setText(Integer.toString(c.getAge() + 1));
    }
}
