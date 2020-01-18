package com.company;

import java.sql.*;

public class Main {
    private final static String url = "jdbc:postgresql://localhost:5432/";
    private final static String user = "postgres";
    private final static String password = "postgres";
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return conn;
    }
    public static void main(String[] args) {
        PieceOfNews pieceOfNews = new PieceOfNews("A", "AA");
        PieceOfNews pieceOfNews1 = new PieceOfNews("B", "AA");
        PieceOfNews pieceOfNews2 = new PieceOfNews("C", "AA");
        //addPieceOfNews(pieceOfNews);
        //addPieceOfNews(pieceOfNews1);
        //addPieceOfNews(pieceOfNews2);
        //System.out.println(getPieceOfNews("B"));
        //deletePieceOfNews("B");
        //updatePieceOfNewsHead("D", "A");
        //updatePieceOfNewsText("B", "BB");
        //updatePieceOfNewsHeadAndText("C", "E", "EE");
    }

    public static boolean addPieceOfNews(PieceOfNews pieceOfNews) {
        String SQL = "insert into news(head, text) values (?,?)";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1,pieceOfNews.getHead());
            statement.setString(2,pieceOfNews.getText());
            statement.executeUpdate();
            System.out.println("Successfully created piece of news: " + pieceOfNews.getHead());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static PieceOfNews getPieceOfNews(String head) {
        String SQL = "select * from news where head = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1,head);
            try(ResultSet rs = statement.executeQuery()){
                if(rs.next()) {
                    PieceOfNews pieceOfNews = new PieceOfNews(rs.getString("head"), rs.getString("text"));
                    return pieceOfNews;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deletePieceOfNews(String head) {
        String SQL = "delete from news where head = ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, head);
            statement.executeUpdate();
            System.out.println("Successfully delete piece of news: " + head);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePieceOfNewsHead(String head, String newHead) {
        String SQL = "update news set head = ? where head like ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, newHead);
            statement.setString(2, "%" + head + "%");
            statement.executeUpdate();
            System.out.println("Successfully update  piece of news head: " + head + " to " + newHead);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePieceOfNewsText(String head, String newText) {
        String SQL = "update news set text = ? where head like ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, newText);
            statement.setString(2, "%" + head + "%");
            statement.executeUpdate();
            System.out.println("Successfully update  piece of news text: " + head);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePieceOfNewsHeadAndText(String head,String newHead, String newText) {
        String SQL = "update news set head = ?, text = ? where head like ?";
        try (Connection conn = connect();
             PreparedStatement statement = conn.prepareStatement(SQL)) {
            statement.setString(1, newHead);
            statement.setString(2, newText);
            statement.setString(3, "%" + head + "%");
            statement.executeUpdate();
            System.out.println("Successfully update  piece of news text and head : " + head + " to " + newHead);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

class PieceOfNews{
    private String head;
    private String text;

    public PieceOfNews(String head, String text) {
        this.head = head;
        this.text = text;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "PieceOfNews{" +
                "head='" + head + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}