package com.example.igor.profile.InnerThings;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Example {
        public static void main2(String... args) {
                DBConnect.init();
                //IngridientManager.Initialize();
                //ToolManager.Initialize();

                ResultSet rs = DBConnect.sendQuery(DBConnect.TypeQuery.SELECT, "select * from team42");
                try {

                        while (rs != null && rs.next()) {
                                int id = rs.getInt("id");
                                String name = rs.getString("name");
                                String function = rs.getString("function");
                                System.out.printf("%d %s %s\n", id, name, function);
                        }
                } catch (SQLException e) {
                        throw new RuntimeException("Something wrong with results.");
                }

                rs = DBConnect.sendQuery(DBConnect.TypeQuery.SELECT, "select * from ingredients");
                try {

                        while (rs != null && rs.next()) {
                                int id1 = rs.getInt(1);
                                String name = rs.getString(2);
                                String steps = rs.getString(3);
                                System.out.printf("%d %s %s\n",id1, name, steps);
                        }
                } catch (SQLException e) {
                        throw new RuntimeException("Something wrong with results.");
                }
//                DBConnect.sendQuery(DBConnect.TypeQuery.UPDATE, "Delete from team42 where id = 6");
                //DBConnect.sendQuery(DBConnect.TypeQuery.CREATE, "Create table bla (id int auto_increment, primary key(id))");
        }
}

