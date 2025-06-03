package DTOs;

import java.util.Date;

public class ArtifactOwnership {
    private int id;
    private int artifact_id;
    private int owner_id;
    private Date acquisition_date;
    private Date sale_date;

    public ArtifactOwnership(int id, int artifact_id, int owner_id, Date acquisition_date, Date sale_date) {
        this.id = id;
        this.artifact_id = artifact_id;
        this.owner_id = owner_id;
        this.acquisition_date = acquisition_date;
        this.sale_date = sale_date;
    }

    public ArtifactOwnership(int artifact_id, int owner_id, Date acquisition_date, Date sale_date) {
        this.artifact_id = artifact_id;
        this.owner_id = owner_id;
        this.acquisition_date = acquisition_date;
        this.sale_date = sale_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtifactId() {
        return artifact_id;
    }

    public void setArtifactId(int artifact_id) {
        this.artifact_id = artifact_id;
    }

    public int getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(int owner_id) {
        this.owner_id = owner_id;
    }

    public Date getAcquisitionDate() {
        return acquisition_date;
    }

    public void setAcquisitionDate(Date acquisition_date) {
        this.acquisition_date = acquisition_date;
    }

    public Date getSaleDate() {
        return sale_date;
    }

    public void setSaleDate(Date sale_date) {
        this.sale_date = sale_date;
    }

    @Override
    public String toString() {
        return "ArtifactOwnership{" +
                "id=" + id +
                ", artifact_id=" + artifact_id +
                ", owner_id=" + owner_id +
                ", acquisition_date=" + acquisition_date +
                ", sale_date=" + sale_date +
                '}';
    }
}
