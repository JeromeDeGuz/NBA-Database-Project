/* TO RUN CODE:
javac -cp sqlite-jdbc.jar project.java
java -cp .:sqlite-jdbc.jar project 
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.io.File;
import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

public class project {
    static Connection connection;

    public static void main(String[] args) {
        // uraniumconnect();
        MyDatabase db = new MyDatabase("NBAdatabase.db.sql");

        runConsole(db);
        System.out.println("Exiting...");
    }

    // handles connecting to uranium
    public static void uraniumconnect() {
        Properties prop = new Properties();
        String fileName = "auth.cfg";
        try {
            FileInputStream configFile = new FileInputStream(fileName);
            prop.load(configFile);
            configFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find config file.");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error reading config file.");
            System.exit(1);
        }
        String username = (prop.getProperty("username"));
        String password = (prop.getProperty("password"));

        if (username == null || password == null) {
            System.out.println("Username or password not provided.");
            System.exit(1);
        }

        String connectionUrl = "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;"
                + "database=cs3380;"
                + "user=" + username + ";"
                + "password=" + password + ";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";
    }

    // the general console loop
    public static void runConsole(MyDatabase db) {
    
        Scanner console = new Scanner(System.in);
        System.out.print("Welcome to the NBA Database! Type h for help. ");
        System.out.print("db > ");
        String line = console.nextLine();
        String[] parts;
    
        while (line != null && !line.equals("quit")) {
            parts = line.split("\\s+");
            if (parts[0].equals("h")) {
                printHelp();
            } else if (parts[0].equals("r")) {
                if (parts.length == 3) {
                    db.roster(parts[1], parts[2]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("gap")) {
                if (parts.length == 2) {
                    db.gameAppear(parts[1]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("mates")) {
                if (parts.length == 4) {
                    db.teammates(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("ro")) {
                db.rankOfficials();
            } else if (parts[0].equals("si")) {
                db.stadiumInfo();
            } else if (parts[0].equals("pc")) {
                if (parts.length == 2) {
                    db.playerCountry(parts[1]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("rc")) {
                if (parts.length == 2) {
                    db.rankCoaches(parts[1]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("ll")) {
                if (parts.length == 4) {
                    db.leagueAvg(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("crp")) {
                if (parts.length == 4) {
                    db.compareAvg(parts[1], parts[2], parts[3]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("spt")) {
                if (parts.length == 3) {
                    db.playerStatsPerTeam(parts[1], parts[2]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("cc")) {
                db.champs();
            } else if (parts[0].equals("per")) {
                if (parts.length == 3) {
                    db.per(parts[1], parts[2]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("mr")) {
                if (parts.length == 3) {
                    db.draftComm(parts[1], parts[2]);
                } else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
            } else if (parts[0].equals("ts")) {
                if (parts.length == 2) {
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
    
    private static void printHelp() {
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
    
        // 1
        System.out.println(
                "r <team> <season> - Output roster for a team in a particular season\n                    NOTE: <season> must be of the form YYYY\n");
        // 2
        System.out.println(
                "gap <limit> - Get game appearance percentage of each player in each season\n              NOTE: <limit> must be a positive integer which represents how many records you want to see\n");
        // 3
        System.out.println("mates <first> <last> <team> - Show all the teammates for a particular player\n");
        // 4
        System.out.println("ro - Rank the officials based on number of games officiated\n");
        // 5
        System.out.println("si - Show all the teams and their arena name and capacity\n");
        // 6
        System.out.println(
                "pc <season> - Show the total number of players from each country in a particular season\n              NOTE: <season> must be of the form YYYY");
        // 7
        System.out.println(
                "rc <limit> - Rank coaches with highest win percentage in the regular season\n             NOTE: <limit> must be a positive integer which represents how many records you want to see");
        // 8
        System.out.println(
                "ll <statType> <season> <limit> - League leaders in a major stat category based on averages for players in a particular season\n                                 NOTE: <season> must be of the form YYYY\n                                 NOTE: <limit> must be a positive integer which represents the limit\n                                 NOTE: <statType> must be from the following:\n                                 pts, rbs, ast, blk, stl, tov, mins, fgm, fga, 3pm, 3pa, ftm, fta, oreb, dreb, pf\n");
        // 9
        System.out.println(
                "crp <statType> <first> <last> - Compare a player's regular season career averages against their playoff averages\n                                NOTE: <statType> must be from the following:\n                                pts, rbs, ast, blk, stl, tov, mins, fgm, fga, 3pm, 3pa, ftm, fta, oreb, dreb, pf\n");
        // 10
        System.out.println(
                "spt <first> <last> - Major stat averages for a player for all the teams he played for in his career\n");
        // 11
        System.out.println("cc - List all championship winning teams in chronological order\n");
        // 12
        System.out.println(
                "per <team> <season> - Highest player efficiency rating (PER) on a team in a particular season\n                      NOTE: <season> must be of the form YYYY\n");
        // 13
        System.out.println(
                "mr <year> <round> - Given a specific draft round of players, measure their performance through their careeer\n                    NOTE: <year> must be of the form YYYY\n                    NOTE: <round> must be either 1 or 2\n");
        // 14
        System.out.println(
                "ts <statType> - Get the career totals for a specific stat for all players\n                NOTE: <statType> must be from the following:\n                pts, rbs, ast, blk, stl, tov, mins, fgm, fga, 3pm, 3pa, ftm, fta, oreb, dreb, pf\n");
    
        System.out.println("quit - To exit program");
    
        System.out.println("-----------------End of Help-----------------");
    }
}

class MyDatabase {
    private Connection connection;

    public MyDatabase(String initscript) {
        try {
            String url = "jdbc:sqlite::memory:";
            // Class.forName("org.sqlite.JDBC");
            // createTables();
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            if (initscript != null)
                this.loadData(initscript);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } 
        // catch (ClassNotFoundException e) {
        //     System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        //     System.exit(1);
        // } 
        catch (IOException fnf) {
            System.out.println(fnf.getMessage());
            System.exit(2);
        }
    }

    public void loadData(String script) throws IOException, SQLException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(script));
            String line = reader.readLine();
            // assumes each query is its own line
            while (line != null) {
                // System.out.println(line);
                this.connection.createStatement().execute(line);
                line = reader.readLine();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Don't swallow DB errors
        }
    }

    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printError() {
        System.out.println("You have entered unexpected paramters. Type h for help");
    }


    // 1
    public void roster(String teamName, String season) {
        try {
            int seas = Integer.parseInt(season);
            String sql = "Select firstName, lastName, jerseyNumber from Player natural join Play Natural join Team where seasonID = ? AND lower(teamName) like lower(?);";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, seas);
            statement.setString(2, "%" + teamName + "%");

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing Roster for " + teamName + " for the " + season + " season:");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("firstName") + " " + resultSet.getString("lastName") + " "
                        + resultSet.getInt("jerseyNumber"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 2
    // NO INPUT NEEDED
    public void gameAppear(String lim) {
        try {
            int num = Integer.parseInt(lim);
            String sql = "with gamesPlayedPlayer as ( select playerID, firstName, lastName, seasonID, count(gps.gameID) as playerGP from Player natural join GamePlayerStats gps left join RegularGame rg on gps.gameID = rg.gameID left join PlayoffGame pg on gps.gameID = pg.gameID group by playerID, firstName, lastName, seasonID), GamesPlayedTeam as (select teamName, seasonID, count(gts.gameID) as teamGP from GameTeamStats gts left join RegularGame rg on gts.gameID = rg.gameID left join PlayoffGame pg on gts.gameID = pg.gameID group by teamName, seasonID) select seasonID, teamName, firstName, lastName, 100.0*(gpp.playerGP/gpt.teamGP) as appearancePercentage from GamesPlayedPlayer gpp join Play on gpp.seasonID = Play.seasonID join GamesPlayedTeam gpt on Play.teamName = gpt.teamName order by appearancePercentage desc limit ?;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, num);

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the game appearance percentage for each player for each season:");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("firstName") + " " + resultSet.getString("lastName") + " "
                        + resultSet.getInt("seasonID") + " " + resultSet.getString("teamName") + " "
                        + resultSet.getInt("appearancePercentage"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 3
    public void teammates(String first, String last, String team) {
        try {
            String sql = "with seasonTeamsPlayed as( select seasonID, teamName from Play natural join Player where lower(firstName) = lower(?) and lower(lastName) = lower(?) and lower(teamName) = lower(?)), xroster as ( select playerID, firstName, lastName from Player natural join Play where (seasonID, teamName) in seasonsTeamsPlayed) select playerID, firstName, lastName from Player where (playerID, firstName, lastName) in xroster and playerID not in (select playerID from Player natural join Play where lower(firstName) = lower(?) and lower(lastName) = lower(?) and lower(teamName) = lower(?));";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + first + "%");
            statement.setString(2, "%" + last + "%");
            statement.setString(3, "%" + team + "%");
            statement.setString(4, "%" + first + "%");
            statement.setString(5, "%" + last + "%");
            statement.setString(6, "%" + team + "%");

            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing all the teammates of " + first + " " + last + ":");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("firstName") + " " + resultSet.getString("lastName") + " "
                        + resultSet.getInt("playerID"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 4 THIS IS DONE
    public void rankOfficials() {
        try {
            String sql = "select officialID, firstName, lastName, count(gameID) as numGames from officials natural join officiate group by officialID, firstName, lastName order by numGames desc;";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing the officials who have officiated the most games:");
            System.out.printf("%-15s %-20s %-20s %s%n", "officialID", "First Name", "Last Name", "Number of Games"); // Header
            System.out.println("-------------------------------------------------------------------------------");
            while (resultSet.next()) {
                System.out.printf("%-15d %-20s %-20s %s%n", resultSet.getInt("officialID"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getInt("numGames"));
            }

            // while (resultSet.next()) {
            // System.out.println(resultSet.getString("firstName") + " " +
            // resultSet.getString("lastName") + " "
            // + resultSet.getInt("officialID") + " " + resultSet.getInt("numGames"));
            // }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 5 DONE
    public void stadiumInfo() {
        try {
            String sql = "select basedIn, teamName, stadiumName, capacity from teams natural join stadiums order by teamName;";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing all the teams and their stadium info:\n");
            System.out.printf("%-40s %-30s %s%n", "Team", "Stadium", "Capacity"); // Header
            System.out.println("-------------------------------------------------------------------------------");

            while (resultSet.next()) {
                String team = resultSet.getString("basedIn") + " " + resultSet.getString("teamName");
                System.out.printf("%-40s %-30s %d%n", team, resultSet.getString("stadiumName"),
                        resultSet.getInt("capacity"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 6
    public void playerCountry(String season) {
        try {
            int seas = Integer.parseInt(season);
            String sql = "select country, count(playerID) as num from Play natural join Player natural join Location where seasonID = ? group by country order by country;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, seas);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing total number of players from each country:");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("country") + " " + resultSet.getInt("num"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 7 DONE
    public void rankCoaches(String limit) {
        try {
            int lim = Integer.parseInt(limit);

            if (lim <= 0) {
                printError();
                return;
            }
            int counter = 1;
            String sql = "with coachGamesWon as (\n" + //
                    "\tselect c.coachID, c.firstname, c.lastname, count(distinct g.gameID) as gamesWon \n" + //
                    "\tfrom games g join gameTeamInfo gti on g.gameID = gti.gameID join gameTeamStats gts on g.gameID = gts.gameID join manage m on m.season = g.season and m.teamName = gts.teamName join coaches c on m.coachID = c.coachID\n"
                    + //
                    "\twhere winner = m.teamName and g.gameID not in (select gameID from playoffGames)\n" + //
                    "\tgroup by c.coachID, c.firstname, c.lastname),\n" + //
                    "coachGamesPlayed as (\n" + //
                    "\tselect c.coachID, c.firstname, c.lastname, count(distinct g.gameID) as gamesPlayed \n" + //
                    "\tfrom games g join gameTeamStats gts on g.gameID = gts.gameID join manage m on m.season = g.season and gts.teamName = m.teamName  join coaches c on m.coachID = c.coachID\n"
                    + //
                    "\twhere g.gameID not in (select gameID from playoffGames)\n" + //
                    "\tgroup by c.coachID, c.firstname, c.lastname)\n" + //
                    "select cgw.firstname, cgw.lastname, 100.0* cgw.gamesWon/cgp.gamesPlayed as winPercentage\n" + //
                    "from coaches c join coachGamesWon cgw on c.coachID = cgw.coachID join coachGamesPlayed cgp on c.coachID = cgp.coachID\n"
                    + //
                    "order by winPercentage desc limit " + lim + ";";

            Statement statement = connection.createStatement();
            // statement.setInt(1, lim);
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing coaches and their win percentages over the regular season:\n");
            System.out.printf("%5s %-28s %s%n", "", "Coach", "Win Percentage"); // Header
            System.out.println("-------------------------------------------------------------------------------");

            while (resultSet.next()) {
                String coach = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
                System.out.printf("%2d. %-30s %.1f%n",counter, coach, resultSet.getFloat("winPercentage"));
                counter++;
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } catch(NumberFormatException nfe) {
            printError();
        }
    }

    // 8 DONE
    public void leagueAvg(String stat, String season, String limit) {
        try {
            int lim = Integer.parseInt(limit);
            String sql;
            String[] validStats = { "pts", "rbs", "ast", "blk", "stl", "tov", "mins", "fgm", "fga", "3pm", "3pa", "ftm",
                    "fta", "oreb", "dreb", "pf" };
            boolean check = false;
            for (String x : validStats) {
                if (stat.equals(x)) {
                    check = true;
                }
            }
            if (check) {
                sql = "select p.firstname,p.lastName, avg(gps."+stat+") as avgStat from GamePlayerStats gps join players p on gps.playerID = p.playerID join games g on gps.gameID = g.gameID where g.season like (?) group by p.firstname,p.lastName order by avgStat desc limit ?;";
            } else {
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,"%"+seas+"%");
            statement.setInt(2, lim);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing top " + lim + " players based on regular season averages for " + stat
                    + " for the " + seas + " season: ");
            String formatString = "%n| %-15s | %-15s | %-10s |%n";      // Format Structure
            System.out.printf(formatString, "FirstName", "LastName", "AVG"+stat);       // Column Labels
            System.out.printf("+-----------------+-----------------+------------+%n");   //Top Bar

            while (resultSet.next()) {
                System.out.printf(formatString, resultSet.getString("firstname"), resultSet.getString("lastName"), resultSet.getInt("avgStat"));
            }
            System.out.printf("+-----------------+-----------------+------------+%n");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
        }
    }

    // 9 DONE
    public void compareAvg(String stat, String first, String last) {
        try {
            String sql;
            String[] validStats = { "pts", "rbs", "ast", "blk", "stl", "tov", "mins", "fgm", "fga", "3pm", "3pa", "ftm",
                    "fta", "oreb", "dreb", "pf" };
            boolean check = false;
            for (String x : validStats) {
                if (stat.equals(x)) {
                    check = true;
                }
            }
            if (check) {
                sql = "with reg as (SELECT players.playerID, players.firstname, players.lastname, avg(gps."+stat+") as regAvg FROM players natural join gamePlayerStats gps NATURAL JOIN games WHERE games.gameID not in(SELECT gameID from playoffGames) group by players.playerID, players.firstname, players.lastname), playoff as (SELECT players.playerID, players.firstname, players.lastname, avg(gps."+stat+") as pfAvg FROM players natural join gamePlayerStats gps NATURAL JOIN games NATURAL JOIN playoffGames group by players.playerID, players.firstname, players.lastname) SELECT playerID, firstname, lastname, reg.regAvg, playoff.pfAvg FROM reg NATURAL join playoff WHERE lower(firstname) like lower(?) and  lower(lastname) like lower(?);";
            } else {
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + first + "%");
            statement.setString(2, "%" + last + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing a comparison of player: " + first + " " + last + " and his " + stat
                    + " in the regular season vs playoffs: ");
            String formatString = "%n| %-15s | %-15s | %-10s | %-10s |%n";      // Format Structure
            System.out.printf(formatString, "FirstName", "LastName", "Regular AVG"+stat, "Playoff AVG"+stat);       // Column Labels
            System.out.printf("+-----------------+-----------------+------------+------------+%n");   //Top Bar
            while (resultSet.next()) {
                System.out.printf(formatString, resultSet.getString("firstname"), resultSet.getString("lastName"), resultSet.getInt("regAvg"), resultSet.getInt("playoffAvg"));
            }
            System.out.printf("+-----------------+-----------------+------------+------------+%n");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
        }
    }

    // 10 DONE
    public void playerStatsPerTeam(String first, String last) {
        try {
            String sql = "select p.playerID, p.firstName, p.lastName, play.teamName, avg(gps.pts) as points, avg(gps.reb) as rebounds, avg(gps.ast) as assists, avg(gps.blk) as blocks, avg(gps.stl) as steals from Players p natural join Play  join GamePlayerStats gps on play.playerID = gps.playerID natural join Games where lower(p.firstName) = lower(?) AND lower(p.lastName) = lower(?) group by p.playerID, p.firstName, p.lastName, Play.teamName order by Play.teamName;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + first + "%");
            statement.setString(2, "%" + last + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing Major stat averages for player: " + first + " " + last
                    + " for all teams they have played for: ");
            String formatString = "%n| %-15s | %-15s | %-10s | %-10s | %-10s | %-10s | %-10s |%n";      // Format Structure
            System.out.printf(formatString, "FirstName", "LastName", "Points", "Rebounds", "Assists", "Blocks", "Steals");       // Column Labels
            System.out.printf("+-----------------+-----------------+------------+------------+------------+------------+------------+%n");   //Top Bar
            while (resultSet.next()) {
                System.out.printf(formatString, resultSet.getString("firstname"), resultSet.getString("lastName"), resultSet.getInt("points"), resultSet.getInt("rebounds"), resultSet.getInt("assists") + " " + resultSet.getInt("blocks"), resultSet.getInt("steals"));
            }
            System.out.printf("+-----------------+-----------------+------------+------------+------------+------------+------------+%n");
        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
        }
    }

    // 11 DONE
    // NO INPUT NEEDED
    public void champs() {
        try {
            String sql = "select years, champion from Seasons order by years;";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing the Champions in chronological order: ");
            String formatString = "%n| %-10s | %-15s |%n";      // Format Structure
            System.out.printf(formatString, "Year", "Champion");       // Column Labels
            System.out.printf("+------------+-----------------+%n");   //Top Bar
            while (resultSet.next()) {
                System.out.printf(formatString, resultSet.getInt("years"), resultSet.getString("champion"));
            }
            System.out.printf("+------------+-----------------+%n"); 
        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
        }
    }

    // 12

    public void per(String team, String season) {
        try {
            int seas = Integer.parseInt(season);
            String sql = "select p.playerID, p.firstName, p.lastName, AVG((gps.pts+gps.rbs+gps.ast+gps.stl+gps.blk)-(gps.tov+gps.pf+(gps.fga-gps.fgm))) as avgPer from Play join Player p on Play.playerID = p.playerID join GamePlayerStats gps on gps.playerID = Play.playerID left join RegularGame rg on rg.gameID = gps.gameID left join PlayoffGame pg on pg.gameID = gps.gameID where lower(Play.teamName) like lower(?) AND (rg.seasonYear = ? OR pg.seasonYear = ?) group by p.playerID, p.firstName, p.lastName order by avgPer desc limit 10;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + team + "%");
            statement.setInt(2, seas);
            statement.setInt(3, seas);
            ResultSet resultSet = statement.executeQuery();

            System.out.println(
                    "Showing the highest player efficiency rating from " + team + " for the " + seas + " season: ");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("playerID") + " " + resultSet.getString("firstName") + " "
                        + resultSet.getString("lastName") + " " + resultSet.getInt("avgPer"));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 13
    public void draftComm(String year, String round) {
        try {
            int y = Integer.parseInt(year);
            int r = Integer.parseInt(round);
            String sql = "with DraftYrRound as(select di.draftYear, di.round, di.pick, di.playerID from DraftInfo di where di.round = ? AND di.draftYear = ?) Select dyr.draftYear, dyr.draftRound, dyr.draftPick, p.firstName, p.lastName, Play.seasonID, AVG((gps.pts+gps.rbs+gps.ast+gps.stl+gps.blk)-(gps.tov+gps.pf+(gps.fga-gps.fgm))) as avgPer from DraftYrRound dyr join Play on Play.playerID = dyr.playerID join Player p on p.playerID = Play.playerID join GamePlayerStats gps on gps.playerID = p.playerID left join RegularGame rg on rg.gameID = gps.gameID left join PlayoffGame pg on pg.gameID where Play.seasonID >= ? group by fyr.draftYear, dyr.round, p.firstName, p.lastName, dyr.draftPick, Play.seasonID order by p.firstName, p.lastName, Play.seasonID;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, r);
            statement.setInt(2, y);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the player efficiency rating for players drafted in round " + r + " of the " + y
                    + " draft:");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("draftYear") + " " + resultSet.getInt("draftRound") + " "
                        + resultSet.getInt("draftPick") + " " + resultSet.getInt("seasonID") + " "
                        + resultSet.getString("firstName") + " " + resultSet.getString("lastName") + " "
                        + resultSet.getInt("avgPer"));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 14
    public void totalStat(String stat) {
        try {
            String sql;
            String[] validStats = { "pts", "rbs", "ast", "blk", "stl", "tov", "mins", "fgm", "fga", "3pm", "3pa", "ftm",
                    "fta", "oreb", "dreb", "pf" };
            boolean check = false;
            for (String x : validStats) {
                if (stat.equals(x)) {
                    check = true;
                }
            }
            if (check) {
                sql = "SELECT p.firstname, p.lastname, sum(gps." + stat
                + ") AS totalxStatistic FROM Players p JOIN GamePlayerStats gps ON p.playerID = gps.playerID JOIN games g ON gps.gameID = g.gameID GROUP BY p.firstname, p.lastname ORDER BY totalxStatistic DESC";

            } else {
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, stat);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the total " + stat + " of all players all time");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("draftYear") + " " + resultSet.getInt("draftRound") + " "
                        + resultSet.getInt("draftPick") + " " + resultSet.getInt("seasonID") + " "
                        + resultSet.getString("firstName") + " " + resultSet.getString("lastName") + " "
                        + resultSet.getInt("avgPer"));
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }
}

/* Might not need these create table statements since we have the sql files */
// private void createTables(){
// String conf = "create table conference( "+
// " confName text, "+
// " primary key(confName));";
// try {
// connection.createStatement().executeUpdate(conf);

