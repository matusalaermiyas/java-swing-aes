import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

// UI class responsible for displaying UI for user

public class UI {
    private JFrame window;
    private JTextField inputField, secretKeyInput;
    private JButton encrypt, decrypt;
    private JLabel encryptLabel, secretKeyLabel;

    private Aes aes;

    public UI() {
        aes = new Aes();
        window = new JFrame("AES Encryption");

        encryptLabel = new JLabel("Encrypt or Decrypt");
        secretKeyLabel = new JLabel("Secret Key");
        inputField = new JTextField();
        secretKeyInput = new JTextField();
        encrypt = new JButton("Encrypt");
        decrypt = new JButton("Decrypt");

        encryptLabel.setBounds(200, 100, 150, 50);
        inputField.setBounds(330, 100, 300, 40);
        secretKeyLabel.setBounds(200, 150, 150, 50);
        secretKeyInput.setBounds(330, 150, 300, 40);
        encrypt.setBounds(330, 200, 100, 40);
        decrypt.setBounds(530, 200, 100, 40);

        window.setLayout(null);
        window.setSize(800, 800);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.add(encryptLabel);
        window.add(inputField);
        window.add(secretKeyLabel);
        window.add(secretKeyInput);
        window.add(encrypt);
        window.add(decrypt);

        handleEncryption();
        handleDecryption();
    }

    private void handleEncryption() {
        encrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (inputField.getText().equals("")) {
                    JOptionPane.showMessageDialog(window, "Please Message to Encrypt", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (secretKeyInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(window, "Please Enter Secret Key", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String res = aes.encrypt(inputField.getText(), secretKeyInput.getText());

                // Copy to the clipboard
                StringSelection stringSelection = new StringSelection(res);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                // end

                JOptionPane.showMessageDialog(window, res, "Result, Also copied to clipboard",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public void handleDecryption() {
        decrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (inputField.getText().equals("")) {
                    JOptionPane.showMessageDialog(window, "Please Enter Encrypted Message", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (secretKeyInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(window, "Please Enter Secret Key", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String res = aes.decrypt(inputField.getText(), secretKeyInput.getText());

                if (res == null) {
                    JOptionPane.showMessageDialog(window, "You Entered Unencrypted Message", "Message",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(window, res);
            }
        });
    }

}
