package gatitopedia.excepciones;

public class GatitoWarningExceptions extends Exception {

	private static final long serialVersionUID = 1L;

	// Validaci�n para n�meros libres: edad y peso
	public static final String[] RANGO_EDAD = { "La edad del gato debe ser de 0 a 20 a�os.", "Rango de Edad Incorrecto" };
	public static final String[] ERROR_PARSEOE = { "El campo de edad debe contener �nicamente n�meros.",
			"Formato de Edad Incorrecto" };

	public static final String[] RANGO_PESO = { "El peso del gato no puede ser 0 kg.", "Rango de Peso Incorrecto" };
	public static final String[] ERROR_PARSEOP = { "El campo de peso debe contener �nicamente n�meros.",
			"Formato de Peso Incorrecto" };

	// Validaci�n para n�mero con rango: precio
	public static final String[] RANGO_PRECIO = { "El precio de adopci�n debe ser de 1 a 10000 pesos.",
			"Rango de Precio Incorrecto" };
	public static final String[] ERROR_PARSEOPR = { "El campo del precio debe contener �nicamente n�meros.",
			"Formato de Precio Incorrecto" };

	// Validaci�n de texto en fromato libre
	public static final String[] NOMBRE_OBLIGATORIO = {
			"El nombre del gato es obligatorio. Por favor ingrese un nombre.", "Nombre requerido" };

	// Validaci�n para texto con formato predefinido
	public static final String[] ID_OBLIGATORIO = {
			"El identificador del gato no puede estar vac�o. Por favor, ingrese un valor.", "Identifcador requerido" };
	public static final String[] ID_FORMATO = {
			"El identificador del gato no cumple con el formato esperado.\nAseg�rese de que tenga 3 letras may�sculas, 4 d�gitos y\ntermine con 'H' o 'M'.",
			"Identificador incorrecto" };

	// Validac�n de dato obtenido de opciones mutuamente excluyentes din�micas:
	// personalidad
	public static final String[] PERSONALIDAD_NO_VACIO = { "No se permiten valores vac�os en el campo 'Personalidad'.",
			"Campo vac�o no permitido" };
	
	// Validaci�n de opciones no excluyentes din�micas: color del gato
	public static final String[] COLOR_OBLIGATORIO = { "Debe agregar al menos un color a la lista.",
	"Colores requeridos" };
	

	private String titulo;

	// Constructor que recibe un arreglo de Strings
	public GatitoWarningExceptions(String[] msg) {
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
