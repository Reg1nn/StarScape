package com.Speerpunkt.Starscape.Projection;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.Speerpunkt.Starscape.Command.Terminal;
import com.Speerpunkt.Starscape.DataStorage.LocalProjectionStorage;
import org.springframework.stereotype.Component;

//This class will project the Data onto the Screen.
//It Implements the DataStorage to renew the Projection accurately
//When an Input is detected the Projection reads the Mouse Position off the Screen
//MouseClicks will draw the Saved Element on Screen

@Component
public class ProjectionWindow
{

	// All Utility Elements
	private String inputCommand = "!SFlare"; // currently selected Element to Draw
	private Color commandColor; // the currently chosen color
	private int sizeCommand = 1;
	private LocalProjectionStorage lps; // The Projection Stored as Data
	private int screenSizeX;
	private int screenSizeY;

	// GUI Elements
	JFrame menuFrame = new JFrame(); // The menu Window that will open when the Application is started
	JFrame projectionFrame; // The actual Projection Window. It will contain the Canvas
	Terminal terminal;
	JTextPane input;
	JTextPane log;

	// Menu Elements

	// Painting Tools
	JLabel universe; // The UI Container
	JLayeredPane universeBounds; // container for the canvas
	BufferedImage userSpace; // the canvas
	Graphics2D userTelescope; // The Paint
	TelescopeControls tc;

	// Preset Colors
	Color black = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	Color StarRed = new Color(1.0f, 0.0f, 0.2f, 0.7f);
	Color StarBurnRed = new Color(1.0f, 0.342f, 0.1f, 0.7f);
	Color StarBlue = new Color(0.0f, 0.2f, 1.0f, 0.7f);
	Color StarWhite = new Color(0.90f, 0.95f, 0.85f, 0.7f);
	Color StarGreen = new Color(0.0f, 1.0f, 0.2f, 0.4f);
	Color StarGold = new Color(0.99f, 0.8f, 0.0f, 0.6f);
	Color WhiteGreen = new Color(0.00f, 1.0f, 0.843f, 0.6f);
	Color NebulaPink = new Color(0.93f, 0.43f, 0.63f, 0.7f);
	Color SpaceGreen = new Color(0.21f, 0.72f, 0.49f, 0.7f);
	Color SpaceBlue = new Color(0.00f, 0.12f, 0.26f, 0.7f);

	public ProjectionWindow(int sW, int sH)
	{
		System.out.println(sW + " " + sH);

		// TODO: Create simple Menu. Style later.

		// Open the Menu when the Application is started
		menuFrame.setSize(sW, sH);
		menuFrame.setUndecorated(true);
		menuFrame.setResizable(false);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO: ->add Exception Handler later!
		menuFrame.setTitle("Starscape Menu");
		
		// menuFrame.pack();
		menuFrame.setVisible(true);
		ConstructMenu("InstantLaunch");
	}

	// Constructs the Menu and the associated Event Handlers
	private void ConstructMenu(String menuCommand)
	{
		switch (menuCommand)
		{
		case "InstantLaunch":
			SpaceLaunch(menuFrame.getWidth(), menuFrame.getHeight()); // TODO: -> Add Setting for adjustable Sizes later
		}
	}

