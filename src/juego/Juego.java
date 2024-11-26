
package juego;

import java.awt.Color;
import java.awt.Image;

import clases.BolaFuego;
import clases.Casita;
import clases.Islas;
import clases.ListaEnlazada;
import clases.Nodo;
import clases.Pep;
import clases.PepServicio;
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
	private ListaEnlazada bolasFuego =  new ListaEnlazada();
	private Random random;
	private int contadorDeTiempo; // Contador para controlar la liberación de tortugas
	private int tortugaActiva; // Índice de la tortuga activa que está cayendo
	private Islas isla_seleccionada;
	private int tAntGnomo;
	int cantMaxGnomos = 6;
	//Contador de tortugas que son matadas por las bolas de fuego.
	int cantTortugasMatadasPorPep = 0;
	private int tiempo, gPerdidos;
	private Pep pep;
	public PepServicio pepServicio;
	private boolean enMenu = true;
	private int estado = 0; //Variable para representar los 3 estados del juego. 0 INICIO 1 GANO 2 PERDIO. Asi se pueden mostrar 3 menus distintos.
	private int gnomosRescatados = 0;
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Al Rescate de los Gnomos", 1200, 800);
		this.contadorDeTiempo =0;
		this.tortugaActiva=0;
		// Inicializar lo que haga falta para el juego
		this.fondo = Herramientas.cargarImagen("imagenes/fondo.jpg");
		this.tiempo = entorno.tiempo();
		this.gnomo = new Gnomo[cantMaxGnomos];
		this.tAntGnomo = 0;
			
		this.islas = new Islas[15];
		//Isla de la casa gnomos
		this.islas[0] = new Islas(600, 150, 150, 45, 0);
		//Segunda fila de isla de abajo hacia arriba
	    this.islas[1] = new Islas(475, 275, 150, 45, 1);	
        this.islas[2] = new Islas(725, 275, 150, 45, 1);
        //Tercer fila de isla de abajo hacia arriba
        this.islas[3] = new Islas(350, 400, 150, 45, -1);
        this.islas[4] = new Islas(600, 400, 150, 45, -1);
        this.islas[5] = new Islas(850, 400, 150, 45, -1);
        //ante ultimo fila de islas.
        this.islas[6] = new Islas(225, 525, 150, 45, 1);
	    this.islas[7] = new Islas(475, 525, 150, 45, 1);	
        this.islas[8] = new Islas(725, 525, 150, 45, 1);
        this.islas[9] = new Islas(975, 525, 150, 45, 1);
        //Ultima fila de islas
        this.islas[10] = new Islas(100, 650, 150, 45, -1);
        this.islas[11] = new Islas(350, 650, 150, 45, -1);
        this.islas[12] = new Islas(600, 650, 150, 45, -1);
        this.islas[13] = new Islas(850, 650, 150, 45, -1);
        this.islas[14] = new Islas(1100, 650, 150, 45, -1);
		this.casita = new Casita(600, 110, 0.03);
		
		
		  // Inicializar tortugas
        this.tortugas = new Tortuga[5]; // Cantidad de tortugas
        this.random = new Random();

        this.iniciarTortugas(tortugas);
		this.pep = new Pep(100, 600, 1);
		this.pepServicio = new PepServicio();
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
		entorno.dibujarImagen(fondo, 600, 400, 0);
		//Contadores
		this.contadorGPerdidos();
		this.cronometro();
		this.contadorTEliminadas();
		this.contadorGRescatados();
		boolean murioPep = this.murioPep();
		
		//CASO GANADOR.
		if (this.gnomosRescatados >= 5) {
			this.estado = 1;
			this.enMenu = true;
			this.resetearVar();
		}
		
		if (this.enMenu) {
	        dibujarMenu();
	        if (entorno.sePresiono(entorno.TECLA_ESCAPE))
	        	System.exit(0);
	        if (entorno.sePresiono(entorno.TECLA_ENTER)) { // Inicia el juego al presionar 'e'
	            this.enMenu = false;
	        }
		} else {
			if (murioPep || this.gPerdidos == 10) {
				this.pep = null;
				this.finJuego();
				this.estado = 2;
				this.resetearVar();
			} else {
				// Si se presiona la 'p' hacemos el movimiento inicial
				if(this.entorno.sePresiono('p')) {
					for(Gnomo p: this.gnomo) {
						if(p!= null){
							p.iniciar(1);
						}
					}
				}
				
				casita.getImageCasita();
				casita.dibujarCasita(this.entorno);
	
				// Dibuja y actualiza tortugas
				// Actualiza y dibuja las tortugas
			    actualizarTortugas();
				this.pep.mostrar(entorno);
				
			    for(int i = 0; i < this.islas.length; i++) {
					Islas islas = this.islas[i];
					if(islas != null) {
						islas.dibujarIslas(this.entorno);
						islas.getImageIslas();
						islas.dibujarImagenIslas(this.entorno);
						//islas.movimiento();
		
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
		 		//logica pep
		 		this.pepServicio.logicaPep(entorno, pep, islas);
		 		boolean dispararBolaFuego = this.entorno.sePresiono('c');
		 		if (dispararBolaFuego) {
					BolaFuego bolaFuego = this.pep.lanzarBola(entorno);
					this.bolasFuego.agregarAtras(bolaFuego);
				}
		 		if (bolasFuego.length() > 0) {
		 			this.logicaBolasFuego();
		 		}
		 		
		 		if(this.gnomo.length > 0 && this.gnomo != null) {
		 			for (int x = 0; x < this.gnomo.length; x++) {
		 				Gnomo gnomo = this.gnomo[x];
		 				if(gnomo != null) {
		 					if(this.colisionEntrePepYGnmonos(gnomo)) {
		 						this.gnomosRescatados++;
		 						this.gnomo[x] = null;
		 					}
		 				}
		 			}
		 		}
				
			}
		}
 		
	}
	
	private void iniciarTortugas(Tortuga[] tortugas) {
		for (int i = 0; i < tortugas.length; i++) {
            int x = random.nextInt(1200); // Posición aleatoria en x
            int y = 400; // Comienza desde la parte superior
            
            tortugas[i] = new Tortuga(x, y, 1); // Velocidad de caída
        }
		
	}
	private void resetearVar() {
		this.enMenu = true;
		this.gPerdidos = 0;
		this.cantTortugasMatadasPorPep = 0;
		this.pep = new Pep(100, 600, 1);
		this.iniciarTortugas(tortugas);
	}
	
	private void cronometro() {
		int milisegundos = this.tiempo;
		int segundos = 00;
		int minutos = 00;
		for(int m = 0; m < 300; m++) {			
			    segundos = milisegundos/1000;
			    minutos = segundos/60;
			    if(segundos > 59)
			    	segundos-=60;
			}
			entorno.escribirTexto("Tiempo: "+minutos+":"+segundos, 20, 20);
		}

	private void contadorGPerdidos() {
			entorno.escribirTexto("Gnomos perdidos: "+ this.gPerdidos, 20, 40);
	}

	private void contadorGRescatados() {
		entorno.escribirTexto("Gnomos rescatados: "+this.gnomosRescatados, 20, 80);
}
	
	private void contadorTEliminadas() {
			entorno.escribirTexto("Tortugas eliminadas: "+cantTortugasMatadasPorPep , 20, 60);		
	}
	
	private void finJuego() {
		entorno.escribirTexto("FIN DEL JUEGO!" , entorno.ancho()/2, entorno.alto()/2);		
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
	    //La tortuga para empezar a colisionar debe estar abajo de la tercer fila de islas.
	    if(tortuga.getY() - tortuga.getAlto() /2 > 450) {
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
					this.gPerdidos++;
				}
				 // Verifica colisiones con tortugas
	            for (Tortuga tortuga : tortugas) {
	                if (tortuga.enIsla == true && colisionTortugaGnomo(tortuga, p)) {
	                    this.gnomo[i] = null; // Elimina el gnomo
				this.gPerdidos++;
	                    break; // Sale del bucle si se elimina el gnomo
	                }
	            }
			}
			
			
		}
	}
	
	//Logica para las bolas de FUEGO:
	// Movimiento y colision con las tortugas	
	private void logicaBolasFuego () {
		Nodo actual = bolasFuego.getPrimero();
		while (actual != null) {
			//movimiento de bolas
			int anchoPantalla = this.entorno.ancho();
			if (actual.elemento.getX() > 0 && actual.elemento.getX() < anchoPantalla) {
				if (actual.elemento.getDir()) { //Si debe moverse a la derecha
					actual.elemento.moverDer();
				} else { //Debe moverse a la izquierda
					actual.elemento.moverIzq();
				}
				boolean colisionTortuga = false;
				int contador = 0;
				while (!colisionTortuga && contador < this.tortugas.length) {
					colisionTortuga = this.colisionBolaFuegoConTortuga(actual.elemento, this.tortugas[contador]);
					if (colisionTortuga) {
						bolasFuego.quitar(actual.elemento);
						this.reiniciarTortuga(this.tortugas[contador]);
						this.cantTortugasMatadasPorPep++;
					}
					contador++;
				}
			} else {
				bolasFuego.quitar(actual.elemento);
			}
			actual.elemento.dibujar(entorno);
			actual = actual.siguiente;
		}
	}
	
	private boolean colisionBolaFuegoConTortuga (BolaFuego bolaFuego, Tortuga tortuga) {
		boolean tocaX = tortuga.getX() - tortuga.getAncho()/2 <  bolaFuego.getX() &&
				tortuga.getX() + tortuga.getAncho()/2 > bolaFuego.getX();
		boolean tocaY = bolaFuego.getY() + bolaFuego.getRadio() > tortuga.getY() - tortuga.getAlto()/2
				&& bolaFuego.getY() - bolaFuego.getRadio() < tortuga.getY() + tortuga.getAlto()/2;
		
		return tocaX && tocaY;
		
	}
	
	private boolean murioPep () {
		
		boolean tocaX = false;
		boolean tocaY = false;
		boolean colision = false;
		boolean pepCayoPrecipio = false;
		int contador = 0;
		if (this.pep != null) {
			while (!colision && contador < this.tortugas.length) {
				Tortuga tortuga = this.tortugas[contador];
				tocaX = this.pep.getX() - this.pep.getAncho()/2 < tortuga.getX() + tortuga.getAncho()/2 
						&& this.pep.getX() + this.pep.getAncho()/2 > tortuga.getX() - tortuga.getAncho()/2;
				tocaY = this.pep.getY() - this.pep.getAlto()/2 < tortuga.getY() + tortuga.getAlto()/2
							&& this.pep.getY() + this.pep.getAlto()/2 > tortuga.getY() - tortuga.getAlto()/2;
				colision = tocaX && tocaY;
				contador++;
				
			}
				int altoPantalla = this.entorno.alto();
				pepCayoPrecipio = this.pep.getY() > altoPantalla;
		} else {
			colision = true;
		}
		return colision || pepCayoPrecipio;
	}
	
	Color miColor = new Color ( 0, 143, 57);
	Color miColor2 = new Color ( 204, 169, 221);
	
	
	private void dibujarMenu() {
	    entorno.dibujarImagen(fondo, 600, 400, 0); // Dibuja el fondo
	    entorno.cambiarFont("Old English Text MT", 60, Color.WHITE, entorno.ITALICA);
	    entorno.escribirTexto("¡Al Rescate de los Gnomos!", 273, 103);
	    entorno.cambiarFont("Old English Text MT", 60, miColor2, entorno.ITALICA);
	    entorno.escribirTexto("¡Al Rescate de los Gnomos!", 272, 102);
	    entorno.cambiarFont("Old English Text MT", 60, miColor, entorno.ITALICA);
	    entorno.escribirTexto("¡Al Rescate de los Gnomos!", 270, 100);
	    entorno.cambiarFont("Rockwell Extra Bold", 30 , Color.WHITE);
	    switch (this.estado) {
		case 1:
			entorno.escribirTexto("GANASTE :D", 355, 450);
			entorno.cambiarFont("Old English Text MT", 30, miColor2, entorno.ITALICA);
		    entorno.escribirTexto("GANASTE :D", 354, 451);
		    entorno.cambiarFont("Old English Text MT", 30, miColor, entorno.ITALICA);
		    entorno.escribirTexto("GANASTE :D", 352, 452);	
		    entorno.cambiarFont("Rockwell Extra Bold", 30 , Color.WHITE);
		    entorno.escribirTexto("Presiona 'enter' para volver a jugar", 353, 303);
		    entorno.escribirTexto("Presiona 'escape' para salir", 353, 403);
		    entorno.cambiarFont("Rockwell Extra Bold", 30 , miColor2);
		    entorno.escribirTexto("Presiona 'enter' para  volver a jugar", 352, 302);
		    entorno.escribirTexto("Presiona 'escape' para salir", 352, 402);
		    entorno.cambiarFont("Rockwell Extra Bold", 30 , miColor);
		    entorno.escribirTexto("Presiona 'enter' para volver a jugar", 350, 300);
		    entorno.escribirTexto("Presiona 'escape' para salir", 350, 400);
			break;
		case 2:
			entorno.escribirTexto("PERDISTE D:", 454, 500);
			entorno.cambiarFont("Old English Text MT", 30, miColor2, entorno.ITALICA);
		    entorno.escribirTexto("PERDISTE D:", 452, 499);
		    entorno.cambiarFont("Old English Text MT", 30, miColor, entorno.ITALICA);
		    entorno.escribirTexto("PERDISTE D:", 450, 497);	
		    entorno.cambiarFont("Rockwell Extra Bold", 30 , Color.WHITE);
		    entorno.escribirTexto("Presiona 'enter' para volver a jugar", 353, 303);
		    entorno.escribirTexto("Presiona 'escape' para salir", 353, 403);
		    entorno.cambiarFont("Rockwell Extra Bold", 30 , miColor2);
		    entorno.escribirTexto("Presiona 'enter' para volver a jugar", 352, 302);
		    entorno.escribirTexto("Presiona 'escape' para salir", 352, 402);
		    entorno.cambiarFont("Rockwell Extra Bold", 30 , miColor);
		    entorno.escribirTexto("Presiona 'enter' para volver a jugar", 350, 300);
		    entorno.escribirTexto("Presiona 'escape' para salir", 350, 400);
			break;
		default:
			entorno.cambiarFont("Rockwell Extra Bold", 30 , Color.WHITE);
		    entorno.escribirTexto("Presiona 'enter' para jugar", 353, 303);
		    entorno.escribirTexto("Presiona 'escape' para salir", 353, 403);
		    entorno.cambiarFont("Rockwell Extra Bold", 30 , miColor2);
		    entorno.escribirTexto("Presiona 'enter' para jugar", 352, 302);
		    entorno.escribirTexto("Presiona 'escape' para salir", 352, 402);
		    entorno.cambiarFont("Rockwell Extra Bold", 30 , miColor);
		    entorno.escribirTexto("Presiona 'enter' para jugar", 350, 300);
		    entorno.escribirTexto("Presiona 'escape' para salir", 350, 400);
		    break;
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
	
	//Chequea la colision entre PEP y los GNOMOS
	public boolean colisionEntrePepYGnmonos (Gnomo gnomo) {
		// Chequea que pep colisione en X con el gnomo
        boolean tocaX = gnomo.getX() - gnomo.getAncho() / 2 < this.pep.getX() &&
        				gnomo.getX() + gnomo.getAncho() / 2 > this.pep.getX();
        // Chequea que pep colisione en y con el gnomo.
        boolean tocaY = gnomo.getY() + gnomo.getAlto() / 2 > this.pep.getY() - this.pep.getAlto() / 2 &&
        				gnomo.getY() - gnomo.getAlto() / 2 < this.pep.getY() + this.pep.getAlto() / 2;
        return tocaX && tocaY;

	}
}
