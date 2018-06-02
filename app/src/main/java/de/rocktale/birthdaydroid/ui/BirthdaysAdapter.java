package de.rocktale.birthdaydroid.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.rocktale.birthdaydroid.R;
import de.rocktale.birthdaydroid.model.Contact;

public class BirthdaysAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private List<Contact> contacts;

    public BirthdaysAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position));
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
        return contacts.size();
    }
}
