import java.awt.Color;

public class Constants {
	
	 final int arrayBrickStartX= 300,arrayBrickStartY= 50,paddleHeight=70,paddleWidth=20,brickWidth=23,brickHeight=23,brickMargine=2,stringX = 12,stringY = 200;
	
	 int ballX = 10, ballY = 100, paddleX=160, paddleY=100,brickRows = 2, brickCols = 2,numOfBricksRemaining = brickRows*brickCols,threadspeed=50,level=0,iter=0;
	 
	 Thread threadBreakout;
	 final int moveRightParam=5; // to the right

     final int moveLeftParam= -5; //to the left

     final int moveUpwards=-5; // upward

     final int moveDonwards= 5; // down

     int frameWidth = 10, frameHeight = 10; // Width and height of the ball
     int [][]arrayBrick = new int[4][4];
     boolean isArrayBrickInitialized = false;
	 boolean isGameRunning, isGameOver;
	 boolean isDirectionRight=false;
     boolean isDirectionUp=false;
     boolean paddleup=false,paddledown=false;
     String levelString;

}
