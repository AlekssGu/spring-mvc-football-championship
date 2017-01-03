-- Database: football_champ

-- DROP DATABASE football_champ;

CREATE DATABASE football_champ
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Latvian_Latvia.1257'
    LC_CTYPE = 'Latvian_Latvia.1257'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;    
    
CREATE SEQUENCE game_seq;
CREATE SEQUENCE game_penalties_seq;
CREATE SEQUENCE game_changes_seq;
CREATE SEQUENCE game_referees_seq;
CREATE SEQUENCE game_goals_seq;
CREATE SEQUENCE goal_assists_seq;
CREATE SEQUENCE players_on_field_seq;
CREATE SEQUENCE team_seq;
CREATE SEQUENCE team_players_seq;
CREATE SEQUENCE game_referees_link_seq;

-- drop table game CASCADE;    
    
CREATE TABLE game (
    id Integer NOT NULL DEFAULT nextval('game_seq'),
    game_date Date NOT NULL,
    attendees Integer NOT NULL,
    place Varchar(50) NOT NULL,
    game_round Integer,
    game_round_seq Integer,
    is_overtime Boolean,
    team_home_id Integer,
    team_guest_id Integer,
    team_won_id Integer,
    PRIMARY KEY (id),
    FOREIGN KEY (team_home_id) REFERENCES team (id),
    FOREIGN KEY (team_guest_id) REFERENCES team (id)
);    

-- drop table best_players;

CREATE TABLE best_players (
    id Integer NOT NULL, 
    goals Integer DEFAULT 0,
    assists Integer DEFAULT 0
);

-- drop table best_referees;

    CREATE TABLE best_referees (
        fullname varchar(50) NOT NULL,
        name varchar(50) NOT NULL, 
        surname varchar(50) NOT NULL, 
        penalties Integer DEFAULT 0
    );

-- drop table game_penalties;

CREATE TABLE game_penalties (
	id Integer NOT NULL DEFAULT nextval('game_penalties_seq'),
    team_id Integer NOT NULL,
    game_id Integer NOT NULL,
    penalty_time timestamp,
    player_number Integer,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES game (id),
    FOREIGN KEY (team_id) REFERENCES team (id)
);

-- drop table game_changes;

CREATE TABLE game_changes (
	id Integer NOT NULL DEFAULT nextval('game_changes_seq'),
    game_id Integer NOT NULL,
    team_id Integer NOT NULL,
    change_time timestamp,
    player_off_number Integer,
    player_on_number Integer,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES game (id),
    FOREIGN KEY (team_id) REFERENCES team (id)
);

-- drop table team CASCADE;

CREATE TABLE team (
  	id Integer NOT NULL DEFAULT nextval('team_seq'),
  	team_name varchar(50) NOT NULL, 
    game_count Integer DEFAULT 0,
    games_won Integer DEFAULT 0,
    games_lost Integer DEFAULT 0,
    games_tied Integer DEFAULT 0,
    goals_scored Integer DEFAULT 0,
    goals_lost Integer DEFAULT 0,
    goals_relation Integer DEFAULT 0,
    total_points Integer DEFAULT 0,
    PRIMARY KEY (id)
);    

-- drop table game_goals CASCADE;

CREATE TABLE game_goals (
	id Integer NOT NULL DEFAULT nextval('game_goals_seq'),
    game_id Integer NOT NULL,
    team_id	Integer NOT NULL,
    goal_time timestamp,
    player_number Integer,
    is_penalty varchar(1),
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES game (id),
    FOREIGN KEY (team_id) REFERENCES team (id)
);

-- drop table goal_assists;

CREATE TABLE goal_assists (
  	id Integer NOT NULL DEFAULT nextval('goal_assists_seq'),
    goal_id Integer NOT NULL,
    player_number Integer,
    PRIMARY KEY (id),
    FOREIGN KEY (goal_id) REFERENCES game_goals (id)
);    

-- drop table game_referees;

CREATE TABLE game_referees (
  	id Integer NOT NULL DEFAULT nextval('game_referees_seq'),
--    game_id Integer NOT NULL,
    referee_type varchar(20), -- VT / T
    referee_name varchar(50),
    referee_surname varchar(50),
    PRIMARY KEY (id)
  --  FOREIGN KEY (game_id) REFERENCES game (id)
);    

-- drop table game_referees_link;

CREATE TABLE game_referees_link (
    id Integer NOT NULL DEFAULT nextval('game_seq'),
    game_id Integer NOT NULL,
    referee_id Integer NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (game_id) REFERENCES game(id),
    FOREIGN KEY (referee_id) REFERENCES game_referees(id)
);    

-- drop table team_players CASCADE;

CREATE TABLE team_players (
  	id Integer NOT NULL DEFAULT nextval('team_players_seq'),
    team_id Integer NOT NULL,
    position varchar(1) NOT NULL,
    name varchar(50),
    surname varchar(50),
    player_number Integer,
    PRIMARY KEY (id),
    FOREIGN KEY (team_id) REFERENCES team (id)
);    

-- drop table players_on_field;

CREATE TABLE players_on_field (
  	id Integer NOT NULL DEFAULT nextval('players_on_field_seq'),
    game_id Integer NOT NULL,
    team_id Integer NOT NULL,
    player_id Integer NOT NULL,
    time_on timestamp,
    time_off timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (game_id) REFERENCES game (id),
    FOREIGN KEY (player_id) REFERENCES team_players (id),
    FOREIGN KEY (team_id) REFERENCES team (id)
);

/* queries
select * from public.team;
select * from public.game;
select * from public.game_referees;
select team_id, count(*) from public.team_players group by team_id;
select * from public.players_on_field;
select * from game_goals;
select * from goal_assists;
select * from game_changes;
select chn.*, tpl.name, tpl.surname, plo.time_on, plo.time_off, 
  from players_on_field plo
  join team tem on tem.id = plo.team_id
  join team_players tpl on tpl.team_id = tem.id and tpl.id = plo.player_id
  join game_changes chn on chn.team_id = tem.id and chn.player_on_number = tpl.player_number;
  
select * from game_penalties;  

delete from game_penalties;
delete from game_changes;
delete from goal_assists;
delete from game_goals;
delete from public.players_on_field;
delete from public.game_referees;
delete from public.game;
delete from public.team_players;
delete from public.team;
*/