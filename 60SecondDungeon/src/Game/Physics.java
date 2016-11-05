package Game;

public class Physics {

	private  int[] JumpInc = new int[] {0,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-0,-0,-0,-2,-2,-2,-2,-0,0,0,0};
	private  int[] FallInc = new int[] {0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
	private  boolean jumping = false;
	private  boolean falling = false;
	private  int arrayPos = 0;
	private  long Time;
	
	gamePanel bob;
	
	public  void startJump() {
		if(jumping){
			return;
		}
		Time = System.currentTimeMillis();
		arrayPos = 0;
		jumping = true;
	}
	public  boolean isJumping(){
		return jumping;
	}
	public boolean isFalling(){
		return falling;
	}
	public  float getGravity(){
		if(jumping){
			long currentTime = System.currentTimeMillis();
			long timePassed = currentTime - Time;
			float timeFloat = timePassed;
			if(timePassed > 50){
				arrayPos++;
				Time = System.currentTimeMillis();
				
				if(arrayPos>20){
					jumping = false;
					
					return 0;
			
				}
			//	return 0;
			
			}else {
				return JumpInc[arrayPos]*(timeFloat/100);
			}
			
		}//end of jumping
		if(falling){// begin falling
			long currentTime = System.currentTimeMillis();
			long timePassed = currentTime - Time;
			float timeFloat = timePassed;
			if(timePassed > 50){
				arrayPos++;
				Time = System.currentTimeMillis();//get time
				
				if(arrayPos>10){
					arrayPos=10;

				}
			}
			
		return FallInc[arrayPos]*(timeFloat/100);
			
			
		}//end of jumping		
		
		
		
		return 0;
	}
	public  void isFloor(boolean floor){
		if((jumping) && (floor == false)){
			return;
		}
		
		
		if(floor){
			falling = false;
			return;
		
		}
		if((falling == false)&&(floor == false)){
			arrayPos=0;
			falling = true;
			Time = System.currentTimeMillis();
		}
	}
	
}
