import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Pe4Nik on 03.04.2016.
 */
public class Custom extends JPanel {
    JRadioButton bassButton = new JRadioButton("Bass");
    JRadioButton gompertzButton = new JRadioButton("Gompertz");
    JButton jcalc;
    JLabel jM, jp, jq, jT, jx, jb, jn, jneed;
    JTextField jtM, jtp, jtq, jtT, jtx, jtb, jtn, jtneed;
    DefaultTableModel model;
    double M, p, q, T, Nt = 0, nt = 0, x, b, n, fxbn, Fxbn;
    XYSeriesCollection data;
    XYSeries prevseries, prevseries1;
    int ib = 1, itb = 1;
    String s;
    BigDecimal d, d1;

    void func1(XYSeries series, XYSeries series1) {
        M = Double.valueOf(jtM.getText());
        p = Double.valueOf(jtp.getText());
        q = Double.valueOf(jtq.getText());
        T = Double.valueOf(jtT.getText());

        if(itb == 1) {
            itb = 0;
        }
        else {
            while (model.getRowCount()>0){
                model.removeRow(0);
            }
            //model.addRow(new Object[] { "t", "n(t)", "N(t)"});
        }

        Nt = 0;
        nt = 0;
        try(FileWriter writer = new FileWriter("bass.txt", false)) {
        for (int t = 0; t <= T; t++) {
            nt = Double.valueOf(p * M + ( q - p) * Nt - ( q / M) * Math.pow( Nt, 2));
            s = Double.valueOf(p * M + ( q - p) * Nt - ( q / M) * Math.pow( Nt, 2)).toString();
            d = new BigDecimal(s);
            s = Double.valueOf(Nt).toString();
            d1 = new BigDecimal(s);
            model.addRow(new Object[] { t, d.setScale(2, RoundingMode.HALF_UP), d1.setScale(2, RoundingMode.HALF_UP)});
            series.add(t, nt);
            Nt += nt;
            series1.add(t, Nt);

                writer.write(String.valueOf(t));
                writer.append(' ');
                writer.write(String.valueOf(d.setScale(2, RoundingMode.HALF_UP)));
                writer.append(' ');
                writer.write(String.valueOf(d1.setScale(2, RoundingMode.HALF_UP)));
                if(t < T)
                    writer.append('\n');
        }
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    void func2(XYSeries series, XYSeries series1) {
        x = Double.valueOf(jtx.getText());
        b = Double.valueOf(jtb.getText());
        n = Double.valueOf(jtn.getText());

        if(itb == 1) {
            itb = 0;
        }
        else {
            while (model.getRowCount()>0){
                model.removeRow(0);
            }
        }

        fxbn = 0;
        Fxbn = 0;
        try(FileWriter writer = new FileWriter("gompertz.txt", false)) {
            for (; x <= 15; x++) {
                fxbn = b * Math.exp(-1 * b * x) * Math.exp( -1 * n * Math.exp(-1 * b * x)) *
                        (int)(1 + n * (1 - Math.exp(-1 * b * x)));
                Fxbn = (1 - Math.exp(-1 * b * x)) * Math.exp(-1 * n * Math.exp(-1 * b * x));
                s = Double.valueOf(fxbn).toString();
                d = new BigDecimal(s);
                s = Double.valueOf(Fxbn).toString();
                d1 = new BigDecimal(s);
                model.addRow(new Object[] { x, d.setScale(2, RoundingMode.HALF_UP), d1.setScale(2, RoundingMode.HALF_UP)});
                series.add(x, fxbn);
                series1.add(x, Fxbn);

                writer.write(String.valueOf(x));
                writer.append(' ');
                writer.write(String.valueOf(d.setScale(2, RoundingMode.HALF_UP)));
                writer.append(' ');
                writer.write(String.valueOf(d1.setScale(2, RoundingMode.HALF_UP)));
                if(x < 15)
                    writer.append('\n');
            }
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    Custom(DefaultTableModel mymodel, XYSeriesCollection datas) {
        model = mymodel;
        data = datas;
        setLayout(new GridLayout(1, 50, 10, 10));
        jM = new JLabel("M:");
        jM.setHorizontalAlignment(JLabel.RIGHT);
        jp = new JLabel("p:");
        jp.setHorizontalAlignment(JLabel.RIGHT);
        jq = new JLabel("q:");
        jq.setHorizontalAlignment(JLabel.RIGHT);
        jT = new JLabel("T:");
        jT.setHorizontalAlignment(JLabel.RIGHT);
        jx = new JLabel("x:");
        jx.setHorizontalAlignment(JLabel.RIGHT);
        jb = new JLabel("b:");
        jb.setHorizontalAlignment(JLabel.RIGHT);
        jn = new JLabel("n:");
        jn.setHorizontalAlignment(JLabel.RIGHT);
        jneed = new JLabel("n:");
        jneed.setHorizontalAlignment(JLabel.RIGHT);
        jneed.setVisible(false);


        jtM = new JTextField("");
        jtp = new JTextField("");
        jtq = new JTextField("");
        jtT = new JTextField("");
        jtx = new JTextField("");
        jtb = new JTextField("");
        jtn = new JTextField("");
        jtneed = new JTextField("");
        jtneed.setVisible(false);
        //jtM.setHorizontalAlignment(JTextField.LEFT);

        jcalc = new JButton("Рассчитать");



        jcalc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ib == 1)
                    ib = 0;
                else {
                    data.removeSeries(prevseries);
                    data.removeSeries(prevseries1);
                }
                XYSeries series, series1;
                if(bassButton.isSelected()) {
                    series = new XYSeries("n(t)");
                    series1 = new XYSeries("N(t)");
                    func1(series, series1);
                }
                else {
                    series = new XYSeries("f(x;b,n)");
                    series1 = new XYSeries("F(x;b,n)");
                    func2(series, series1);
                }

                /*if(gompertzButton.isSelected()) {
                    series = new XYSeries("f(x;b,n)");
                    series1 = new XYSeries("F(x;b,n)");
                    func2(series, series1);
                }*/
                data.addSeries(series);
                data.addSeries(series1);
                prevseries = series;
                prevseries1 = series1;
            }
        });


        bassButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gompertzButton.setSelected(false);
                add(jM);
                add(jtM);
                add(jp);
                add(jtp);
                add(jq);
                add(jtq);
                add(jT);
                add(jtT);
                remove(jneed);
                remove(jtneed);
                remove(jx);
                remove(jtx);
                remove(jb);
                remove(jtb);
                remove(jn);
                remove(jtn);
                add(jcalc);
                validate();
                repaint();
            }
        });

        gompertzButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bassButton.setSelected(false);
                remove(jM);
                remove(jtM);
                remove(jp);
                remove(jtp);
                remove(jq);
                remove(jtq);
                remove(jT);
                remove(jtT);
                add(jneed);
                add(jtneed);
                add(jx);
                add(jtx);
                add(jb);
                add(jtb);
                add(jn);
                add(jtn);
                add(jcalc);
                validate();
                repaint();
            }
        });


        bassButton.setSelected(true);
        add(bassButton);
        add(gompertzButton);
        //add(jx);
        //add(jb);
        //add(jn);

        add(jM);
        add(jtM);
        add(jp);
        add(jtp);
        add(jq);
        add(jtq);
        add(jT);
        add(jtT);
        add(jcalc);
    }
}
