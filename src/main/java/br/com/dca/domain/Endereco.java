package br.com.dca.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Entidade para respresentar os dados do Endereço.
 * 
 * @author Dênis Casado Aguilar
 */
@Entity
public class Endereco extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 200, message = "Logradouro deve conter no mínimo 3 caracteres e no máximo 200 caracteres.")
    @NotNull(message = "Logradouro é obrigatório!")
    @Column(nullable = false, length = 200)
    private String logradouro;

    @Size(min = 1, max = 20, message = "Número deve conter no máximo 20 caracteres.")
    @NotNull(message = "Número é obrigatório!")
    @Column(nullable = false, length = 20)
    private String numero;

    @Size(max = 80, message = "Complemento deve conter no máximo 80 caracteres.")
    @Column(length = 80)
    private String complemento;

    @Size(max = 9, message = "Cep deve conter 8 caracteres (numéricos).")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "Cep deve seguir o formato \\d{5}-?\\{d3}")
    @NotNull(message = "Cep é obrigatório!")
    @Column(length = 8)
    private String cep;

    @Size(max = 100, message = "Bairro deve conter no máximo 100 caracteres.")
    @Column(length = 100)
    private String bairro;

    @Size(min = 3, max = 120, message = "Cidade deve conter no mínimo 3 caracteres e no máximo 120 caracteres.")
    @NotNull(message = "Cidade é obrigatório!")
    @Column(nullable = false, length = 120)
    private String cidade;

    @Size(min = 2, max = 2, message = "Estado deve conter no máximo 2 caracteres.")
    @NotNull(message = "Estado é obrigatório!")
    @Column(nullable = false, length = 2)
    private String estado;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
        if (!Strings.isNullOrEmpty(this.cep)) {
            this.cep = this.cep.replace("-", "");
        }
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

    public void popularComDadosCep(Cep cep) {
        Preconditions.checkNotNull(cep, "Informe o Cep!");
        this.logradouro = cep.getLogradouro();
        this.bairro = cep.getBairro();
        this.cidade = cep.getCidade();
        this.estado = cep.getEstado();
        this.cep = cep.getNumeroCep();
    }

    @Override
    public String toString() {
        return "Endereco [logradouro=" + logradouro + ", numero=" + numero + ", complemento="
                        + complemento + ", cep=" + cep + ", bairro=" + bairro + ", cidade=" + cidade
                        + ", estado=" + estado + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.logradouro, this.numero, this.complemento, this.cep,
                        this.bairro, this.cidade, this.estado);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass()) return false;

        Endereco outro = (Endereco) obj;

        return Objects.equal(this.logradouro, outro.logradouro)
                        && Objects.equal(this.numero, outro.numero)
                        && Objects.equal(this.complemento, outro.complemento)
                        && Objects.equal(this.cep, outro.cep)
                        && Objects.equal(this.bairro, outro.bairro)
                        && Objects.equal(this.cidade, outro.cidade)
                        && Objects.equal(this.estado, outro.estado);
    }

}
