package com.example.nabot.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class WritingDTO implements Serializable {
    private int id;
    private String title;
    private int writer;
    private String content;
    private String update_time;
    private String write_time;
    private int view;
    private int show;
    private int board_id;
    private int comment_count;
    private String writer_name;

    public WritingDTO(int id, String title, int writer, String content, String update_time, String write_time, int view, int show, int board_id, int comment_count, String writer_name) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.update_time = update_time;
        this.write_time = write_time;
        this.view = view;
        this.show = show;
        this.board_id = board_id;
        this.comment_count = comment_count;
        this.writer_name = writer_name;
    }

    public WritingDTO(String title, int writer, String content, int board_id) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.board_id = board_id;
    }

    public WritingDTO(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getWriter_name() {
        return writer_name;
    }

    public void setWriter_name(String writer_name) {
        this.writer_name = writer_name;
    }
}
