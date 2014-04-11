import javax.swing.*;

import java.awt.*;

import java.awt.event.*;


public class Main extends JFrame {
	 private static final long serialVersionUID = 1L;
		
	private JPanel jContentPane = null;
    
	private PanelGame panel = null; // This is the panel of the game class
    
	public Main() // Main Constructor 
	{
		super("Breakout");
		
		initialize();
		
this.addKeyListener(new KeyAdapter() {
            
        	//Method for the key pressed
    
		public void keyPressed(KeyEvent evt) {
        		
                        formKeyPressed(evt);
                }

                // Method for the key released

                public void keyReleased(KeyEvent evt) {

                        formKeyReleased(evt);

                }

        });
	}
	
	 private void formKeyPressed(KeyEvent evt)

     {

             panel.keyPressed(evt);

     }

     

     //      Here i'm stating the method that will send the key released to the game class

     private void formKeyReleased(KeyEvent evt)

     {

             panel.keyReleased(evt);

     }
	private JPanel getJContentPane() {

        if (jContentPane == null) {

                jContentPane = new JPanel();

                jContentPane.setLayout(new BorderLayout());

                jContentPane.add(getPanel(), BorderLayout.CENTER);

        }

        return jContentPane;

	}
	
	private PanelGame getPanel() 		// Get PanelGame Object 
	{

            if (panel == null) {

                    panel = new PanelGame(); // The panel is created

            }

            return panel;

    }
	
	private void initialize() 	// Window display setup
	{
		    
		//JPanel panelMain = new JPanel(new BorderLayout());
        this.setResizable(false);

        this.setBounds(new Rectangle(450, 200, 250, 250)); // Position on the desktop
        this.setMinimumSize(new Dimension(450,250)); // this.setMinimumSize(new Dimension(450, 450));
        this.setMaximumSize(new Dimension(450, 250));
        this.setContentPane(getJContentPane());
        this.setTitle("BreakOut Game");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		this.addWindowListener(new WindowListener() {
            public void windowClosed(WindowEvent we) {
               // System.out.println("Window close event occur");
              }
            public void windowActivated(WindowEvent we) {
                //System.out.println("Window Activated");
            }
            public void windowClosing(WindowEvent we) {
             //   System.out.println("Window Closing");
            }
            public void windowDeactivated(WindowEvent we) {
               // System.out.println("Window Deactivated");
            }
            public void windowDeiconified(WindowEvent we) {
                // System.out.println("Window Deiconified");
            }
            public void windowIconified(WindowEvent we) {
                // System.out.println("Window Iconified");
            }
            public void windowOpened(WindowEvent we) {
              //  System.out.println("Window Opened");
            }
        });*/
		
		
	}

	
	
	public static void main(String[] args) {

        // TODO Auto-generated method stub

        SwingUtilities.invokeLater(new Runnable() {

                public void run() {

                        Main thisClass = new Main();

                        thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                        thisClass.setVisible(true);

                }

        });

}
}