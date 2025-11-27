import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.jdbc;

public class populate {
    static Connection connection;
    public static void main(String[] args) {
        MyDatabase db = new MyDatabase();
    }
}

class MyDatabase{
    private Connection connection;

    //this constructor has just been copied from the Unit 10 transaction.java (Rob's notes)
    public MyDatabase(){
		try {
			String url = "jdbc:sqlite:project.db";

            connection = DriverManager.getConnection(url);
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
    }

    //1
    public void roster(String teamName, String season){
        try{
            int seas = Integer.parseInt(season);
            String sql = "Select firstName, lastName, jerseyNumber from Player natural join Play Natural join Team where seasonID=? AND teamName = ?;";

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
    public void teammates(String first, last, team){
        try{
            String sql = "with seasonTeamsPlayed as( select seasonID, teamName from Play natural join Player where firstName = ? and lastName = ? and teamName = ?), xroster as ( select playerID, firstName, lastName from Player natural join Play where (seasonID, teamName) in seasonsTeamsPlayed) select playerID, firstName, lastName from Player where (playerID, firstName, lastName) in xroster and playerID not in (select playerID from Player natural join Play where firstName = ? and lastName = ? and teamName = ?);";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+first+"%");
            statement.setString(2,"%"+last+"%");
            statement.setString(3,"%"+team+"%");

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

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

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

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

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

            String sql = "select p.firstname,p.lastName, avg(gps.?) as avg from GamePlayerStats gps join Player p on gps.playerID = p.playerID join RegularGame rg on gps.gameID = rg.gameID where rg.seasonYear = ? group by p.firstname,p.lastName, order by avg desc limit ?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, stat);
            statement.setInt(2, seas);
            statement.setInt(3, lim);
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
    public void compareAvg(String stat, String id){
        try{
            int playerID = Integer.parseInt(id);
            String sql = "select p.firstName, p.lastName, (select avg(gps1.?) from GamePlayerStats gps1 join RegularGame rg1 on gps1.playerID = rg1.playerID) as regAvg, (select avg(gps2.?) from GamePlayerStats gps2 join RegularGame rg2 on gps2.playerID = rg2.playerID) as playoffAvg from Player p where p.playerID = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, stat);
            statement.setString(2, stat);
            statement.setInt(3, playerID);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing a comparison of player: "+playerID+"'s "+stat+" in the regular season vs playoffs: ");

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
    public void playerStatsPerTeam(String id){
        try{
            int playerID = Integer.parseInt(id);
            String sql = "select p.firstName, p.lastName, avg(gps.pts) as points, avg(gps.rbs) as rebounds, avg(gps.ast) as assists, avg(gps.blk) as blocks, avg(gps.stl) as steals from Player p join Play on p.playerID = Play.playerID join GamePlayerStats gps on p.playerID = gps.playerID join Team on Play.teamName = Team.teamName where p.playerID = ? group by p.playerID, p.firstName, p.lastName, Play.teamName order by Play.teamName;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playerID);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing Major stat averages for player: "+playerID+" for all teams they have played for: ");
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
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the Champions in chronological order: ");
            while(resultSet.next()){
                System.out.println(resultSet.getInt("seasonYear")+" "+resultSet.getString("champion"));
            }
        } catch(SQLException e){
            e.printStackTrace(System.out);
        }
    }

    //12

    
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

            String regGame = "create table RegularGame( "+
                             " gameID integer, "+
                             " date text, "+
                             " seasonYear integer, "+
                             " stadiumName text, "+
                             " primary key(gameID), "+
                             " foreign key(seasonYear) references Season, "+
                             " foreign key(stadiumName) references Stadium);";

            connection.createStatement().executeUpdate(regGame);

            String pGame = "create table PlayoffGame( "+
                             " gameID integer, "+
                             " date text, "+
                             " round text, "+
                             " seasonYear integer, "+
                             " stadiumName text, "+
                             " primary key(gameID), "+
                             " foreign key(seasonYear) references Season, "+
                             " foreign key(stadiumName) references Stadium);";

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
            // String GTI = "create table GameTeamInfo( "+
            //              " gameID integer, "+
            //              " homeTeam text, "+
            //              " awayTeam text, "+
            //              " winner text, "+
            //              " primary key(gameID), "+
            //              " foreign key(gameID) references Game(gameID));";

            // connection.createStatement().executeUpdate(GTI);

            // String GPS = "create table GamePlayerStats( "+
            //              " playerID integer, "+
            //              " gameID integer, "+
            //              " mins integer, "+
            //              " pts integer, "+
            //              " fgm integer, "+
            //              " fga integer, "+
            //              " fg% real, "+
            //              " 3pm integer, "+
            //              " 3pa integer, "+
            //              " 3p% real, "+
            //              " ftm integer, "+
            //              " fta integer, "+
            //              " ft% real, "+
            //              " oreb integer, "+
            //              " dreb integer, "+
            //              " reb integer, "+
            //              " ast integer, "+
            //              " stl integer, "+
            //              " blk integer, "+
            //              " tov integer, "+
            //              " pf integer, "+
            //              " +/- integer, "+
            //              " primary key(gameID, playerID), "+
            //              " foreign key(gameID) referenes Game(gameID), "+
            //              " foreign key(playerID) references Player);";

            // // connection.createStatement().executeUpdate(GPS);

            // String gti = "create table GameTeamInfo( "+
            //              " gameID integer, "+
            //              " homeTeam text, "+
            //              " awayTeam text, "+
            //              " winner text, "+
            //              " primary key(gameID), "+
            //              " foreign key(gameID) reference Game(gameID));";

            // connection.createStatement().executeUpdate(gti);

            // String gts = "create table GameTeamStats( "+
            //              " teamName text, "+
            //              " gameID integer, "+
            //              " mins integer, "+
            //              " pts integer, "+
            //              " fgm integer, "+
            //              " fga integer, "+
            //              " fg% real, "+
            //              " 3pm integer, "+
            //              " 3pa integer, "+
            //              " 3p% real, "+
            //              " ftm integer, "+
            //              " fta integer, "+
            //              " ft% real, "+
            //              " oreb integer, "+
            //              " dreb integer, "+
            //              " reb integer, "+
            //              " ast integer, "+
            //              " stl integer, "+
            //              " blk integer, "+
            //              " tov integer, "+
            //              " pf integer, "+
            //              " +/- integer, "+
            //              " primary key(teamName, gameID), "+
            //              " foreign key(teamName) references Team, "+
            //              " foreign key(gameID) references Game(gameID));";

            // connection.createStatement().executeUpdate(gts);

            String play = "create table Play( "+
                          " SeasonID integer, "+
                          " playerID integer, "+
                          " teamName text, "+
                          " jersey integer, "+
                          " primary key(SeasonID, playerID), "+
                          " foreign key(SeasonID) references Season(seasonYear), "+
                          " foreign key(playerID) references Player, "+
                          " foreign key(teamName) references Team);";

            connection.createStatement().executeUpdate(play);

            // The foreign key reference to the Game table is an issue here too
            // String officiate = "create table Officiate( "+
            //                    " gameID integer, "+
            //                    " officialID integer, "+
            //                    " primary key(gameID, officialID), "+
            //                    " foreign key(gameID) references Game(gameID), "+
            //                    " foreign key(officialID) references Officials);";

            // connection.createStatement().executeUpdate(officiate);
            
            //In the normalization doc, we have a set B that is other attributes in the Player table. What are those attributes?
            //
            String player = "create table Player( "+
                            " playerID integer, "+
                            " firstName text, "+
                            " lastName text, "+
                            " birthdate text, "+
                            " school text, "+
                            " seasonExp real, "+
                            " position text, "+
                            " teamName text, "+
                            " teamCity text, "+
                            " fromYear integer, "+
                            " toYear integer, "+
                            " height real, "+
                            " weight real, "+
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
            // TODO: handle exception
            e.printStackTrace(System.out);
        }
    }
}

