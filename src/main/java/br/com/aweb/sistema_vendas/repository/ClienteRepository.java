package br.com.aweb.sistema_vendas.repository;

import br.com.aweb.sistema_vendas.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
