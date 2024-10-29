package clases;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;

public class BolaFuego {
	private int x;
	private int y;
	private Image img;
	private int radio;
	private int velocidad;
	private boolean dir; //true derecha, false izquierda
	
	public BolaFuego (int x, int y, int radio, int velocidad, boolean direccion) {
		this.x = x;
		this.y = y;
		this.radio = radio;
		this.velocidad = velocidad; 
		this.dir = direccion;
	}
	
	public void moverDer() {
		this.x= this.x + this.velocidad;
	}
	
	public void moverIzq() {
		this.x= this.x - this.velocidad;
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(x, y, radio*2, Color.ORANGE);
	}
	
	public int getVelocidad() {
		return this.velocidad;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRadio() {
		return radio;
	}
	
	public void setDir(boolean dir) {
		this.dir = dir;
	}
	
	public boolean getDir () {
		return this.dir;
	}
}
