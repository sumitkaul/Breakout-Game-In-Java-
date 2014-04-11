import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;


/* Main game panel*/

public class PanelGame extends JPanel implements Runnable 
{ 
	Constants constant = new Constants();		// declares all constant in class Constant 
	
     private void initializeBrickArray()			// initialize the main brick array. 
	 {
    	 for(int i = 0 ; i < constant.brickRows ; i++)
		 {
			 for(int j = 0 ; j < constant.brickCols ; j++)
			 {
				 constant.arrayBrick[i][j] = 1; 	//set default to 1 
			 }			
		 }
	}
     
	 public PanelGame()
	 {
		 constant.isGameRunning=true;		// true when game starts
		 constant.threadBreakout= new Thread(this);		
		 constant.threadBreakout.start();		
	 }
	 
	  public void paintComponent(Graphics graphic)
	  {
		  super.paintComponent(graphic);
          
		  /* setting up the background */
		  
		  graphic.setColor(Color.white);
          graphic.fillRect(0, 0,this.getWidth(),this.getHeight());
        
          // Draw ball and ball characteristics 
          int offset, red=255;
          int tempVarible= (400-constant.ballX)*(400-constant.ballX)+(105-constant.ballY)*(105-constant.ballY);
  		  offset= (int)Math.sqrt(tempVarible);
   		  if(offset>255)
  			  red=255;
  		  if(offset<255)
  			  red=offset;
        
  		  graphic.setColor(new Color(red,0,0));
  		  graphic.fillOval(constant.ballX, constant.ballY, 10 , 10);       

          // Draw Paddle
          graphic.setColor(Color.black);
          graphic.fillRect(constant.paddleX, constant.paddleY, constant.paddleWidth, constant.paddleHeight);
          
          // Draw Brick stack
          if(constant.isArrayBrickInitialized == false)
          {
        	  initializeBrickArray();		// set default value to 1
        	  constant.isArrayBrickInitialized = true;
          }
          
          drawBricks(graphic);	// draw brick stack 
          // display Level
          constant.levelString = Integer.toString(constant.level);
          graphic.drawString("level :"+constant.levelString, constant.stringX, constant.stringY);
          
       if(constant.isGameOver)
       {
    	   graphic.setColor(Color.cyan);
           graphic.fillRect(0, 0,this.getWidth(),this.getHeight());
    	   Font big = new Font("SansSerif", Font.BOLD, 48);
    	   			graphic.setFont(big);
    	   			if(constant.iter%2==1)
    	   			graphic.setColor(new Color(255,255,150));
    	   			else
    	   			graphic.setColor(new Color(0,255,0));	
    	         	graphic.drawString("Game Over", 100, 125	);
    	         	constant.isGameRunning = false;
    	         	constant.iter++;
    	         	
       }
                   
	  }
	  
	  private void drawBricks(Graphics graphic)		// draw brick stack
      {
		 int arrayBrickX= constant.arrayBrickStartX;
		 int arrayBrickY= constant.arrayBrickStartY;
		  
		  for(int i = 0 ; i < constant.brickRows ; i++)
			 {
				 for(int j = 0 ; j < constant.brickCols ; j++)
				 {
					 if(constant.arrayBrick[i][j] == 1)			// If brick-ball collision has not happened
					 {
						 graphic.setColor(Color.blue);
						 graphic.fillRect(arrayBrickX, arrayBrickY,  constant.brickWidth, constant.brickHeight);
					 }
					arrayBrickX +=  constant.brickWidth+ constant.brickMargine;		// update X coordinate for next brick
					 
				 }			
				arrayBrickY +=  constant.brickHeight+ constant.brickMargine;	// update Y coordinate for next brick
				arrayBrickX -= constant.brickCols*( constant.brickWidth+ constant.brickMargine);	// reset X coordinate for next line of brick
			 }
      }
	  
