import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        XYSeriesCollection dataset1 = new XYSeriesCollection();
        JFreeChart chart1 = ChartFactory.createXYLineChart(
                "", // Title
                "Месяц", // x-axis Label
                "Суммарное число продаж", // y-axis Label
                dataset1, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                true, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        JPanel panel1 = new JPanel();
        //panel1.setSize(new Dimension(300,300));
        //panel1.setLayout(new BorderLayout());
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();



        ChartPanel chp1 = new ChartPanel(chart1);

        DefaultTableModel model1 = new DefaultTableModel();

        String header[] = new String[] { "t", "n(t)", "N(t)"};
        model1.setColumnIdentifiers(header);
        //model1.addRow(new Object[] { "t", "n(t)", "N(t)"});

        JTable jtable1 = new JTable();
        jtable1.setModel(model1);
        JScrollPane scrollPane = new JScrollPane(jtable1);

        //jtable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // set preferred column widths
        /*int index = 0;
        while (index < 3) {
            TableColumn a = jtable1.getColumnModel().getColumn(index);
            a.setPreferredWidth(300);
            index++;
        }*/


        JPanel cs1 = new Custom(model1, dataset1);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;

        panel1.add(cs1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;

        panel1.add(chp1, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 1;


        panel1.add(scrollPane, c);

        JFrame frame = new MainFrame(panel1);
        frame.setTitle("Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
