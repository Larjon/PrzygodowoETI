package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	// Ustawienia ekranu
	final int originalTitleSize = 16; 									// 16x16 znakow
	final int scale = 3;
	
	final int tileSize = originalTitleSize * scale; 					// wielkosc 16x16 * 3 = 48
	final int maxScreenCol = 16; 										// x = 16 kolumn moze byc np 18
	final int maxScreenRow = 12; 										// y = 12 wierszy moze byc np 14
	final int screenWidth = tileSize * maxScreenCol; 					// 48 * 16 = 768 piseli
	final int screenHeight = tileSize * maxScreenRow; 					// 48 * 12 = 576 pixeli
	
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; //odwoluje sie do metody run()
	
	
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
		
		if(keyH.upPressed == true) {					// Poruszanie sie za pomocą klawiatury
			playerY -= playerSpeed;
		}
		else if(keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		else if(keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}
		else if(keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
			
			
			
			
	}
	
	public void paintComponent(Graphics g) { 			// Graphics funkcja do rysowania obiektow na ekranie
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;					// Graphics2D rozszerza klase Graphics dla wiekszej kontroli, np koordynatów, tekstu itp
		
		g2.setColor(Color.white);
		
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
	}
	
}

















