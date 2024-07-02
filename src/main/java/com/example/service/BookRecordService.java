package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entiy.Bookrecord;
import com.example.mapper.BookRecordMapper;
import com.example.way.DateToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BookRecordService extends ServiceImpl<BookRecordMapper, Bookrecord> {

    @Autowired
    BookRecordMapper bookRecordMapper;
    /**
     * 归还图书
     * @param id
     * @return
     */
    public boolean returnBook(Long id) {
        Bookrecord record = getById(id);
        if (record != null && "借出".equals(record.getBorrowStatus())) {

            String s = DateToString.dateToString();
            record.setReturnData(s);

            record.setBorrowStatus("归还");
            updateById(record);
            return true;
        }
        return false;
    }

    /**
     * 借书
     * @param id
     * @param request
     * @return
     */
    public boolean borrowBook(int id, Bookrecord request) {
        Bookrecord record = getById(id);
        if (record == null) {
            // 如果记录不存在，则新建一条记录进行借阅
            Bookrecord newRecord = new Bookrecord();
            newRecord.setId(id);
            newRecord.setBookName(request.getBookName());

            String s = DateToString.dateToString();
            newRecord.setBorrowData(s);

            newRecord.setReturnData(" ");
            newRecord.setBorrower(request.getBorrower());
            newRecord.setBorrowStatus("借出");
            save(newRecord); // 插入新记录
            return true;
        } else if (!"借出".equals(record.getBorrowStatus())) {
            // 如果图书未被借出，则更新借阅时间和状态

            String s = DateToString.dateToString();
            record.setBorrowData(s);

            record.setReturnData(" ");
            record.setBookName(request.getBookName());
            record.setBorrower(request.getBorrower());
            record.setBorrowStatus("借出");
            updateById(record); // 更新已有记录
            return true;
        }
        return false;
    }

    /**
     * 根据最新借阅时间查询
     * @return
     */
    public List<Bookrecord> getRecordsOrderByBorrowData() {
        QueryWrapper<Bookrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("borrow_data"); // 假设数据库列名是 borrowDate
        return this.list(queryWrapper);
    }

    /**
     * 查找未归还记录
     * @return
     */
    public List<Bookrecord> getRecordsWithEmptyReturnData() {

        QueryWrapper<Bookrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("return_data"," ");
        return bookRecordMapper.selectList(queryWrapper);
    }

    /**
     * 通过借阅状态查询
     * @param borrowStatus
     * @return
     */
    public List<Bookrecord> getRecordsByBorrowStatus(String borrowStatus) {
        QueryWrapper<Bookrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("borrow_status", borrowStatus); // 假设"borrow_status"是数据库中的列名
        return bookRecordMapper.selectList(queryWrapper);
    }

    /**
     * 根据图书名查询记录
     * @param bookName
     * @return
     */
    public List<Bookrecord> getRecordsByBookName(String bookName) {
        QueryWrapper<Bookrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("book_name", bookName); // 使用 like 条件进行模糊查询
        return bookRecordMapper.selectList(queryWrapper);
    }

    /**
     * 根据借阅人查询记录
     */
    public List<Bookrecord> getRecordsByBorrower(String borrower) {
        QueryWrapper<Bookrecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("borrower", borrower);
        return bookRecordMapper.selectList(queryWrapper);
    }
}
