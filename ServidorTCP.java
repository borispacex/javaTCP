import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorTCP {
	// Iniciamos
	public static void main(String[] args) {
		// Creamos e iniciamos el objeto servidor
		ServidorTCP Servidor = new ServidorTCP(8886);
		Servidor.iniciar();
	}

	// Declaramos las variables de tipo Socket
	ServerSocket sServidor;
	Socket sCliente;
	int puerto;
	PrintStream salida;
	Scanner entrada;
	String mensajeSolicitud = "";
	String mensajeRespuesta = "";

	// Constructor para enviarle el puerto
	public ServidorTCP(int p) {
		puerto = p;
	}

	// Funcion para iniciar el servidor
	public void iniciar() {
		try {
			// Se inicia el servidor
			sServidor = new ServerSocket(puerto);
			System.out.println(" - SERVIDOR TCP INICIADO - ");
			System.out.println("- Esperando Cliente -");

			// Se esperan las peticiones del cliente
			while (true) {
				// El servidor es aceptado
				sCliente = sServidor.accept();
				entrada = new Scanner(sCliente.getInputStream());
				salida = new PrintStream(sCliente.getOutputStream());
				// mensaje recibido
				mensajeSolicitud = entrada.nextLine();

				System.out.println("Solicitud del Cliente : " + mensajeSolicitud);
				// mensaje a enviar
				mensajeRespuesta = "Bienvenido " + mensajeSolicitud;
				salida.println(mensajeRespuesta);
			}
		} catch (Exception e) {
			e.printStackTrace();
			finalizar();
		} finally {
			finalizar();
		}
	}

	// Funcion para finalizar servidor
	public void finalizar() {
		try {
			// Finalizando los flujos y el socket
			salida.close();
			entrada.close();
			sServidor.close();
			sCliente.close();
			System.out.println("Conexion Finalizada...");
		} catch (Exception e) {
			// Imprimir error
			e.printStackTrace();
		}
	}
}
