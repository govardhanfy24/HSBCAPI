package com.example.hsbcapi.service;

import com.example.hsbcapi.exception.ResourceNotFoundException;
import com.example.hsbcapi.model.Employee;
import com.example.hsbcapi.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void findAllReturnsEmployees() {
        when(employeeRepository.findAll())
                .thenReturn(List.of(new Employee(1L, "Ravi", "ravi@example.com")));

        assertEquals(1, employeeService.findAll().size());
    }

    @Test
    void findByIdReturnsEmployee() {
        Employee employee = new Employee(1L, "Ravi", "ravi@example.com");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        assertEquals(employee, employeeService.findById(1L));
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.findById(99L));
    }

    @Test
    void createSavesEmployee() {
        Employee input = new Employee(null, "Ravi", "ravi@example.com");
        Employee saved = new Employee(1L, "Ravi", "ravi@example.com");
        when(employeeRepository.save(input)).thenReturn(saved);

        assertEquals(saved, employeeService.create(input));
    }

    @Test
    void deleteRemovesExistingEmployee() {
        when(employeeRepository.existsById(1L)).thenReturn(true);

        employeeService.deleteById(1L);

        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void deleteThrowsWhenMissing() {
        when(employeeRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> employeeService.deleteById(99L));
    }
}
