import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {

	public static void main(String[] args) {
		ClienteTCP Cliente = new ClienteTCP("127.0.0.1", 8886);
		Cliente.iniciar();
	}

	Socket sCliente;
	Scanner entrada;
	PrintStream salida;
	String direccion;
	int puerto;
	String mensajeSolicitud = "";
	String mensajeRespuesta = "";

	public ClienteTCP(String d, int p) {
		direccion = d;
		puerto = p;
	}

	public void iniciar() {
		try {
			boolean respu = false;
			do {
				// Estableciendo conexion con el servidor
				sCliente = new Socket(direccion, puerto);
				System.out.println(" - CONEXION INICIADA - ");
				// Mostramos el ip y el puerto que nos ayudaran a la conexion
				System.out.println("Conectado a: " + sCliente.getRemoteSocketAddress());
				// Obtengo una regerencia a los flujos de datos de entrada y salida
				salida = new PrintStream(sCliente.getOutputStream());
				entrada = new Scanner(sCliente.getInputStream());

				// Este bloque de codigo se encarga de enviar mensajes al servidor
				Scanner lectura = new Scanner(System.in);
				System.out.print("\nCual es tu solicitud : ");
				mensajeSolicitud = lectura.nextLine();
				salida.println(mensajeSolicitud);
				// Mensaje recibido
				mensajeRespuesta = entrada.nextLine();
				System.out.println("Respuesta del servidor : " + mensajeRespuesta);

				System.out.print("desea hacer otra consulta? si/no : ");
				String resp = lectura.next();
				if (resp.equals("si")) {
					respu = true;
				} else {
					respu = false;
				}
			} while (respu);

		} catch (Exception e) {
			e.printStackTrace();
			finalizar();
		}
	}

	public void finalizar() {
		try {
			salida.close();
			entrada.close();
			sCliente.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
