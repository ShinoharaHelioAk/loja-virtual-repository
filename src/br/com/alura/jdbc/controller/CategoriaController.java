package br.com.alura.jdbc.controller;

import java.sql.Connection;
// import java.sql.SQLException;
// import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.dao.CategoriaDao;
import br.com.alura.jdbc.factory.ConnectionFactory;
import br.com.alura.jdbc.modelo.Categoria;

public class CategoriaController {
	private CategoriaDao categoriaDao;
	
	public CategoriaController() {
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.categoriaDao = new CategoriaDao(connection);
	}
	
	public List<Categoria> listar() {
		// List<Categoria> categorias = new ArrayList<Categoria>();
		// categorias.add(new Categoria(1, "Categoria de teste")); 
		// return categorias;
		return this.categoriaDao.listar();
	}
}
