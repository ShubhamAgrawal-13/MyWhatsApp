package com.example.mywhatsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mywhatsapp.MessageActivity;
import com.example.mywhatsapp.Model.Chat;
import com.example.mywhatsapp.Model.User;
import com.example.mywhatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.text.DateFormat.getDateTimeInstance;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageURL;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageURL) {
        this.mContext = mContext;
        this.mChat = mChat;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        String timestamp = chat.getTimestamp();

        // data & time conversion
        String dataTime = getTimeDate(Long.parseLong(timestamp));

        holder.timestamp.setText(dataTime);

        if(imageURL.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(mContext).load(imageURL).into(holder.profile_image);
        }

        if(position == mChat.size()-1){
            if(chat.isIsseen()){
                holder.txt_seen.setText("Seen");
            }
            else {
                holder.txt_seen.setText("delivered");
            }
        }else {
            if(chat.isIsseen()){
                holder.txt_seen.setVisibility(View.GONE);
            }
            else {
                holder.txt_seen.setText("delivered");
            }
        }

    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        return MSG_TYPE_LEFT;

    }

    public static String getTimeDate(long timestamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timestamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "datetime";
        }
    }
}