	// Constructs the SpaceInterface
	private void SpaceLaunch(int sW, int sH)
	{
		// Space Window is constructed, Menus Window closed.
		projectionFrame = new JFrame();
		projectionFrame.setSize(sW, sH);
		projectionFrame.setUndecorated(true);
		projectionFrame.setResizable(false);
		projectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO: ->add Exception Handler later!
		projectionFrame.setTitle("com/Speerpunkt/Starscape");
		projectionFrame.setAlwaysOnTop(false);
		projectionFrame.getContentPane().setLayout(null);

		// System.out.println(sW + " " + sH);

		// Instantiates the Telescope(Graphics) and the darkness of Space(The Image)
		userSpace = new BufferedImage(sW, sH, BufferedImage.TYPE_INT_ARGB);
		userTelescope = userSpace.createGraphics();
		userTelescope.setPaint(black);
		userTelescope.fillRect(0, 0, sW, sH); // TODO: Create Timer to make cool Intro here.
		userTelescope.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		// Initiates Telescope COntrols
		tc = new TelescopeControls(this);

		// Presets the Discovered Color
		commandColor = StarGold;

		// Create the Interface Consisting of the Background Label and the Layered Pane
		// holding the Components
		// Layered Pane
		universeBounds = new JLayeredPane();
		universeBounds.setLayout(null);
		universeBounds.setSize(sW, sH);
		screenSizeX = sW;
		screenSizeY = sH;
		universeBounds.setLocation(0, 0);
		// Background
		universe = new JLabel();
		universe.setLayout(null);
		universe.setSize(sW, sH);
		universe.setLocation(0, 0);
		universe.addMouseListener(tc);

		// Adding Terminal/Text Interaction
		// Creates the Two Text Components
		input = new JTextPane();
		log = new JTextPane();

		// Put the Panes inside a ScrollPane
		JScrollPane inputScrollPane = new JScrollPane(input);
		inputScrollPane.setSize(sW / 8, sH / 15);
		inputScrollPane.setLocation(3, 3);
		inputScrollPane.setOpaque(true);
		inputScrollPane.setBackground(new Color(0.2f, 0.2f, 0.2f, 0.3f));

		JScrollPane logScrollPane = new JScrollPane(log);
		logScrollPane.setSize(sW / 8, sH / 8);
		logScrollPane.setLocation(3, 2 + sH / 15 + 5);
		logScrollPane.setOpaque(true);
		logScrollPane.setBackground(new Color(0.2f, 0.2f, 0.2f, 0.3f));

		// Connect the Text Panes to the Terminal
		terminal = new Terminal(this, input, log);
		input.addMouseListener(terminal);
		input.addKeyListener(terminal);

		// add them to the UI
		universeBounds.add(universe, 0);
		universeBounds.add(logScrollPane, 0);
		universeBounds.add(inputScrollPane, 0);

		// Modifies the Two Text Components
		// Input
		input.setOpaque(true);
		input.setBackground(new Color(Color.OPAQUE));
		ChangePane(input, "Captain, input your Commands here...", StarRed);

		log.setOpaque(true);
		log.setBackground(new Color(Color.OPAQUE));
		log.setEditable(false);
		ChangePane(log, "Starscape initialized successfuly...", StarRed);

		// update the Frame
		projectionFrame.setContentPane(universeBounds);
		Blink();
		projectionFrame.setVisible(true);

	}

	public void ChangePane(JTextPane p, String t, Color c)
	{

		if (p == input)
		{
			p.setText(t);
			StyledDocument docL = p.getStyledDocument();
			Style localStyle = input.addStyle("Green", null);
			StyleConstants.setForeground(localStyle, c);
			docL.setCharacterAttributes(0, docL.getLength(), localStyle, false);
		} else if (p == log)
		{
			p.setText(p.getText() + "\n" + t);
			StyledDocument docL = p.getStyledDocument();
			Style localStyle = input.addStyle("Green", null);
			StyleConstants.setForeground(localStyle, c);
			docL.setCharacterAttributes(0, docL.getLength(), localStyle, false);
		} else
		{
			System.out.println("Kritischer Fehler");
		}
		p.repaint();
		p.revalidate();
	}

	// Updates the Telescope Snapshot displayed
	private void Blink()
	{
		universe.setIcon(new ImageIcon(userSpace));
		projectionFrame.validate();
	}

