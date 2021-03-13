package opdracht2_2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class BoekingsController implements Initializable {

    public ArrayList<String> airports = new ArrayList<>() {{
        add("Vaughan Airport");
        add( "Groundhog Mountain Airport");
        add( "Lower Granite State Airport");
        add( "Howell Airport");
        add( "Northern Lite Airport");
        add( "Hawks Run Airport");
        add( "Lazy J. Aerodrome");
        add( "Mountain View Regional Hospital Heliport");
        add( "L P Askew Farms Airport");
        add( "Purkeypile Airport");
        add( "Providence Seward Medical Center Heliport");
        add( "Ware Island Airport");
        add( "Community Hospital of De Queen Heliport");
        add( "Yat Heliport");
        add( "Grant Airport");
        add("Lugo Substation Heliport");
        add( "Swansboro Country Airport");
        add(  "Los Angeles County Sheriff's Department Heliport");
        add("St Vincent General Hospital Heliport");
        add( "Berlin Fairgrounds Heliport");
    }};
    private Random random = new Random();
    private ArrayList<Vlucht> vluchten = new ArrayList<>();

    @FXML
    TableView<Vlucht> tab;

    @FXML
    TableColumn<Object, Object> vln;
    @FXML
    TableColumn<Object, Object> vt;
    @FXML
    TableColumn<Object, Object> at;
    @FXML
    TableColumn<Object, Object> duur;

    @FXML
    ChoiceBox<String> cbvp;
    String cbvp_s = "";
    @FXML
    ChoiceBox<String> cbap;
    String cbap_s = "";

    @FXML
    DatePicker dpk;

    @FXML
    TextField vt_h;
    @FXML
    TextField vt_s;
    @FXML
    ChoiceBox<String> reisklasse;

    public BoekingsController() {
        for (var i = 0; i < 400; i++){
            generateRandomVlucht();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vln.setCellValueFactory(new PropertyValueFactory<>("vluchtnummer"));
        vt.setCellValueFactory(new PropertyValueFactory<>("vertrektijd"));
        at.setCellValueFactory(new PropertyValueFactory<>("aankomsttijd"));
        duur.setCellValueFactory(new PropertyValueFactory<>("duur"));

        tab.getItems().addAll(vluchten);

        cbvp.getItems().addAll(airports);
        cbvp.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cbvp_s = airports.get((Integer) newValue);
            doFilter();
        });

        cbap.getItems().addAll(airports);
        cbap.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cbap_s = airports.get((Integer) newValue);
            doFilter();
        });

        dpk.setValue(LocalDate.now());
        vt_h.setText(String.valueOf(LocalDateTime.now().getHour()));
        vt_s.setText(String.valueOf(LocalDateTime.now().getMinute()));
        dpk.valueProperty().addListener((ov, oldValue, newValue) -> { doFilter(); });
        vt_h.textProperty().addListener((ov, oldValue, newValue) -> { doFilter(); });
        vt_s.textProperty().addListener((ov, oldValue, newValue) -> { doFilter(); });
        reisklasse.getItems().addAll(Arrays.asList("ECONOMY", "BUSINESS", "FIRST"));
    }
    public void doFilter(){
        LocalDateTime vertrektijdF;
        try{
            vertrektijdF = dpk.getValue().atStartOfDay()
                    .plusHours(Long.parseLong(vt_h.getText()))
                    .plusMinutes(Long.parseLong(vt_s.getText()));
        }catch (Exception ignored){
            vertrektijdF = dpk.getValue().atStartOfDay();
        }
        LocalDateTime finalVertrektijdF = vertrektijdF;
        ArrayList<Vlucht> filtered = vluchten.stream()
                .filter(v -> ((v.getVertrekpunt().equals(cbvp_s) || cbvp_s.equals("")) &&
                        (v.getAankomstpunt().equals(cbap_s) || cbap_s.equals("")) &&
                        v.getVertrektijd().isAfter(finalVertrektijdF)))
                .collect(Collectors.toCollection(ArrayList::new));

        tab.getItems().clear();
        tab.getItems().addAll(filtered);

        tab.refresh();
    }

    public void generateRandomVlucht(){
        long dMod = ThreadLocalRandom.current().nextLong(-5, 5);
        Vlucht vlucht = new Vlucht(anyPunt(), anyPunt(), vluchten.size(), randTime(-1, dMod), randTime(1, dMod));
        vluchten.add(vlucht);
    }

    public String anyPunt()
    {
        return airports.get(random.nextInt(airports.size()));
    }

    public static LocalDateTime randTime(int mod, long dMod){
        long v = ThreadLocalRandom.current().nextLong(0, 10);
        LocalDateTime localDateTime = LocalDateTime.now().plusHours(v * mod);
        return localDateTime.plusDays(dMod);
    }

    public static class Vlucht {
        public String vertrekpunt;
        public String aankomstpunt;
        public int vluchtnummer;
        public LocalDateTime vertrektijd;
        public LocalDateTime aankomsttijd;

        public Vlucht(String vertrekpunt, String aankomstpunt, int vluchtnummer, LocalDateTime vertrektijd, LocalDateTime aankomsttijd) {
            this.vertrekpunt = vertrekpunt;
            this.aankomstpunt = aankomstpunt;
            this.vluchtnummer = vluchtnummer;
            this.vertrektijd = vertrektijd;
            this.aankomsttijd = aankomsttijd;
        }

        public String getDuur(){
            Duration duration = Duration.between(vertrektijd, aankomsttijd);
            return formatDuration(duration);
        }

        public static String formatDuration(Duration duration) {
            long seconds = duration.getSeconds();
            long absSeconds = Math.abs(seconds);
            String positive = String.format(
                    "%d:%02d:%02d",
                    absSeconds / 3600,
                    (absSeconds % 3600) / 60,
                    absSeconds % 60);
            return seconds < 0 ? "-" + positive : positive;
        }

        public int getVluchtnummer() {
            return vluchtnummer;
        }

        public LocalDateTime getVertrektijd() {
            return vertrektijd;
        }

        public LocalDateTime getAankomsttijd() {
            return aankomsttijd;
        }

        public String getVertrekpunt() {
            return vertrekpunt;
        }

        public String getAankomstpunt() {
            return aankomstpunt;
        }
    }
}
