package clases;
import entorno.Entorno;

public class PepServicio {
	private Entorno entorno;
	private Pep pep;
	private Islas[] islas;
	//Constante: Rango de colision
	private static final int RANGO_COLISION = 20;
	private static final int PEP_MAX_Y = 400 + 45/2;
	public PepServicio () {}
	
	public void logicaPep (Entorno entorno, Pep pep, Islas[] islas) {
		this.islas = islas;
		this.entorno = entorno;
		this.pep = pep;
		Islas isla = this.islaCercanaPep();
		
		if (isla != null) {
			this.pep.setPepEnIsla(this.pepParadoEnIsla(isla));
		} else {
			this.pep.setPepEnIsla(false);
			
		}
		
		boolean pepMoverDer = this.entorno.estaPresionada(entorno.TECLA_DERECHA) || this.entorno.estaPresionada('d');;
		boolean pepMoverIzq =  this.entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || this.entorno.estaPresionada('a');
		boolean pepSalto = this.entorno.estaPresionada(entorno.TECLA_ESPACIO);
		
		
		if (!this.pep.getPepEnIsla()) {
			this.pep.caer();
		} 
		if (!pepColisionConIslaParteDerecha(isla) && pepMoverDer ){
			this.pep.moverHaciaDerecha();
		}
		if (!pepColisionConIslaParteIzquierda(isla) && pepMoverIzq) {
			this.pep.moverHaciaIzquierda();
		}
		if (!this.pepColisionConIslaParteBaja(isla) && pepSalto && this.pep.getY() > PEP_MAX_Y ) {
			this.pep.saltar();
		}
		
		
	}
	
	private boolean pepColisionaEnY (Islas isla) {
		boolean colisiona = false;
		if (isla != null) {
			colisiona = isla.getY() + isla.getAlto()/2 > this.pep.getY() - this.pep.getAlto()/2 &&
							isla.getY() - isla.getAlto()/2 < this.pep.getY() + this.pep.getAlto()/2;
		} 
		return colisiona;
	}
	
	private boolean pepColisionaEnX (Islas isla) {
		boolean colisiona = false;
		if (isla != null) {
			colisiona = isla.getX() - isla.getAncho()/2 < this.pep.getX() &&
							isla.getX() + isla.getAncho()/2 > this.pep.getX();
		}
		return colisiona;
	}
	
	//Chequea que PEP este parado en la isla.
	private boolean pepParadoEnIsla (Islas isla) {
		boolean parado = false;
		if (isla != null) {
			int pepInferior = this.pep.getY() + this.pep.getAlto()/2;
	 		int parteSuperiorIsla = isla.getY() - isla.getAlto()/2;
			parado = pepInferior <= parteSuperiorIsla + 3 && pepInferior >= parteSuperiorIsla - 3;
		}
		
		return parado;
	}
	
	//Metodo para saber si PEP colisiona con la parte derecha de la isla.
	private boolean pepColisionConIslaParteDerecha (Islas isla) {
		boolean colisionDerecha = false;
		if (isla != null) {
			int derechaPep = this.pep.getX() + this.pep.getAncho()/2;
			int islaParteIzquierda = isla.getX() - isla.getAncho()/2;
			
			colisionDerecha = derechaPep > islaParteIzquierda - RANGO_COLISION && derechaPep < islaParteIzquierda + RANGO_COLISION;
		}
		
		return colisionDerecha; 
	}
	
	//Metodo para saber si PEP colisiona con la parte izquierda de la isla.
	private boolean pepColisionConIslaParteIzquierda (Islas isla) {
		boolean colision = false;
		if (isla != null) {
			int izqPep = this.pep.getX() - this.pep.getAncho()/2;
			int islaParteDerecha = isla.getX() + isla.getAncho()/2;
				
			colision = izqPep < islaParteDerecha + RANGO_COLISION && izqPep > islaParteDerecha - RANGO_COLISION;
		}
			
		return colision; 
	}
	
	//Metodo para saber si PEP colisiona con la parte baja de la isla
	private boolean pepColisionConIslaParteBaja (Islas isla) {
		boolean colision = false;
		if (isla != null) {
			int cabezaPep = this.pep.getY() - this.pep.getAlto()/2;
			int islaParteBaja = isla.getY() + isla.getAlto()/2;
			
			colision = cabezaPep < islaParteBaja + RANGO_COLISION && cabezaPep > islaParteBaja - RANGO_COLISION - 20;
		}
		return colision;
	}
	
	//Busca la isla mas cercana a PEP
	private Islas islaCercanaPep () {
		Islas islaEncontrada = null;
		for (Islas isla : this.islas) { 
			boolean colisionY = this.pepColisionaEnY(isla);
			boolean colisionX = this.pepColisionaEnX(isla);
			if (colisionY && colisionX) {
				islaEncontrada = isla;
			};
		}
		
		return islaEncontrada;
	}
	
}
