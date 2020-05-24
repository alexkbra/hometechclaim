package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A InteresesToCliente.
 */
@Entity
@Table(name = "intereses_to_cliente")
public class InteresesToCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fechacreacion")
    private Instant fechacreacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("interesesToClientes")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("interesesToClientes")
    private Intereses intereses;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechacreacion() {
        return fechacreacion;
    }

    public InteresesToCliente fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public InteresesToCliente cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Intereses getIntereses() {
        return intereses;
    }

    public InteresesToCliente intereses(Intereses intereses) {
        this.intereses = intereses;
        return this;
    }

    public void setIntereses(Intereses intereses) {
        this.intereses = intereses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InteresesToCliente)) {
            return false;
        }
        return id != null && id.equals(((InteresesToCliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InteresesToCliente{" +
            "id=" + getId() +
            ", fechacreacion='" + getFechacreacion() + "'" +
            "}";
    }
}
