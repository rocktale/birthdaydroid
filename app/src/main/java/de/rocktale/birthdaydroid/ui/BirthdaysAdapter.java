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

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        if (contacts != null) {
            holder.bind(contacts.get(position));
        }
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
        if (contacts != null) {
            return contacts.size();
        } else {
            return 0;
        }
    }

    public void setContacts(List<Contact> contacts)
    {
        this.contacts = contacts;
    }
}
