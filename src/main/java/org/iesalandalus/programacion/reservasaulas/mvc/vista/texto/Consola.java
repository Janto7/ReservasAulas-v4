package org.iesalandalus.programacion.reservasaulas.mvc.vista.texto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Permanencia;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private Consola() {
	}

	public static void mostrarMenu() {
		System.out.println();
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}

	public static void mostrarCabecera(String mensaje) {
		String mensajeCabecera = "| " + mensaje + " |";
		String formatoStr = "%0" + mensajeCabecera.length() + "d%n";
		System.out.printf("\n" + String.format(formatoStr, 0).replace("0", "="));
		System.out.printf("%s%n", mensajeCabecera);
		System.out.printf(String.format(formatoStr, 0).replace("0", "=") + "\n");
	}

	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}

	// A partir de un nombre obtenemos un objeto de tipo Aula
	public static Aula leerAula() {
		System.out.print("Introduce el nombre del aula: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el número de puestos del aula: ");
		int puestos = Entrada.entero();
		return new Aula(nombre, puestos);
	}

	// Lee y devuelve el número de puestos que va a tener un aula
	public static int leerNumeroDePuestos() {
		System.out.print("Introduce el número de puestos del aula: ");
		int puestos = Entrada.entero();
		return puestos;
	}

	public static Aula leerAulaFicticia() {
		System.out.print("Introduce el nombre del aula: ");
		String nombre = Entrada.cadena();
		return Aula.getAulaFicticia(nombre);
	}

	public static String leerNombreAula() {

		System.out.print("Introduce el nombre del aula: ");
		String nombreAula = Entrada.cadena();
		return nombreAula;
	}

	public static Profesor leerProfesor() {

		System.out.print("Introduce el nombre del profesor: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el correo del profesor: ");
		String correo = Entrada.cadena();
		System.out.print("Introduce el teléfono del profesor: ");
		String telefono = Entrada.cadena();
		Profesor profesor = (telefono == null || telefono.trim().equals("")) ? new Profesor(nombre, correo)
				: new Profesor(nombre, correo, telefono);

		return profesor;
	}

	public static String leerNombreProfesor() {
		System.out.print("Introduce el nombre del profesor: ");
		String nombreProfesor = Entrada.cadena();
		return nombreProfesor;
	}

	// A partir de un correo obtenemos un profesor
	public static Profesor leerProfesorFicticio() {
		System.out.print("Introduce el correo del profesor: ");
		return Profesor.getProfesorFicticio(Entrada.cadena());
	}

	public static Tramo leerTramo() {
		System.out.print("Introduce el tramo de la reserva (0.- Mañana, 1.- Tarde): ");
		int tramoReserva = Entrada.entero();
		Tramo tramo = null;
		if (tramoReserva < 0 || tramoReserva >= Tramo.values().length) {
			System.out.println("ERROR: La opción elegida no corresponde con ningún tramo.");
		} else {
			tramo = Tramo.values()[tramoReserva];
		}
		return tramo;
	}

	public static LocalDate leerDia() {

		LocalDate dia = null;
		do {
			System.out.printf("Introduza una fecha(dd/MM/yyyy):");
			String fechaStr = Entrada.cadena();

			try {
				dia = LocalDate.parse(fechaStr, FORMATO_DIA);
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: El formato de la fecha no es correcto. Formato correcto (dd/MM/yyyy)");
				dia = null;
			}

		} while (dia == null);

		return dia;

	}

	/*
	 * En función de la permanencia elegida por el usuario, devolverá una
	 * permanencia de tipo tramo o de tipo hora
	 */
	public static Permanencia leerPermanencia() {
		int ordinalPermanencia = Consola.elegirPermanencia();
		LocalDate dia = leerDia();
		Permanencia permanencia = null;
		if (ordinalPermanencia == 0) {
			Tramo tramo = leerTramo();
			permanencia = new PermanenciaPorTramo(dia, tramo);
		} else if (ordinalPermanencia == 1) {
			// Utilizamos el método leerHora() para leer una permanencia por hora.
			LocalTime hora = leerHora();
			permanencia = new PermanenciaPorHora(dia, hora);
		}
		return permanencia;
	}

	public static int elegirPermanencia() {
		int ordinalPermanencia;
		do {
			System.out.print("\nElige una permanencia (0.- Por Tramo, 1.- Por Hora): ");
			ordinalPermanencia = Entrada.entero();
		} while (ordinalPermanencia < 0 || ordinalPermanencia > 1);
		return ordinalPermanencia;
	}

	private static LocalTime leerHora() {
		LocalTime hora = null;
		String formato = "HH:mm";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formato);
		do {
			System.out.printf("Introduce la hora (%s): ", formato);
			String horaStr = Entrada.cadena();
			try {
				hora = LocalTime.parse(horaStr, dtf);
			} catch (DateTimeParseException e) {
				System.out.println("ERROR: El formato de la hora no es correcto.");
				hora = null;
			}

		} while (hora == null);

		return hora;
	}

	/*
	 * A partir de un aula ficticia, de un profesor ficticio y de una permanencia,
	 * devuelve una reserva
	 */
	public static Reserva leerReserva() {
		Profesor profesor = leerProfesorFicticio();
		Aula aula = leerAulaFicticia();
		Permanencia permanencia = leerPermanencia();
		return new Reserva(profesor, aula, permanencia);
	}

	/*
	 * A partir de un aula ficticia y de una permanencia, devuelve reserva ficticia.
	 */
	public static Reserva leerReservaFicticia() {
		return Reserva.getReservaFicticia(leerAulaFicticia(), leerPermanencia());
	}

}