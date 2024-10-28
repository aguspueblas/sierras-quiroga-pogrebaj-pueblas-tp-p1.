package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class casita {

	private int x;
	private int y;
	private double escala;
        private Image casitaI;

	public casita(int x, int y, double escala) {
		this.x = x;
		this.y = y;
		this.escala = escala;		
	}

	public Image getImageCasita() {
		this.casitaI = Herramientas.cargarImagen("imagenes/casita.png");
		return casitaI;
	}

	public void dibujarCasita(Entorno entorno) {
		entorno.dibujarImagen(casitaI, this.x, this.y-45, 0, 0.058);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getEscala() {
		return escala;
	}

}