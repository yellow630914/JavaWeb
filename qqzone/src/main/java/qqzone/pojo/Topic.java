package qqzone.pojo;

import java.sql.Date;
import java.util.List;

public class Topic {
    private Integer id ;
    private String title ;
    private String content ;
    private Date topicDate ;
    private Integer author ;          //M:1

    private List<Reply> replyList ;     //1:N

    public UserBasic getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(UserBasic authorDetails) {
        this.authorDetails = authorDetails;
    }

    private UserBasic authorDetails;

    public Topic(Integer topicId){
    }

    public Topic(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTopicDate() {
        return topicDate;
    }

    public void setTopicDate(Date topicDate) {
        this.topicDate = topicDate;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
