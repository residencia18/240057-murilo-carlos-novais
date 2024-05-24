package com.resitic.clinica.model;

import com.resitic.clinica.controller.forms.EnderecoFORM;

import jakarta.persistence.Embeddable;

@Embeddable
public class Endereco {
	private String logradouro;
	private String bairro;
	private String cep;
	private String numero;
	private String complemento;
	private String cidade;
	private String uf;

	public Endereco() {
	}

	public Endereco(EnderecoFORM endereco) {
		update(endereco);
	}

	public Endereco(Endereco endereco) {
		this.logradouro = endereco.getLogradouro();
		this.bairro = endereco.getBairro();
		this.cep = endereco.getCep();
		this.numero = endereco.getNumero();
		this.complemento = endereco.getComplemento();
		this.cidade = endereco.getCidade();
		this.uf = endereco.getUf();
	}

	public void update(EnderecoFORM endereco) {
		if (endereco.getLogradouro() != null) {
			this.logradouro = endereco.getLogradouro();
		}
		if (endereco.getBairro() != null) {
			this.bairro = endereco.getBairro();
		}
		if (endereco.getCep() != null) {
			this.cep = endereco.getCep();
		}
		if (endereco.getNumero() != null) {
			this.numero = endereco.getNumero();
		}
		if (endereco.getComplemento() != null) {
			this.complemento = endereco.getComplemento();
		}
		if (endereco.getCidade() != null) {
			this.cidade = endereco.getCidade();
		}
		if (endereco.getUf() != null) {
			this.uf = endereco.getUf();
		}
	}

	// Getters e Setters

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
