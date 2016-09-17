import javax.swing.*;
import java.awt.*;

/**
 * Created by Pe4Nik on 03.04.2016.
 */
public class MainFrame extends JFrame {
    MainFrame(JPanel jp) {
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(jp, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
}
