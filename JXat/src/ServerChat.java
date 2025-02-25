import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ServerChat {
    private JTextArea textArea;
    private JTextField textField;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        new ServerChat().startServer();
    }

    public void startServer() {
        JFrame frame = new JFrame("Chat do Servidor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());

        
        frame.getContentPane().setBackground(Color.BLACK);  
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setForeground(new Color(0, 255, 0));  
        textArea.setBackground(Color.BLACK);  
        textArea.setFont(new Font("Courier New", Font.PLAIN, 14)); 
        textArea.setCaretColor(Color.GREEN);  
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        textField = new JTextField();
        frame.add(textField, BorderLayout.SOUTH);

        textField.addActionListener(e -> sendMessage());

        frame.setVisible(true);

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("\033[0;32mServidor iniciado na porta 8080...");  
            Socket socket = serverSocket.accept();
            System.out.println("\033[0;32mCliente conectado!");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while (true) {
                message = in.readLine();
                textArea.append("Cliente: " + message + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = textField.getText();
        out.println(message);
        textArea.append("Servidor: " + message + "\n");
        textField.setText("");
    }
}
