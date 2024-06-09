package po.simulator;

import po.simulator.organizmy.Zwierze;

import java.awt.*;

public class Zolw extends Zwierze {

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Zolw(y, x, swiat);
        return org;
    }

    public Zolw(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 2;
        inicjatywa = 1;
        nazwa = "Zolw";
    }
    @Override
    public Color rysowanie(){
        return Color.PINK;
    }
    @Override
    public void akcja() {
        boolean czyRuszy = (int) (Math.random() * 4 ) == 0;
        if (czyRuszy) super.akcja();
    }

    @Override
    public boolean odbilAtak(Organizm org2) {
        if (org2.getInicjatywa() > 0) return (org2.getSila() < 5);
        else return false;
    }
}
