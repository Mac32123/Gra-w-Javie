package po.simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        final Swiat[] world = {null};
        JFrame okno = new JFrame("Programowanie obiektowe Java");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setLocationByPlatform(true);
        okno.setVisible(true);
        okno.setLayout(null);
        //Swiat world = new Swiat(10, 10);
        final int[] dl = {4};
        final int[] sz = {4};
    JLabel d, s, info1, info2;
    info1 = new JLabel("Długość");
    info1.setBounds(200, 0, 100, 40);
    info2 = new JLabel("Szerokość");
    info2.setBounds(193, 120, 100, 40);
    d = new JLabel("4");
    d.setBounds(220, 60, 60, 60 );
    s = new JLabel("4");
    s.setBounds(220, 180, 60, 60);
    JButton b = new JButton("+");
    b.setBounds(420,60,60,60);
    b.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(dl[0] < 20) {
                dl[0]++;
                d.setText(String.valueOf(dl[0]));
            }
        }
    });

        JButton c = new JButton("-");
        c.setBounds(10,60,60,60);
        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dl[0] > 4) {
                    dl[0]--;
                    d.setText(String.valueOf(dl[0]));
                }
            }
        });
        // dodać Jframe na początek i przekazać go światu. Stworzyć świat dopiero po ustaleniu długości
        JButton f = new JButton("+");
        f.setBounds(420,180,60,60);
        f.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sz[0] < 20) {
                    sz[0]++;
                    s.setText(String.valueOf(sz[0]));
                }
            }
        });
        JButton g = new JButton("-");
        g.setBounds(10,180,60,60);
        g.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(sz[0] > 4) {
                    sz[0]--;
                    s.setText(String.valueOf(sz[0]));
                }
            }
        });

        KeyAdapter a = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(world[0] != null) {
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        world[0].getHuman().setKierunek('G');
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        world[0].getHuman().setKierunek('D');
                    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        world[0].getHuman().setKierunek('L');
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        world[0].getHuman().setKierunek('P');
                    }
                }
            }
        };


        JButton read = new JButton("Wczytaj grę");

        JButton start = new JButton("Start");
        start.setBounds(175,260,100,60);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okno.remove(b);
                okno.remove(c);
                okno.remove(d);
                okno.remove(info1);
                okno.remove(s);
                okno.remove(info2);
                okno.remove(f);
                okno.remove(g);
                okno.remove(start);
                okno.remove(read);
                okno.setVisible(false);
                okno.setVisible(true);
                okno.removeKeyListener(a);
                world[0] = new Swiat(sz[0], dl[0], okno);
                okno.addKeyListener(a);
            }
        });

        read.setBounds(10,260,100,60);
        read.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String wholeSave = "";
                try {
                    wholeSave = Files.readString(Path.of("save"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                String[] parts = wholeSave.split("\n");

                okno.remove(b);
                okno.remove(c);
                okno.remove(d);
                okno.remove(info1);
                okno.remove(s);
                okno.remove(info2);
                okno.remove(f);
                okno.remove(g);
                okno.remove(start);
                okno.remove(read);
                okno.setVisible(false);
                okno.setVisible(true);
                okno.removeKeyListener(a);
                world[0] = new Swiat(parts, okno);
                okno.addKeyListener(a);
            }
        });


        okno.addKeyListener(a);


    okno.add(b);
    okno.add(c);
    okno.add(d);
    okno.add(info1);
    okno.add(s);
    okno.add(info2);
        okno.add(f);
        okno.add(g);
        okno.add(start);
        okno.add(read);
    okno.setSize(500,500);

    //f.setVisible(true);
        //world.getPlansza()[4][4] = new Owca(4, 4, world);
        //world.getPlansza()[3][2] = new Owca(3, 2, world);
        //world.rysuj();
    }
}
