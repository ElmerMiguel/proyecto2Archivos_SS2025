package com.archivos.ecommerce.repository;

import com.archivos.ecommerce.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombreRol(Rol.NombreRol nombreRol);
}