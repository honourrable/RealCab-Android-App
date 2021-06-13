package tr.yildiz.edu.realcab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Objects;

public class ActivityMain extends AppCompatActivity implements View.OnClickListener{

    private ImageButton buttonDrawer;
    private ImageButton buttonClotheList;
    private ImageButton buttonChangeRoom;
    private ImageButton buttonCombines;
    private ImageButton buttonExitApp;
    private ArrayList<Drawer> drawers;
    private ArrayList<Clothe> clothes;
    private ArrayList<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defineVariables();
        defineListeners();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle = new Bundle();

        switch (v.getId()){

            case R.id.button_drawers:
                intent = new Intent(v.getContext(), ActivityDrawers.class);

                // All drawers list is shared between activities
                bundle.putParcelableArrayList("All Drawers", drawers);
                intent.putExtras(bundle);

                // All clothes list is shared between activities
                bundle.putParcelableArrayList("All Clothes", clothes);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.button_clothe_list:
                intent = new Intent(v.getContext(), ActivityClothesList.class);
                bundle.putParcelableArrayList("All Clothes", clothes);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.button_change_room:
                intent = new Intent(v.getContext(), ActivityChangeRoom.class);
                startActivity(intent);
                break;

            case R.id.button_events:
                intent = new Intent(v.getContext(), ActivityEvents.class);

                // All events list is shared between activities
                bundle.putParcelableArrayList("All Events", events);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.button_exit_app:
                confirmExit();
                break;

            default:
                break;
        }
    }

    private void defineVariables() {
        buttonDrawer = findViewById(R.id.button_drawers);
        buttonClotheList = findViewById(R.id.button_clothe_list);
        buttonChangeRoom = findViewById(R.id.button_change_room);
        buttonCombines = findViewById(R.id.button_events);
        buttonExitApp = findViewById(R.id.button_exit_app);

        getExtra();
    }

    private void getExtra(){
        drawers = new ArrayList<>();
        clothes = new ArrayList<>();
        events = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("All Drawers")) {
                drawers = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Drawers");
            }
            else{
                drawers = Drawer.getDrawers();
            }

            if (extras.containsKey("All Clothes")) {
                clothes = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Clothes");
            }
            else{
                clothes = Clothe.getClothes();
            }

            if(extras.containsKey("All Events")){
                events = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Events");
            }
            else{
                events = Event.getEvents();
            }
        }
        else{
            drawers = Drawer.getDrawers();
            clothes = Clothe.getClothes();
            events = Event.getEvents();
        }
    }

    public void defineListeners(){
        buttonDrawer.setOnClickListener(this);
        buttonClotheList.setOnClickListener(this);
        buttonChangeRoom.setOnClickListener(this);
        buttonCombines.setOnClickListener(this);
        buttonExitApp.setOnClickListener(this);
    }

    private void confirmExit() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.icon_alert);
        alert.setTitle("Exit");
        alert.setMessage("Are you sure you want to exit? Unsaved changes will be lost");
        alert.setPositiveButton("Yes", (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        alert.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.cancel());
        alert.show();
    }

    @Override
    public void onBackPressed() {
        confirmExit();
    }
}