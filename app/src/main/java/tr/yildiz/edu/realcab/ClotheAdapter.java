package tr.yildiz.edu.realcab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ClotheAdapter extends RecyclerView.Adapter<ClotheAdapter.MyViewHolder>{

    private final Context context;
    private final ArrayList<Clothe> clothes;

    public ClotheAdapter(Context context, ArrayList<Clothe> clothes){
        this.context = context;
        this.clothes = clothes;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView type;
        TextView colour;
        TextView pattern;
        TextView purchaseDate;
        TextView price;

        public MyViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.clothe_image);
            type = itemView.findViewById(R.id.text_type_value);
            colour = itemView.findViewById(R.id.text_colour_value);
            pattern = itemView.findViewById(R.id.text_pattern_value);
            purchaseDate = itemView.findViewById(R.id.text_purchase_date_value);
            price = itemView.findViewById(R.id.text_price_value);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.clothe_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.imageView.setImageResource(Integer.parseInt(clothes.get(position).getImagePath()));
        holder.type.setText(clothes.get(position).getType());
        holder.colour.setText(clothes.get(position).getColour());
        holder.pattern.setText(clothes.get(position).getPattern());
        holder.purchaseDate.setText(clothes.get(position).getPurchaseDate());
        holder.price.setText(clothes.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }
}
