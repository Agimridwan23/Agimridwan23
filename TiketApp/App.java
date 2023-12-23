import java.util.Scanner;
import java.util.HashSet;

public class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean lanjutPembelian;

        String[] filmNames = {"172 Days", "Wonka", "Panggonan Wingit", "Siksa Neraka", "Jatuh Cinta Seperti di Film-film"};
        int[] filmPrices = {45000, 40000, 38000, 42000, 40000};
        String[] jamTayang = {"12.00", "13.40", "14.50", "19.20", "20.15"};

        // Menyimpan kursi yang sudah dipesan
        HashSet<String> kursiDipesan = new HashSet<>();

        do {
            System.out.println("********** PEMESANAN TIKET BIOSKOP **********");
            System.out.println("Pilih menu:");
            System.out.println("1. Daftar Film");
            System.out.println("2. Pesan Tiket");

            // Input menu choice
            System.out.print("Masukkan pilihan (1/2): ");
            int menuChoice = input.nextInt();
            input.nextLine();  // Consume the newline character

            switch (menuChoice) {
                case 1:
                    // Menampilkan daftar film
                    System.out.println(">>> List Nama Film <<<");
                    for (int i = 0; i < filmNames.length; i++) {
                        System.out.println((i + 1) + ". " + filmNames[i]);
                    }
                    break;
                case 2:
                    // Menjalankan program pembelian tiket
                    beliTiket(input, filmNames, filmPrices, jamTayang, kursiDipesan);
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }

            // Menanyakan apakah ingin melanjutkan pembelian tiket
            System.out.print("Apakah ingin melanjutkan? (y/n): ");
            char lanjutPilihanChar = input.next().charAt(0);
            lanjutPembelian = (lanjutPilihanChar == 'y' || lanjutPilihanChar == 'Y');

        } while (lanjutPembelian);

        input.close();
    }

    private static void beliTiket(Scanner input, String[] filmNames, int[] filmPrices, String[] jamTayang, HashSet<String> kursiDipesan) {
        System.out.print("Masukkan nama: ");
        String nama = input.next();
        System.out.print("Masukkan no. Telepon: ");
        String nomor = input.next();

        Pelanggan pelanggan = new Pelanggan(nama, nomor);

        System.out.println("------------------------------------------");
        System.out.println(">>> List Nama Film <<<");

        // Menampilkan daftar film
        for (int i = 0; i < filmNames.length; i++) {
            System.out.println((i + 1) + ". " + filmNames[i]);
        }

        // Memilih film
        System.out.print("-> Masukkan pilihan film (1-" + filmNames.length + "): ");
        int n = input.nextInt();
        input.nextLine();

        if (n < 1 || n > filmNames.length) {
            System.out.println("Pilihan tidak valid.");
            input.close();
            return;
        }

        String kode = filmNames[n - 1];
        int harga = filmPrices[n - 1];

        System.out.println("------------------------------------------");
        System.out.println(">>> Pilih Jam Tayang <<<");

        // Menampilkan jam tayang
        for (int i = 0; i < jamTayang.length; i++) {
            System.out.println((i + 1) + ". " + jamTayang[i]);
        }

        // Memilih jam tayang
        System.out.print("-> Masukkan pilihan jam (1-" + jamTayang.length + "): ");
        int i = input.nextInt();
        input.nextLine();

        if (i < 1 || i > jamTayang.length) {
            System.out.println("Pilihan tidak valid.");
            input.close();
            return;
        }

        String jam = jamTayang[i - 1];

        // Input jumlah tiket
        System.out.print("Masukkan jumlah tiket yang ingin dibeli: ");
        int jumlahTiket = input.nextInt();
        input.nextLine();

        if (jumlahTiket <= 0) {
            System.out.println("Jumlah tiket tidak valid.");
            input.close();
            return;
        }

        // Memilih nomor kursi
        String nomorKursi;
        do {
            System.out.println(">>> Pilih Nomor Kursi <<<");
            System.out.println(" A1 A2 A3 A4 A5 ");
            System.out.println(" B1 B2 B3 B4 B5 ");
            System.out.println(" C1 C2 C3 C4 C5 ");
            System.out.print("-> Masukkan nomor urut kursi: ");
            nomorKursi = input.nextLine();

            if (kursiDipesan.contains(nomorKursi)) {
                System.out.println("Kursi " + nomorKursi + " sudah dipesan. Pilih kursi lain.");
            }

        } while (kursiDipesan.contains(nomorKursi));

        // Menandai kursi sebagai sudah dipesan
        kursiDipesan.add(nomorKursi);

        // Calculate totalHarga
        int totalHarga = harga * jumlahTiket;

        // Menanyakan apakah ingin melanjutkan pembelian tiket
        System.out.print("Apakah ingin melanjutkan pembelian tiket? (y/n): ");
        char lanjutPembelianChar = input.next().charAt(0);
        boolean lanjutPembelian = (lanjutPembelianChar == 'y' || lanjutPembelianChar == 'Y');

        // Menampilkan invoice
        tampilkanInvoice(pelanggan, kode, jam, nomorKursi, jumlahTiket, harga, totalHarga);
    }

    // Metode untuk menampilkan invoice
    private static void tampilkanInvoice(Pelanggan pelanggan, String kode, String jam, String nomorKursi,
                                         int jumlahTiket, int harga, int totalHarga) {
        System.out.println("\n\n===== INVOICE PEMESANAN TIKET =====");
        System.out.println("Nama Pelanggan\t\t: " + pelanggan.getNama());
        System.out.println("No. Telp\t\t: " + pelanggan.getNomor());
        System.out.println("------------------------------------------");
        System.out.println("Kode film\t\t: " + kode);
        System.out.println("Jam tayang\t\t: " + jam + " WIB");
        System.out.println("Nomor Kursi\t\t: " + nomorKursi);
        System.out.println("Jumlah Tiket\t\t: " + jumlahTiket);
        System.out.println("Harga tiket (per tiket)\t: Rp. " + harga);
        System.out.println("Total Harga\t\t: Rp. " + totalHarga);
        System.out.println("Status Pembayaran\t: Sukses");
        System.out.println("Terima kasih telah menggunakan layanan kami!");
        System.out.println("================================================");
    }
}

