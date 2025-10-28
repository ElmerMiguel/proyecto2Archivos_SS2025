package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.Usuario;
import com.archivos.ecommerce.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByEmail(String email); 
    List<Usuario> findByRol_NombreRol(Rol.NombreRol nombreRol);
}