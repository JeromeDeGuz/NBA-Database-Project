import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Scanner;

public class project {
    static Connection connection;
    public static void main(String[] args) {
        MyDatabase db = new MyDatabase();

        runConsole(db);
        System.out.println("Exiting...");
    }

    public static void runConsole(MyDatabase db) {

		Scanner console = new Scanner(System.in);
		System.out.print("Welcome to the NBA Database! Type h for help. ");
		System.out.print("db > ");
		String line = console.nextLine();
		String[] parts;

		while (line != null && !line.equals("quit")) {
			parts = line.split("\\s+");
			if(parts[0].equals("h")){
                printHelp();
            } else if(parts[0].equals("r")) {
                if(parts.length == 3){
                    db.roster(parts[1], parts[2]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("gap")) {
                if(parts.length == 2){
                    db.gameAppear(parts[1]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("mates")) {
                if(parts.length == 4){
                    db.teammates(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("ro")) {
                db.rankOfficials();
            } else if(parts[0].equals("si")) {
                db.stadiumInfo();
            } else if(parts[0].equals("pc")) {
                if(parts.length == 2){
                    db.playerCountry(parts[1]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("rc")) {
                if(parts.length == 2){
                    db.rankCoaches(parts[1]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("ll")) {
                if(parts.length == 4){
                    db.leagueAvg(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("crp")) {
                if(parts.length == 4){
                    db.compareAvg(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("spt")) {
                if(parts.length == 3){
                    db.playerStatsPerTeam(parts[1], parts[2]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("cc")) {
                db.champs();
            } else if(parts[0].equals("per")) {
                if(parts.length == 3){
                    db.per(parts[1], parts[2]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("mr")) {
                if(parts.length == 3){
                    db.draftComm(parts[1], parts[2]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if(parts[0].equals("ts")) {
                if(parts.length == 2){
                    db.totalStat(parts[1]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else {
                System.out.println("The entered command does not exist, please type h for help!");
            }
            System.out.print("db > ");
			line = console.nextLine();
	    }
        console.close();
    }

    private static void printHelp(){
        System.out.println("NBA Database");
        System.out.println("Commands:");

        
        String formatString = "| %-15s | %10s | %10s | %10s | %10s | %10s | %10s |%n";
        System.out.printf("+-----------------+-----------------+-----------------+");
        System.out.printf("+-----------------+-----------------+-----------------+%n");
        System.out.printf(formatString, "Jerome", "Gurwinder", "3", "4", "5", "6", "7");
        System.out.printf("+-----------------+-----------------+-----------------+");
        System.out.printf("+-----------------+-----------------+-----------------+%n");
        System.out.printf(formatString, "Jerome", "Gurwinder", "3", "4", "5", "6", "7");
        System.out.printf(formatString, "Alice", "25", "3", "4", "5", "6", "7");
        
        //1
        System.out.println("r <team> <season> - Output roster for a team in a particular season\n                    NOTE: <season> must be of the form YYYY\n");
        //2
        System.out.println("gap <limit> - Get game appearance percentage of each player in each season\n              NOTE: <limit> must be a positive integer which represents how many records you want to see\n");
        //3
        System.out.println("mates <first> <last> <team> - Show all the teammates for a particular player\n");
        //4
        System.out.println("ro - Rank the officials based on number of games officiated\n");
        //5
        System.out.println("si - Show all the teams and their arena name and capacity\n");
        //6
        System.out.println("pc <season> - Show the total number of players from each country in a particular season\n              NOTE: <season> must be of the form YYYY");
        //7
        System.out.println("rc <limit> - Rank coaches with highest win percentage in the regular season\n             NOTE: <limit> must be a positive integer which represents how many records you want to see");
        //8
        System.out.println("ll <statType> <season> <limit> - League leaders in a major stat category based on averages for players in a particular season\n                                 NOTE: <season> must be of the form YYYY\n                                 NOTE: <limit> must be a positive integer which represents the limit\n                                 NOTE: <statType> must be from the following:\n                                 pts, rbs, ast, blk, stl, tov, mins, fgm, fga, 3pm, 3pa, ftm, fta, oreb, dreb, pf\n");
        //9
        System.out.println("crp <statType> <first> <last> - Compare a player's regular season career averages against their playoff averages\n                                NOTE: <statType> must be from the following:\n                                pts, rbs, ast, blk, stl, tov, mins, fgm, fga, 3pm, 3pa, ftm, fta, oreb, dreb, pf\n");
        //10
        System.out.println("spt <first> <last> - Major stat averages for a player for all the teams he played for in his career\n");
        //11
        System.out.println("cc - List all championship winning teams in chronological order\n");
        //12
        System.out.println("per <team> <season> - Highest player efficiency rating (PER) on a team in a particular season\n                      NOTE: <season> must be of the form YYYY\n");
        //13
        System.out.println("mr <year> <round> - Given a specific draft round of players, measure their performance through their careeer\n                    NOTE: <year> must be of the form YYYY\n                    NOTE: <round> must be either 1 or 2\n");
        //14
        System.out.println("ts <statType> - Get the career totals for a specific stat for all players\n                NOTE: <statType> must be from the following:\n                pts, rbs, ast, blk, stl, tov, mins, fgm, fga, 3pm, 3pa, ftm, fta, oreb, dreb, pf\n");

        System.out.println("quit - To exit program");

        System.out.println("-----------------End of Help-----------------");
    }
}

class MyDatabase{
    private Connection connection;

    public MyDatabase(){
		try {
			String url = "jdbc:sqlite:project.db";

            //createTables();
            connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
    }

    //1
    public void roster(String teamName, String season){
        try{
            int seas = Integer.parseInt(season);
            String sql = "Select firstName, lastName, jerseyNumber from Player natural join Play Natural join Team where seasonID = ? AND lower(teamName) like lower(?);";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, seas);
            statement.setString(2, "%"+teamName+"%");

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing Roster for "+teamName+" for the "+season+" season:");

            while(resultSet.next()){
                System.out.println(resultSet.getString("firstName")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("jerseyNumber"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //2
    public void gameAppear(String lim){
        try{
            int num = Integer.parseInt(lim);
            String sql = "with gamesPlayedPlayer as ( select playerID, firstName, lastName, seasonID, count(gps.gameID) as playerGP from Player natural join GamePlayerStats gps left join RegularGame rg on gps.gameID = rg.gameID left join PlayoffGame pg on gps.gameID = pg.gameID group by playerID, firstName, lastName, seasonID), GamesPlayedTeam as (select teamName, seasonID, count(gts.gameID) as teamGP from GameTeamStats gts left join RegularGame rg on gts.gameID = rg.gameID left join PlayoffGame pg on gts.gameID = pg.gameID group by teamName, seasonID) select seasonID, teamName, firstName, lastName, 100.0*(gpp.playerGP/gpt.teamGP) as appearancePercentage from GamesPlayedPlayer gpp join Play on gpp.seasonID = Play.seasonID join GamesPlayedTeam gpt on Play.teamName = gpt.teamName order by appearancePercentage desc limit ?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, num);

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the game appearance percentage for each player for each season:");

            while(resultSet.next()){
                System.out.println(resultSet.getString("firstName")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("seasonID")+" "+resultSet.getString("teamName")+" "+resultSet.getInt("appearancePercentage"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //3
    public void teammates(String first, String last, String team){
        try{
            String sql = "with seasonTeamsPlayed as( select seasonID, teamName from Play natural join Player where lower(firstName) = lower(?) and lower(lastName) = lower(?) and lower(teamName) = lower(?)), xroster as ( select playerID, firstName, lastName from Player natural join Play where (seasonID, teamName) in seasonsTeamsPlayed) select playerID, firstName, lastName from Player where (playerID, firstName, lastName) in xroster and playerID not in (select playerID from Player natural join Play where lower(firstName) = lower(?) and lower(lastName) = lower(?) and lower(teamName) = lower(?));";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+first+"%");
            statement.setString(2,"%"+last+"%");
            statement.setString(3,"%"+team+"%");
            statement.setString(4,"%"+first+"%");
            statement.setString(5,"%"+last+"%");
            statement.setString(6,"%"+team+"%");

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing all the teammates of "+first+" "+last+":");

            while(resultSet.next()){
                System.out.println(resultSet.getString("firstName")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("playerID"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //4
    public void rankOfficials(){
        try{
            String sql = "select officialID, firstName, lastName, count(gameID) as numGames from Officials natural join Officiate group by officialID, firstName, lastName order by numGames desc;";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing the officials who have officiated the most games:");

            while(resultSet.next()){
                System.out.println(resultSet.getString("firstName")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("officialID")+" "+resultSet.getInt("numGames"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //5
    public void stadiumInfo(){
        try{
            String sql = "select teamName, capacity from Team natural join Stadium order by teamName;";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing all the teams and their stadium info:");

            while(resultSet.next()){
                System.out.println(resultSet.getString("teamName")+" "+resultSet.getInt("capacity"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //6
    public void playerCountry(String season){
        try{
            int seas = Integer.parseInt(season);
            String sql = "select country, count(playerID) as num from Play natural join Player natural join Location where seasonID = ? group by country order by country;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, seas);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing total number of players from each country:");

            while(resultSet.next()){
                System.out.println(resultSet.getString("country")+" "+resultSet.getInt("num"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //7
    public void rankCoaches(String limit){
        try{
            int lim = Integer.parseInt(limit);
            String sql = "with coachGamesWon as ( select coachID, firstName, lastName, count(gameID) as gamesWon from RegularGame natural join GameTeamStats natural join GameTeamInfo natural join Team natural join Manage natural join Coach where winner = teamName group by coachID, firstName, lastName), coachGamesPlayed as (select coachID, firstName, lastName, count(gameID) as gamesPlayed from RegularGame natural join GameTeamStats natural join Team natural join Manage natural join Coach group by coachID, firstName, lastName) select c.coachID, c.firstName, c.lastName, 100.0*(cgw.gamesWon/cgp.gamesPlayed) as winPercentage from Coach c join coachGamesWon cgw on c.coachID = cgw.coachID join coachGamesPlayed cgp on c.coachID = cgp.coachID order by winPercentage desc limit ?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lim);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing top "+lim+" coaches based on regular season win percentage: ");

            while(resultSet.next()){
                System.out.println(resultSet.getString("coachID")+" "+resultSet.getString("firstName")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("winPercentage"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //8
    public void leagueAvg(String stat, String season, String limit){
        try{
            int seas = Integer.parseInt(season);
            int lim = Integer.parseInt(limit);
            String sql;
            String[] validStats = {"pts", "rbs", "ast", "blk", "stl", "tov", "mins", "fgm", "fga", "3pm", "3pa", "ftm", "fta", "oreb", "dreb", "pf"};
            boolean check = false;
            for(String x:validStats){
                if(stat.equals(x)){
                    check = true;
                }
            }
            if(check){
                sql = "select p.firstname,p.lastName, avg(gps."+stat+") as avg from GamePlayerStats gps join Player p on gps.playerID = p.playerID join RegularGame rg on gps.gameID = rg.gameID where rg.seasonYear = ? group by p.firstname,p.lastName, order by avg desc limit ?;";
            } else{
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }
            

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, seas);
            statement.setInt(2, lim);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing top "+lim+" players based on regular season averages for "+stat+" for the "+seas+" season: ");

            while(resultSet.next()){
                System.out.println(resultSet.getString("firstname")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("avg"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //9
    public void compareAvg(String stat, String first, String last){
        try{
            String sql;
            String[] validStats = {"pts", "rbs", "ast", "blk", "stl", "tov", "mins", "fgm", "fga", "3pm", "3pa", "ftm", "fta", "oreb", "dreb", "pf"};
            boolean check = false;
            for(String x:validStats){
                if(stat.equals(x)){
                    check = true;
                }
            }
            if(check){
                sql = "select p.firstName, p.lastName, (select avg(gps1."+stat+") from GamePlayerStats gps1 join RegularGame rg1 on gps1.playerID = rg1.playerID) as regAvg, (select avg(gps2."+stat+") from GamePlayerStats gps2 join RegularGame rg2 on gps2.playerID = rg2.playerID) as playoffAvg from Player p where lower(p.firstName) like lower(?) and lower(p.lastName) like lower(?);";
            } else{
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }
            
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%"+first+"%");
            statement.setString(2, "%"+last+"%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing a comparison of player: "+first+" "+last+" and his "+stat+" in the regular season vs playoffs: ");

            while(resultSet.next()){
                System.out.println(resultSet.getString("firstname")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("regAvg")+" "+resultSet.getInt("playoffAvg"));
            }

            resultSet.close();
            statement.close();
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //10
    public void playerStatsPerTeam(String first, String last){
        try{
            String sql = "select p.firstName, p.lastName, avg(gps.pts) as points, avg(gps.rbs) as rebounds, avg(gps.ast) as assists, avg(gps.blk) as blocks, avg(gps.stl) as steals from Player p join Play on p.playerID = Play.playerID join GamePlayerStats gps on p.playerID = gps.playerID join Team on Play.teamName = Team.teamName where lower(p.firstName) = lower(?) AND lower(p.lastName) = lower(?) group by p.playerID, p.firstName, p.lastName, Play.teamName order by Play.teamName;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%"+first+"%");
            statement.setString(2, "%"+last+"%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing Major stat averages for player: "+first+" "+last+" for all teams they have played for: ");
            while(resultSet.next()){
                System.out.println(resultSet.getString("firstname")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("points")+" "+resultSet.getInt("rebounds")+" "+resultSet.getInt("assists")+" "+resultSet.getInt("blocks")+" "+resultSet.getInt("steals"));
            }
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //11
    public void champs(){
        try{
            String sql = "select seasonYear, champion from Season order by seasonYear;";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing the Champions in chronological order: ");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("seasonYear")+" "+resultSet.getString("champion"));
            }
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //12

    public void per(String team, String season){
        try{
            int seas = Integer.parseInt(season);
            String sql = "select p.playerID, p.firstName, p.lastName, AVG((gps.pts+gps.rbs+gps.ast+gps.stl+gps.blk)-(gps.tov+gps.pf+(gps.fga-gps.fgm))) as avgPer from Play join Player p on Play.playerID = p.playerID join GamePlayerStats gps on gps.playerID = Play.playerID left join RegularGame rg on rg.gameID = gps.gameID left join PlayoffGame pg on pg.gameID = gps.gameID where lower(Play.teamName) like lower(?) AND (rg.seasonYear = ? OR pg.seasonYear = ?) group by p.playerID, p.firstName, p.lastName order by avgPer desc limit 10;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+team+"%");
            statement.setInt(2,seas);
            statement.setInt(3,seas);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the highest player efficiency rating from "+team+" for the "+seas+" season: ");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("playerID")+" "+resultSet.getString("firstName")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("avgPer"));
            }
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //13
    public void draftComm(String year, String round){
        try{
            int y = Integer.parseInt(year);
            int r = Integer.parseInt(round);
            String sql = "with DraftYrRound as(select di.draftYear, di.round, di.pick, di.playerID from DraftInfo di where di.round = ? AND di.draftYear = ?) Select dyr.draftYear, dyr.draftRound, dyr.draftPick, p.firstName, p.lastName, Play.seasonID, AVG((gps.pts+gps.rbs+gps.ast+gps.stl+gps.blk)-(gps.tov+gps.pf+(gps.fga-gps.fgm))) as avgPer from DraftYrRound dyr join Play on Play.playerID = dyr.playerID join Player p on p.playerID = Play.playerID join GamePlayerStats gps on gps.playerID = p.playerID left join RegularGame rg on rg.gameID = gps.gameID left join PlayoffGame pg on pg.gameID where Play.seasonID >= ? group by fyr.draftYear, dyr.round, p.firstName, p.lastName, dyr.draftPick, Play.seasonID order by p.firstName, p.lastName, Play.seasonID;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,r);
            statement.setInt(2,y);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the player efficiency rating for players drafted in round "+r+" of the "+y+" draft:");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("draftYear")+" "+resultSet.getInt("draftRound")+" "+resultSet.getInt("draftPick")+" "+resultSet.getInt("seasonID")+" "+resultSet.getString("firstName")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("avgPer"));
            }
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //14
    public void totalStat(String stat){
        try{
            String sql;
            String[] validStats = {"pts", "rbs", "ast", "blk", "stl", "tov", "mins", "fgm", "fga", "3pm", "3pa", "ftm", "fta", "oreb", "dreb", "pf"};
            boolean check = false;
            for(String x:validStats){
                if(stat.equals(x)){
                    check = true;
                }
            }
            if(check){
                sql = "select p.firstName, p.lastName, sum(gps."+stat+") as total from Player p join GamePlayerStats gps on gps.playerID = p.playerID group by p.playerID, p.firstName, p.lastName;";
            } else{
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,stat);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the total "+stat+" of all players all time");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("draftYear")+" "+resultSet.getInt("draftRound")+" "+resultSet.getInt("draftPick")+" "+resultSet.getInt("seasonID")+" "+resultSet.getString("firstName")+" "+resultSet.getString("lastName")+" "+resultSet.getInt("avgPer"));
            }
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }
    private void createTables(){
        String conf = "create table conference( "+
                      " confName text, "+
                      " primary key(confName));";
        try {
            connection.createStatement().executeUpdate(conf);

            String div = "create table Division( "+
                        "divName text, "+
                        "confName text, "+
                        "primary key(divName) "+
                        "foreign key(confName) references conference);";
            
            connection.createStatement().executeUpdate(div);

            String coach = "create table Coach( "+
                           " coachID integer, "+
                           " coachName text, "+
                           "primary key(coachID);";
            
            connection.createStatement().executeUpdate(coach);

            String team = "create table Team( "+
                          " teamName text, "+
                          " yearFounded integer, "+
                          " divName text, "+
                          " stadiumName text, "+
                          " primary key(teamName), "+
                          " foreign key(divName) references Division, "+
                          " foreign key(stadiumName) references Stadium);";

            connection.createStatement().executeUpdate(team);

            String stad = "create table Stadium( "+
                          " stadiumName text, "+
                          " capacity integer, "+
                          " locationID integer, "+
                          " primary key(stadiumName), "+
                          " foreign key(locationID) references Location);";

            connection.createStatement().executeUpdate(stad);

            String loc = "create table Location( "+
                         " locationID integer, "+
                         " city text, "+
                         " country text, "+
                         " primary key(locationID));";

            connection.createStatement().executeUpdate(loc);

            String Game = "create table Game( "+
                             " gameID integer, "+
                             " date text, "+
                             " seasonYear integer, "+
                             " stadiumName text, "+
                             " primary key(gameID), "+
                             " foreign key(seasonYear) references Season, "+
                             " foreign key(stadiumName) references Stadium);";

            connection.createStatement().executeUpdate(Game);

            String pGame = "create table PlayoffGame( "+
                             " gameID integer, "+
                             " round text, "+
                             " primary key(gameID));";

            connection.createStatement().executeUpdate(pGame);

            String seas = "create table Season( "+
                          " seasonYear integer, "+
                          " champion text, "+
                          " primary key(seasonYear), "+
                          " foreign key(champion) references Team(teamName));";

            connection.createStatement().executeUpdate(seas);

            String off = "create table Officials( "+
                         " officialID integer, "+
                         " name text, "+
                         " jerseyNumber integer, "+
                         " primary key(officialID));";

            connection.createStatement().executeUpdate(off);

            String man = "create table Manage( "+
                         " coachID integer, "+
                         " teamName text, "+
                         " seasonYear integer, "+
                         " primary key(coachID, teamName, seasonYear), "+
                         " foreign key(teamName) references Team, "+
                         " foreign key(coachID) references Coach, "+
                         " foreign key(seasonYear) references Season);";

            connection.createStatement().executeUpdate(man);

            String teach = "create table Teach( "+
                           " coachID integer, "+
                           " playerID integer, "+
                           " primary key(coachID, playerID), "+
                           " foreign key(coachID) references Coach, "+
                           " foreign key(playerID) references Player);";

            connection.createStatement().executeUpdate(teach);

            // We didnt have a Game relation in our normalization word doc. Only PlayoffGame/RegularGame
            // How do we go about this?
            // Also, couldnt homeTeam, awayTeam and winner reference Team?
            String GTI = "create table GameTeamInfo( "+
                         " gameID integer, "+
                         " homeTeam text, "+
                         " awayTeam text, "+
                         " winner text, "+
                         " primary key(gameID), "+
                         " foreign key(gameID) references Game(gameID));";

            connection.createStatement().executeUpdate(GTI);

            String GPS = "create table GamePlayerStats( "+
                         " playerID integer, "+
                         " gameID integer, "+
                         " mins integer, "+
                         " pts integer, "+
                         " fgm integer, "+
                         " fga integer, "+
                         " fg% real, "+
                         " 3pm integer, "+
                         " 3pa integer, "+
                         " 3p% real, "+
                         " ftm integer, "+
                         " fta integer, "+
                         " ft% real, "+
                         " oreb integer, "+
                         " dreb integer, "+
                         " reb integer, "+
                         " ast integer, "+
                         " stl integer, "+
                         " blk integer, "+
                         " tov integer, "+
                         " pf integer, "+
                         " +/- integer, "+
                         " primary key(gameID, playerID), "+
                         " foreign key(gameID) referenes Game(gameID), "+
                         " foreign key(playerID) references Player);";

            connection.createStatement().executeUpdate(GPS);

            String gti = "create table GameTeamInfo( "+
                         " gameID integer, "+
                         " homeTeam text, "+
                         " awayTeam text, "+
                         " winner text, "+
                         " primary key(gameID), "+
                         " foreign key(gameID) reference Game(gameID));";

            connection.createStatement().executeUpdate(gti);

            String gts = "create table GameTeamStats( "+
                         " teamName text, "+
                         " gameID integer, "+
                         " mins integer, "+
                         " pts integer, "+
                         " fgm integer, "+
                         " fga integer, "+
                         " fg% real, "+
                         " 3pm integer, "+
                         " 3pa integer, "+
                         " 3p% real, "+
                         " ftm integer, "+
                         " fta integer, "+
                         " ft% real, "+
                         " oreb integer, "+
                         " dreb integer, "+
                         " reb integer, "+
                         " ast integer, "+
                         " stl integer, "+
                         " blk integer, "+
                         " tov integer, "+
                         " pf integer, "+
                         " +/- integer, "+
                         " primary key(teamName, gameID), "+
                         " foreign key(teamName) references Team, "+
                         " foreign key(gameID) references Game(gameID));";

            connection.createStatement().executeUpdate(gts);

            String play = "create table Play( "+
                          " seasonID integer, "+
                          " playerID integer, "+
                          " teamName text, "+
                          " jersey integer, "+
                          " primary key(seasonID, playerID), "+
                          " foreign key(seasonID) references Season(seasonYear), "+
                          " foreign key(playerID) references Player, "+
                          " foreign key(teamName) references Team);";

            connection.createStatement().executeUpdate(play);

            // The foreign key reference to the Game table is an issue here too
            String officiate = "create table Officiate( "+
                               " gameID integer, "+
                               " officialID integer, "+
                               " primary key(gameID, officialID), "+
                               " foreign key(gameID) references Game(gameID), "+
                               " foreign key(officialID) references Officials);";

            connection.createStatement().executeUpdate(officiate);
            
            //In the normalization doc, we have a set B that is other attributes in the Player table. What are those attributes?
            //
            String player = "create table Player( "+
                            " playerID integer, "+
                            " firstName text, "+
                            " lastName text, "+
                            " birthdate text, "+
                            " height real, "+
                            " weight real, "+
                            " position text, "+
                            " fromYear integer, "+
                            " toYear integer, "+
                            " birthLocation integer, "+
                            " primary key(playerID), "+
                            " foreign key(birthLocation) references Location(locationID));";

            connection.createStatement().executeUpdate(player);

            String draftInfo = "create table DraftInfo( "+
                               " draftYear integer, "+
                               " draftRound integer, "+
                               " draftPick integer, "+
                               " playerID integer, "+
                               " teamDrafted text, "+
                               " primary key(draftYear, draftRound, draftPick), "+
                               " foreign key(playerID) referenes Player, "+
                               " foreign key(teamDrafted) references Team(teamName));";

            connection.createStatement().executeUpdate(draftInfo);



        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}
