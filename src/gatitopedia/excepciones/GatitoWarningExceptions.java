package gatitopedia.excepciones;

public class GatitoWarningExceptions extends Exception {

	private static final long serialVersionUID = 1L;

	// Validación para números libres: edad y peso
	public static final String[] RANGO_EDAD = { "La edad del gato debe ser de 0 a 20 años.", "Rango de Edad Incorrecto" };
	public static final String[] ERROR_PARSEOE = { "El campo de edad debe contener únicamente números.",
			"Formato de Edad Incorrecto" };

	public static final String[] RANGO_PESO = { "El peso del gato no puede ser 0 kg.", "Rango de Peso Incorrecto" };
	public static final String[] ERROR_PARSEOP = { "El campo de peso debe contener únicamente números.",
			"Formato de Peso Incorrecto" };

	// Validación para número con rango: precio
	public static final String[] RANGO_PRECIO = { "El precio de adopción debe ser de 1 a 10000 pesos.",
			"Rango de Precio Incorrecto" };
	public static final String[] ERROR_PARSEOPR = { "El campo del precio debe contener únicamente números.",
			"Formato de Precio Incorrecto" };

	// Validación de texto en fromato libre
	public static final String[] NOMBRE_OBLIGATORIO = {
			"El nombre del gato es obligatorio. Por favor ingrese un nombre.", "Nombre requerido" };

	// Validación para texto con formato predefinido
	public static final String[] ID_OBLIGATORIO = {
			"El identificador del gato no puede estar vacío. Por favor, ingrese un valor.", "Identifcador requerido" };
	public static final String[] ID_FORMATO = {
			"El identificador del gato no cumple con el formato esperado.\nAsegúrese de que tenga 3 letras mayúsculas, 4 dígitos y\ntermine con 'H' o 'M'.",
			"Identificador incorrecto" };

	// Validacón de dato obtenido de opciones mutuamente excluyentes dinámicas:
	// personalidad
	public static final String[] PERSONALIDAD_NO_VACIO = { "No se permiten valores vacíos en el campo 'Personalidad'.",
			"Campo vacío no permitido" };
	
	// Validación de opciones no excluyentes dinámicas: color del gato
	public static final String[] COLOR_OBLIGATORIO = { "Debe agregar al menos un color a la lista.",
	"Colores requeridos" };
	

	private String titulo;

	// Constructor que recibe un arreglo de Strings
	public GatitoWarningExceptions(String[] msg) {
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
