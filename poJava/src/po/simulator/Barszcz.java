package po.simulator;

import po.simulator.organizmy.Roslina;

import java.awt.*;

public class Barszcz extends Roslina {
    public Barszcz(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 10;
        inicjatywa = 0;
        nazwa = "Barszcz";
    }

    public boolean doEliminacji(Organizm org2) {
        return org2.getInicjatywa() > 0;
    }

    @Override
    public void akcja(){
        zabijDookola();
        super.akcja();
    }

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Barszcz(y, x, swiat);
        return org;
    }
    @Override
    public void przyjmijAtak(Organizm org2) {
        if (org2.getInicjatywa() > 0) {
            swiat.getPlansza()[this.polozenie.y][this.polozenie.x] = null;
            komunikatDoubleKill(org2);
        }
    }

    @Override
    public int getSila() {
        return 0;
    }

    @Override
    public Color rysowanie() {
        return Color.CYAN;
    }
}
