package refer.model.po;

/**
 * Created by lv on 2016/12/11.
 */
public class Record {
    private int id;
    private String content;
    private Integer paperId;
    private Integer reportId;

    public Record(String content, Integer paperId, Integer reportId) {
        this.content = content;
        this.paperId = paperId;
        this.reportId = reportId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (id != record.id) return false;
        if (content != null ? !content.equals(record.content) : record.content != null) return false;
        if (paperId != null ? !paperId.equals(record.paperId) : record.paperId != null) return false;
        if (reportId != null ? !reportId.equals(record.reportId) : record.reportId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (paperId != null ? paperId.hashCode() : 0);
        result = 31 * result + (reportId != null ? reportId.hashCode() : 0);
        return result;
    }
}
