import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;

import java.util.*;

public class TicketApp extends Application {
    private Map<String, Set<String>> bookedSeatsInfo = new HashMap<>();

    private TextField namaField;
    private TextField nomorField;
    private ChoiceBox<String> filmChoiceBox;
    private ChoiceBox<String> jamChoiceBox;
    private TextField jumlahTiketField;
    private TextField nomorKursiField;
    private TextArea invoiceArea;
    private GridPane kursiGrid;
    private Set<String> selectedSeats;
    private Set<String> bookedSeats;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Pemesanan Tiket Bioskop");

        namaField = new TextField();
        nomorField = new TextField();
        filmChoiceBox = new ChoiceBox<>();
        jamChoiceBox = new ChoiceBox<>();
        jumlahTiketField = new TextField();
        nomorKursiField = new TextField();
        invoiceArea = new TextArea();
        Button pesanButton = new Button("Pesan Tiket");

        filmChoiceBox.getItems().addAll("172 Days", "Wonka", "Panggonan Wingit", "Siksa Neraka", "Jatuh Cinta Seperti di Film-film");
        jamChoiceBox.getItems().addAll("12.00", "13.40", "14.50", "19.20", "20.15");

        kursiGrid = new GridPane();
        kursiGrid.setHgap(5);
        kursiGrid.setVgap(5);
        selectedSeats = new HashSet<>();
        bookedSeats = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                String kursiLabel = (char) ('A' + i) + Integer.toString(j + 1);
                ToggleButton kursiButton = new ToggleButton(kursiLabel);
                kursiButton.setOnAction(e -> handleSeatSelection(kursiButton, kursiLabel));
                kursiGrid.add(kursiButton, j, i);
            }
        }

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(
                new Label("Nama: "), namaField,
                new Label("No. Telepon: "), nomorField,
                new Label("Pilih Film: "), filmChoiceBox,
                new Label("Pilih Jam: "), jamChoiceBox,
                new Label("Jumlah Tiket: "), jumlahTiketField,
                new Label("Nomor Kursi: "), nomorKursiField,
                kursiGrid, pesanButton,
                new Label("Invoice: "), invoiceArea
        );

        pesanButton.setOnAction(e -> generateInvoice());

        Scene scene = new Scene(layout, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void handleSeatSelection(ToggleButton seatButton, String seatLabel) {
        int jumlahTiketInt = Integer.parseInt(jumlahTiketField.getText());
        String film = filmChoiceBox.getValue();
        String jam = jamChoiceBox.getValue();

        // Cek apakah kursi sudah dipilih pada pemesanan sebelumnya
        if (isSeatBooked(film, jam, seatLabel)) {
            seatButton.setSelected(false);
            return;
        }

        // Cek apakah jumlah kursi yang dipilih tidak melebihi jumlah tiket yang dibeli
        if (seatButton.isSelected() && selectedSeats.size() < jumlahTiketInt) {
            selectedSeats.add(seatLabel);
        } else {
            seatButton.setSelected(false);  // Tidak membiarkan pemilihan kursi lebih dari jumlah tiket
        }

        // Update nomorKursiField dengan daftar kursi yang dipilih
        nomorKursiField.setText(String.join(", ", selectedSeats));
    }

    private boolean isSeatBooked(String film, String jam, String seatLabel) {
        // Mengecek apakah kursi sudah dipesan untuk film dan jam tertentu
        String filmJamKey = film + jam;
        return bookedSeatsInfo.containsKey(filmJamKey) && bookedSeatsInfo.get(filmJamKey).contains(seatLabel);
    }

    private void generateInvoice() {
        String film = filmChoiceBox.getValue();
        String jam = jamChoiceBox.getValue();
        String nama = namaField.getText();
        String nomor = nomorField.getText();
        String nomorKursi = nomorKursiField.getText();
        String jumlahTiket = jumlahTiketField.getText();

        double hargaPerTiket = 50.000;
        int jumlahTiketInt = Integer.parseInt(jumlahTiket);
        double totalHarga = jumlahTiketInt * hargaPerTiket;

        String separator = "------------------------------------------";
        String invoiceText = "===== INVOICE PEMESANAN TIKET =====\n" +
                "Nama Pelanggan\t\t: " + nama + "\n" +
                "No. Telp\t\t\t\t: " + nomor + "\n" +
                separator + "\n" +
                "Film\t\t\t\t\t: " + film + "\n" +
                "Jam\t\t\t\t\t: " + jam + "\n" +
                "Nomor Kursi\t\t\t: " + nomorKursi + "\n" +
                "Jumlah Tiket\t\t\t: " + jumlahTiket + "\n" +
                separator + "\n" +
                "Harga tiket (per tiket)\t: Rp. " + hargaPerTiket + "\n" +
                "Total Harga\t\t\t: Rp. " + totalHarga + "\n" +
                "Status Pembayaran\t\t: Sukses\n" +
                "Terima kasih telah menggunakan layanan kami!\n" +
                "================================================";
        invoiceArea.setText(invoiceText);

        System.out.println("===== INVOICE PEMESANAN TIKET =====");
        System.out.println("Nama Pelanggan\t\t: " + nama);
        System.out.println("No. Telp\t\t: " + nomor);
        System.out.println(separator);
        System.out.println("Film\t\t\t: " + film);
        System.out.println("Jam\t\t\t: " + jam);
        System.out.println("Nomor Kursi\t\t: " + nomorKursi);
        System.out.println("Jumlah Tiket\t\t: " + jumlahTiket);
        System.out.println(separator);
        System.out.println("Harga tiket (per tiket)\t: Rp. " + hargaPerTiket);
        System.out.println("Total Harga\t\t: Rp. " + totalHarga);
        System.out.println("Status Pembayaran\t: Sukses");
        System.out.println("Terima kasih telah menggunakan layanan kami!");
        System.out.println("================================================");

        // Menambahkan kursi yang dipilih pada pemesanan sebelumnya ke bookedSeats
        String filmJamKey = film + jam;
        bookedSeatsInfo.computeIfAbsent(filmJamKey, k -> new HashSet<>()).addAll(selectedSeats);

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Konfirmasi");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Ingin memesan tiket lagi?");

        ButtonType buttonTypeYes = new ButtonType("Ya");
        ButtonType buttonTypeNo = new ButtonType("Tidak", ButtonData.CANCEL_CLOSE);
        confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                resetInputFields();
            } else {
                System.exit(0);
            }
        });
    }

    private void resetInputFields() {
        namaField.clear();
        nomorField.clear();
        filmChoiceBox.setValue(null);
        jamChoiceBox.setValue(null);
        jumlahTiketField.clear();
        nomorKursiField.clear();
        invoiceArea.clear();

        selectedSeats.clear();
        for (Node node : kursiGrid.getChildren()) {
            if (node instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) node;
                toggleButton.setSelected(false);

                String kursiLabel = toggleButton.getText();
                if (isSeatBooked(filmChoiceBox.getValue(), jamChoiceBox.getValue(), kursiLabel)) {
                    toggleButton.setDisable(true);
                } else {
                    toggleButton.setDisable(false);
                }
            }
        }
    }
}
