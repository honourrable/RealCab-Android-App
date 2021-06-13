package tr.yildiz.edu.realcab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{

    private final Context context;
    private final ArrayList<Event> events;

    public EventAdapter(Context context, ArrayList<Event> events){
        this.context = context;
        this.events = events;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        TextView type;
        TextView date;
        TextView location;
        ImageButton removeEvent;
        ImageButton editEvent;

        public MyViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.image_event);
            name = itemView.findViewById(R.id.event_name);
            type = itemView.findViewById(R.id.event_type);
            date = itemView.findViewById(R.id.event_date);
            location = itemView.findViewById(R.id.event_location);
            removeEvent = itemView.findViewById(R.id.remove_event);
            editEvent = itemView.findViewById(R.id.update_event);
        }
    }

    @NonNull
    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.event_card, parent, false);
        return new EventAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventAdapter.MyViewHolder holder, final int position) {
        holder.imageView.setImageResource(R.drawable.icon_stage);
        holder.name.setText(events.get(position).getEventName());
        holder.type.setText(events.get(position).getEventType());
        holder.date.setText(events.get(position).getEventDate());
        holder.location.setText(events.get(position).getEventLocation());

        holder.removeEvent.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            alert.setIcon(R.drawable.icon_alert);
            alert.setMessage("Are you sure you want to remove?");
            alert.setTitle("Remove Event");

            alert.setPositiveButton("Yes", (dialog, whichButton) -> {
                events.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, events.size());

                Toast.makeText(view.getContext(), "Event removed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ActivityMain.class);

                sendExtra(intent);
                context.startActivity(intent);
            });
            alert.setNegativeButton("No", (dialog, whichButton) -> dialog.cancel());
            alert.show();
        });

        holder.editEvent.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ActivityEventEdit.class);
            sendExtra(intent);
            intent.putExtra("Event No", position);
            context.startActivity(intent);
        });
    }

    private void sendExtra(Intent intent){
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("All Events", events);
        intent.putExtras(bundle);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
