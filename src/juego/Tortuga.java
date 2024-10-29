package juego;

import java.awt.Image;

import clases.Islas;
import entorno.Entorno;
import entorno.Herramientas;

public class Tortuga {
    private double x;
    private double y;
    private double velocidadY;
    boolean enIsla;
    private int direccion; // 1 para derecha, -1 para izquierda
    private double limiteIzquierdo;
    private double limiteDerecho;
    private Image imgTortuga;
    private double escala;
    private Islas islaActual;
    private boolean direccionDerecha; 
    private double velocidadX; // Velocidad de movimiento en el eje X
    private double ancho; // Ancho de la tortuga
    private double alto; // Alto de la tortuga
    
    public Tortuga(int x, int y, double velocidadY) {
        this.x = x;
        this.y = y;
        this.velocidadY = velocidadY;
        this.enIsla = false;
        this.direccion = 1; // Comienza moviéndose a la derecha
        this.imgTortuga = Herramientas.cargarImagen("imagenes/tortuga.png");
        this.escala = 0.11; // Ajusta la escala según necesites
        this.velocidadX =1.5;
        this.ancho = 40;
        this.alto = 30;
    }
    
    public void setLimites(double limiteIzquierdo, double limiteDerecho) {
        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
    }

    public Islas getIsla() {
        return this.islaActual;
    }
    
    public double getAncho() {
        return this.ancho;
    }
    public double getAlto() {
        return this.alto;
    }
    public void actualizar() {
        if (!enIsla) {
            // Caída de la tortuga
            this.y += this.velocidadY;
        } else {
            // Movimiento de ida y vuelta sobre la isla
            moverEnIsla(); // Usar este método para mover lateralmente
        }
    }

    public void setEnIsla(boolean enIsla, double limiteIzquierdo, double limiteDerecho, Islas isla) {
        this.enIsla = enIsla;
        this.limiteIzquierdo = limiteIzquierdo;
        this.limiteDerecho = limiteDerecho;
        this.islaActual = isla;
        this.direccionDerecha = true; // Por defecto se mueve hacia la derecha
    }


    public Islas getIslaActual() {
        return this.islaActual;
    }


    public void dibujarTortuga(Entorno entorno) {
        entorno.dibujarImagen(this.imgTortuga, this.x, this.y, 0, this.escala);
    }

    public void detener() {
        this.velocidadY = 0; // Detener la tortuga estableciendo la velocidad a 0
    }

    // Métodos para obtener la posición de la tortuga
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x; 
    }
    
    public void setY(double y) {
        this.y = y;
    }

    public boolean isEnIsla() {
        return enIsla;
    }
    public void setDireccionDerecha(boolean direccionDerecha) {
        this.direccionDerecha = direccionDerecha;
    }

    private void moverEnIsla() {
        // Cambia la dirección de la tortuga si alcanza los límites
        if (x <= limiteIzquierdo) {
            x = limiteIzquierdo; // Nuevo limite izquierda
            direccion = 1; // Cambia a derecha
        } else if (x >= limiteDerecho) {
            x = limiteDerecho; // Nuevo limite derecho
            direccion = -1; // Cambia a izquierda
        }

        x += direccion * velocidadX;
    }
    
    public double getY() {
        return y;
    }
}