// String div = "create table Division( "+
// "divName text, "+
// "confName text, "+
// "primary key(divName) "+
// "foreign key(confName) references conference);";

// connection.createStatement().executeUpdate(div);

// String coach = "create table Coach( "+
// " coachID integer, "+
// " coachName text, "+
// "primary key(coachID);";

// connection.createStatement().executeUpdate(coach);

// String team = "create table Team( "+
// " teamName text, "+
// " yearFounded integer, "+
// " divName text, "+
// " stadiumName text, "+
// " primary key(teamName), "+
// " foreign key(divName) references Division, "+
// " foreign key(stadiumName) references Stadium);";

// connection.createStatement().executeUpdate(team);

// String stad = "create table Stadium( "+
// " stadiumName text, "+
// " capacity integer, "+
// " locationID integer, "+
// " primary key(stadiumName), "+
// " foreign key(locationID) references Location);";

// connection.createStatement().executeUpdate(stad);

// String loc = "create table Location( "+
// " locationID integer, "+
// " city text, "+
// " country text, "+
// " primary key(locationID));";

// connection.createStatement().executeUpdate(loc);

// String Game = "create table Game( "+
// " gameID integer, "+
// " date text, "+
// " seasonYear integer, "+
// " stadiumName text, "+
// " primary key(gameID), "+
// " foreign key(seasonYear) references Season, "+
// " foreign key(stadiumName) references Stadium);";

