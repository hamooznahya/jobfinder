package progressoft.com.jobfinder.Adapter;

public class Result {

    private String companyName;
    private String jobTitle;
    private String logo;
    private String location;
    private String postDate;
    private String url;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    private String provider;


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCompanyName() {
        return companyName;
    }


    public String getJobTitle() {
        return jobTitle;
    }

    public String getLogo() {
        return logo;
    }

    public String getLocation() {
        return location;
    }

    public String getPostDate() {
        return postDate;
    }


    public String getUrl() {
        return url;
    }
}
