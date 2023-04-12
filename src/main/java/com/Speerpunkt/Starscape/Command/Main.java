package com.Speerpunkt.Starscape.Command;


import com.Speerpunkt.Starscape.Projection.ProjectionWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class Main {

	public static void main(String[] args)
	{



		/**Detects the Screens Dimensions on Startup */
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth(); //TODO: -> Optimize for Multiscreen-Setups
		int height = gd.getDisplayMode().getHeight();

		SpringApplication.run(Main.class, args);

		System.out.println(width+" "+height);

		ProjectionWindow w;
		w = new ProjectionWindow(width, height);
		Terminal t;
		t = new Terminal(w, w.GetInput(), w.GetLog());
		w.SetTerminal(t);
	}

}