	  /* Update the ball location and redraw */ 
	  private void drawBall(int ballNewLocationX, int ballNewLocationY)
	   {
              constant.ballX= ballNewLocationX; 
              constant.ballY= ballNewLocationY; 

              this.constant.frameWidth=this.getWidth(); 
              this.constant.frameHeight=this.getHeight(); 

              repaint();
      }
	   
	  /*For Handing key Pressed events for paddle*/
	  public void keyPressed(KeyEvent evt)
      {
		  if(evt.getKeyCode()== KeyEvent.VK_UP)
                constant.paddleup=true;
          else if (evt.getKeyCode()== KeyEvent.VK_DOWN)
                          constant.paddledown=true;
      }
      
	  
      /*For Handing key Pressed events for paddle*/
  	  public void keyReleased(KeyEvent evt)
      {
              if(evt.getKeyCode()== KeyEvent.VK_UP)
                          constant.paddleup=false;
                  else if (evt.getKeyCode()== KeyEvent.VK_DOWN)
                          constant.paddledown=false;
      }
	  
      /*For Handling the paddle*/
       private void paddleMovement()
       {
    	   
    	   if ( constant.paddledown == true && constant.paddleY <= (this.getHeight()-constant.paddleHeight))

               constant.paddleY +=  constant.moveDonwards;

    	   if ( constant.paddleup == true && constant.paddleY >= 0)

               constant.paddleY +=  constant.moveUpwards;
    	       paddleRepaint(constant.paddleX, constant.paddleY);
          
       }
       
       /*For Handling the paddle repainting*/
       private void paddleRepaint(int repaintCoordinateX, int repaintCoordinateY){

           this.constant.paddleX=repaintCoordinateX;
           
           this.constant.paddleY=repaintCoordinateY;

           repaint();

   }

       /* condition checking for ball-paddle collision  */
	   private void checkPaddleCollision(int ballX, int ballY)
	   {
		   // for diagonal collision
		   if((ballX>=constant.paddleX-2 && ballX<constant.paddleX && ballY>=constant.paddleY-2 && ballY<= constant.paddleY)||
				   (ballX>=constant.paddleX+ constant.paddleWidth && ballX<constant.paddleX+constant.paddleWidth+2 && ballY>=constant.paddleY-2 && ballY<=constant.paddleY)||
				   (ballX>=constant.paddleX-2 && ballX<constant.paddleX && ballY>=constant.paddleY+constant.paddleHeight && ballY<=constant.paddleY+constant.paddleHeight+2)||
				   (ballX>=constant.paddleX+constant.paddleWidth)&& ballX<=constant.paddleX+constant.paddleWidth+2 && ballY>=constant.paddleY+constant.paddleHeight && ballY<=constant.paddleY+constant.paddleHeight+2)
		   {
			   constant.isDirectionRight = !constant.isDirectionRight;
			   constant.isDirectionUp = !constant.isDirectionUp;

		  }
		   
		   // collision on the left side of the paddle
		   else if( constant.ballX >= constant.paddleX-5 && constant.ballX<constant.paddleX && constant.ballY>=constant.paddleY && constant.ballY<=(constant.paddleY+constant.paddleHeight))
			   constant.isDirectionRight=false;
		   
		   // collision on the right side of the paddle
		   else if(constant.ballX>=(constant.paddleX+constant.paddleWidth) && constant.ballX<(constant.paddleX+constant.paddleWidth+5) && constant.ballY>=constant.paddleY && constant.ballY<=(constant.paddleY+constant.paddleHeight))
			   constant.isDirectionRight=true;
			
		   // collision on the down side of the paddle
		   else if(constant.ballY >=(constant.paddleY+constant.paddleHeight) && constant.ballY <(constant.paddleY+constant.paddleHeight+5) && constant.ballX>=constant.paddleX && constant.ballX<=(constant.paddleX+constant.paddleWidth))
			   constant.isDirectionUp=false;
		
		   // collision on the upper side of the paddle
		   else if(constant.ballY>=(constant.paddleY-5) && constant.ballY<(constant.paddleY) && constant.ballX>=constant.paddleX && constant.ballX<=(constant.paddleX+constant.paddleWidth))
			   constant.isDirectionUp=true;
		}
	   
