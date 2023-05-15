UniStep

Features

1. Club Information

- List all clubs: Get all the clubs and users’ preferred clubs.  
- Get club page: Get the specific club with its ratings and comments and some recommended clubs for user with the same club type.
- The Clubs can be modified by a special user Admin (role = 1): Add club, Edit club, Delete club.

Usages:
### Get list of all clubs
curl -X GET http://localhost:8080/api/10/clubs

Expected Output:
"allClubs": [
    {
      "id": 1,
      "name": "Chess Club",
      "email": "chessclub@unist.ac.kr",
      "clubType_id": 9,
      "description": "A club for chess enthusiasts",
      "mission": "To promote chess as a fun and intellectually stimulating activity",
      "contact": "John Smith",
      "head_id": 101
    },
...
],

"preferredClubs": [
    {
      "id": 14,
      "name": "Theater Club",
      "email": "theaterclub@unist.ac.kr",
      "clubType_id": 4,
      "description": "A club for students interested in acting and theater production",
      "mission": "To develop performance skills and stagecraft through various productions and events",
      "contact": "Ethan Brown",
      "head_id": 114
    },
…
]


### Get club page
curl -X GET http://localhost:8080/api/18/clubs/15

Expected Output:

{
"club": {
    "id": 15,
    "name": "Fashion Club",
    "email": "fashionclub@unist.ac.kr",
    "clubType_id": 2,
    "description": "A club for students interested in fashion design and styling",
    "mission": "To promote creativity and exploration in the fashion industry and develop skills in design and sewing",
    "contact": "Maggie Chen",
    "head_id": 115
  },
"user": {
    "id": 18,
    "name": "Gerda",
    "surname": "Artois",
    "email": "qfpkdr@unist.ac.kr",
    "password": "1Tqgvd",
    "role": 0
  },
  "ratings": [
    {
      "id": 14,
      "user_id": 16,
      "club_id": 15,
      "value": 5,
      "comment": "Spohn"
    },
…},
"ratingAverage": 3.1,
"recommendedClubs": [
    {
      "id": 2,
      "name": "Photography Club",
      "email": "photoclub@unist.ac.kr",
      "clubType_id": 2,
      "description": "A club for photography enthusiasts",
      "mission": "To provide a space for students to explore and develop their photographic skills",
      "contact": "Jane Doe",
      "head_id": 102
    },
…}
}

### Add club
curl -X POST -H "Content-Type: application/json" -d '{"name": "Marat", "email": "marat@gmail.com", "clubType_id": 2, "description": "Marat Yerkebayev is the best!", "mission": "Bar", "contact": "87009809778", "head_id": 2}' http://localhost:8080/admin/clubs

Excepted output:
{
  "id": 46,
  "name": "Marat",
  "email": "marat@gmail.com",
  "clubType_id": 2,
  "description": "Marat Yerkebayev is the best!",
  "mission": "Bar",
  "contact": "87009809778",
  "head_id": 2
}

### Edit club
curl -X PUT http://localhost:8080/admin/clubs/5 -H "Content-Type: application/json" -d '{"name": "Marat Demo", "email": "marat@gmail.com", "clubType_id": 3, "description": "Marat Yerkebayev is the best!", "mission": "Bar", "contact": "87009809778", "head_id": 2}'

Excepted output:
{
  "id": 5,
  "name": "Marat Demo",
  "email": "marat@gmail.com",
  "clubType_id": 3,
  "description": "Marat Yerkebayev is the best!",
  "mission": "Bar",
  "contact": "87009809778",
  "head_id": 2
}

### Delete club
curl -X DELETE http://localhost:8080/admin/clubs/3

Excepted output:
Deleted...


2. Ratings and Comments

- Get average rating of Club: It takes two arguments: user_id, club_id. Checks whether they exist and returns average rating of the club

- Get all comments and ratings of the club: It takes two arguments: user_id, club_id.  Checks whether they exist and returns all the comments and ratings of each user for the specific club.

Usages:

 ### Get average rating of club
curl -X GET http://localhost:8080/api/5/clubs/14/ratings/avg

Expected Output: 
3.1

### Get all ratings and comments of club
curl -X GET http://localhost:8080/api/5/clubs/14/ratings
Expected Output:
[
  {
    "id": 13,
    "user_id": 15,
    "club_id": 14,
    "value": 4,
    "comment": "Di Loreto"
  },
  {
    "id": 46,
    "user_id": 48,
    "club_id": 14,
    "value": 2,
    "comment": "Lerdahl"
  }, ... ]

### Add rating in club
curl -X POST http://localhost:8080/api/1/clubs/1/ratings -H 'Content-Type: application/json' -d '{"rating": 5, "comment": "This club is great!"}'

Excepted output:
{
  "id": 458,
  "user_id": 1,
  "club_id": 1,
  "value": 5,
  "comment": "This club is great!"
}

### Edit rating
curl -X PUT http://localhost:8080/api/1/clubs/1/ratings/539 \
-H 'Content-Type: application/json' \
-d '{
    "rating": 7,
    "comment": "This club is amazing!"
}'

Excepted output:
{
  "id": 6,
  "user_id": 8,
  "club_id": 7,
  "value": 7,
  "comment": "This club is amazing!"
}

### Delete rating
curl -X DELETE http://localhost:8080/api/1/clubs/1/ratings/539

Excepted output:
Deleted...


3. Recommendations
- Choose types: Authenticated User manages preferred club types 
- Get recommended clubs by user preferences: We recommend clubs to the user based on their preferred club types.
- Get recommended clubs by club type: If a user is on the page of one club, they can see other clubs of the same type.
 
Usages
### Add preferred club types
curl -X POST http://localhost:8080/api/10/choose_types -H "Content-Type: application/json" -d '[1, 5, 7]'

Excepted output:
Club types added successfully

### Get clubs by user prefer
curl -X GET http://localhost:8080/api/15/clubs/prefer

Expected Output:
[
  {
    "id": 14,
    "name": "Theater Club",
    "email": "theaterclub@unist.ac.kr",
    "clubType_id": 4,
    "description": "A club for students interested in acting and theater production",
    "mission": "To develop performance skills and stagecraft through various productions and events",
    "contact": "Ethan Brown",
    "head_id": 114
  }, ... ]


### Get clubs by type of that club
curl -X GET http://localhost:8080/api/1/clubs/1/prefer

Expected Output:
[
  {
    "id": 1,
    "name": "Chess Club",
    "email": "chessclub@unist.ac.kr",
    "clubType_id": 9,
    "description": "A club for chess enthusiasts",
    "mission": "To promote chess as a fun and intellectually stimulating activity",
    "contact": "John Smith",
    "head_id": 101
  }, ... ]

4. Registration

- Sign up: User fills all the necessary information. System will make sure that the User filled UNIST Email. Also, checks whether the user is already registered or not.
- Sign in: Checks the email and password. If it is valid, returns user id. In another case returns -1.

Usages

### Login
curl -X GET http://localhost:8080/login?email=yerkebayev@unist.ac.kr&password=123hey

Excepted output: 
374

### Register
curl -X POST http://localhost:8080/register \
-H "Content-Type: application/json" \
-d '{"name": "Irina", "surname": "Kairatovna", "email": "weareik@unist.ac.kr", "password": "123ik"}'

Excepted output:
{
  "id": 376,
  "name": "Irina",
  "surname": "Kairatovna",
  "email": "weareik@unist.ac.kr",
  "password": "123ik",
  "role": null
}
