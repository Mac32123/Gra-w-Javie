package po.simulator;

import po.simulator.organizmy.Zwierze;

import java.awt.*;
import java.util.ArrayList;

public class Lis extends Zwierze {

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Lis(y, x, swiat);
        return org;
    }

    public Lis(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 3;
        inicjatywa = 7;
        nazwa = "Lis";
    }
    @Override
    public Color rysowanie(){
        return Color.orange;
    }
    @Override
    public void akcja() {
        polozeniePrev = new koordynaty(polozenie);
        koordynaty[] pola = znajdzBezpiecznePole();
        int dlugosc = pola.length;
        if (dlugosc != 1) {
            int i = (int) (Math.random() * dlugosc);
            polozenie = pola[i];
        }
        Organizm org2 = swiat.getPlansza()[polozenie.y][polozenie.x];
        if (org2 != null && org2 != this) {
            kolizja(org2);
        }
        else {
            swiat.getPlansza()[polozeniePrev.y][polozeniePrev.x] = null;
            swiat.getPlansza()[polozenie.y][polozenie.x] = this;
        }
    }
    koordynaty[] znajdzBezpiecznePole() {
        ArrayList<koordynaty> wolne = new ArrayList<koordynaty>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (polozenie.x + i >= 0 && polozenie.x + i < swiat.getMaxx() && polozenie.y + j >= 0 && polozenie.y + j < swiat.getMaxy()) {
                    Organizm org = swiat.getPlansza()[polozenie.y + j][polozenie.x + i];
                    if (org != null) {
                        if (org.getSila() <= this.sila) {
                            wolne.add(new koordynaty(polozenie.y + j, polozenie.x + i));
                        }
                    }
                    else {
                        wolne.add(new koordynaty(polozenie.y + j, polozenie.x + i));
                    }
                }
            }
        }
        koordynaty[] wolneArr = new koordynaty[wolne.size()];
        wolneArr = wolne.toArray(wolneArr);
        return wolneArr;
    }
}
