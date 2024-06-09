package po.simulator.organizmy;

import po.simulator.Organizm;
import po.simulator.Swiat;
import po.simulator.koordynaty;

public abstract class Zwierze extends Organizm {

    protected koordynaty polozeniePrev;

    @Override
    public void akcja() {
        polozeniePrev = new koordynaty(polozenie);
        int i = (int) (Math.random() * 3) - 1;
        int j = (int) (Math.random() * 3) - 1;
        if (polozenie.x == 0 && i == -1) i++;
        if (polozenie.y == 0 && j == -1) j++;
        if ((polozenie.x == swiat.getMaxx() -1)  && i == 1) i--;
        if ((polozenie.y == swiat.getMaxy() -1) && j == 1) j--;
        polozenie.x = polozenie.x + i;
        polozenie.y = polozenie.y + j;
        Organizm org2 = swiat.getPlansza()[polozenie.y][polozenie.x];
        if (org2 != null && org2 != this) {
            kolizja(org2);
        }
        else {
            swiat.getPlansza()[polozeniePrev.y][polozeniePrev.x] = null;
            swiat.getPlansza()[polozenie.y][polozenie.x] = this;
        }
    }
@Override
    public void kolizja(Organizm org2) {
        if (this.nazwa == org2.getNazwa()) {
            rozmnazanie();
            polozenie.x = polozeniePrev.x;
            polozenie.y = polozeniePrev.y;
        }
	else if (this.sila < org2.getSila() && !odbilAtak(org2)) {
            swiat.getPlansza()[polozeniePrev.y][polozeniePrev.x] = null;
            this.przyjmijAtak(org2);
        }
	else if (this.sila >= org2.getSila() && !org2.odbilAtak(this)) {
            swiat.getPlansza()[polozeniePrev.y][polozeniePrev.x] = null;
            swiat.getPlansza()[polozenie.y][polozenie.x] = this;
            org2.przyjmijAtak(this);
        }
	else if (org2.odbilAtak(this) || odbilAtak(org2)) {
            polozenie.x = polozeniePrev.x;
            polozenie.y = polozeniePrev.y;
            komunikatOdbil(org2);
        }
    }

    protected void komunikatOdbil(Organizm org2) {
        Organizm org1 = this;
        if (org2.getSila() > this.getSila()) {
            Organizm tmp = org2;
            org2 = org1;
            org1 = tmp;
        }
        String kom = swiat.getKomunikaty();
        kom = kom + org2.getNazwa() + " odbił atak " + org1.getNazwa() + " organizmy pozostały na swoich pozycjach ";
        swiat.setKomunikaty(kom);
    }

    public void rozmnazanie() {
        boolean end = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (polozenie.x + i > 0 && polozenie.x + i < swiat.getMaxx() && polozenie.y + j > 0 && polozenie.y + j < swiat.getMaxy() && !end) {
                    Organizm dziecko = swiat.getPlansza()[polozenie.y + j][polozenie.x + i];
                    if (!(polozenie.x + i == polozeniePrev.x && polozenie.y + j == polozeniePrev.y) && dziecko == null) {
                        dziecko = stworzDziecko(polozenie.y + j, polozenie.x + i, swiat);
                        dziecko.czyWykonalAkcje = true;
                        swiat.getPlansza()[polozenie.y + j][polozenie.x + i] = dziecko;
                        end = true;
                    }
                }
            }
        }
    }

    protected koordynaty znajdzDobrePole() {
        koordynaty wolne = new koordynaty(-1, -1);
        koordynaty brak = new koordynaty(-1, -1);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (polozenie.x + i >= 0 && polozenie.x + i < swiat.getMaxx() && polozenie.y + j >= 0 && polozenie.y + j < swiat.getMaxy()) {
                    Organizm org = swiat.getPlansza()[polozenie.y + j][polozenie.x + i];
                    if (org == null) {
                        wolne.x = polozenie.x + i;
                        wolne.y = polozenie.y + j;
                        return wolne;
                    }
                }
            }
        }
        return brak;
    }

    protected boolean ucieczka(Organizm org2) {
        koordynaty pole = znajdzDobrePole();
        koordynaty brak = new koordynaty(-1, -1);
        if (pole.x == brak.x && pole.y == brak.y) return false;
        else {
            polozenie = pole;
            swiat.getPlansza()[polozenie.y][polozenie.x] = this;
            komunikatUcieczka(org2);
            return true;
        }
    }

    protected void komunikatUcieczka(Organizm org2) {
        String kom = swiat.getKomunikaty();
        kom = kom + this.getNazwa() + " uciekł przed " + org2.getNazwa() + " na pole " + (this.polozenie.x) + "," + (this.polozenie.y) + " ";
        swiat.setKomunikaty(kom);
    }

    protected Organizm stworzDziecko(int y, int x, Swiat swiat){
        return null;
    }

}
