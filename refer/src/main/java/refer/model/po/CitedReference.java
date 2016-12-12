package refer.model.po;

/**
 * Created by Lv on 2016/12/6.
 */
public class CitedReference {
    private int id;
    private String checkValue;//条目标号
    private String authorAbr;
    private String authorExp;
    private String citedWorkAbr;
    private String citedWorkExp;
    private String year;
    private String volume;
    private String issue;
    private String page;
    private String identifier;
    private String citingArticles;
    private String viewRecordText;
    private String viewRecordUrl;
    private Integer paperId;
    private Integer reportId;
    private Integer selected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public String getAuthorAbr() {
        return authorAbr;
    }

    public void setAuthorAbr(String authorAbr) {
        this.authorAbr = authorAbr;
    }

    public String getAuthorExp() {
        return authorExp;
    }

    public void setAuthorExp(String authorExp) {
        this.authorExp = authorExp;
    }

    public String getCitedWorkAbr() {
        return citedWorkAbr;
    }

    public void setCitedWorkAbr(String citedWorkAbr) {
        this.citedWorkAbr = citedWorkAbr;
    }

    public String getCitedWorkExp() {
        return citedWorkExp;
    }

    public void setCitedWorkExp(String citedWorkExp) {
        this.citedWorkExp = citedWorkExp;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCitingArticles() {
        return citingArticles;
    }

    public void setCitingArticles(String citingArticles) {
        this.citingArticles = citingArticles;
    }

    public String getViewRecordText() {
        return viewRecordText;
    }

    public void setViewRecordText(String viewRecordText) {
        this.viewRecordText = viewRecordText;
    }

    public String getViewRecordUrl() {
        return viewRecordUrl;
    }

    public void setViewRecordUrl(String viewRecordUrl) {
        this.viewRecordUrl = viewRecordUrl;
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

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CitedReference that = (CitedReference) o;

        if (id != that.id) return false;
        if (checkValue != null ? !checkValue.equals(that.checkValue) : that.checkValue != null) return false;
        if (authorAbr != null ? !authorAbr.equals(that.authorAbr) : that.authorAbr != null) return false;
        if (authorExp != null ? !authorExp.equals(that.authorExp) : that.authorExp != null) return false;
        if (citedWorkAbr != null ? !citedWorkAbr.equals(that.citedWorkAbr) : that.citedWorkAbr != null) return false;
        if (citedWorkExp != null ? !citedWorkExp.equals(that.citedWorkExp) : that.citedWorkExp != null) return false;
        if (year != null ? !year.equals(that.year) : that.year != null) return false;
        if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;
        if (issue != null ? !issue.equals(that.issue) : that.issue != null) return false;
        if (page != null ? !page.equals(that.page) : that.page != null) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        if (citingArticles != null ? !citingArticles.equals(that.citingArticles) : that.citingArticles != null)
            return false;
        if (viewRecordText != null ? !viewRecordText.equals(that.viewRecordText) : that.viewRecordText != null)
            return false;
        if (viewRecordUrl != null ? !viewRecordUrl.equals(that.viewRecordUrl) : that.viewRecordUrl != null)
            return false;
        if (paperId != null ? !paperId.equals(that.paperId) : that.paperId != null) return false;
        if (reportId != null ? !reportId.equals(that.reportId) : that.reportId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (checkValue != null ? checkValue.hashCode() : 0);
        result = 31 * result + (authorAbr != null ? authorAbr.hashCode() : 0);
        result = 31 * result + (authorExp != null ? authorExp.hashCode() : 0);
        result = 31 * result + (citedWorkAbr != null ? citedWorkAbr.hashCode() : 0);
        result = 31 * result + (citedWorkExp != null ? citedWorkExp.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (issue != null ? issue.hashCode() : 0);
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (citingArticles != null ? citingArticles.hashCode() : 0);
        result = 31 * result + (viewRecordText != null ? viewRecordText.hashCode() : 0);
        result = 31 * result + (viewRecordUrl != null ? viewRecordUrl.hashCode() : 0);
        result = 31 * result + (paperId != null ? paperId.hashCode() : 0);
        result = 31 * result + (reportId != null ? reportId.hashCode() : 0);
        return result;
    }
}