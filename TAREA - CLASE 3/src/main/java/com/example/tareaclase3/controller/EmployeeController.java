package com.example.tareaclase3.controller;

import com.example.tareaclase3.entity.Employee;
import com.example.tareaclase3.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Mostrar lista de empleados con buscador
    @GetMapping("/list")
    public String listEmployees(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Employee> employees;
        if (search != null && !search.isEmpty()) {
            employees = employeeRepository.findByNombreContainingOrApellidoContainingOrPuestoContainingOrDepartamentoContainingOrCiudadContaining(
                    search, search, search, search, search);
        } else {
            employees = employeeRepository.findAll();
        }
        model.addAttribute("employees", employees);
        model.addAttribute("search", search);
        return "employee/list";  // Nombre de la plantilla Thymeleaf (employee/list.html)
    }

    // Mostrar formulario para crear empleado
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/create";  // Nombre de la plantilla (employee/create.html)
    }

    // Procesar creación de empleado
    @PostMapping("/create")
    public String createEmployee(@ModelAttribute Employee employee) {
        employeeRepository.save(employee);
        return "redirect:/employee/list";
    }

    // Mostrar formulario para editar empleado
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            return "redirect:/employee/list";
        }
        model.addAttribute("employee", employee);
        return "employee/edit";  // Nombre de la plantilla (employee/edit.html)
    }

    // Procesar edición de empleado
    @PostMapping("/edit")
    public String editEmployee(@ModelAttribute Employee employee) {
        // Al usar el mismo ID, save() hará un update
        employeeRepository.save(employee);
        return "redirect:/employee/list";
    }

    // Eliminar empleado
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employee/list";
    }
}
