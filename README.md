# M4-SPRING-T4
# Taller: API para Manejo de Cuenta 4
> **Larry M. Ramírez - Coach Técnico**

## Objetivo.
Implementar la conexión al sistema de gestión de bases de datos PostgreSQL que persista cuentas bancarias y las transacciones asociadas a las mismas.

## Instrucciones
- Crear una entidad llamada `Transaccion` que almacene el tipo de transacción (retiro, depósito), monto, y fecha de la transacción.
- Crear una entidad llamada `Cuenta` que almacene los datos asociados a una cuenta bancaria (información del cliente, saldo,  historial de transacciones, etc).
- Establecer de forma adecuada el mapeo de las entidades principal e inversa con las respectivas anotaciones `@OneToMany` y `@ManyToOne`.
- Implementar un servicio y controlador para manejar las transacciones:
	- Un cliente puede realizar depósitos y retiros, afectando su saldo.
	- Registrar una transacción cada vez que se realice una operación.
- Realizar pruebas de depósito y retiro, asegurándose de que las excepciones se manejen correctamente y el registro de transacciones se esté almacenando de forma adecuada.
- Verificar que el saldo se actualice después de cada operación exitosa.

**Nota:** Los talleres deben entregarse por medio de la estrategia establecida para la formación, por medio de un Pull Request desde el repositorio Fork hacia la rama main del repositorio del taller. 
