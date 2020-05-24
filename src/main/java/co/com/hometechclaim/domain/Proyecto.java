package co.com.hometechclaim.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
public class Proyecto implements Serializable {

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

    @Column(name = "fechaultimomantenimiento")
    private Instant fechaultimomantenimiento;

    @NotNull
    @Column(name = "porcentajedescuento", precision = 21, scale = 2, nullable = false)
    private BigDecimal porcentajedescuento;

    @NotNull
    @Column(name = "total", precision = 21, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @OneToMany(mappedBy = "proyecto")
    private Set<Equiposinstalados> equiposinstalados = new HashSet<>();

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

    public Proyecto fechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
        return this;
    }

    public void setFechacreacion(Instant fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Proyecto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValoriva() {
        return valoriva;
    }

    public Proyecto valoriva(BigDecimal valoriva) {
        this.valoriva = valoriva;
        return this;
    }

    public void setValoriva(BigDecimal valoriva) {
        this.valoriva = valoriva;
    }

    public BigDecimal getTotalsiniva() {
        return totalsiniva;
    }

    public Proyecto totalsiniva(BigDecimal totalsiniva) {
        this.totalsiniva = totalsiniva;
        return this;
    }

    public void setTotalsiniva(BigDecimal totalsiniva) {
        this.totalsiniva = totalsiniva;
    }

    public Instant getFechaultimomantenimiento() {
        return fechaultimomantenimiento;
    }

    public Proyecto fechaultimomantenimiento(Instant fechaultimomantenimiento) {
        this.fechaultimomantenimiento = fechaultimomantenimiento;
        return this;
    }

    public void setFechaultimomantenimiento(Instant fechaultimomantenimiento) {
        this.fechaultimomantenimiento = fechaultimomantenimiento;
    }

    public BigDecimal getPorcentajedescuento() {
        return porcentajedescuento;
    }

    public Proyecto porcentajedescuento(BigDecimal porcentajedescuento) {
        this.porcentajedescuento = porcentajedescuento;
        return this;
    }

    public void setPorcentajedescuento(BigDecimal porcentajedescuento) {
        this.porcentajedescuento = porcentajedescuento;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Proyecto total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Proyecto activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Proyecto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Proyecto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Set<Equiposinstalados> getEquiposinstalados() {
        return equiposinstalados;
    }

    public Proyecto equiposinstalados(Set<Equiposinstalados> equiposinstalados) {
        this.equiposinstalados = equiposinstalados;
        return this;
    }

    public Proyecto addEquiposinstalados(Equiposinstalados equiposinstalados) {
        this.equiposinstalados.add(equiposinstalados);
        equiposinstalados.setProyecto(this);
        return this;
    }

    public Proyecto removeEquiposinstalados(Equiposinstalados equiposinstalados) {
        this.equiposinstalados.remove(equiposinstalados);
        equiposinstalados.setProyecto(null);
        return this;
    }

    public void setEquiposinstalados(Set<Equiposinstalados> equiposinstalados) {
        this.equiposinstalados = equiposinstalados;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proyecto)) {
            return false;
        }
        return id != null && id.equals(((Proyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", fechacreacion='" + getFechacreacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", valoriva=" + getValoriva() +
            ", totalsiniva=" + getTotalsiniva() +
            ", fechaultimomantenimiento='" + getFechaultimomantenimiento() + "'" +
            ", porcentajedescuento=" + getPorcentajedescuento() +
            ", total=" + getTotal() +
            ", activo='" + isActivo() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
