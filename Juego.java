package juego;

import java.awt.Image;
import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	private Islas[] islas;

	private Gnomo[] gnomo;
	private Image fondo;
	private casita casita;
	private int tAntGnomo;
	int cantMaxGnomos = 6;

	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al Rescate de los Gnomos", 1200, 800);
		// Inicializar lo que haga falta para el juego
		
        this.fondo = Herramientas.cargarImagen("imagenes/fondo.jpg");
	    this.gnomo = new Gnomo[cantMaxGnomos];
		this.tAntGnomo = entorno.tiempo();
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
		this.casita = new casita(600, 110, 0.03);
        
	    
		// Inicia el juego!

		this.entorno.iniciar();
		
	}
	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el annunciate del TP para mayor detalle).
	 */	
	
	public void tick(){
		// Procesamiento de un instante de tiempo

		// Si se presiona la 'p' hacemos el movimiento inicial
		if(this.entorno.sePresiono('p')) {
			for(Gnomo p: this.gnomo) {
				if(p!= null){
					p.iniciar(1);
				}
			}
		}
		
		// Visualizacion o vista de Juego
        entorno.dibujarImagen(fondo, 600, 400, 0);
		casita.getImageCasita();
		casita.dibujarCasita(this.entorno);
        for(int i = 0; i < this.islas.length; i++) {
			Islas islas = this.islas[i];
			if(islas != null) {
				islas.dibujarIslas(this.entorno);
				islas.getImageIslas();
				islas.dibujarImagenIslas(entorno);
		        }
		}
        
        // Generacion de Gnomos
		this.crearGnomos();
		
		// Actualizo los Gnomos
		this.actualizarGnomos();
	}
	


	boolean colisionGnomoIsla(Gnomo p) {
        for (Islas isla : this.islas) {

            // Verificar si el gnomo está sobre la isla en el eje X
            boolean tocaX = p.getX() + p.getAncho() / 2 > isla.getX() - isla.getAncho() / 2 &&
                            p.getX() - p.getAncho() / 2 < isla.getX() + isla.getAncho() / 2;

            // Verificar si el gnomo está justo encima de la isla en el eje Y
            boolean tocaY = p.getY() + p.getAlto() / 2 >= isla.getY() - isla.getAlto() / 2 &&
                            p.getY() + p.getAlto() / 2 <= isla.getY() + isla.getAlto() / 2;

            if (tocaX && tocaY) {
                return true; // El gnomo está sobre una isla
            }
        }
        return false;  // No se detectó ninguna isla debajo del gnomo
    }

	
	private void crearGnomos()
	{
		if(entorno.tiempo() - this.tAntGnomo > 2500) //Crea nuevos gnomos
		{
			this.tAntGnomo = entorno.tiempo();
        		
			for(int nuevoGnomo = 0; nuevoGnomo < cantMaxGnomos ; nuevoGnomo++)
			{
				if(this.gnomo[nuevoGnomo] == null)
				{
					this.gnomo[nuevoGnomo] = new Gnomo(600,90,20,40,1);
					break;
				}
			}
		}
	}
	
	private void actualizarGnomos()
	{
		for(int i = 0; i < this.gnomo.length; i++) {
			
			Gnomo p = this.gnomo[i];
			
			if(p != null) {
				// Empiezo a visualizar al gnomo
				p.dibujarGnomo(this.entorno);
				p.getImageGnomo();
				p.dibujarImagenGnomo(entorno);
				
				// Actualizo la posicion del gnomoo
				if(p.cae() == true){
					p.caer(); //ACTUALIZA SU POSICION Y CAE
					
					if(colisionGnomoIsla(p)) {
						p.yaCayo(); //ACTUALIZAR SU ESTADO A CORRIENDO
						p.cambioDirec(); //ACTUALIZAR SU DIRECCION
					}
				}
				else if (p.cae() == false){ //LO HAGO CORRER
					if(p.direc() == 0 && p.getX() + p.getAncho()/2 < this.entorno.ancho()) {
						this.gnomo[i].moverseDer();
					}
					else if(p.direc() == 1 && p.getX() - p.getAncho() >= -10 ) {
						this.gnomo[i].moverseIzq();
					}
					
					if(!colisionGnomoIsla(p)) {
						p.empiezaACaer(); //ACTUALIZAR SU ESTADO A CAYENDO
					}
				}
			
				if(p.getY() > 900) {
					this.gnomo[i] = null;
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}