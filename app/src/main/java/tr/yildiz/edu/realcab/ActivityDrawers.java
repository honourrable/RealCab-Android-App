package tr.yildiz.edu.realcab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

public class ActivityDrawers extends AppCompatActivity {

    private ImageButton createDrawer;
    private ArrayList<Drawer> drawers;
    private ArrayList<Clothe> clothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawers);
        defineVariables();
        defineListeners();
    }

    private void defineListeners() {

        createDrawer.setOnClickListener(v -> {
            ArrayList<Clothe> newClothes = new ArrayList<>();
            drawers.add(new Drawer(drawers.size() + 1, newClothes));

            Intent intent = new Intent(v.getContext(), ActivityMain.class);
            sendExtra(intent);
            Toast.makeText(ActivityDrawers.this, "New drawer added", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

    private void defineVariables() {
        createDrawer = findViewById(R.id.button_create_drawer);

        RecyclerView recyclerViewList = findViewById(R.id.recyclerViewDrawers);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewList.setLayoutManager(layoutManager);

        getExtra();

        DrawerAdapter adapter = new DrawerAdapter(this, drawers, clothes);
        recyclerViewList.setAdapter(adapter);
    }

    private void getExtra(){
        drawers = new ArrayList<>();
        clothes = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("All Drawers")) {
                drawers = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Drawers");
            }
            else{
                drawers = Drawer.getDrawers();
            }
        }

        if(extras != null){
            if (extras.containsKey("All Clothes")) {
                clothes = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Clothes");
            }
            else{
                clothes = Clothe.getClothes();
            }
        }
    }

    private void sendExtra(Intent intent){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("All Drawers", drawers);
        bundle.putParcelableArrayList("All Clothes", clothes);
        intent.putExtras(bundle);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityDrawers.this, ActivityMain.class);
        sendExtra(intent);
        startActivity(intent);
    }
}