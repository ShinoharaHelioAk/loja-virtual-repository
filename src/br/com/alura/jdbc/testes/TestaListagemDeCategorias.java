package br.com.alura.jdbc.testes;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.alura.jdbc.dao.CategoriaDao;
import br.com.alura.jdbc.factory.ConnectionFactory;
import br.com.alura.jdbc.modelo.Categoria;
import br.com.alura.jdbc.modelo.Produto;

public class TestaListagemDeCategorias {

	public static void main(String[] args) throws SQLException {
		try(Connection connection = new ConnectionFactory().recuperarConexao()) {
			CategoriaDao categoriaDao = new CategoriaDao(connection);
			List<Categoria> listaDeCategorias = categoriaDao.listarComProdutos();
			listaDeCategorias.stream().forEach(lc -> {
				System.out.println("++++++++ "+lc.getNome()+" ++++++++");
				for (Produto produto : lc.getProdutos()) {
					System.out.println(lc.getNome() + " - " + produto.getNome());
				}
			});
		}
	}

}
