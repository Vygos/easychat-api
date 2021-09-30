package br.com.vygos.easychatapi.service;


import br.com.vygos.easychatapi.domain.entity.Usuario;
import br.com.vygos.easychatapi.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
        usuario.orElseThrow(()-> new UsernameNotFoundException("Usuario não encontrado"));

        log.info("EMAIL USUARIO: " + usuario.get().getEmail());
        
        return new User(username, usuario.get().getPassword(), getPermissoes(usuario.get()));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        Set<GrantedAuthority> autorities = new LinkedHashSet<>();
//        if(!usuario.getPermissaoUsuario().isEmpty()){
//            usuario.getPermissaoUsuario().forEach(p -> autorities.add(new SimpleGrantedAuthority(p.getDsPermissao().toUpperCase())));
//            return autorities;
//        }
        return autorities;
    }
}