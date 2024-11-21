/*
 * Paradigmas de Programación
 * José Enrique González Sánchez
 * Grupo 512
 * 20-11-2024
 * Práctica 8
 */

package gatitopedia.utilerias;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import gatitopedia.excepciones.ManejoDeErroresGP;

public class ManejadorDeArchivos {

	public static void copiarArchivo(String rutaOrigen, String rutaDestino) throws ManejoDeErroresGP {

		try (BufferedInputStream lector = new BufferedInputStream(new FileInputStream(new File(rutaOrigen)));
				BufferedOutputStream escritor = new BufferedOutputStream(new FileOutputStream(new File(rutaDestino)))) {

			byte[] buffer = new byte[1024];
			int bytesLeidos = lector.read(buffer);

			while (bytesLeidos > 0) {
				escritor.write(buffer, 0, bytesLeidos);
				bytesLeidos = lector.read(buffer);
			}
			
		} catch (IOException e) {
			throw new ManejoDeErroresGP(ManejoDeErroresGP.ERROR_CP);
		}
	}
}