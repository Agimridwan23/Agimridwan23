// File: Pemesanan.java
public class Pemesanan {
    private String kode;
    private int harga;
    private String jam;
    private String nomorKursi;

    // Constructor
    public Pemesanan(String kode, int harga, String jam, String nomorKursi) {
        this.kode = kode;
        this.harga = harga;
        this.jam = jam;
        this.nomorKursi = nomorKursi;
    }

    // Getter methods
    public String getKode() {
        return kode;
    }

    public int getHarga() {
        return harga;
    }

    public String getJam() {
        return jam;
    }

    public String getNomorKursi() {
        return nomorKursi;
    }
}