// connection.createStatement().executeUpdate(Game);

// String pGame = "create table PlayoffGame( "+
// " gameID integer, "+
// " round text, "+
// " primary key(gameID));";

// connection.createStatement().executeUpdate(pGame);

// String seas = "create table Season( "+
// " seasonYear integer, "+
// " champion text, "+
// " primary key(seasonYear), "+
// " foreign key(champion) references Team(teamName));";

// connection.createStatement().executeUpdate(seas);

// String off = "create table Officials( "+
// " officialID integer, "+
// " name text, "+
// " jerseyNumber integer, "+
// " primary key(officialID));";

// connection.createStatement().executeUpdate(off);

// String man = "create table Manage( "+
// " coachID integer, "+
// " teamName text, "+
// " seasonYear integer, "+
// " primary key(coachID, teamName, seasonYear), "+
// " foreign key(teamName) references Team, "+
// " foreign key(coachID) references Coach, "+
// " foreign key(seasonYear) references Season);";

// connection.createStatement().executeUpdate(man);

// String teach = "create table Teach( "+
// " coachID integer, "+
// " playerID integer, "+
// " primary key(coachID, playerID), "+
// " foreign key(coachID) references Coach, "+
// " foreign key(playerID) references Player);";

// connection.createStatement().executeUpdate(teach);

