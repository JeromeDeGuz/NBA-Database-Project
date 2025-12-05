/* TO RUN CODE:
FOR MSSQL:  
javac -cp mssql-jdbc-11.2.0.jre18.jar project.java
java -cp .:mssql-jdbc-11.2.0.jre18.jar project
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
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
        MyDatabase db = new MyDatabase("NBAdatabaseServer.sql");
        db.introduction();
        System.out.println("\n***** Begin with entering 'h' to display the list of commands *****");
        runConsole(db);
        System.out.println("Exiting...");
    }

    public static void runConsole(MyDatabase db) {

        Scanner console = new Scanner(System.in);
        System.out.print("db > ");
        String line = console.nextLine().trim();
        String[] parts;
        String command = "";

        while (line != null && !line.equals("quit")) {
            // checking for just white space as input. If there is, move onto the next iteration of while loop. 
            // I was getting array out of bounds exception without this check when entering a space and then enter
            if(line.isBlank() || line.isEmpty()) { 
                db.printError();
                System.out.print("db > ");
                line = console.nextLine();;
                continue;
            }
            parts = line.split("\\s+");
            command = parts[0].toLowerCase();

            switch (command) {
                case "h":
                    printHelp();
                    break;
            
                case "r":
                    if (parts.length == 3) {
                        db.roster(parts[1], parts[2]);
                    } else {db.printError();}
                    break;
                case "gap":
                    if (parts.length == 3) {
                        db.gameAppear(parts[1],parts[2]);
                    } else {db.printError();}
                    break;
                case "mates":
                    if (parts.length == 4) {
                        db.teammates(parts[1], parts[2], parts[3]);
                    } else {db.printError();}
                    break;
                case "ro":
                    if (parts.length == 2) {
                        db.rankOfficials(parts[1]);
                    } else {db.printError();}
                    break;
                case "si":
                    db.stadiumInfo();
                    break;
                case "pc":
                    if (parts.length == 2) {
                        db.playerCountry(parts[1]);
                    } else {db.printError();}
                    break;
                case "rc":
                    if (parts.length == 2) {
                        db.rankCoaches(parts[1]);
                    } else {db.printError();}
                    break;
                case "ll":
                    if (parts.length == 4) {
                        db.leagueAvg(parts[1], parts[2], parts[3]);
                    } else {db.printError();}
                    break;
                case "crp":
                    if (parts.length == 4) {
                        db.compareAvg(parts[1], parts[2], parts[3]);
                    } else {db.printError();}
                    break;
                case "spt":
                    if (parts.length == 3) {
                        db.playerStatsPerTeam(parts[1], parts[2]);
                    } else {db.printError();}
                    break;
                case "cc":
                    db.champs();
                    break;
                case "per":
                    if (parts.length == 3) {
                        db.per(parts[1], parts[2]);
                    } else {db.printError();}
                    break;
                case "mr":
                    if (parts.length == 3) {
                        db.draftComm(parts[1], parts[2]);
                    } else {db.printError();}
                    break;
                case "ts":
                    if (parts.length == 2) {
                        db.totalStat(parts[1]);
                    } else {db.printError();}
                    break;
                case "repopulate":
                    try {
                        System.out.println("Repopulating database with initial data...");
                        db.loadData("NBAdatabaseServer.sql");;
                    } catch (IOException ioe) {
                        System.out.println("Error repopulating database: " + ioe.getMessage());
                    } catch (SQLException sqle) {
                        System.out.println("SQL Error repopulating database: " + sqle.getMessage());
                    }
                    break;
                case "deleteall":
                    System.out.println("Deleting all data from the database...");
                    db.deleteData("deleteData.sql");
                    break;
                default:
                    System.out.println("Invalid command inputs. Please type h for help!");
            }

            // if (parts[0].equals("h")) {
            // printHelp();
            // } else if (parts[0].equals("r")) {
            // if (parts.length == 3) {
            // db.roster(parts[1], parts[2]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("gap")) {
            // if (parts.length == 2) {
            // db.gameAppear(parts[1]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("mates")) {
            // if (parts.length == 4) {
            // db.teammates(parts[1], parts[2], parts[3]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("ro")) {
            // if (parts.length == 2)
            // db.rankOfficials(parts[1]);
            // else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("si")) {
            // db.stadiumInfo();
            // } else if (parts[0].equals("pc")) {
            // if (parts.length == 2) {
            // db.playerCountry(parts[1]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("rc")) {
            // if (parts.length == 2) {
            // db.rankCoaches(parts[1]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("ll")) {
            // if (parts.length == 4) {
            // db.leagueAvg(parts[1], parts[2], parts[3]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("crp")) {
            // if (parts.length == 4) {
            // db.compareAvg(parts[1], parts[2], parts[3]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("spt")) {
            // if (parts.length == 3) {
            // db.playerStatsPerTeam(parts[1], parts[2]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("cc")) {
            // db.champs();
            // } else if (parts[0].equals("per")) {
            // if (parts.length == 3) {
            // db.per(parts[1], parts[2]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("mr")) {
            // if (parts.length == 3) {
            // db.draftComm(parts[1], parts[2]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("ts")) {
            // if (parts.length == 2) {
            // db.totalStat(parts[1]);
            // } else {
            // System.out.println("This command requires appropriate arguments. Please type
            // h for help!");
            // }
            // } else if (parts[0].equals("deleteAll")) {
            // db.deleteData("deleteData.sql");

            // } else if (parts[0].equals("repopulate")) {
            // try {
            // db.loadData("NBAdatabaseServer.sql");
            // } catch (IOException ioe) {
            // System.out.println("Error repopulating database: " + ioe.getMessage());
            // } catch (SQLException sqle) {
            // System.out.println("SQL Error repopulating database: " + sqle.getMessage());
            // }
            // } else {
            // System.out.println("The entered command does not exist, please type h for
            // help!");
            // }

            System.out.print("db > ");
            line = console.nextLine().trim();
        }

        console.close();
    }

    private static void printParameterError(){
        System.out.println("You have entered unexpected paramters. Type h for help");
        return;
    }

    private static void printHelp() {

        String help = """
                --------------------------------------------------------Start of Help-------------------------------------------------------
                | NBA Database for the 2016/2017 - 2022/2023 Seasons                                                                        |
                | Commands:                                                                                                                 |
                ----------------------------------------------------------------------------------------------------------------------------
                | r [team] [season]                - Output roster for a team (Season Format: YYYY/YYYY)                                     |
                ----------------------------------------------------------------------------------------------------------------------------
                | gap [first] [last]               - Get game appearance percentage for a particular player for every season                 |
                ----------------------------------------------------------------------------------------------------------------------------
                | mates [first] [last] [limit]     - Show all the teammates for a particular player                                          |
                ----------------------------------------------------------------------------------------------------------------------------
                | ro [limit]                       - Rank the officials based on number of games officiated                                  |
                ----------------------------------------------------------------------------------------------------------------------------
                | si                               - how all the teams and their arena name and capacity                                     |
                ----------------------------------------------------------------------------------------------------------------------------
                | pc [season]                      - Number of players by country (Season Format: YYYY/YYYY)                                 |
                ----------------------------------------------------------------------------------------------------------------------------
                | rc [limit]                       - Top coaches by win percentage in Regular Season (limit = # of records)                  |
                ----------------------------------------------------------------------------------------------------------------------------
                | ll [statType] [season] [limit]   - League Leaders per season (by avg) (Season Format: YYYY/YYYY) (limit = # of records)    |
                ----------------------------------------------------------------------------------------------------------------------------
                | crp [statType] [first] [last].   - Compare Regular Season vs Playoff career averages                                       |
                | *                                 statType] must be one of the following:                                                 |
                | *                                 pts, rbs, ast, blk,                                                                     |
                | *                                 stl, tov, mins, fgm,                                                                    |
                | *                                 fga, 3pm, 3pa, ftm,                                                                     |
                | *                                 fta, oreb, dreb, pf                                                                     |
                ----------------------------------------------------------------------------------------------------------------------------
                | spt [first] [last]               - Major stat averages for a player per team                                               |
                ----------------------------------------------------------------------------------------------------------------------------
                | cc                               - List all championship winning teams in chronological order                              |
                ----------------------------------------------------------------------------------------------------------------------------
                | per [team] [season]              - Highest player efficiency rating (PER) on a team (Season Format: YYYY/YYYY)             |
                ----------------------------------------------------------------------------------------------------------------------------
                | mr [year] [round]                - Given a specific draft round of players, measure their performance through their career |
                | *                                 (Year Format: YYYY)                                                                     |
                ----------------------------------------------------------------------------------------------------------------------------
                | ts [statType]                    - Get the career totals for a specific stat for all players                               |
                | *                                 [statType] must be one of the following:                                                |
                | *                                 pts, rbs, ast, blk,                                                                     |
                | *                                 stl, tov, mins, fgm,                                                                    |
                | *                                 fga, 3pm, 3pa, ftm,                                                                     |
                | *                                 fta, oreb, dreb, pf                                                                     |
                ----------------------------------------------------------------------------------------------------------------------------
                | deleteAll                        - Deletes all data from the database                                                      |
                ----------------------------------------------------------------------------------------------------------------------------
                | repopulate                       - Repopulates the database with initial data                                              |
                ----------------------------------------------------------------------------------------------------------------------------
                | quit                             - To exit program                                                                         |
                ---------------------------------------------------------End of Help--------------------------------------------------------
                """;
        System.out.println(help);

    }

}

class MyDatabase {
    private Connection connection;

    public MyDatabase(String initscript) {
        try {
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

            connection = DriverManager.getConnection(connectionUrl);

            System.out.println("Connection to SQL Server has been established.");

            if (initscript != null) {
                this.loadData(initscript);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException ioe) {
            System.out.println("Error loading initial script: " + ioe.getMessage());
            System.exit(1);
        }
    }

    public void introduction() {
        System.out.println("\n*********************************************** Welcome to the NBA database! ***********************************************");
        System.out.println(
                "This database contains information relating to the National Basketball Association (NBA)");
        System.out.println(
                "Welcome to the NBA database! This database contains information relating to the National Basketball Association (NBA) from");
        System.out.println(
                "the 2016/2017 - 2022/2023 seasons. It has information on players, teams, coaches, officials, games, statistics, and more!");
        System.out.println(
                "Below we have a list of commands you can use that will provide interesting data from the database. This data can be used");
        System.out.println(
                "for various types of analysis. This includes things like highest player averages for specific statistics, seeing the spread");
        System.out.println("of players from different countries, ranking coaches and officials, and more!");
        System.out.println(
                "****************************************************************************************************************************\n");
    }

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

            Statement statement = connection.createStatement();

            String batch = "";
            String line = reader.readLine();
            int count = 0;
            // assumes each query is its own line
            while (line != null) {

                batch += line + "\n";

                if (line.trim().endsWith(";")) {
                    statement.addBatch(batch);
                    batch = "";
                    count++;

                    if (count % 1000 == 0) {
                        statement.executeBatch();
                    }
                }
                line = reader.readLine();
            }
            if (!batch.isEmpty()) {
                statement.addBatch(batch);
            }
            statement.close();

            reader.close();
        } catch (SQLException e) {
            System.out.println("Database may already be populated or error loading database.");
            // e.printStackTrace(); // Don't swallow DB errors
        }
    }

    public void deleteData(String script) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(script));
            String line = reader.readLine();
            // assumes each query is its own line
            while (line != null) {
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

    // prints message for invalid parameters
    public void printError() {
        System.out.println("Invalid command inputs. Please type h for help!");
    }

    // prints message for invalid query or database isn't loaded to run query
    public void printInvalidQueryOrDb() {
        System.out.println("Invalid command inputs or database isn't loaded to run query. Please type h for help!");
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
                System.out.println("\nShowing Roster for " + teamName + " for the " + season + " season:\n");

                String formatString = "| %-20s | %-20s | %-20s |%n"; // Format Structure
                System.out.printf("|----------------------|----------------------|----------------------|%n"); // Top
                System.out.printf(formatString, "First Name", "Last Name", "Jersey Number"); // Column Labels
                // Bar

                do {
                    System.out.println("|----------------------|----------------------|----------------------|");
                    System.out.printf("| %-20s | %-20s | %-20s |%n",
                            resultSet.getString("firstName"), resultSet.getString("lastName"),
                            resultSet.getInt("jersey"));
                } while (resultSet.next());
                System.out.printf(
                        "|----------------------|----------------------|----------------------|%n%n"); // Lower
                // Bar
            } else
                System.out.println("Either invalid team name or season entered.\n");
                
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
        }
    }

     // 2 DONE
    public void gameAppear(String first, String last) {
        try {
            String sql = "with gamesPlayedPlayer as (\n" + //
                    "\tselect p.playerID, p.firstname, p.lastname, g.season, count(distinct gps.gameID) as playerGP\n" + //
                    "\tfrom players p join gamePlayerStats gps on p.playerID = gps.playerID join games g on gps.gameID = g.gameID join gameTeamStats gts on gts.gameID = gps.gameID and gts.gameID = g.gameID\n"
                    + //
                    "\tgroup by p.playerID, p.firstname, p.lastname, g.season ),\n" + //
                    "gamesPlayedTeam as (\n" + //
                    "\tselect gts.teamName, g.season, count(distinct gts.gameID) as teamGP\n" + //
                    "\tfrom gameTeamStats gts join games g on gts.gameID = g.gameID\n" + //
                    "\tgroup by gts.teamName, g.season)\n" + //
                    "select gpp.season, gpt.teamName, gpp.firstname, gpp.lastname, ((1.0*gpp.playerGP)/gpt.teamGP)*100.0 as appearancePercentage\n"
                    + //
                    "from gamesPlayedPlayer gpp join play on gpp.season = play.season and gpp.playerID = play.playerID join gamesPlayedTeam gpt on play.teamName = gpt.teamName and gpt.season = play.season\n"
                    + //
                    "where gpp.firstname like ? and gpp.lastname like ? order by gpp.season desc, gpp.lastname asc;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + first + "%");
            statement.setString(2, "%" + last + "%");

            ResultSet resultSet = statement.executeQuery();

             boolean initial = resultSet.next();

            if (initial) {
            System.out.println("\nShowing game appearance percentage for player with name similar to " + first + " " + last + "\n");
            String formatString = "| %-10s | %-15s | %-20s | %-20s | %-30s |%n"; // Format Structure
            System.out.printf(
                    "|------------|-----------------|----------------------|----------------------|--------------------------------|%n"); // Top
            System.out.printf(formatString, "Season", "Team", "First Name", "Last Name",
                    "Game Appearance Percentage (%)"); // Column Labels
            // Bar
            do{
                System.out.printf(
                        "|------------|-----------------|----------------------|----------------------|--------------------------------|%n");
                System.out.printf("| %-10s | %-15s | %-20s | %-20s | %-30.1f |%n", resultSet.getString("season"),
                        resultSet.getString("teamName"),
                        resultSet.getString("firstName"), resultSet.getString("lastName"),
                        resultSet.getFloat("appearancePercentage"));
            } while (resultSet.next());
            System.out.printf(
                    "|------------|-----------------|----------------------|----------------------|--------------------------------|%n%n");
            } else
                System.out.println("Player not found.\n");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            printInvalidQueryOrDb();
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
                    + //
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
            statement.setInt(3, num);
            statement.setString(4, "%" + first + "%");
            statement.setString(5, "%" + last + "%");

            ResultSet resultSet = statement.executeQuery();

            boolean initial = resultSet.next();

            if (initial) {
                System.out.println("\nShowing teammates for player with name similar to " + first + " " + last + "\n");

                String formatString = "| %-10s | %-20s | %-20s | %-20s |%n"; // Format Structure
                System.out.printf(
                        "|------------|----------------------|----------------------|----------------------|%n");
                System.out.printf(formatString, "Season", "Team", "First Name", "Last Name"); // Column Labels

                do {
                    System.out.printf(
                            "|------------|----------------------|----------------------|----------------------|%n");
                    System.out.printf("| %-10s | %-20s | %-20s | %-20s |%n",
                            resultSet.getString("season"), resultSet.getString("teamName"),
                            resultSet.getString("firstname"), resultSet.getString("lastname"));
                } while (resultSet.next());
                System.out.printf(
                        "|------------|----------------------|----------------------|----------------------|%n%n");
            } else
                System.out.println("Player not found.\n");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
        } catch (NumberFormatException nfe) {
            printError();
        }
    }

    // 4 THIS IS DONE
    public void rankOfficials(String lim) {
        try {
            int num = Integer.parseInt(lim);
            if (num <= 0) {
                printError();
                return;
            }
            String sql = "select top (?) o.officialID, o.firstName, o.lastName, count(officiate.gameID) as numGames from officials o join officiate on o.officialID = officiate.officialID group by o.officialID, o.firstName, o.lastName order by numGames desc, o.lastname asc;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, num);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\nShowing the officials who have officiated the most games:\n");
            String format = ("| %-10s | %-20s | %-20s | %-10s |%n");
            System.out.printf("|------------|----------------------|----------------------|------------|%n");
            System.out.printf(format, "ID", "FirstName", "LastName", "Games");
            System.out.printf("|------------|----------------------|----------------------|------------|%n");
            String floats = ("| %-10d | %-20s | %-20s | %-10d |%n");

            while (resultSet.next()) {
                System.out.printf(floats, resultSet.getInt("officialID"), resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getInt("numGames"));
                System.out.printf("|------------|----------------------|----------------------|------------|%n%n");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
        } catch (NumberFormatException nfe) {
            printError();
        }
    }

    // 5
    // DONE
    public void stadiumInfo() {
        try {
            String sql = "select teams.basedIn,teams.teamName, stadiums.stadiumName, stadiums.capacity from teams join stadiums on teams.stadiumName = stadiums.stadiumName order by teams.basedIn;";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("\nShowing all the teams and their stadium info:\n");
            String format = ("| %-35s | %-30s | %-10s |%n"); // Header
            System.out
                    .printf("|-------------------------------------|--------------------------------|------------|%n");
            System.out.printf(format, "Team", "Stadium", "Capacity");
            System.out
                    .printf("|-------------------------------------|--------------------------------|------------|%n");
            String floats = ("| %-35s | %-30s | %-10d |%n");

            while (resultSet.next()) {
                String team = resultSet.getString("basedIn") + " " + resultSet.getString("teamName");
                System.out.printf(floats, team, resultSet.getString("stadiumName"), resultSet.getInt("capacity"));
                System.out.printf(
                        "|-------------------------------------|--------------------------------|------------|%n%n");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
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
                System.out.println("\nShowing number of players from each country for the " + season + " season:\n");

                String format = ("| %-30s | %-15s |%n");
                System.out.printf("|--------------------------------|-----------------|%n");
                System.out.printf(format, "Country", "NumPlayers");
                System.out.printf("|--------------------------------|-----------------|%n");
                String floats = ("| %-30s | %-15d |%n");

                do {
                    System.out.printf(floats,
                            resultSet.getString("country"), resultSet.getInt("numPlayers"));
                    System.out.printf("|--------------------------------|-----------------|%n%n");
                } while (resultSet.next());

                // Bar
            } else
                System.out.println("Invalid season entered.\n");

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
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
                    "select top (?) cgw.firstname, cgw.lastname, 100.0* cgw.gamesWon/cgp.gamesPlayed as winPercentage\n"
                    + //
                    "from coaches c join coachGamesWon cgw on c.coachID = cgw.coachID join coachGamesPlayed cgp on c.coachID = cgp.coachID\n"
                    + //
                    "order by winPercentage desc;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lim);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\nShowing coaches and their win percentages over the regular season:\n");
            String format = ("| %-20s | %-20s | %-10s |%n");
            System.out.printf("|----------------------|----------------------|------------|%n");
            System.out.printf(format, "FirstName", "LastName", "Win%");
            System.out.printf("|----------------------|----------------------|------------|%n");
            String floats = ("| %-20s | %-20s | %-10.1f |%n");
            while (resultSet.next()) {
                System.out.printf(floats, resultSet.getString("firstname"), resultSet.getString("lastname"),
                        resultSet.getFloat("winPercentage"));
                System.out.printf("|----------------------|----------------------|------------|%n%n");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
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
                sql = "select top (?) p.firstname,p.lastName, avg(cast(gps.[" + stat + "] as float)) as avgStat "
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

            String formatString = "| %-30s | %-30s | %-15s |%n"; // Format Structure
            System.out
                    .printf("|--------------------------------|--------------------------------|-----------------|%n");
            System.out.printf(formatString, "FirstName", "LastName", "AVG " + stat); // Column Labels
            System.out
                    .printf("|--------------------------------|--------------------------------|-----------------|%n"); // Top
                                                                                                                        // Bar
            String avgs = "| %-30s | %-30s | %-15.1f |%n";

            while (resultSet.next()) {
                System.out.printf(avgs, resultSet.getString("firstname"), resultSet.getString("lastName"),
                        resultSet.getDouble("avgStat"));
                System.out.printf(
                        "|--------------------------------|--------------------------------|-----------------|%n");
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
                        + "(SELECT players.playerID, players.firstname, players.lastname, avg(cast(gps.[" + stat
                        + "] as float)) as regAvg "
                        + "FROM players join gamePlayerStats gps on players.playerID = gps.playerID "
                        + "JOIN games on gps.gameID = games.gameID WHERE games.gameID not in "
                        + "(SELECT gameID from playoffGames) "
                        + "group by players.playerID, players.firstname, players.lastname), "
                        + "playoff as "
                        + "(SELECT players.playerID, players.firstname, players.lastname, avg(cast(gps.[" + stat
                        + "] as float)) as pfAvg "
                        + "FROM players join gamePlayerStats gps on players.playerID = gps.playerID "
                        + "JOIN games on gps.gameID = games.gameID "
                        + "JOIN playoffGames on games.gameID = playoffGames.gameID "
                        + "group by players.playerID, players.firstname, players.lastname) "
                        + "SELECT reg.playerID, reg.firstname, reg.lastname, reg.regAvg, playoff.pfAvg "
                        + "FROM reg join playoff on reg.playerID = playoff.playerID "
                        + "WHERE lower(reg.firstname) like lower(?) and lower(reg.lastname) like lower(?);";
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
            String formatString = "| %-30s | %-30s | %-15s | %-15s |%n"; // Format Structure
            System.out.printf(
                    "|--------------------------------|--------------------------------|-----------------|-----------------|%n");
            System.out.printf(formatString, "FirstName", "LastName", "REG AVG" + stat, "PF AVG" + stat); // Column
                                                                                                         // Labels
            System.out.printf(
                    "|--------------------------------|--------------------------------|-----------------|-----------------|%n"); // Top
                                                                                                                                  // Bar
            String avgs = "| %-30s | %-30s | %-15.1f | %-15.1f |%n";
            while (resultSet.next()) {
                System.out.printf(avgs, resultSet.getString("firstname"), resultSet.getString("lastName"),
                        resultSet.getDouble("regAvg"), resultSet.getDouble("pfAvg"));
                System.out.printf(
                        "|--------------------------------|--------------------------------|-----------------|-----------------|%n");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
            // e.printStackTrace(System.out);
        }
    }

    // 10 DONE
    public void playerStatsPerTeam(String first, String last) {
        try {
            String sql = "select p.playerID, p.firstName, p.lastName, play.teamName, "
                    + "avg(cast(gps.pts as float)) as points, avg(cast(gps.reb as float)) as rebounds, avg(cast(gps.ast as float)) as assists, avg(cast(gps.blk as float)) as blocks, avg(cast(gps.stl as float)) as steals "
                    + "from Players p join Play on p.playerID = play.playerID "
                    + "join GamePlayerStats gps on play.playerID = gps.playerID "
                    + "join Games on gps.gameID = Games.gameID "
                    + "where lower(p.firstName) like lower(?) AND lower(p.lastName) like lower(?) AND Play.season = Games.season "
                    + "group by p.playerID, p.firstName, p.lastName, Play.teamName "
                    + "order by Play.teamName;";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + first + "%");
            statement.setString(2, "%" + last + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing Major stat averages for player: " + first + " " + last
                    + " for all teams they have played for: ");

            String formatString = "| %-20s | %-20s | %-15s | %-10s | %-10s | %-10s | %-10s | %-10s |%n"; // Format
                                                                                                         // Structure

            System.out.printf(
                    "|----------------------|----------------------|-----------------|------------|------------|------------|------------|------------|%n");
            System.out.printf(formatString, "FirstName", "LastName", "Team", "Points", "Rebounds", "Assists", "Blocks",
                    "Steals"); // Column Labels
            System.out.printf(
                    "|----------------------|----------------------|-----------------|------------|------------|------------|------------|------------|%n"); // Top
            // Bar
            String avgs = "| %-20s | %-20s | %-15s | %-10.1f | %-10.1f | %-10.1f | %-10.1f | %-10.1f |%n";

            while (resultSet.next()) {
                System.out.printf(avgs, resultSet.getString("firstname"), resultSet.getString("lastName"),
                        resultSet.getString("teamName"), resultSet.getDouble("points"),
                        resultSet.getDouble("rebounds"), resultSet.getDouble("assists"),
                        resultSet.getDouble("blocks"), resultSet.getDouble("steals"));
                System.out.printf(
                        "|----------------------|----------------------|-----------------|------------|------------|------------|------------|------------|%n");
            }

        } catch (SQLException e) {
            System.out.println("You have entered unexpected parameters. Type h for help!");
            // e.printStackTrace(System.out);
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

            String formatString = "| %-15s | %-15s |%n"; // Format Structure
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
        int year = Integer.parseInt(season);
        if(year < 2016){
            System.out.println("Invalid season year input. Please input a valid season year ex. [2016/2017]");
            return;
        }
        try {
            String sql = "SELECT  p.firstname, p.lastname,"
                    + " SUM(gps.fgm * 85.910 + gps.stl * 53.897 + gps.[3pm] * 51.757 + gps.ftm * 46.845 + gps.blk * 39.190 + gps.oreb * 39.190 + gps.ast * 34.677 + gps.dreb * 14.707"
                    + " - gps.pf * 17.174 - (gps.fta - gps.ftm) * 20.091 - (gps.fga - gps.fgm) *39.190 - gps.tov * 53.897)/SUM(gps.min) AS avgPER "
                    + " FROM Players p "
                    + " LEFT JOIN Play szn ON p.playerID = szn.playerID"
                    + " LEFT JOIN gamePlayerStats gps ON szn.playerID = gps.playerID"
                    + " LEFT JOIN games rg ON gps.gameID = rg.gameID AND rg.season = szn.season"
                    + " WHERE lower(szn.teamName) LIKE lower(?)"
                    + " AND lower(szn.season) LIKE lower(?)"
                    + " GROUP BY p.firstname, p.lastname"
                    + " ORDER BY avgPer DESC";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + team + "%");
            statement.setString(2, "%" + season + "%");
            ResultSet resultSet = statement.executeQuery();

            System.out.println(
                    "Showing the highest player efficiency rating from " + team + " for the " + season + " season: /n");

            String formatString = "| %-20s | %-20s | %-15s |%n"; // Format Structure
            String printFormat = "| %-20s | %-20s | %-15.1f |%n";
            System.out.printf("|----------------------|----------------------|-----------------|%n");
            System.out.printf(formatString, "First Name", "Last Name", "PER"); // Column Labels
            System.out.printf("|----------------------|----------------------|-----------------|%n"); // Top Bar

            while (resultSet.next()) {
                System.out.printf(printFormat, resultSet.getString("firstname"), resultSet.getString("lastname"),
                        resultSet.getDouble("avgPer"));
                System.out.printf("|----------------------|----------------------|-----------------|%n");
            }

        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
        }
    }

    // 13 DONE
    public void draftComm(String year, String round) {
        try {
            int y = Integer.parseInt(year);
            int r = Integer.parseInt(round);
            if(y < 1998){
                System.out.println("Invalid draft year, not in database. Please enter a draft year between [1998 - 2021]");
                return;
            } 
            if(r < 1){
                System.out.println("Invalid draft round. Please enter a valid draft round [1 - 2]");
                return;
            }
            String sql = "SELECT dyr.draftYear, dyr.round, dyr.pick, p.firstname, p.lastname, szn.season, "
                    + " SUM(gps.fgm * 85.910 + gps.stl * 53.897 + gps.[3pm] * 51.757 + gps.ftm * 46.845"
                    + " + gps.blk * 39.190 + gps.oreb * 39.190 + gps.ast * 34.677 + gps.dreb * 14.707"
                    + " - gps.pf * 17.174 - (gps.fta - gps.ftm) * 20.091 - (gps.fga - gps.fgm) *39.190 - gps.tov * 53.897)/SUM(gps.min) AS avgPER "
                    + " FROM DraftInfo dyr "
                    + " JOIN Play szn ON szn.playerID = dyr.playerID "
                    + " JOIN Players p ON p.playerID = szn.playerID "
                    + " JOIN GamePlayerStats gps ON gps.playerID = p.playerID "
                    + " JOIN Games rg ON rg.gameID = gps.gameID AND rg.season = szn.season "
                    + " WHERE CAST(LEFT(szn.season, 4) AS int) >= dyr.draftYear AND dyr.draftYear = ? AND dyr.round = ?"
                    + " GROUP BY dyr.draftYear, dyr.round, dyr.pick, p.firstname, p.lastname, szn.season"
                    + " ORDER BY dyr.pick, szn.season";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, y);
            statement.setInt(2, r);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the player efficiency rating for players drafted in round " + r + " of the " + y
                    + " draft:");

            String formatString = "| %-20s | %-20s | %-10s | %-15s | %-15s |%n"; // Format Structure
            String formatPrint = "| %-20s | %-20s | %-10s | %-15s | %-15.1f |%n"; // Print Structure w/ avgPer double
                                                                                  // value
            // rounded to .1
            System.out.printf(
                    "|----------------------|----------------------|------------|-----------------|-----------------|%n");
            System.out.printf(formatString, "First Name", "Last Name", "Pick", "Season Year", "PER");
            System.out.printf(
                    "|----------------------|----------------------|------------|-----------------|-----------------|%n");
            ;

            while (resultSet.next()) {
                System.out.printf(formatPrint, resultSet.getString("firstname"), resultSet.getString("lastname"),
                        resultSet.getString("pick"),
                        resultSet.getString("season"), resultSet.getDouble("avgPER")); // Column Labels
                System.out.printf(
                        "|----------------------|----------------------|------------|-----------------|-----------------|%n");
                ;
            }

        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
        }
    }

    // 14 DONE
    public void totalStat(String stat) {
        try {
            String sql;

            if (sanitize(stat)) {
                sql = "SELECT TOP 10 p.firstname, p.lastname, sum(gps.[" + stat
                        + "]) AS totalxStatistic FROM Players p JOIN GamePlayerStats gps ON p.playerID = gps.playerID JOIN games g ON gps.gameID = g.gameID GROUP BY p.firstname, p.lastname ORDER BY totalxStatistic DESC;";

            } else {
                System.out.println("You have entered unexpected paramters. Type h for help");
                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            // statement.setString(1, stat); //Not needed, not setting anything
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Showing the total " + stat + " of all players all time");

            String formatString = "| %-20s | %-20s | %-15s |%n";
            System.out.printf("|----------------------|----------------------|-----------------|%n");
            System.out.printf(formatString, "First Name", "Last Name", "Total " + stat);
            System.out.printf("|----------------------|----------------------|-----------------|%n");
            while (resultSet.next()) {
                System.out.printf(formatString, resultSet.getString("firstname"), resultSet.getString("lastname"),
                        resultSet.getInt("totalxStatistic"));
                System.out.printf("|----------------------|----------------------|-----------------|%n");
                ;

            }

        } catch (SQLException e) {
            printInvalidQueryOrDb();
            // e.printStackTrace(System.out);
        }
    }
}
