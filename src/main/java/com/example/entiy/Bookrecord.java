package com.example.entiy;

import lombok.Data;

@Data
public class Bookrecord {
    private long id;
    private String bookName;
    private String borrowData;
    private String returnData;
    private String borrower;
    private String borrowStatus;

    public void setBorrowData(String borrowDate) {
        this.borrowData = borrowDate;
    }

    public void setReturnData(String returnDate) {
        this.returnData = returnDate;
    }

    public Bookrecord() {
    }
}
