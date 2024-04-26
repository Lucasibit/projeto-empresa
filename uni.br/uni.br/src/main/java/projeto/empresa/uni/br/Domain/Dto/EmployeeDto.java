package projeto.empresa.uni.br.Domain.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private String name;
    private Date birthdayDate;
    private double grossSalary;
    private boolean hasTransportationVoucher;
    private int childrens;
    private boolean nightShift;


    public String getName() {
        return name;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public boolean isHasTransportationVoucher() {
        return hasTransportationVoucher;
    }

    public int getChildrens() {
        return childrens;
    }

    public boolean isNightShift() {
        return nightShift;
    }

}
