package tr.yildiz.edu.realcab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Objects;

public class ActivityClothesList extends AppCompatActivity {

    private ArrayList<Clothe> clothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_list);
        defineVariables();
    }

    private void defineVariables() {
        RecyclerView recyclerViewList = findViewById(R.id.recyclerViewClothes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewList.setLayoutManager(layoutManager);

        getExtra();

        ClotheAdapter adapter = new ClotheAdapter(this, clothes);
        recyclerViewList.setAdapter(adapter);
    }

    private void getExtra(){
        clothes = new ArrayList<>();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("All Clothes")) {
                clothes = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Clothes");
            }
        }
        else{
            clothes = Clothe.getClothes();
        }
    }
}