package it.univr;

import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
public class Medication {

    private Date date;

    private String drug;

    private float dose;

    private String note;

    private int days;

    protected Medication(){};
    public Medication(Date date, String drug, float dose, int days, String note){
        this.date = date;
        this.drug = drug;
        this.dose = dose;
        this.days = days;
        this.note = note;
    }


    public Date getDate() {
        return this.date;
    }

    public String getDrug() { return this.drug; }

    public float getDose() { return this.dose; }

    public int getDays() { return this.days; }

    public String getNote() { return this.note; }

    @Override
    public boolean equals(Object m){
        if (m instanceof Medication) {
            Medication med = (Medication) m;
            return (this.date.equals(med.date) &&
                    this.drug.equals(med.drug) &&
                    this.dose == med.dose &&
                    this.days == med.days &&
                    this.note.equals(med.note));
        }
        return false;

    }
}
