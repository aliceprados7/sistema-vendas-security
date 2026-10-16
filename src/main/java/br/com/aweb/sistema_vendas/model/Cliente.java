package br.com.aweb.sistema_vendas.model;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nomeCompleto;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF 
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório.")
    @Column(nullable = false, length = 20)
    private String telefone;

    @NotBlank(message = "O logradouro é obrigatório.")
    @Column(nullable = false, length = 150)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @NotBlank(message = "O bairro é obrigatório.")
    @Column(nullable = false, length = 100)
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    @Column(nullable = false, length = 100)
    private String cidade;

    @NotBlank(message = "A UF é obrigatória.")
    @Size(min = 2, max = 2, message = "A UF deve conter 2 letras.")
    @Column(nullable = false, length = 2)
    private String uf;

    @NotBlank(message = "O CEP é obrigatório.")
    @Column(nullable = false, length = 9)
    private String cep;
}