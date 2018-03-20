package in.amal.gns.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private String subject;
    private String from;
    private String to;
    private String body;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "subject='" + subject + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
