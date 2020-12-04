# Bitcoin-Wallet
This Android project is a Wallet of Bitcoins.

## Faucet: envío de bitcoins ficticios

Para el envío de bitcoins ficticios utilizarás este sitio https://bitcoinfaucet.uo1.net/send.php
Allí deberás completar el “address” a donde querés enviar los bitcoins y por otro lado la
cantidad de bitcoins a enviar. Ten en cuenta de enviar siempre cantidades bajas como
0.00001 bitcoins por ejemplo (ya que sino el sitio web podría restringirte la cantidad a enviar y
anular la transacción).

Esto servirá para que puedas darle vida al estado de tu wallet y al historial de transacciones
con datos a mostrar.


### Arquiitectura:

Clean Architecure modularizado, patrón de presentación MVVM.


### Librerías utilizadas:

*	ViewModel + LiveData.
*	Dagger, para inyección de dependencias.
*	Coroutines, para manejo de llamadas asincronas.
*	Retrofit, para las llamadas al servicio.
*	Room, para generar base de datos.
*	Kotlin Flow, para mantener actualiza la vista ante cualquier cambio de la base de datos.
*	Navigation.
*	WorkManager, para mantener actualizados los datos.
*	Zxing, para creación de QR.
*	Mockito y Coroutines Test para los Test Unitarios.
