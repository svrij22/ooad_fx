package opdracht1_2;

import java.time.Period;

public class VerhuurRegel {
    /*
    private LocalDate startDatum = LocalDate.of(2015, 2, 20);
    private LocalDate eindDatum = LocalDate.of(2017, 1, 15);
    */

    private Period verhuurPeriode;

    public VerhuurRegel(Period verhuurPeriode) {
        /*
        this.startDatum = LocalDate.of(2015, 2, 20);
        this.eindDatum = LocalDate.of(2017, 1, 15);
        */
        this.verhuurPeriode = verhuurPeriode;
    }

    //Kan geefPeriode niet goed schrijven zonder VerhuurTransactie
    public Period geefPeriode() {
        return verhuurPeriode;
    }
}
