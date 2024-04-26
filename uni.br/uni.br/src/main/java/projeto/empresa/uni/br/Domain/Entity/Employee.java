package projeto.empresa.uni.br.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Employee")
public class Employee {

    @Id @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Date birthdayDate;
    private LocalDateTime createdAt;
    private boolean onVocation = false;
    private LocalDateTime startVocationDate;
    private LocalDateTime finishVocationDate;
    private double grossSalary;
    private double netSalary;
    private boolean hasTransportationVoucher;
    private int childrens;
    private boolean nightShift;

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdayDate(Date birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setStartVocationDate(LocalDateTime startVocationDate) {
        this.startVocationDate = startVocationDate;
    }

    public void setFinishVocationDate(LocalDateTime finishVocationDate) {
        this.finishVocationDate = finishVocationDate;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public void setHasTransportationVoucher(boolean hasTransportationVoucher) {
        this.hasTransportationVoucher = hasTransportationVoucher;
    }

    public void setChildrens(int childrens) {
        this.childrens = childrens;
    }

    public void setNightShift(boolean nightShift) {
        this.nightShift = nightShift;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthdayDate() {
        return birthdayDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isOnVocation() {
        return onVocation;
    }

    public LocalDateTime getStartVocationDate() {
        return startVocationDate;
    }

    public LocalDateTime getFinishVocationDate() {
        return finishVocationDate;
    }

    public double getNetSalary() {
        return this.netSalary;
    }

    public void setNetSalary(){
        double salaryBase = getGlossSalary();
        double netSalary = salaryBase - (salaryBase * 0.13);

        if(isHasTransportationVoucher()){
            netSalary -= salaryBase * 0.06;
        }

        if(isNightShift()){
            netSalary += salaryBase * 0.05;
        }
        if(getChildrens() > 3){
            netSalary += 50*3;
        }else{
            netSalary += 50*getChildrens();
        }

        this.netSalary = netSalary;
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

    public double getGlossSalary(){
        return this.grossSalary;
    }

    public void setGlossSalary(double salary){
        this.grossSalary = salary;
        setNetSalary();
    }

    public void setOnVocation(boolean onVocation){
        this.onVocation = onVocation;
        if(this.onVocation){
            setGlossSalary(getGlossSalary() / 3);
        }else{
            setGlossSalary(getGlossSalary());
        }
    }

    public boolean getOnVocation(){
        return this.onVocation;
    }
}
