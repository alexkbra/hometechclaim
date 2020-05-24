package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Dealer.
 */
@Entity
@Table(name = "dealer")
public class Dealer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Size(min = 5, max = 255)
    @Column(name = "correo", length = 255)
    private String correo;

    @Size(min = 5, max = 255)
    @Column(name = "codigo", length = 255)
    private String codigo;

    @Column(name = "idciudad")
    private Long idciudad;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "direccion", length = 100, nullable = false)
    private String direccion;

    @Size(min = 5, max = 45)
    @Column(name = "telefonofijo", length = 45)
    private String telefonofijo;

    @Size(min = 5, max = 45)
    @Column(name = "telefonocelular", length = 45)
    private String telefonocelular;

    @Column(name = "idusuario")
    private String idusuario;

    @OneToMany(mappedBy = "dealer")
    private Set<Colaboradores> colaboradores = new HashSet<>();

    @OneToMany(mappedBy = "dealer")
    private Set<Cliente> clientes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("dealers")
    private Comerciales comerciales;

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

    public Dealer nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Dealer correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public Dealer codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getIdciudad() {
        return idciudad;
    }

    public Dealer idciudad(Long idciudad) {
        this.idciudad = idciudad;
        return this;
    }

    public void setIdciudad(Long idciudad) {
        this.idciudad = idciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public Dealer direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public Dealer telefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
        return this;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public Dealer telefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
        return this;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Dealer idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public Set<Colaboradores> getColaboradores() {
        return colaboradores;
    }

    public Dealer colaboradores(Set<Colaboradores> colaboradores) {
        this.colaboradores = colaboradores;
        return this;
    }

    public Dealer addColaboradores(Colaboradores colaboradores) {
        this.colaboradores.add(colaboradores);
        colaboradores.setDealer(this);
        return this;
    }

    public Dealer removeColaboradores(Colaboradores colaboradores) {
        this.colaboradores.remove(colaboradores);
        colaboradores.setDealer(null);
        return this;
    }

    public void setColaboradores(Set<Colaboradores> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public Dealer clientes(Set<Cliente> clientes) {
        this.clientes = clientes;
        return this;
    }

    public Dealer addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setDealer(this);
        return this;
    }

    public Dealer removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setDealer(null);
        return this;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Comerciales getComerciales() {
        return comerciales;
    }

    public Dealer comerciales(Comerciales comerciales) {
        this.comerciales = comerciales;
        return this;
    }

    public void setComerciales(Comerciales comerciales) {
        this.comerciales = comerciales;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dealer)) {
            return false;
        }
        return id != null && id.equals(((Dealer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dealer{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", idciudad=" + getIdciudad() +
            ", direccion='" + getDireccion() + "'" +
            ", telefonofijo='" + getTelefonofijo() + "'" +
            ", telefonocelular='" + getTelefonocelular() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            "}";
    }
}
