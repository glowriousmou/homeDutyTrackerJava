package com.oumou.homeDutyTracker.dao.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<T,P>{
    int create(T t) throws SQLException;
    // T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    List<T> getAll(P p) throws SQLException;
    //boolean update(T t) throws SQLException;
    //boolean delete(T t) throws SQLException;

}
