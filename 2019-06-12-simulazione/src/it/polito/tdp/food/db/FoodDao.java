package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Edge;


public class FoodDao {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}

	public List<Edge> listEdges(Map<Integer, Condiment> idMap) {
		String sql = "SELECT fc1.condiment_food_code AS c1, fc2.condiment_food_code AS c2, COUNT(fc1.food_code) AS ct " + 
				"FROM food_condiment AS fc1, food_condiment AS fc2 " + 
				"WHERE fc1.food_code=fc2.food_code " + 
				"	AND fc1.condiment_food_code!=fc2.condiment_food_code " + 
				"GROUP BY fc1.condiment_food_code, fc2.condiment_food_code" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Edge> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Edge(idMap.get(res.getInt("c1")),
							idMap.get(res.getInt("c2")),
							res.getInt("ct")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
}