	   /* condition checking for ball-brick stack collision  */
	   
	   private void checkArrayBrickCollision(int ballX, int ballY)
	   {
		   for(int i = 0 ; i < constant.brickRows ; i++)
			 {
				 for(int j = 0 ; j < constant.brickCols ; j++)
				 {
					 if(constant.arrayBrick[i][j] == 1)		// If brick still exists
					 {
						 // calculate X coordinate of current brick
						 int brickX = constant.arrayBrickStartX + (j * (constant.brickWidth+ constant.brickMargine));
						
						 // calculate Y coordinate of current brick
						 int brickY = constant.arrayBrickStartY + (i * (constant.brickHeight+ constant.brickMargine));

						 // check for individual brick collision 
						 checkBrickCollision(constant.ballX, constant.ballY, brickX , brickY, i , j);
					 }
					 
				 }
			 }
	   }
	   
	   /* condition checking for ball individual brick collision  */
	   private void checkBrickCollision(int ballX, int ballY, int brickX , int brickY,int brickRowNum,int brickColNum)
	      {
		   // for diagonal collision
		   if((constant.ballX>=brickX-5 && constant.ballX<brickX && constant.ballY>=brickY-5 && constant.ballY<= brickY)||
				   (constant.ballX>=brickX+ constant.brickWidth && constant.ballX<brickX+constant.brickWidth+5 && constant.ballY>=brickY-5 && constant.ballY<=brickY)||
				   (constant.ballX>=brickX-5 && constant.ballX<brickX && constant.ballY>=brickY+constant.brickHeight && constant.ballY<=brickY+constant.brickHeight+5)||
				   (constant.ballX>=brickX+constant.brickWidth)&& constant.ballX<=brickX+constant.brickWidth+5 && constant.ballY>=brickY+constant.brickHeight && constant.ballY<=brickY+constant.brickHeight+5)
				   {
				   constant.isDirectionRight = !constant.isDirectionRight;		// Change the horizontal direction
				   constant.isDirectionUp = !constant.isDirectionUp;			// Change the vertical direction
				   constant.arrayBrick[brickRowNum][brickColNum] = 0;			// Update value in arrayBrick to 0, The brick does not exist anymore
				   numOfBricksDecrease();										// Update number of remaining bricks in panel

				   }
		   
		   // collision on the left side of the brick
		   else if( constant.ballX >= brickX-5 && constant.ballX<brickX && constant.ballY>=brickY && constant.ballY<=(brickY+constant.brickHeight))
		   {
			   constant.isDirectionRight=false;									// Change the horizontal direction
			   constant.arrayBrick[brickRowNum][brickColNum] = 0;				// Update value in arrayBrick to 0, The brick does not exist anymore
			   numOfBricksDecrease();											// Update number of remaining bricks in panel
		   }
		   
		   // collision on the right side of the brick
		   if(constant.ballX>=(brickX+constant.brickWidth) && constant.ballX<(brickX+constant.brickWidth+5) && constant.ballY>=brickY && constant.ballY<=(brickY+constant.brickHeight))
		   {
			   constant.isDirectionRight=true;									// Change the horizontal direction
			   constant.arrayBrick[brickRowNum][brickColNum] = 0;				// Update value in arrayBrick to 0, The brick does not exist anymore
			   numOfBricksDecrease();											// Update number of remaining bricks in panel
		   }
			
		   // collision on the down side of the brick
		   if(constant.ballY >=(brickY+constant.brickHeight) &&constant.ballY<(brickY+constant.brickHeight+5) && constant.ballX>=brickX && constant.ballX<=(brickX+constant.brickWidth))
		   {
			   constant.isDirectionUp=false;									// Change the vertical direction
			   constant.arrayBrick[brickRowNum][brickColNum] = 0;				// Update value in arrayBrick to 0, The brick does not exist anymore
			   numOfBricksDecrease();											// Update number of remaining bricks in panel
			}

		   // collision on the down side of the brick
		   if(constant.ballY>=(brickY-5) && constant.ballY<(brickY) && constant.ballX>=brickX && constant.ballX<=(brickX+constant.brickWidth))
		   {
			   constant.isDirectionUp=true;										// Change the vertical direction
			   constant.arrayBrick[brickRowNum][brickColNum] = 0;				// Update value in arrayBrick to 0, The brick does not exist anymore
			   numOfBricksDecrease();											// Update number of remaining bricks in panel
			}
		}
	   
