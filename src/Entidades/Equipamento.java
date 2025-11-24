package Entidades;

// classe equipamento
public class Equipamento {
    private Integer id;
    private String nome;
    private String tipo;


    public Equipamento(Integer id, String nome, String tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    //gets e settes e toString
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " (" + tipo + ")";
    }
}
