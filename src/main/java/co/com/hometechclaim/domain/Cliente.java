package co.com.hometechclaim.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @Size(min = 5, max = 255)
    @Column(name = "correo", length = 255)
    private String correo;

    @Size(min = 5, max = 255)
    @Column(name = "codigo_dealer", length = 255)
    private String codigoDealer;

    @Column(name = "idciudad")
    private Long idciudad;

    @Size(min = 5, max = 45)
    @Column(name = "telefonocelular", length = 45)
    private String telefonocelular;

    @Size(min = 5, max = 45)
    @Column(name = "telefonofijo", length = 45)
    private String telefonofijo;

    @Size(min = 5, max = 45)
    @Column(name = "telefonoempresarial", length = 45)
    private String telefonoempresarial;

    @Size(min = 5, max = 100)
    @Column(name = "direccionresidencial", length = 100)
    private String direccionresidencial;

    @Size(min = 5, max = 100)
    @Column(name = "direccionempresarial", length = 100)
    private String direccionempresarial;

    @NotNull
    @Column(name = "fechanacimiento", nullable = false)
    private LocalDate fechanacimiento;

    @Column(name = "idusuario")
    private String idusuario;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @OneToMany(mappedBy = "cliente")
    private Set<InteresesToCliente> interesesToClientes = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    private Set<NovedadToCliente> novedadToClientes = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    private Set<Equiposinstalados> equiposinstalados = new HashSet<>();

    @OneToMany(mappedBy = "cliente")
    private Set<Cotizacion> cotizacions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clientes")
    private Dealer dealer;

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

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Cliente apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public Cliente correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigoDealer() {
        return codigoDealer;
    }

    public Cliente codigoDealer(String codigoDealer) {
        this.codigoDealer = codigoDealer;
        return this;
    }

    public void setCodigoDealer(String codigoDealer) {
        this.codigoDealer = codigoDealer;
    }

    public Long getIdciudad() {
        return idciudad;
    }

    public Cliente idciudad(Long idciudad) {
        this.idciudad = idciudad;
        return this;
    }

    public void setIdciudad(Long idciudad) {
        this.idciudad = idciudad;
    }

    public String getTelefonocelular() {
        return telefonocelular;
    }

    public Cliente telefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
        return this;
    }

    public void setTelefonocelular(String telefonocelular) {
        this.telefonocelular = telefonocelular;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public Cliente telefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
        return this;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonoempresarial() {
        return telefonoempresarial;
    }

    public Cliente telefonoempresarial(String telefonoempresarial) {
        this.telefonoempresarial = telefonoempresarial;
        return this;
    }

    public void setTelefonoempresarial(String telefonoempresarial) {
        this.telefonoempresarial = telefonoempresarial;
    }

    public String getDireccionresidencial() {
        return direccionresidencial;
    }

    public Cliente direccionresidencial(String direccionresidencial) {
        this.direccionresidencial = direccionresidencial;
        return this;
    }

    public void setDireccionresidencial(String direccionresidencial) {
        this.direccionresidencial = direccionresidencial;
    }

    public String getDireccionempresarial() {
        return direccionempresarial;
    }

    public Cliente direccionempresarial(String direccionempresarial) {
        this.direccionempresarial = direccionempresarial;
        return this;
    }

    public void setDireccionempresarial(String direccionempresarial) {
        this.direccionempresarial = direccionempresarial;
    }

    public LocalDate getFechanacimiento() {
        return fechanacimiento;
    }

    public Cliente fechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
        return this;
    }

    public void setFechanacimiento(LocalDate fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public Cliente idusuario(String idusuario) {
        this.idusuario = idusuario;
        return this;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Cliente imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Cliente imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Set<InteresesToCliente> getInteresesToClientes() {
        return interesesToClientes;
    }

    public Cliente interesesToClientes(Set<InteresesToCliente> interesesToClientes) {
        this.interesesToClientes = interesesToClientes;
        return this;
    }

    public Cliente addInteresesToCliente(InteresesToCliente interesesToCliente) {
        this.interesesToClientes.add(interesesToCliente);
        interesesToCliente.setCliente(this);
        return this;
    }

    public Cliente removeInteresesToCliente(InteresesToCliente interesesToCliente) {
        this.interesesToClientes.remove(interesesToCliente);
        interesesToCliente.setCliente(null);
        return this;
    }

    public void setInteresesToClientes(Set<InteresesToCliente> interesesToClientes) {
        this.interesesToClientes = interesesToClientes;
    }

    public Set<NovedadToCliente> getNovedadToClientes() {
        return novedadToClientes;
    }

    public Cliente novedadToClientes(Set<NovedadToCliente> novedadToClientes) {
        this.novedadToClientes = novedadToClientes;
        return this;
    }

    public Cliente addNovedadToCliente(NovedadToCliente novedadToCliente) {
        this.novedadToClientes.add(novedadToCliente);
        novedadToCliente.setCliente(this);
        return this;
    }

    public Cliente removeNovedadToCliente(NovedadToCliente novedadToCliente) {
        this.novedadToClientes.remove(novedadToCliente);
        novedadToCliente.setCliente(null);
        return this;
    }

    public void setNovedadToClientes(Set<NovedadToCliente> novedadToClientes) {
        this.novedadToClientes = novedadToClientes;
    }

    public Set<Equiposinstalados> getEquiposinstalados() {
        return equiposinstalados;
    }

    public Cliente equiposinstalados(Set<Equiposinstalados> equiposinstalados) {
        this.equiposinstalados = equiposinstalados;
        return this;
    }

    public Cliente addEquiposinstalados(Equiposinstalados equiposinstalados) {
        this.equiposinstalados.add(equiposinstalados);
        equiposinstalados.setCliente(this);
        return this;
    }

    public Cliente removeEquiposinstalados(Equiposinstalados equiposinstalados) {
        this.equiposinstalados.remove(equiposinstalados);
        equiposinstalados.setCliente(null);
        return this;
    }

    public void setEquiposinstalados(Set<Equiposinstalados> equiposinstalados) {
        this.equiposinstalados = equiposinstalados;
    }

    public Set<Cotizacion> getCotizacions() {
        return cotizacions;
    }

    public Cliente cotizacions(Set<Cotizacion> cotizacions) {
        this.cotizacions = cotizacions;
        return this;
    }

    public Cliente addCotizacion(Cotizacion cotizacion) {
        this.cotizacions.add(cotizacion);
        cotizacion.setCliente(this);
        return this;
    }

    public Cliente removeCotizacion(Cotizacion cotizacion) {
        this.cotizacions.remove(cotizacion);
        cotizacion.setCliente(null);
        return this;
    }

    public void setCotizacions(Set<Cotizacion> cotizacions) {
        this.cotizacions = cotizacions;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Cliente dealer(Dealer dealer) {
        this.dealer = dealer;
        return this;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", codigoDealer='" + getCodigoDealer() + "'" +
            ", idciudad=" + getIdciudad() +
            ", telefonocelular='" + getTelefonocelular() + "'" +
            ", telefonofijo='" + getTelefonofijo() + "'" +
            ", telefonoempresarial='" + getTelefonoempresarial() + "'" +
            ", direccionresidencial='" + getDireccionresidencial() + "'" +
            ", direccionempresarial='" + getDireccionempresarial() + "'" +
            ", fechanacimiento='" + getFechanacimiento() + "'" +
            ", idusuario='" + getIdusuario() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", imagenContentType='" + getImagenContentType() + "'" +
            "}";
    }
}
