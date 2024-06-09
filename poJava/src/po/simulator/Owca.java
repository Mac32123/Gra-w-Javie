package po.simulator;

import po.simulator.organizmy.Zwierze;

import java.awt.*;

public class Owca extends Zwierze {

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Owca(y, x, swiat);
        return org;
    }

    public Owca(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 4;
        inicjatywa = 4;
        nazwa = "Owca";
    }
    @Override
    public Color rysowanie(){
        return Color.GRAY;
    }

}
