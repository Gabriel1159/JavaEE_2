package com.example.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Invitation {
    int f_vid;
    int f_from;
    int f_to;
    int f_tid;

    public Invitation(int f_from, int f_to, int f_tid) {
        this.f_from = f_from;
        this.f_to = f_to;
        this.f_tid = f_tid;
    }

    public int getF_vid() {
        return f_vid;
    }

    public int getF_from() {
        return f_from;
    }

    public void setF_from(int f_from) {
        this.f_from = f_from;
    }

    public int getF_to() {
        return f_to;
    }

    public void setF_to(int f_to) {
        this.f_to = f_to;
    }

    public int getF_tid() {
        return f_tid;
    }

    public void setF_tid(int f_tid) {
        this.f_tid = f_tid;
    }
}
