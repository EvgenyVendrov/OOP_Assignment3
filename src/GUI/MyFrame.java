package GUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Coords.GpsCoord;
import Geom.Point3D;
import gameUtils.Fruit;
import gameUtils.Game;
import gameUtils.Map;
import gameUtils.MapFactory;
import gameUtils.Pacman;

public class MyFrame extends JFrame implements MouseListener, ComponentListener, MenuListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private Map gameMap;
	private BufferedImage gameChangingImage;
	public static Point3D lastClicked;
	public static int height;
	public static int width;
	private ImagePanel imagePanel;
	private JMenu mainMenu;
	private JMenu subMenuDefaultGame;
	private JMenu existingGame;
	private JMenuItem loadCsv;
	private JMenu newGame;
	private JMenuItem packman;
	private JMenuItem fruit;
	private JMenuItem saveAsCsv;
	private JMenuItem saveAsKml;
	private JMenuItem play;
	public static boolean isPackmanAdding;
	public static boolean isFruitAdding;
	private Game thisGuisGame;
	public final JFileChooser fc = new JFileChooser("C:" + File.separator + "Users" + File.separator + "evgen"
			+ File.separator + "eclipse-workspace" + File.separator + "Assignment3" + File.separator + "config");


	public MyFrame() throws IOException {
		initComponents();
		this.addMouseListener(this);
		this.addComponentListener(this);
		this.thisGuisGame = new Game(new ArrayList<Pacman>(), new ArrayList<Fruit>());
	}

//*******component listener*******
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		height = this.getHeight() - 79;
		width = this.getWidth() - 22;
		this.imagePanel.resizeImage(width, height);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < this.thisGuisGame.getPackCollection().size(); i++) {
			Pacman current = this.thisGuisGame.getPackCollection().get(i);
			this.imagePanel.drawingPackman((int) (this.gameMap.gpsToPixel(current.getLocation()).y()),
					(int) (this.gameMap.gpsToPixel(current.getLocation()).x()) + 54, getGraphics());
		}
		for (Fruit current : this.thisGuisGame.getFruitCollection()) {
			GpsCoord gpsOfFruit = current.getLocation();
			Point3D gps2pixel = this.gameMap.gpsToPixel(gpsOfFruit);
			this.imagePanel.drawingFruit((int) gps2pixel.y(), (int) gps2pixel.x() + 54, getGraphics());
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub

	}

