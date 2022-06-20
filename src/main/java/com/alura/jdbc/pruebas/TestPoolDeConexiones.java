package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Timer;

import com.alura.jdbc.factory.ConnectionFactory;

public class TestPoolDeConexiones {

	public static void main(String[] args) throws SQLException {
		 
		/* simulacion de multiples conexiones a la base de datos
		 * Para comporbar si funciona hay que revisar la base de 
		 * datos en la consola. 
		 * En la consola de MySQL ejecutar el siguiente comando: 
		 * show processlist;
		 */
		
//		ConnectionFactory conectionFactory = new ConnectionFactory();
//		for (int i = 0; i < 20 ; i++) {
//			Connection conexion = conectionFactory.conectar();
//			System.out.println("Abriendo conexion "+(i+1));
//		}
		
	}
}
