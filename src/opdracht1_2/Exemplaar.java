package opdracht1_2;

import java.time.LocalDate;
import java.util.ArrayList;

public class Exemplaar {
    private String omschrijving;
    private ArrayList<VerhuurRegel> verhuurRegels;
    private String details;

    public Exemplaar(String omschrijving, String details) {
        this.omschrijving = omschrijving;
        this.details = details;
    }

    public String geefOmschrijving() {
        return omschrijving;
    }

    public boolean isVerhuurd() {
        LocalDate vandaag = LocalDate.now();

        return verhuurRegels.stream()
                .anyMatch(v -> {
                    v.geefPeriode();

                    //Check of vandaag binnen periode valt met data van VerhuurTransactie
                    return true;
                });
    }

    public String geefDetails() {
        return details;
    }
}
