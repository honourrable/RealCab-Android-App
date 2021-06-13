package tr.yildiz.edu.realcab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EventEditAdapter extends RecyclerView.Adapter<EventEditAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<Event> events;
    private final ArrayList<Clothe> clothes;
    private int eventNo;
    private int currentPosition;
    private ImageView currentImage;
    private static final int SELECT_PHOTO = 123456;

    public EventEditAdapter(Context context, ArrayList<Event> events, int eventNo){
        this.context = context;
        this.events = events;
        this.eventNo = eventNo;

        clothes = events.get(eventNo).getEventClothes();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageButton changeImage;
        ImageButton removeClothe;

        public MyViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.event_clothe_image);
            changeImage = itemView.findViewById(R.id.change_event_clothe);
            removeClothe = itemView.findViewById(R.id.event_remove_clothe);
        }
    }

    @NonNull
    @Override
    public EventEditAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_edit_card, parent, false);
        return new EventEditAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EventEditAdapter.MyViewHolder holder, final int position) {

        if(clothes.get(position).getImagePath() == null){
            holder.imageView.setImageResource(R.drawable.icon_default_clothe);
        }
        else {
            holder.imageView.setImageResource(Integer.parseInt(clothes.get(position).getImagePath()));
        }
//        else{
//            File file = new File(clothes.get(position).getImagePath());
//            Uri uri = Uri.fromFile(file);
//            holder.imageView.setImageURI(uri);
//        }

        holder.removeClothe.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
            alert.setIcon(R.drawable.icon_alert);
            alert.setMessage("Are you sure you want to remove?");
            alert.setTitle("Remove Clothe");

            alert.setPositiveButton("Yes", (dialog, whichButton) -> {
                clothes.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, clothes.size());
                events.get(eventNo).setEventClothes(clothes);

                Toast.makeText(v.getContext(), "Clothe removed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ActivityEvents.class);
                sendExtra(intent);
                context.startActivity(intent);
            });
            alert.setNegativeButton("No", (dialog, whichButton) -> dialog.cancel());
            alert.show();
        });

        holder.changeImage.setOnClickListener(v -> {
            currentPosition = position;
            currentImage = holder.imageView;

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Pick an image"), SELECT_PHOTO);
        });
    }

    private void sendExtra(Intent intent){
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("All Events", events);
        intent.putExtras(bundle);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentImage(Uri uri) {
        this.currentImage.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }
}
