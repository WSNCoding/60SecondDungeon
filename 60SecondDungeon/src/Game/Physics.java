package Game;

public class Physics {

	private  int[] JumpInc = new int[] {0,-2,-4,-6,-8,-10,-12,-14,-16,-18,-19};
	private  int[] FallInc = new int[] {0,2,4,6,8,10,12,14,16,18,20};
	private  boolean jumping = false;
	private  boolean falling = false;
	private  int arrayPos = 0;
	private  long Time;
	
	
	
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
			if(timePassed > 250){
				arrayPos++;
				Time = System.currentTimeMillis();
				
				if(arrayPos>10){
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
			if(timePassed > 250){
				arrayPos++;
				Time = System.currentTimeMillis();//get time
				
				if(arrayPos>10){
					arrayPos=10;

				}
			}
			
		return FallInc[arrayPos]*(timeFloat/250);
			
			
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
