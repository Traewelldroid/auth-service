# auth-service

Ktor service for OAuth flow

# Endpoints

- `/auth` -> Requests a new authorization token and redirects it to the user
- `/auth/callback` -> OAuth 2 Callback url

# Configuration

| Name                      | Description                       | Default                                                              |
|---------------------------|-----------------------------------|----------------------------------------------------------------------|
| URL                       | The URL of the server             | `http://localhost:8080`                                              |
| HOSTNAME                  | The hostname the server runs on   | `0.0.0.0`                                                            |
| PORT                      | The PORT the server runs on       | `8080`                                                               |
| TRAEWELLING_URL           | The Träwelling instance used      | `https://traewelling.de`                                             |
| TRAEWELLING_CLIENT_ID     | Träwelling API Client ID          | Can be obtained [here](https://traewelling.de/settings/applications) |
| TRAEWELLING_CLIENT_SECRET | Träwelling API Client Secret      | Can be obtained [here](https://traewelling.de/settings/applications) |
| DEEPLINK                  | URL used to deeplink into the APP | `https://app.traewelldroid.de`                                       |

# Docker

Pre-built Docker images can be found [here](https://github.com/Traewelldroid/auth-service/pkgs/container/auth-service%2Fservice) and a [docker-compose file here](docker-compose.yaml)
