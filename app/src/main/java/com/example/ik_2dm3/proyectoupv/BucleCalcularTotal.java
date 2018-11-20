package com.example.ik_2dm3.proyectoupv;

public class BucleCalcularTotal {
    public int Contador =1;
    public double txorizo;
    public double Indabak;
    public double Azenario;
    public double kipula;
    public double Total;
    public BucleCalcularTotal(double txorizo, double Indabak, double Azenario, double kipula){
        this.txorizo =txorizo;
        this.Indabak = Indabak;
        this.Azenario = Azenario;
        this.kipula = kipula;
            Total=txorizo+Indabak+Azenario+kipula;
    }
    public double getTxorizo() {
        return txorizo;
    }

    public void setTxorizo(double txorizo) {
        this.txorizo = txorizo;
    }

    public double getIndabak() {
        return Indabak;
    }

    public void setIndabak(double indabak) {
        Indabak = indabak;
    }

    public double getAzenario() {
        return Azenario;
    }

    public void setAzenario(double azenario) {
        Azenario = azenario;
    }

    public double getKipula() {
        return kipula;
    }

    public void setKipula(double kipula) {
        this.kipula = kipula;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }


}
