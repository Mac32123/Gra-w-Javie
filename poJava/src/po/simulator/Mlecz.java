package po.simulator;

import po.simulator.organizmy.Roslina;

import java.awt.*;

public class Mlecz extends Roslina {
    public Mlecz(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 0;
        inicjatywa = 0;
        nazwa = "Mlecz";
    }

    @Override
    public void akcja(){
        for(int i =0; i < 3; i++){
            super.akcja();
        }
    }

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Mlecz(y, x, swiat);
        return org;
    }

    @Override
    public Color rysowanie() {
        return Color.YELLOW;
    }
}
