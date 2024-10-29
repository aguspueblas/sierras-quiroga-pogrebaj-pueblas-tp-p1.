
package juego;

import java.awt.Image;
import java.awt.Color;
import entorno.Herramientas;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.util.Random;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	private Islas[] islas;
	private Gnomo[] gnomo;
	private Image fondo;
	private Tortuga[] tortugas;
	private Casita casita;
	private Random random;
	private int contadorDeTiempo; // Contador para controlar la liberación de tortugas
	private int tortugaActiva; // Índice de la tortuga activa que está cayendo
	private Islas isla_seleccionada;
	private int tAntGnomo;
	int cantMaxGnomos = 6;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al Rescate de los Gnomos", 1200, 800);
		this.contadorDeTiempo =0;
		this.tortugaActiva=0;
		// Inicializar lo que haga falta para el juego
		this.fondo = Herramientas.cargarImagen("imagenes/fondo.jpg");
		
		this.gnomo = new Gnomo[cantMaxGnomos];
		this.tAntGnomo = entorno.tiempo();
			
		this.islas = new Islas[15];
		this.islas[0] = new Islas(600, 150, 150, 45, 0);
	    this.islas[1] = new Islas(475, 275, 150, 45, 1);	
        this.islas[2] = new Islas(725, 275, 150, 45, 1);
        this.islas[3] = new Islas(350, 400, 150, 45, -1);
        this.islas[4] = new Islas(600, 400, 150, 45, -1);
        this.islas[5] = new Islas(850, 400, 150, 45, -1);
        this.islas[6] = new Islas(225, 525, 150, 45, 1);
	    this.islas[7] = new Islas(475, 525, 150, 45, 1);	
        this.islas[8] = new Islas(725, 525, 150, 45, 1);
        this.islas[9] = new Islas(975, 525, 150, 45, 1);
        this.islas[10] = new Islas(100, 650, 150, 45, -1);
        this.islas[11] = new Islas(350, 650, 150, 45, -1);
        this.islas[12] = new Islas(600, 650, 150, 45, -1);
        this.islas[13] = new Islas(850, 650, 150, 45, -1);
        this.islas[14] = new Islas(1100, 650, 150, 45, -1);
		this.casita = new Casita(600, 110, 0.03);
		
		
		  // Inicializar tortugas
        this.tortugas = new Tortuga[5]; // Cantidad de tortugas
        this.random = new Random();

        for (int i = 0; i < tortugas.length; i++) {
            int x = random.nextInt(1200); // Posición aleatoria en x
            int y = 0; // Comienza desde la parte superior
            
            tortugas[i] = new Tortuga(x, y, 1); // Velocidad de caída
        }


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
		// Si se presiona la 'p' hacemos el movimiento inicial
		if(this.entorno.sePresiono('p')) {
			for(Gnomo p: this.gnomo) {
				if(p!= null){
					p.iniciar(1);
				}
			}
		}
		// Procesamiento de un instante de tiempo
		entorno.dibujarImagen(fondo, 600, 400, 0);

		casita.getImageCasita();
		casita.dibujarCasita(this.entorno);
		// Dibuja y actualiza tortugas
		
		// Actualiza y dibuja las tortugas
	    actualizarTortugas();
		
		/*for(int i = 0; i < this.islas.length; i++) {
			Islas islas = this.islas[i];
			if(islas != null) {
				islas.dibujarIslas(this.entorno);
				islas.getImageIslas();
				islas.dibujarImagenIslas(this.entorno);
		        }
		}*/
	    for(int i = 0; i < this.islas.length; i++) {
			Islas islas = this.islas[i];
			if(islas != null) {
				islas.dibujarIslas(this.entorno);
				islas.getImageIslas();
				islas.dibujarImagenIslas(this.entorno);
				islas.movimiento();

				if (this.islas[5].tocaElBordeX() || this.islas[3].tocaElBordeX()) {
					this.islas[3].rebotar();
					this.islas[4].rebotar();
					this.islas[5].rebotar();
				}
				
				if (this.islas[1].tocaElBordeX() || this.islas[2].tocaElBordeX()) {
					this.islas[1].rebotar();
					this.islas[2].rebotar();
				}							
				
				this.islas[6].reaparecerIzq();
				this.islas[7].reaparecerIzq();
				this.islas[8].reaparecerIzq();
				this.islas[9].reaparecerIzq();
				this.islas[10].reaparecerDer();
				this.islas[11].reaparecerDer();
				this.islas[12].reaparecerDer();
				this.islas[13].reaparecerDer();
				this.islas[14].reaparecerDer();
		        }
		}

		// Actualizar límites de las tortugas que están en las islas
	    actualizarLimitesTortugas();
	    
	    // Generacion de Gnomos
	 		this.crearGnomos();
	 		
 		// Actualizo los Gnomos
 		this.actualizarGnomos();
	}
	
	private void actualizarLimitesTortugas() {
	    for (Tortuga tortuga : tortugas) {
	        if (tortuga.enIsla) { // Si la tortuga está en una isla
	            // Obtener la isla actual de la tortuga
	            Islas isla = tortuga.getIsla();
	            if (isla != null) {
	                // Actualizar los límites según la posición actual de la isla
	                double limiteIzquierdo = isla.getX() - isla.getAncho() / 2;
	                double limiteDerecho = isla.getX() + isla.getAncho() / 2;

	                // Configurar los límites actualizados en la tortuga
	                tortuga.setLimites(limiteIzquierdo, limiteDerecho);
	            }
	        }
	    }
	}
	
	private boolean colisionTortugaGnomo(Tortuga tortuga, Gnomo gnomo) {
	    // Verifica si la tortuga y el gnomo se solapan en el eje X
	    boolean tocaX = tortuga.getX() + tortuga.getAncho() / 2 > gnomo.getX() - gnomo.getAncho() / 2 &&
	                     tortuga.getX() - tortuga.getAncho() / 2 < gnomo.getX() + gnomo.getAncho() / 2;

	    // Verifica si la tortuga y el gnomo se solapan en el eje Y
	    boolean tocaY = tortuga.getY() + tortuga.getAlto() / 2 > gnomo.getY() - gnomo.getAlto() / 2 &&
	                     tortuga.getY() - tortuga.getAlto() / 2 < gnomo.getY() + gnomo.getAlto() / 2;

	    return tocaX && tocaY; // Retorna true si hay colisión
	}

	
	
	private boolean hayTortugaEnIsla(Islas isla) {
	    // Recorre todas las tortugas
	    for (Tortuga tortuga : tortugas) {
	        // Si alguna tortuga tiene como isla actual la isla dada, devuelve true
	        if (tortuga.getIsla() == isla) {
	            return true;
	        }
	    }
	    // Si no se encontró ninguna tortuga en la isla, devuelve false
	    return false;
	}
	
	private void actualizarTortugas() {
	    // Si hay menos de 5 tortugas activas, se generan nuevas hasta llegar a 5
	    while (tortugaActiva < 5) {
	        tortugaActiva++;
	        int x = random.nextInt(1200); // Nueva posición aleatoria en X
	        tortugas[tortugaActiva - 1] = new Tortuga(x, 0, 1); // Velocidad de caída
	    }

	    for (int i = 0; i < tortugaActiva; i++) {
	        Tortuga tortuga = tortugas[i];
	        tortuga.actualizar(); // Actualiza la posición de la tortuga
	        tortuga.dibujarTortuga(this.entorno);
	        
	        // Verifica si la tortuga ha caído
	        if (tortuga.getY() > entorno.alto()) {
	            reiniciarTortuga(tortuga); // REINICIA TORTUGA SI SE CAYO FUERA DE UNA ISLA
	        } else {
	            // Verifica colisión con las islas
	            manejarColisionesConIslas(tortuga);
	        }
	    }
	}

	private void reiniciarTortuga(Tortuga tortuga) {
	    // Genera una nueva posición aleatoria en X para la tortuga
	    int x = random.nextInt(1200);
	    tortuga.setX(x); 
	    tortuga.setY(0); 
	    tortuga.enIsla = false;
	}
	
	private void manejarColisionesConIslas(Tortuga tortuga) {
	    Islas islaSeleccionada = null;

	    for (int j = 0;j < this.islas.length; j++) {
	    	 Islas isla = this.islas[j];
	        if (isla != null && j != 0 && tortuga.getX() > isla.getX() - isla.getAncho() / 2
	                && tortuga.getX() < isla.getX() + isla.getAncho() / 2
	                && !hayTortugaEnIsla(isla)) {

	            // Verifica si la tortuga está en la parte superior de la isla
	            if (tortuga.getY() + 40 >= isla.getY() && tortuga.getY() < isla.getY() + 45) { // 45 es la altura de la isla
	                islaSeleccionada = isla;
	                tortuga.setY(isla.getY() - 40); 
	                tortuga.enIsla = true; // Se ha detectado que la tortuga está en una isla
	                break; // Si la tortuga está en una isla, no necesita revisar las demás
	            }
	        }
	    }

	    if (tortuga.enIsla && islaSeleccionada != null) {
	        restringirTortugaEnIsla(tortuga, islaSeleccionada);
	    }
	}

	private void restringirTortugaEnIsla(Tortuga tortuga, Islas isla) {
	    double limiteIzquierdo = isla.getX() - isla.getAncho() / 2;
	    double limiteDerecho = isla.getX() + isla.getAncho() / 2;

	    // Restringe la posición X de la tortuga dentro de la isla
	    if (tortuga.getX() < limiteIzquierdo) {
	        tortuga.setX(limiteIzquierdo); // Mantiene la tortuga dentro del límite izquierdo
	    } else if (tortuga.getX() > limiteDerecho) {
	        tortuga.setX(limiteDerecho); // Mantiene la tortuga dentro del límite derecho
	    }
	
	    tortuga.setEnIsla(true, limiteIzquierdo, limiteDerecho, isla);
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
					this.gnomo[nuevoGnomo] = new Gnomo(600,90,10,40,1);
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
				 // Verifica colisiones con tortugas
	            for (Tortuga tortuga : tortugas) {
	                if (tortuga.enIsla == true && colisionTortugaGnomo(tortuga, p)) {
	                    this.gnomo[i] = null; // Elimina el gnomo
	                    break; // Sale del bucle si se elimina el gnomo
	                }
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
