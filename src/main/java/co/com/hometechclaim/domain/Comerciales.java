package co.com.hometechclaim.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Comerciales.
 */
@Entity
@Table(name = "comerciales")
public class Comerciales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 45)
    @Column(name = "codigo", length = 45, nullable = false)
    private String codigo;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Size(min = 5, max = 255)
    @Column(name = "correo", length = 255)
    private String correo;

    @Size(min = 5, max = 255)
    @Column(name = "zona", length = 255)
    private String zona;

    @Column(name = "idciudad")
    private Long idciudad;

    @Column(name = "idusuario")
    private String idusuario;

    @OneToMany(mappedBy = "comerciales")
    private Set<Dealer> dealers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Comerciales codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Comerciales nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Comerciales descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreo() {
        return correo;
    }

    public Comerciales correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getZona() {
        return zona;
    }

    public Comerciales zona(String zona) {
        this.zona = zona;
        return this;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Long getIdciudad() {
        return idciudad;
    }

    public Comerciales idciudad(Long idciudad) {
        this.idciudad = idciudad;
        return this;
    }

    public void setIdciudad(Long idciudad) {
        this.idciudad = idciudad;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Comerciales idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public Set<Dealer> getDealers() {
        return dealers;
    }

    public Comerciales dealers(Set<Dealer> dealers) {
        this.dealers = dealers;
        return this;
    }

    public Comerciales addDealer(Dealer dealer) {
        this.dealers.add(dealer);
        dealer.setComerciales(this);
        return this;
    }

    public Comerciales removeDealer(Dealer dealer) {
        this.dealers.remove(dealer);
        dealer.setComerciales(null);
        return this;
    }

    public void setDealers(Set<Dealer> dealers) {
        this.dealers = dealers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comerciales)) {
            return false;
        }
        return id != null && id.equals(((Comerciales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Comerciales{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", zona='" + getZona() + "'" +
            ", idciudad=" + getIdciudad() +
            ", idusuario='" + getIdusuario() + "'" +
            "}";
    }
}
