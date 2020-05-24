package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import co.com.hometechclaim.domain.enumeration.Estadocotizacion;

/**
 * A Cotizacion.
 */
@Entity
@Table(name = "cotizacion")
public class Cotizacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fechacreacion")
    private Instant fechacreacion;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "descripcion", length = 100, nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "valoriva", precision = 21, scale = 2, nullable = false)
    private BigDecimal valoriva;

    @NotNull
    @Column(name = "totalsiniva", precision = 21, scale = 2, nullable = false)
    private BigDecimal totalsiniva;

    @NotNull
    @Column(name = "porcentajedescuento", precision = 21, scale = 2, nullable = false)
    private BigDecimal porcentajedescuento;

    @Column(name = "fechamantenimiento")
    private Instant fechamantenimiento;

    @Column(name = "activo")
    private Boolean activo;

    @NotNull
    @Column(name = "total", precision = 21, scale = 2, nullable = false)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "estadocotizacion")
    private Estadocotizacion estadocotizacion;

    @OneToMany(mappedBy = "cotizacion")
    private Set<EquiposcotizadosToEquipos> equiposcotizadosToEquipos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("cotizacions")
    private Cliente cliente;

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

    public Cotizacion fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Cotizacion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValoriva() {
        return valoriva;
    }

    public Cotizacion valoriva(BigDecimal valoriva) {
        this.valoriva = valoriva;
        return this;
    }

    public void setValoriva(BigDecimal valoriva) {
        this.valoriva = valoriva;
    }

    public BigDecimal getTotalsiniva() {
        return totalsiniva;
    }

    public Cotizacion totalsiniva(BigDecimal totalsiniva) {
        this.totalsiniva = totalsiniva;
        return this;
    }

    public void setTotalsiniva(BigDecimal totalsiniva) {
        this.totalsiniva = totalsiniva;
    }

    public BigDecimal getPorcentajedescuento() {
        return porcentajedescuento;
    }

    public Cotizacion porcentajedescuento(BigDecimal porcentajedescuento) {
        this.porcentajedescuento = porcentajedescuento;
        return this;
    }

    public void setPorcentajedescuento(BigDecimal porcentajedescuento) {
        this.porcentajedescuento = porcentajedescuento;
    }

    public Instant getFechamantenimiento() {
        return fechamantenimiento;
    }

    public Cotizacion fechamantenimiento(Instant fechamantenimiento) {
        this.fechamantenimiento = fechamantenimiento;
        return this;
    }

    public void setFechamantenimiento(Instant fechamantenimiento) {
        this.fechamantenimiento = fechamantenimiento;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Cotizacion activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Cotizacion total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Estadocotizacion getEstadocotizacion() {
        return estadocotizacion;
    }

    public Cotizacion estadocotizacion(Estadocotizacion estadocotizacion) {
        this.estadocotizacion = estadocotizacion;
        return this;
    }

    public void setEstadocotizacion(Estadocotizacion estadocotizacion) {
        this.estadocotizacion = estadocotizacion;
    }

    public Set<EquiposcotizadosToEquipos> getEquiposcotizadosToEquipos() {
        return equiposcotizadosToEquipos;
    }

    public Cotizacion equiposcotizadosToEquipos(Set<EquiposcotizadosToEquipos> equiposcotizadosToEquipos) {
        this.equiposcotizadosToEquipos = equiposcotizadosToEquipos;
        return this;
    }

    public Cotizacion addEquiposcotizadosToEquipos(EquiposcotizadosToEquipos equiposcotizadosToEquipos) {
        this.equiposcotizadosToEquipos.add(equiposcotizadosToEquipos);
        equiposcotizadosToEquipos.setCotizacion(this);
        return this;
    }

    public Cotizacion removeEquiposcotizadosToEquipos(EquiposcotizadosToEquipos equiposcotizadosToEquipos) {
        this.equiposcotizadosToEquipos.remove(equiposcotizadosToEquipos);
        equiposcotizadosToEquipos.setCotizacion(null);
        return this;
    }

    public void setEquiposcotizadosToEquipos(Set<EquiposcotizadosToEquipos> equiposcotizadosToEquipos) {
        this.equiposcotizadosToEquipos = equiposcotizadosToEquipos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Cotizacion cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cotizacion)) {
            return false;
        }
        return id != null && id.equals(((Cotizacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cotizacion{" +
            "id=" + getId() +
            ", fechacreacion='" + getFechacreacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", valoriva=" + getValoriva() +
            ", totalsiniva=" + getTotalsiniva() +
            ", porcentajedescuento=" + getPorcentajedescuento() +
            ", fechamantenimiento='" + getFechamantenimiento() + "'" +
            ", activo='" + isActivo() + "'" +
            ", total=" + getTotal() +
            ", estadocotizacion='" + getEstadocotizacion() + "'" +
            "}";
    }
}
