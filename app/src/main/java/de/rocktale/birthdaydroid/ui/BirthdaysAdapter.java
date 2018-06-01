package de.rocktale.birthdaydroid.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.rocktale.birthdaydroid.R;
import de.rocktale.birthdaydroid.model.ContactWithBirthday;

public class BirthdaysAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private ContactWithBirthday[] mBirthdays;

    public BirthdaysAdapter(ContactWithBirthday[] birthdays) {
        this.mBirthdays = birthdays;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(mBirthdays[position]);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View listItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.birthday_list_item, parent, false);

        return new ContactViewHolder(listItemView);
    }

    @Override
    public int getItemCount() {
        return mBirthdays.length;
    }
}
