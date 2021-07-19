package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.EdgeAndWeight;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	
	public List<EdgeAndWeight> getArchi(Map<Integer, Food> foodIdMap){
		String sql = "select f1.food_code as id1,f2.food_code as id2, avg(c.condiment_calories) as peso "
				+ "from food_condiment f1, food_condiment f2, condiment c "
				+ "where f1.food_code>f2.food_code and f1.condiment_code=c.condiment_code "
				+ "and f1.condiment_code=f2.condiment_code "
				+ "group by f1.food_code,f2.food_code";
		List<EdgeAndWeight> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(foodIdMap.get(res.getInt("id1"))!=null && foodIdMap.get(res.getInt("id2"))!=null) {
					result.add(new EdgeAndWeight(res.getInt("id1"),res.getInt("id2"),res.getDouble("peso")));
				}
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	public List<Integer> getVertexId(int nPorzioni){
		String sql = "select food_code as id, count(portion_id) as peso "
				+ "from portion "
				+ "group by food_code "
				+ "having peso<=?";
		Connection conn = DBConnect.getConnection();
		List<Integer> result = new ArrayList<>();
		try {
			

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, nPorzioni);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					result.add(res.getInt("id"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
}
