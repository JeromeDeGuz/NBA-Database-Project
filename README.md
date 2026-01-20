# NBA Database Project

**A relational database system for analyzing NBA teams, players, games, and performance statistics**

This project was developed as part of **COMP 3380 – Databases** at the University of Manitoba. 
It demonstrates the full lifecycle of database development: **data discovery, ER/EER modeling, normalization, SQL query design, and interface implementation** — using real-world NBA data.

---

## Project Overview

Professional sports generate massive, highly connected datasets. This project models the **NBA ecosystem** to enable meaningful analysis of teams, players, games, and performance metrics.

The goal of this project was to:

* Design a **normalized relational database** from public NBA data
* Apply **ER/EER modeling principles** with justified constraints
* Write **non-trivial SQL queries** that answer analyst-driven questions
* Build a **safe, usable interface** for interacting with the database

> This project prioritizes **correct modeling, query expressiveness, and analytical usefulness** over UI polish.

---

## Core Concepts Demonstrated

This repository showcases proficiency in:

✔ Relational database design
✔ ER → relational model translation
✔ Normalization (up to BCNF / 3NF where applicable)
✔ SQL aggregation and analytical queries
✔ Defensive database access (SQL injection awareness)
✔ Translating real-world data into structured systems

---

## Dataset Description

The database is built using **publicly available NBA data**, representing entities such as:

* Players
* Teams
* Games
* Seasons
* Player statistics
* Team performance metrics

The dataset is **highly connected**, allowing complex queries such as:

* Player performance trends across seasons
* Team comparisons and rankings
* Aggregate statistics (averages, totals, rankings)

Data was cleaned and transformed prior to insertion to ensure **consistency, referential integrity, and minimal redundancy**.

---

## Database Design

### ER / EER Modeling

* Clearly defined entities and relationships
* Participation and cardinality constraints explicitly justified
* Design choices driven by **real-world NBA rules** (e.g., player-team associations, games involving two teams)

### Relational Model

* ER model translated into relational tables
* Tables merged and normalized to reduce redundancy
* Functional dependencies analyzed and resolved

---

## Technologies Used

| Component   | Details                        |
| ----------- | ------------------------------ |
| Database    | SQL (department-hosted server) |
| Language    | Python / Java (for interface)  |
| Interface   | Command-line (analyst-focused) |
| Data Source | Public NBA datasets            |

---

## Example Queries

The database supports **analyst-oriented queries**, including:

* Aggregated player statistics using `GROUP BY`
* Ranked results using `ORDER BY`
* Summary metrics using aggregate functions (AVG, SUM, COUNT)
* Parameterized queries to explore specific teams, seasons, or players

Each query was designed to answer a **real analytical question**, not just demonstrate syntax.

---

## Interface Overview

The project includes a lightweight interface that allows users to:

* Run predefined analytical queries
* Provide parameters safely (no raw SQL input)
* View clean, labeled, readable query results
* Reset and repopulate the database when needed

> The interface is intentionally simple — prioritizing **clarity, safety, and correctness**.

---

## Running the Project

### Makefile Commands: 

#### Running the Database:

Using the native command line terminal run the command, 
to build and run the database.
```bash
make msql
```

#### Cleaning up Database:

Using the native command line terminal run the command, 
to clean up the files created from building the database.
```bash
make clean
```

### **Explore**

   * Select queries from the menu
   * Input parameters
   * Review formatted results

> Designed to run on the university environment without additional installations.
> Ability to destroy and repopulate database from menu option.

