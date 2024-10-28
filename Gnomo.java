package juego;
import java.util.Random;
import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Gnomo {

	private int x;
	private int y;
	private int ancho;
	private int alto;
	private int velocidad;
	private boolean cayendo; // true si cae, false si se mueve
	private int direccion; //1 si izq, 0 si derecha
	private Image GnomoI;
	Random random = new Random();
	
	public Gnomo(int x, int y, int ancho, int alto, int velocidad){
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.velocidad = velocidad;
		this.cayendo = true;
		this.direccion = random.nextInt(2);
	}		
	public void empiezaACaer()
	{
		this.cayendo = true;
	}
	public void yaCayo()
	{
		this.cayendo = false;
	}
	public void moverseDer() {
			this.x= this.x + this.velocidad;
		}
		
	public boolean cae() {
		return this.cayendo;
	}
	
	public void moverseIzq() {
			this.x= this.x - this.velocidad;
	}

	public void caer() {
		this.y= this.y + velocidad*2;
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
	
	public int getVelocidad() {
		return velocidad;
	}
	public int direc() {
		return this.direccion;
	}
	public void cambioDirec()
	{
		this.direccion = random.nextInt(2);
	}

	public void iniciar(int velocidad) {
		this.velocidad = velocidad;
	}
	
	public void dibujarGnomo(Entorno entorno) {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.RED);
		
	}
	
	public Image getImageGnomo() {
		this.GnomoI = Herramientas.cargarImagen("imagenes/gnomo.png");
		return GnomoI;		
	}
	public void dibujarImagenGnomo(Entorno entorno) {
		entorno.dibujarImagen(GnomoI, this.x+2, this.y-11, 0, 0.05);
	}
}
