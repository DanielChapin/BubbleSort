package org.daniel.bubblesort.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

 /*
  * Bubble Sort
  * By: Daniel Chapin
  * 	BubbleSort is a graphical representation of the bubble sort algorithm.
  * 	Each individual value is represented as a of bar with a height of that value.
  * 	The algorithm sorts by looping through all elements and doing the following:
  * 	 - Check if the value is sorted in comparison with the next element.
  * 		- If it isn't, switch the two.
  * 	 - Repeat until there is no switches.
  */

public class BubbleSortMain extends JPanel {
	
	//  Declaration of the array to be sorted.
	private int[] unsortedArray;
	
	//  Declaration of the width of each individual bar.
	private int barWidth;
	
	//  Declaration of the current index in the array, by default: 0, or the first element.
	private int currentIndex = 0;
	
	//  Declaration of whether or not the program should sort without user input.
	private boolean doSortAutomatically = false;

	//  Main method
	public static void main(String[] args) {
		//  Init a new BubbleSortMain in order to create a JPanel and to stop working statically.
		new BubbleSortMain();
	}
	
	private BubbleSortMain() {
		//  Declare and init frame.
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //  Stop the program when frame closes.
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  //  Set size of frame to size of screen
		frame.setUndecorated(true);  //  Remove the bar on top.
		frame.setContentPane(this);  //  Set content pane to self for easy graphics.
		frame.setVisible(true);  // Make the frame visible.
		
		//  Add a key listener to the frame in order to get keyboard input.
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent event) {
				
			}

			@Override
			public void keyPressed(KeyEvent event) {
				
			}

			@Override
			public void keyReleased(KeyEvent event) {
				if(unsortedArray != null) 
					switch(event.getKeyCode()) {
					//  If space is pressed, only make one step.
					case KeyEvent.VK_SPACE:
						
						break;
					//  If the left arrow is pressed, start or stop sorting automatically.
					case KeyEvent.VK_RIGHT:
						doSortAutomatically = !doSortAutomatically;
						if(doSortAutomatically) startSortingLoop();
						break;
					}
				else
					//  If there isn't an array, create a new one with the length the user presents.
					initUnsortedArray(new Scanner(System.in).nextInt());
			}
			
		});
	}
	
	private void initUnsortedArray(int length) {
		
	}
	
	private void startSortingLoop() {
		/*  
		 *  now is the current time, lastLoopTime is the last update time, 
		 *  loopTime is the time to wait between updating.
		 */
		long now = System.nanoTime(), lastLoopTime = now, loopTime = 1000000000 / 5;
		
		/*  
		 *  repeatedly set now to the current time, and check if the time since the 
		 *  last loop is greater than or equal to the wait time. If it is, then update.
		 */
		while(true)
			if((now = System.nanoTime()) - lastLoopTime >= loopTime) {
				//  Set the time of the last loop to now because the lastLoop is occurring.
				lastLoopTime = now;
				//  Take a step and then render.
				takeSortingStep();
				render();
			}
	}
	
	private void takeSortingStep() {
		//  Iterate the currentIndex, if it's at the end, go to the beginning.
		if(++currentIndex == unsortedArray.length) currentIndex = 0;
	}

	private void render() {
		//  Get the Graphics object of the JPanel.
		final Graphics graphics = this.getGraphics();
		//  Paint the background(clear what is currently on the screen).
		super.paint(graphics);
		//  Set the current drawing color to white.
		graphics.setColor(Color.white);
		//  Loop through all of the ints in the array and draw them.
		for(int i = 0; i < unsortedArray.length - 1; i++) {
			/*
			 *  Make the current index red to make it pop, 
			 *  and the one it is being compared against blue.
			 */
			if(i == currentIndex) graphics.setColor(Color.red);
			else if(i == currentIndex + 1) graphics.setColor(Color.blue);
			graphics.fillRect(barWidth * i, 0, barWidth, unsortedArray[i]);
		}
			
		
	}
}
