package projeto.empresa.uni.br.Services;

import org.springframework.cglib.core.Local;
import projeto.empresa.uni.br.Domain.Dto.EmployeeDto;
import projeto.empresa.uni.br.Domain.Dto.SalaryByEmployeeResponse;
import projeto.empresa.uni.br.Domain.Dto.VocationStartAndFinshRequest;
import projeto.empresa.uni.br.Domain.Dto.VocationStartAndFinshResponse;
import projeto.empresa.uni.br.Domain.Entity.Employee;
import projeto.empresa.uni.br.Repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    public List<Employee> findAll(){
        return repository.findAll();
    }

    public Employee findById(Long id) throws Exception{
        Optional<Employee> result = repository.findById(id);
        if(!result.isPresent()){
            throw new Exception("Employee is null");
        }

        return result.get();
    }

    public List<Employee> findEmployeesNotInVocation(){
        List<Employee> employees = repository.findAll();

        employees = employees.stream()
                .filter(employee -> !employee.getOnVocation())
                .collect(Collectors.toList());

        return employees;
    }


    public boolean createEmployee(EmployeeDto employeeRequest){

        Employee employee = new Employee();

        employee.setName(employeeRequest.getName());
        employee.setHasTransportationVoucher(employeeRequest.isHasTransportationVoucher());
        employee.setChildrens(employeeRequest.getChildrens());
        employee.setNightShift(employeeRequest.isNightShift());
        employee.setBirthdayDate(employeeRequest.getBirthdayDate());
        employee.setCreatedAt(LocalDateTime.now());
        employee.setStartVocationDate(null);
        employee.setFinishVocationDate(null);
        employee.setGlossSalary(employeeRequest.getGrossSalary());

        Employee result = repository.save(employee);

        if(result == null){
            return false;
        }
        return true;
    }

    public VocationStartAndFinshResponse setVocation(VocationStartAndFinshRequest request) throws Exception {
        Optional<Employee> result = repository.findById(request.employeeid);

        if(!result.isPresent())
            throw new Exception("Employee not exists");

        Employee employee = result.get();
        if(request.isVocation == employee.getOnVocation()){
            throw new Exception(String.format("/{0}/ is already /{0}/", request.isVocation));
        }

        if(request.isVocation){
            employee.setStartVocationDate(LocalDateTime.now());
            employee.setFinishVocationDate(LocalDateTime.now().plusDays(request.days));
        }
        employee.setOnVocation(request.isVocation);

        repository.save(employee);

        VocationStartAndFinshResponse response = new VocationStartAndFinshResponse();
        response.onVocation = employee.getOnVocation();
        response.finishVocation = employee.getFinishVocationDate();
        response.startVocation = employee.getStartVocationDate();
        return response;
    }

    public SalaryByEmployeeResponse findSalaryByEmployee(Long employeeId) throws Exception{
        Optional<Employee> result = repository.findById(employeeId);
        if(!result.isPresent())
            throw new Exception("Employee not exists");

        Employee employee = result.get();
        SalaryByEmployeeResponse response = new SalaryByEmployeeResponse();

        response.employeeId = employee.getId();
        response.employeeName = employee.getName();
        response.grossSalary = employee.getGlossSalary()*12;
        response.grossSalaryByMonth = employee.getGlossSalary();
        response.netSalary = employee.getNetSalary()*12;

        return response;
    }

    public double findSalaryOfAllEmployeesValue(){
        double total = 0;

        List<Employee> employees = repository.findAll();
        for (int i=0; i<employees.size(); i++)
        {
            total += employees.get(i).getGlossSalary();
        }

        return total;
    }
    public void delete(Long id){
        repository.deleteById(id);
    }

    public Employee update(Employee request) throws Exception{
        Optional<Employee> result = repository.findById(request.getId());
        if(!result.isPresent())
            throw new Exception("Employee is null");

        return repository.save(request);
    }
}
