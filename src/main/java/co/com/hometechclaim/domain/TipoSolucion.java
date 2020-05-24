package co.com.hometechclaim.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoSolucion.
 */
@Entity
@Table(name = "tipo_solucion")
public class TipoSolucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 250)
    @Column(name = "nombre", length = 250, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 2, max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Lob
    @Column(name = "imagen_url")
    private byte[] imagenUrl;

    @Column(name = "imagen_url_content_type")
    private String imagenUrlContentType;

    @OneToMany(mappedBy = "tipoSolucion")
    private Set<Solucion> solucions = new HashSet<>();

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

    public TipoSolucion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCode() {
        return code;
    }

    public TipoSolucion code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getImagenUrl() {
        return imagenUrl;
    }

    public TipoSolucion imagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
        return this;
    }

    public void setImagenUrl(byte[] imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getImagenUrlContentType() {
        return imagenUrlContentType;
    }

    public TipoSolucion imagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
        return this;
    }

    public void setImagenUrlContentType(String imagenUrlContentType) {
        this.imagenUrlContentType = imagenUrlContentType;
    }

    public Set<Solucion> getSolucions() {
        return solucions;
    }

    public TipoSolucion solucions(Set<Solucion> solucions) {
        this.solucions = solucions;
        return this;
    }

    public TipoSolucion addSolucion(Solucion solucion) {
        this.solucions.add(solucion);
        solucion.setTipoSolucion(this);
        return this;
    }

    public TipoSolucion removeSolucion(Solucion solucion) {
        this.solucions.remove(solucion);
        solucion.setTipoSolucion(null);
        return this;
    }

    public void setSolucions(Set<Solucion> solucions) {
        this.solucions = solucions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoSolucion)) {
            return false;
        }
        return id != null && id.equals(((TipoSolucion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoSolucion{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", code='" + getCode() + "'" +
            ", imagenUrl='" + getImagenUrl() + "'" +
            ", imagenUrlContentType='" + getImagenUrlContentType() + "'" +
            "}";
    }
}
