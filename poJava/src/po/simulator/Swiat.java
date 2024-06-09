package po.simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Swiat {

    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private JButton[][] entities;
    private JButton skill;
    private final JFrame okno;
    private final Organizm[][] plansza;
    private String komunikaty;
    private Czlowiek human;
    private int maxy;
    private int maxx;
    private boolean czlowiekZyje;
    private String umjejetnosc;

    private void inicjuj(int y, int x){
        int iloscPol = x * y;			// /200 - rośliny, /50
        int iloscRoslin = iloscPol / 200 + (int) (Math.random() * 2);
        int iloscZwierzat = iloscPol / 50;
        int rodzaj = 0;
        for (int i = 0; i < iloscZwierzat; i++) {
            int yy = (int) (Math.random() * y);
            int xx = (int) (Math.random() * x);
            if (yy == 0 && xx == 0) xx++;
            switch (rodzaj) {
                case 0: plansza[yy][xx] = new Wilk(yy, xx, this); break;
                case 1: if ((int) (Math.random() * 2) == 0) plansza[yy][xx] = new Owca(yy, xx, this);
                else plansza[yy][xx] = new Zolw(yy, xx, this); break;
                case 2: if ((int) (Math.random() * 2) == 0) plansza[yy][xx] = new Antylopa(yy, xx, this);
                else plansza[yy][xx] = new Lis(yy, xx, this);
            }
            rodzaj++;
            rodzaj %= 3;
        }
        rodzaj = 1;
        for (int i = 0; i < iloscRoslin; i++) {									//dodać sprawdzenie czy pole nie jest już zajęte
            int yy = (int) (Math.random() * y);
            int xx = (int) (Math.random() * x);
            if (yy == 0 && xx == 0) xx++;
            switch (rodzaj) {
                case 0: plansza[yy][xx] = new Guarana(yy, xx, this); break;
                case 1: if ((int) (Math.random() * 2) == 0) plansza[yy][xx] = new Trawa(yy, xx, this);
                else plansza[yy][xx] = new Mlecz(yy, xx, this); break;
                case 2: if ((int) (Math.random() * 2) == 0) plansza[yy][xx] = new Barszcz(yy, xx, this);
                else plansza[yy][xx] = new Jagoda(yy, xx, this); break;
            }
            rodzaj++;
            rodzaj %= 3;
        }
    }

    private void inicjujOkno(int r_width, int r_height){
        okno.setFocusable(true);
        okno.requestFocusInWindow();
        JLabel info = new JLabel("Tarcza Alzura");
        info.setBounds(485, 450, 100, 60);
        okno.add(info);
        JButton nextTurn = new JButton("Następna tura");
        nextTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tura();
            }
        });
        nextTurn.setBounds(250, 500, 120 , 60);
        okno.add(nextTurn);
        JButton save = new JButton("Zapisz grę");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        save.setBounds(0, 500, 120 , 60);
        okno.add(save);
        skill = new JButton("Umiejętność");
        skill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                human.wywolajUmiejetnosc();
            }
        });
        skill.setBounds(450, 500, 150, 60);
        skill.setBackground(Color.green);
        okno.add(skill);
        entities = new JButton[maxy][maxx];
        for(int i =0; i < maxx; i++) {
            for (int j = 0; j < maxy; j++) {
                entities[j][i] = new JButton();
                entities[j][i].setBounds(i*r_width, j*r_height, r_width, r_height);
                int finalJ = j;
                int finalI = i;
                entities[j][i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(plansza[finalJ][finalI] == null){
                            JFrame dodaj = new JFrame("Dodaj organizm");
                            dodaj.setLocationByPlatform(true);
                            dodaj.setVisible(true);
                            dodaj.setLayout(null);
                            dodaj.setSize(300, 400);
                            JLabel choose = new JLabel("Wybierz zwierze");
                            choose.setBounds(100,10, 100, 100);
                            dodaj.add(choose);
                            String[] organizmy = {"Zolw", "Lis", "Wilk", "Owca", "Antylopa", "Trawa", "Mlecz", "Jagoda", "Barszcz", "Guarana"};
                            JComboBox wybierz = new JComboBox(organizmy);
                            wybierz.setBounds(100, 100,90,20);
                            dodaj.add(wybierz);
                            JButton zatwierdz = new JButton("Zatwierdź");
                            zatwierdz.setBounds(100, 300, 100, 50);
                            zatwierdz.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String org = (String) wybierz.getItemAt(wybierz.getSelectedIndex());
                                    plansza[finalJ][finalI] = createOrganizmFromString(org, finalJ, finalI);
                                    dodaj.dispose();
                                    rysuj();
                                }
                            });
                            dodaj.add(zatwierdz);
                        }
                        else System.out.println(plansza[finalJ][finalI].getNazwa());
                    }
                });
            }
        }
    }


    public Swiat(String[] saves, JFrame okno){
        maxy = Integer.parseInt(saves[0]);
        maxx = Integer.parseInt(saves[1]);
        this.okno = okno;
        int r_width = (WIDTH - 13)/ maxx;
        int r_height = (HEIGHT -40 )/ maxy;
        inicjujOkno(r_width, r_height);
        komunikaty = "";
        czlowiekZyje = true;
        int k = 3;
        plansza = new Organizm[maxy][maxx];
        for (int i = 0; i < maxy; i++) {
            for (int j = 0; j < maxx; j++) {
                if(saves[k].equals("null")){
                plansza[i][j] = null;
                } else{
                    String[] organizm = saves[k].split(" ");
                    plansza[i][j] = createOrganizmFromString(organizm[0],i, j);
                    plansza[i][j].setSila(Integer.parseInt(organizm[1]));
                }
                k++;
            }
        }
        String[] humanKord = saves[k+2].split(" ");
        human = (Czlowiek) plansza[Integer.parseInt(humanKord[0])][Integer.parseInt(humanKord[1])];
        String[] humanSkill = saves[k+1].split(": ");
        human.setUmiejetnosc(humanSkill[0]);
        human.setCzyAktywna(humanSkill[1].trim().equals("aktywna"));
        human.setLicznikUmiejetnosci(Integer.parseInt(saves[k+3]));
        rysuj();
    }

    public Swiat(int y, int x, JFrame okno){
        maxx = x;
        maxy = y;
        int r_width = (WIDTH - 13)/ maxx;
        int r_height = (HEIGHT -40 )/ maxy;
        this.okno = okno;
        inicjujOkno(r_width, r_height);
        this.human = new Czlowiek(0, 0, 189033, this);
        komunikaty = "";
        czlowiekZyje = true;
        plansza = new Organizm[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                plansza[i][j] = null;
            }
        }
        if (x * y < 40) {
            // zakomunikowac "Plansza jest zbyt mała. Mogą wystąpić nieprzewidzane błędy";
        }
        else if (x * y > 600) {
            //zakomunikować "Plansza jest zbyt duża. Mogą wystąpić nieprzewidziane błędy";
        }
        inicjuj(y, x);
        plansza[0][0] = human;
        rysuj();
    }

    public Czlowiek getHuman() {
        return human;
    }

    public JFrame getOkno() {
        return okno;
    }

    public void setMaxx(int maxx) {
        this.maxx = maxx;
    }

    public void setMaxy(int maxy) {
        this.maxy = maxy;
    }

    public int getMaxx() {
        return maxx;
    }

    public int getMaxy() {
        return maxy;
    }

    public boolean isCzlowiekZyje() {
        return czlowiekZyje;
    }

    public Organizm[][] getPlansza() {
        return plansza;
    }

    public String getKomunikaty() {
        return komunikaty;
    }

    public void setKomunikaty(String komunikaty) {
        this.komunikaty = komunikaty;
    }

    public void setCzlowiekZyje(boolean czlowiekZyje) {
        this.czlowiekZyje = czlowiekZyje;
    }

    public void tura(){
        okno.requestFocusInWindow();
        komunikaty = "";
        for (int i = 0; i < maxy; i++) {
            for (int j = 0; j < maxx; j++) {
                Organizm org = plansza[i][j];
                if (org != null) org.czyWykonalAkcje = false;
            }
        }
        int inicjatywa = 7;
        while (inicjatywa >= 0) {
            for (int i = 0; i < maxy; i++) {
                for (int j = 0; j < maxx; j++) {
                    Organizm org = plansza[i][j];
                    if (org != null) {
                        if (!org.czyWykonalAkcje) {
                            if (org.getInicjatywa() == inicjatywa){
                                org.czyWykonalAkcje = true;
                                org.akcja();
                            }
                        }
                    }
                }
            }
            switch (inicjatywa) {
                case 7 -> inicjatywa = 5;
                case 5 -> inicjatywa--;
                case 4 -> inicjatywa = 1;
                case 1 -> inicjatywa = 0;
                case 0 -> inicjatywa = -1;
            }
        }

        if(isCzlowiekZyje()) {
            rysuj();
        } else {
            okno.setVisible(false);
            okno.dispose();;
        }
        if(komunikaty != "") System.out.println(komunikaty);
    }

    public void rysuj(){
        if(human.isCzyAktywna()){
            skill.setBackground(Color.yellow);
        }else if(human.getLicznikUmiejetnosci() >0){
            skill.setBackground(Color.red);
        }else {
            skill.setBackground(Color.green);
        }
            for(int i =0; i < maxx; i++){
                for(int j =0; j < maxy; j++){
                    entities[j][i].setForeground(plansza[j][i] == null ? Color.white : plansza[j][i].rysowanie());
                    entities[j][i].setBackground(plansza[j][i] == null ? Color.white : plansza[j][i].rysowanie());
                    okno.add(entities[j][i]);
                }
            }
                okno.setSize(WIDTH + 100,HEIGHT + 100);

    }

    public void save()
        throws IOException {
            String str = maxy + "\n" + maxx + "\n{\n";
            for(int i =0; i < maxy; i++){
                for(int j = 0; j < maxx; j++){
                    if(plansza[i][j] == null) str += "null\n";
                    else str += plansza[i][j].nazwa + " " + plansza[i][j].getSila() + "\n";
                }
            }
            str += "}\n" + umjejetnosc + "\n" + human.polozenie.y + " " + human.polozenie.x + "\n" + human.getLicznikUmiejetnosci();
            BufferedWriter writer = new BufferedWriter(new FileWriter("save"));
            writer.write(str);
            writer.close();
        }

    public void setUmjejetnosc(String umjejetnosc) {
        this.umjejetnosc = umjejetnosc;
    }

    public String getUmjejetnosc() {
        return umjejetnosc;
    }

    public Organizm createOrganizmFromString(String org, int y, int x){
        Organizm newOrg = switch (org) {
            case "Czlowiek" -> new Czlowiek(y, x, 189033, this);
            case "Zolw" -> new Zolw(y, x, this);
            case "Antylopa" -> new Antylopa(y, x, this);
            case "Lis" -> new Lis(y, x, this);
            case "Owca" -> new Owca(y, x, this);
            case "Wilk" -> new Wilk(y, x, this);
            case "Barszcz" -> new Barszcz(y, x, this);
            case "Guarana" -> new Guarana(y, x, this);
            case "Jagoda" -> new Jagoda(y, x, this);
            case "Mlecz" -> new Mlecz(y, x, this);
            case "Trawa" -> new Trawa(y, x, this);
            default -> null;
        };
        return newOrg;
    }

}
