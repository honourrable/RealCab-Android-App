package tr.yildiz.edu.realcab;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Drawer implements Parcelable {

    private final int number;
    private final ArrayList<Clothe> clothes;

    public Drawer(int number, ArrayList<Clothe> clothes) {
        this.number = number;
        this.clothes = clothes;
    }

    public static ArrayList<Drawer> getDrawers() {

        ArrayList<Drawer> drawers = new ArrayList<>();
        drawers.add(new Drawer(1, new ArrayList<>()));
        drawers.add(new Drawer(2, new ArrayList<>()));
        drawers.add(new Drawer(3, new ArrayList<>()));
        drawers.add(new Drawer(4, new ArrayList<>()));
        drawers.add(new Drawer(5, new ArrayList<>()));

        return drawers;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<Clothe> getClothes() {
        return clothes;
    }

    protected Drawer(Parcel in) {
        number = in.readInt();
        clothes = in.createTypedArrayList(Clothe.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeTypedList(clothes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Drawer> CREATOR = new Creator<Drawer>() {
        @Override
        public Drawer createFromParcel(Parcel in) {
            return new Drawer(in);
        }

        @Override
        public Drawer[] newArray(int size) {
            return new Drawer[size];
        }
    };
}
