package com.example.lbelvale.ft_hangouts;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lbelvale on 12/23/16.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, List<Message> messages) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_message, parent, false);
        }

        MessageViewHolder viewHolder = (MessageViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new MessageViewHolder();
            viewHolder.message = (TextView) convertView.findViewById(R.id.message);
            convertView.setTag(viewHolder);
        }

        Message message = getItem(position);

        viewHolder.message.setText(message.getMessage());
        if (message.getSendMessage() == 0) {
            viewHolder.message.setGravity(Gravity.RIGHT);
            convertView.setBackgroundColor(Color.LTGRAY);
        }
        else {
            viewHolder.message.setGravity(Gravity.LEFT);
            convertView.setBackgroundColor(Color.GRAY);
        }
        return convertView;
    }

    private class  MessageViewHolder {
        public TextView message;
    }
}
