package com.example.nabot.domain;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class CommentDTO {
    private  int id;
    private  int writer;
    private String content;
    private String update_time;
    private String write_time;
    private int show;
    private int reply;
    private int writing_id;
    private String writer_name;

    public CommentDTO(int id, int writer, String content, String update_time, String write_time, int show, int reply, int writing_id, String writer_name) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.update_time = update_time;
        this.write_time = write_time;
        this.show = show;
        this.reply = reply;
        this.writing_id = writing_id;
        this.writer_name = writer_name;
    }
    public CommentDTO(int writer, String content, int writing_id) {
        this.writer = writer;
        this.content = content;
        this.writing_id = writing_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getWrite_time() {
        return write_time;
    }

    public void setWrite_time(String write_time) {
        this.write_time = write_time;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public int getWriting_id() {
        return writing_id;
    }

    public void setWriting_id(int writing_id) {
        this.writing_id = writing_id;
    }

    public String getWriter_name() {
        return writer_name;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
    }
}
