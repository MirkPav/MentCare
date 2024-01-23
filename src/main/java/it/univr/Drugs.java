package it.univr;

import java.util.Map;
import java.util.Set;

public class Drugs {


    Map<String, Float> drug;

    public Drugs(Map<String, Float> drug){
        this.drug = drug;
    }

    public Float getMaxDose(String drug){
        return this.drug.get(drug);
    }

    public Set<String> getDrugs(){
        return drug.keySet();
    }

}
