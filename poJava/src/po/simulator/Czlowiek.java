package po.simulator;

import po.simulator.organizmy.Zwierze;

import java.awt.*;

public class Czlowiek extends Zwierze {

    private char kierunek;
    private String umiejetnosc;
    private boolean czyAktywna;
    private boolean czyDziala;
    private int licznikUmiejetnosci;

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        //czlowiek nie może się rozmnażać
        return null;
    }

    public Czlowiek(int y, int x, int index, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 5;
        inicjatywa = 4;
        int umj = index % 5;
        switch (umj) {
            case 0 -> umiejetnosc = "Niesmiertelnosc";
            case 1 -> umiejetnosc = "Eliksir";
            case 2 -> umiejetnosc = "Szybkosc";
            case 3 -> umiejetnosc = "Tarcza Alzura";
            case 4 -> umiejetnosc = "Calopalenie";
        }
        czyAktywna = false;
        this.swiat.setUmjejetnosc(umiejetnosc + ": nieaktywna");
        nazwa = "Czlowiek";
    }
    @Override
    public Color rysowanie(){
        return Color.BLACK;
    }

    public void setKierunek(char kier) {
        this.kierunek = kier;
    }

    public char getKierunek() {
        return kierunek;
    }

    public void setUmiejetnosc(String umiejetnosc) {
        this.umiejetnosc = umiejetnosc;
    }

    public void setCzyAktywna(boolean czyAktywna) {
        this.czyAktywna = czyAktywna;
    }

    public void setLicznikUmiejetnosci(int licznikUmiejetnosci) {
        this.licznikUmiejetnosci = licznikUmiejetnosci;
    }

    @Override
    public void akcja() {
        polozeniePrev = new koordynaty(polozenie);
        if (umiejetnosc == "Szybkosc") czyDziala = licznikUmiejetnosci >= 3 || ((int) (Math.random() * 2) == 0);
        int i = kierunek == 'L' ? -1 : (kierunek == 'P' ? 1 : 0);
        int j = kierunek == 'D' ? 1 : (kierunek == 'G' ? -1 : 0);
        if (umiejetnosc == "Szybkosc" && czyAktywna && czyDziala) {
            i *= 2;
            j *= 2;
        }
        while (polozenie.x + i < 0) i++;
        while (polozenie.y + j < 0) j++;
        while (polozenie.x + i > swiat.getMaxx() - 1) i--;
        while (polozenie.y + j > swiat.getMaxy() - 1) j--;
        polozenie.x = polozenie.x + i;
        polozenie.y = polozenie.y + j;
        Organizm org2 = swiat.getPlansza()[polozenie.y][polozenie.x];
        if (umiejetnosc == "Eliksir" && czyAktywna) {
            sila--;
        }
        if (org2 != null && org2 != this) {
            kolizja(org2);
        }
        else {
            swiat.getPlansza()[polozeniePrev.y][polozeniePrev.x] = null;
            swiat.getPlansza()[polozenie.y][polozenie.x] = this;
        }
        if (umiejetnosc == "Calopalenie" && czyAktywna) zabijDookola();
        if (licznikUmiejetnosci >= 0) licznikUmiejetnosci--;
        if (licznikUmiejetnosci == 0 && czyAktywna) {
            czyAktywna = false;
            licznikUmiejetnosci = 5;
        }
        if (czyAktywna) swiat.setUmjejetnosc(umiejetnosc + ": aktywna ");
        else swiat.setUmjejetnosc(umiejetnosc + ": nieaktywna");
    }

    @Override
    public boolean odbilAtak(Organizm org2) {
        if (umiejetnosc.equals("Tarcza Alzura") && czyAktywna) return true;
        else return false;
    }
    @Override
    public void przyjmijAtak(Organizm org2) {
        if (umiejetnosc == "Niesmiertelnosc" && czyAktywna) {
            boolean Ucieczka = ucieczka(org2);
            if (!Ucieczka) {
                swiat.getPlansza()[polozenie.y][polozenie.x] = this;
                org2.komunikatSmierc(this);
            }
        } else {
            komunikatSmierc(org2);
            swiat.setCzlowiekZyje(false);
        }
    }

    public int getLicznikUmiejetnosci() {
        return licznikUmiejetnosci;
    }

    public boolean isCzyAktywna() {
        return czyAktywna;
    }

    public void wywolajUmiejetnosc() {
        if (licznikUmiejetnosci > 0) {
            if (czyAktywna) swiat.setUmjejetnosc(umiejetnosc + ": nie możesz w tej chwili aktywować umiejętności, ponieważ jest już aktywna");
            else swiat.setUmjejetnosc(umiejetnosc + ": aktywna");
        }
        else {
            czyAktywna = true;
            licznikUmiejetnosci = 5;
            swiat.setUmjejetnosc(umiejetnosc + ": aktywna ");
            if (umiejetnosc == "Eliksir") sila += 5;
            if (umiejetnosc == "Calopalenie") zabijDookola();
        }
        swiat.rysuj();
    }

}
