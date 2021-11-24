## MeowBot
![build](https://github.com/lwise/meowbot/workflows/MeowBot%20CI/badge.svg) [![Kotlin version badge](https://img.shields.io/badge/kotlin-1.4.0-blue.svg)](http://kotlinlang.org/) [![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)


### Implemented Features
- Meowbot will respond when you meow :3
- [DnD Alignment Game](https://github.com/lwise/meowbot/wiki/Meowbot---DnD-Alignment-Game)
- Use `m!pic` command to get a random cat picture! 
- Meowbot can function as a music player

### Quick Getting Started Guide

#### Prerequisite:
- Have Kotlin setup
- You need to setup a bot account for Meowbot on your discord server. Follow the guide here: https://discordpy.readthedocs.io/en/latest/discord.html
- Postgres installed with user and database setup. Currently we are developing and testing our code using Postgres 13. https://www.postgresql.org/download/
- Get an API key from https://thecatapi.com/
- To use the Spotify functionality, you need to set up a developer application for Spotify: https://developer.spotify.com/dashboard/login

#### Create a `.env` File:
To run Meowbot locally and connected to the bot account you need a `.env` file with the following information:

```
BOT_TOKEN=<token provided to you from Discord for the bot account>
DATABASE_URL=<postgres url>
CAT_API_KEY=<thecatapi.com key>
SPOTIFY_CLIENT_ID=<spotify developer app client id>
SPOTIFY_CLIENT_SECRET=<spotify developer app client secret>
```

#### Setting up your test DB
You can use this to set up your local `users` table for Meowbot:
```sql
CREATE TABLE users (
  id bigint PRIMARY KEY NOT NULL,
  username varchar(32) NOT NULL,
  guild_id bigint NOT NULL DEFAULT 0,
  chaotic_points integer NOT NULL DEFAULT 0,
  lawful_points integer NOT NULL DEFAULT 0,
  good_points integer NOT NULL DEFAULT 0,
  evil_points integer NOT NULL DEFAULT 0,
  fish_points integer NOT NULL DEFAULT 0
);
```

Then, where `<your_id>` is your user id on discord and `<your_name>` is your Discord username WITHOUT the trailing numbers...

```sql
INSERT INTO users (id, username, guild_id) VALUES (<your_id>, '<your_name>', <your_guild_id>);
```

If you want to run MeowBot on your server you will also need to replace the values in `config.properties` with those from your own server.

#### Running Meowbot:
Run the application from `src/main/kotlin/com/lwise/MeowBot.kt`. 
