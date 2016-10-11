package Game;
import java.awt.*;


import java.math.*;
import java.util.Random;
public class Generate {
static int Width= 100;
int Height;
static Random rand = new Random();

public static String[] CreateWorld(){
	String[] World;
	World = new String[8];
	
	//lava
	boolean drawLava = false; 
	int lavaRemaining = 0;
	int PlatformY = 3;
	int PlatformWidth = 0;
	int GapWidth = 0;
	boolean DrawGap = true;
	for(int a = 0;a<8;a++){
		World[a] = new String();
	}
	
	
	for(int i = 0; i <101; i++){
		if(DrawGap == true && GapWidth == 0){
		//Change Plaform hegiht (maybe)+ work out next platform
			if(PlatformY < 4){
				PlatformY += rand.nextInt(3)+1;}
				else if(PlatformY > 6){
					PlatformY += rand.nextInt(2) -3;
					} else  {
						PlatformY += rand.nextInt(3) + -3;
						}
			
			PlatformWidth = rand.nextInt(4) + 1;
			DrawGap = false;
		}//End of Platform Generator
		
		if(DrawGap == false && PlatformWidth == 0){
			DrawGap = true;
			GapWidth = rand.nextInt(3) + 1;}
	
	
	//create map/World
	World[0] += "D";	
	boolean drawn = false;
	for(int x = 1; x<7; x++){
			
			if(DrawGap == false && PlatformWidth != 0 && x== PlatformY){
				World[x] += "P";
				PlatformWidth--;
				drawn = true;
			}
			else {
				if(DrawGap == true && GapWidth > 0) GapWidth--;
			
			World[x] +=" ";
			}
		
		
	} //end platforms
	if (!drawn) {
		System.out.println("Error: platform lvl "+PlatformY);
	}
	//draw Lava
	if(drawLava == true && lavaRemaining != 0){
		World[7] += "L";
		lavaRemaining--;
	}
	else if(drawLava == true && lavaRemaining == 0){
		drawLava = false;
	}
	if(drawLava == false){
		for(int n = 0; n<rand.nextInt(2)+1;n++){
			World[7] += "D";
			
				
				
			
		}
		drawLava = true;
		lavaRemaining = rand.nextInt(4)+1;
	}
	}
	
	
	for(int b = 0; b < 8; b++){
		System.out.println(World[b]);
	}
	
	
	
	return World;
	
}


}