//*******mouse listener******* 
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (isPackmanAdding) {
			lastClicked = new Point3D(arg0.getX(), arg0.getY(), 0);
			this.imagePanel.drawingPackman(arg0.getX(), arg0.getY(), getGraphics());
			Pacman current = null;
			try {
				current = new Pacman(new GpsCoord(this.gameMap.clickedToAddPoint()), arg0.getX(), arg0.getY(), 1, 1);
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.thisGuisGame.addPacman(current);
		} else if (isFruitAdding) {
			lastClicked = new Point3D(arg0.getX(), arg0.getY(), 0);
			this.imagePanel.drawingFruit(arg0.getX(), arg0.getY(), getGraphics());
			Fruit current = null;
			try {
				current = new Fruit(new GpsCoord(this.gameMap.clickedToAddPoint()), arg0.getX(), arg0.getY(), 1);
			} catch (InvalidPropertiesFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.thisGuisGame.addFruit(current);
		}
		width = this.getWidth() - 22;
		height = this.getHeight() - 79;
		lastClicked = new Point3D(arg0.getX(), arg0.getY(), 0);
		try {
			System.out.println("***" + this.gameMap.clickedToAddPoint());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

//*******private methods*******
	private void initComponents() throws IOException {
		// now building the menu bar and all its features
		JMenuBar menuBar = new JMenuBar();
		// menu first - external "button:
		this.mainMenu = new JMenu("Menu");
		mainMenu.setMnemonic(KeyEvent.VK_R);
		mainMenu.addMenuListener(this);
		// creating subMenus
		this.subMenuDefaultGame = new JMenu("default game");
		subMenuDefaultGame.setMnemonic(KeyEvent.VK_R);
		subMenuDefaultGame.addMenuListener(this);
		// creating all menuItems for default game
		this.existingGame = new JMenu("play existing game");
		existingGame.setMnemonic(KeyEvent.VK_R);
		existingGame.addMenuListener(this);
		this.loadCsv = new JMenuItem("load CSV file");
		loadCsv.setMnemonic(KeyEvent.VK_R);
		loadCsv.addActionListener(new MenuAction(this));
		existingGame.add(loadCsv);
		subMenuDefaultGame.add(existingGame);
		this.newGame = new JMenu("build your own game");
		newGame.setMnemonic(KeyEvent.VK_R);
		this.packman = new JMenuItem("add pacman");
		packman.setMnemonic(KeyEvent.VK_R);
		packman.addActionListener(new MenuAction(this));
		this.fruit = new JMenuItem("add fruit");
		fruit.setMnemonic(KeyEvent.VK_R);
		fruit.addActionListener(new MenuAction(this));
		this.saveAsCsv = new JMenuItem("save the game as CSV file");
		saveAsCsv.setMnemonic(KeyEvent.VK_R);
		saveAsCsv.addActionListener(new MenuAction(this));
		newGame.add(packman);
		newGame.add(fruit);
		newGame.add(saveAsCsv);
		subMenuDefaultGame.add(newGame);
		mainMenu.add(subMenuDefaultGame);
		// creating menu items
		this.saveAsKml = new JMenuItem("save as KML");
		saveAsKml.setMnemonic(KeyEvent.VK_R);
		saveAsKml.addActionListener(new MenuAction(this));
		this.play = new JMenuItem("run movment simulation");
		play.setMnemonic(KeyEvent.VK_R);
		play.addActionListener(new MenuAction(this));
		// adding last menu items to the main one
		mainMenu.add(play);
		mainMenu.add(saveAsKml);
		// adding the main menu to the menu bar
		menuBar.add(mainMenu);
		this.setJMenuBar(menuBar);
		this.gameMap = MapFactory.defaultMapInit();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameChangingImage = this.gameMap.getImage();
		this.imagePanel = new ImagePanel(this.gameChangingImage);
		this.getContentPane().add(imagePanel);
		this.pack();
		this.setVisible(true);
		height = this.getHeight() - 22;
		width = this.getWidth() - 79;
		isPackmanAdding = false;
		isFruitAdding = false;
	}

// *******action listener*******
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.equals(this.packman)) {
			System.out.println("packmans");
		}
	}

//*******menu listener*******
	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void menuSelected(MenuEvent e) {
		if (e.getSource().equals(this.subMenuDefaultGame)) {
			System.out.println("menu clicked");
		}
	}

	public Game getGame() {
		return this.thisGuisGame;
	}

	private void showGame(Game givenGame) {
		for (int i = 0; i < this.thisGuisGame.getPackCollection().size(); i++) {
			Pacman current = this.thisGuisGame.getPackCollection().get(i);
			this.imagePanel.drawingPackman((int) (this.gameMap.gpsToPixel(current.getLocation()).y()),
					(int) (this.gameMap.gpsToPixel(current.getLocation()).x()) + 54, getGraphics());
		}
		for (Fruit current : this.thisGuisGame.getFruitCollection()) {
			GpsCoord gpsOfFruit = current.getLocation();
			Point3D gps2pixel = this.gameMap.gpsToPixel(gpsOfFruit);
			this.imagePanel.drawingFruit((int) gps2pixel.y(), (int) gps2pixel.x() + 54, getGraphics());
		}
	}

	public void start() throws InterruptedException {
		while (!this.thisGuisGame.getFruitCollection().isEmpty()) {
			this.thisGuisGame.move();
			repaint();
			Thread.sleep(50);
			this.showGame(thisGuisGame);
			Thread.sleep(40);
		}
		showGame(thisGuisGame);
	}
}
