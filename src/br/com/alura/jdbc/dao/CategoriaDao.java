package br.com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.modelo.Categoria;
import br.com.alura.jdbc.modelo.Produto;

public class CategoriaDao {
	private Connection connection;

	public CategoriaDao(Connection connection) {
		this.connection = connection;
	}
	
	public List<Categoria> listar() {
		List<Categoria> categorias = new ArrayList<Categoria>();
		String sql = "SELECT * FROM CATEGORIA";
		
		try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				while (rs.next()) {
					Categoria categoria = new Categoria(rs.getInt(1), rs.getString(2));
					categorias.add(categoria);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return categorias;
	}

	public List<Categoria> listarComProdutos() {
		Categoria ultima = null;
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		System.out.println("Executando a query de listar categoria por produto.");
		
		String sql = "SELECT C.ID, C.NOME, P.ID, P.NOME, P.DESCRICAO FROM CATEGORIA C INNER JOIN "
				+ "PRODUTO P ON C.ID = P.CATEGORIA_ID";
		
		try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
			pstmt.execute();
			try (ResultSet rs = pstmt.getResultSet()) {
				while (rs.next()) {
					if (ultima == null || !ultima.getNome().equals(rs.getString(2))) {
						Categoria categoria = new Categoria(rs.getInt(1), rs.getString(2));
						ultima = categoria;
						categorias.add(categoria);
					}
					Produto produto = new Produto(rs.getInt(3), rs.getString(4), rs.getString(5));
					ultima.adicionar(produto);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return categorias;
	}
}
