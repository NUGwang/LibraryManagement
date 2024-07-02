package com.example.controller;


import com.example.entiy.Bookrecord;
import com.example.service.BookRecordService;
import com.example.way.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/records")
public class BookRecordController {
    @Autowired
    private BookRecordService bookRecordService;

    @GetMapping
    public List<Bookrecord> getAllRecords() {
        return bookRecordService.getRecordsOrderByBorrowData();
    }

    /**
     * 某一记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable Long id) {
        Bookrecord record = bookRecordService.getById(id);
        if (record == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, null, "未找到图书记录"));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(true, record, "获取图书记录成功"));
        }
    }

    /**
     * 归还
     * @param id
     * @return
     */
    @PostMapping("/return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        boolean success = bookRecordService.returnBook(id);
        if (success) {
            return ResponseEntity.ok("图书已归还");
        } else {
            return ResponseEntity.badRequest().body("图书已归还或状态异常");
        }
    }

    /**
     * 借阅
     * @param id
     * @param request
     * @return
     */
    @PostMapping("/borrow/{id}")
    public ResponseEntity<?> borrowBook(@PathVariable int id, @RequestBody Bookrecord request) {
        boolean success = bookRecordService.borrowBook(id, request);
        if (success) {
            return ResponseEntity.ok("成功借阅图书");
        } else {
            return ResponseEntity.badRequest().body("图书已借出，请勿重复借阅");
        }
    }

    /**
     * 最新借阅查询
     * @return
     */
    @GetMapping("/latest")
    public ResponseEntity<?> getRecordsOrderByBorrowDate() {
        List<Bookrecord> records = bookRecordService.getRecordsOrderByBorrowData();
        return ResponseEntity.ok(new ApiResponse<>(true, records, "获取图书记录成功"));
    }
    /**
     * 通过未归还查询
     * @param
     * @return
     */
    @GetMapping("/emptyReturnData")
    public ResponseEntity<?> getRecordsWithEmptyReturnData() {
        List<Bookrecord> records = bookRecordService.getRecordsWithEmptyReturnData();
        return ResponseEntity.ok(new ApiResponse<>(true, records, "获取空 returnData 记录成功"));
    }

    /**
     * 通过借阅状态查询
     * @param status
     * @return
     */
    @GetMapping("/borrowStatus/{status}")
    public ResponseEntity<?> getRecordsByBorrowStatus(@PathVariable String status) {
        List<Bookrecord> records = bookRecordService.getRecordsByBorrowStatus(status);
        return ResponseEntity.ok(records);
    }

    /**
     * 根据图书名查询记录
     * @param name
     * @return
     */
    @GetMapping("/bookName/{name}")
    public ResponseEntity<?> getRecordsByBookName(@PathVariable String name) {
        List<Bookrecord> records = bookRecordService.getRecordsByBookName(name);
        return ResponseEntity.ok(records);
    }


    /**
     * 根据借阅人查询记录的端点
     * @param name
     * @return
     */
    @GetMapping("/borrower/{name}")
    public ResponseEntity<?> getRecordsByBorrower(@PathVariable String name) {
        try {
            List<Bookrecord> records = bookRecordService.getRecordsByBorrower(name);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取记录失败: " + e.getMessage());
        }
    }
}

