package cz.uhk.pgrf1.c03.madr.uloha2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import cz.uhk.pgrf1.c03.madr.uloha2.clip.Clipper;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Line;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Polygon;
import cz.uhk.pgrf1.c03.madr.uloha2.render.LineRenderer;
import cz.uhk.pgrf1.c03.madr.uloha2.render.PolygonRenderer;
import cz.uhk.pgrf1.c03.madr.uloha2.render.ScanLineRenderer;
import cz.uhk.pgrf1.c03.madr.uloha2.render.SeedFillRenderer;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi drzenim a
 * tazenim leveho tlacitka krelim primku a pridavam body polygonu drzenim a
 * tazenim praveho tlacitka vykresluju kruznici, po spusteni kreslim vysec
 * pomoci tazeni mysi
 * 
 * @author PGRF FIM UHK
 * @version 2017
 */
public class CanvasMouse {
	// barvy hranic polygonu
	public static int polCutterColor = 0x0000FF;
	public static int polColor = 0xFFFF00;

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
	int step = 0;
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

		// Ovladaci panel
		JPanel pnl = new JPanel();
		frame.add(pnl, BorderLayout.NORTH);
		JLabel lbl = new JLabel("Left Button Mode: ");
		JRadioButton polygonButton = new JRadioButton("Draw Polygons");
		polygonButton.addActionListener(e -> setMode(0));
		polygonButton.setSelected(true);
		JRadioButton seedFillButton = new JRadioButton("Fill with SeedFill");
		JRadioButton seedFillPatternButton = new JRadioButton("Fill with SeedFill (pattern)");
		seedFillPatternButton.addActionListener(e -> setMode(2));
		seedFillButton.addActionListener(e -> setMode(3));
		JRadioButton scanLineButton = new JRadioButton("Fill drawn Polygon with ScanLine");
		scanLineButton.addActionListener(e -> setMode(1));
		ButtonGroup group = new ButtonGroup();
		group.add(polygonButton);
		group.add(seedFillButton);
		group.add(seedFillPatternButton);
		group.add(scanLineButton);
		pnl.add(lbl);
		pnl.add(polygonButton);
		pnl.add(scanLineButton);
		pnl.add(seedFillButton);
		pnl.add(seedFillPatternButton);

		// Konec ovladaciho panelu
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		panel.addMouseListener(new MouseAdapter() {
			Point p;

			public void mousePressed(MouseEvent e) {
				p = new Point(e.getX(), e.getY());
				if (mode == 0) {
					// Leve tlacitko
					if (e.getButton() == MouseEvent.BUTTON1) {

						step = 1;
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

						step = 2;
						tempLine.setFirst(polCutter.getLast());

					}
				}

				else {
					if (e.getButton() == MouseEvent.BUTTON1) {
						SeedFillRenderer sfren = new SeedFillRenderer(img);
						ScanLineRenderer slren = new ScanLineRenderer(img);
						switch (mode) {
						case 1:
							if (pol.getSize() > 2) {
								//slren.fill(pol);
								Clipper clip = new Clipper(polCutter);
								
								Polygon clippedPol = new Polygon(clip.clipPoly(pol));
									slren.fill(clippedPol);
								
								
								panel.repaint();
							}
							break;
						case 2:
							int[][] pattern = { { 0xFF0000, 0xFF0000, 0xFF0000 }, { 0xFF0000, 0xFF0000, 0xFF0000 },
									{ 0xFF0000, 0xFF0000, 0xFF0000 }, { 0xFF0000, 0xFF0000, 0xFF0000 },
									{ 0xFFFFFF, 0xFFFFFF, 0xFFFFFF }, { 0xFFFFFF, 0xFFFFFF, 0xFFFFFF },
									{ 0xFFFFFF, 0xFFFFFF, 0xFFFFFF }, { 0xFFFFFF, 0xFFFFFF, 0xFFFFFF }

							};
							sfren.draw(p, img.getRGB((int) p.getX(), (int) p.getY()), pattern);
							panel.repaint();
							break;
						case 3:
							int[][] color = { { 0xFFFFFA }

							};
							System.out.println(color.length);
							sfren.draw(p, img.getRGB((int) p.getX(), (int) p.getY()), color);
							panel.repaint();
							break;
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

				PolygonRenderer pren = new PolygonRenderer(img);
				if (mode == 0) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						// Pokud chci vykreslit usecku se stejnym bodem (2x kliknu na jedno misto)
						if (tempLine.getLast() == null)
							tempLine.setLast(tempLine.getFirst());
						step = 0;
						clear();
						panel.repaint();
						// pokud jsem neuvolnil tlacitko mimo platno, tak pridam bod polygonu
						if (outOfField) {
							outOfField = false;

						} else {
							pol.add(tempLine.getLast());
						}

						pren.draw(pol, polColor);
						pren.draw(polCutter, polCutterColor);
						// Pokud se na platne nachazi vysec, tak ji vykreslim (bylo vypocitano r)

					}

					if (e.getButton() == MouseEvent.BUTTON3) {

						step = 0;
						clear();
						panel.repaint();
						if (outOfField) {
							outOfField = false;

						} else {
							polCutter.add(tempLine.getLast());
						}

					}

					pren.draw(pol, polColor);
					pren.draw(polCutter, polCutterColor);

				}
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
				if (mode == 0) {
					Point p = new Point(e.getX(), e.getY());

					tempLine.setLast(p);
					clear();
					if (step == 1) {

						if (pol.getSize() >= 2) {
							tempLine2.setLast(p);
							tempLine2.setFirst(pol.getPoint(0));
							pren.draw(pol, polColor);
							pren.draw(polCutter, polCutterColor);

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
								pren.draw(pol, polColor);
								pren.draw(polCutter, polCutterColor);
							}
						}

						else {
							// pro vykresleni prvni cary
							pren.draw(polCutter, polCutterColor);
							try {
								lren.draw(tempLine, 0xFF0000);
								if (outOfField == true) {
									outOfField = false;
								}
							} catch (ArrayIndexOutOfBoundsException exception) {
								outOfField = true;
								clear();
								pren.draw(pol, polColor);
								pren.draw(polCutter, polCutterColor);
							}

						}

					} else if (step == 2) {
						System.out.println("2");
						tempLine2.setLast(p);
						tempLine2.setFirst(polCutter.getPoint(0));
						pren.draw(pol, polColor);
						pren.draw(polCutter, polCutterColor);

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
							pren.draw(pol, polColor);
							pren.draw(polCutter, polCutterColor);
						}

					}

					panel.repaint();

				}
			}
		});

	}

	private void setMode(int number) {

		switch (number) {
		case 0:
			mode = 0;
			break;
		case 1:

			mode = 1;
			break;
		case 2:
			mode = 2;
			break;
		case 3:
			mode = 3;
			break;
		}

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

		PolygonRenderer pren = new PolygonRenderer(img);
		pren.draw(polCutter, polCutterColor);
		panel.repaint();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());

	}

}
