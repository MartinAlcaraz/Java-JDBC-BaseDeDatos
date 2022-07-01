package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.model.Categoria;
import com.alura.jdbc.model.Producto;

/**
 * DAO (data access object)
 *  Esta clase se encarga del acceso a la base de datos de los productos.
 *
 */
public class ProductoDAO {

	final private Connection conexion;  

	public ProductoDAO(Connection conexion) {
		this.conexion = conexion;
	}

	public void guardar(Producto producto) {
		Integer cantidad = producto.getCantidad();
		Integer maxCantidad = 50; // para guardar por cada registro hasta 50 unidades.

		try {
			conexion.setAutoCommit(false); // evita el commit automatico cuando se ejecuta execute()

			final java.sql.PreparedStatement statement = conexion.prepareStatement(
					"INSERT INTO PRODUCTO(nombre, descripcion, cantidad, categoria_id) VALUES (?,?,?,?)",
					// La consulta sería INSERT INTO PRODUCTO(nombre,descripcion,cantidad,categoria_id) VALUES
					// ('vaso','vaso de
					// vidrio', 20, 3) Los datos string hay que declararlos entre comillas simples ''
					java.sql.Statement.RETURN_GENERATED_KEYS);

			try (statement) { // try with resource
				// el siguiente codigo hace que se guarden hasta 50 productos por cada registro
				do {
					int cantParaGuardar = Math.min(cantidad, maxCantidad);
					ejecutarRegistro(producto, cantParaGuardar, statement);
					cantidad -= cantParaGuardar;
				} while (cantidad > 0);
				conexion.commit(); // se confirma la consulta sql y se modifica la BBDD
				
				conexion.setAutoCommit(true);  // se vuelve a setear en true para que sea automatico el commit en los demás metodos que utilizan esta conexion.				
			} catch (SQLException e) {
				conexion.rollback(); // si ocurre un error no se ejecuta la modificacion en la base de datos y
										// deshace todos los cambios de la transaccion actual.
				
				conexion.setAutoCommit(true); // por si ocurre una excepcion.  
				throw new RuntimeException(e);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	private void ejecutarRegistro(Producto producto, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, cantidad);
		statement.setInt(4, producto.getCategoriaId());
		statement.execute();

		final ResultSet resultSet = statement.getGeneratedKeys();
		try (resultSet) {
			while (resultSet.next()) {
				producto.setId(resultSet.getInt(1)); // el indice 1 contiene el id generado en la consulta de insercion.
				System.out.println(producto);

			}
			// java.sql.Statement.RETURN_GENERATED_KEYS devuelve el id generado en la
			// insercion del producto

			// Retrieves any auto-generated keys created as a result of executing this
			// Statement object.
			// If this Statement object did not generate any keys, an empty ResultSetobject
			// is returned.
		}
	}

	public List<Producto> listar() {
		
		try {
			final java.sql.PreparedStatement statement = conexion
					.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			try (statement) {
				statement.execute();

				ResultSet resultSet = statement.getResultSet(); // si el resultado es true, es un listado y devuelve el
																// resultado de la consulta
				List<Producto> listaResultado = new ArrayList<>();
				while (resultSet.next()) {
					Producto fila = new Producto(resultSet.getString("NOMBRE"), resultSet.getString("DESCRIPCION"),
							resultSet.getInt("CANTIDAD"), resultSet.getInt("ID"));

					listaResultado.add(fila);
				}
				return listaResultado;
			} 
		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}
	
	public int eliminar(Integer id) {
		//final Connection conexion = new ConnectionFactory().conectar();
		try{
			final java.sql.PreparedStatement statement = conexion.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
			try (statement) {      // try with resource: cierra al conexion automaticamente cuando termina la ultima instruccion
				statement.setInt(1, id);
				statement.execute();
				return statement.getUpdateCount(); // devuelve cuantas filas fueron modificadas luego de realizar la consulta
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int modificar(Producto producto) {
		
		try  { 
			java.sql.PreparedStatement statement = conexion.prepareStatement(
					"UPDATE PRODUCTO SET" + " NOMBRE = ? ,DESCRIPCION = ? ,CANTIDAD = ? WHERE ID = ?");
			try (statement) {
				statement.setString(1, producto.getNombre());
				statement.setString(2, producto.getDescripcion());
				statement.setInt(3, producto.getCantidad());
				statement.setInt(4, producto.getId());
				statement.execute();
				return statement.getUpdateCount(); // devuelve la cantidad de registros en la tabla
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Producto> listar(Integer categoriaId) {
		List<Producto> lista = new ArrayList<>();
		try {
			final java.sql.PreparedStatement statement = conexion
					.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM CATEGORIA WHERE ID = ?");
			try (statement) {
				statement.setInt(1, categoriaId);
				statement.execute();

				ResultSet resultSet = statement.getResultSet(); 
																
				while (resultSet.next()) {
					Producto producto = new Producto(resultSet.getString("NOMBRE"), resultSet.getString("DESCRIPCION"),
							resultSet.getInt("CANTIDAD"), resultSet.getInt("ID"));

					lista.add(producto);
				}
				return lista;
			} 
		} catch (SQLException e) {
			throw new RuntimeException();
		}

	}
}
