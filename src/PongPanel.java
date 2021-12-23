import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;

public class PongPanel extends JPanel implements ActionListener, KeyListener {
	private final static Color BACKGROUND_COLOUR = Color.BLACK;
	private final static int TIMER_DELAY = 5;
	private static int BALL_MOVEMENT_SPEED = 1;
	private GameState gameState = GameState.Initialising; 
	private Ball ball;
	private Paddle paddleOne;
	private Paddle paddleTwo;

	public PongPanel() {
		setBackground(BACKGROUND_COLOUR);
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
	}

	public void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddleOne = new Paddle(Player.One, getWidth(), getHeight());
		paddleTwo = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	public void moveObject(Sprite sprite) {
		sprite.setXPosition(sprite.getXPosition() + sprite.getXVelocity(), getWidth());
		sprite.setYPosition(sprite.getYPosition() + sprite.getYVelocity(), getHeight());
	}
	
	public void resetBall() {
		ball.resetToInitialPosition();
	}
	
	public void checkWallBounce() {
		if (ball.getYPosition() <= 0 || (ball.getYPosition() + ball.getHeight()) >= getHeight()) {
			ball.setYVelocity(-ball.getYVelocity());
		}

		if (ball.getXPosition() <= 0 || (ball.getXPosition() + ball.getWidth()) >= getWidth()) {
			resetBall();
		}
	}

	private void update() {
		switch(gameState) {
			case Initialising: {
				createObjects();
				ball.setXVelocity(1 * BALL_MOVEMENT_SPEED);
				ball.setYVelocity(1 * BALL_MOVEMENT_SPEED);
				gameState = GameState.Playing;
				break;
			}
			case Playing: {
				moveObject(ball);
				checkWallBounce();
				break;
			}
			case GameOver: {
				break;
			}
		}
	}

	private void paintSprite(Graphics g, Sprite sprite) {
		g.setColor(sprite.getColor());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if (gameState != GameState.Initialising) {
			paintSprite(g, ball);
			paintSprite(g, paddleOne);
			paintSprite(g, paddleTwo);
		}
	}

	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2d.dispose();
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		update();
		repaint();
	}
}