/* TO RUN CODE:
FOR SQLITE:
javac -cp sqlite-jdbc.jar project.java
java -cp .:sqlite-jdbc.jar project 

FOR MSSQL:  
javac -cp mssql-jdbc-11.2.0.jre18.jar project.java
java -cp .:mssql-jdbc-11.2.0.jre18.jar project
*/

//

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
        MyDatabase db = new MyDatabase("testdata.sql"); // COMMENT THIS OUT
        // IF YOU'RE USING SQLITE

        runConsole(db);
        System.out.println("Exiting...");
    }

    // handles connecting to uranium

    // the general console loop
    // the general console loop
    public static void runConsole(MyDatabase db) {

        Scanner console = new Scanner(System.in);
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
                if (parts.length == 2)
                    db.rankOfficials(parts[1]);
                else {
                    System.out.println("This command requires appropriate arguments. Please type h for help!");
                }
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
            } else if (parts[0].equals("deleteAll")) {
                db.deleteData("deleteData.sql");

            } else if (parts[0].equals("repopulate")) {
                try {
                    db.loadData2("testdata.sql");
                } catch (IOException ioe) {
                    System.out.println("Error repopulating database: " + ioe.getMessage());
                } catch (SQLException sqle) {
                    System.out.println("SQL Error repopulating database: " + sqle.getMessage());
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

        // 1
        System.out.println(
                "r <team> <season> - Output roster for a team in a particular season\n                    NOTE: <season> must be of the form YYYY/YYYY\n");
        // 2
        System.out.println(
                "gap <limit> - Get game appearance percentage of each player in each season\n              NOTE: <limit> must be a positive integer which represents how many records you want to see\n");
        // 3
        System.out.println("mates <first> <last> <limit> - Show all the teammates for a particular player\n");
        // 4
        System.out.println("ro <limit> - Rank the officials based on number of games officiated\n");
        // 5
        System.out.println("si - Show all the teams and their arena name and capacity\n");
        // 6
        System.out.println(
                "pc <season> - Show the total number of players from each country in a particular season\n              NOTE: <season> must be of the form YYYY/YYYY");
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
                "per <team> <season> - Highest player efficiency rating (PER) on a team in a particular season\n                      NOTE: <season> must be of the form YYYY/YYYY\n");
        // 13
        System.out.println(
                "mr <year> <round> - Given a specific draft round of players, measure their performance through their careeer\n                    NOTE: <year> must be of the form YYYY/YYYY\n                    NOTE: <round> must be either 1 or 2\n");
        // 14
        System.out.println(
                "ts <statType> - Get the career totals for a specific stat for all players\n                NOTE: <statType> must be from the following:\n                pts, reb, ast, blk, stl, tov, mins, fgm, fga, 3pm, 3pa, ftm, fta, oreb, dreb, pf\n");
        System.out.println("quit - To exit program");

        System.out.println("-----------------End of Help-----------------");
    }

}

class MyDatabase {
    private Connection connection;

    public MyDatabase(String initscript) {
        try {
            // String connectionUrl = "jdbc:sqlite::memory:"; // COMMENT THIS OUT IF YOU'RE
            // USING MSSQL
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

            // COMMENT THIS OUT IF YOU'RE USING SQLITE
            String connectionUrl = "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;"
                    + "database=cs3380;"
                    + "user=" + username + ";"
                    + "password=" + password + ";"
                    + "encrypt=false;"
                    + "trustServerCertificate=false;"
                    + "loginTimeout=30;";

            connection = DriverManager.getConnection(connectionUrl);

            System.out.println("Connection to SQLite has been established.");

            // if (initscript != null)
            //     this.loadData(initscript);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        // } catch (IOException fnf) {
        //     System.out.println(fnf.getMessage());
        //     System.exit(2);
        // }
    }}

