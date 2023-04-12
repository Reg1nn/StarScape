package com.Speerpunkt.Starscape.Command;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextPane;

import com.Speerpunkt.Starscape.Projection.ProjectionWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//09.04.2023
//David Kleykamp
//This Class will take care of checking the User Input and validating it
// It communicates with the Projection Window and serves as a
// Key Listener for its Input and Output Components
//TODO: It will implement the security layer at a later point
//

public class Terminal implements MouseListener, KeyListener {

    private ProjectionWindow wind; // private to protect from access.
    private JTextPane correspondent, log; // The Terminal Components

    private String currentCommand = "!SFlare";

    // TODO: Farben Auslagern
    Color black = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    Color StarRed = new Color(1.0f, 0.0f, 0.2f, 0.7f);
    Color StarBlue = new Color(0.0f, 0.2f, 1.0f, 0.7f);
    Color StarWhite = new Color(0.99f, 0.99f, 0.99f, 0.8f);
    Color StarGreen = new Color(0.0f, 1.0f, 0.2f, 0.7f);

    public Terminal(ProjectionWindow w, JTextPane c, JTextPane l) {
        wind = w;
        correspondent = c;
        log = l;
        // SetCommand("!SFlare");
    }

    // Directly sets the current, checked command for the window
    public void SetCommand(String prompt) {
        currentCommand = prompt;
        wind.SetCurrentCommand(prompt);
    }

    // Looks if a Command has been put in and if it checks out
    // gives it to the Projection Window so it can be executed.
    // saved the previous command in case a color has been chosen
    // so the Object selection is not reset.
    private boolean IsCommand(String prompt) {
        String savedCommand = currentCommand;
        prompt.toLowerCase();
        prompt.trim();
        prompt.replace("\n", "");

        switch (prompt) {
            case "!help": {
                SetCommand("!help");
                SetCommand(savedCommand);
                return true;
            }
            case "!halo": {
                SetCommand("!halo");
                return true;
            }
            case "!sflare": {
                SetCommand("!SFlare");
                return true;
            }
            case "!nebula": {
                SetCommand("!Nebula");
                return true;
            }
            case "!w": {
                SetCommand("!w");
                SetCommand(savedCommand);
                return true;
            }
            case "!r": {
                SetCommand("!r");
                SetCommand(savedCommand);
                return true;
            }
            case "!g": {
                SetCommand("!g");
                SetCommand(savedCommand);
                return true;
            }
            case "!b": {
                SetCommand("!b");
                SetCommand(savedCommand);
                return true;
            }
            case "!gold": {
                SetCommand("!gold");
                SetCommand(savedCommand);
                return true;
            }
            case "!burnred": {
                SetCommand("!burnred");
                SetCommand(savedCommand);
                return true;
            }
            case "!whitegreen": {
                SetCommand("!whitegreen");
                SetCommand(savedCommand);
                return true;
            }
            case "!nebulapink": {
                SetCommand("!nebulapink");
                SetCommand(savedCommand);
                return true;
            }
            case "!spacegreen": {
                SetCommand("!spacegreen");
                SetCommand(savedCommand);
                return true;
            }
            case "!spaceblue": {
                SetCommand("!spaceblue");
                SetCommand(savedCommand);
                return true;
            }
            case "!+": {
                SetCommand("!+");
                SetCommand(savedCommand);
                return true;
            }
            case "!-": {
                SetCommand("!-");
                SetCommand(savedCommand);
                return true;
            }
            case "!exit": {
                SetCommand("!exit");
                return true;
            }
        }
        return false;
    }

    public String GetCommand() {
        return currentCommand;
    }

    public void setWind(ProjectionWindow pwind) {
        this.wind = pwind;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("unclick");
        wind.ChangePane(correspondent, "", StarGreen);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // When Enter is pressed the input of the Input Area is read and compared to
    // all available valid commands
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (IsCommand(correspondent.getText())) {
                wind.ChangePane(log, "---new command detected = " + correspondent.getText() + " ---", StarRed);
                wind.ChangePane(correspondent, "", StarRed);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
