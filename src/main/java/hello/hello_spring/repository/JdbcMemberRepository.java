package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//코드의 흐름을 알고 있으면 된다.
public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
        //위와 같이 작성하게 되면 dataSource.getConnection()을 얻을 수 있음.

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";
        //?는 파라미터 바인딩 떄문이다.

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //결과를 받는 것.

        //DB는 반환하는 과정이 필요하다. 그래야 데이터가 계속 안 쌓이면서 잘 관리가 된다.
        //안 그러면 나중에 데이터가 쌓여 터지게 된다.
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //key옵션: DB에서 primary를 해야 id값을 얻을 수 있었음
            //이를 해주는 문구임.

            pstmt.setString(1, member.getName());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys(); //DB에 있는 내용을 꺼내온다.

            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql ="select * from member where id =?";
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try {
            conn =getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs=pstmt.executeQuery();

            if(rs.next()){
                Member member=new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return  Optional.of(member);
            }else{
                return Optional.empty();
            }
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql="select * from member where name=?";
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try{
            conn= getConnection();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,name);

            rs=pstmt.executeQuery();

            if(rs.next()){
                Member member=new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);
        }

    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}