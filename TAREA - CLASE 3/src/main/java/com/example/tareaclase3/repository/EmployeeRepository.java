package com.example.tareaclase3.repository;

import com.example.tareaclase3.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Búsqueda por coincidencia en varios campos
    List<Employee> findByNombreContainingOrApellidoContainingOrPuestoContainingOrDepartamentoContainingOrCiudadContaining(
            String nombre, String apellido, String puesto, String departamento, String ciudad);
}
