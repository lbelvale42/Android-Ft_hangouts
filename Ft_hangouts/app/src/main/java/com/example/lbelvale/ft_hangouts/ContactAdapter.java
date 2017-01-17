package com.example.lbelvale.ft_hangouts;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lbelvale on 12/20/16.
 */

public class ContactAdapter extends ArrayAdapter<Contact>{

    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, 0, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_contact, parent, false);
        }

        ContactViewHolder viewHolder = (ContactViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ContactViewHolder();
            viewHolder.firstname = (TextView) convertView.findViewById(R.id.firstname);
            viewHolder.lastname = (TextView) convertView.findViewById(R.id.lastname);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        Contact contact = getItem(position);

        viewHolder.firstname.setText(contact.getFirstname() + " " + contact.getLastname());
        viewHolder.lastname.setText(contact.getPhone());
        viewHolder.avatar.setImageDrawable(new ColorDrawable(contact.getColor()));

        return convertView;
    }

    private class ContactViewHolder {
        public TextView firstname;
        public TextView lastname;
        public ImageView avatar;
    }

}
