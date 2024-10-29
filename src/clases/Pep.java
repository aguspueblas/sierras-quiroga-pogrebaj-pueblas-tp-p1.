package clases;

import java.awt.Image;
import java.awt.Point;

import entorno.Entorno;
import entorno.Herramientas;

public class Pep {
	//Posición de Pep
	private int x;
	private int y;
	private Image img;
	private boolean dir; //true derecha, false izquierda
	private int alto;
	private int ancho;
	private double escala;
	private int velocidad;
	private boolean enIsla;
	
	/**
	 * Constructor del objeto PEP
	 * @param x Posicion en X
	 * @param y Posicion en Y
	 * @param alto Alto del rectangulo
	 * @param ancho Ancho del rectangulo
	 * @param velocidad Velocidad en la que se mueve el objeto
	 */
	public Pep (int x, int y, int velocidad) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("imagenes/pep.png");
		this.alto = 55;
		this.ancho = 20;
		this.escala = 0.07;
		this.velocidad = velocidad;
	}
	
	/**
	 * Metodo para mostrar a Pep en el entorno del juego
	 * @param entorno
	 */
	public void mostrar (Entorno entorno) {
		entorno.dibujarImagen(this.img, this.x, this.y, 0, this.escala);
	}
	
	public void moverHaciaDerecha () {
		this.x = this.x + this.velocidad;
		this.dir = true;
	}
	
	public void moverHaciaIzquierda () {
		this.dir = false;
		this.x = this.x - this.velocidad;
	}

	public void saltar () {
		this.y = this.y - 12 ;
	}
	
	public void caer() {
		this.y = this.y + 2;
	}
	
	public int getX () {
		return this.x;
	}
	
	public int getY () {
		return this.y;
	}
	
	public int getAncho () {
		return this.ancho;
	}
	
	public int getAlto () {
		return this.alto;
	}
	
	public double getEscala() {
		return this.escala;
	}
	
	public boolean getDireccion () {
		return this.dir;
	}
	
	public BolaFuego lanzarBola (Entorno entorno) {
		BolaFuego bola;
		int velocidad = 2;
		if (this.dir) { //True derecha
			bola = new BolaFuego(this.x + 20,this.y + 10,10,velocidad, this.dir);
		} else {
			bola = new BolaFuego(this.x - 20,this.y + 10 ,10,velocidad, this.dir);
		}
		bola.dibujar(entorno);
		return bola;
	}
	
	public boolean getPepEnIsla () {
		return this.enIsla;
	}
	
	public void setPepEnIsla (boolean enIsla) {
		this.enIsla = enIsla;
	}
}
