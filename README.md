## MeowBot
![build](https://github.com/lwise/meowbot/workflows/MeowBot%20CI/badge.svg) [![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)


### Quick Getting Started Guide

#### Prerequsite:
- Have Kotlin setup
- You need to setup a bot account for Meowbot on your discord server. Follow the guide here: https://discordpy.readthedocs.io/en/latest/discord.html
- Postgres installed with user and database setup. Currently we are developing and testing our code using Postgres 13. https://www.postgresql.org/download/

#### Create a `.env` File:
To run Meowbot locally and connected to the bot account you need a `.env` file with the following information:

```
BOT_TOKEN=<token provided to you from Discord for the bot account>
DATABASE_URL=<postgres url>
```

#### Running Meowbot:
Run the application from `src/main/kotlin/com/lwise/MeowBot.kt`. 
