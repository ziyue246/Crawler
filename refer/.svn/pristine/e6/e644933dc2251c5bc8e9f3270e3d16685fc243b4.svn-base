package refer.model.po;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Lv on 2016/12/6.
 */
public class Paper implements Serializable {
    private int id;
    private String title;
    private String authors;
    private Integer reportId;
    private Timestamp expiryTime;
    private Integer finished;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Timestamp getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Timestamp expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paper paper = (Paper) o;

        if (id != paper.id) return false;
        if (title != null ? !title.equals(paper.title) : paper.title != null) return false;
        if (authors != null ? !authors.equals(paper.authors) : paper.authors != null) return false;
        if (reportId != null ? !reportId.equals(paper.reportId) : paper.reportId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (reportId != null ? reportId.hashCode() : 0);
        return result;
    }
}
