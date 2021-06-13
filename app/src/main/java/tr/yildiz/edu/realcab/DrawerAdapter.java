package tr.yildiz.edu.realcab;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DrawerAdapter  extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder>{

    private final Context context;
    private final ArrayList<Drawer> drawers;
    private final ArrayList<Clothe> clothes;

    public DrawerAdapter(Context context, ArrayList<Drawer> drawers, ArrayList<Clothe> clothes){
        this.context = context;
        this.drawers = drawers;
        this.clothes = clothes;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView drawerNumber;
        TextView drawerClothesNumber;
        ImageButton removeDrawer;
        ImageButton openDrawer;

        public MyViewHolder(View itemView){
            super(itemView);

            drawerNumber = itemView.findViewById(R.id.text_drawer_number_value);
            drawerClothesNumber = itemView.findViewById(R.id.text_drawer_clothes_number_value);
            removeDrawer = itemView.findViewById(R.id.button_remove_drawer);
            openDrawer = itemView.findViewById(R.id.button_open_drawer);
        }
    }

    @NonNull
    @Override
    public DrawerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_card, parent, false);
        return new DrawerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DrawerAdapter.MyViewHolder holder, final int position) {
        String number = String.valueOf(drawers.get(position).getNumber());
        holder.drawerNumber.setText(number);
        number = String.valueOf(drawers.get(position).getClothes().size());
        holder.drawerClothesNumber.setText(number);

        holder.removeDrawer.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
            alert.setIcon(R.drawable.icon_alert);
            alert.setMessage("Are you sure you want to remove?");
            alert.setTitle("Remove Drawer");

            alert.setPositiveButton("Yes", (dialog, whichButton) -> {
                drawers.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, drawers.size());

                Toast.makeText(v.getContext(), "Drawer removed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ActivityMain.class);

                sendExtra(intent);
                context.startActivity(intent);
            });
            alert.setNegativeButton("No", (dialog, whichButton) -> dialog.cancel());
            alert.show();
        });

        holder.openDrawer.setOnClickListener(v ->{
            Intent intent = new Intent(v.getContext(), ActivityClothesEdit.class);
            intent.putExtra("Drawer No", position);

            sendExtra(intent);
            context.startActivity(intent);
        });
    }

    private void sendExtra(Intent intent){
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("All Drawers", drawers);
        bundle.putParcelableArrayList("All Clothes", clothes);
        intent.putExtras(bundle);
    }

    @Override
    public int getItemCount() {
        return drawers.size();
    }
}