// // We didnt have a Game relation in our normalization word doc. Only
// PlayoffGame/RegularGame
// // How do we go about this?
// // Also, couldnt homeTeam, awayTeam and winner reference Team?
// String GTI = "create table GameTeamInfo( "+
// " gameID integer, "+
// " homeTeam text, "+
// " awayTeam text, "+
// " winner text, "+
// " primary key(gameID), "+
// " foreign key(gameID) references Game(gameID));";

// connection.createStatement().executeUpdate(GTI);

// String GPS = "create table GamePlayerStats( "+
// " playerID integer, "+
// " gameID integer, "+
// " mins integer, "+
// " pts integer, "+
// " fgm integer, "+
// " fga integer, "+
// " fg% real, "+
// " 3pm integer, "+
// " 3pa integer, "+
// " 3p% real, "+
// " ftm integer, "+
// " fta integer, "+
// " ft% real, "+
// " oreb integer, "+
// " dreb integer, "+
// " reb integer, "+
// " ast integer, "+
// " stl integer, "+
// " blk integer, "+
// " tov integer, "+
// " pf integer, "+
// " +/- integer, "+
// " primary key(gameID, playerID), "+
// " foreign key(gameID) referenes Game(gameID), "+
// " foreign key(playerID) references Player);";

// connection.createStatement().executeUpdate(GPS);

