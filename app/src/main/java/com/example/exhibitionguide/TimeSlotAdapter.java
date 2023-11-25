package com.example.exhibitionguide;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    private ArrayList<String> timeSlots;
    private boolean a = true;
    private int lastPosition = -1;
    public TimeSlotAdapter(ArrayList<String> timeSlots) {
        this.timeSlots = timeSlots;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_slot_item, parent, false);
        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        String timeSlot = timeSlots.get(position);
        holder.timeSlot.setText(timeSlot);

        // Check if time slot is in the past

        if (position == lastPosition) {
            holder.timeSlot.setSelected(true);
            holder.timeSlot.setBackgroundColor(Color.CYAN);
        } else {
            holder.timeSlot.setSelected(false);
            holder.timeSlot.setBackgroundResource(R.drawable.border);
        }

        // Set the background color of the time slot TextView to blue if it's the current time slot
        if(a) {
             if(position == 0){
            holder.timeSlot.setBackgroundColor(Color.CYAN);
            a=false;
            lastPosition = holder.getAdapterPosition();
             }
          }

        holder.timeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int previousPosition = lastPosition;
                lastPosition = holder.getAdapterPosition();
                notifyItemChanged(previousPosition);
                notifyItemChanged(lastPosition);

                Context context = view.getContext();
                // Cast the context to MainActivity
                art_exhibiton mainActivity = (art_exhibiton) context;
                // Call the setText method from Top_Layer_activity and pass in the selected time slot
                mainActivity.setTexts(String.format(timeSlot));

            }
        });
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        private TextView timeSlot;

        public TimeSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            timeSlot = itemView.findViewById(R.id.time_slot_text_view);

        }
    }
}