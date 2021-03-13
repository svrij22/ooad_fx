package opdracht1_2;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class VerhuurProduct {

    private static ArrayList<VerhuurProduct> verhuurProducten = new ArrayList<>();
    private ArrayList<Exemplaar> exemplaren = new ArrayList<>();

    public VerhuurProduct() {
        verhuurProducten.add(this);
    }

    public static ArrayList<VerhuurProduct> geefAlle(){
        return verhuurProducten;
    }

    public void voegExemplaarToe(Exemplaar e){
        this.exemplaren.add(e);
    }

    public ArrayList<Exemplaar> geefExemplaren(){
        return this.exemplaren;
    }


    public ArrayList<Exemplaar> geefBeschikbareExemplaren(){
        return geefExemplaren()
                .stream()
                .filter(p -> !p.isVerhuurd())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
