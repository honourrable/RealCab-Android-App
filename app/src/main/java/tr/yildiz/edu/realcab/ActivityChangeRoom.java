package tr.yildiz.edu.realcab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ActivityChangeRoom extends AppCompatActivity implements View.OnClickListener {

    private ImageButton save;
    private ImageButton reset;
    private ImageButton head;
    private ImageButton face;
    private ImageButton upperBody;
    private ImageButton lowerBody;
    private ImageButton feet;
    private ImageButton temp;
    private static final int SELECT_IMAGE = 123;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_room);

        defineVariables();
        defineListeners();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.head:
                temp = head;
                fileChooser();
                break;

            case R.id.face:
                temp = face;
                fileChooser();
                break;

            case R.id.upper_body:
                temp = upperBody;
                fileChooser();
                break;

            case R.id.lower_body:
                temp = lowerBody;
                fileChooser();
                break;

            case R.id.feet:
                temp = feet;
                fileChooser();
                break;

            case R.id.button_finish:
                confirmStoragePermission(ActivityChangeRoom.this);
                takeScreenshot();
                break;

            case R.id.button_reset:
                resetImages();
                break;

            default:
                break;
        }
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            String dirPath = Environment.getExternalStorageDirectory().toString() + "/combines";
            File fileDir = new File(dirPath);

            if(!fileDir.exists()){
                boolean mkdir = fileDir.mkdirs();
            }

            String path = dirPath + "/" + "combine" + "-" + now + ".jpeg";

            View view = getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            // openScreenshot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void openScreenshot(File imageFile) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(imageFile);
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);
//    }

    public static void confirmStoragePermission(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void fileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Pick a photo"), SELECT_IMAGE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(ActivityChangeRoom.this, "No data in file!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Uri selectedImageURI = data.getData();
            assert selectedImageURI != null;
            File file = new File(selectedImageURI.getPath());
            String imagePath = file.getPath();
            temp.setImageURI(selectedImageURI);


            Toast.makeText(ActivityChangeRoom.this, "Image selected",
                    Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ActivityChangeRoom.this, "Could not open file!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void defineListeners() {
        head.setOnClickListener(this);
        face.setOnClickListener(this);
        upperBody.setOnClickListener(this);
        lowerBody.setOnClickListener(this);
        feet.setOnClickListener(this);
        save.setOnClickListener(this);
        reset.setOnClickListener(this);
    }

    private void defineVariables() {
        save = findViewById(R.id.button_finish);
        reset = findViewById(R.id.button_reset);
        head = findViewById(R.id.head);
        face = findViewById(R.id.face);
        upperBody = findViewById(R.id.upper_body);
        lowerBody = findViewById(R.id.lower_body);
        feet = findViewById(R.id.feet);

        resetImages();
    }

    private void resetImages(){
        head.setImageResource(R.drawable.icon_default_image);
        face.setImageResource(R.drawable.icon_default_image);
        upperBody.setImageResource(R.drawable.icon_default_image);
        lowerBody.setImageResource(R.drawable.icon_default_image);
        feet.setImageResource(R.drawable.icon_default_image);
    }
}