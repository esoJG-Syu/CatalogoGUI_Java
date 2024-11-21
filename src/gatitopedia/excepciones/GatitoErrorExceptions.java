/*
 * Paradigmas de Programaci�n
 * Jos� Enrique Gonz�lez S�nchez
 * Grupo 512
 * 20-11-2024
 * Pr�ctica 8
 */

package gatitopedia.excepciones;

public class GatitoErrorExceptions extends Exception {

	private static final long serialVersionUID = 1L;

	// Validaci�n de error para el m�todo de copia de archivos
	public static final String[] ERROR_CP = {
			"Hubo un problema al intentar hacer la copia de la imagen.\nSe asignara la imagen por defecto",
			"Error al copiar imagen" };

	private String titulo;

	// Constructor que recibe un arreglo de Strings
	public GatitoErrorExceptions(String[] msg) {
		super(msg[0]); // Mensaje de error
		setTitulo(msg[1]); // T�tulo del error
	}

	// Getter para el t�tulo
	public String getTitulo() {
		return titulo;
	}

	// Setter para el t�tulo
	private void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
