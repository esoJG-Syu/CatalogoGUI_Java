/*
 * Paradigmas de Programación
 * José Enrique González Sánchez
 * Grupo 512
 * 20-11-2024
 * Práctica 8
 */

package gatitopedia.excepciones;

public class GatitoErrorExceptions extends Exception {

	private static final long serialVersionUID = 1L;

	// Validación de error para el método de copia de archivos
	public static final String[] ERROR_CP = {
			"Hubo un problema al intentar hacer la copia de la imagen.\nSe asignara la imagen por defecto",
			"Error al copiar imagen" };

	private String titulo;

	// Constructor que recibe un arreglo de Strings
	public GatitoErrorExceptions(String[] msg) {
		super(msg[0]); // Mensaje de error
		setTitulo(msg[1]); // Título del error
	}

	// Getter para el título
	public String getTitulo() {
		return titulo;
	}

	// Setter para el título
	private void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