// String gti = "create table GameTeamInfo( "+
// " gameID integer, "+
// " homeTeam text, "+
// " awayTeam text, "+
// " winner text, "+
// " primary key(gameID), "+
// " foreign key(gameID) reference Game(gameID));";

// connection.createStatement().executeUpdate(gti);

// String gts = "create table GameTeamStats( "+
// " teamName text, "+
// " gameID integer, "+
// " mins integer, "+
// " pts integer, "+
// " fgm integer, "+
// " fga integer, "+
// " fg% real, "+
// " 3pm integer, "+
// " 3pa integer, "+
// " 3p% real, "+
// " ftm integer, "+
// " fta integer, "+
// " ft% real, "+
// " oreb integer, "+
// " dreb integer, "+
// " reb integer, "+
// " ast integer, "+
// " stl integer, "+
// " blk integer, "+
// " tov integer, "+
// " pf integer, "+
// " +/- integer, "+
// " primary key(teamName, gameID), "+
// " foreign key(teamName) references Team, "+
// " foreign key(gameID) references Game(gameID));";

// connection.createStatement().executeUpdate(gts);

// String play = "create table Play( "+
// " seasonID integer, "+
// " playerID integer, "+
// " teamName text, "+
// " jersey integer, "+
// " primary key(seasonID, playerID), "+
// " foreign key(seasonID) references Season(seasonYear), "+
// " foreign key(playerID) references Player, "+
// " foreign key(teamName) references Team);";

