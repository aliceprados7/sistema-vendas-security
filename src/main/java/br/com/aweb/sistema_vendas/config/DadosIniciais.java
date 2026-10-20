package br.com.aweb.sistema_vendas.config;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.model.Pedido;
import br.com.aweb.sistema_vendas.model.Produto;
import br.com.aweb.sistema_vendas.repository.ClienteRepository;
import br.com.aweb.sistema_vendas.repository.ProdutoRepository;
import br.com.aweb.sistema_vendas.service.ClienteService;
import br.com.aweb.sistema_vendas.service.PedidoService;
import br.com.aweb.sistema_vendas.service.ProdutoService;

/**
 * Popula produtos, clientes e pedidos de exemplo.
 * Só insere quando a respectiva tabela está vazia, então é seguro rodar
 * a cada deploy: não duplica nada e não mexe em dados cadastrados depois.
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DadosIniciais.class);

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PedidoService pedidoService;

    @Override
    public void run(String... args) {
        try {
            if (produtoRepository.count() == 0) {
                criarProdutos();
            }
            if (clienteRepository.count() == 0) {
                criarClientes();
            }
            if (pedidoService.listarTodos().isEmpty()) {
                criarPedidos();
            }
        } catch (Exception e) {
            // Falha no seed não deve impedir a aplicação de subir
            log.warn("Não foi possível carregar os dados iniciais: {}", e.getMessage(), e);
        }
    }

    private void criarProdutos() {
        produto("Notebook Dell", "Notebook 15 polegadas, 16GB RAM, SSD 512GB", "4500.00", 20);
        produto("Mouse Logitech", "Mouse sem fio 2.4GHz", "120.00", 80);
        produto("Teclado Mecânico", "Teclado mecânico ABNT2 switch azul", "350.00", 40);
        produto("Monitor 24\"", "Monitor LED Full HD 24 polegadas", "899.90", 30);
        produto("Headset Gamer", "Headset com microfone e som surround", "259.90", 35);
        produto("Webcam HD", "Webcam 1080p com microfone embutido", "189.90", 25);
    }

    private void criarClientes() {
        cliente("Maria Silva", "maria@email.com", "529.982.247-25", "(16) 99999-1111",
                "Rua das Flores", "100", null, "Centro", "São Carlos", "SP", "13560-000");
        cliente("João Souza", "joao@email.com", "111.444.777-35", "(16) 98888-2222",
                "Avenida Brasil", "250", "Apto 12", "Jardim Paulista", "São Carlos", "SP", "13560-001");
        cliente("Ana Pereira", "ana@email.com", "390.533.447-05", "(11) 97777-3333",
                "Rua Augusta", "1500", null, "Consolação", "São Paulo", "SP", "01304-001");
    }

    private void criarPedidos() {
        List<Cliente> clientes = clienteService.listarTodos();
        List<Produto> produtos = produtoService.listarTodos();
        if (clientes.size() < 3 || produtos.size() < 5) {
            return;
        }

        Pedido p1 = pedidoService.criarPedido(clientes.get(0));
        pedidoService.adicionarItem(p1.getId(), produtos.get(0).getId(), 1);
        pedidoService.adicionarItem(p1.getId(), produtos.get(1).getId(), 2);

        Pedido p2 = pedidoService.criarPedido(clientes.get(1));
        pedidoService.adicionarItem(p2.getId(), produtos.get(3).getId(), 2);
        pedidoService.adicionarItem(p2.getId(), produtos.get(4).getId(), 1);

        Pedido p3 = pedidoService.criarPedido(clientes.get(2));
        pedidoService.adicionarItem(p3.getId(), produtos.get(2).getId(), 1);

        // Um pedido cancelado para aparecer nos dois status
        Pedido p4 = pedidoService.criarPedido(clientes.get(0));
        pedidoService.adicionarItem(p4.getId(), produtos.get(5).getId(), 1);
        pedidoService.cancelarPedido(p4.getId());
    }

    private void produto(String nome, String descricao, String preco, int estoque) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setPreco(new BigDecimal(preco));
        p.setQuantidadeEmEstoque(estoque);
        produtoService.salvar(p);
    }

    private void cliente(String nome, String email, String cpf, String telefone, String logradouro,
            String numero, String complemento, String bairro, String cidade, String uf, String cep) {
        Cliente c = new Cliente();
        c.setNomeCompleto(nome);
        c.setEmail(email);
        c.setCpf(cpf);
        c.setTelefone(telefone);
        c.setLogradouro(logradouro);
        c.setNumero(numero);
        c.setComplemento(complemento);
        c.setBairro(bairro);
        c.setCidade(cidade);
        c.setUf(uf);
        c.setCep(cep);
        clienteService.salvar(c);
    }
}