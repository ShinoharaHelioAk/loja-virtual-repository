package br.com.alura.jdbc.controller;

import java.sql.Connection;
//import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.dao.ProdutoDAO;
import br.com.alura.jdbc.factory.ConnectionFactory;
import br.com.alura.jdbc.modelo.Produto;

public class ProdutoController {
	private ProdutoDAO produtoDao;
	
	public ProdutoController() {
		Connection connection = new ConnectionFactory().recuperarConexao();
		this.produtoDao = new ProdutoDAO(connection);
	}

	public void deletar(Integer id) {
		// System.out.println("Deletando produto");
		this.produtoDao.deletar(id);
	}

	public void salvar(Produto produto) {
		// System.out.println("Salvando produto");
		this.produtoDao.salvarComCategoria(produto);
	}

	public List<Produto> listar() {
		// List<Produto> produtos = new ArrayList<Produto>();
		// produtos.add(new Produto("Nome do Produto de teste", "Descrição do produto de teste"));
		// return produtos;
		return this.produtoDao.listar();
	}

	public void alterar(String nome, String descricao, Integer id) {
		// System.out.println("Alterando produto");
		this.produtoDao.alterar(nome, descricao, id);
	}
}
