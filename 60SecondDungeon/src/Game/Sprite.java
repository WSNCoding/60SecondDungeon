package Game;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite implements ImageObserver,Runnable  {
	
	private BufferedImage img;
	private int imgWidth;
	private int imgHeight;
	
	//animated related variables
	public boolean CurrentlyBeingAnimated;
	private static int ANI_GROW = 1;  // grown then shrink back to normal
	private static int ANI_SPIN = 2;
	private static double[] GrowthIncrements = {1.0, 1.02, 1.03, 1.05 , 1.06, 1.1, 
													2, 2.5 ,2.8 ,3.2 , 3.9,5,8,10};
	
	private static double[] AnimateAngles = {0.0,  10.0,  15.0,  40.0, 70.0, 130.0, 190.0,
											 260.0, 265.0, 270.0, 275.0, 290.0,
											 310.0, 320.0 , 
											 340.0, 345.0, 350.0, 355.0, 360.0};
	private int current_increment;
	private int increment;
	private int current_annimation;
	private double animation_speed;
	private double max_animation;

	
	
	public Sprite(String SpriteName)
	{
		
		img = null;
		CurrentlyBeingAnimated = false;
		String BasePath = "Images/";

		try {
	        
			img = ImageIO.read(ClassLoader.getSystemResource( BasePath+SpriteName ));
			imgWidth = img.getWidth();
            imgHeight = img.getHeight();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void Animate_Grow(double speed, double max)
	{
		CurrentlyBeingAnimated = true;
		current_increment = 0;
		increment=1;
		current_annimation = ANI_GROW;
		animation_speed = 10;
		max_animation=max;
		if (max >GrowthIncrements.length)
			max=GrowthIncrements.length;
		new Thread(this).start();
	}
	
	public void Animate_Spin(double speed)
	{
		CurrentlyBeingAnimated = true;
		current_increment = 0;
		increment=1;
		current_annimation = ANI_SPIN;
		animation_speed = speed;
		new Thread(this).start();
	}
	
	
	
	public void DrawSprite(Graphics2D g, int xpos,int ypos)
	{
		if (img == null) return;
		
	    // Display the image with its center at the initial (x, y)
	    AffineTransform transform = new AffineTransform();  // identity transform
	    transform.translate(xpos, ypos);
		
		if (!CurrentlyBeingAnimated)
		      g.drawImage(img, transform, this);
	}
	
	public void DrawAnimatedSprite(Graphics2D g, int xpos,int ypos)
	{
		if (img == null) return;
		
	    // Display the image with its center at the initial (x, y)
	    AffineTransform transform = new AffineTransform();  // identity transform

		
		if (!CurrentlyBeingAnimated)
		{
		    transform.translate(xpos, ypos);
		    g.drawImage(img, transform, this);
		}
		else
		{
			
			switch (current_annimation)
			{
			
			case 1: // ANI_GROW  // draw animated image
		      transform.translate(xpos, ypos+imgHeight-(imgHeight*GrowthIncrements[current_increment]));
		      // Try applying more transform to this image
		      transform.scale(1, GrowthIncrements[current_increment]);
		      g.drawImage(img, transform, this);
		      break;
			case 2: // ANI_SPIN: // draw spinning animated image
			      transform.translate(xpos, ypos);
			      transform.rotate(Math.toRadians(AnimateAngles[current_increment]), imgWidth/2, imgHeight/2); // about its center
			      g.drawImage(img, transform, this);
				break;
			}// end animation case statement
		}
	}
	
	
	public void DrawRotated(Graphics2D g, int xpos, int ypos, int angle)
	{

		// drawImage() does not use the current transform of the Graphics2D context
	      // Need to create a AffineTransform and pass into drawImage()
	      AffineTransform transform = new AffineTransform();  // identity transform
	      // Display the image with its center at the initial (x, y)
	      transform.translate(xpos, ypos);
	      // Try applying more transform to this image
	      transform.translate(angle, 5.0);
	      transform.rotate(Math.toRadians(15), imgWidth/2, imgHeight/2); // about its center
	         //transform.scale(1, 1-(i/10));
	      g.drawImage(img, transform, this);
	
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run() {
		// routine which update animation 
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		
		double delta = 0;
		
		while (CurrentlyBeingAnimated) 
		{
			long now = System.nanoTime();
			delta+=(now-lastTime) / nsPerTick;
			lastTime = now;
			
			while (delta>=animation_speed) 
			{
				delta-=animation_speed;
				
				switch (current_annimation)
				{
					case 1: // ANI_GROW  // draw animated image
						current_increment += increment;
						// change animation
						if (current_increment >= max_animation)  
						{ 
							increment=-increment; //start to shrink;
							current_increment--;
						}
						// are we back to orgininal size?
						if (current_increment < 0) CurrentlyBeingAnimated=false;
						break;
						
					case 2: //ANI_SPIN // draw spinning image
						current_increment ++;
						// change animation
						if (current_increment >= AnimateAngles.length)  
							CurrentlyBeingAnimated=false;
						break;

				} // end of case statement
			}			
			
		}
		
	}
	
}
