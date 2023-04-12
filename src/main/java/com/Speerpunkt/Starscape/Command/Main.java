package com.Speerpunkt.Starscape.Command;


import com.Speerpunkt.Starscape.Projection.ProjectionWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;

@SpringBootApplication
public class Main {
	
	private Terminal t;

	public static void main(String[] args)
	{

		/**Detects the Screens Dimensions on Startup */
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth(); //TODO: -> Optimize for Multiscreen-Setups
		int height = gd.getDisplayMode().getHeight();

		System.out.println(width+" "+height);



		ProjectionWindow w = new ProjectionWindow(width, height);



		//Run SpringBoot
		SpringApplication.run(Main.class, args);




	}

}
