/*
 * Paradigmas de Programaci�n
 * Jos� Enrique Gonz�lez S�nchez
 * Grupo 512
 * 20-11-2024
 * Pr�ctica 8
 */

package gatitopedia.dominio;

import java.util.ArrayList;
import java.util.Date;

import gatitopedia.excepciones.GatitoWarningExceptions;

public class Gato {
	private int edadGato; // N�mero libre
	private float pesoGato;
	private float precioAdopcion; // N�mero con rango
	private String nombreGato; // Texto en formato libre
	private String identificadorGato; // Texto con formato predefinido
	private Date fechaNacimiento; // Fecha
	private String sexoGato; // Dato mutuamente excluyente fijo: "Macho" o "Hembra"
	private boolean gatoEsterilizado; // Dato mutuamente excluyente fijo: "S�" o "No"
	private String personalidad; // Dato mutuamente excluyente din�mico
	/*
	 * Dato multivalorado no excluyentes fijas "habitosEntrenamiento" Uso de caja de
	 * arena, Uso de rascador, Caminar con correa, Obedecer ordenes.
	 */
	private ArrayList<String> habitosEntrenamiento;
	private ArrayList<String> colorGato; // Dato multivalorado no excluyente din�mico
	private String rutaImagen;

	public Gato() {
		// Valores por defecto para las variables miembro
		this.edadGato = 0;
		this.pesoGato = 0.0f;
		this.precioAdopcion = 0.0f;
		this.nombreGato = "";
		this.identificadorGato = "";
		this.fechaNacimiento = null;
		this.sexoGato = "";
		this.gatoEsterilizado = false;
		this.personalidad = "";
		this.habitosEntrenamiento = null;
		this.colorGato = null;
		this.rutaImagen = "";
	}

	// Edad Gato
	public int getEdadGato() {
		return edadGato;
	}

	public void setEdadGato(String edadGato) throws GatitoWarningExceptions {
		/*  PENDIENTE
        if (edadGato == null || edadGato.trim().isEmpty()) {
            throw new ExcepcionesGatitopedia(ExcepcionesGatitopedia.CVACIO_EDAD);
        }
        */
		try {
			int edadInt = Integer.parseInt(edadGato.trim());
			setEdadGato(edadInt);
		} catch(NumberFormatException e) {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.ERROR_PARSEOE);
		}
	}

    public void setEdadGato(int edadGato) throws GatitoWarningExceptions {

    	if (edadGato < 0 || edadGato > 20) {
            throw new GatitoWarningExceptions(GatitoWarningExceptions.RANGO_EDAD);
        }

    	this.edadGato = edadGato;
    }

	// Peso Gato
	public float getPesoGato() {
		return pesoGato;
	}

	public void setPesoGato(String pesoGato) throws GatitoWarningExceptions {
        
		try {
    		float pesoFloat = Float.parseFloat(pesoGato.trim());
    		setPesoGato(pesoFloat);
		} catch(NumberFormatException e) {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.ERROR_PARSEOP);
		}
	}

	public void setPesoGato(float pesoGato) throws GatitoWarningExceptions {
		if (pesoGato > 0) {
			this.pesoGato = pesoGato;
		} else {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.RANGO_PESO);
		}
	}

	// Precio Gato
	public float getPrecioAdopcion() {
		return precioAdopcion;
	}

	public void setPrecioAdopcion(String precioGato) throws GatitoWarningExceptions {
		try {
			float precioFloat = Float.parseFloat(precioGato.trim());
			setPrecioAdopcion(precioFloat);
		} catch(NumberFormatException e) {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.ERROR_PARSEOPR);
		}
	}

	public void setPrecioAdopcion(float precioAdopcion) throws GatitoWarningExceptions {
		if (precioAdopcion > 0 && precioAdopcion <= 10000) {
			this.precioAdopcion = precioAdopcion;
		} else {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.RANGO_PRECIO);
		}
	}

	// Nombre Gato
	public String getNombreGato() {
		return nombreGato;
	}

	public void setNombreGato(String nombreGato) throws GatitoWarningExceptions {
		String nombreLimpio = nombreGato.trim();
		if (!nombreLimpio.isEmpty()) {
			this.nombreGato = nombreLimpio;
		} else {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.NOMBRE_OBLIGATORIO);
		}
	}

	// Identificador Gato
	public String getIdentificadorGato() {
		return identificadorGato;
	}

	public void setIdentificadorGato(String idGato) throws GatitoWarningExceptions {
		String idLimpio = idGato.trim();
		
		if(idLimpio.isEmpty()) {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.ID_OBLIGATORIO);
		}
		
		String regex = "[A-Z]{3}[0-9]{4}[HM]";
		boolean comprobarFormato = idLimpio.matches(regex);

		if (comprobarFormato) {
			this.identificadorGato = idLimpio;
		} else {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.ID_FORMATO);
		}
	}

	// Nacimiento Gato
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	// Sexo Gato
	public String getSexoGato() {
		return sexoGato;
	}

	public void setSexoGato(String sexoGato) throws GatitoWarningExceptions {
			this.sexoGato = sexoGato;
	}

	// Esterilizaci�n Gato
	public boolean getGatoEsterilizado() {
		return gatoEsterilizado;
	}

	public void setGatoEsterilizado(boolean gatoEsterilizado) throws GatitoWarningExceptions {
		this.gatoEsterilizado = gatoEsterilizado;
	}

	// Personalidad Gato
	public String getPersonalidad() {
		return personalidad;
	}

	public void setPersonalidad(String personalidad) throws GatitoWarningExceptions {
		
		if(personalidad.isEmpty()) {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.PERSONALIDAD_NO_VACIO);
		}
		this.personalidad = personalidad;
	}

	// H�bitos Entrenamiento
	public ArrayList<String> getHabitosEntrenamiento() {
		return habitosEntrenamiento;
	}

	public void setHabitosEntrenamiento(ArrayList<String> habitosEntrenamiento) {
		this.habitosEntrenamiento = habitosEntrenamiento;
	}

	// Color Gato
	public ArrayList<String> getColorGato() {
		return colorGato;
	}

	public void setColorGato(ArrayList<String> colorGato) throws GatitoWarningExceptions {
		if(colorGato.isEmpty()) {
			throw new GatitoWarningExceptions(GatitoWarningExceptions.COLOR_OBLIGATORIO);
		}
		this.colorGato = colorGato;
	}

	// Ruta imagen
	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	@Override
	public String toString() {
		return identificadorGato + " " + nombreGato + " " + edadGato + " " + pesoGato;
	}
}
