# AndroidBeaconListener
App dedicada a la obtencion de datos emitidos por un beacon bluetooth.

La App recibe los datos de un beacon buscado por el nombre "fistro" y al recibirlos realiza un POST a una base de datos donde se guarda el valor de la medición junto con el tipo de medida que es, cuando se ha realizado y la posición.

Para hacerla funcionar simplemente es necesario descargar la aplicación y conceder los permisos bluetooth a la APP. Al pulsar el botón del servicio compenzará a recibir beacons y subirlos a la base de datos.