    public static boolean sanitize(String input) {
        String[] validStats = { "pts", "reb", "ast", "blk", "stl", "tov", "min", "fgm", "fga", "3pm", "3pa", "ftm",
                "fta",
                "oreb", "dreb", "pf" };

        boolean check = false;
        for (String x : validStats) {
            if (x.toLowerCase().equals(input.toLowerCase())) {
                check = true;
            }
        }

        return check;
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

            reader.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Don't swallow DB errors
        }
    }

    // efficient load data method -> testing for this
    public void loadData2(String script) throws IOException, SQLException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(script));
            String line = reader.readLine();
            String query = "";

            while (line != null) {
                if (line.charAt(line.length() - 1) == ';') { // checks if we're at the end of query by looking for. If we are, execute the query
                    this.connection.createStatement().execute(line);
                } else {
                    query += line + " ";
                }
                line = reader.readLine();

            }
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace(); // commented since loading database without deleting first wil throw exceptions
        }
    }

    public void deleteData(String script) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(script));
            String line = reader.readLine();
            // assumes each query is its own line
            while (line != null) {
                // System.out.println(line);
                this.connection.createStatement().execute(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException fnf) {
            System.out.println(fnf.getMessage());
        } catch (IOException io) {
            System.out.println(io.getMessage());
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

    // 1 DONE
    public void roster(String teamName, String season) {
        try {
            String sql = "select firstname, lastname,  jersey\n" + //
                    "from players join play on players.playerID = play.playerID join teams on play.teamName = teams.teamName\n"
                    + //
                    "where season = ? and teams.teamName like ? order by lastname asc;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, season);
            statement.setString(2, "%" + teamName + "%");

            ResultSet resultSet = statement.executeQuery();

            boolean initial = resultSet.next();

            if (initial) {
                System.out.println("Showing Roster for " + teamName + " for the " + season + " season:\n");

                String formatString = "| %-30s | %-40s | %-20s |%n"; // Format Structure
                System.out.printf(formatString, "First Name", "Last Name", "Jersey Number"); // Column Labels
                System.out.printf(
                        "+--------------------------------------------------------------------------------------------------+%n"); // Top
                // Bar

                do {
                    System.out.printf("| %-30s | %-40s | %-20s |%n%n",
                            resultSet.getString("firstName"), resultSet.getString("lastName"),
                            resultSet.getInt("jersey"));
                } while (resultSet.next());
                System.out.printf(
                        "+--------------------------------------------------------------------------------------------------+%n"); // Lower
                // Bar
            } else
                System.out.println("No roster found for " + teamName + " for the " + season + " season.");
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 2 DONE
    public void gameAppear(String lim) {
        try {
            int num = Integer.parseInt(lim);
            if (num <= 0) {
                printError();
                return;
            }
            String sql = "with gamesPlayedPlayer as (\n" + //
                    "\tselect p.playerID, p.firstname, p.lastname, g.season, count(distinct gps.gameID) as playerGP\n" + //
                    "\tfrom players p join gamePlayerStats gps on p.playerID = gps.playerID join games g on gps.gameID = g.gameID join gameTeamStats gts on gts.gameID = gps.gameID\n"
                    + //
                    "\tgroup by p.playerID, p.firstname, p.lastname, g.season, gts.teamName ),\n" + //
                    "gamesPlayedTeam as (\n" + //
                    "\tselect gts.teamName, g.season, count(gts.gameID) as teamGP\n" + //
                    "\tfrom gameTeamStats gts join games g on gts.gameID = g.gameID\n" + //
                    "\tgroup by gts.teamName, g.season)\n" + //
                    "select top " + num
                    + " gpp.season, gpt.teamName, gpp.firstname, gpp.lastname, ((1.0*gpp.playerGP)/gpt.teamGP)*100.0 as appearancePercentage\n"
                    + //
                    "from gamesPlayedPlayer gpp join play on gpp.season = play.season and gpp.playerID = play.playerID join gamesPlayedTeam gpt on play.teamName = gpt.teamName and gpt.season = play.season\n"
                    + //
                    "order by appearancePercentage desc, gpp.season desc, gpp.lastname asc;";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing the game appearance percentage for each player for each season:\n");
            String formatString = "| %-10s | %-15s | %-30s | %-40s | %-30s |%n"; // Format Structure
            System.out.printf(formatString, "Season", "Team", "First Name", "Last Name",
                    "Game Appearance Percentage (%)"); // Column Labels
            System.out.printf(
                    "+-------------------------------------------------------------------------------------------------------------------------------------------+%n"); // Top
            // Bar
            while (resultSet.next()) {
                System.out.printf("| %-10s | %-15s | %-30s | %-40s | %-30.1f |%n%n", resultSet.getString("season"),
                        resultSet.getString("teamName"),
                        resultSet.getString("firstName"), resultSet.getString("lastName"),
                        resultSet.getFloat("appearancePercentage"));
            }
            System.out.printf(
                    "+-------------------------------------------------------------------------------------------------------------------------------------------+%n"); // Lower
            // Bar

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } catch (NumberFormatException nfe) {
            printError();
        }
    }

    // 3 DONE
    public void teammates(String first, String last, String lim) {
        try {
            int num = Integer.parseInt(lim);
            if (num <= 0) {
                printError();
                return;
            }

            String sql = "WITH seasonsTeamsPlayed AS (\n" + //
                    "    SELECT DISTINCT play.season, play.teamName\n" + //
                    "    FROM play JOIN players ON play.playerID = players.playerID\n" + //
                    "    WHERE players.firstname LIKE ? and players.lastname LIKE ?),\n" + //
                    "xroster AS (\n" + //
                    "    SELECT DISTINCT st.season, st.teamName, p.playerID\n" + //
                    "    FROM players p JOIN play ON p.playerID = play.playerID JOIN seasonsTeamsPlayed st ON play.season = st.season AND play.teamName = st.teamName)\n"
                    + 
                    "SELECT top (?) xr.season, xr.teamName, p.playerID, p.firstname, p.lastname\n" + //
                    "FROM players p\n" + //
                    "JOIN xroster xr ON p.playerID = xr.playerID\n" + //
                    "WHERE p.playerID NOT IN (\n" + //
                    "    SELECT playerID\n" + //
                    "    FROM players\n" + //
                    "    WHERE firstname LIKE ? and lastname LIKE ?\n" + //
                    ")\n" + //
                    "order by xr.season, p.lastname asc;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + first + "%");
            statement.setString(2, "%" + last + "%");
            statement.setInt(3,num);
            statement.setString(4, "%" + first + "%");
            statement.setString(5, "%" + last + "%");

            ResultSet resultSet = statement.executeQuery();

            boolean initial = resultSet.next();

            if (initial) {
                System.out.println("Showing teammates for player with name similar to " + first + " " + last + "\n");

                String formatString = "| %-10s | %-20s | %-30s | %-40s |%n"; // Format Structure
                System.out.printf(formatString, "Season", "Team", "First Name", "Last Name"); // Column Labels
                System.out.printf(
                        "+---------------------------------------------------------------------------------------------------------------+%n"); // Top
                // Bar

                do {
                    System.out.printf("| %-10s | %-20s | %-30s | %-40s |%n%n",
                            resultSet.getString("season"), resultSet.getString("teamName"),
                            resultSet.getString("firstname"), resultSet.getString("lastname"));
                } while (resultSet.next());
                System.out.printf(
                        "+---------------------------------------------------------------------------------------------------------------+%n"); // Lower
                // Bar
            } else
                System.out.println("Player not found.\n");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } catch (NumberFormatException nfe) {
            printError();
        }
    }

    // 4 THIS IS DONE
    // NO USER INPUT NEEDED
    public void rankOfficials(String lim) {
        try {
            int num = Integer.parseInt(lim);
            if (num <= 0) {
                printError();
                return;
            }
            String sql = "select top (?) o.officialID, o.firstName, o.lastName, count(officiate.gameID) as numGames from officials o join officiate on o.officialID = officiate.officialID group by o.officialID, o.firstName, o.lastName order by numGames desc, o.lastname asc;";

            // Statement statement = connection.createStatement();
            PreparedStatement statement = connection.prepareStatement(sql);
            // ResultSet resultSet = statement.executeQuery(sql);
            statement.setInt(1, num);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the officials who have officiated the most games:\n");
            System.out.printf("| %-10s | %-20s | %-30s | %-15s |%n", "officialID", "First Name", "Last Name",
                    "Number of Games"); // Header
            System.out.println(
                    "+--------------------------------------------------------------------------------------+");
            while (resultSet.next()) {
                System.out.printf("| %-10d | %-20s | %-30s | %-15d |%n%n", resultSet.getInt("officialID"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"), resultSet.getInt("numGames"));
            }
            System.out.println(
                    "+--------------------------------------------------------------------------------------+");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } catch (NumberFormatException nfe) {
            printError();
        }
    }

    // 5
    // NO USER INPUT NEEDED
    // DONE
    public void stadiumInfo() {
        try {
            String sql = "select teams.basedIn,teams.teamName, stadiums.stadiumName, stadiums.capacity from teams join stadiums on teams.stadiumName = stadiums.stadiumName order by teams.teamName;";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing all the teams and their stadium info:\n");
            System.out.printf("| %-40s | %-30s | %-10s |%n", "Team", "Stadium", "Capacity"); // Header
            System.out.println(
                    "+----------------------------------------------------------------------------------------+");

            while (resultSet.next()) {
                String team = resultSet.getString("basedIn") + " " + resultSet.getString("teamName");
                System.out.printf("| %-40s | %-30s | %-10d |%n%n", team, resultSet.getString("stadiumName"),
                        resultSet.getInt("capacity"));
            }
            System.out.println(
                    "+----------------------------------------------------------------------------------------+");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 6 DONE
    public void playerCountry(String season) {
        try {
            String sql = "select l.country, count(distinct p.playerID) as numPlayers\n" + //
                    "from play join players p on play.playerID = p.playerID join locations l on p.locationID = l.locationID\n"
                    + //
                    "where play.season = ?\n" + //
                    "group by l.country order by numPlayers desc;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, season);
            ResultSet resultSet = statement.executeQuery();
            boolean initial = resultSet.next();

            if (initial) {
                System.out.println("Showing number of players from each country for the " + season + " season:\n");

                String formatString = "| %-30s | %-20s |%n%n"; // Format Structure
                System.out.printf(formatString, "Country", "Number of Players"); // Column Labels
                System.out.printf(
                        "+-------------------------------------------------------+%n"); // Top
                // Bar

                do {
                    System.out.printf("| %-30s | %-20s |%n",
                            resultSet.getString("country"), resultSet.getInt("numPlayers"));
                } while (resultSet.next());
                System.out.printf(
                        "+-------------------------------------------------------+%n"); // Lower
                // Bar
            } else
                System.out.println("Invalid season entered\n");

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
                    "select top (?) cgw.firstname, cgw.lastname, 100.0* cgw.gamesWon/cgp.gamesPlayed as winPercentage\n" + //
                    "from coaches c join coachGamesWon cgw on c.coachID = cgw.coachID join coachGamesPlayed cgp on c.coachID = cgp.coachID\n"
                    + //
                    "order by winPercentage desc;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,lim);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing coaches and their win percentages over the regular season:\n");
            System.out.printf("| %5s %-28s | %-15s |%n", "", "Coach", "Win Percentage"); // Header
            System.out.println("+------------------------------------------------------+");

            while (resultSet.next()) {
                String coach = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
                System.out.printf("| %2d. %-30s | %-15.1f |%n%n", counter, coach, resultSet.getFloat("winPercentage"));
                counter++;
            }
            System.out.println("+------------------------------------------------------+");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } catch (NumberFormatException nfe) {
            printError();
        }
    }

    // 8 DONE
    public void leagueAvg(String stat, String season, String limit) {
        try {
            int lim = Integer.parseInt(limit);
            String sql;

            if (sanitize(stat)) {
                sql = "select top ? p.firstname,p.lastName, avg(gps." + stat + ") as avgStat "
                        + "from GamePlayerStats gps join players p on gps.playerID = p.playerID "
                        + "join games g on gps.gameID = g.gameID "
                        + "where g.season like (?) group by p.firstname,p.lastName order by avgStat desc;";
            } else {
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(2, "%" + season + "%");
            statement.setInt(1, lim);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing top " + lim + " players based on regular season averages for " + stat
                    + " for the " + season + " season: ");

            String formatString = "%n| %-30s | %-30s | %-15s |%n"; // Format Structure
            System.out.printf("|----------------------------------|----------------------------------|-----------------|%n");
            System.out.printf(formatString, "FirstName", "LastName", "AVG " + stat); // Column Labels
            System.out.printf("|----------------------------------|----------------------------------|-----------------|%n"); // Top Bar
            String avgs = "%n| %-15s | %-15s | %-15.1f |%n";

            while (resultSet.next()) {
                System.out.printf(avgs, resultSet.getString("firstname"), resultSet.getString("lastName"),
                        resultSet.getDouble("avgStat"));
                System.out.printf("|----------------------------------|----------------------------------|-----------------|%n");
            }

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

            if (sanitize(stat)) {
                sql = "with reg as "
                        + "(SELECT players.playerID, players.firstname, players.lastname, avg(gps." + stat
                        + ") as regAvg "
                        + "FROM players join gamePlayerStats gps on players.playerID = gps.playerID "
                        + "JOIN games on gps.gameID = games.gameID WHERE games.gameID not in "
                        + "(SELECT gameID from playoffGames) "
                        + "group by players.playerID, players.firstname, players.lastname), "
                        + "playoff as "
                        + "(SELECT players.playerID, players.firstname, players.lastname, avg(gps." + stat
                        + ") as pfAvg "
                        + "FROM players join gamePlayerStats gps on players.playerID = gps.playerID "
                        + "JOIN games on gps.gameID = games.gameID "
                        + "JOIN playoffGames on games.gameID = playoffGames.gameID "
                        + "group by players.playerID, players.firstname, players.lastname) "
                        + "SELECT playerID, firstname, lastname, reg.regAvg, playoff.pfAvg "
                        + "FROM reg join playoff on reg.playerID = pfAvg.playerID AND reg.firstname = pfAvg.firstname AND reg.lastname = pfAvg.lastname "
                        + "WHERE lower(firstname) like lower(?) and lower(lastname) like lower(?);";
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
            String formatString = "%n| %-30s | %-30s | %-15s | %-15s |%n"; // Format Structure
            System.out.printf("|----------------------------------|----------------------------------|-----------------|-----------------|%n");
            System.out.printf(formatString, "FirstName", "LastName", "REG AVG" + stat, "PF AVG" + stat); // Column
                                                                                                         // Labels
            System.out.printf("|----------------------------------|----------------------------------|-----------------|-----------------|%n"); // Top Bar
            String avgs = "%n| %-15s | %-15s | %-15.1f | %-15.1f |%n";
            while (resultSet.next()) {
                System.out.printf(avgs, resultSet.getString("firstname"), resultSet.getString("lastName"),
                        resultSet.getDouble("regAvg"), resultSet.getDouble("pfAvg"));
                System.out.printf("|-----------------|-----------------|-----------------|-----------------|%n");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
            e.printStackTrace(System.out);
        }
    }

    // 10 DONE
    public void playerStatsPerTeam(String first, String last) {
        try {
            String sql = "select p.playerID, p.firstName, p.lastName, play.teamName, "
                    + "avg(gps.pts) as points, avg(gps.reb) as rebounds, avg(gps.ast) as assists, avg(gps.blk) as blocks, avg(gps.stl) as steals "
                    + "from Players p join Play on p.playerID = play.playerID "
                    + "join GamePlayerStats gps on play.playerID = gps.playerID "
                    + "join Games on gps.gameID = Games.gameID "
                    + "where lower(p.firstName) like lower(?) AND lower(p.lastName) like lower(?) "
                    + "group by p.playerID, p.firstName, p.lastName, Play.teamName "
                    + "order by Play.teamName;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + first + "%");
            statement.setString(2, "%" + last + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing Major stat averages for player: " + first + " " + last
                    + " for all teams they have played for: ");

            String formatString = "%n| %-20s | %-20s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |%n"; // Format
                                                                                                           // Structure

            System.out.printf(
                    "|------------------------|------------------------|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|%n");
            System.out.printf(formatString, "FirstName", "LastName", "Team", "Points", "Rebounds", "Assists", "Blocks",
                    "Steals"); // Column Labels
            System.out.printf(
                    "|------------------------|------------------------|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|%n"); // Top
                                                                                                                                                                            // Bar
            String avgs = "%n| %-15s | %-15s | %-15s | %-15.1f | %-15.1f | %-15.1f | %-15.1f | %-15.1f |%n";

            while (resultSet.next()) {
                System.out.printf(avgs, resultSet.getString("firstname"), resultSet.getString("lastName"),
                        resultSet.getString("teamName"), resultSet.getDouble("points"),
                        resultSet.getDouble("rebounds"), resultSet.getDouble("assists"),
                        resultSet.getDouble("blocks"), resultSet.getDouble("steals"));
                System.out.printf(
                        "|------------------------|------------------------|-----------------|-----------------|-----------------|-----------------|-----------------|-----------------|%n");
            }

        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
            e.printStackTrace(System.out);
        }
    }

    // 11 DONE
    // NO INPUT NEEDED
    public void champs() {
        try {
            String sql = "SELECT years, champion as teamName FROM seasons ORDER BY years";
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Showing the Champions in chronological order: ");

            String formatString = "%n| %-15s | %-15s |%n"; // Format Structure
            System.out.printf("|-----------------|-----------------|%n");
            System.out.printf(formatString, "Season Year", "Champion"); // Column Labels
            System.out.printf("|-----------------|-----------------|%n"); // Top Bar

            while (resultSet.next()) {
                System.out.printf(formatString, resultSet.getString("years"), resultSet.getString("teamName"));
                System.out.printf("|-----------------|-----------------|%n");
            }

        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
        }
    }

    // 12 DONE

    public void per(String team, String season) {
        try {
            String sql = "SELECT  p.firstname, p.lastname,"
                    + " SUM(gps.fgm * 85.910 + gps.stl * 53.897 + gps.'3pm' * 51.757 + gps.ftm * 46.845 + gps.blk * 39.190 + gps.oreb * 39.190 + gps.ast * 34.677 + gps.dreb * 14.707"
                    + " - gps.pf * 17.174 - (gps.fta - gps.ftm) * 20.091 - (gps.fga - gps.fgm) *39.190 - gps.tov * 53.897)/SUM(gps.min) AS avgPER "
                    + " FROM Players p "
                    + " LEFT JOIN Play szn ON p.playerID = szn.playerID"
                    + " LEFT JOIN gamePlayerStats gps ON szn.playerID = gps.playerID"
                    + " LEFT JOIN games rg ON gps.gameID = rg.gameID AND rg.season = szn.season"
                    + " WHERE lower(szn.teamName) LIKE lower(?)"
                    + " AND lower(szn.season) LIKE lower(?)"
                    + " GROUP BY p.firstname, p.lastname"
                    + " ORDER BY avgPER DESC "
                    + " LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + team + "%");
            statement.setString(2, "%" + season + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println(
                    "Showing the highest player efficiency rating from " + team + " for the " + season + " season: /n");

            String formatString = "%n| %-15s | %-15s | %-15s |%n"; // Format Structure
            String printFormat = "%n| %-15s | %-15s | %-15.1f |%n";
            System.out.printf(formatString, "First Name", "Last Name", "PER"); // Column Labels
            System.out.printf("+-----------------+-----------------+-----------------+%n"); // Top Bar

            while (resultSet.next()) {
                System.out.printf(printFormat, resultSet.getString("firstname"), resultSet.getString("lastname"),
                        resultSet.getDouble("avgPer"));
            }
            System.out.printf("+-----------------+-----------------+-----------------+%n"); // Lower Bar
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 13 DONE REMOVE LIMIT!!!!!!!!!!!!!!
    public void draftComm(String year, String round) {
        try {
            int y = Integer.parseInt(year);
            int r = Integer.parseInt(round);
            String sql = "SELECT dyr.draftYear, dyr.round, dyr.pick, p.firstname, p.lastname, szn.season, "
                    + " SUM(gps.fgm * 85.910 + gps.stl * 53.897 + gps.'3pm' * 51.757 + gps.ftm * 46.845"
                    + " + gps.blk * 39.190 + gps.oreb * 39.190 + gps.ast * 34.677 + gps.dreb * 14.707"
                    + " - gps.pf * 17.174 - (gps.fta - gps.ftm) * 20.091 - (gps.fga - gps.fgm) *39.190 - gps.tov * 53.897)/SUM(gps.min) AS avgPER "
                    + " FROM DraftInfo dyr "
                    + " JOIN Play szn ON szn.playerID = dyr.playerID "
                    + " JOIN Players p ON p.playerID = szn.playerID "
                    + " JOIN GamePlayerStats gps ON gps.playerID = p.playerID "
                    + " JOIN Games rg ON rg.gameID = gps.gameID AND rg.season = szn.season "
                    + " WHERE szn.season >= dyr.draftYear AND dyr.draftYear = ? AND dyr.round = ?"
                    + " GROUP BY dyr.draftYear, dyr.round, dyr.pick, p.firstname, p.lastname, szn.season"
                    + " ORDER BY dyr.pick, szn.season"
                    + " LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, y);
            statement.setInt(2, r);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the player efficiency rating for players drafted in round " + r + " of the " + y
                    + " draft:");
            String formatString = "| %-15s | %-15s | %-15s | %-15s |%n"; // Format Structure
            String formatPrint = "%n| %-15s | %-15s | %-15s | %-15.1f |%n"; // Print Structure w/ avgPer double value
                                                                            // rounded to .1
            System.out.printf(formatString, "First Name", "Last Name", "Season Year", "PER");
            System.out.printf("+-----------------+-----------------+-----------------+-----------------+%n"); // Top Bar

            while (resultSet.next()) {
                System.out.printf(formatPrint, resultSet.getString("firstname"), resultSet.getString("lastname"),
                        resultSet.getString("season"), resultSet.getDouble("avgPER")); // Column Labels
            }
            System.out.printf("+-----------------+-----------------+-----------------+-----------------+%n"); // Lower
                                                                                                              // Bar
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    // 14 DONE
    public void totalStat(String stat) {
        try {
            String sql;

            if (sanitize(stat)) {
                sql = "SELECT p.firstname, p.lastname, sum(gps.'" + stat
                        + "') AS totalxStatistic FROM Players p JOIN GamePlayerStats gps ON p.playerID = gps.playerID JOIN games g ON gps.gameID = g.gameID GROUP BY p.firstname, p.lastname ORDER BY totalxStatistic DESC LIMIT 10;";

            } else {
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            // statement.setString(1, stat); //Not needed, not setting anything
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the total " + stat + " of all players all time");

            String formatString = "%n| %-15s | %-15s | %-15s |%n";
            System.out.printf(formatString, "First Name", "Last Name", "Total " + stat);
            System.out.printf("+-----------------+-----------------+-----------------+%n");
            while (resultSet.next()) {
                // System.out.println(resultSet.getInt("draftYear") + " " +
                // resultSet.getInt("draftRound") + " "
                // + resultSet.getInt("draftPick") + " " + resultSet.getInt("seasonID") + " "
                // + resultSet.getString("firstName") + " " + resultSet.getString("lastName") +
                // " "
                // + resultSet.getInt("avgPer"));
                System.out.printf(formatString, resultSet.getString("firstname"), resultSet.getString("lastname"),
                        resultSet.getInt("totalxStatistic"));

            }
            System.out.printf("+-----------------+-----------------+-----------------+%n");
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
