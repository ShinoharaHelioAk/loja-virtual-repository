package br.com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.modelo.Categoria;
import br.com.alura.jdbc.modelo.Produto;

public class ProdutoDAO {

	private Connection connection;

	public ProdutoDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Produto produto) {
		String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO) VALUES (?, ?)";

		try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstm.setString(1, produto.getNome());
			pstm.setString(2, produto.getDescricao());

			pstm.execute();

			try(ResultSet rst = pstm.getGeneratedKeys()) {
				while(rst.next()) {
					produto.setId(rst.getInt(1));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void salvarComCategoria(Produto produto) {
		String sql = "INSERT INTO PRODUTO (NOME, DESCRICAO, CATEGORIA_ID) VALUES (?,?,?)";
		try (PreparedStatement pstmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, produto.getNome());
			pstmt.setString(2, produto.getDescricao());
			pstmt.setInt(3, produto.getCategoriaId());
			
			pstmt.execute();
			
			try (ResultSet rs = pstmt.getGeneratedKeys()) {
				while (rs.next()) {
					produto.setId(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Produto> listar() {
		List<Produto> produtos = new ArrayList<Produto>();

		String sql = "SELECT ID, NOME, DESCRICAO FROM PRODUTO";

		try(PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.execute();

			trasformarResultSetEmProduto(produtos, pstm);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return produtos;
	}

	public List<Produto> buscar(Categoria categoria) {
		List<Produto> produtos = new ArrayList<Produto>();

		String sql = "SELECT ID, NOME, DESCRICAO FROM PRODUTO WHERE CATEGORIA_ID = ?";

		try(PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setInt(1, categoria.getId());
			
			pstm.execute();

			trasformarResultSetEmProduto(produtos, pstm);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return produtos;
	}
	
	public void deletar(Integer id) {
		String sql = "DELETE FROM PRODUTO WHERE ID = ?";
		
		try (PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void alterar(String nome, String descricao, Integer id) {
		String sql = "UPDATE PRODUTO P SET P.NOME = ?, P.DESCRICAO = ? WHERE ID = ?";
		try(PreparedStatement pstmt = this.connection.prepareStatement(sql)) {
			pstmt.setString(1, nome);
			pstmt.setString(2, descricao);
			pstmt.setInt(3, id);
			
			pstmt.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void trasformarResultSetEmProduto(List<Produto> produtos, PreparedStatement pstm) {
		try(ResultSet rst = pstm.getResultSet()) {
			while(rst.next()) {
				Produto produto = 
						new Produto(rst.getInt(1), rst.getString(2), rst.getString(3));

				produtos.add(produto);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
