/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krypto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author dell
 */
public class CezarController implements Initializable {

    public final int NUMBER_OF_LETTERS_IN_ALPHABET = 'z' - 'a' + 1;
    public final int LETTER_VALUE = 'a';

    @FXML
    private TextArea text;
    @FXML
    private TextArea wynik;
    @FXML
    private Button szyfruj;
    @FXML
    private Button deszyfruj;

    @FXML
    private TextField klucz;

    @FXML
    private Label info;

    @FXML
    private RadioButton playfair;

    @FXML
    private RadioButton cezar;
    
    @FXML
    private RadioButton plotkowy;
    
    @FXML
    private ToggleGroup szyfr;
    

    private char[] chars = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
        'y', 'z', '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', '!', '@',
        '#', '$', '%', '^', '&', '(', ')', '+',
        '-', '*', '/', '[', ']', '{', '}', '=',
        '<', '>', '?', '_', '"', '.', ',', ' '
    };

    String encrypt(String text, int offset) {
        char[] plain = text.toCharArray();

        for (int i = 0; i < plain.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (j <= chars.length - offset) {
                    if (plain[i] == chars[j]) {
                        plain[i] = chars[j + offset];
                        break;
                    }
                } else if (plain[i] == chars[j]) {
                    plain[i] = chars[j - (chars.length - offset + 1)];
                }
            }
        }
        return String.valueOf(plain);
    }

    String decrypt(String cip, int offset) {
        char[] cipher = cip.toCharArray();
        for (int i = 0; i < cipher.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (j >= offset && cipher[i] == chars[j]) {
                    cipher[i] = chars[j - offset];
                    break;
                }
                if (cipher[i] == chars[j] && j < offset) {
                    cipher[i] = chars[(chars.length - offset + 1) + j];
                    break;
                }
            }
        }
        return String.valueOf(cipher);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        szyfr.selectedToggleProperty().addListener((observable) -> {
            wynik.setText("");
            text.setText("");
            klucz.setText("");
        
        });

        szyfruj.setOnAction((event) -> {
            if (validate()) {

                if (cezar.isSelected()) {
                    wynik.setText(encrypt(text.getText(), Integer.parseInt(klucz.getText())));
                    text.setText("");
                } else if(playfair.isSelected()) {
                    PlayfairCipherDecryption pf = new PlayfairCipherDecryption();
                   
                    pf.setKey(klucz.getText());
                    pf.KeyGen();
                    
                    wynik.setText(pf.encryptMessage(text.getText()));
                    text.setText("");
                } else if(plotkowy.isSelected()) {
                   Plotkowy pl = new Plotkowy(Integer.parseInt(klucz.getText()));
                    wynik.setText(pl.hash(text.getText()));
                    text.setText("");
                }

            }
        });

        deszyfruj.setOnAction(((event) -> {
            System.err.println("DE-Syfrowanie");
            if (!wynik.getText().isEmpty()) {
                if (cezar.isSelected()) {
                    text.setText(decrypt(wynik.getText(), Integer.parseInt(klucz.getText())));
                    wynik.setText("");
                } else if (playfair.isSelected()) {
                    PlayfairCipherDecryption pf = new PlayfairCipherDecryption();
                    pf.setKey(klucz.getText());
                    pf.KeyGen();
                    text.setText(
                            pf.decryptMessage(wynik.getText()));
                    wynik.setText("");
                } else if (plotkowy.isSelected()) {
                    Plotkowy pl = new Plotkowy(Integer.parseInt(klucz.getText()));
                    text.setText(
                            pl.decode(wynik.getText()));
                    wynik.setText("");
                }

            }

        }));
    }

    private boolean validate() {
        

        if (text.getText().trim().isEmpty() || klucz.getText().trim().isEmpty()) {
            info.setVisible(true);
            info.setText("Brak danych");//Integer.parseInt(klucz.getText()
            return false;
        }

        if(cezar.isSelected() || plotkowy.isSelected()) {
                try {
            int x = Integer.parseInt(klucz.getText());
            if (x > chars.length) {
                info.setVisible(true);
                info.setText("Klucz musi byc mniejszy od alfabetu:" + chars.length);
                return false;
            }
        } catch (NumberFormatException e) {
            info.setVisible(true);
            info.setText("Klucz musi byc calkowity");
            return false;
        }
        }

        info.setVisible(false);
        //
        return true;
    }

    public String encode(String text, int key) {
        key %= NUMBER_OF_LETTERS_IN_ALPHABET;

        StringBuilder wynik = new StringBuilder(text);

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= LETTER_VALUE && c < LETTER_VALUE + NUMBER_OF_LETTERS_IN_ALPHABET) {
                c = (char) ((text.charAt(i) - LETTER_VALUE + key) % NUMBER_OF_LETTERS_IN_ALPHABET + LETTER_VALUE);
                wynik.setCharAt(i, c);
            }
        }

        return wynik.toString();
    }

}
