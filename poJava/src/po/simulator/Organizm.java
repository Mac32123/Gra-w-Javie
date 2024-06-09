package po.simulator;

import java.awt.*;
import java.util.ArrayList;

public abstract class Organizm {
    protected String nazwa;
    protected  int sila;
    protected int inicjatywa;
    protected koordynaty polozenie;
    protected Swiat swiat;
    protected boolean doEliminacji(Organizm org2){
        return org2 != this;
    }

    public boolean czyWykonalAkcje;

    public abstract void akcja();
    public abstract void kolizja(Organizm org2);
    public abstract Color rysowanie();

    public String getNazwa() {
        return nazwa;
    }

    public int getSila(){
        return sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public koordynaty getPolozenie() {
        return polozenie;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void przyjmijAtak(Organizm org2){
        String kom = swiat.getKomunikaty();
        kom += org2.getNazwa() +" zabił " + this.getNazwa() + " ";
        swiat.setKomunikaty(kom);
    }

    public void dodajSile(int ile){
        sila += ile;
    }

    public final void kill(Organizm org2){
        swiat.getPlansza()[polozenie.y][polozenie.x] = null;
        if(this.getNazwa() == "Czlowiek") swiat.setCzlowiekZyje(false);
        komunikatZatrucie(org2);
    }

     public void komunikatSmierc(Organizm org2){
        String kom = swiat.getKomunikaty() + org2.getNazwa() + " zabil " + this.getNazwa() + " ";
        swiat.setKomunikaty(kom);
     }

     public void komunikatZatrucie(Organizm org2){
         String kom = swiat.getKomunikaty();
         kom = kom + this.getNazwa() + " został unicestiwiony przez " + org2.getNazwa() + " i umarł ";
         swiat.setKomunikaty(kom);
     }

     public void zabijDookola(){
         for (int i = -1; i < 2; i++) {
             for (int j = -1; j < 2; j++) {
                 if (polozenie.x + i >= 0 && polozenie.x + i < swiat.getMaxx() && polozenie.y + j >= 0 && polozenie.y + j < swiat.getMaxy()) {
                     Organizm org = swiat.getPlansza()[polozenie.y + j][polozenie.x + i];
                     if (org != null) {
                         if (doEliminacji(org)) {
                             org.kill(this);
                         }
                     }
                 }
             }
         }
     }

     public boolean odbilAtak(Organizm org2){
        return false;
     }

}
