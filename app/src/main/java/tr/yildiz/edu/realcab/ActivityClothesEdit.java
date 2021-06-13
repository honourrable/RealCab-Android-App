package tr.yildiz.edu.realcab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ActivityClothesEdit extends AppCompatActivity {

    private ClotheEditAdapter adapter;
    private ImageButton addClothe;
    private ArrayList<Clothe> clothes;
    private ArrayList<Drawer> drawers;
    private int position;
    private static final int SELECT_PHOTO = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_edit);

        defineVariables();
        defineListeners();
    }

    private void defineListeners() {

        addClothe.setOnClickListener(v -> {
            drawers.get(position).getClothes().add(new Clothe());

            Intent intent = new Intent(v.getContext(), ActivityDrawers.class);
            sendExtra(intent);
            Toast.makeText(ActivityClothesEdit.this, "New clothe added", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });
    }

    private void defineVariables() {
        addClothe = findViewById(R.id.button_add_clothe);

        RecyclerView recyclerViewList = findViewById(R.id.recyclerViewEditClothes);
        GridLayoutManager layoutManager  = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewList.setLayoutManager(layoutManager);
        getExtra();

        adapter = new ClotheEditAdapter(this, drawers, position);
        recyclerViewList.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(ActivityClothesEdit.this, "No data in file!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Uri selectedImageURI = data.getData();
            assert selectedImageURI != null;
            File file = new File(selectedImageURI.getPath());
            String imagePath = file.getPath();

            clothes.get(adapter.getCurrentPosition()).setImagePath(imagePath);
            adapter.setCurrentImage(selectedImageURI);

            Toast.makeText(ActivityClothesEdit.this, "Image selected",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ActivityClothesEdit.this, "Could not open file!",
                    Toast.LENGTH_SHORT).show();
        }
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

        if(intent.hasExtra("Drawer No")) {
            position = intent.getExtras().getInt("Drawer No", 0);
        }
        else{
            position = 0;
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
        Intent intent = new Intent(ActivityClothesEdit.this, ActivityDrawers.class);
        sendExtra(intent);
        startActivity(intent);
    }
}