package accesoadatos.dto;

public class EstudianteDTO {
    
    private String matricula;
    private String nombreEstudiante;
    private String periodoEscolar;
    private String seccionEstudiante;
    private int avanceCrediticio;
    private double promedio;
    
    public String getSeccionEstudiante() {
        return seccionEstudiante;
    }

    public void setSeccionEstudiante(String seccionEstudiante) {
        this.seccionEstudiante = seccionEstudiante;
    }
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    public int getAvanceCrediticio() {
        return avanceCrediticio;
    }

    public void setAvanceCrediticio(int avanceCrediticio) {
        this.avanceCrediticio = avanceCrediticio;
    }
    
}