	/* Update number of remaining bricks in panel */
	   private void numOfBricksDecrease()
	   {
		   constant.numOfBricksRemaining--;
		   if(constant.numOfBricksRemaining==0)			// No more bricks left for a particular level
		   {   
			  levelUp();
		   }
	   }
	   
	   /*Increase the level of the game once all bricks in the panel got hit*/
	   private void levelUp()
	      {
		   constant.ballX=10;
		   constant.ballY=100;
		   try{
		   Thread.sleep(5);
		   }
		   catch(InterruptedException ex)
		   { 
		   }
		   constant.threadspeed= constant.threadspeed-10;
		  
		   if(constant.level%2==0)
		   {
			   constant.brickRows++;
			   constant.brickCols++;
		   }
		   initializeBrickArray();
		   constant.numOfBricksRemaining = constant.brickRows*constant.brickCols;
		   constant.level++;
		   if(constant.level==4)
		   {
			   initializeBrickArray();
			   constant.isGameOver = true;
		   }
		   else
			   {
			   		Frame f = new Frame( "Next Level" );
			   		JOptionPane.showMessageDialog(f, "Enjoy "+constant.level+" the next level. It will be faster");
			   }
		    }
	   
	   
	   /* update the location of ball and check for ball-frame collision*/
	   private void updateBallLocation(int ballFrameX, int ballFrameY)
	      {
		   		constant.ballX = ballFrameX;
		   		constant.ballY= ballFrameY;
		   		
		   		if (constant.isDirectionRight) 
		   		{
                  // Move ball to right
                  constant.ballX += constant.moveRightParam;

                  // collision on the left side of the frame
        		  if (constant.ballX >= (constant.frameWidth - 10))
                	  constant.isDirectionRight= false;
		   		}
		   		else
		   		{
		   			// Move ball to left
		   			constant.ballX += constant.moveLeftParam;
		   			
		   		  // collision on the right side of the frame
		  		  if ( constant.ballX <= 0)
                	   constant.isDirectionRight =  true;
		   		}
		   		
		   		if (constant.isDirectionUp) 
		   		{ 
		   			// Move ball to Downwards
		   			constant.ballY+= constant.moveUpwards;
		   		
		   			// collision on the top side of the frame
			  		if (constant.ballY<= 0)
	            	   constant.isDirectionUp= false;
			   		}
	           else
	           {
	                 // Move ball Upwards
	                  constant.ballY += constant.moveDonwards;
	                 
	                 // collision on the down side of the frame
				  	 if (constant.ballY >= (constant.frameHeight - 10))
	                	  constant.isDirectionUp =  true;
	           	}
	      }
		 
	  public void run() 
	  {
          while(true)
          {
        	  if(constant.isGameRunning)
        	  {
        		  updateBallLocation(constant.ballX, constant.ballY);
        		  drawBall(constant.ballX, constant.ballY);
        		  checkPaddleCollision(constant.ballX, constant.ballY);
        		  checkArrayBrickCollision(constant.ballX, constant.ballY);
        		  // drawBricks(graphic);
        		  try 
                  {
        			  Thread.sleep(constant.threadspeed);
                  }
                  catch(InterruptedException ex)
                  {
                  }
                  paddleMovement();
        	  }
          }
	  }
}

        