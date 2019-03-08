package org.daniel.bubblesort.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	//  The amount of changes made during this sorting cycle.
	private int changesMade = 0;
	//  Finished sorting?
	private boolean completed = false;
	
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
		frame.setSize(600, 300);     //  Set the size of the frame.
		frame.setContentPane(this);  //  Set content pane to self for easy graphics.
		frame.setVisible(true);      // Make the frame visible.
		
		this.setBackground(Color.gray);
		
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
				if(unsortedArray != null && !completed) 
					switch(event.getKeyCode()) {
					//  If space is pressed, only make one step.
					case KeyEvent.VK_SPACE:
						takeSortingStep();
						render();
						break;
					//  If the left arrow is pressed, start or stop sorting automatically.
					case KeyEvent.VK_RIGHT:
						doSortAutomatically = !doSortAutomatically;
						if(doSortAutomatically) startSortingLoop();
						break;
				} else {
					completed = false;
					currentIndex = 0;
					initUnsortedArray(24);
					render();
				}
			}
			
		});
	}
	
	private void initUnsortedArray(int length) {
		unsortedArray = new int[length];
		for(int i = 0; i < length; i++)
			unsortedArray[i] = (int) ((double) this.getHeight() * Math.random());
		barWidth = this.getWidth() / length;
	}
	
	private void startSortingLoop() {
		/*  
		 *  now is the current time, lastLoopTime is the last update time, 
		 *  loopTime is the time to wait between updating.
		 */
		long now = System.nanoTime(), lastLoopTime = now, loopTime = 1000000000 / 25;
		
		/*  
		 *  repeatedly set now to the current time, and check if the time since the 
		 *  last loop is greater than or equal to the wait time. If it is, then update.
		 */
		while(true)
			if((now = System.nanoTime()) - lastLoopTime >= loopTime) {
				//  Set the time of the last loop to now because the lastLoop is occurring.
				lastLoopTime = now;
				if(!doSortAutomatically || completed) return;
				//  Take a step and then render.
				takeSortingStep();
				render();
			}
	}
	
	private void takeSortingStep() {
		//  Iterate the currentIndex, if it's at the end, go to the beginning.
		if(++currentIndex == unsortedArray.length - 1) {
			//  If we are at the end, and nothing was changed, it's done!
			if(changesMade == 0) {
				completed = true;
				doSortAutomatically = false;
				return;
			}
			currentIndex = 0;
			changesMade = 0;
		}
		//  If the current is larger than the next, swap the two.
		if(unsortedArray[currentIndex] > unsortedArray[currentIndex + 1]) {
			final int temp = unsortedArray[currentIndex];
			unsortedArray[currentIndex] = unsortedArray[currentIndex + 1];
			unsortedArray[currentIndex + 1] = temp;
			changesMade++;
		}
	}

	private void render() {
		//  Get the Graphics object of the JPanel.
		final Graphics graphics = this.getGraphics();
		//  Paint the background(clear what is currently on the screen).
		super.paint(graphics);
		//  Loop through all of the ints in the array and draw them.
		for(int i = 0; i < unsortedArray.length; i++) {
			/*
			 * 	Go through the spectrum if finished.
			 *  Otherwise, make the current index red to make it pop, 
			 *  and the one it is being compared against blue.
			 *  All the others are made white.
			 */
			if(completed) 
				graphics.setColor(Color.getHSBColor((float) i / unsortedArray.length, 1f, 1f));
			else
				if(i == currentIndex + 1) graphics.setColor(Color.RED);
				else if(i == currentIndex + 2) graphics.setColor(Color.BLUE);
				else graphics.setColor(Color.WHITE);
			graphics.fillRect(barWidth * i, this.getHeight() - unsortedArray[i], barWidth, unsortedArray[i]);
		}
	}
	
}
