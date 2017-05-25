package ep2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;

public final class Map extends JPanel implements ActionListener {

    private final int SPACESHIP_X = 220;
    private final int SPACESHIP_Y = 430;
   
    private final Timer timer_map;
    private final Timer timer_aliens;
    
    private final Image background;
    private final Spaceship spaceship;
    private final ArrayList<Aliens> aliens = new ArrayList<>();;

    public Map() {
        
        addKeyListener(new KeyListerner());
        
        setFocusable(true);
        setDoubleBuffered(true);
        ImageIcon image = new ImageIcon("images/space.jpg");
        
        this.background = image.getImage();

        spaceship = new Spaceship(SPACESHIP_X, SPACESHIP_Y);
        
        

        timer_map = new Timer(Game.getDelay(), this);
        timer_map.start();
        
        
        timer_aliens = new Timer(500, new ActionListener() {
        public void actionPerformed(ActionEvent evt) {

    aliens.add(new Aliens(0, 0));
     
    }    
});
        timer_aliens.start();
                            
    }
   
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(this.background, 0, 0, null);
       
        draw(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void draw(Graphics g) {
               
        // Draw spaceship
        g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
        
       for (int i = 0; i < aliens.size(); i++) {
				Aliens in = aliens.get(i);
				g.drawImage(in.getImage(), in.getX(), in.getY(), this);
			}
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
       
        updateSpaceship();
        //checarColisoes();
        
      
    for (int i = 0; i < aliens.size(); i++) {
			Aliens in = aliens.get(i);

			if (in.isVisible()) {
				in.move();
			} else
                            aliens.remove(i);
                            
			
			
		}
      
        checarColisoes();
        repaint();
    }
    
    private void dranMissionAccomplished(Graphics g) {

        String message = "MISSION ACCOMPLISHED";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }
    
    private void drawGameOver(Graphics g) {

        String message = "Game Over";
        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metric = getFontMetrics(font);

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(message, (Game.getWidth() - metric.stringWidth(message)) / 2, Game.getHeight() / 2);
    }
    
    private void updateSpaceship() {
        spaceship.move();
       
       
    }
    
    public void checarColisoes() {
		Rectangle formaNave = spaceship.getBounds();
		Rectangle formaInimigo;
		

		for (int i = 0; i < aliens.size(); i++) {

			Aliens tempInimigo = aliens.get(i);
			formaInimigo = tempInimigo.getBounds();
                        Aliens in = aliens.get(i);

			if (formaNave.intersects(formaInimigo)) {
				//spaceship.setVisible(false);
				tempInimigo.setVisible(false);                             
                                in.explode();
                                
				//emJogo = false;
			}
                        
		}
    }
    
    private class KeyListerner extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            spaceship.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            spaceship.keyReleased(e);
        }

        
    }
}