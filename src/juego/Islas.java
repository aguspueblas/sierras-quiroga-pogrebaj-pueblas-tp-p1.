package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Islas {
	
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private Image islasI;
	
	public Islas(int x, int y, int ancho, int alto) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;		
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

	public Image getImageIslas() {
		this.islasI = Herramientas.cargarImagen("imagenes/isla.jpg");
		return islasI;		
	}
	public void dibujarImagenIslas(Entorno entorno) {
		entorno.dibujarImagen(islasI, this.x, this.y, 0);
	}

}
