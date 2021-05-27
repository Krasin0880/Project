package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.service.EmployeeServiceImpl;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmployeeRepositoryTest {

    @Autowired
    EmployeeServiceImpl employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReturnEmployeeWhenFindById() {
        Mockito.doReturn(Optional.of(new Employee()))
                .when(employeeRepository)
                .findById(1L);
        Optional<Employee> employeeOptional = employeeRepository.findById(1L);
        assertThat(employeeOptional).isNotNull();
    }

    @Test
    public void shouldReturnAllEmployee() {
        List<Employee> list = new ArrayList<>();
        Mockito.doReturn(list)
                .when(employeeRepository)
                .findAll();
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).isNotNull();
    }

    @Test
    public void shouldReturnExceptionWhenFindById() {
        Mockito.doReturn(Optional.of(new Employee()))
                .when(employeeRepository)
                .findById(1L);
        Throwable exception = assertThrows(RuntimeException.class,
                () -> employeeService.getEmployeeById(2L));
        Assertions.assertEquals(" Employee not found for id :: " + 2L, exception.getMessage());

    }

    @Test
    public void shouldReturnTrueWhenDelete() {
        Employee user = new Employee();
        user.setFirstName("Test Name");
        user.setId(1L);
        when(employeeRepository.findById(user.getId())).thenReturn(Optional.of(user));
        boolean isDelete = employeeService.deleteEmployeeById(user.getId());
        verify(employeeRepository).deleteById(user.getId());
        assertTrue(isDelete);
    }
}
