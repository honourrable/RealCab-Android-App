package tr.yildiz.edu.realcab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ActivityEventEdit extends AppCompatActivity {

    private EventEditAdapter adapter;
    private ImageButton eventAddClothe;
    private ImageButton saveInfo;
    private ImageButton resetInfo;
    private EditText eventName;
    private EditText eventType;
    private EditText eventDate;
    private EditText eventLocation;
    private int eventNo;
    private ArrayList<Event> events;
    private final ArrayList<EditText> editTexts = new ArrayList<>();
    private static final int SELECT_PHOTO = 123456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        defineVariables();
        defineListeners();
    }

    private void defineListeners() {

        eventAddClothe.setOnClickListener(v -> {
            events.get(eventNo).getEventClothes().add(new Clothe());

            Intent intent = new Intent(v.getContext(), ActivityEvents.class);
            sendExtra(intent);
            Toast.makeText(ActivityEventEdit.this, "New clothe added", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        });

        saveInfo.setOnClickListener(view -> {
            if(isFilled()) {
                events.get(eventNo).setEventName(eventName.getText().toString());
                events.get(eventNo).setEventType(eventType.getText().toString());
                events.get(eventNo).setEventDate(eventDate.getText().toString());
                events.get(eventNo).setEventLocation(eventLocation.getText().toString());

                Intent intent = new Intent(view.getContext(), ActivityEvents.class);
                sendExtra(intent);
                Toast.makeText(ActivityEventEdit.this, "Information saved", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
            else{
                Toast.makeText(ActivityEventEdit.this, "Please enter all required information!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        resetInfo.setOnClickListener(view -> cleanEditText());
    }

    private void defineVariables() {

        eventAddClothe = findViewById(R.id.event_add_clothe);
        saveInfo = findViewById(R.id.event_update);
        resetInfo = findViewById(R.id.event_reset);
        eventName = findViewById(R.id.edit_event_name);
        eventType = findViewById(R.id.edit_event_type);
        eventDate = findViewById(R.id.edit_event_date);
        eventLocation = findViewById(R.id.edit_event_location);
        editTexts.add(eventName);
        editTexts.add(eventType);
        editTexts.add(eventDate);
        editTexts.add(eventLocation);

        RecyclerView recyclerViewList = findViewById(R.id.recyclerViewEditEvents);
        GridLayoutManager layoutManager  = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewList.setLayoutManager(layoutManager);

        getExtra();
        fill();

        adapter = new EventEditAdapter(this, events, eventNo);
        recyclerViewList.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(ActivityEventEdit.this, "No data in file!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Uri selectedImageURI = data.getData();
            assert selectedImageURI != null;
            File file = new File(selectedImageURI.getPath());
            String imagePath = file.getPath();

            events.get(eventNo).getEventClothes().get(adapter.getCurrentPosition()).setImagePath(imagePath);
            adapter.setCurrentImage(selectedImageURI);

            Toast.makeText(ActivityEventEdit.this, "Image selected",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ActivityEventEdit.this, "Could not open file!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void getExtra(){
        events = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null){
            if (extras.containsKey("All Events")) {
                events = Objects.requireNonNull(this.getIntent().getExtras()).getParcelableArrayList("All Events");
            }
            else{
                events = Event.getEvents();
            }
        }

        if(intent.hasExtra("Event No")){
            eventNo = Objects.requireNonNull(intent.getExtras()).getInt("Event No", 0);
        }
        else{
            eventNo = 0;
        }
    }

    private void sendExtra(Intent intent){
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("All Events", events);
        intent.putExtras(bundle);
    }

    private boolean isFilled(){
        String str;
        for(EditText i : editTexts){
            str = i.getText().toString();
            if(TextUtils.isEmpty(str)){
                return false;
            }
        }
        return true;
    }

    private void cleanEditText(){
        for(EditText i : editTexts){
            i.setText("");
        }
    }

    private void fill(){
        eventName.setText(events.get(eventNo).getEventName());
        eventType.setText(events.get(eventNo).getEventType());
        eventDate.setText(events.get(eventNo).getEventDate());
        eventLocation.setText(events.get(eventNo).getEventLocation());
    }
}