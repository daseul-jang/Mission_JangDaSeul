package com.techit.dao;

import com.techit.config.Database;
import com.techit.dto.QuoteDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuoteDao {
    private final Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public QuoteDao() {
        Database db = new Database();
        conn = db.getConnection();
        pstmt = null;
        rs = null;
    }

    public void save(final QuoteDto quoteDto) {
        try {
            String sql = "INSERT INTO quote_tb SET quote_txt = ?, quote_author = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, quoteDto.getQuoteTxt());
            pstmt.setString(2, quoteDto.getQuoteAuthor());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public QuoteDto findById(final int quoteNo) {
        try {
            String sql = "SELECT * FROM quote_tb WHERE quote_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quoteNo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new QuoteDto(rs.getInt("quote_no"), rs.getString("quote_txt"), rs.getString("quote_author"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<QuoteDto> findAll() {
        List<QuoteDto> quotes = new ArrayList<>();

        try {
            String sql = "SELECT * from quote_tb";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                QuoteDto quoteDto = new QuoteDto();
                quoteDto.setQuoteNo(rs.getInt("quote_no"));
                quoteDto.setQuoteTxt(rs.getString("quote_txt"));
                quoteDto.setQuoteAuthor(rs.getString("quote_author"));

                quotes.add(quoteDto);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return quotes;
    }

    public void update(final QuoteDto quoteDto) {
        try {
            String sql = "UPDATE quote_tb SET quote_txt = ?, quote_author = ? WHERE quote_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, quoteDto.getQuoteTxt());
            pstmt.setString(2, quoteDto.getQuoteAuthor());
            pstmt.setInt(3, quoteDto.getQuoteNo());
            pstmt.executeUpdate();

            System.out.printf("%d번 명언이 수정되었습니다.\n", quoteDto.getQuoteNo());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove(final int quoteNo) {
        try {
            String sql = "DELETE FROM quote_tb WHERE quote_no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, quoteNo);
            pstmt.executeUpdate();

            System.out.printf("%d번 명언이 삭제되었습니다.\n", quoteNo);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void connectionClose() {
        try {
            conn.close();
            pstmt.close();
            Optional.ofNullable(rs).ifPresent(rs -> {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
