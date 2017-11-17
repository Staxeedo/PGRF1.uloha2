package cz.uhk.pgrf1.c03.madr.uloha2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import cz.uhk.pgrf1.c03.madr.uloha2.model.Line;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Polygon;
import cz.uhk.pgrf1.c03.madr.uloha2.render.LineRenderer;
import cz.uhk.pgrf1.c03.madr.uloha2.render.PolygonRenderer;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 * drzenim a tazenim leveho tlacitka krelim primku a pridavam body polygonu
 * drzenim a tazenim praveho tlacitka vykresluju kruznici, po spusteni kreslim
 * vysec pomoci tazeni mysi
 * @author PGRF FIM UHK
 * @version 2017
 */
public class CanvasMouse {

	private JPanel panel;
	Boolean outOfField = false;
	private BufferedImage img;
	// Primky, pro zadavani
	Line tempLine = new Line();
	Line tempLine2 = new Line();

	Polygon pol = new Polygon();
	Polygon polCutter = new Polygon(new Point(29, 24), new Point(104, 598), new Point(476, 481));

	/*
	 * Promenna slouzici pro zapamatovani modu 0 zacinam kreslit novy polygon 1
	 * kresleni polygonu
	 */
	int mode = 0;

	public CanvasMouse(int width, int height) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				present(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		panel.addMouseListener(new MouseAdapter() {
			Point p;

			public void mousePressed(MouseEvent e) {
				p = new Point(e.getX(), e.getY());

				// Leve tlacitko
				if (e.getButton() == MouseEvent.BUTTON1) {

					mode = 1;
					/*
					 * Pokud jeste nemame prvni bod polygonu tak ho pridame a nastavime ho jako
					 * prvni bod primky, jinak pocatecni bod primky je posledni bod polygonu
					 */
					if (pol.getSize() == 0) {
						pol.add(p);
						tempLine.setFirst(p);
					} else {

						tempLine.setFirst(pol.getLast());
					}
				}

				// Prave tlacitko
				if (e.getButton() == MouseEvent.BUTTON3) {

					mode = 2;
					tempLine.setFirst(polCutter.getLast());

				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				PolygonRenderer pren = new PolygonRenderer(img);
				if (e.getButton() == MouseEvent.BUTTON1) {
					// Pokud chci vykreslit usecku se stejnym bodem (2x kliknu na jedno misto)
					if (tempLine.getLast() == null)
						tempLine.setLast(tempLine.getFirst());
					mode = 0;
					clear();
					panel.repaint();
					// pokud jsem neuvolnil tlacitko mimo platno, tak pridam bod polygonu
					if (outOfField) {
						outOfField = false;

					} else {
						pol.add(tempLine.getLast());
					}

					pren.draw(pol,0xFFFF00);
					pren.draw(polCutter,0x0000FF);
					// Pokud se na platne nachazi vysec, tak ji vykreslim (bylo vypocitano r)

				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					
					mode = 0;
					clear();
					panel.repaint();
					if (outOfField) {
						outOfField = false;

					} else {
						polCutter.add(tempLine.getLast());
					}

				}
				
				
				pren.draw(pol,0xFFFF00);
				pren.draw(polCutter,0x0000FF);
			
			

			}
		}

		);

		/*
		 * Listener pro to pohyby mysi
		 */
		panel.addMouseMotionListener(new MouseAdapter() {
			LineRenderer lren = new LineRenderer(img);
			PolygonRenderer pren = new PolygonRenderer(img);

			@Override
			public void mouseDragged(MouseEvent e) {

				Point p = new Point(e.getX(), e.getY());

				tempLine.setLast(p);
				clear();
				if (mode == 1) {

					if (pol.getSize() >= 2) {
						tempLine2.setLast(p);
						tempLine2.setFirst(pol.getPoint(0));
						pren.draw(pol,0xFFFF00);
						pren.draw(polCutter,0x0000FF);

						try {
							lren.draw(tempLine, 0xFF0000);
							lren.draw(tempLine2, 0xFF0000);
							// Bod je zpet na platne
							if (outOfField == true) {
								outOfField = false;
							}

						} catch (ArrayIndexOutOfBoundsException exception) {
							outOfField = true;
							clear();
							pren.draw(pol,0xFFFF00);
							pren.draw(polCutter,0x0000FF);
						}
					} 
					
					
					
					else {
						// pro vykresleni prvni cary
						pren.draw(polCutter,0x0000FF);
						try {
							lren.draw(tempLine, 0xFF0000);
							if (outOfField == true) {
								outOfField = false;
							}
						} catch (ArrayIndexOutOfBoundsException exception) {
							outOfField = true;
							clear();
							pren.draw(pol,0xFFFF00);
							pren.draw(polCutter,0x0000FF);
						}
						
						
						
						
					}

				}
				else if(mode==2)
				{
					System.out.println("2");
					tempLine2.setLast(p);
					tempLine2.setFirst(polCutter.getPoint(0));
					pren.draw(pol,0xFFFF00);
					pren.draw(polCutter,0x0000FF);

					try {
						lren.draw(tempLine, 0xFF0000);
						lren.draw(tempLine2, 0xFF0000);
						// Bod je zpet na platne
						if (outOfField == true) {
							outOfField = false;
						}

					} catch (ArrayIndexOutOfBoundsException exception) {
						outOfField = true;
						clear();
						pren.draw(pol,0xFFFF00);
						pren.draw(polCutter,0x0000FF);
					}
					
				}

				panel.repaint();

			}

		});

	}

	public void clear() {
		Graphics gr = img.getGraphics();
		gr.setColor(new Color(0x2f2f2f));
		gr.fillRect(0, 0, img.getWidth(), img.getHeight());
	}

	public void present(Graphics graphics) {
		graphics.drawImage(img, 0, 0, null);
	}

	public void start() {
		clear();
		img.getGraphics().drawString("Use mouse buttons Left for polygon, Right for Circle Sector", 5,
				img.getHeight() - 5);
		PolygonRenderer pren = new PolygonRenderer(img);
		pren.draw(polCutter,0x0000FF);
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());

	}

}
