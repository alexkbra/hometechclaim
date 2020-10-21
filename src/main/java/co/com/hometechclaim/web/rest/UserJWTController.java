package co.com.hometechclaim.web.rest;

import co.com.hometechclaim.domain.Cliente;
import co.com.hometechclaim.domain.Dealer;
import co.com.hometechclaim.repository.ClienteRepository;
import co.com.hometechclaim.repository.DealerRepository;
import co.com.hometechclaim.security.jwt.JWTFilter;
import co.com.hometechclaim.security.jwt.TokenProvider;
import co.com.hometechclaim.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;
    private final DealerRepository dealerRepository;
    private final ClienteRepository clienteRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,DealerRepository dealerRepository,ClienteRepository clienteRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.dealerRepository = dealerRepository;
        this.clienteRepository = clienteRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        
        String typeUser = getTypeUser(loginVM.getUsername());
        return new ResponseEntity<>(new JWTToken(jwt,typeUser, loginVM.getUsername()), httpHeaders, HttpStatus.OK);
    }
    
    @PostMapping("/authenticate_social_networks")
    public ResponseEntity<JWTToken> authorize_social_networks(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken("user", "user");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        
        String typeUser = getTypeUser(loginVM.getUsername());
        return new ResponseEntity<>(new JWTToken(jwt,typeUser, loginVM.getUsername()), httpHeaders, HttpStatus.OK);
    }
    
    
    public String getTypeUser(String username) {
        String typeUser = "ANONYMOUS";
        Dealer dealer = dealerRepository.findByIdusuario(username);
        if(dealer != null){
            typeUser = "DELAER";
        }else{
            Cliente cliente = clienteRepository.findByIdusuario(username);
            if(cliente != null){
                typeUser = "CLIENT";
            }
            
        }
        
        return typeUser;
    }
    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;
        private String idUser;
        private String typeUser;

        JWTToken(String idToken, String typeUser, String idUser) {
            this.idToken = idToken;
            this.typeUser = typeUser;
            this.idUser = idUser;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
        
        @JsonProperty("type_user")
        public String getTypeUser() {
            return typeUser;
        }

        public void setTypeUser(String typeUser) {
            this.typeUser = typeUser;
        }
        
        @JsonProperty("id_user")
        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }
        
        
    }
}
