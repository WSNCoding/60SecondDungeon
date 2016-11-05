package Game;
import java.awt.BorderLayout;
import java.util.Random;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
public class gamePanel extends Canvas  implements Runnable, KeyListener,MouseListener{

	public static Sprite Brick;
	public static Sprite Stone;
	public static Sprite Spike;
	public static Sprite Lava;
	public static Sprite Platform;
	public static Sprite Man;
	public static Sprite MenuImage;
	public static Sprite Win;
	public static Sprite Lose;	
	public int Death;
	public long EndTime;
	public static int GameState;
	public static int Height = 64*5;
	float floatHeight = Height;
	boolean HasJumped =false;
	public Physics engine; 
	
	public static boolean Key_Left = false;
	public static boolean Key_Right = false;
	public static boolean Key_Space;
int StartBlock;


private static final long serialVersionUID = 2009862575529087230L;

public static int WIDTH = 422;  // width of game windows

public static int HEIGHT = 200;  // height of game windows

public static int SCALE = 3; // scaling window

private JFrame frame;

BufferStrategy bs; //create an strategy for multi-buffering.

Boolean NeedToExit; // set to true for game to quit

int angle=0;


String[] Map;
int[] PlatGen;

// timing for screen updates

int ticks = 0;

public int frames = 0;

Generate Create = new Generate();
static Random rand = new Random();
int currentPos =64;


public gamePanel()

{
	URL iconURL = getClass().getResource("/Images/Man.png");
	// iconURL is null when not found
	ImageIcon icon = new ImageIcon(iconURL);
	

	Map = Arrays.copyOf(Generate.CreateWorld(), 8);
	GameState = 0;
setMinimumSize(new Dimension (WIDTH * SCALE, HEIGHT*SCALE));

setMaximumSize(new Dimension (WIDTH * SCALE, HEIGHT*SCALE));

setPreferredSize(new Dimension (WIDTH * SCALE, HEIGHT*SCALE));


frame = new JFrame("60Second Dungeon");
frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
frame.setLayout(new BorderLayout());

frame.add(this,BorderLayout.CENTER);

frame.pack();

frame.setResizable(false);

frame.setLocationRelativeTo(null);

frame.setVisible(true);
frame.setIconImage(icon.getImage());

// createphysics engine
engine = new Physics();


//activate keylistener
addKeyListener(this);
addMouseListener(this);
// screen graphics

createBufferStrategy(2);//jframe extends windows so i can call this method 

    bs = this.getBufferStrategy();//returns the buffer strategy used by this component

   

    NeedToExit = false;
//image load brhm
Brick = new Sprite("Green.png");	
Stone = new Sprite("Blue.png");	
Lava = new Sprite("Orange.png");
Spike = new Sprite("Spike.png");
Platform = new Sprite("Platform.png");
Man = new Sprite("Man.png"); 
MenuImage = new Sprite("Background.png");
Win = new Sprite("Win.png"); 
Lose = new Sprite("End.png");

int Smile = rand.nextInt(100);
if(Smile == 21){
	Brick = new Sprite("SMILE.png");	

}
else if(Smile == 23){
	Brick = new Sprite("Brick.png");	
	Stone = new Sprite("Overlay.png");	
	Lava = new Sprite("Lava.png");
}
//Line Generation





}


public static void main(String[] args) {

new gamePanel().start();

}


public synchronized void start() {

NeedToExit = false;

new Thread(this).start();

}


public synchronized void stop(){

NeedToExit = true;

}



@Override

public void run() {

long lastTime = System.nanoTime();

double nsPerTick = 10000000000D / 2000D;



long lastTimer = System.currentTimeMillis();


double delta = 0;


while (!NeedToExit) 

{

long now = System.nanoTime();

delta+=(now-lastTime) / nsPerTick;

lastTime = now;


while (delta>=1) 

{
//System.out.println(" "+delta+"  ");

ticks++;

// Leo :  call you function to update your game;

switch(GameState) {
case 0: UpdateMenu();
	break;
case 1: UpdateGame();
	break;
case 2: UpdateEnd();
	break;
}

delta-=1;

}

frames++;

// Leo: Call your function to draw your screen;
switch(GameState) {
	case 0: RenderMenu();
		break;
	case 1: RenderGame();
		break;
	case 2:RenderEnd();
		break;
}

//RenderGame();
//frame.removeAll();



if (System.currentTimeMillis() - lastTimer >= 1000) {

lastTimer+=1000;

//System.out.println("Frames per second ="+frames);

ticks=0;

frames=0;

}


}

// exit game completely

System.exit(0);

}




// um game logic
public void UpdateMenu(){
	
	
	
}
public void RenderMenu(){
Graphics2D g2=null;

angle++;
try{
	g2 = (Graphics2D) bs.getDrawGraphics(); 
	MenuImage.DrawSprite(g2, 0, 0);
	//MenuImage.DrawSprite(g2, 1, 1);
}  finally {

    g2.dispose();
}
bs.show();
	
}
public void UpdateEnd(){
	
	
	
}
public void RenderEnd(){
Graphics2D g2=null;


try{
	g2 = (Graphics2D) bs.getDrawGraphics(); 
	switch(Death){
	case 0:
		Lose.DrawSprite(g2, -10, -10);
		break;
	case 1:
		Win.DrawSprite(g2,-10,-10);
		break;
	
	
	
	
	}
}  finally {

    g2.dispose();
}
bs.show();
	
}




