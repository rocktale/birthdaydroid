package de.rocktale.birthdaydroid.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import de.rocktale.birthdaydroid.R;
import de.rocktale.birthdaydroid.model.Contact;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private TextView contactName;
    private TextView contactBirthday;
    private TextView contactAge;

    private ImageView contactThumb;

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final ColorGenerator colors = ColorGenerator.MATERIAL;

    ContactViewHolder(final View itemView) {
        super(itemView);
        contactName = itemView.findViewById(R.id.contactName);
        contactBirthday = itemView.findViewById(R.id.contactBirthday);
        contactAge = itemView.findViewById(R.id.contactAge);
        contactThumb = itemView.findViewById(R.id.contactThumb);
    }

    public void bind(Contact c) {
        contactName.setText(c.fullName);
        contactBirthday.setText(dateFormat.format(c.birthday.getDate()));

        // display age after next birthday
        contactAge.setText(Long.toString(c.birthday.currentAge(LocalDate.now()) + 1));

        String initials = "?";
        if (!c.fullName.isEmpty())
        {
            initials = Character.toString(c.fullName.charAt(0));
        }
        TextDrawable fallbackImage = TextDrawable.builder()
                .buildRound(initials, colors.getColor(initials));


        if (c.profilePicture != null) {
            Picasso.get()
                    .load(c.profilePicture)
                    .placeholder(fallbackImage)
                    .error(fallbackImage)
                    .transform(new CircleImageTransformation())
                    .resize(48, 48)
                    .into(contactThumb);

        }
    }
}
