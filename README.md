# MS Calendar Timezone fix

Outlook shares its calendar with its own timezone IDs, not with standard (IANA) ones. Because of that Google Calendar
imports them by falling back to UTC, causing dates to shift.

## Deployment
First create your config file at `src/main/resources/config.properties`, set `calendarUrl=<original calendar URL here>`.
Then:

```shell
# Build image to tarball (linux/amd64 by default)
./gradlew jibBuildTar

# Copy tarball to the remote machine, then load it
docker load --input ms-calendar-tz-fix.tar

# Start it on the remote machine
docker run -p 8080:8080 -d --restart=always --name ms-calendar-tz-fix abelk/ms-calendar-tz-fix:1.0.0
```

### Docker image platform

Images are built for **linux/amd64** by default. Override at build time with Gradle project properties:

```shell
# 32-bit ARM (e.g. older Raspberry Pi)
./gradlew jibBuildTar -PjibArchitecture=arm

# 64-bit ARM
./gradlew jibBuildTar -PjibArchitecture=arm64
```

Architecture values match [Jib platform names](https://github.com/GoogleContainerTools/jib/blob/master/docs/faq.md#how-do-i-specify-a-platform-in-the-manifest-list-or-oci-index-of-a-base-image).

After this, you can set Google Calendar to use the new calendar URL (wherever you're hosting it).
