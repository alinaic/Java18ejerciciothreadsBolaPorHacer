import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Ventana extends JFrame implements Runnable {

	private Bola bola = new Bola(50, 50);
	private Zona zona = new Zona(700, 300, 100, 200, Color.RED);
	boolean hiloActivo = true;
	Thread hilo = new Thread(this);

	public Ventana() {

		this.setSize(800, 600);
		this.setLocation(50, 50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

		// vamos a decirle a la ventana que es lo que va a dibujar:
		this.setContentPane(new JComponent() {
			public void paint(Graphics g) {
				bola.dibujar(g);
				zona.dibujar(g);

			}
		}

		);// vamos a indicar que hacer cuando se pulsen las teclas del teclado

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());
				if (e.getKeyCode() == 39) {
					bola.direccionBola = Direcciones.DERECHA;

				} else if (e.getKeyCode() == 40) {
					bola.direccionBola = Direcciones.ABAJO;

				} else if (e.getKeyCode() == 37) {
					bola.direccionBola = Direcciones.IZQUERDA;

				} else if (e.getKeyCode() == 38) {
					bola.direccionBola = Direcciones.ARRIBA;

				}

			}
		});

		// lanzamos el hilo:

		hilo.start();

	}// end ventana

	@Override
	public void run() {
		while (hiloActivo) {
			//System.out.println("x bola: " + bola.getX() + " y: " + bola.getY());
			bola.mover();
			if (zona.detectarColision(bola)) {

				JOptionPane.showConfirmDialog(null, "Has ganado");

				hiloActivo = false;

			} else if (bola.getX() > this.getWidth()

			|| bola.getY() > this.getHeight()) {

				JOptionPane.showConfirmDialog(null, "Lo siento has perdido");
				hiloActivo = false;

			}
			repaint();
			try {
				Thread.sleep(10);

			} catch (InterruptedException e) {
				System.out.println("hilo interrumpido");
			}
		}// end while

		// public void paint(Graphics g) {
		// super.paint(g);
		// bola.dibujar(g);{

	}// end run

}// end class
