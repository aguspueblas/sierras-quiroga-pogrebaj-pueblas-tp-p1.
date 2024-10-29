package clases;

public class ListaEnlazada {
	private Nodo primero;
	
	public ListaEnlazada (){
		this.primero = null;
	}
	
	public Nodo getPrimero () {
		return this.primero;
	}
	//METODO PARA SABER LA LONGITUD DE LA LISTA.
	public int length () {
		Nodo actual = this.primero;
		int contador = 0;
		while (actual != null) {
			contador++;
			actual = actual.siguiente;
		}
		return contador;
	}
	
	//Agrega una bolaFuegoAdelante
	public void agregarAdelante (BolaFuego bolaFuego) {
		Nodo nuevo = new Nodo(bolaFuego);
		nuevo.siguiente = this.primero;
		this.primero = nuevo; 
	}
	
	public void agregarAtras (BolaFuego bolaFuego) {
		Nodo nuevo = new Nodo(bolaFuego);
		if(this.primero == null) {
			this.primero = nuevo;
		} else {
			Nodo actual = this.primero;
			while (actual.siguiente != null) {
				actual = actual.siguiente;
			}
			actual.siguiente = nuevo;
		}
	}
	
	//Quita elemento de la lista.
	public void quitar (BolaFuego bolaFuego) {
		if (this.primero != null && this.primero.elemento == bolaFuego) {
			this.primero = this.primero.siguiente;
		} else {
			Nodo actual = this.primero;
			while (actual.siguiente != null && actual.siguiente.elemento != bolaFuego)
				actual = actual.siguiente;
			
			if (actual.siguiente != null) {
				actual.siguiente = actual.siguiente.siguiente;
			}
		}
	}
}
