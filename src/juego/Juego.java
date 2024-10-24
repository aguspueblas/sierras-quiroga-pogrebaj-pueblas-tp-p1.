package juego;

import java.awt.Image;
import java.awt.Color;
import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	private Islas[] islas;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al Rescate de los Gnomos", 1200, 800);
		
		// Inicializar lo que haga falta para el juego
		this.islas = new Islas[15];
		this.islas[0] = new Islas(600, 150, 150, 45);
	        this.islas[1] = new Islas(475, 275, 150, 45);	
                this.islas[2] = new Islas(725, 275, 150, 45);
                this.islas[3] = new Islas(350, 400, 150, 45);
                this.islas[4] = new Islas(600, 400, 150, 45);
                this.islas[5] = new Islas(850, 400, 150, 45);
                this.islas[6] = new Islas(225, 525, 150, 45);
	        this.islas[7] = new Islas(475, 525, 150, 45);	
                this.islas[8] = new Islas(725, 525, 150, 45);
                this.islas[9] = new Islas(975, 525, 150, 45);
                this.islas[10] = new Islas(100, 650, 150, 45);
                this.islas[11] = new Islas(350, 650, 150, 45);
                this.islas[12] = new Islas(600, 650, 150, 45);
                this.islas[13] = new Islas(850, 650, 150, 45);
                this.islas[14] = new Islas(1100, 650, 150, 45);

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		// Procesamiento de un instante de tiempo
		for(int i = 0; i < this.islas.length; i++) {
			Islas islas = this.islas[i];
			if(islas != null) {
				islas.dibujarIslas(this.entorno);
				islas.getImageIslas();
				islas.dibujarImagenIslas(this.entorno);
		        }
		}
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
