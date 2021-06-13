package tr.yildiz.edu.realcab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Objects;

public class ActivityEvents extends AppCompatActivity {

    private ImageButton createEvent;
    private ArrayList<Event> events;
    private final ArrayList<EditText> editTexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        defineVariables();
        defineListeners();
    }

    private void defineListeners() {

        createEvent.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText editTextName = new EditText(this);
            editTextName.setHint("Name");
            final EditText editTextType = new EditText(this);
            editTextType.setHint("Type");
            final EditText editTextDate = new EditText(this);
            editTextDate.setHint("Date");
            final EditText editTextLocation = new EditText(this);
            editTextLocation.setHint("Location");
            alert.setMessage("Enter Event Information");
            alert.setTitle("Create Event");

            layout.addView(editTextName);
            layout.addView(editTextType);
            layout.addView(editTextDate);
            layout.addView(editTextLocation);
            alert.setView(layout);

            editTexts.add(editTextName);
            editTexts.add(editTextType);
            editTexts.add(editTextDate);
            editTexts.add(editTextLocation);

            alert.setPositiveButton("Okay", (dialog, whichButton) -> {
                if(isFilled()){
                    String eventName = editTextName.getText().toString();
                    String eventType = editTextType.getText().toString();
                    String eventDate = editTextDate.getText().toString();
                    String eventLocation = editTextLocation.getText().toString();

                    Event newEvent = new Event(eventName, eventType, eventDate, eventLocation);
                    events.add(newEvent);

                    Intent intent = new Intent(view.getContext(), ActivityMain.class);
                    sendExtra(intent);
                    Toast.makeText(ActivityEvents.this, "New event added", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ActivityEvents.this, "Please enter all required information!",
                            Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            alert.show();
        });
    }

    private void defineVariables() {
        createEvent = findViewById(R.id.add_event);

        RecyclerView recyclerViewList = findViewById(R.id.recyclerViewEvents);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewList.setLayoutManager(layoutManager);

        getExtra();

        EventAdapter adapter = new EventAdapter(this, events);
        recyclerViewList.setAdapter(adapter);
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
    }

    private void sendExtra(Intent intent) {
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityEvents.this, ActivityMain.class);
        sendExtra(intent);
        startActivity(intent);
    }
}