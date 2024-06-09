package po.simulator;

import po.simulator.organizmy.Roslina;

import java.awt.*;

public class Trawa extends Roslina {
    public Trawa(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 0;
        inicjatywa = 0;
        nazwa = "Trawa";
    }
    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Trawa(y, x, swiat);
        return org;
    }

    @Override
    public Color rysowanie() {
        return Color.green;
    }
}
