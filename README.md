# MS Calendar Timezone fix

Outlook shares its calendar with its own timezone IDs, not with standard (IANA) ones. Because of that Google Calendar
imports them by falling back to UTC, causing dates to shift.

## Deployment
First create your config file at `src/main/resources/config.properties`, set `calendarUrl=<original calendar URL here>`.
Then:

```shell
# SSH to the machine where your Docker registry is
ssh user@machine -L5000:localhost:5000

# Build and deploy
./gradlew jib

# Start it on the remote machine
docker run -p 8080:8080 -d --restart=always \
  --name ms-calendar-tz-fix localhost:5000/ms-calendar-tz-fix:1.0.0
```

After this, you can set Google Calendar to use the new calendar URL (wherever you're hosting it).

## Notes
- You might want to change the architecture in the jib config in the build file, it's set to `arm` currently
  (I use it on Raspberry Pi).
