# Chat con sockets

Consiste en un chat *Java* con servidor y clientes en el que el servidor tendrá que atender y conectar a los clientes. Para realizar la práctica usaremos sockets e hilos.

# Requisitos

* Deben controlarse las condiciones de error para que cuando un cliente caiga o se desconecte, se actualice el número de conexiones.
* No se pide crear chats “privados” sino sólo el general
* Cuando un cliente se conecta al servidor su textArea estará vacío inicialmente.

## Servidor

[ ] Se arranca una ventana donde se muestra el número de conexiones actuales.
[ ] Contiene un textArea dónde se visualiza la información de quién sale y entra del chat y lo que escribe.
[ ] Un botón Salir que desconecta el servidor.
[ ] Se debe imponer un máximo de conexiones (por ejemplo 5).
[ ] Para cada mensaje del usuario se muestra su nombre de usuario y el mensaje que ha escrito.
[ ] Debe aparecer como información el número de puerto que el servidor está usando y el número de clientes conectados.

## Cliente

[ ] En primer lugar, se le pide un nombre o nick, e intenta conectarse al servidor.
[ ] Si todo va bien se habilita en esta misma ventana o bien se abre una ventana donde podrá escribir mensajes y enviarlos a todos.
[ ] Debe aparece el número de clientes que hay conectados en el chat.

[ ] Un textArea general en el que verá lo que se escribe en el chat
[ ] Un textArea pequeño dónde puede escribir el texto (mensajes) a enviar.
[ ] Un botón de Enviar, que envía el texto al servidor chat.
[ ] Un botón VerUsuarios, que mostrará en el textArea un listado de los clientes actuales conectados al chat.
[ ] Un botón Limpiar, que resetea la información del textArea.
[ ] Un botón de Salir, que desconecta al cliente.