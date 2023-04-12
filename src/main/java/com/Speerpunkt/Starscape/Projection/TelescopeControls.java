package com.Speerpunkt.Starscape.Projection;

import org.springframework.stereotype.Service;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//08.04.2023
//David Kleykamp
//This Class will take care of checking the User Mouse Input
//On the Space UI, eg. Placing a Star at a certain Location.
//It serves the Projection Window to detect where a User Clicked
@Service
public class TelescopeControls implements MouseListener
{

	private static ProjectionWindow wind;

	public TelescopeControls(ProjectionWindow w)
	{

		wind = w;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// DiscoverAt(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// DiscoverAt(e.getX(), e.getY());
	}

	// Detects the current Command and attempts to execute it
	@Override
	public void mouseReleased(MouseEvent e)
	{
		//wind.GetCurrentCommand();
		wind.DiscoverAt(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
}
