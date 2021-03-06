package de.rocktale.birthdaydroid.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import de.rocktale.birthdaydroid.R;
import de.rocktale.birthdaydroid.model.Contact;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private TextView contactName;
    private TextView contactBirthday;
    private TextView contactAge;

    private ImageView contactThumb;

    private View tableRow;
    private Context context;

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final ColorGenerator colors = ColorGenerator.MATERIAL;

    ContactViewHolder(final View itemView) {
        super(itemView);
        contactName = itemView.findViewById(R.id.contactName);
        contactBirthday = itemView.findViewById(R.id.contactBirthday);
        contactAge = itemView.findViewById(R.id.contactAge);
        contactThumb = itemView.findViewById(R.id.contactThumb);
        tableRow = itemView.findViewById(R.id.listEntry);
        context = itemView.getContext();
    }

    public void bind(final Contact c) {
        contactName.setText(c.fullName);
        contactBirthday.setText(dateFormat.format(c.birthday.getDate()));

        final LocalDate today = LocalDate.now();

        // display age on next birthday
        contactAge.setText(Long.toString(c.birthday.ageOnNextBirthday(today)));

        String initials = "?";
        if (!c.fullName.isEmpty())
        {
            initials = Character.toString(c.fullName.charAt(0));
        }
        final TextDrawable fallbackImage = TextDrawable.builder()
                .buildRound(initials, colors.getColor(initials));


        if (c.profilePicture != null) {
            Picasso.get()
                    .load(c.profilePicture)
                    .placeholder(fallbackImage)
                    .error(fallbackImage)
                    .transform(new CircleImageTransformation())
                    .fit()
                    .centerInside()
                    .into(contactThumb);

        }

        if (c.birthday.isAtDate(today))
        {
            tableRow.setBackgroundColor(ContextCompat.getColor(context, R.color.list_background_highlight));
        }
        else
        {
            tableRow.setBackgroundColor(Color.TRANSPARENT);
        }

        String birthdayString;
        long remainingDays = c.birthday.daysTillNextBirthday(today);
        if (remainingDays == 0)
        {
            birthdayString = "heute";
        } else if (remainingDays == 1)
        {
            birthdayString = "morgen";
        } else
        {
            birthdayString = "in " + Long.toString(remainingDays) + " Tagen";
        }

        final String msg = c.fullName + " hat " + birthdayString + " Geburtstag.";

        tableRow.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
