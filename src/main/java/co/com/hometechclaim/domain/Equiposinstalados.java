package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A Equiposinstalados.
 */
@Entity
@Table(name = "equiposinstalados")
public class Equiposinstalados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "observacion", length = 500, nullable = false)
    private String observacion;

    @Column(name = "fechainstalacion")
    private Instant fechainstalacion;

    @Column(name = "posibleactuliazcion")
    private Boolean posibleactuliazcion;

    @NotNull
    @Column(name = "cantidad", precision = 21, scale = 2, nullable = false)
    private BigDecimal cantidad;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("equiposinstalados")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("equiposinstalados")
    private Equipo equipo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("equiposinstalados")
    private Proyecto proyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Equiposinstalados descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public Equiposinstalados observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Instant getFechainstalacion() {
        return fechainstalacion;
    }

    public Equiposinstalados fechainstalacion(Instant fechainstalacion) {
        this.fechainstalacion = fechainstalacion;
        return this;
    }

    public void setFechainstalacion(Instant fechainstalacion) {
        this.fechainstalacion = fechainstalacion;
    }

    public Boolean isPosibleactuliazcion() {
        return posibleactuliazcion;
    }

    public Equiposinstalados posibleactuliazcion(Boolean posibleactuliazcion) {
        this.posibleactuliazcion = posibleactuliazcion;
        return this;
    }

    public void setPosibleactuliazcion(Boolean posibleactuliazcion) {
        this.posibleactuliazcion = posibleactuliazcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public Equiposinstalados cantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Equiposinstalados cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public Equiposinstalados equipo(Equipo equipo) {
        this.equipo = equipo;
        return this;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public Equiposinstalados proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equiposinstalados)) {
            return false;
        }
        return id != null && id.equals(((Equiposinstalados) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equiposinstalados{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", fechainstalacion='" + getFechainstalacion() + "'" +
            ", posibleactuliazcion='" + isPosibleactuliazcion() + "'" +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
