package qqzone.pojo;

import java.sql.Date;

public class Reply {
    private Integer id ;
    private String content ;
    private Date replyDate ;
    private Integer author ;  //M:1
    private Integer topic ;       //M:1

    private UserBasic authorDetails;

    public UserBasic getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(UserBasic authorDetails) {
        this.authorDetails = authorDetails;
    }

    private HostReply hostReply ;   //1:1

    public Reply(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getTopic() {
        return topic;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    public HostReply getHostReply() {
        return hostReply;
    }

    public void setHostReply(HostReply hostReply) {
        this.hostReply = hostReply;
    }
}
