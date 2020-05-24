package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Solucion.
 */
@Entity
@Table(name = "solucion")
public class Solucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 250)
    @Column(name = "nombre", length = 250, nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @OneToMany(mappedBy = "solucion")
    private Set<RequerimientoToSolucion> requerimientoToSolucions = new HashSet<>();

    @OneToMany(mappedBy = "solucion")
    private Set<TipoEquipo> tipoEquipos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("solucions")
    private TipoSolucion tipoSolucion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Solucion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Solucion descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCode() {
        return code;
    }

    public Solucion code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public Solucion imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public Solucion imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public Set<RequerimientoToSolucion> getRequerimientoToSolucions() {
        return requerimientoToSolucions;
    }

    public Solucion requerimientoToSolucions(Set<RequerimientoToSolucion> requerimientoToSolucions) {
        this.requerimientoToSolucions = requerimientoToSolucions;
        return this;
    }

    public Solucion addRequerimientoToSolucion(RequerimientoToSolucion requerimientoToSolucion) {
        this.requerimientoToSolucions.add(requerimientoToSolucion);
        requerimientoToSolucion.setSolucion(this);
        return this;
    }

    public Solucion removeRequerimientoToSolucion(RequerimientoToSolucion requerimientoToSolucion) {
        this.requerimientoToSolucions.remove(requerimientoToSolucion);
        requerimientoToSolucion.setSolucion(null);
        return this;
    }

    public void setRequerimientoToSolucions(Set<RequerimientoToSolucion> requerimientoToSolucions) {
        this.requerimientoToSolucions = requerimientoToSolucions;
    }

    public Set<TipoEquipo> getTipoEquipos() {
        return tipoEquipos;
    }

    public Solucion tipoEquipos(Set<TipoEquipo> tipoEquipos) {
        this.tipoEquipos = tipoEquipos;
        return this;
    }

    public Solucion addTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipos.add(tipoEquipo);
        tipoEquipo.setSolucion(this);
        return this;
    }

    public Solucion removeTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipos.remove(tipoEquipo);
        tipoEquipo.setSolucion(null);
        return this;
    }

    public void setTipoEquipos(Set<TipoEquipo> tipoEquipos) {
        this.tipoEquipos = tipoEquipos;
    }

    public TipoSolucion getTipoSolucion() {
        return tipoSolucion;
    }

    public Solucion tipoSolucion(TipoSolucion tipoSolucion) {
        this.tipoSolucion = tipoSolucion;
        return this;
    }

    public void setTipoSolucion(TipoSolucion tipoSolucion) {
        this.tipoSolucion = tipoSolucion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Solucion)) {
            return false;
        }
        return id != null && id.equals(((Solucion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Solucion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", code='" + getCode() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            "}";
    }
}