	// Checks the Command selected and Executes it with the given Coordinates
	public void DiscoverAt(int xC, int yC)
	{
		terminal.GetCommand();
		System.out.println("Command is " + inputCommand);
		switch (inputCommand)
		{
		case "!help":
		{
			String iC = "Captain, following Commands are available! \n" + "!halo \n" + "!SFlare \n" + "!Nebula  \n"
					+ "and finally !w,!b,!g,!r,!gold,!burnred,!whitegreen,!nebulapink,!spacegreen,!spaceblue  \n"
					+ "for Color Selection  \n" + "We automatically search for Halo Stars in the beginning!";
			ChangePane(log, iC, StarBlue);
			break;
		}
		case "!halo":
		{
			if (xC + yC != 0)
			{
				Random r = new Random();

				// Creates a new random Halo Star with a
				DiscoverHaloStar(xC, yC, r.nextInt((int) screenSizeX / 100), 1.0f, r.nextInt(5));
				System.out.println("----Halo Star Discovered!----");
			}
			break;
		}
		case "!SFlare":
		{
			if (xC + yC != 0)
			{
				Random r = new Random();
				DiscoverSFlare(xC, yC, r.nextInt((int) screenSizeX / 70), 1.0f, r.nextInt(4, 7));
				System.out.println("----Standart Flare Star Discovered!----");
				break;
			}
		}
		case "!Nebula":
		{
			if (xC + yC != 0)
			{

				Random r = new Random();
				DiscoverNebula(xC, yC, r.nextInt((int) screenSizeX / 23, (int) screenSizeX / 12),
						r.nextInt((int) screenSizeY / 25, (int) screenSizeY / 14), 0.80f, r.nextInt(3, 7));
				System.out.println("----Standart Flare Star Discovered!----");
			}
			break;
		}
		case "!w":
		{
			commandColor = StarWhite;
			terminal.SetCommand("!SFlare");
			break;
		}
		case "!b":
		{
			commandColor = StarBlue;
			terminal.SetCommand("!halo");
			break;
		}
		case "!g":
		{
			commandColor = StarGreen;
			terminal.SetCommand("!SFlare");
			break;
		}
		case "!r":
		{
			commandColor = StarRed;
			terminal.SetCommand("!halo");
			break;
		}
		case "!gold":
		{
			commandColor = StarGold;
			terminal.SetCommand("!SFlare");
			break;
		}
		case "!burnred":
		{
			commandColor = StarGold;
			terminal.SetCommand("!Nebula");
			break;
		}
		case "!whitegreen":
		{
			commandColor = WhiteGreen;
			terminal.SetCommand("!Nebula");
			break;
		}
		case "!nebulapink":
		{
			commandColor = NebulaPink;
			terminal.SetCommand("!Nebula");
			break;
		}
		case "!spacegreen":
		{
			commandColor = SpaceGreen;
			terminal.SetCommand("!SFlare");
			break;
		}
		case "!spaceblue":
		{
			commandColor = SpaceBlue;
			terminal.SetCommand("!halo");
			break;
		}
		}
	}

	// Creates a Halo Star
	// diameter: diameter of the Star
	// intensity: the Alpha of the Core
	// fadeSteps: the number of outer Circles extending from the core
	// Draws a Core with the given Alpha and then additonal Circles underneath with
	// fading alpha effect
	private void DiscoverSFlare(int xC, int yC, int diameter, float intensity, int fadeSteps)
	{
		System.out.println("----Attempting to Draw a SFlare----");
		float fade = intensity;

		// minimum 2 outer circles
		if (fadeSteps <= 3)
		{
			fadeSteps = 4;
		}

		System.out.println(fadeSteps);

		if (diameter <= 5)
		{
			diameter = 6;
		}

		// Draws the Individual Circles which are connected to another
		for (int i = fadeSteps; i > 0; i--)
		{

			if (i == 1)
			{
				// Draw the Flares
				userTelescope.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
				userTelescope.setColor(commandColor);
				userTelescope.drawLine(xC - diameter, yC, xC + diameter, yC);

				userTelescope.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
				userTelescope.setColor(commandColor);
				userTelescope.drawLine(xC, yC - diameter, xC, yC + diameter);
				// TODO: Expand the Lines, make them prettier
			} else if (i == fadeSteps)
			{
				// Draw the Star Core
				userTelescope.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				userTelescope.setColor(commandColor);
				userTelescope.fillOval(xC - diameter / i / 2, yC - diameter / i / 2, diameter / i+1, diameter / i+1);
			} else
			{
				// Draw the big flares
				// Sideways
				userTelescope.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
				userTelescope.setColor(commandColor);
				int h = diameter / (fadeSteps - i)+1;
				int w = diameter / i / 2+1;
				userTelescope.fillOval(xC - w / 2, yC - h / 2, w, h);
				// Up
				userTelescope.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
				userTelescope.setColor(commandColor);
				h = diameter / i / 2+1;
				w = diameter / (fadeSteps - i)+1;
				userTelescope.fillOval(xC - w / 2, yC - h / 2, w, h);
			}

			fade -= fade / fadeSteps;
		}
		// Redraw the Universe
		Blink();
	}

