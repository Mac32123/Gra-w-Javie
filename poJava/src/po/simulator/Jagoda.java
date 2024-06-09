package po.simulator;

import po.simulator.organizmy.Roslina;

import java.awt.*;

public class Jagoda extends Roslina {
    public Jagoda(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 99;
        inicjatywa = 0;
        nazwa = "Jagoda";
    }

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Jagoda(y, x, swiat);
        return org;
    }
    @Override
    public void przyjmijAtak(Organizm org2) {
        if (org2.getInicjatywa() > 0) {
            swiat.getPlansza()[this.polozenie.y][this.polozenie.x] = null;
            if(org2.nazwa == "czlowiek") swiat.setCzlowiekZyje(false);
            komunikatDoubleKill(org2);
        }
    }

    @Override
    public int getSila() {
        return 0;
    }

    @Override
    public Color rysowanie() {
        return Color.RED;
    }
}