	public void UpdateGame() {
		//Timer!!!!
		if((EndTime - System.currentTimeMillis())/1000 == 0){
			Death=0;
			GameState = 2;
			
			
			
		}
		//System.out.println("Time  Remaining== "+(EndTime - System.currentTimeMillis())/1000);
		//moevingness
		Movement();
		// um game logiv=c
		
		
		
		
		
		
		if(floatHeight>64){ 
			//System.out.println("F  "+floatHeight);
			//System.out.println("F  "+Height);
			if (engine.isFalling() || engine.isJumping()){
		  float inc = engine.getGravity();
		  
		  floatHeight+=inc;
		  Height = (int) floatHeight;
		 }
		}else if((floatHeight == 64)||(floatHeight<64)){
			floatHeight+=5;
			engine.isFloor(false);
			//System.out.println("F  "+floatHeight);
			//System.out.println("F  "+Height);
			
		}
		 
		 //IF DROP BRHRHRHRH
		 if (engine.isJumping()==false)
		 {
		  // do we have floor under answer no
		  int currentposx = (currentPos / 64)+10; // may need to tweak this cus i real bad
		  int currentposy = (Height / 64)+1;
		  if (Map[currentposy+1].charAt(currentposx)==' ') {
			  //System.out.println("NO FLOOR");
			  engine.isFloor(false);
		  } else
		  {
		   engine.isFloor(true);
		   Height = (currentposy - 1)*64; 
		   floatHeight = (float)Height;
		   HasJumped =false;
		  } 
		  if (Map[currentposy+1].charAt(currentposx)=='L') {
			  GameState =2;
			  Death = 0;
		  }
		  if(currentposx == 90){
			  GameState =2;
			  Death = 1;
		  }
		 }
		 
		};
	
	




public void RenderGame() {


Graphics2D g2=null;//create a child object of Graphics


try{
	g2 = (Graphics2D) bs.getDrawGraphics(); 
	
	int ColumnNumbs = 20;
	StartBlock = currentPos / 64;  // 64 = width of block
	int PixelsIn = currentPos % 64;  // get remainder
	int Xpos = -PixelsIn;
	g2.setColor(Color.BLUE);
	g2.fillRect(0, 0, getWidth(), getHeight());
	
	for(int leng = 0; leng<21; leng++){
		
		for(int count = 0; count<Map.length; count++){
			
			switch(Map[count].charAt(StartBlock)){
			case 'D': Brick.DrawSprite(g2, Xpos, count*64);
			break;
			case 'P': 
					Platform.DrawSprite(g2, Xpos, count*64);
					  
			break;
			case 'L': Lava.DrawSprite(g2, Xpos, count*64);
			break;
			}
		}
	 Xpos += 64;
	 StartBlock++;
//	 System.out.println("Start block is ::"+StartBlock+"\n Xpos is:: "+Xpos+" \n PixelsIn is :: "+PixelsIn);
	 Man.DrawSprite(g2, 640, Height);
	
	 
	}
			
	
		
	}


 finally {

      g2.dispose();

}

bs.show();


}


@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	int key = e.getKeyCode();

    if (key == KeyEvent.VK_LEFT) {
    
    		Key_Left = true;	
    	

    	
    }

    if (key == KeyEvent.VK_RIGHT) {
    
    		Key_Right = true;	
    	

    	
    }
    if ((key == KeyEvent.VK_SPACE)&&(HasJumped == false)) {
    	Key_Space = true;	
    	HasJumped = true;
    }

    if (key == KeyEvent.VK_DOWN) {
        //System.out.println("Does not work");
    }

    

}


@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
    int key = e.getKeyCode();
	if (key == KeyEvent.VK_LEFT) {
    	
    		Key_Left = false;	
    
    }

    if (key == KeyEvent.VK_RIGHT) {
  
    		Key_Right = false;	
    	
    }
    if (key == KeyEvent.VK_SPACE  ) {
    	Key_Space = false;
    }
}

public void Movement(){
	  if (Key_Left) {
	    	if(currentPos>10){
	    		//Key_Left = true;	
	    		currentPos-= 1;

	    	}
	    }

	    if (Key_Right) {
	    	if(currentPos<5120){
	    		//Key_Right = true;	
	    	currentPos+=1;

	    	}
	    }
	    if (Key_Space) {
	    	//Key_Space = true;	
	        if ((!engine.isJumping())&&((Height/64) != 1))  {
	            System.out.println("start jjump");  	
	        	engine.startJump();  }
	    }
	
	
	
}
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	System.out.println("You Press The Key::"+e.getButton());
	int X = e.getX();
	int Y = e.getY();
	if((e.getButton() == 1)&&(GameState==0)){
		//System.out.println("You Left Clicked At::\n X:"+ e.getX()+"\n Y:"+e.getY());
		
			if((X>0)&&(250>X)&&(Y>0)&&(Y<250)){
				GameState= 1;
				EndTime = System.currentTimeMillis()+60*1000;
			
			}
			if((X>0)&&(586>X)&&(Y>250)&&(Y<589)){
				System.exit(1);
			}
			if((X>801)&&(1041>X)&&(Y>67)&&(Y<263)){
				if(Desktop.isDesktopSupported())
				{
				  try {
					Desktop.getDesktop().browse(new URI("http://wsncoding.github.io/"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
					

	}
	if((e.getButton() == 1)&&(GameState==2)){
		//System.out.println("You Left Clicked At::\n X:"+ e.getX()+"\n Y:"+e.getY());
		if((X>0)&&(451>X)&&(Y>453)&&(Y<570)){
					Map = Arrays.copyOf(Generate.CreateWorld(), 8);
					currentPos =64;
					EndTime = System.currentTimeMillis()+(60*1000);
					Death =0;
					GameState= 1;
		
		
		}
		if((X>887)&&(1268>X)&&(Y>453)&&(Y<570)){
			System.exit(1);
		}
		}
		
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
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}






}




