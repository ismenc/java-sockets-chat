# Chat con sockets

Consiste en un chat *Java* con servidor y clientes en el que el servidor tendrá que atender y conectar a los clientes. Para relizar la práctica usaremos:
* Java Swing para crear la interfaz gráfica.
* Sockets para conectar las aplicaciones del servidor y clientes.
* Hilos para que el servidor pueda atender a múltiples clientes.

# Especificaciones

* Se deben controlar los casos de error para el número de conexiones:
  * Cliente desconectado
  * Cliente caido (connection timeout)
* No se pide crear chats “privados”, sólo el general
* Cuando un cliente se conecta al servidor su textArea estará vacío inicialmente.

## Servidor

- [x] Se arranca una ventana donde se muestra el número de conexiones actuales.

- [x] Contiene un textArea dónde se visualiza la información de quién sale y entra del chat y lo que escribe.

- [x] Un botón Salir que desconecta el servidor.

- [x] Se debe imponer un máximo de conexiones (por ejemplo 5).

- [x] Para cada mensaje del usuario se muestra su nombre de usuario y el mensaje que ha escrito.

- [x] Debe aparecer el número de puerto que el servidor está usando y el número de clientes conectados.

## Cliente

- [x] En primer lugar se le pide un nick e intenta conectarse al servidor.

- [x] Si todo va bien se habilita esta misma ventana dónde se podrá escribir mensajes y enviarlos.

- [x] Debe aparece el número de clientes que hay conectados en el chat.


- [x] Un textArea general en el que verá lo que se escribe en el chat

- [x] Un textArea pequeño dónde puede escribir el texto (mensajes) a enviar.

- [x] Un botón de Enviar que envía el texto al servidor chat.

- [x] Un botón VerUsuarios que mostrará en el textArea un listado de los clientes conectados al chat.

- [x] Un botón Limpiar que resetea la información del textArea.

- [x] Un botón de Salir que desconecta al cliente.