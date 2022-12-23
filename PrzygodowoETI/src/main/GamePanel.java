package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// Ustawienia ekranu
	final int originalTitleSize = 16; 									// 16x16 znakow
	final int scale = 3;
	
	public final int tileSize = originalTitleSize * scale; 				// wielkosc 16x16 * 3 = 48
	public final int maxScreenCol = 16; 										// x = 16 kolumn moze byc np 18  //zmiana na publiczne
    public final int maxScreenRow = 12; 										// y = 12 wierszy moze byc np 14 //zmiana na publiczne
    public final int screenWidth = tileSize * maxScreenCol; 					// 48 * 16 = 768 piseli
    public final int screenHeight = tileSize * maxScreenRow; 					// 48 * 12 = 576 pixeli
    
    //Parametry Świata
    
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxWorldWidth = tileSize * maxWorldCol;
    public final int maxWorldHeight = tileSize * maxWorldRow;
    
    
    
    
    
    
	//ILOSC FPSOW
	int FPS = 60;
	
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; //odwoluje sie do metody run()
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public Player player = new Player(this, keyH);
	
	// Konstruktor game panelu
	
	// Ustawienie domyslej pozycji
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;

	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight) );
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); 					//lepsze renderowanie
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	
	/*
	
	@Override
	public void run() {
		// TODO czas gry
		
		//int c =0;
		double drawInterval = 1000000000/FPS; //rysujemy co 0.01666 sekundy = 60 fps
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			//c++;
			//System.out.println("test szybkości wykonywania programu"+c); 		// aktualizacja klatek gry
			
			
			//long currentTime = System.nanoTime();
			//System.out.println("aktualny czas" + currentTime);										// informacje o rysowaniu 
			
			
			
			
			
			update();									// aktualizowanie
			
			repaint();  								// mlaowanie
			
			
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	*/
	
	
	public void run() {
	
		double drawInterval = 1000000000/FPS; 					//rysujemy co 0.01666 sekundy = 60 fps
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
			
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime)/ drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >=1) {
				update();									
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS "+ drawCount);
				drawCount = 0;
				timer = 0;
			}		
		}	
	}
	
	
	
	public void update() {
		
		player.update();
			
			

	}
	
	public void paintComponent(Graphics g) { 			// Graphics funkcja do rysowania obiektow na ekranie
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;					// Graphics2D rozszerza klase Graphics dla wiekszej kontroli, np koordynatów, tekstu itp
		
		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose();
	}
	
}


















