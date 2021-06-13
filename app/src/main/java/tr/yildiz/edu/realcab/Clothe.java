package tr.yildiz.edu.realcab;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Clothe implements Parcelable {

    private String type;
    private String colour;
    private String pattern;
    private String purchaseDate;
    private String price;
    private String imagePath;

    public Clothe(){}

    public Clothe(String type, String colour, String pattern, String purchaseDate, String price, String imagePath) {
        this.type = type;
        this.colour = colour;
        this.pattern = pattern;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.imagePath = imagePath;
    }

    protected Clothe(Parcel in) {
        type = in.readString();
        colour = in.readString();
        pattern = in.readString();
        purchaseDate = in.readString();
        price = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<Clothe> CREATOR = new Creator<Clothe>() {
        @Override
        public Clothe createFromParcel(Parcel in) {
            return new Clothe(in);
        }

        @Override
        public Clothe[] newArray(int size) {
            return new Clothe[size];
        }
    };

    public static ArrayList<Clothe> getClothes() {

        ArrayList<Clothe> clothes = new ArrayList<>();
        clothes.add(new Clothe("T-Shirt", "White", "Plain", "20/02/2021",
                "30$", Integer.toString(R.drawable.tshirt_white)));
        clothes.add(new Clothe("Tie", "Red", "Lined", "22/02/2021",
                "28$", Integer.toString(R.drawable.tie_red)));
        clothes.add(new Clothe("Sunglass", "Blue", "Avigator", "25/02/2021",
                "28$", Integer.toString(R.drawable.sunglasses_blue)));
        clothes.add(new Clothe("Suit", "Black", "Plain", "02/03/2021",
                "28$", Integer.toString(R.drawable.suit_black)));
        clothes.add(new Clothe("Shoes", "Brown", "Plain", "05/03/2021",
                "28$", Integer.toString(R.drawable.shoes_brown)));
        clothes.add(new Clothe("Hat", "Grey", "Plain", "08/03/2021",
                "28$", Integer.toString(R.drawable.hat_grey)));
        clothes.add(new Clothe("Backpack", "Green", "Plain", "10/03/2021",
                "28$", Integer.toString(R.drawable.backpack_green)));

        return clothes;
    }

    public String getType() {return type;}
    public String getColour() {return colour;}
    public String getPattern() {return pattern;}
    public String getPurchaseDate() {return purchaseDate;}
    public String getPrice() {return price;}
    public String getImagePath() {return imagePath;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(colour);
        parcel.writeString(pattern);
        parcel.writeString(purchaseDate);
        parcel.writeString(price);
        parcel.writeString(imagePath);
    }
}
