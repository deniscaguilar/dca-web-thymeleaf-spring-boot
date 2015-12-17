package br.com.dca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.common.base.Objects;

/**
 * Entidade para respresentar os dados do Cep.
 * 
 * @author Dênis Casado Aguilar
 *
 */
@Entity
public class Cep extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Size(max = 9, message = "Cep deve conter 8 caracteres (numéricos).")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Cep deve seguir o formato \\d{5}-?\\{d3}")
    @NotNull(message = "Cep é obrigatório!")
    @Column(nullable = false, unique = true, length = 8)
    private String numeroCep;

    @Size(min = 3, max = 200, message = "Logradouro deve conter no mínimo 3 caracteres e no máximo 200 caracteres.")
    @NotNull(message = "Logradouro é obrigatório!")
    @Column(nullable = false, length = 200)
    private String logradouro;

    @Size(max = 60, message = "Bairro deve conter no máximo 60 caracteres.")
    @Column(length = 60)
    private String bairro;

    @Size(max = 100, message = "Cidade deve conter no máximo 100 caracteres.")
    @NotNull(message = "Cidade é obrigatória!")
    @Column(nullable = false, length = 100)
    private String cidade;

    @Size(max = 2, message = "Estado deve conter no máximo 2 caracteres.")
    @NotNull(message = "Estado é obrigatório!")
    @Column(nullable = false, length = 2)
    private String estado;

    public Cep() {}

    public Cep(String numeroCep) {
        this.numeroCep = numeroCep;
    }

    public Cep(String logradouro, String bairro, String cidade, String estado) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getNumeroCep() {
        return numeroCep;
    }

    public void setNumeroCep(String numeroCep) {
        this.numeroCep = numeroCep;
    }

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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Cep [numeroCep=" + numeroCep + ", logradouro=" + logradouro + ", bairro=" + bairro
                        + ", cidade=" + cidade + ", estado=" + estado + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.numeroCep, this.logradouro, this.bairro, this.cidade,
                        this.estado);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;

        Cep outro = (Cep) obj;

        return Objects.equal(this.numeroCep, outro.numeroCep)
                        && Objects.equal(this.logradouro, outro.logradouro)
                        && Objects.equal(this.bairro, outro.bairro)
                        && Objects.equal(this.cidade, outro.cidade)
                        && Objects.equal(this.estado, outro.estado);
    }

}
