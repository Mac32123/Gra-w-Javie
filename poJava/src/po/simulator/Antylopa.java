package po.simulator;

import po.simulator.organizmy.Zwierze;

import java.awt.*;

public class Antylopa extends Zwierze {

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Antylopa(y, x, swiat);
        return org;
    }

    public Antylopa(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 4;
        inicjatywa = 4;
        nazwa = "Antylopa";
    }
    @Override
    public Color rysowanie(){
        return Color.magenta;
    }
    @Override
    public void akcja() {
        polozeniePrev = new koordynaty(polozenie);
        int i = (int) (Math.random() * 5) - 2;
        int j = (int) (Math.random() * 5) - 2;
        while (polozenie.x + i < 0) i++;
        while (polozenie.y + j < 0) j++;
        while (polozenie.x +i > swiat.getMaxx() - 1) i--;
        while (polozenie.y + j > swiat.getMaxy() - 1) j--;
        polozenie.x = polozenie.x + i;
        polozenie.y = polozenie.y + j;
        Organizm org2 = swiat.getPlansza()[polozenie.y][polozenie.x];
        if (org2 != null && org2 != this) {
            kolizja(org2);
        }
        else {
            swiat.getPlansza()[polozeniePrev.y][polozeniePrev.x] = null;
            swiat.getPlansza()[polozenie.y][polozenie.x] = this;
        }
    }
    @Override
    public void przyjmijAtak(Organizm org2) {
        boolean Ucieczka = (int) (Math.random() * 2) == 0;
        if (Ucieczka) {
            Ucieczka = ucieczka(org2);
        }
        if (!Ucieczka) {
            komunikatSmierc(org2);
        }
    }
}
