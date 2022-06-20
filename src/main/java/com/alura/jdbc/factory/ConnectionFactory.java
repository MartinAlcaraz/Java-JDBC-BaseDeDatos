package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource; // en el archivo pom.xml
/*
 * Patrón de diseño llamado factory method. 
 *  El factory method tiene como objetivo encapsular el código de creación 
 * de un objeto específico, centralizando la lógica en un solo punto.
 */
public class ConnectionFactory {
/** Por qué usar un pool de conexiones?
 *  En un escenario donde múltiples clientes pueden acceder a una misma aplicación simultáneamente 
 * lo mejor sería reutilizar un conjunto de conexiones de tamaño fijo o dinámico. En lugar
 * de abrir una conexion por cada cliente se abre una cantidad específica de conexiones y se
 * reutilizan.
 */
	private DataSource dataSource;
	/**
	 * En lugar de crear una conexion con el DriverManager
	 * se toma una conexion del pool de conexiones del dataSource.
	 */
	public ConnectionFactory() {							//	 la dependencia cp30 provee este metodo.
		var pooledDataSource = new ComboPooledDataSource(); // com.mchange.v2.c3p0.ComboPooledDataSource.ComboPooledDataSource()
		
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("root1234");
		pooledDataSource.setMaxPoolSize(10);  // maximo de conexiones que se mantendrán abiertas
								// de esta forma se evita que se sature la base de datos.
		this.dataSource = pooledDataSource;
	}									
	
	public Connection conectar() {
		try {
			return  this.dataSource.getConnection(); // se obtiene una conexion del pool de conexiones
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}  
	}
	
	/**
	 * Utilizar el pool de conexiones es una buena práctica;

El pool de conexiones controla la cantidad de conexiones abiertas entre la aplicación y la base de datos;

Es normal que haya un mínimo y un máximo de conexiones.

De la misma forma que hay, en JDBC, una interfaz para representar la 
conexión (java.sql.Connection), también hay una interfaz que representa el 
pool de conexiones (javax.sql.DataSource);

C3P0 es una implementación Java de un pool de conexiones.
	 */
	

	// // se crea una conexion unica en cada solicitud
//	public Connection conectar() throws SQLException {
//		
//		System.out.println("abriendo conexion");
//		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC", 
//				"root", 
//				"root1234");
//		return  conexion;	
//	}
		
	
}