	// Creates a 4 pointed Flare Star
	// diameter: diameter of one Flare (Point to Point) <--->
	// intensity: the Alpha of the Core
	// fadeSteps: the number of outer Circles extending from the core
	// Draws a Core with the given Alpha and then additonal Circles underneath with
	// fading alpha effect
	private void DiscoverHaloStar(int xC, int yC, int diameter, float intensity, int fadeSteps)
	{
		System.out.println("----Attempting to Draw a Halo Star----");
		float fade = intensity;

		// minimum 2 outer circles
		if (fadeSteps <= 2)
		{
			fadeSteps = 3;
		}

		if (diameter <= 2)
		{
			diameter = 3;
		}

		// Draws the Individual Circles which are connected to another
		for (int i = fadeSteps; i > 0; i--)
		{
			userTelescope.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
			userTelescope.setColor(commandColor);
			userTelescope.fillOval(xC - diameter / i / 2, yC - diameter / i / 2, diameter / i, diameter / i);
			System.out.println("Drew a Circle! Diameter: " + diameter / i + " Alpha/Fade: " + fade);
			fade -= fade / fadeSteps;
		}
		// Redraw the Universe
		Blink();
	}

	// Creates a Nebula
	// Uses intense Randomisation to paint the Clouds
	// height: approximate height of the Nebula
	// intensity: the Alpha of the Core
	// fadeSteps: the number of outer Nebula Circles extending from the core
	private void DiscoverNebula(int xC, int yC, int width, int height, float intensity, int fadeSteps)
	{
		System.out.println("----Attempting to Draw a Halo Star----");

		Random xR = new Random();
		Random yR = new Random();
		Random kR = new Random(); // Kleinlich Randomisierer
		int pX = xC;
		int pY = yC;
		int rK1 = 9;
		int rK2 = 7;
		int rK3 = kR.nextInt(12, 20);
		float fade = intensity;
		System.out.println(fadeSteps);
		System.out.println(height);
		System.out.println(width);
		System.out.println((int) ((height / 2) * (width / 2) * 3.14));

		for (int i = fadeSteps; i > 0; i--)
		{
			float rK4 = kR.nextFloat(fade, fade + 0.4f);
			width = (int) (height * rK4);
			height = (int) (width * rK4);

			for (int f = (int) (((height / 2) * (width / 2) * 3.14) / rK3); f > 5; f--)
			{
				rK1 = kR.nextInt((int) (height * kR.nextFloat(0.2f, 1.1f)), (int) height * 2);
				rK2 = kR.nextInt((int) width / rK3, (int) (width * kR.nextFloat(0.2f, 1.1f)));
				pY = yR.nextInt(yC - height / i - rK1, yC + height / i + rK2);
				pX = xR.nextInt(xC - width / i - rK2, xC + width / i + rK1);
				userTelescope.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
				userTelescope.setColor(commandColor);
				userTelescope.fillOval(pX, pY, yR.nextInt(1, 3), yR.nextInt(1, 3));
			}
			fade -= fade / fadeSteps;
		}

		Blink();
	}

	// Get the Current Command that has been typed into the Terminal
	public void GetCurrentCommand()
	{
		inputCommand = terminal.GetCommand();
		DiscoverAt(0, 0);
	}

	public void SetCurrentCommand(String p)
	{
		System.out.println("PW erhält command " + p);
		inputCommand = p;
		DiscoverAt(0, 0);
		System.out.println("Command ist " + inputCommand);
	}

	public void SetTerminal(Terminal t)
	{
		this.terminal = t;
	}

}
