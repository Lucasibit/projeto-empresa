package projeto.empresa.uni.br.Domain.Dto;


import java.time.LocalDateTime;

public class VocationStartAndFinshResponse {

    public boolean onVocation;
    public LocalDateTime startVocation;
    public LocalDateTime finishVocation;

    public String errorMessage = "";
}
