package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.vista.FactoriaVista;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.controlador.IControlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;


/*
 * @Author: José Antonio Del Rey Martínez, IES AL-ÁNDALUS, ALMERÍA
 * GitHub: Janto7
 * Tarea Online 9
 */
public class MainApp {

	public static void main(String[] args) {

		IModelo modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		IVista vista = procesarArgumentosVista(args);

		IControlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}

	private static IVista procesarArgumentosVista(String[] args) {

		IVista vista = FactoriaVista.GRAFICA.crear();

		for (String argumento : args) {

			if (argumento.equalsIgnoreCase("-vgrafica")) {

				vista = FactoriaVista.GRAFICA.crear();

			} else if (argumento.equalsIgnoreCase("-vtexto")) {

				vista = FactoriaVista.TEXTO.crear();
			}
		}

		return vista;
	}
}