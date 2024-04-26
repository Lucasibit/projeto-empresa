package projeto.empresa.uni.br.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.empresa.uni.br.Domain.Dto.EmployeeDto;
import projeto.empresa.uni.br.Domain.Dto.SalaryByEmployeeResponse;
import projeto.empresa.uni.br.Domain.Dto.VocationStartAndFinshRequest;
import projeto.empresa.uni.br.Domain.Dto.VocationStartAndFinshResponse;
import projeto.empresa.uni.br.Domain.Entity.Employee;
import projeto.empresa.uni.br.Repository.EmployeeRepository;
import projeto.empresa.uni.br.Services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService service;

    public EmployeeController(EmployeeRepository repository){
        super();
        this.service = new EmployeeService(repository);
    }

    @GetMapping()
    public ResponseEntity<List<Employee>> getAll()
    {
        return new ResponseEntity<>(this.service.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody EmployeeDto request){
        boolean result = service.createEmployee(request);
        if(result){
            return new ResponseEntity<>("created", HttpStatus.OK);
        }

        return new ResponseEntity<>("Not created", HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/vocation")
    public ResponseEntity<VocationStartAndFinshResponse> setVocation(@RequestBody VocationStartAndFinshRequest request){
        try {
            VocationStartAndFinshResponse result = service.setVocation(request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            VocationStartAndFinshResponse error = new VocationStartAndFinshResponse();
            error.errorMessage = e.getMessage();

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byNotVocation")
    public ResponseEntity<List<Employee>> getByNotVocation(){
        return new ResponseEntity<>(service.findEmployeesNotInVocation(), HttpStatus.OK);
    }

    @GetMapping("/salaryInYear/{id}")
    public ResponseEntity<SalaryByEmployeeResponse> getSalaryInYearByEmployee(@PathVariable(required = true) Long id){
        try {
            SalaryByEmployeeResponse result = service.findSalaryByEmployee(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/salaryOfAllEmployeesValue")
    public ResponseEntity<Double> getSalaryOfAllEmployeesValue(){
        return new ResponseEntity<>(service.findSalaryOfAllEmployeesValue(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable(required = true) Long id){
        try {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>("deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    public ResponseEntity<Employee> update(@RequestBody Employee request){
        try {
            Employee result = service.update(request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
