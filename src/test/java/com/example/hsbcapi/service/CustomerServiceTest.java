package com.example.hsbcapi.service;

import com.example.hsbcapi.exception.ResourceNotFoundException;
import com.example.hsbcapi.model.Customer;
import com.example.hsbcapi.repository.CustomerRepository;
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
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void findAllReturnsCustomers() {
        when(customerRepository.findAll())
                .thenReturn(List.of(new Customer(1L, "Asha", "asha@example.com")));

        assertEquals(1, customerService.findAll().size());
    }

    @Test
    void findByIdReturnsCustomer() {
        Customer customer = new Customer(1L, "Asha", "asha@example.com");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        assertEquals(customer, customerService.findById(1L));
    }

    @Test
    void findByIdThrowsWhenMissing() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.findById(99L));
    }

    @Test
    void createSavesCustomer() {
        Customer input = new Customer(null, "Asha", "asha@example.com");
        Customer saved = new Customer(1L, "Asha", "asha@example.com");
        when(customerRepository.save(input)).thenReturn(saved);

        assertEquals(saved, customerService.create(input));
    }

    @Test
    void deleteRemovesExistingCustomer() {
        when(customerRepository.existsById(1L)).thenReturn(true);

        customerService.deleteById(1L);

        verify(customerRepository).deleteById(1L);
    }

    @Test
    void deleteThrowsWhenMissing() {
        when(customerRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> customerService.deleteById(99L));
    }
}
