package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.model.Categoria;
import com.alura.jdbc.model.Producto;

public class CategoriaDAO {

	private Connection conexion;

	public CategoriaDAO(Connection conexion) {
		this.conexion = conexion;
	}

	public List<Categoria> listar() {
		List<Categoria> lista = new ArrayList<>();

		try {
			final PreparedStatement statement = conexion.prepareStatement("SELECT ID, NOMBRE FROM CATEGORIA");
			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				try (resultSet) {
					while (resultSet.next()) {
						Categoria categoria = new Categoria(resultSet.getInt("ID"), resultSet.getString("NOMBRE"));
						lista.add(categoria);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return lista;
	}

	public List<Categoria> listarConProductos() {
		List<Categoria> lista = new ArrayList<>(); // lista de categorias, donde cada categoria tiene una lista de productos
		/**
		 * Se crea una lista de categorias y dentro de cada categoria una lista de productos con una sola solicitud.
		 */
		try {
			final PreparedStatement statement = conexion.prepareStatement
					("SELECT C.ID, C.NOMBRE, P.ID, P.NOMBRE, P.CANTIDAD FROM CATEGORIA C INNER JOIN PRODUCTO P ON P.CATEGORIA_ID = C.ID");
			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				try (resultSet) {
					while (resultSet.next()) {						
						Integer categoriaId = resultSet.getInt("C.ID");
						String categoriaNombre = resultSet.getString("C.NOMBRE");
						
						Categoria categoria = lista.stream()
								.filter(cat ->cat.getId().equals(categoriaId)).findAny() //si existe alguna categoria  en la lista igual a la categoriaId asignarla a la lista
								.orElseGet(()-> {										// si no
									Categoria cat = new Categoria(categoriaId, categoriaNombre);	// crea una nueva categoria, la agrega a la lista y la asigna a la variable categoria
									lista.add(cat);
									return cat;
								});
						Producto producto = new Producto(resultSet.getString("P.NOMBRE"), 
								resultSet.getInt("P.CANTIDAD"), resultSet.getInt("P.ID"));
						
						categoria.agregar(producto);  // en cada clase categoria se agrega un producto a la lista de productos de cada categoria.						
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return lista;
	}

}
