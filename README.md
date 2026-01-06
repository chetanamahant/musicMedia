
🎵 Media Player Application

📌 Project Overview

The Media Player Application is a backend system designed to replicate the core functionalities of modern music streaming platforms like Spotify. 
The primary goal of this project is to demonstrate how a real-world, scalable, and production-ready backend can be designed using Spring Boot and RESTful APIs.
This application focuses on music content management, user interactions, and listening behavior tracking, while keeping the architecture clean, modular, and extensible. 
It is built to handle multiple users, large song libraries, and continuous user interactions such as streaming, liking, and playlist management.

The project is intentionally developed as a backend-only system so it can easily integrate with:
          ->   Web applications (React / Angular)
          ->   Mobile applications (Android / iOS)
          ->   Third-party services (recommendation engines, analytics, etc.)

🚀 Features & Functionalities

🔐 Authentication & Authorization
•	User signup & login
•	JWT-based authentication
•	Token blacklisting (logout support)
•	Role-based access (USER / ADMIN)

🎶 Song Management
•	Add, update, delete songs
•	Fetch all songs / song by ID
•	Stream audio files
•	Search songs

📂 Playlist Management
•	Create playlists
•	Update playlist details
•	Add / remove songs from playlist
•	Fetch user playlists

❤️ Like System
•	Like a song
•	Remove like
•	Fetch liked songs by user

🕒 Listening History
•	Automatically track songs listened by users
•	Fetch recently played songs
•	Store listening timestamp

🎤 Artist Management
•	Create artists
•	Fetch artist details
•	Fetch songs by artist

💿 Album Management
•	Create albums
•	Assign songs to albums
•	Fetch album details

🏷 Genre Management
•	Create genres
•	Assign genre to songs
•	Fetch all genres

🎯 Recommendation System (Basic)
•	Recommend songs based on:
o	Listening history
o	Likes
o	Genre preference

🧩 Global Exception Handling
•	Custom exceptions for:
o	Resource not found
o	Unauthorized access
o	Duplicate users
o	Invalid requests
•	Centralized error response format

🧱 Project Architecture
The project follows Layered Architecture:
Controller  →  Service Interface  →  Service Implementation  →  Repository  →  Database
DTOs are used for request/response abstraction, and Entities are isolated from API contracts.

📁 Project Structure (Reference)
src/main/java/com/example/mediaPlayer/demo
│
├── configuration
│   ├── AsyncConfig
│   ├── SecurityConfig
│   ├── JwtAuthenticationFilter
│   └── CustomUserDetailsService
│
├── Controller
│   ├── AuthController
│   ├── SongController
│   ├── PlaylistController
│   ├── AlbumController
│   ├── ArtistController
│   ├── GenreController
│   ├── HistoryController
│   ├── LikeController
│   └── RecommendationController
│
├── dto
│   ├── request
│   │   ├── LoginRequest
│   │   ├── SignupRequest
│   │   ├── PlaylistRequest
│   │   └── UpdatePlaylistRequest
│   └── response
│       ├── ApiSuccessResponse
│       ├── ApiErrorResponse
│       ├── LoginResponse
│       ├── SongResponseDTO
│       ├── PlaylistResponse
│       ├── AlbumResponseDTO
│       ├── GenreResponse
│       └── ListeningHistoryDTO
│
├── Entity
│   ├── User
│   ├── Song
│   ├── Playlist
│   ├── Album
│   ├── Artist
│   ├── Genre
│   ├── SongLike
│   ├── ListeningHistory
│   └── BlacklistedToken
│
├── exception
│   ├── GlobalExceptionHandler
│   ├── ResourceNotFoundException
│   ├── UnauthorizedException
│   ├── DuplicateUserException
│   └── BadRequestException
│
├── Repo
│   ├── UserRepo
│   ├── SongRepository
│   ├── PlaylistRepository
│   ├── AlbumRepository
│   ├── ArtistRepository
│   ├── GenreRepository
│   ├── LikeRepository
│   ├── HistoryRepository
│   └── BlacklistedTokenRepository
│
├── ServiceI
│   ├── AuthServiceInterface
│   ├── SongServiceInterface
│   ├── PlaylistServiceInterface
│   ├── AlbumServiceInterface
│   ├── ArtistServiceInterface
│   ├── GenreServiceInterface
│   ├── LikeServiceInterface
│   ├── HistoryServiceInterface
│   └── RecommendationServiceInterface
│
├── ServiceImpl
│   ├── AuthServiceImpl
│   ├── SongServiceImpl
│   ├── PlaylistServiceImpl
│   ├── AlbumServiceImpl
│   ├── ArtistServiceImpl
│   ├── GenreServiceImpl
│   ├── LikeServiceImpl
│   ├── HistoryServiceImpl
│   └── RecommendationServiceImpl
│
├── util
│   ├── JwtUtil
│   ├── FileStreamingUtil
│   ├── CustomUserDetails
│   ├── JwtAuthenticationEntryPoint
│   ├── JwtAccessDeniedHandler
│   └── Role
│
└── MediaPlayerApplication

🛠 Technology Stack
Category 	Technology
Language -	Java 21
Framework-	Spring Boot
Security -	Spring Security + JWT
ORM	     -  Spring Data JPA (Hibernate)
Database -	PostgreSQL
Build Tool -	Maven
API Style  -	RESTful APIs
File Streaming	Resource / FileSystemResource
Exception Handling - Lobal Exception handling (Custom exception)

📦 API Response Format
✅ Success Response
{
"success": true,
"message": "Song liked successfully",
"data": "Song liked",
"timestamp": "2026-01-06T20:00:25.1593864"
}

❌ Error Response

{
"status": False,
"message": "Resource not found",
"timestamp": "2026-01-06T12:30:00"
}


🧪 How to Run the Project
1.	Clone the repository
      git clone https://github.com/chetanamahant/musicMedia

2.	Configure PostgreSQL in application.properties
3.	Build & run
      mvn clean install
      mvn spring-boot:run
4.	Access APIs via Postman
      http://localhost:9091/api/

🔮 Future Enhancements
•	Advanced recommendation engine (ML-based)
•	Caching with Redis
•	Elasticsearch for fast song search
•	Audio analytics
•	User follow system
•	Admin dashboard

👨‍💻 Author
Chetana Mahant
Backend Software Engineer
Java | Spring Boot | Microservices | Secure APIs


