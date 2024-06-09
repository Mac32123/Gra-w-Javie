package po.simulator;

public class Save {
    private int maxx;
    private int maxy;
    private Organizm[][] plansza;
    private String umiejetnosc;
    private boolean czyAktywna;
    private koordynaty polozenieCzlowieka;
    private int cooldown;

    public Organizm[][] getPlansza() {
        return plansza;
    }

    public int getMaxy() {
        return maxy;
    }

    public int getMaxx() {
        return maxx;
    }

    public int getCooldown() {
        return cooldown;
    }

    public koordynaty getPolozenieCzlowieka() {
        return polozenieCzlowieka;
    }

    public String getUmiejetnosc() {
        return umiejetnosc;
    }

    public boolean isCzyAktywna() {
        return czyAktywna;
    }

    public void setMaxy(int maxy) {
        this.maxy = maxy;
    }

    public void setMaxx(int maxx) {
        this.maxx = maxx;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setCzyAktywna(boolean czyAktywna) {
        this.czyAktywna = czyAktywna;
    }

    public void setPlansza(Organizm[][] plansza) {
        this.plansza = plansza;
    }

    public void setPolozenieCzlowieka(koordynaty polozenieCzlowieka) {
        this.polozenieCzlowieka = polozenieCzlowieka;
    }

    public void setUmiejetnosc(String umiejetnosc) {
        this.umiejetnosc = umiejetnosc;
    }
}

