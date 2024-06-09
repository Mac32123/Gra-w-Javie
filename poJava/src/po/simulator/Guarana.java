package po.simulator;

import po.simulator.organizmy.Roslina;

import java.awt.*;

public class Guarana extends Roslina {
    public Guarana(int y, int x, Swiat swiat){
        polozenie = new koordynaty(y, x);
        this.swiat = swiat;
        sila = 0;
        inicjatywa = 0;
        nazwa = "Guarana";
    }

    public void komunikatDodajSile(Organizm org2) {
        String kom = swiat.getKomunikaty();
        kom += org2.getNazwa() + " zjadł " + this.getNazwa() + " i zwiększył swoją siłę do " + org2.getSila() + " ";
        swiat.setKomunikaty(kom);
    }

    @Override
    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        Organizm org = new Guarana(y, x, swiat);
        return org;
    }
    @Override
    public void przyjmijAtak(Organizm org2) {
        if (org2.getInicjatywa() > 0) {
            org2.dodajSile(3);
            komunikatDodajSile(org2);
        }
    }

    @Override
    public Color rysowanie() {
        return Color.blue;
    }
}
