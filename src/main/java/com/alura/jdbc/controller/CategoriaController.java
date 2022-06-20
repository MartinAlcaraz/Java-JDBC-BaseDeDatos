package com.alura.jdbc.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.model.Categoria;

public class CategoriaController {

	private CategoriaDAO categoriaDAO;
	
	public CategoriaController() {
		ConnectionFactory factory = new ConnectionFactory();
		Connection conexion = factory.conectar();
		this.categoriaDAO = new CategoriaDAO(conexion);
		
	}
	public List<Categoria> listar() {	
		return categoriaDAO.listar();
	}
	
    public List<Categoria> cargarCategorias() {
        return categoriaDAO.listarConProductos();
    }

}
