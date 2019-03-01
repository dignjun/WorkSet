package com.example.template;

public class Foo {
    public static void main(String[] args) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        jdbcTemplate.execute(new ConnectionCallback() {
//
//            public Object doInConnection(Connection con) throws SQLException, DataAccessException {
//                // do insert
//                PreparedStatement ps = null;
//                try {
//                    ps = con.prepareStatement("insert into foo (name,age) values (zhangsan, 18)");
//                    ps.executeUpdate();
//                } finally {
//                    JdbcUtils.closeStatement(ps);
//                }
//                // get the key
//                Statement keystmt = null;
//                ResultSet rs = null;
//                HashMap keys = new HashMap(1);
//                try {
//                    keystmt = con.createStatement();
//                    rs = keystmt.executeQuery("select * from foo");
//                    if(rs.next()){
//                        String name = rs.getString(1);
//                        int age = rs.getInt(2);
//                        System.out.println(name + ":" + age);
//                    }
//                } finally {
//                    JdbcUtils.closeResultSet(rs);
//                    JdbcUtils.closeStatement(keystmt);
//                }
//                return null;
//            }
//        });
    }
}
