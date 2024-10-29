package clases;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Islas {
	
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int velocidad;
	private Image islasI;
	

	public Islas(int x, int y, int ancho, int alto, int velocidad) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;	
		this.velocidad = velocidad;
	}
	
	public void dibujarIslas(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, null);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public int getVelocidad(){
		return velocidad;
	}	

	public Image getImageIslas() {
		this.islasI = Herramientas.cargarImagen("imagenes/isla.jpg");
		return islasI;		
	}
	
	public void dibujarImagenIslas(Entorno entorno) {
		entorno.dibujarImagen(islasI, this.x, this.y, 0);
	}

	public void movimiento() {
		this.x = this.x + this.velocidad;
	}
	
	public void rebotar() {
		this.velocidad = this.velocidad*(-1);
	}
	
	public boolean tocaElBordeX() {
		boolean tocaX = this.x - this.ancho/2 <= 0 || this.x + this.ancho/2 >= 1200;
		return tocaX;			
        }
	
	public void reaparecerIzq() {
		if(this.x - this.ancho/2 >= 1200) 
			this.x = (this.x - this.ancho) -1200;		
	}
	
	public void reaparecerDer() {
		if(this.x + this.ancho/2 <= 0) 
			this.x = (this.x + this.ancho) +1200;
	}

}

