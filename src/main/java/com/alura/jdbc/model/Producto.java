package com.alura.jdbc.model;

public class Producto {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
	private Integer categoriaId;
	
	public Producto(String nombre, String descripcion, Integer cantidad) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}
	
	public Producto(String nombre, String descripcion, Integer cantidad, Integer id) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.id = id;
	}

	public Producto(String nombre, int cantidad, int id) {
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.id = id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	public int getCategoriaId() {
		 return this.categoriaId;
	}

	public Integer getCantidad() {
		return cantidad;
	}
	
	@Override
	public String toString() {
		return String.format("Producto id: %d, nombre: %s, descripcion: %s, cantidad: %d", this.id, this.nombre, this.descripcion, this.cantidad);
	}


	
}