// connection.createStatement().executeUpdate(play);

// // The foreign key reference to the Game table is an issue here too
// String officiate = "create table Officiate( "+
// " gameID integer, "+
// " officialID integer, "+
// " primary key(gameID, officialID), "+
// " foreign key(gameID) references Game(gameID), "+
// " foreign key(officialID) references Officials);";

// connection.createStatement().executeUpdate(officiate);

// //In the normalization doc, we have a set B that is other attributes in the
// Player table. What are those attributes?
// //
// String player = "create table Player( "+
// " playerID integer, "+
// " firstName text, "+
// " lastName text, "+
// " birthdate text, "+
// " height real, "+
// " weight real, "+
// " position text, "+
// " fromYear integer, "+
// " toYear integer, "+
// " birthLocation integer, "+
// " primary key(playerID), "+
// " foreign key(birthLocation) references Location(locationID));";

// connection.createStatement().executeUpdate(player);

// String draftInfo = "create table DraftInfo( "+
// " draftYear integer, "+
// " draftRound integer, "+
// " draftPick integer, "+
// " playerID integer, "+
// " teamDrafted text, "+
// " primary key(draftYear, draftRound, draftPick), "+
// " foreign key(playerID) referenes Player, "+
// " foreign key(teamDrafted) references Team(teamName));";

// connection.createStatement().executeUpdate(draftInfo);

// } catch (SQLException e) {
// e.printStackTrace(System.out);
// }
// }
// }
