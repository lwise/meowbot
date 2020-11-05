## MeowBot
![build](https://github.com/lwise/meowbot/workflows/MeowBot%20CI/badge.svg) [![Kotlin version badge](https://img.shields.io/badge/kotlin-1.4.0-blue.svg)](http://kotlinlang.org/) [![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)


### Implemented Features
- Meowbot will respond when you meow :3
- [DnD Alignment Game](https://github.com/lwise/meowbot/wiki/Meowbot---DnD-Alignment-Game)
- Use `m!pic` command to get a random cat picture! 

### Quick Getting Started Guide

#### Prerequisite:
- Have Kotlin setup
- You need to setup a bot account for Meowbot on your discord server. Follow the guide here: https://discordpy.readthedocs.io/en/latest/discord.html
- Postgres installed with user and database setup. Currently we are developing and testing our code using Postgres 13. https://www.postgresql.org/download/
- Get an API key from https://thecatapi.com/

#### Create a `.env` File:
To run Meowbot locally and connected to the bot account you need a `.env` file with the following information:

```
BOT_TOKEN=<token provided to you from Discord for the bot account>
DATABASE_URL=<postgres url>
CAT_API_KEY=<thecatapi.com key>
```

If you want to run MeowBot on your server you will also need to replace the values in `config.properties` with those from your own server.

#### Running Meowbot:
Run the application from `src/main/kotlin/com/lwise/MeowBot.kt`. 
