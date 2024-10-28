package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Tortuga {
    private double x;
    private double y;
    private double velocidadY;
    private double velocidadX; // Velocidad horizontal de la isla
    boolean enIsla;
    private int direccion; // 1 para derecha, -1 para izquierda
    private double limiteIzquierdo;
    private double limiteDerecho;
    private Image imgTortuga;
    private double escala;
    private Islas islaActual;
    private boolean direccionDerecha; 
    
    public Tortuga(int x, int y, double velocidadY) {
        this.x = x;
        this.y = y;
        this.velocidadY = velocidadY;
        this.enIsla = false;
        this.direccion = 1; // Comienza moviéndose a la derecha
        this.imgTortuga = Herramientas.cargarImagen("imagenes/tortuga.png");
        this.escala = 0.11; // Ajusta la escala según necesites
        this.velocidadX = 2 * direccion;
    }

    public Islas getIsla() {
        return this.islaActual;
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
    
    public double getVelocidadX() {
        return this.velocidadX;
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
    
    public void moverConIsla() {
        if (this.enIsla && this.islaActual != null) {
            // Obtiene la velocidad de la isla y mueve la tortuga con la isla
            double velocidadIsla = this.islaActual.getVelocidad();
            this.x += velocidadIsla;
        }
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

    public void moverEnIsla() {
        // Movimiento de ida y vuelta sobre la isla
        this.x += direccion * 1; // Ajustar valocidad

        // Cambiar de dirección al llegar a los límites de la isla
        if (this.x <= limiteIzquierdo || this.x >= limiteDerecho) {
            direccion *= -1; // Cambia la dirección
        }
    }
    public void moverHorizontalmente(double velocidad) {
        if (this.enIsla) {
            // Intenta moverse a la izquierda o a la derecha según la velocidad
            this.x += velocidad;

            // Restringe el movimiento dentro de los límites de la isla
            if (this.x < this.limiteIzquierdo) {
                this.x = this.limiteIzquierdo;
            } else if (this.x > this.limiteDerecho) {
                this.x = this.limiteDerecho;
            }
        }
    }
    public double getY() {
        return y;
    }
}