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

public class ClotheEditAdapter extends RecyclerView.Adapter<ClotheEditAdapter.MyViewHolder>{
    
    private final Context context;
    private final ArrayList<Clothe> clothes;
    private final ArrayList<Drawer> drawers;
    private ImageView currentImage;
    private int currentPosition;
    private static final int SELECT_PHOTO = 1234;

    public ClotheEditAdapter(Context context, ArrayList<Drawer> drawers, int drawerPosition){
        this.context = context;
        this.drawers = drawers;

        clothes = drawers.get(drawerPosition).getClothes();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageButton changeImage;
        ImageButton removeClothe;

        public MyViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.clothe_edit_image);
            changeImage = itemView.findViewById(R.id.change_clothe_image);
            removeClothe = itemView.findViewById(R.id.remove_clothe_image);
        }
    }

    @NonNull
    @Override
    public ClotheEditAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.clothe_edit_card, parent, false);
        return new ClotheEditAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClotheEditAdapter.MyViewHolder holder, final int position) {

        if(clothes.get(position).getImagePath() == null){
            holder.imageView.setImageResource(R.drawable.icon_default_clothe);
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

                Toast.makeText(v.getContext(), "Clothe removed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ActivityDrawers.class);
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

        bundle.putParcelableArrayList("All Drawers", drawers);
        bundle.putParcelableArrayList("All Clothes", clothes);
        intent.putExtras(bundle);
    }

    public void setCurrentImage(Uri uri) {
        this.currentImage.setImageURI(uri);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }
}
