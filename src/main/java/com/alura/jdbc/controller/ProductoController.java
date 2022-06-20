package com.alura.jdbc.controller;


import java.sql.SQLException;
import java.util.List;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.model.Categoria;
import com.alura.jdbc.model.Producto;

// La clase Controller es el nexo entre la view y la base de datos

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	public ProductoController() {
		 this.productoDAO = new ProductoDAO(new ConnectionFactory().conectar()); // cuando inicia el programa se abre una conexion que sigue abierta durante  la ejecucion de todo el programa, en lugar de abrir una conexion en cada solicitud.
	}

	public int modificar(Producto producto) throws SQLException {
		return productoDAO.modificar(producto);
	}

	public int eliminar(Integer id) {
		return productoDAO.eliminar(id);
	}

	public List<Producto> listar() {		
		return productoDAO.listar();
	}
	
	public List<Producto> listar(Categoria categoria) {		
		return productoDAO.listar(categoria.getId());
	}
	
	public void guardar(Producto producto, Integer CategoriaId){
		producto.setCategoriaId(CategoriaId);
		productoDAO.guardar(producto);		
	}
}
