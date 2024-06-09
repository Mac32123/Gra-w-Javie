package po.simulator.organizmy;

import po.simulator.Organizm;
import po.simulator.Swiat;

public abstract class Roslina extends Organizm {

    @Override
    public void akcja() {
        int i = (int) (Math.random() * 3 ) - 1;
        int j = (int) (Math.random() * 3 ) - 1;
        if (polozenie.x == 0 && i == -1) i++;
        if (polozenie.y == 0 && j == -1) j++;
        if ((polozenie.x == swiat.getMaxx() - 1) && i == 1) i--;
        if ((polozenie.y == swiat.getMaxy() - 1) && j == 1) j--;
        if (swiat.getPlansza()[polozenie.y + j][polozenie.x + i] != this) rozpylanie(polozenie.y + j, polozenie.x + i);
    }

    public void rozpylanie(int y, int x) {
        boolean sukces = (int) (Math.random() * 10) == 0;
        if (sukces) {
            Organizm org2 = swiat.getPlansza()[y][x];
            if (org2 != null) kolizja(org2);
            else {
                Organizm org = stworzDziecko(y, x, swiat);
                org.czyWykonalAkcje = true;
                swiat.getPlansza()[y][x] = org;
            }
        }
    }

    @Override
    public void kolizja(Organizm org2) {
        if (org2.getNazwa() != nazwa && org2.getInicjatywa() == 0) {
            if (sila >= org2.getSila() && !org2.odbilAtak(this)) {
                int y = org2.getPolozenie().y;
                int x = org2.getPolozenie().x;
                Organizm org = stworzDziecko(y, x, swiat);
                org.czyWykonalAkcje = true;
                org2.przyjmijAtak(this);
                swiat.getPlansza()[y][x] = org;
            }
        }
    }


    public void komunikatDoubleKill(Organizm org2) {
        String kom = swiat.getKomunikaty();
        kom += org2.getNazwa() + " zjadł " + this.getNazwa() + " i umarł ";
        swiat.setKomunikaty(kom);
    }
    @Override
    public void komunikatSmierc(Organizm org2) {
        if (org2.getInicjatywa() > 0) {
            String kom = swiat.getKomunikaty();
            kom += org2.getNazwa() + " zjadł " + this.getNazwa() + " ";
            swiat.setKomunikaty(kom);
        }
    }

    protected abstract Organizm stworzDziecko(int y, int x, Swiat swiat);

}
