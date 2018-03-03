import java.sql.ResultSet;
import java.sql.SQLException;

public class Example {
        public static void main(String... args) {
                DBConnect.init();

                DBConnect.showTables();
                DBConnect.descTable("team42");
//                ResultSet rs = DBConnect.sendQuery(DBConnect.TypeQuery.SELECT, "select * from team42");
//                try {
//
//                        while (rs != null && rs.next()) {
//                                int id = rs.getInt("id");
//                                String name = rs.getString("name");
//                                String function = rs.getString("function");
//                                System.out.printf("%d %s %s\n", id, name, function);
//                        }
//                } catch (SQLException e) {
//                        throw new RuntimeException("Something wrong with results.");
//                }
//                DBConnect.sendQuery(DBConnect.TypeQuery.UPDATE, "insert into recipe values(1, NULL,  'Жареная картошка', " +
//                        "'Дефолтная жареная картошечка', 30, 1000, 100.0, NULL, " +
//                        "'1, 10, https://drive.google.com/open?id=1WfsEhdpSHiWHHwIPFsxVV00LH_2dgjLf |Картошку тщательно помыть (не чистить) и нарезать соломкой. " +
//                        "В сковороде разогреть оливковое масло (немного) и выложить картошку. " +
//                        "Жарить на большом огне, не мешая, примерно 4-5 минуты. Затем аккуратно " +
//                        "перевернуть и жарить дальше, чтобы со всех сторон образовалась равномерная золотистая корочка.&" +
//                        "2, 10, https://drive.google.com/open?id=1WfsEhdpSHiWHHwIPFsxVV00LH_2dgjLf |" +
//                        "Вот такая. Пока картошечка жарится, сделать салатик из редиса, огурцов, зелени и сметаны.&" +
//                        "3, 10, https://drive.google.com/open?id=1WfsEhdpSHiWHHwIPFsxVV00LH_2dgjLf |" +
//                        "Когда картошка хорошо поджарится со всех сторон, добавить мелко нарубленный чеснок, посолить, накрыть крышкой, убавить огонь и прогреть несколько минут. " +
//                        "Затем добавить зелень, аккуратно перемешать, прогреть ещё пару минут и сразу же подавать.')");

//                DBConnect.sendQuery(DBConnect.TypeQuery.UPDATE, "update recipe set steps = '1,10,https://netpics.org/images/2018/03/02/WYFpZ.jpg|" +
//                        "Картошку тщательно помыть (не чистить) и нарезать соломкой." +
//                        "В сковороде разогреть оливковое масло (немного) и выложить картошку." +
//                        "Жарить на большом огне, не мешая, примерно 4-5 минуты. Затем аккуратно " +
//                        "перевернуть и жарить дальше, чтобы со всех сторон образовалась равномерная золотистая корочка.&" +
//                        "2,10,https://netpics.org/images/2018/03/02/WYFpZ.jpg|" +
//                        "Вот такая. Пока картошечка жарится, сделать салатик из редиса, огурцов, зелени и сметаны.&" +
//                        "3,10,https://netpics.org/images/2018/03/02/WYFpZ.jpg|" +
//                        "Когда картошка хорошо поджарится со всех сторон, добавить мелко нарубленный чеснок, посолить, накрыть крышкой, убавить огонь и прогреть несколько минут. " +
//                        "Затем добавить зелень, аккуратно перемешать, прогреть ещё пару минут и сразу же подавать.' where id_recipe=1");
//                DBConnect.sendQuery(DBConnect.TypeQuery.CREATE, "Create table bla (id int auto_increment, primary key(id))");
//                DBConnect.sendQuery(DBConnect.TypeQuery.CREATE, "");


        }
}

