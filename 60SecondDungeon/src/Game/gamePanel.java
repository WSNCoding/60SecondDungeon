package Game;
import java.awt.BorderLayout;
import java.util.Random;

import java.awt.Canvas;

import java.awt.Dimension;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
public class gamePanel extends Canvas implements Runnable, KeyListener{
	
	public static Sprite Brick;
	public static Sprite Stone;
	public static Sprite Spike;
	public static Sprite Lava;
	public static Sprite Platform;
	public static Sprite Man;
	public static int Height = 64*5;
	float floatHeight = Height;
	public Physics engine; 

int StartBlock;


private static final long serialVersionUID = 2009862575529087230L;

public static int WIDTH = 422;  // width of game windows

public static int HEIGHT = 200;  // height of game windows

public static int SCALE = 3; // scaling window

private JFrame frame;

BufferStrategy bs; //create an strategy for multi-buffering.

Boolean NeedToExit; // set to true for game to quit


String[] Map;
int[] PlatGen;

// timing for screen updates

int ticks = 0;

int frames = 0;

Generate Create = new Generate();
static Random rand = new Random();
int currentPos =64;


public gamePanel()

{
	

	Map = Arrays.copyOf(Generate.CreateWorld(), 8);
	
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


// createphysics engine
engine = new Physics();


//activate keylistener
addKeyListener(this);

// screen graphics

createBufferStrategy(2);//jframe extends windows so i can call this method 

    bs = this.getBufferStrategy();//returns the buffer strategy used by this component

   

    NeedToExit = false;
//image load brh
Brick = new Sprite("Green.png");	
Stone = new Sprite("Blue.png");	
Lava = new Sprite("Orange.png");
Spike = new Sprite("Spike.png");
Platform = new Sprite("Platform.png");
Man = new Sprite("Man.png"); 
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

double nsPerTick = 10000000000D / 60D;



long lastTimer = System.currentTimeMillis();


double delta = 0;


while (!NeedToExit) 

{

long now = System.nanoTime();

delta+=(now-lastTime) / nsPerTick;

lastTime = now;


while (delta>=1) 

{
//	System.out.println(" "+delta+"  ");

ticks++;

// Leo :  call you function to update your game;

UpdateGame();

delta-=1;

}

frames++;

// Leo: Call your function to draw your screen;

RenderGame();


if (System.currentTimeMillis() - lastTimer >= 1000) {

lastTimer+=1000;

System.out.println("Frames per second ="+frames);

ticks=0;

frames=0;

}


}

// exit game completely

System.exit(0);

}




// Leo:  put your code here to run your game
	public void UpdateGame() {

		// Leo:  put your code here to run your game
		 if (engine.isFalling() || engine.isJumping()){
		  float inc = engine.getGravity();
		  
		  floatHeight+=inc;
		  Height = (int) floatHeight;
		 }
		 
		 //fix 2 - do we need to drop
		 if (engine.isJumping()==false)
		 {
		  // do we have floor under us
		  int currentposx = (currentPos / 64)+10; // may need to tweak this
		  int currentposy = (Height / 64)+1;
		  if (Map[currentposy+1].charAt(currentposx)==' ') {
			  System.out.println("NO FLOOR");
			  engine.isFloor(false);
		  } else
		  {
		   engine.isFloor(true);
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
	for(int leng = 0; leng<21; leng++){
		
		for(int count = 0; count<Map.length; count++){
			
			switch(Map[count].charAt(StartBlock)){
			case 'D': Brick.DrawSprite(g2, Xpos, count*64);
			break;
			case 'P': 
					Stone.DrawAnimatedSprite(g2, leng*64, count*64);
					Platform.DrawSprite(g2, Xpos, count*64);
					  
			break;
			case 'L': Lava.DrawSprite(g2, Xpos, count*64);
			break;
			default:
				Stone.DrawAnimatedSprite(g2, leng*64, count*64);
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
    	if(currentPos>10){
    			
    		currentPos-= 4;

    	}
    }

    if (key == KeyEvent.VK_RIGHT) {
    	if(currentPos<5120){
    		
    	currentPos+=4;

    	}
    }
    if (key == KeyEvent.VK_SPACE  ) {

        if (!engine.isJumping())  {
            System.out.println("start jjump");  	
        	engine.startJump();  }
    }

    if (key == KeyEvent.VK_DOWN) {
        System.out.println("Does not work");
    }

    

}


@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
}





