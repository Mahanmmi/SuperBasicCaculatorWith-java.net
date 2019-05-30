import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInput extends Socket {
    private JTextField inputField;
    private JPanel panel;
    private JButton button;
    private JTextArea resultsArea;
    private PrintWriter writer;
    private BufferedReader reader;

    private void setInput() {
        String input = inputField.getText();
        writer.println(input);

        try {
            resultsArea.append(input + " = " + reader.readLine() + "\n");
        } catch (IOException e) {
            System.out.println("Cannot read from server");
        }

        inputField.setText("");
    }

    ClientInput(String host, int port) throws IOException {
        super(host, port);


        JFrame frame;
        frame = new JFrame("Calculator");
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width / 2 - frame.getWidth() / 2;
        int y = screenSize.height / 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);


        try {
            writer = new PrintWriter(getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(getInputStream()));
        } catch (IOException e) {
            System.out.println("Can't connect");
            return;
        }
        System.out.println("Connected!");


        button.addActionListener(event -> {
            setInput();
        });
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    setInput();
                }
            }
        });

        frame.setVisible(true);
    }

}
