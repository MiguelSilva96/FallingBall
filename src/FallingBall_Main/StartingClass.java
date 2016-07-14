package FallingBall_Main;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;
import java.net.URL;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private Bola bola1;
	private URL base; // para importar imagens para vari�veis
	// image e second: vari�veis para doubble buffering
	private Image image, character, picos1, picos2, barra;
	private Graphics second;
	private boolean vezum = true;
	public Plataforma plat1, plat2, plat3, plat4;
	public long score = 0;
	private boolean tecla_direita = false;
	private boolean tecla_esquerda = false;
	private boolean life = true;
	private boolean reset = false;

	@Override
	public void init() {

		// Configura��o da janela
		setSize(480, 800);
		setBackground(Color.YELLOW);
		setFocusable(true); // Muda automaticamente o input para a janela do
							// jogo

		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Falling Ball");
		// frame.setResizable(false);
		// frame.setLocationRelativeTo(null);//centra a janela

		addKeyListener(this);

		// definir a base do url para importar as imagens para gr�ficos
		try {
			base = getCodeBase();
		} catch (Exception e) {

		}

		// Definir Sprites
		character = getImage(base, "data/bola_direita.png");
		picos1 = getImage(base, "data/picos1.png");
		picos2 = getImage(base, "data/picos2.png");
		barra = getImage(base, "data/barra_dim.png");

	}

	@Override
	public void start() {
		Thread jogo = new Thread(this);
		bola1 = new Bola();
		plat1 = new Plataforma();
		plat2 = new Plataforma();
		plat3 = new Plataforma();
		plat4 = new Plataforma();
		jogo.start();

	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void run() {

		while (true) {
			if (life==true) {
				bola1.update();

				if (bola1.getCenterY() < 735 && bola1.getCenterY() > 68) {
					plat1.update(230);
					plat2.update(420);
					plat3.update(614);
					plat4.update(801);
				}

				if (bola1.getCenterY() <= plat1.getCenterY() - 70
						&& bola1.getCenterY() >= plat1.getCenterY() - 75
						&& bola1.getCenterX() >= plat1.getCenterX() - 65
						&& bola1.getCenterX() <= plat1.getCenterX() + 65) {
					bola1.setSpeedY(-5);
					addscore();

				} else if (bola1.getCenterY() <= plat2.getCenterY() - 70
						&& bola1.getCenterY() >= plat2.getCenterY() - 75
						&& bola1.getCenterX() >= plat2.getCenterX() - 65
						&& bola1.getCenterX() <= plat2.getCenterX() + 65) {
					bola1.setSpeedY(-5);
					addscore();
				} else if (bola1.getCenterY() <= plat3.getCenterY() - 70
						&& bola1.getCenterY() >= plat3.getCenterY() - 75
						&& bola1.getCenterX() >= plat3.getCenterX() - 65
						&& bola1.getCenterX() <= plat3.getCenterX() + 65) {
					bola1.setSpeedY(-5);
					addscore();
				} else if (bola1.getCenterY() <= plat4.getCenterY() - 70
						&& bola1.getCenterY() >= plat4.getCenterY() - 75
						&& bola1.getCenterX() >= plat4.getCenterX() - 65
						&& bola1.getCenterX() <= plat4.getCenterX() + 65) {
					bola1.setSpeedY(-5);
					addscore();
				} else {
					bola1.setSpeedY(5);
					vezum = true;
				}

				movimento();
			}else{
				if(reset==true){
					life = true;
					score = 0;
					vezum = true;
					plat1.setVez_1(true);
					plat2.setVez_1(true);
					plat3.setVez_1(true);
					plat4.setVez_1(true);
					bola1.setCenterX(240);
					bola1.setCenterY(200);
					bola1.setSpeedY(5);
				}
			}

			repaint();

			try {
				Thread.sleep(22);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Key Listeners
	@Override
	public void keyPressed(KeyEvent tecla) {
		/*
		 * if (bola1.getCenterY() < 735 && bola1.getCenterY()>68) { switch
		 * (tecla.getKeyCode()) { case KeyEvent.VK_LEFT: character =
		 * getImage(base, "data/bola_esquerda - C�pia.png");
		 * bola1.ir_esquerda();
		 * 
		 * break; case KeyEvent.VK_RIGHT: character = getImage(base,
		 * "data/bola_direita.png");
		 * 
		 * bola1.ir_direita();
		 * 
		 * break; }
		 * 
		 * }
		 */

		switch (tecla.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			tecla_esquerda = true;
			break;
		case KeyEvent.VK_RIGHT:
			tecla_direita = true;
			break;
		case KeyEvent.VK_SPACE:
			reset=true;
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent tecla) {
		switch (tecla.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			tecla_esquerda = false;
			// bola1.stop();
			break;
		case KeyEvent.VK_RIGHT:
			tecla_direita = false;
			// bola1.stop();
			break;
		case KeyEvent.VK_SPACE:
			reset=false;
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent tecla) {

	}

	@Override
	public void update(Graphics g) { // M�todo para double buffering
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		// g.drawImage(getImage(base, "data/BackgroundSkyFinalLook.jpg"), 0, 0,
		// this); // �nica linha de c�digo referente ao background. Este deve
		// ser substitu�do porque n�o foi feito por mim
		if (life == true) {
			g.drawImage(barra, plat1.getCenterX() - 50,
					plat1.getCenterY() - 22, this);
			g.drawImage(barra, plat2.getCenterX() - 50,
					plat2.getCenterY() - 22, this);
			g.drawImage(barra, plat3.getCenterX() - 50,
					plat3.getCenterY() - 22, this);
			g.drawImage(barra, plat4.getCenterX() - 50,
					plat4.getCenterY() - 22, this);
			g.drawImage(picos1, 0, 770, this);
			g.drawImage(picos2, 0, 0, this);
			g.drawImage(character, bola1.getCenterX() - 48,
					bola1.getCenterY() - 48, this);
		} else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 480, 800);
			g.setColor(Color.WHITE);
			g.drawString("Tiveste " + score +" pontos!", 200, 400);
			g.drawString("(Pressiona espa�o para jogar outra vez)", 135, 600);

		}
	}

	public void addscore() {
		if (vezum) {
			score++;
			vezum = false;
		}

	}

	public void movimento() {
		if (bola1.getCenterY() < 735 && bola1.getCenterY() > 68) {
			if (tecla_esquerda == true && tecla_direita == true) {

			} else if (tecla_esquerda == true) {
				character = getImage(base, "data/bola_esquerda.png");
				bola1.ir_esquerda();
			} else if (tecla_direita == true) {
				character = getImage(base, "data/bola_direita.png");
				bola1.ir_direita();
			} else if (tecla_esquerda == false && tecla_direita == false) {
				bola1.stop();
			}
		} else {
			bola1.stop();

			if (bola1.getCenterY() <= 68) {
				bola1.setSpeedY(0);
				bola1.setCenterY(68);
			}
			
			life=false;

		}
	}
}
