package po.simulator;

import po.simulator.organizmy.Zwierze;

import java.awt.*;

public class Wilk extends Zwierze {

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Wilk(y, x, swiat);
        return org;
    }

    public Wilk(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 9;
        inicjatywa = 5;
        nazwa = "Wilk";
    }
    @Override
    public Color rysowanie(){
        return new Color(185, 122, 87);
    }

}
